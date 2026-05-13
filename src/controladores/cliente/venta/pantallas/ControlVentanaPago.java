package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.venta.pantallas.VentanaPago;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de pago del carrito de compras.
 */
public class ControlVentanaPago implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo vista. Ventana de pago asociada a este controlador. */
	private VentanaPago vista;
	
	/** Campo cliente. Cliente registrado que realiza el pago. */
	private ClienteRegistrado cliente;
	
	/**
	 * Instancia un nuevo Objeto ControlVentanaPago.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que realiza el pago.
	 */
	public ControlVentanaPago(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		vista = new VentanaPago();
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}

	/**
	 * actionPerformed.
	 * Gestiona el pago del carrito con el número de tarjeta introducido.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(VentanaPago.PAY_ACTION)) {
			try{
				if(tienda.pagarCarritoDe(cliente, vista.getNumeroTarjeta()) == true) {
					new VentanaMensaje("El carrito se ha pagado. Puedes ver tu código de pedido en las notificaciones.");
					SwingUtilities.invokeLater(() -> new ControlVerCompras(tienda, cliente));
				} else {
					new VentanaMensaje("El número de tarjeta introducido es erróneo. Introduce un número de 16 dígitos.", VentanaMensaje.ERROR);
				}
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
		}
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de pago.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}
	
	/**
	 * puedeVolver.
	 * Indica si se puede volver a la pantalla anterior desde esta ventana.
	 *
	 * @return true si la operación fue correcta, falso en caso contrario
	 */
	@Override
	public boolean puedeVolver() {return false;}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, descripción de la funcionalidad de la ventana.
	 */
	@Override
	public String getExplicacion() {
		return "Introduce los datos para realizar el pago.";
	}
}