package dyds.solid.ejA;

import java.util.ArrayList;

public class ProcesadorDeContenidosNuevos {
    protected ArrayList<ManejadorRedes> lista;
    protected Repositorio repo;

    public ProcesadorDeContenidosNuevos(ArrayList<ManejadorRedes> l) {
        lista = l;
        repo = new RepositorioDummy();
    }

    public void procesar(Contenido contenidoNuevo) {
        if (contenidoNuevo.validar() && repo.grabar(contenidoNuevo))
            for (int i = 0; i < lista.size(); i++)
                lista.get(i).postearContenido(contenidoNuevo);
    }

    public Repositorio getRepo() {
        return repo;
    }
}
