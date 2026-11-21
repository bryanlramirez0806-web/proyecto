package polizas.General;

import java.util.List;

public interface DAO {

    // Método original de Agregar
    int create(Object obj);

    // Método original de Consultar
    List read();

    // Método original de Actualizar
    int update(Object obj);

    // Método original de Eliminar
    int delete(Object obj);
}