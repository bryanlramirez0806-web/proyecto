package polizas.Coberturas;

/**
 * Registro de la tabla cobertura.
 */
public class Cobertura {
    private int idCobertura;
    private String nombre;
    private String descripcion;

    public Cobertura(int idCobertura, String nombre, String descripcion) {
        this.idCobertura = idCobertura;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdCobertura() { return idCobertura; }
    public void setIdCobertura(int idCobertura) { this.idCobertura = idCobertura; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return this.nombre; // Se usa en los JComboBox
    }
}
