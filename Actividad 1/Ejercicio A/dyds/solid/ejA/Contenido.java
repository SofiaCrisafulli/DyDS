package dyds.solid.ejA;

public class Contenido {

	private String texto;

	public Contenido(String cantidad) {
		this.texto = cantidad;
	}

	public boolean validar() {
		if(texto != null && texto != "") return true;
		else return false;
	}
}
