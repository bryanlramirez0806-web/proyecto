package polizas.Clientes;

public class Cliente {
    protected int idCliente;
    protected String nombre;
    protected String telefono;
    protected String correo;
    protected String rfc;
    protected String direccion;
    protected String medioContacto;
    protected boolean activo;

    public Cliente(int idCliente, String nombre, String telefono, String correo, String rfc, String direccion, String medioContacto, boolean activo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
        if (this.nombre != null && !this.nombre.trim().isEmpty()) {
            return this.nombre;
        }
        return "Cliente #" + this.idCliente;
    }
}