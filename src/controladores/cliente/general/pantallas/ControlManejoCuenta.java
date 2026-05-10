package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.general.pantallas.VentanaCuentaCliente;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Tipo: Class ControlManejoCuenta.
 */
public class ControlManejoCuenta implements ControladorPantalla {

	/** Campo tienda. */
	private Tienda tienda;
	
	/** Campo vista. */
	private VentanaCuentaCliente vista;
	
	/** Campo cliente. */
	private ClienteRegistrado cliente;
	
	/**
	 * Instancia un nuevo Objeto ControlManejoCuenta.
	 *
	 * @param tienda parámetro tienda
	 * @param cliente parámetro cliente
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
	 *
	 * @param e parámetro e
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
	 * @return valor de Vista
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene la explicacion de la ventana.
	 *
	 * @return valor de Explicacion
	 */
	@Override
	public String getExplicacion() {
		return "Aquí puedes cerrar sesión o cambiar tu contraseña.";
	}
}
