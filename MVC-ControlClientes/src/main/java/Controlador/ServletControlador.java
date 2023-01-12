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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(req, resp);
                    break;
                case "modificar":
                    this.modificar(req, resp);
                    break;
//                default:
//                    this.accionDefault(req, resp);
//                    break;
            }
        } else {
            this.accionDefault(req, resp);
        }
    }

    private void modificar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String saldoString = req.getParameter("saldo");
        double saldo = 0;
        if (saldoString != null && !saldoString.equals("")) {
            saldo = Double.parseDouble(saldoString);
        }
        int idCliente = Integer.parseInt(req.getParameter("idCliente"));
        System.out.println(req.getParameter("idCliente"));
        Cliente nuevo = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        int registrosModificados = new ClienteDAO().actualizar(nuevo);
        System.out.println("registrosModificados = " + registrosModificados);

        if (registrosModificados != 1) {
            req.getRequestDispatcher("cliente.jsp");
        } else {
            this.accionDefault(req, resp);
        }

    }

    private void editarCliente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //recuperamos el idCliente
        int idCliente = Integer.parseInt(req.getParameter("idCliente"));
        Cliente id = new Cliente(idCliente);
        Cliente cliente = new ClienteDAO().encontrar(id);//no sé si esto devuelve el cliente que esta buscando
        cliente.setIdCliente(idCliente);

        req.setAttribute("cliente", cliente);
        String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";
        req.getRequestDispatcher(jspEditar).forward(req, resp);

    }

    private void accionDefault(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDAO().listar();
        HttpSession sesion = req.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));
        //req.getRequestDispatcher("clientes.jsp").forward(req, resp);
        resp.sendRedirect("clientes.jsp"); //redirigir notificando al navegador (no se reenvian los formularios
    }

    private void insertarCliente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String saldoString = req.getParameter("saldo");
        double saldo = 0;
        if (saldoString != null && !saldoString.equals("")) {
            saldo = Double.parseDouble(saldoString);
        }

        Cliente nuevo = new Cliente(nombre, apellido, email, telefono, saldo);

        int registrosModificados = new ClienteDAO().insertar(nuevo);
        System.out.println("registrosModificados = " + registrosModificados);

        this.accionDefault(req, resp);

    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double total = 0;
        for (Cliente cliente : clientes) {
            total += cliente.getSaldo();
        }
        return total;
    }

}
