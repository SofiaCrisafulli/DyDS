package dyds.solid.tests;

import static org.junit.Assert.*;

import dyds.solid.ejB.*;
import org.junit.Before;
import org.junit.Test;

public class EjBtest {
	@Before
	public void setUp() throws Exception {
		SesionQuepasApp.getInstance().resetSingleton();
	}

	@Test
	public void testSinMsgs() {
		Compra compra = new Compra(100000, new Usuario(true));
		PremiosPorCompras premiosPorCompras = new PremiosPorCompras();

		assertEquals(SesionQuepasApp.getInstance().getQueue().size(), 0);
	}

	@Test
	public void testUnMsgEnviado() {
		Compra compra = new Compra(100000, new Usuario(true));
		PremiosPorCompras premiosPorCompras = new PremiosPorCompras();
		premiosPorCompras.controlarCompra(compra);

		assertEquals(SesionQuepasApp.getInstance().getQueue().size(), 1);
	}

	@Test
	public void testMsgEnviadoDestinoCorrecto() {
		Compra compra = new Compra(100000, new Usuario(true));
		PremiosPorCompras premiosPorCompras = new PremiosPorCompras();
		premiosPorCompras.controlarCompra(compra);

		MensajeQuepasApp expectedMensajeQuepasApp = SesionQuepasApp.getInstance().getQueue().get(0);

		assertEquals(expectedMensajeQuepasApp.getDestino(),"dummy@quepasapp");
	}

	@Test
	public void testMsgEnviadoCodigoGiftcardCorrecto() {
		Compra compra = new Compra(100000, new Usuario(true));
		PremiosPorCompras premiosPorCompras = new PremiosPorCompras();
		premiosPorCompras.controlarCompra(compra);

		MensajeQuepasApp expectedMensajeQuepasApp = SesionQuepasApp.getInstance().getQueue().get(0);

		assertTrue(expectedMensajeQuepasApp.getMensaje().contains("1001"));
	}

	@Test
	public void testDosComprasImportantesCodigoGiftcardCorrecto() {
		Compra compra1 = new Compra(100000, new Usuario(true));
		Compra compra2 = new Compra(150000, new Usuario(true));
		PremiosPorCompras premiosPorCompras = new PremiosPorCompras();
		premiosPorCompras.controlarCompra(compra1);
		premiosPorCompras.controlarCompra(compra2);

		MensajeQuepasApp expectedMensajeQuepasApp = SesionQuepasApp.getInstance().getQueue().get(1);

		assertTrue(expectedMensajeQuepasApp.getMensaje().contains("1002"));
	}

	@Test
	public void testTresComprasUnaNoImportanteCodigoGiftcardCorrecto() {
		Compra compra1 = new Compra(100000, new Usuario(true));
		Compra compra2 = new Compra(150, new Usuario(true));
		Compra compra3 = new Compra(150000, new Usuario(true));
		PremiosPorCompras premiosPorCompras = new PremiosPorCompras();
		premiosPorCompras.controlarCompra(compra1);
		premiosPorCompras.controlarCompra(compra2);
		premiosPorCompras.controlarCompra(compra3);

		MensajeQuepasApp expectedMensajeQuepasApp = SesionQuepasApp.getInstance().getQueue().get(1);

		assertTrue(expectedMensajeQuepasApp.getMensaje().contains("1002"));
	}
}
