package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import controladores.gestor.ControlInicioGestor;
import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.*;
import vistas.cliente.VentanaInicioCliente;
import vistas.empleado.VentanaInicioEmpleado;
import vistas.noRegistrado.VentanaLoginRegistro;

/**
 * Controla la pantalla unificada de login + registro.
 * Crea la vista, inyecta ambos controladores en sus botones respectivos
 * y la muestra como vista actual.
 */
public class ControlLoginRegistro {

    private final Tienda            tienda;
    private final VentanaLoginRegistro vista;

    public ControlLoginRegistro(Tienda tienda) {
        this.tienda = tienda;
        this.vista  = new VentanaLoginRegistro();

        // Inyectar listener de login
        vista.setControladorLogin(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                intentarLogin();
            }
        });

        // Inyectar listener de registro
        vista.setControladorRegistro(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                intentarRegistro();
            }
        });

        TiendaFrame.getInstance().setVistaActual(vista);
    }

    // ──────────────────────────────────────────────────────────────────────
    private void intentarLogin() {
        String nombre = vista.getLoginUsuario();
        String pass   = new String(vista.getLoginPassword());

        try {
            Usuario usuario = tienda.iniciarSesion(nombre, pass);
            redirigirSegunUsuario(usuario);
        } catch (CustomException ex) {
            new VentanaMensaje(ex.getMessage());
        }
    }

    private void intentarRegistro() {
        String nombre = vista.getRegUsuario();
        String pass   = new String(vista.getRegPassword());
        String conf   = new String(vista.getRegConfirmacion());

        try {
            Usuario usuario = tienda.registrarse(nombre, pass, conf);
            redirigirSegunUsuario(usuario);
        } catch (CustomException ex) {
            new VentanaMensaje(ex.getMessage());
        }
    }

    private void redirigirSegunUsuario(Usuario usuario) {
        if (usuario instanceof Gestor) {
            SwingUtilities.invokeLater(() -> new ControlInicioGestor(tienda));
        } else if (usuario instanceof Empleado) {
            new vistas.empleado.VentanaInicioEmpleado(tienda);
        } else if (usuario instanceof ClienteRegistrado) {
            new vistas.cliente.VentanaInicioCliente(tienda);
        }
    }
}
