package polizas.Companias;

public class Compania {
    protected int idCompania;
    protected String nombre;
    protected String telefono;
    protected String correo;
    protected String sitioValidacion;
    protected boolean activo;

    public Compania(int idCompania, String nombre, String telefono, String correo, String sitioValidacion, boolean activo) {
        this.idCompania = idCompania;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.sitioValidacion = sitioValidacion;
        this.activo = activo;
    }

    public int getIdCompania() { return idCompania; }
    public void setIdCompania(int idCompania) { this.idCompania = idCompania; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getSitioValidacion() { return sitioValidacion; }
    public void setSitioValidacion(String sitioValidacion) { this.sitioValidacion = sitioValidacion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return this.nombre; // Permite seleccionarla fácilmente en un JComboBox
    }
}
