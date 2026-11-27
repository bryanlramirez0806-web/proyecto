package polizas.Clientes;

import polizas.General.Persona;

/**
 * Cliente del despacho. Hereda de Persona el nombre y los apellidos.
 */
public class Cliente extends Persona {
    private int idCliente;
    private String tipoPersona;
    private String rfc;
    private String curp;
    private String telefono;
    private String correo;
    private String fechaRegistro;
    private boolean activo;

    public Cliente(int idCliente, String tipoPersona, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String rfc, String curp, String telefono, String correo, String fechaRegistro, boolean activo) {
        super(nombre, apellidoPaterno, apellidoMaterno);
        this.idCliente = idCliente;
        this.tipoPersona = tipoPersona;
        this.rfc = rfc;
        this.curp = curp;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(String tipoPersona) { this.tipoPersona = tipoPersona; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return getNombreCompleto(); // Muestra el nombre completo en los JComboBox
    }
}
