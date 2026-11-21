package polizas.TiposPoliza;

public class TipoPoliza {
    protected int idTipo;
    protected String nombre;
    protected String descripcion;
    protected boolean activo;

    public TipoPoliza(int idTipo, String nombre, String descripcion, boolean activo) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return this.nombre; // Para mostrarlo en JComboBox
    }
}