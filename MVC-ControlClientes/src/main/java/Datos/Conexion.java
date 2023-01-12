package Datos;

import java.sql.*;
import javax.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_clientes?useSSL=false&useTimezone=true&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";
    
    private static BasicDataSource dataSouerce;
    
    public static DataSource getDataSource(){
        if(dataSouerce == null){
            dataSouerce = new BasicDataSource();
            dataSouerce.setUrl(JDBC_URL);
            dataSouerce.setUsername(JDBC_USER);
            dataSouerce.setPassword(JDBC_PASS);
            dataSouerce.setInitialSize(50);
        }
        return dataSouerce;
    }
    
    public static Connection getConnection() throws SQLException{
        return getDataSource().getConnection();
    }
    
    public static void close(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException ex) {
           ex.printStackTrace(System.out);
        }
    }
    
    public static void close(PreparedStatement stmt){
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
