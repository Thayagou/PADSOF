package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.general.pantallas.VentanaCuentaCliente;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de gestión de cuenta del cliente, permite cambiar contraseña y cerrar sesión.
 */
public class ControlManejoCuenta implements ControladorPantalla {

	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo vista. Ventana de gestión de cuenta asociada a este controlador. */
	private VentanaCuentaCliente vista;
	
	/** Campo cliente. Cliente registrado que gestiona su cuenta. */
	private ClienteRegistrado cliente;
	
	/**
	 * Instancia un nuevo Objeto ControlManejoCuenta.
	 * Inicializa la vista y configura la navegación hacia esta pantalla.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que gestiona su cuenta.
	 */
	public ControlManejoCuenta(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		this.vista = new VentanaCuentaCliente();
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}

	/**
	 * actionPerformed.
	 * Gestiona los eventos de cambio de contraseña y cierre de sesión.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaCuentaCliente.CHANGE_BTN:
			try{
				cliente.cambiarContrasena(vista.getContrasenaAntigua(), vista.getContrasenaNueva(), vista.getConfirmacionNueva());
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
			break;
		case VentanaCuentaCliente.LOGOUT_BTN:
			if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que quieres cerrar sesión? Podrás volver a iniciar sesión más tarde."))
				SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
			break;
		}
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de gestión de cuenta.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene la explicacion de la ventana.
	 *
	 * @return valor de Explicacion, descripción de las acciones disponibles.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí puedes cerrar sesión o cambiar tu contraseña.";
	}
}