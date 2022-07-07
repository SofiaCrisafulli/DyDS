package dyds.solid.ejC;

import java.util.ArrayList;
import java.util.List;

public class FiltroSuperaNivel extends Filtro{

    private int nivel;

    public FiltroSuperaNivel(int n){
        nivel = n;
    }

    public boolean compararConFiltro(Personaje p) {
        return p.getNivel() > nivel;
    }

    public int getNivel(){
        return nivel;
    }
}
