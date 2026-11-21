package polizas.Documentos;

public class Documento {
    protected int idDocumento;
    protected int idCliente;
    protected String tipo;
    protected String fechaRecepcion;
    protected String rutaArchivo;

    public Documento(int idDocumento, int idCliente, String tipo, String fechaRecepcion, String rutaArchivo) {
        this.idDocumento = idDocumento; this.idCliente = idCliente; this.tipo = tipo;
        this.fechaRecepcion = fechaRecepcion; this.rutaArchivo = rutaArchivo;
    }

    public int getIdDocumento() { return idDocumento; }
    public int getIdCliente() { return idCliente; }
    public String getTipo() { return tipo; }
    public String getFechaRecepcion() { return fechaRecepcion; }
    public String getRutaArchivo() { return rutaArchivo; }

    @Override public String toString() { return "Doc #" + idDocumento + " - " + tipo; }
}