package org.ed05_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestorJuegosTestNegro {
    private GestorJuegos juegoteca ;

    @BeforeEach
    public void setUp() {
        juegoteca = new GestorJuegos();
        // Escenario base: Se registra un juego con 10 unidades para probar las ventas.
        juegoteca.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{10});
    }

    /**
     * CP_V_01: Venta exitosa.
     * Escenario: El código existe, tiene el formato correcto y hay stock suficiente.
     * Resultado esperado: Retorna la cantidad vendida (5).
     */
    @Test
    public void testVentaExitosa_CP_V_01() {
        assertEquals(5, juegoteca.venderJuego("ABC123", 5));
    }

    /**
     * CP_NV_01: Código con formato inválido.
     * Escenario: El código "AB12" es demasiado corto o no sigue el patrón requerido.
     * Resultado esperado: El sistema captura la excepción y retorna 0.
     */
    @Test
    public void testCodigoInvalido_CP_NV_01() {
        assertEquals(0, juegoteca.venderJuego("AB12", 1));
    }

    /**
     * CP_NV_02: Juego inexistente.
     * Escenario: El código tiene formato válido ("NON999") pero no está en el catálogo.
     * Resultado esperado: Retorna -1 (Código de error para juego no encontrado).
     */
    @Test
    public void testJuegoNoExiste_CP_NV_02() {
        assertEquals(-1, juegoteca.venderJuego("NON999", 1));
    }

    /**
     * CP_NV_03: Stock insuficiente.
     * Escenario: Se intentan vender 20 unidades de "ABC123", pero solo hay 10 disponibles.
     * Resultado esperado: Retorna -2 (Código de error para falta de existencias).
     */
    @Test
    public void testStockInsuficiente_CP_NV_03() {
        assertEquals(-2, juegoteca.venderJuego("ABC123", 20));
    }

    /**
     * CP_NV_04: Cantidad de venta negativa.
     * Escenario: Se intenta realizar una venta de -5 unidades (valor lógicamente imposible).
     * Resultado esperado: El sistema captura el error de validación y retorna 0.
     */
    @Test
    public void testCantidadNegativa_CP_NV_04() {
        assertEquals(0, juegoteca.venderJuego("ABC123", -5));
    }
}

