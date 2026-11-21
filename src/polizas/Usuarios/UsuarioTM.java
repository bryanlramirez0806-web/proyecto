package polizas.Usuarios;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class UsuarioTM extends AbstractTableModel {
    protected List<Usuario> usuarioLista;
    protected String[] encabezados = {"ID Usuario", "Nombre", "Teléfono", "Correo", "Rol", "Activo"};

    public UsuarioTM() {
        usuarioLista = new ArrayList<>();
        this.setColumnNames(encabezados);
    }

    @Override
    public String getColumnName(int cantidad) {
        return encabezados[cantidad];
    }

    public void setColumnNames(String[] nuevosNombres) {
        if (nuevosNombres.length == encabezados.length) {
            this.encabezados = nuevosNombres;
            fireTableStructureChanged();
        } else {
            throw new IllegalArgumentException("Valide los campos");
        }
    }

    public void vaciarLista() {
        usuarioLista.clear();
    }

    @Override
    public int getRowCount() {
        return usuarioLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1) {
            Usuario u = usuarioLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return u.getIdUsuario();
                case 1: return u.getNombre();
                case 2: return u.getTelefono();
                case 3: return u.getCorreo();
                case 4: return u.getRol();
                case 5: return u.isActivo();
                default: return null;
            }
        }
        return null;
    }

    public void addUsuario(Usuario u) {
        usuarioLista.add(u);
        fireTableRowsInserted(usuarioLista.size() - 1, usuarioLista.size() - 1);
    }

    public void updateUsuario(int fila, Usuario nueva) {
        usuarioLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public Usuario getUsuario(int fila) {
        return usuarioLista.get(fila);
    }

    public void removeUsuario(int fila) {
        usuarioLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<Usuario> lista) {
        this.usuarioLista = lista;
    }
}