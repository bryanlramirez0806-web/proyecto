package polizas.General;

/**
 * Clase abstracta con los datos de contacto que comparten Cliente y Usuario
 * (diagrama de clases de la Fase 2). No se puede crear un objeto Persona
 * directamente; solo sirve para que las otras clases hereden sus datos.
 */
public abstract class Persona {
    protected String nombre;
    protected String telefono;
    protected String correo;

    public Persona(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    // Cada clase hija decide cómo se muestra (por ejemplo en un JComboBox)
    @Override
    public abstract String toString();
}
