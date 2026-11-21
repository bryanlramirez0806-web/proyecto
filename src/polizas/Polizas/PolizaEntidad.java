package polizas.Polizas;

/**
 * Póliza de seguro. Se llama PolizaEntidad (y no Poliza) porque el paquete
 * ya se llama "Polizas" y así no se confunden al leer el código.
 */
public class PolizaEntidad {
    private int idPoliza;
    private String numeroPoliza;
    private int idCotizacion;
    private int idVehiculo;
    private int idCompania;
    private int idUsuario;
    private int idTipo;
    private Integer idPolizaAnterior;
    private String fechaEmision;
    private String fechaInicio;
    private String fechaVencimiento;
    private double primaTotal;
    private String estatus;

    // Solo para mostrar en la tabla (vienen de un JOIN, no se guardan en poliza)
    private String nombreCliente = "";
    private String placas = "";
    private String nombreCompania = "";
    private String nombreTipo = "";

    public PolizaEntidad(int idPoliza, String numeroPoliza, int idCotizacion, int idVehiculo, int idCompania, int idUsuario, int idTipo, Integer idPolizaAnterior, String fechaEmision, String fechaInicio, String fechaVencimiento, double primaTotal, String estatus) {
        this.idPoliza = idPoliza;
        this.numeroPoliza = numeroPoliza;
        this.idCotizacion = idCotizacion;
        this.idVehiculo = idVehiculo;
        this.idCompania = idCompania;
        this.idUsuario = idUsuario;
        this.idTipo = idTipo;
        this.idPolizaAnterior = idPolizaAnterior;
        this.fechaEmision = fechaEmision;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.primaTotal = primaTotal;
        this.estatus = estatus;
    }

    public int getIdPoliza() { return idPoliza; }
    public void setIdPoliza(int idPoliza) { this.idPoliza = idPoliza; }

    public String getNumeroPoliza() { return numeroPoliza; }
    public void setNumeroPoliza(String numeroPoliza) { this.numeroPoliza = numeroPoliza; }

    public int getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(int idCotizacion) { this.idCotizacion = idCotizacion; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdCompania() { return idCompania; }
    public void setIdCompania(int idCompania) { this.idCompania = idCompania; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public Integer getIdPolizaAnterior() { return idPolizaAnterior; }
    public void setIdPolizaAnterior(Integer idPolizaAnterior) { this.idPolizaAnterior = idPolizaAnterior; }

    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double getPrimaTotal() { return primaTotal; }
    public void setPrimaTotal(double primaTotal) { this.primaTotal = primaTotal; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getPlacas() { return placas; }
    public void setPlacas(String placas) { this.placas = placas; }

    public String getNombreCompania() { return nombreCompania; }
    public void setNombreCompania(String nombreCompania) { this.nombreCompania = nombreCompania; }

    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }

    @Override
    public String toString() {
        String num = (this.numeroPoliza == null || this.numeroPoliza.trim().isEmpty())
                ? "En trámite"
                : this.numeroPoliza;
        return "Póliza N°: " + num + " (#" + this.idPoliza + ")";
    }
}
