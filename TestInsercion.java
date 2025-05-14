public class TestInsercion {
    public static void main(String[] args) {
        GestionCola gestor = new GestionCola();

        // Insertar 5 normales
        gestor.insertarCliente(new Cliente("A001", false));
        gestor.insertarCliente(new Cliente("A002", false));
        gestor.insertarCliente(new Cliente("A003", false));
        gestor.insertarCliente(new Cliente("A004", false));
        gestor.insertarCliente(new Cliente("A005", false));

        // Insertar 1 preferencial
        gestor.insertarCliente(new Cliente("B001", true));

        // Mostrar cola
        gestor.mostrarCola();
    }
}
