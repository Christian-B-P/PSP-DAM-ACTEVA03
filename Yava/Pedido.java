package Yava;
public class Pedido {
    private final int id;
    private final String nombrePlato;

    public Pedido(int id, String nombrePlato) {
        this.id = id;
        this.nombrePlato = nombrePlato;
    }

    public int getId() {
        return id;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    @Override
    public String toString() {
        return "Pedido #" + id + ": " + nombrePlato;
    }
}