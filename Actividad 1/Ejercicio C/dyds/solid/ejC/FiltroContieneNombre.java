package dyds.solid.ejC;

public class FiltroContieneNombre extends Filtro {

    private String nombrePersonaje;

    public FiltroContieneNombre(String nombre) {
        nombrePersonaje = nombre;
    }

    public boolean compararConFiltro(Personaje p) {
        return p.getNombre().contains(nombrePersonaje);
    }

    public String getNombre(){
        return nombrePersonaje;
    }
}
