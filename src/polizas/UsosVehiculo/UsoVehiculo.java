package polizas.UsosVehiculo;

/**
 * Registro de la tabla usovehiculo.
 */
public class UsoVehiculo {
    private int idUso;
    private String nombre;

    public UsoVehiculo(int idUso, String nombre) {
        this.idUso = idUso;
        this.nombre = nombre;
    }

    public int getIdUso() { return idUso; }
    public void setIdUso(int idUso) { this.idUso = idUso; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return this.nombre; // Se usa en los JComboBox
    }
}
