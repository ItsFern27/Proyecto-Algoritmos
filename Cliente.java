public class Cliente {
    private String codigo;
    private boolean preferencial;

    public Cliente(String codigo, boolean preferencial) {
        this.codigo = codigo;
        this.preferencial = preferencial;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isPreferencial() {
        return preferencial;
    }
}