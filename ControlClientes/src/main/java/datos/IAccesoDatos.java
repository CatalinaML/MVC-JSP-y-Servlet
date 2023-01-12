package datos;

import java.util.List;

public interface IAccesoDatos<E> {
    
    List<E> listar();
    E buscar(E buscar);
    int insertar(E insertar);
    int actualizar(E actualizar);
    int eliminar(E eliminar);
}
