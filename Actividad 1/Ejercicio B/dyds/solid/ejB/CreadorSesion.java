package dyds.solid.ejB;

public class CreadorSesion {
    private ComponerMensajeAviso aviso;

    public CreadorSesion(){
        aviso = new ComponerMensajeAviso();
    }

    public SesionQuepasApp crear(Usuario usuario, Giftcard nuevaGiftcard) {
        SesionQuepasApp sesionQuepasApp = null;
        try {
            sesionQuepasApp = SesionQuepasApp.getInstance();
            String contactoUsuario = usuario.getContacto();
            String mensaje = aviso.crearMensaje(nuevaGiftcard);
            sesionQuepasApp.enviarMensaje(new MensajeQuepasApp().to(contactoUsuario).withBody(mensaje));
        } finally {
            if (sesionQuepasApp != null)
                sesionQuepasApp.cerrar();
        }
        return sesionQuepasApp;
    }
}

