package dyds.solid.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dyds.solid.ejC.*;

public class EjCTest {

	LogicaPersonajes logicaPersonajes = new LogicaPersonajes();

	@Before
	public void setUp() throws Exception {
		Personaje personajeA = new Personaje("Pungui", Profesion.Granuja, 500, 5);
		Personaje personajeB = new Personaje("Conano", Profesion.Campeon, 20, 50);
		Personaje personajeC = new Personaje("El Magias", Profesion.Mago, 200, 10);
		Personaje personajeD = new Personaje("El Raton", Profesion.Granuja, 750, 15);

		logicaPersonajes.getPersonajes().add(personajeA);
		logicaPersonajes.getPersonajes().add(personajeB);
		logicaPersonajes.getPersonajes().add(personajeC);
		logicaPersonajes.getPersonajes().add(personajeD);
	}

	@Test
	public void testProfesionesCant() {
		List<Personaje> clientesBB = logicaPersonajes.getFiltro(new FiltroPorProfesion(Profesion.Granuja));
		assertEquals(clientesBB.size(), 2);
	}

	@Test
	public void testProfPersonaje1() {
		List<Personaje> clientesBB = logicaPersonajes.getFiltro(new FiltroPorProfesion(Profesion.Granuja));
		assertEquals(clientesBB.get(0).getNombre(),"Pungui");
	}

	@Test
	public void testProfPersonaje2() {
		List<Personaje> clientesBB = logicaPersonajes.getFiltro(new FiltroPorProfesion(Profesion.Granuja));
		assertEquals(clientesBB.get(1).getNombre(),"El Raton");
	}

	@Test
	public void testContieneNombreCant() {
		List<Personaje> clientesB = logicaPersonajes.getFiltro(new FiltroContieneNombre("El"));
		assertEquals(clientesB.size(), 2);
	}

	@Test
	public void testContieneNombrePers1() {
		List<Personaje> clientesB = logicaPersonajes.getFiltro(new FiltroContieneNombre("Con"));

		assertEquals(clientesB.get(0).getNombre(),"Conano");
	}

	@Test
	public void testMasCarosQue() {
		List<Personaje> clientesSaldo = logicaPersonajes.getFiltro(new FiltroSuperaNivel(50));
		assertEquals(clientesSaldo.size(), 3);
	}
}
