package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IAccesoDatos<Cliente> {

    //Sentencias
    private static final String SQL_SELECT = "SELECT * FROM cliente";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM cliente WHERE idCliente = ?";
    private static final String SQL_INSERT = "INSERT INTO cliente (nombre, apellido, email, telefono, saldo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE cliente SET nombre=?, apellido=?, emmail=?, telefono=?, saldo=? WHERE idCliente = ?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE idCliente = ?";

    @Override
    public List<Cliente> listar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDouble("saldo"));

                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return clientes;
    }

    @Override
    public Cliente buscar(Cliente buscar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            //definir parámetros
            stmt.setInt(1, buscar.getIdCliente());
            rs = stmt.executeQuery();
            rs.absolute(1); //si existe lo que buscamos se posiciona en el primer registro

            buscar.setIdCliente(rs.getInt("idCliente"));
            buscar.setNombre(rs.getString("nombre"));
            buscar.setApellido(rs.getString("apellido"));
            buscar.setEmail(rs.getString("email"));
            buscar.setTelefono(rs.getString("telefono"));
            buscar.setSaldo(rs.getDouble("saldo"));

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return buscar;
    }

    @Override
    public int insertar(Cliente insertar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosModificados = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            //definir parámetros
            stmt.setString(1, insertar.getNombre());
            stmt.setString(2, insertar.getApellido());
            stmt.setString(3, insertar.getEmail());
            stmt.setString(4, insertar.getTelefono());
            stmt.setDouble(5, insertar.getSaldo());

            registrosModificados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosModificados;
    }

    @Override
    public int actualizar(Cliente actualizar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosModificados = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            //definir parámetros
            stmt.setString(1, actualizar.getNombre());
            stmt.setString(2, actualizar.getApellido());
            stmt.setString(3, actualizar.getEmail());
            stmt.setString(4, actualizar.getTelefono());
            stmt.setDouble(5, actualizar.getSaldo());
            stmt.setInt(6, actualizar.getIdCliente());

            registrosModificados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosModificados;
    }

    @Override
    public int eliminar(Cliente eliminar) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registrosModificados = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, eliminar.getIdCliente());
            registrosModificados = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return registrosModificados;
    }
}
