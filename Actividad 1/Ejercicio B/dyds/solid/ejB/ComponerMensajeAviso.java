package dyds.solid.ejB;

public class ComponerMensajeAviso {
    public String crearMensaje(Giftcard giftcard) {
        return "Gracias! por tu compra ganaste una Giftcard! reclamala con: " + giftcard.darCodigo();
    }
}

