package Datos;

import java.util.List;

public interface IAccesoDatos<E> {
    
    List<E> listar();
    E encontrar(E cliente);
    int insertar(E cliente);
    int actualizar(E cliente);
    int eliminar(E cliente);
}
