package polizas.Documentos;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class DocumentoTM extends AbstractTableModel {
    protected List<Documento> lista = new ArrayList<>();
    protected String[] encabezados = {"ID", "Cliente", "Tipo", "F. Recepción", "Ruta Archivo"};

    @Override public String getColumnName(int c) { return encabezados[c]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    @Override public Object getValueAt(int r, int c) {
        Documento d = lista.get(r);
        switch(c) {
            case 0: return d.getIdDocumento(); case 1: return d.getIdCliente(); case 2: return d.getTipo();
            case 3: return d.getFechaRecepcion(); case 4: return d.getRutaArchivo(); default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<Documento> l) { this.lista = l; }
}