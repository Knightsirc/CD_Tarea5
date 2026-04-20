package org.ed05_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestorJuegosTestBlanco {
    private GestorJuegos juegoteca;

    @BeforeEach
    public void setUp() {
        // Inicializa una nueva instancia antes de cada test para asegurar aislamiento
        juegoteca = new GestorJuegos();
    }

    /**
     * CP1: Error por inconsistencia de parámetros.
     * Los arrays de códigos y unidades deben tener la misma longitud.
     */
    @Test
    public void cp1() {
        String[] codigos = {"ABC123", "XYZ456"};
        int[] unidades = {10};
        // Se espera -1 cuando las longitudes no coinciden
        assertEquals(-1, juegoteca.registrarLoteJuegos(codigos, unidades));
    }

    /**
     * CP2: Error por entrada vacía.
     * No se puede registrar un lote si no se proporcionan datos.
     */
    @Test
    public void cp2() {
        String[] codigos = {};
        int[] unidades = {};
        // Se espera -1 para arrays vacíos
        assertEquals(-1, juegoteca.registrarLoteJuegos(codigos, unidades));
    }

    /**
     * CP3: Error por valores negativos.
     * El sistema no debe permitir la entrada de cantidades menores a cero.
     */
    @Test
    public void cp3() {
        String[] codigos = {"ABC123"};
        int[] unidades = {-5};
        // Se espera -2 para unidades negativas
        assertEquals(-2, juegoteca.registrarLoteJuegos(codigos, unidades));
    }

    /**
     * CP4: Error por exceso de capacidad.
     * Verifica que no se supere el límite total de stock (aprox. 200 unidades).
     */
    @Test
    public void cp4() {
        // Llenamos el stock casi al límite
        juegoteca.registrarLoteJuegos(new String[]{"OLD001"}, new int[]{195});
        String[] codigos = {"NEW002"};
        int[] unidades = {10};
        // 195 + 10 = 205 (Excede el límite), se espera -3
        assertEquals(-3, juegoteca.registrarLoteJuegos(codigos, unidades));
    }

    /**
     * CP5: Operación con cantidad cero.
     * Verifica que registrar 0 unidades sea tratado como una operación válida pero neutra.
     */
    @Test
    public void cp5() {
        // 1. Estado inicial: 10 unidades
        juegoteca.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{10});

        // 2. Intento de registro de 0 unidades: debe retornar 0
        String[] codigos = {"ABC123"};
        int[] unidades = {0};
        assertEquals(0, juegoteca.registrarLoteJuegos(codigos, unidades));

        // 3. Verificación de integridad: si añadimos 5 más, el retorno debe ser 5
        // Esto confirma que el paso anterior no corrompió el contador
        assertEquals(5, juegoteca.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{5}));
    }

    /**
     * CP6: Caso de éxito - Registro simple.
     * Registro de un juego nuevo en un sistema vacío.
     */
    @Test
    public void cp6() {
        String[] codigos = {"ABC123"};
        int[] unidades = {10};
        // Debe retornar las unidades registradas con éxito
        assertEquals(10, juegoteca.registrarLoteJuegos(codigos, unidades));
    }

    /**
     * CP7: Caso de éxito - Actualización.
     * Registro de unidades adicionales para un código que ya existe.
     */
    @Test
    public void cp7() {
        juegoteca.registrarLoteJuegos(new String[]{"ABC123"}, new int[]{10});
        String[] codigos = {"ABC123"};
        int[] unidades = {5};
        // Se espera que confirme el registro de las 5 nuevas unidades
        assertEquals(5, juegoteca.registrarLoteJuegos(codigos, unidades));
    }

    /**
     * CP8: Caso de éxito - Registro múltiple.
     * Registro de un lote que contiene juegos nuevos y existentes simultáneamente.
     */
    @Test
    public void cp8() {
        juegoteca.registrarLoteJuegos(new String[]{"AAA111"}, new int[]{10});
        String[] codigos = {"AAA111", "BBB222"};
        int[] unidades = {5, 5};
        // Debe retornar la suma total de las unidades del lote (5+5=10)
        assertEquals(10, juegoteca.registrarLoteJuegos(codigos, unidades));
    }
}
