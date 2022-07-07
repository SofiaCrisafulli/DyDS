package dyds.solid.ejB;

public class MensajeQuepasApp {
	
	private String destino;
	private String mensaje;

	public MensajeQuepasApp to(String auditor) {
		this.destino = auditor;
		return this;
	}

	public MensajeQuepasApp withBody(String mensaje) {
		this.mensaje = mensaje;
		return this;
	}

	public String getDestino() {
		return destino;
	}

	public String getMensaje() {
		return mensaje;
	} 
}
