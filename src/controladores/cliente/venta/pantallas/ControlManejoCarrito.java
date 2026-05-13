package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlItemCarrito;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.pantallas.VentanaCarrito;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana del carrito de compras del cliente.
 */
public class ControlManejoCarrito implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo vista. Ventana del carrito asociada a este controlador. */
	private VentanaCarrito vista;
	
	/** Campo cliente. Cliente registrado propietario del carrito. */
	private ClienteRegistrado cliente;

	/**
	 * Instancia un nuevo Objeto ControlManejoCarrito.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado propietario del carrito.
	 */
	public ControlManejoCarrito(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;

		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * recargarPantalla.
	 * Recarga la pantalla completa del carrito para reflejar cambios en los ítems.
	 */
	public void recargarPantalla() {
		TiendaFrame.getInstance().recargarPantallaActual(this);
	}
	
	/**
	 * actionPerformed.
	 * Gestiona las acciones de pagar o cancelar el carrito.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaCarrito.PAY_ACTION:
			SwingUtilities.invokeLater(() -> new ControlVentanaPago(tienda, cliente));
			break;
		case VentanaCarrito.CANCEL_ACTION:
			if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de querer cancelar la compra? Se eliminarán todos los productos del carrito.")) {
				try{
					tienda.cancelarCarritoDe(cliente);
					recargarPantalla();
					new VentanaMensaje("Su carrito ha sido cancelado");
				} catch(Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		}
	}
	
	/**
	 * mostrar.
	 * Actualiza la vista con los ítems actuales del carrito al mostrarse.
	 */
	@Override
	public void mostrar() {
		double precio = cliente.getCarrito().calcularCarrito();
		
		this.vista = new VentanaCarrito(precio);
		
		for(StockExterno st : cliente.getCarrito().getItems()) {
			new ControlItemCarrito(tienda, cliente, st, vista, this);
		}
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().refresh();
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana del carrito.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, descripción de la funcionalidad de la ventana.
	 */
	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran los productos de tu carrito. Puedes quitarlos, finalizar la compra o cancelarla.";
	}
}