package controladores.empleado.gestionarPedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import controladores.TiendaFrame;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.venta.pedidos.EstadoPedido;
import modelo.venta.pedidos.Pedido;
import modelo.venta.productos.StockExterno;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarPedidos.PanelPedidoGestionarPedido;
import vistas.empleado.gestionarPedidos.VentanaGestPedidos;

/**
 * Esta clase representa el controlador del panel para gestionar pedidos
 */
public class ControlPanelGestionarPedido implements ActionListener {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Empleado que realiza la acción */
	private final Empleado empleado;
	/** Pedido que se gestiona */
	private final Pedido pedido;
	/** Nombre de la acción asociada a avanzar el estado del pedido */
	private final String ACTION_NAME = "Avanzar estado del pedido";
	
	/**
	 * Constructor del controlador del panel de gestionar pedidos
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 * @param pedido Pedido que se gestiona
	 * @param vista Ventana sobre la que se muestra el panel
	 * @param padre Controlador de la ventana sobre la que se muestra el panel
	 */
	public ControlPanelGestionarPedido(Tienda tienda, Empleado empleado, Pedido pedido, VentanaGestPedidos vista, ControlGestPedidos padre) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.pedido = pedido;
		
		List<String> productos = new LinkedList<>();
		for(StockExterno s : pedido.getItemsPedido()) {
			productos.add(s.getProducto().getNombre());
		}
		
		EstadoPedido estado = pedido.getEstado();
		String estadoString = estado.equals(EstadoPedido.EN_PREPARACION) ? "EN PREPARACION" : estado.name();
		PanelPedidoGestionarPedido panel = new PanelPedidoGestionarPedido(pedido.getCliente().getNombre(), estadoString, "" + pedido.getId(), "pfp.png", ACTION_NAME, productos.toArray(new String[0]));
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTION_NAME:
			intentarAvanzar();
			break;
		}
	}
	
	/**
	 * Acción que se realiza al intentar avanzar el estado del pedido
	 */
	private void intentarAvanzar() {
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas avanzar este pedido?")) {
			try {
				tienda.getHistorial().avanzarEstadoPedido(empleado, pedido);
			} catch (InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			SwingUtilities.invokeLater(() -> {new ControlGestPedidos(tienda, empleado);});
			new VentanaMensaje("El pedido se ha avanzado correctamente");
		}
	}
}
