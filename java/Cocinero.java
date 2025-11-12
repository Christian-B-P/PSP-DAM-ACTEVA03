package Yava;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Cocinero extends Thread {
    private final String nombre;
    private final List<Pedido> pedidos;
    private final Object lock;

    public Cocinero(String nombre, List<Pedido> pedidos, Object lock) {
        this.nombre = nombre;
        this.pedidos = pedidos;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido = null;

            synchronized (lock) {
                if (!pedidos.isEmpty()) {
                    pedido = pedidos.remove(0);
                } else {
                    break;
                }
            }

            if (pedido != null) {
                prepararPedido(pedido);
            }
        }
    }

    private void prepararPedido(Pedido pedido) {
        try {
            Thread.sleep((int)(Math.random() * 500) + 200); 
        } catch (InterruptedException e) {
            System.err.println(nombre + " fue interrumpido.");
        }

        String registro = nombre + " preparó " + pedido;

        System.out.println(registro);

        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("log_pedidos.txt", true))) {
                writer.write(registro);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error al escribir en log_pedidos.txt: " + e.getMessage());
            }
        }
    }
}