package com.andesfin.andesfin_simulacion.service;

import com.andesfin.andesfin_simulacion.dto.SimulacionRequestDTO;
import com.andesfin.andesfin_simulacion.dto.SimulacionResponseDTO;
import com.andesfin.andesfin_simulacion.entity.*;
import com.andesfin.andesfin_simulacion.repository.SimulacionRepository;
import com.andesfin.andesfin_simulacion.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class SimulacionService {

    private final UsuarioRepository usuarioRepo;
    private final SimulacionRepository simulacionRepo;

    public SimulacionService(UsuarioRepository usuarioRepo, SimulacionRepository simulacionRepo) {
        this.usuarioRepo = usuarioRepo;
        this.simulacionRepo = simulacionRepo;
    }

    public SimulacionResponseDTO simular(SimulacionRequestDTO req) {
        Usuario usuario = usuarioRepo.findById(req.usuario_id())
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        BigDecimal capital = req.capital_disponible();
        List<SimulacionRequestDTO.ProductoCandidatoDTO> cand = req.productos();

        // 1) Filtrar candidatos viables (precio <= capital y precio > 0)
        List<SimulacionRequestDTO.ProductoCandidatoDTO> viables = cand.stream()
                .filter(p -> p.precio().compareTo(BigDecimal.ZERO) > 0)
                .filter(p -> p.precio().compareTo(capital) <= 0)
                .toList();

        // Caso: fondos insuficientes (ninguno viable)
        if (viables.isEmpty()) {
            BigDecimal masBarato = cand.stream()
                    .map(SimulacionRequestDTO.ProductoCandidatoDTO::precio)
                    .filter(x -> x.compareTo(BigDecimal.ZERO) > 0)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            throw new RuntimeException("Fondos insuficientes. Capital: " + capital + " producto_mas_barato: " + masBarato);
        }

        // 2) Evaluar todas las combinaciones (2^n) -> n pequeño (normalmente <= 8)
        int n = viables.size();
        BigDecimal mejorGanancia = BigDecimal.valueOf(-1);
        BigDecimal mejorCosto = BigDecimal.ZERO;
        int mejorMask = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            BigDecimal costo = BigDecimal.ZERO;
            BigDecimal ganancia = BigDecimal.ZERO;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    var p = viables.get(i);
                    costo = costo.add(p.precio());
                    if (costo.compareTo(capital) > 0) break;

                    // ganancia = precio * (%/100)
                    BigDecimal g = p.precio()
                            .multiply(p.porcentaje_ganancia())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    ganancia = ganancia.add(g);
                }
            }

            if (costo.compareTo(capital) <= 0) {
                // criterio: mayor ganancia; si empata, mayor costo (mejor uso de capital)
                if (ganancia.compareTo(mejorGanancia) > 0 ||
                        (ganancia.compareTo(mejorGanancia) == 0 && costo.compareTo(mejorCosto) > 0)) {
                    mejorGanancia = ganancia;
                    mejorCosto = costo;
                    mejorMask = mask;
                }
            }
        }

        // 3) Construir entidad + detalles para auditoría
        Simulacion sim = new Simulacion();
        sim.setId(UUID.randomUUID());
        sim.setUsuario(usuario);
        sim.setFechaSimulacion(OffsetDateTime.now());
        sim.setCapitalDisponible(capital);
        sim.setCostoTotal(mejorCosto);

        BigDecimal capitalRestante = capital.subtract(mejorCosto).setScale(2, RoundingMode.HALF_UP);
        sim.setCapitalRestante(capitalRestante);

        sim.setGananciaTotal(mejorGanancia.setScale(2, RoundingMode.HALF_UP));

        BigDecimal retornoPct = (mejorCosto.compareTo(BigDecimal.ZERO) == 0)
                ? BigDecimal.ZERO
                : mejorGanancia.multiply(BigDecimal.valueOf(100))
                .divide(mejorCosto, 2, RoundingMode.HALF_UP);
        sim.setRetornoTotalPorcentaje(retornoPct);

        // riesgo promedio (simple) solo de seleccionados
        BigDecimal sumaRiesgo = BigDecimal.ZERO;
        int countSel = 0;

        List<SimulacionResponseDTO.ProductoSeleccionadoDTO> seleccionadosResp = new ArrayList<>();

        for (var p : cand) {
            boolean seleccionado = false;

            // Solo marcamos seleccionado si pertenece a viables y está en el mask
            int idx = viables.indexOf(p);
            if (idx >= 0 && (mejorMask & (1 << idx)) != 0) {
                seleccionado = true;
                countSel++;

                BigDecimal g = p.precio()
                        .multiply(p.porcentaje_ganancia())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                sumaRiesgo = sumaRiesgo.add(p.riesgo());

                seleccionadosResp.add(new SimulacionResponseDTO.ProductoSeleccionadoDTO(
                        p.nombre(), p.precio(), p.riesgo(), p.porcentaje_ganancia(), g
                ));
            }

            // Guardamos TODOS los candidatos como detalle (evaluados) y marcamos seleccionado true/false
            SimulacionDetalle det = new SimulacionDetalle();
            det.setId(UUID.randomUUID());
            det.setSimulacion(sim);
            det.setProductoNombre(p.nombre());
            det.setPrecio(p.precio());
            det.setRiesgo(p.riesgo());
            det.setPorcentajeGanancia(p.porcentaje_ganancia());
            det.setGananciaEsperada(
                    p.precio().multiply(p.porcentaje_ganancia()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            );
            det.setSeleccionado(seleccionado);

            sim.getDetalles().add(det);
        }

        BigDecimal riesgoProm = (countSel == 0)
                ? BigDecimal.ZERO
                : sumaRiesgo.divide(BigDecimal.valueOf(countSel), 2, RoundingMode.HALF_UP);
        sim.setRiesgoPromedio(riesgoProm);

        sim = simulacionRepo.save(sim);

        BigDecimal eficiencia = (capital.compareTo(BigDecimal.ZERO) == 0)
                ? BigDecimal.ZERO
                : mejorCosto.multiply(BigDecimal.valueOf(100)).divide(capital, 2, RoundingMode.HALF_UP);

        String msg = "Simulación exitosa con selección óptima (máxima ganancia sin exceder capital).";

        return new SimulacionResponseDTO(
                sim.getId(),
                usuario.getId(),
                sim.getFechaSimulacion(),
                capital,
                seleccionadosResp,
                mejorCosto.setScale(2, RoundingMode.HALF_UP),
                capitalRestante,
                mejorGanancia.setScale(2, RoundingMode.HALF_UP),
                retornoPct,
                riesgoProm,
                eficiencia,
                msg
        );
    }

    public List<Map<String, Object>> listarPorUsuario(UUID usuarioId) {
        return simulacionRepo.findByUsuario_IdOrderByFechaSimulacionDesc(usuarioId).stream()
                .map(s -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", s.getId());
                    m.put("usuario_id", s.getUsuario().getId());
                    m.put("fecha_simulacion", s.getFechaSimulacion());
                    m.put("capital_disponible", s.getCapitalDisponible());
                    m.put("ganancia_total", s.getGananciaTotal());
                    long cantidad = s.getDetalles().stream().filter(SimulacionDetalle::getSeleccionado).count();
                    m.put("cantidad_productos", cantidad);
                    m.put("retorno_porcentaje", s.getRetornoTotalPorcentaje());
                    return m;
                }).toList();
    }
}
