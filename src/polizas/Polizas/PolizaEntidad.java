package polizas.Polizas;

/**
 * Póliza de seguro. Se llama PolizaEntidad (y no Poliza) porque el paquete
 * ya se llama "Polizas" y así no se confunden al leer el código.
 */
public class PolizaEntidad {
    private int idPoliza;
    private String numeroPoliza;
    private String fechaInicio;
    private String fechaFin;
    private double primaTotal;
    private String estado;
    private int idVehiculo;
    private int idAseguradora;
    private int idFormaPago;
    private Integer idPolizaAnterior;

    // Solo para mostrar en la tabla (vienen de un JOIN, no se guardan en poliza)
    private String nombreCliente = "";
    private String placas = "";
    private String nombreAseguradora = "";
    private String nombreFormaPago = "";

    public PolizaEntidad(int idPoliza, String numeroPoliza, String fechaInicio, String fechaFin, double primaTotal,
                         String estado, int idVehiculo, int idAseguradora, int idFormaPago, Integer idPolizaAnterior) {
        this.idPoliza = idPoliza;
        this.numeroPoliza = numeroPoliza;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.primaTotal = primaTotal;
        this.estado = estado;
        this.idVehiculo = idVehiculo;
        this.idAseguradora = idAseguradora;
        this.idFormaPago = idFormaPago;
        this.idPolizaAnterior = idPolizaAnterior;
    }

    public int getIdPoliza() { return idPoliza; }
    public void setIdPoliza(int idPoliza) { this.idPoliza = idPoliza; }

    public String getNumeroPoliza() { return numeroPoliza; }
    public void setNumeroPoliza(String numeroPoliza) { this.numeroPoliza = numeroPoliza; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public double getPrimaTotal() { return primaTotal; }
    public void setPrimaTotal(double primaTotal) { this.primaTotal = primaTotal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdAseguradora() { return idAseguradora; }
    public void setIdAseguradora(int idAseguradora) { this.idAseguradora = idAseguradora; }

    public int getIdFormaPago() { return idFormaPago; }
    public void setIdFormaPago(int idFormaPago) { this.idFormaPago = idFormaPago; }

    public Integer getIdPolizaAnterior() { return idPolizaAnterior; }
    public void setIdPolizaAnterior(Integer idPolizaAnterior) { this.idPolizaAnterior = idPolizaAnterior; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getPlacas() { return placas; }
    public void setPlacas(String placas) { this.placas = placas; }

    public String getNombreAseguradora() { return nombreAseguradora; }
    public void setNombreAseguradora(String nombreAseguradora) { this.nombreAseguradora = nombreAseguradora; }

    public String getNombreFormaPago() { return nombreFormaPago; }
    public void setNombreFormaPago(String nombreFormaPago) { this.nombreFormaPago = nombreFormaPago; }

    @Override
    public String toString() {
        String num = (numeroPoliza == null || numeroPoliza.trim().isEmpty()) ? "En trámite" : numeroPoliza;
        return "Póliza N°: " + num + " (#" + idPoliza + ")";
    }
}
