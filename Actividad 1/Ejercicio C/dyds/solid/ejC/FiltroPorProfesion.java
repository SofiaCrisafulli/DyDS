package dyds.solid.ejC;

public class FiltroPorProfesion extends Filtro {

    private Profesion profesion;

    public FiltroPorProfesion(Profesion p) {
        profesion = p;
    }

    public boolean compararConFiltro(Personaje p) {
        return p.getRubro() == profesion;
    }

    public Profesion getProfesion(){
        return profesion;
    }
}
