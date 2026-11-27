package polizas.Modelos;

/**
 * Registro de la tabla modelo.
 */
public class Modelo {
    private int idModelo;
    private String nombre;
    private int idMarca;

    // Solo para mostrar en la tabla (viene de un JOIN)
    private String nombreMarca = "";

    public Modelo(int idModelo, String nombre, int idMarca) {
        this.idModelo = idModelo;
        this.nombre = nombre;
        this.idMarca = idMarca;
    }

    public int getIdModelo() { return idModelo; }
    public void setIdModelo(int idModelo) { this.idModelo = idModelo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }

    public String getNombreMarca() { return nombreMarca; }
    public void setNombreMarca(String nombreMarca) { this.nombreMarca = nombreMarca; }

    @Override
    public String toString() {
        return this.nombreMarca + " " + this.nombre; // Se usa en los JComboBox
    }
}
