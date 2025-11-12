package Yava;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cocina {
    public static void main(String[] args) {
        Object lock = new Object();
        List<String> listaPlatos = List.of(
            "Paella", "Tortilla Española", "Gazpacho", "Croquetas", "Pulpo a la gallega",
            "Churros", "Fabada", "Callos", "Empanada gallega", "Cochinillo",
            "Salmorejo", "Pisto", "Albóndigas", "Calamares", "Pollo al ajillo",
            "Arroz negro", "Bacalao al pil-pil", "Cordero asado", "Huevos rotos", "Ensalada campera"
        );

        List<String> seleccion = new ArrayList<>(listaPlatos);
        Collections.shuffle(seleccion);
        List<Pedido> pedidos = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pedidos.add(new Pedido(i + 1, seleccion.get(i)));
        }

        Cocinero cocinero1 = new Cocinero("Cocinero 1", pedidos, lock);
        Cocinero cocinero2 = new Cocinero("Cocinero 2", pedidos, lock);
        Cocinero cocinero3 = new Cocinero("Cocinero 3", pedidos, lock);

        cocinero1.start();
        cocinero2.start();
        cocinero3.start();

        try {
            cocinero1.join();
            cocinero2.join();
            cocinero3.join();
        } catch (InterruptedException e) {
            System.err.println("Error al esperar cocineros.");
        }

        System.out.println("\nTodos los pedidos han sido procesados.");
    }
}