package dyds.solid.ejB;

import java.util.ArrayList;
import java.util.Random;

public class CreadorDeGiftcard {
    private Giftcard nuevaGiftcard;
    private int ultimoCodigo = 1000;

    public Giftcard crear(Usuario usuario, ArrayList<Giftcard> listaGiftcardsOtorgadas){
        ultimoCodigo++;
        int codigoGiftcard = (new Random().nextInt(9000) + 1000)*10000 + ultimoCodigo;
        Giftcard nuevaGiftcard = new Giftcard(codigoGiftcard, usuario);
        listaGiftcardsOtorgadas.add(nuevaGiftcard);
        return nuevaGiftcard;
    }
}
