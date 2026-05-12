package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.general.pantallas.ControlInicioCliente;
import controladores.empleado.general.ControlInicioEmpleado;
import controladores.gestor.general.ControlInicioGestor;
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
	/** Modelo de la tienda sobre el que se actúa */
    private final Tienda tienda;
    /** Ventana que se muestra */
    private final VentanaLoginRegistro vista;

    /**
     * Cosntructor del controlador de la ventana loginRegistro
     * @param tienda Modelo de la tienda
     */
    public ControlLoginRegistro(Tienda tienda) {
        this.tienda = tienda;
        this.vista  = new VentanaLoginRegistro();

        vista.setControlador(this);

        TiendaFrame.getInstance().navegarA(this);
    }

    /**
     * Acción que se ejecuta al intentar iniciar sesión
     */
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

    /**
     * Acción que se ejecuta al intentar registrarse
     */
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

    /**
     * Redirige al usuario a un menú en función de su tipo
     * @param usuario Usuario que se redirige
     */
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
