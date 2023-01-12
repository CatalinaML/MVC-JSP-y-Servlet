
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
        List<Cliente> clientes = new ClienteDAO().listar();
        
        req.setAttribute("clientes", clientes);
        req.setAttribute("totalClientes", clientes.size());
        req.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        req.getRequestDispatcher("clientes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
    
    private double calcularSaldoTotal(List<Cliente> clientes){
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.getSaldo();
        }
        return total;
    }
    
    
}
