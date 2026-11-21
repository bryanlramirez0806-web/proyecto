package polizas.Usuarios;

import polizas.General.Persona;

/**
 * Usuario del sistema (administrador, vendedor o personal de operaciones).
 * Hereda de Persona el nombre, teléfono y correo.
 */
public class Usuario extends Persona {
    private int idUsuario;
    private String rol;
    private boolean activo;

    public Usuario(int idUsuario, String nombre, String telefono, String correo, String rol, boolean activo) {
        super(nombre, telefono, correo);
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.activo = activo;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return this.nombre + " (" + this.rol + ")";
    }
}
