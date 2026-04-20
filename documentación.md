# Documentación Detallada: Diagrama de Flujo `registrarLoteJuegos`

Este documento describe exhaustivamente la estructura, lógica y métricas del diagrama de flujo (`.drawio`) diseñado para el algoritmo de registro de inventario.

---

## 1. Descripción General del Flujo
El diagrama modela el comportamiento de un sistema que recibe dos arreglos (códigos de juegos y unidades por código) e intenta registrarlos en el inventario. El proceso se divide en tres fases principales:
1.  **Validación de entrada:** Comprobación de integridad y existencia de datos.
2.  **Pre-cálculo y validación de stock:** Acumulación de unidades y verificación de que no existan valores negativos ni se supere el límite de almacenamiento.
3.  **Registro y consolidación:** Actualización de la estructura de datos (mapa/diccionario) y suma de las unidades al contador final.

---

## 2. Diccionario de Nodos (Etiquetas)

Cada etiqueta roja en el diagrama representa un estado, decisión o proceso clave en el algoritmo:

### Inicialización y Validaciones Tempranas
* **A (INICIO):** Punto de entrada del algoritmo.
* **B:** Inicialización de variable de control (`juegosRegistrados = 0`).
* **C (Decisión):** Evalúa si las longitudes de los arreglos difieren (`length !=`). Si es Verdadero (T), va hacia el error.
* **F (Decisión):** Evalúa si los arreglos están vacíos (`length = 0`). Si es Verdadero (T), va hacia el error.
* **D:** Ruta de confluencia para los errores de longitud que lleva al retorno de `-1`.

### Pre-cálculo (Bucle 1)
* **G:** Inicialización del acumulador (`cantidadtotal = 0`).
* **H (Decisión - Bucle):** Condición de iteración del primer bucle (`i < lenght`). Si es Falso (F), el bucle termina y avanza a **M**.
* **I (Decisión):** Evalúa si la unidad actual es negativa (`uPC < 0`).
* **J:** Retorno de error `-2` si se detecta una cantidad negativa. Sale a **Z**.
* **L:** Incremento y acumulación (`cT += upc`, `i++`). Vuelve a **H**.

### Validación de Capacidad
* **M (Decisión):** Evalúa si el stock actual más el total a ingresar supera el máximo (`oSA + cT > mS`).
* **Ñ:** Retorno de error `-3` si se supera la capacidad. Sale a **Z**.

### Registro en Sistema (Bucle 2)
* **N:** Reinicio del iterador (`i = 0`).
* **O (Decisión - Bucle):** Condición de iteración del segundo bucle (`i < cl`). Si es Falso (F), termina y va a **R**.
* **S (Decisión):** Verifica si el juego ya existe en el sistema (`SJ`).
* **P:** Proceso de actualización si el juego existe (`sJ`).
* **Q:** Actualización de variables de registro e iterador (`i++`, `JR += uPC`). Retorna a **O**.

### Salidas
* **R:** Retorno exitoso del total de juegos registrados (`return juegosRegistrados`).
* **Z (FIN):** Punto de terminación de todos los flujos.

---

## 3. Análisis de Complejidad Ciclomática (McCabe)

La nota contenida en el diagrama incluye el cálculo formal de la complejidad.

**Parámetros extraídos del grafo:**
* **Nodos (n):** 19
* **Aristas/Caminos (a):** 25
* **Nodos de Decisión (P):** 7 (Identificados en los rombos C, F, H, I, M, O, S)

**Fórmulas aplicadas:**
1. Basada en aristas y nodos:
   $$V(G) = a - n + 2 \rightarrow 25 - 19 + 2 = 8$$
2. Basada en nodos predicado/decisión:
   $$V(G) = P + 1 \rightarrow 7 + 1 = 8$$

**Conclusión:** El algoritmo tiene una complejidad ciclomática de **8**. Se requieren un mínimo de 8 casos de prueba para garantizar una cobertura de ramas del 100%.

---

## 4. Caminos Básicos Independientes

Basado en la complejidad $V(G) = 8$, los trayectos completos desde el nodo **A** (INICIO) hasta el nodo **Z** (FIN) son los siguientes:

| Camino | Secuencia de Nodos (Diagrama) | Condición Evaluada | Resultado del Flujo |
| :--- | :--- | :--- | :--- |
| **C1** | A → B → C → D → `return -1` → Z | Longitudes asimétricas. | Lanza excepción/error de tamaño. |
| **C2** | A → B → C → F → D → `return -1` → Z | Arreglos vacíos (`length = 0`). | Lanza excepción/error de vacío. |
| **C3** | A → B → C → F → G → H → I → J → `return -2` → Z | `uPC < 0` (Valor negativo). | Rechaza lote por valores anómalos. |
| **C4** | A → B → C → F → G → H → M → Ñ → `return -3` → Z | `oSA + cT > mS` (Exceso). | Rechaza por sobrepasar límite. |
| **C5** | A → B → C → F → G → H → M → N → O → R → Z | Condición directa al final de bucles. | Retorna `0` (Ningún registro alterado). |
| **C6** | A → B → C → F → G → **H → I → L → H** → M → N → O → R → Z | Acumulación exitosa (`cT += upc`). | Iteración del primer bucle completa. |
| **C7** | A → B → C → F → G → H → M → N → **O → P → Q → N** → O → R → Z | Actualización (`S` = Falso). | Juego existente se actualiza. |
| **C8** | A → B → C → F → G → H → M → N → **O → S → Q → N** → O → R → Z | Nuevo registro (`S` = Verdadero). | Inserción de juego nuevo. |

---

## 5. Tabla de Pruebas Asociadas (Basado en el Diagrama)

| Caso | Entrada (Mock) | Variables Afectadas | Retorno / Salida |
| :--- | :--- | :--- | :--- |
| **CP_01** | `cod = ["A"], uds = [1, 2]` | Ninguna. Falla en nodo C. | `-1` |
| **CP_02** | `cod = [], uds = []` | Ninguna. Falla en nodo F. | `-1` |
| **CP_03** | `cod = ["A"], uds = [-5]` | `cantidadtotal = 0`. Falla en I. | `-2` |
| **CP_04** | `cod = ["A"], uds = [999]` | `cT = 999`. Falla en M (Límite ej: 100). | `-3` |
| **CP_05** | `cod = ["A"], uds = [0]` | `cT = 0`. Pasa M, no altera inventario. | `0` |
| **CP_06** | `cod = ["A"], uds = [5]` | `cT` incrementa. Verifica lógica de suma L. | `5` |
| **CP_07** | `cod = ["EXIS"], uds = [2]` | Entra a nodo P. Actualiza inventario. | `2` |
| **CP_08** | `cod = ["NUEVO"], uds= [2]` | Entra a nodo S. Añade al inventario. | `2` |