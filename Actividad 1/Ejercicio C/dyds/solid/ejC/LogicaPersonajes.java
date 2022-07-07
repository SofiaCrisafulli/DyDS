package dyds.solid.ejC;

import java.util.ArrayList;
import java.util.List;

public class LogicaPersonajes {

    private List<Personaje> personajes = new ArrayList<Personaje>();

    public List<Personaje> getFiltro(Filtro filtro){
        return filtro.filtrar(personajes);
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }
}
