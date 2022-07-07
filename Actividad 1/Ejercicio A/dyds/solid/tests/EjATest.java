package dyds.solid.tests;

import dyds.solid.ejA.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class EjATest {
    private ProcesadorDeContenidosNuevos procesador;

    @Before
    public void setUp() throws Exception {
        ManejadorDeChulogram.getInstance().resetSingleton();
        ArrayList<ManejadorRedes> lista = new ArrayList<>();
        lista.add(ManejadorDeChulogram.getInstance());
        lista.add(ManejadorDeTikitaka.getInstance());
        procesador = new ProcesadorDeContenidosNuevos(lista);
    }

    @Test
    public void testBase() {
        assertEquals(ManejadorDeChulogram.getInstance().getCantidadContenidosEnviados(), 0);
    }

    @Test
    public void testContenidoInvalidoNulo() {
        procesador.procesar(new Contenido(null));
        assertEquals(ManejadorDeChulogram.getInstance().getCantidadContenidosEnviados(), 0);
    }

    @Test
    public void testContenidoInvalidoVacio() {
        procesador.procesar(new Contenido(""));
        assertEquals(ManejadorDeChulogram.getInstance().getCantidadContenidosEnviados(), 0);
    }

    @Test
    public void test1ContenidoReposteado() {
        procesador.procesar(new Contenido("Emoji"));
        assertEquals(ManejadorDeChulogram.getInstance().getCantidadContenidosEnviados(), 1);
    }

    @Test
    public void test1ContenidoGuardado() {
        procesador.procesar(new Contenido("Emoji"));
        assertEquals(procesador.getRepo().getRepoSizeForTesting(), 1);
    }

    @Test
    public void test2ContenidosReposteados() {
        procesador.procesar(new Contenido("Emoji1"));
        procesador.procesar(new Contenido("Emoji2"));
        assertEquals(ManejadorDeChulogram.getInstance().getCantidadContenidosEnviados(), 2);
    }

    @Test
    public void test2ContenidosGuardados() {
        procesador.procesar(new Contenido("Emoji1"));
        procesador.procesar(new Contenido("Emoji2"));
        assertEquals(procesador.getRepo().getRepoSizeForTesting(), 2);
    }
}
