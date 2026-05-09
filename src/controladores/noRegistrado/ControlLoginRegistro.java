package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.general.pantallas.ControlInicioCliente;
import controladores.empleado.general.ControlInicioEmpleado;
import controladores.gestor.ControlInicioGestor;
import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.usuario.*;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.noRegistrado.VentanaLoginRegistro;

/**
 * Controla la pantalla unificada de login + registro.
 * Crea la vista, inyecta ambos controladores en sus botones respectivos
 * y la muestra como vista actual.
 */
public class ControlLoginRegistro implements ActionListener, ControladorPantalla {

    private final Tienda            tienda;
    private final VentanaLoginRegistro vista;

    public ControlLoginRegistro(Tienda tienda) {
        this.tienda = tienda;
        this.vista  = new VentanaLoginRegistro();

        vista.setControlador(this);

        TiendaFrame.getInstance().navegarA(this);
    }

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
        if (usuario instanceof Gestor gestor) {
            SwingUtilities.invokeLater(() -> new ControlInicioGestor(tienda, gestor));
        } else if (usuario instanceof Empleado empleado) {
        	SwingUtilities.invokeLater(() -> new ControlInicioEmpleado(tienda, empleado));
        } else if (usuario instanceof ClienteRegistrado cliente) {
            SwingUtilities.invokeLater(() -> new ControlInicioCliente(tienda, cliente));
        }
    }

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaLoginRegistro.LOGIN_ACTION:
			intentarLogin();
			break;
		case VentanaLoginRegistro.SIGNIN_ACTION:
			intentarRegistro();
			break;
		}
	}

	@Override
	public String getExplicacion() {
		return "Esta ventana sirve para introducir los datos para iniciar sesión o registrarse.";
	}
}
