package polizas.FormasPago;

/**
 * Registro de la tabla formapago.
 */
public class FormaPago {
    private int idFormaPago;
    private String nombre;
    private int numeroPagos;

    public FormaPago(int idFormaPago, String nombre, int numeroPagos) {
        this.idFormaPago = idFormaPago;
        this.nombre = nombre;
        this.numeroPagos = numeroPagos;
    }

    public int getIdFormaPago() { return idFormaPago; }
    public void setIdFormaPago(int idFormaPago) { this.idFormaPago = idFormaPago; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNumeroPagos() { return numeroPagos; }
    public void setNumeroPagos(int numeroPagos) { this.numeroPagos = numeroPagos; }

    @Override
    public String toString() {
        return this.nombre + " (" + this.numeroPagos + (this.numeroPagos == 1 ? " pago)" : " pagos)"); // Se usa en los JComboBox
    }
}
