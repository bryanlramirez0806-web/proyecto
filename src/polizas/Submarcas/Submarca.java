package polizas.Submarcas;

public class Submarca {
    protected int idSubmarca;
    protected int idMarca;
    protected String nombre; // Actualizado de nombreSubmarca a nombre

    public Submarca(int idSubmarca, int idMarca, String nombre) {
        this.idSubmarca = idSubmarca;
        this.idMarca = idMarca;
        this.nombre = nombre;
    }

    public int getIdSubmarca() { return idSubmarca; }
    public void setIdSubmarca(int idSubmarca) { this.idSubmarca = idSubmarca; }

    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return this.nombre;
    }
}