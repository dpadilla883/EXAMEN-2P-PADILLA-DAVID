# EXAMEN 2P - ARQUITECTURA DE SOFTWARE

# NOMBRE: DAVID PADILLA

# AndesFin – Microservicio de Simulación de Inversión (Backend)

Microservicio backend para **AndesFin** que permite:
- Consultar **usuarios**
- Consultar **productos financieros activos**
- Registrar **simulaciones de inversión** (optimización por combinaciones)
- Consultar **simulaciones anteriores** por usuario

La simulación recibe un arreglo de productos candidatos (precio/costo, riesgo, % ganancia), evalúa combinaciones posibles y devuelve una propuesta óptima respetando el capital disponible, con métricas transparentes (costo total, capital restante, ganancia total, retorno %, riesgo promedio, eficiencia de capital). Además, persiste la simulación y su detalle para auditoría y trazabilidad.

---

## Tecnologías

- **Java 17**
- **Spring Boot** (Web)
- **Spring Data JPA (Hibernate)** (ORM)
- **PostgreSQL 16**
- **Maven**
- **Docker + Docker Compose**

---

## Requisitos

- Docker Desktop instalado (Windows)
- Docker Compose (incluido en Docker Desktop)
- Postman para pruebas

---

## Ejecución (Docker)

Desde la **raíz del proyecto** (donde está `docker-compose.yml`):

```bash
docker compose up --build
```

- **Backend**: `http://localhost:8080`
- **PostgreSQL**: `localhost:5432`

### Reiniciar en limpio 
Los scripts SQL se ejecutan automáticamente al crear el volumen por primera vez. Para reiniciar en limpio:

```bash
docker compose down -v
docker compose up --build
```

---

## Carga inicial (scripts SQL)

Al levantar `docker-compose`, PostgreSQL ejecuta automáticamente los scripts en `db/init/`:

- `01_schema.sql` → crea tablas
- `02_seed.sql` → inserta datos iniciales (**mínimo 5 usuarios y 8 productos**)

> Nota: los UUID deben ser válidos (solo 0-9 y a-f).

---

## Endpoints

**Base URL:** `http://localhost:8080`

### 1) Listar usuarios
**GET** `/usuarios`

Ejemplo:
```bash
curl http://localhost:8080/usuarios
```

Respuesta (ejemplo):
```json
[
  {
    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "nombre": "Juan Pérez",
    "email": "juan.perez@email.com",
    "capital_disponible": 5000.00
  }
]
```

---

### 2) Listar productos financieros activos
**GET** `/productos`

Ejemplo:
```bash
curl http://localhost:8080/productos
```

---

### 3) Realizar simulación (optimización)
**POST** `/simulaciones`

> Recomendación: primero llama **GET /usuarios** para copiar un `usuario_id` real.

Body (JSON):
```json
{
  "usuario_id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "capital_disponible": 3000.00,
  "productos": [
    { "nombre": "Fondo Acciones Tech", "precio": 1000.00, "riesgo": 6, "porcentaje_ganancia": 8.50 },
    { "nombre": "Bonos Corporativos AAA", "precio": 500.00,  "riesgo": 3, "porcentaje_ganancia": 5.25 },
    { "nombre": "ETF Global",          "precio": 1500.00, "riesgo": 7, "porcentaje_ganancia": 12.00 },
    { "nombre": "Fondo de Dividendos", "precio": 800.00,  "riesgo": 5, "porcentaje_ganancia": 6.75 }
  ]
}
```

Respuesta (ejemplo resumido):
```json
{
  "id": "f6a7b8c9-d0e1-2345-abcd-678901234567",
  "usuario_id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "fecha_simulacion": "2026-02-03T01:30:00Z",
  "capital_disponible": 3000.00,
  "productos_seleccionados": [
    { "nombre": "ETF Global", "precio": 1500.00, "riesgo": 7, "porcentaje_ganancia": 12.00, "ganancia_esperada": 180.00 },
    { "nombre": "Fondo Acciones Tech", "precio": 1000.00, "riesgo": 6, "porcentaje_ganancia": 8.50, "ganancia_esperada": 85.00 }
  ],
  "costo_total": 2500.00,
  "capital_restante": 500.00,
  "ganancia_total": 265.00,
  "retorno_total_porcentaje": 10.60,
  "riesgo_promedio": 6.50,
  "eficiencia_capital": 83.33,
  "mensaje": "Simulación exitosa con selección óptima (máxima ganancia sin exceder capital)."
}
```

**Algoritmo (resumen):** evalúa combinaciones de los candidatos (sin exceder el capital) y elige la combinación con **mayor ganancia total**.  
**Persistencia:** guarda la simulación y el detalle (candidatos evaluados + cuáles fueron seleccionados).

---

### 4) Consultar simulaciones por usuario
**GET** `/simulaciones/{usuarioId}`

Ejemplo:
```bash
curl http://localhost:8080/simulaciones/a1b2c3d4-e5f6-7890-abcd-ef1234567890
```

Respuesta (ejemplo):
```json
[
  {
    "id": "f6a7b8c9-d0e1-2345-abcd-678901234567",
    "usuario_id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
    "fecha_simulacion": "2026-02-03T01:30:00Z",
    "capital_disponible": 3000.00,
    "ganancia_total": 265.00,
    "cantidad_productos": 2,
    "retorno_porcentaje": 10.60
  }
]
```

---

## Ejemplo de cálculo (tabla)

Para el request de ejemplo (capital = 3000), la ganancia esperada por producto es:

**ganancia_esperada = precio × (porcentaje_ganancia / 100)**

| Producto | Precio | % Ganancia | Ganancia esperada | Riesgo |
|---|---:|---:|---:|---:|
| Fondo Acciones Tech | 1000.00 | 8.50 | 85.00 | 6 |
| Bonos Corporativos AAA | 500.00 | 5.25 | 26.25 | 3 |
| ETF Global | 1500.00 | 12.00 | 180.00 | 7 |
| Fondo de Dividendos | 800.00 | 6.75 | 54.00 | 5 |

**Selección óptima (ejemplo):** ETF Global + Fondo Acciones Tech  
- **Costo total** = 1500 + 1000 = 2500  
- **Ganancia total** = 180 + 85 = 265  
- **Retorno total (%)** = (265 / 2500) × 100 = **10.60%**  
- **Capital restante** = 3000 − 2500 = 500  
- **Riesgo promedio** = (7 + 6) / 2 = **6.50**  
- **Eficiencia de capital (%)** = (2500 / 3000) × 100 = **83.33%**

---

## Estructura del proyecto

- `src/main/java/...` → controladores, servicios, repositorios, DTOs y entidades
- `db/init/01_schema.sql` → creación de tablas
- `db/init/02_seed.sql` → datos iniciales (usuarios y productos)
- `docker-compose.yml` → levanta DB + backend + scripts automáticos
- `Dockerfile` → build y empaquetado del backend

---

## Capturas


### Prueba GET /usuarios (Postman)
![GET usuarios](docs/postman_usuarios.png)

### Prueba GET /productos (Postman)
![GET productos](docs/postman_productos.png)

### Prueba POST
![POST simulaciones](docs/postman_simulaciones.png)