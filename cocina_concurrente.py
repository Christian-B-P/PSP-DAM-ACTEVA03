import threading
import random
import time
import os

PLATOS_DISPONIBLES = [
    "Paella", "Tortilla Española", "Gazpacho", "Croquetas", "Pulpo a la gallega",
    "Churros", "Fabada", "Callos", "Empanada gallega", "Cochinillo",
    "Salmorejo", "Pisto", "Albóndigas", "Calamares", "Pollo al ajillo",
    "Arroz negro", "Bacalao al pil-pil", "Cordero asado", "Huevos rotos", "Ensalada campera"
]

class Pedido:
    def __init__(self, id, nombre_plato):
        self.id = id
        self.nombre_plato = nombre_plato

    def __str__(self):
        return f"Pedido #{self.id}: {self.nombre_plato}"

class CuentaPedidos:
    def __init__(self, pedidos):
        self.pedidos = pedidos
        self.lock = threading.Lock()
        self.log_file = "log_pedidos_python.txt"

        if os.path.exists(self.log_file):
            os.remove(self.log_file)

    def tomar_pedido(self):
        with self.lock:
            if self.pedidos:
                return self.pedidos.pop(0)
            return None

    def registrar_preparacion(self, cocinero, pedido):
        registro = f"{cocinero} preparó {pedido}"
        print(registro)
        with self.lock:
            with open(self.log_file, "a") as f:
                f.write(registro + "\n")

class Cocinero(threading.Thread):
    def __init__(self, nombre, cuenta):
        super().__init__()
        self.nombre = nombre
        self.cuenta = cuenta

    def run(self):
        while True:
            pedido = self.cuenta.tomar_pedido()
            if not pedido:
                break
            time.sleep(random.uniform(0.2, 0.6))
            self.cuenta.registrar_preparacion(self.nombre, pedido)

def main():
    seleccion = random.sample(PLATOS_DISPONIBLES, 6)
    pedidos = [Pedido(i + 1, plato) for i, plato in enumerate(seleccion)]
    cuenta = CuentaPedidos(pedidos)

    cocineros = [
        Cocinero("Cocinero 1", cuenta),
        Cocinero("Cocinero 2", cuenta),
        Cocinero("Cocinero 3", cuenta)
    ]

    for cocinero in cocineros:
        cocinero.start()

    for cocinero in cocineros:
        cocinero.join()

    print("\nTodos los pedidos han sido procesados.")
    print(f"Log guardado en: {cuenta.log_file}")

if __name__ == "__main__":
    main()