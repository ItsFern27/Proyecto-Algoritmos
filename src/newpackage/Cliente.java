package newpackage;

/**
 * Representa a un cliente del banco con atributos como ID, nombre, DNI, edad y si es preferencial.
 * El cliente se considera preferencial si tiene 65 años o más.
 */
public class Cliente {
    private static int contador = 1; // Contador estático para generar IDs únicos
    private String id;
    private String nombre;
    private int dni;
    private int edad;
    private boolean preferencial;

    // Constructor que genera automáticamente los datos del cliente
    public Cliente() {
        this.dni = generarDni();
        this.edad = generarEdad();
        this.nombre = "Cliente_" + dni;
        this.preferencial = edad >= 65;
        this.id = generarId();
    }

    // Genera un número de DNI aleatorio de 8 dígitos
    private int generarDni() {
        return 10000000 + (int)(Math.random() * 90000000);
    }

    // Genera una edad aleatoria entre 18 y 87
    private int generarEdad() {
        return 18 + (int)(Math.random() * 70);
    }

    // Genera el ID del cliente en el formato A001, A002... hasta Z999
    private String generarId() {
        int serie = (contador - 1) / 999;
        int numero = (contador - 1) % 999 + 1;
        contador++;
        char letra = (char) ('A' + serie);
        return letra + String.format("%03d", numero);
    }

    // Métodos de acceso (getters y setters)
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public int getDni() { return dni; }
    public boolean isPreferencial() { return preferencial; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setDni(int dni) { this.dni = dni; }
    public void setPreferencial(boolean preferencial) { this.preferencial = preferencial; }

    // Representación del cliente en forma de texto
    @Override
    public String toString() {
        return id + " (" + edad + " años)" + (preferencial ? " [P]" : "");
    }
}
