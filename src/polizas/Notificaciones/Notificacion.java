package polizas.Notificaciones;

public class Notificacion {
    protected int idNotificacion;
    protected int idPoliza;
    protected int idUsuario;
    protected String tipo;
    protected String fecha;
    protected String medio;
    protected double costoRenovacion;
    protected boolean realizada;

    public Notificacion(int idNotificacion, int idPoliza, int idUsuario, String tipo, String fecha, String medio, double costoRenovacion, boolean realizada) {
        this.idNotificacion = idNotificacion; this.idPoliza = idPoliza; this.idUsuario = idUsuario;
        this.tipo = tipo; this.fecha = fecha; this.medio = medio; this.costoRenovacion = costoRenovacion; this.realizada = realizada;
    }

    public int getIdNotificacion() { return idNotificacion; }
    public int getIdPoliza() { return idPoliza; }
    public int getIdUsuario() { return idUsuario; }
    public String getTipo() { return tipo; }
    public String getFecha() { return fecha; }
    public String getMedio() { return medio; }
    public double getCostoRenovacion() { return costoRenovacion; }
    public boolean isRealizada() { return realizada; }

    @Override public String toString() { return "Notificación #" + idNotificacion; }
}