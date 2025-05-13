package newpackage;

public class Cliente {
    private String id;
    private String nombre;
    private int dni;
    private boolean preferencial;

    // Constructor que inicializa un Cliente con el DNI y crea automáticamente sus demás atributos
    public Cliente(int dni) {
        this.dni = dni;
        this.id = generarId(dni);
        this.nombre = generarNombre(dni);
        this.preferencial = determinarPreferencial(dni);
    }

    // Constructor vacío que crea automáticamente los datos del cliente
    public Cliente() {
        this.dni = generarDni();
        this.id = generarId(this.dni);
        this.nombre = generarNombre(this.dni);
        this.preferencial = determinarPreferencial(this.dni);
    }

    // Métodos para generar automáticamente los atributos (implementación de ejemplo)
    private String generarId(int dni) {
        return "C" + dni; // Ejemplo de generación de ID
    }

    private String generarNombre(int dni) {
        return "Cliente_" + dni; // Ejemplo de generación de nombre
    }

    private boolean determinarPreferencial(int dni) {
        return dni % 2 == 0; // Ejemplo: si el DNI es par, es preferencial
    }

    private int generarDni() {
        return (int) (Math.random() * 10000); // Genera un DNI aleatorio
    }
}
