package polizas.Cotizaciones;

import java.util.Date;

public class Cotizacion {
    protected int idCotizacion;
    protected int idVehiculo;
    protected int idUsuario;
    protected int idCompania;
    protected int idTipo;
    protected String fecha; // Usaremos String o Date según prefieras el manejo plano de texto en la vista
    protected double costo;
    protected String opcionPago;
    protected String estatus;

    public Cotizacion(int idCotizacion, int idVehiculo, int idUsuario, int idCompania, int idTipo, String fecha, double costo, String opcionesPago, String estatus) {
        this.idCotizacion = idCotizacion;
        this.idVehiculo = idVehiculo;
        this.idUsuario = idUsuario;
        this.idCompania = idCompania;
        this.idTipo = idTipo;
        this.fecha = fecha;
        this.costo = costo;
        this.opcionPago = opcionesPago;
        this.estatus = estatus;
    }

    public int getIdCotizacion() { return idCotizacion; }
    public void setIdCotizacion(int idCotizacion) { this.idCotizacion = idCotizacion; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdCompania() { return idCompania; }
    public void setIdCompania(int idCompania) { this.idCompania = idCompania; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public String getOpcionesPago() { return opcionPago; }
    public void setOpcionesPago(String opcionesPago) { this.opcionPago = opcionesPago; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    @Override
    public String toString() {
        return "Cotización #" + this.idCotizacion + " - Costo: $" + this.costo;
    }
}