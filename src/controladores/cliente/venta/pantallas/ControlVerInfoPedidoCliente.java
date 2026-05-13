package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelItemPedido;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.pantallas.VentanaInfoPedidoCliente;
import vistas.common.app.TiendaFrame;

/**
 * Controlador de la ventana de información detallada de un pedido del cliente.
 */
public class ControlVerInfoPedidoCliente implements ControladorPantalla {

	/** Campo vista. Ventana de información de pedido asociada a este controlador. */
	private VentanaInfoPedidoCliente vista;

	/**
	 * Instancia un nuevo Objeto ControlVerInfoPedidoCliente.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado propietario del pedido.
	 * @param pedido Pedido del que se muestra la información.
	 */
	public ControlVerInfoPedidoCliente(Tienda tienda, ClienteRegistrado cliente, Pedido pedido) {
		this.vista = new VentanaInfoPedidoCliente();

		for (StockExterno st : pedido.getItemsPedido()) {
			new ControlPanelItemPedido(tienda, cliente, st, vista);
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);
	}

	/**
	 * actionPerformed.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			/* Sin acciones para esta ventana */
		}
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de información del pedido.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, instrucciones para valorar productos del pedido.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí se muestra la información de un pedido que realizó usted. Puede valorar los productos que compró pinchando en \"Valorar\"";
	}
}