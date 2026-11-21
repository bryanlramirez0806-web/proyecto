package polizas.Vehiculos;

public class Vehiculo {
    private int idVehiculo;
    private int idCliente;
    private int idSubmarca;
    private String modelo;
    private String tipoVehiculo;
    private String numeroSerie;
    private String placas;
    private boolean activo;

    // Solo para mostrar en la tabla (vienen de un JOIN, no se guardan en vehiculo)
    private String nombreCliente = "";
    private String nombreSubmarca = "";

    public Vehiculo(int idVehiculo, int idCliente, int idSubmarca, String modelo, String tipoVehiculo, String numeroSerie, String placas, boolean activo) {
        this.idVehiculo = idVehiculo;
        this.idCliente = idCliente;
        this.idSubmarca = idSubmarca;
        this.modelo = modelo;
        this.tipoVehiculo = tipoVehiculo;
        this.numeroSerie = numeroSerie;
        this.placas = placas;
        this.activo = activo;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdSubmarca() { return idSubmarca; }
    public void setIdSubmarca(int idSubmarca) { this.idSubmarca = idSubmarca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getPlacas() { return placas; }
    public void setPlacas(String placas) { this.placas = placas; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreSubmarca() { return nombreSubmarca; }
    public void setNombreSubmarca(String nombreSubmarca) { this.nombreSubmarca = nombreSubmarca; }

    @Override
    public String toString() {
        // Se usa en el JComboBox de Pólizas: "UTV-123-A - Nissan Versa 2024"
        return this.placas + " - " + this.nombreSubmarca + " " + this.modelo;
    }
}
