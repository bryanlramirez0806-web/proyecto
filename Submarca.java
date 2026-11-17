package polizas.Submarcas;

public class Submarca {
    protected int idSubmarca;
    protected int idMarca;
    protected String nombreSubmarca;
    protected boolean activo;

    public Submarca(int idSubmarca, int idMarca, String nombreSubmarca, boolean activo) {
        this.idSubmarca = idSubmarca;
        this.idMarca = idMarca;
        this.nombreSubmarca = nombreSubmarca;
        this.activo = activo;
    }

    public int getIdSubmarca() { return idSubmarca; }
    public void setIdSubmarca(int idSubmarca) { this.idSubmarca = idSubmarca; }

    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }

    public String getNombreSubmarca() { return nombreSubmarca; }
    public void setNombreSubmarca(String nombreSubmarca) { this.nombreSubmarca = nombreSubmarca; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return this.nombreSubmarca;
    }
}