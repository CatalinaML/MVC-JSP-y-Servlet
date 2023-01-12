
package Controlador;

import Datos.ClienteDAO;
import Modelo.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.accionDefault(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if(accion != null){
            switch(accion){
                case "insertar":
                    this.insertarCliente(req, resp);
                    break;
                    default:
                        this.accionDefault(req, resp);
                        break;
            }
        }else{
            this.accionDefault(req, resp);
        }
    }
    
    private void accionDefault(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Cliente> clientes = new ClienteDAO().listar();
        HttpSession sesion = req.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        //req.getRequestDispatcher("clientes.jsp").forward(req, resp);
        resp.sendRedirect("clientes.jsp"); //redirigir notificando al navegador (no se reenvian los formularios
    }
    
    private void insertarCliente (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String saldoString = req.getParameter("saldo");
        double saldo = 0;
        if(saldoString != null && !saldoString.equals("")){
            saldo = Double.parseDouble(saldoString);
        }
        
        Cliente nuevo = new Cliente(nombre, apellido, email, telefono, saldo);
        
        int registrosModificados = new ClienteDAO().insertar(nuevo);
        System.out.println("registrosModificados = " + registrosModificados);
        
        this.accionDefault(req, resp);
        
    }
    
    private double calcularSaldoTotal(List<Cliente> clientes){
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.getSaldo();
        }
        return total;
    }
    
    
}
