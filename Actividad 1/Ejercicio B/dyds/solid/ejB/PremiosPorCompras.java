package dyds.solid.ejB;

import java.util.ArrayList;

public class PremiosPorCompras {

    private ArrayList<Giftcard> listaGiftcardsOtorgadas;
    private CreadorDeGiftcard giftcard;
    private CreadorSesion sesion;
    private ComponerMensajeAviso aviso;

    public PremiosPorCompras() {
        listaGiftcardsOtorgadas = new ArrayList<>();
        giftcard = new CreadorDeGiftcard();
        sesion = new CreadorSesion();
        aviso = new ComponerMensajeAviso();
    }

    public void controlarCompra(Compra compra) {
        Usuario usuario = compra.darComprador();
        if (compra.importe() > 50000 && usuario.isGold()) {
            Giftcard nuevaGiftcard = giftcard.crear(usuario, listaGiftcardsOtorgadas);
            SesionQuepasApp nuevaSesion = sesion.crear(usuario, nuevaGiftcard);
            String nuevoAviso = aviso.crearMensaje(nuevaGiftcard);
        }
    }
}



