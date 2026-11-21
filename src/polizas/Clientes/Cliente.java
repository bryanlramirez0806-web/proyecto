package polizas.Clientes;

import polizas.General.Persona;

/**
 * Cliente del despacho. Hereda de Persona el nombre, teléfono y correo.
 */
public class Cliente extends Persona {
    private int idCliente;
    private String rfc;
    private String direccion;
    private String medioContacto;
    private boolean activo;

    public Cliente(int idCliente, String nombre, String telefono, String correo, String rfc, String direccion, String medioContacto, boolean activo) {
        super(nombre, telefono, correo);
        this.idCliente = idCliente;
        this.rfc = rfc;
        this.direccion = direccion;
        this.medioContacto = medioContacto;
        this.activo = activo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMedioContacto() {
        return medioContacto;
    }

    public void setMedioContacto(String medioContacto) {
        this.medioContacto = medioContacto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return this.nombre; // Muestra el nombre del cliente en los JComboBox
    }
}
