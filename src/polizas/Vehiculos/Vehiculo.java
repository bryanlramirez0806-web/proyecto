package polizas.Vehiculos;

public class Vehiculo {
    private int idVehiculo;
    private int anio;
    private String numSerie;
    private String placas;
    private String color;
    private int idCliente;
    private int idModelo;
    private int idUso;
    private boolean activo;

    // Solo para mostrar en la tabla (vienen de un JOIN, no se guardan en vehiculo)
    private String nombreCliente = "";
    private String nombreModelo = "";
    private String nombreUso = "";

    public Vehiculo(int idVehiculo, int anio, String numSerie, String placas, String color,
                    int idCliente, int idModelo, int idUso, boolean activo) {
        this.idVehiculo = idVehiculo;
        this.anio = anio;
        this.numSerie = numSerie;
        this.placas = placas;
        this.color = color;
        this.idCliente = idCliente;
        this.idModelo = idModelo;
        this.idUso = idUso;
        this.activo = activo;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getNumSerie() { return numSerie; }
    public void setNumSerie(String numSerie) { this.numSerie = numSerie; }

    public String getPlacas() { return placas; }
    public void setPlacas(String placas) { this.placas = placas; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdModelo() { return idModelo; }
    public void setIdModelo(int idModelo) { this.idModelo = idModelo; }

    public int getIdUso() { return idUso; }
    public void setIdUso(int idUso) { this.idUso = idUso; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreModelo() { return nombreModelo; }
    public void setNombreModelo(String nombreModelo) { this.nombreModelo = nombreModelo; }

    public String getNombreUso() { return nombreUso; }
    public void setNombreUso(String nombreUso) { this.nombreUso = nombreUso; }

    @Override
    public String toString() {
        // Se usa en el JComboBox de Pólizas: "UTV-123-A - Nissan Versa 2024"
        return this.placas + " - " + this.nombreModelo + " " + this.anio;
    }
}
