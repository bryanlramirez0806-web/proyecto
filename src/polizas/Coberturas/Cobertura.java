package polizas.Coberturas;

public class Cobertura {
    protected int idCobertura;
    protected String nombre;
    protected String descripcion;
    protected boolean activo;

    public Cobertura(int idCobertura, String nombre, String descripcion, boolean activo) {
        this.idCobertura = idCobertura;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdCobertura() { return idCobertura; }
    public void setIdCobertura(int idCobertura) { this.idCobertura = idCobertura; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() { return this.nombre; }
}