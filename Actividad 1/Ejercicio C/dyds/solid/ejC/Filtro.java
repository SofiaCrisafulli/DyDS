package dyds.solid.ejC;

import java.util.ArrayList;
import java.util.List;

public abstract class Filtro {

    public List<Personaje> filtrar(List<Personaje> listaBase) {
        List<Personaje> filteredList = new ArrayList<Personaje>();
        for (Personaje personaje : listaBase) {
            if (compararConFiltro(personaje)) {
                filteredList.add(personaje);
            }
        }
        return filteredList;
    }

    public abstract boolean compararConFiltro(Personaje p);
}
