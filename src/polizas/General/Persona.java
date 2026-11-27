package polizas.General;

/**
 * Clase abstracta con el nombre y los apellidos que comparten Cliente y
 * Usuario en el diagrama. No se puede crear un objeto Persona directamente;
 * solo sirve para que otras clases hereden sus datos.
 */
public abstract class Persona {
    protected String nombre;
    protected String apellidoPaterno;
    protected String apellidoMaterno;

    public Persona(String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    // Junta nombre y apellidos, saltando los que estén vacíos
    public String getNombreCompleto() {
        String completo = nombre;
        if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) completo += " " + apellidoPaterno;
        if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) completo += " " + apellidoMaterno;
        return completo;
    }

    // Cada clase hija decide cómo se muestra (por ejemplo en un JComboBox)
    @Override
    public abstract String toString();
}
