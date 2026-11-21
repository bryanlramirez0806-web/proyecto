package polizas.Cancelaciones;

public class Cancelacion {
    protected int idCancelacion;
    protected int idPoliza;
    protected int idUsuario;
    protected String fechaSolicitud;
    protected String motivo;
    protected double montoDevolucion;
    protected String estatusDevolucion;

    public Cancelacion(int idCancelacion, int idPoliza, int idUsuario, String fechaSolicitud, String motivo, double montoDevolucion, String estatusDevolucion) {
        this.idCancelacion = idCancelacion; this.idPoliza = idPoliza; this.idUsuario = idUsuario;
        this.fechaSolicitud = fechaSolicitud; this.motivo = motivo; this.montoDevolucion = montoDevolucion;
        this.estatusDevolucion = estatusDevolucion;
    }

    public int getIdCancelacion() { return idCancelacion; }
    public int getIdPoliza() { return idPoliza; }
    public int getIdUsuario() { return idUsuario; }
    public String getFechaSolicitud() { return fechaSolicitud; }
    public String getMotivo() { return motivo; }
    public double getMontoDevolucion() { return montoDevolucion; }
    public String getEstatusDevolucion() { return estatusDevolucion; }

    @Override public String toString() { return "Cancelación #" + idCancelacion; }
}