package dyds.solid.ejB;

public class Usuario {
    private String contacto;
    private boolean gold;

    public Usuario(boolean gold) {
        this.gold = gold;
        contacto = "dummy@quepasapp";
    }

    public Usuario() {
        contacto = "dummy@quepasapp";
    }


    public String getContacto() {
        return contacto;
    }

    public boolean isGold() {
        return gold;
    }
}
