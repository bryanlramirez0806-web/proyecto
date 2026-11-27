package polizas.Aseguradoras;

/**
 * Registro de la tabla aseguradora.
 */
public class Aseguradora {
    private int idAseguradora;
    private String nombre;
    private String rfc;
    private String telefono;
    private String correo;
    private String nombreContacto;

    public Aseguradora(int idAseguradora, String nombre, String rfc, String telefono, String correo, String nombreContacto) {
        this.idAseguradora = idAseguradora;
        this.nombre = nombre;
        this.rfc = rfc;
        this.telefono = telefono;
        this.correo = correo;
        this.nombreContacto = nombreContacto;
    }

    public int getIdAseguradora() { return idAseguradora; }
    public void setIdAseguradora(int idAseguradora) { this.idAseguradora = idAseguradora; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }

    @Override
    public String toString() {
        return this.nombre; // Se usa en los JComboBox
    }
}
