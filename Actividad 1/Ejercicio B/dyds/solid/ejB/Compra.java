package dyds.solid.ejB;

public class Compra {
	
	private int importe;
	private Usuario comprador;

	public Compra(int importe, Usuario comprador){
		this.importe = importe;
		this.comprador = comprador;
	}
	
	public int importe() {
		return importe;
	}

	public Usuario darComprador() {
		return comprador;
	}
}
