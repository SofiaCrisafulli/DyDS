package dyds.solid.ejC;

public class Personaje {
	private String nombre;
	private Profesion profesion;
	private int nivel;
	private int hp;

	public Personaje(String nombre, Profesion profesion, int nivel, int hp) {
		this.nombre = nombre;
		this.profesion = profesion;
		this.nivel = nivel;
		this.hp = hp;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getHp() { return hp; 	}
	public void setHp(int hp) { this.hp = hp; }
	public Profesion getRubro() { return profesion; }
	public void setRubro(Profesion profesion) { this.profesion = profesion; }

}
