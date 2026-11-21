package polizas.Pagos;

public class Pago {
    protected int idPago;
    protected int idPoliza;
    protected int numeroParcialidad;
    protected double monto;
    protected String fechaLimite;
    protected String fechaPago;
    protected String metodoPago;
    protected String comprobante;
    protected String estatus;

    public Pago(int idPago, int idPoliza, int numeroParcialidad, double monto, String fechaLimite, String fechaPago, String metodoPago, String comprobante, String estatus) {
        this.idPago = idPago; this.idPoliza = idPoliza; this.numeroParcialidad = numeroParcialidad;
        this.monto = monto; this.fechaLimite = fechaLimite; this.fechaPago = fechaPago;
        this.metodoPago = metodoPago; this.comprobante = comprobante; this.estatus = estatus;
    }

    public int getIdPago() { return idPago; }
    public int getIdPoliza() { return idPoliza; }
    public int getNumeroParcialidad() { return numeroParcialidad; }
    public double getMonto() { return monto; }
    public String getFechaLimite() { return fechaLimite; }
    public String getFechaPago() { return fechaPago; }
    public String getMetodoPago() { return metodoPago; }
    public String getComprobante() { return comprobante; }
    public String getEstatus() { return estatus; }

    @Override public String toString() { return "Pago #" + idPago + " - Monto: " + monto; }
}