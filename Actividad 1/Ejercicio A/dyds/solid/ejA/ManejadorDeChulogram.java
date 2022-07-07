package dyds.solid.ejA;

public class ManejadorDeChulogram implements ManejadorRedes {

    protected static ManejadorDeChulogram instance;
    protected int cantidadContenidosEnviados = 0;


    protected ManejadorDeChulogram() {

    }

    public static ManejadorDeChulogram getInstance() {
        if (instance == null) {
            instance = new ManejadorDeChulogram();
        }
        return instance;
    }

    public void postearContenido(Contenido contenido) {
        //Codigo dummy para hacer los tests
        cantidadContenidosEnviados++;
    }

    public int getCantidadContenidosEnviados() {
        return cantidadContenidosEnviados;
    }

    public void resetSingleton() {
        //Por ser un singleton tenemos que limpiarlo para el testing
        cantidadContenidosEnviados = 0;
    }
}
