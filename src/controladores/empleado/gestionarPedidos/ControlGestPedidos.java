package controladores.empleado.gestionarPedidos;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Permiso;
import modelo.venta.pedidos.Pedido;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarPedidos.VentanaGestPedidos;

/**
 * Esta clase representa el controlador de la ventana de gestión de pedidos
 */
public class ControlGestPedidos implements ControladorPantalla {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Empleado que realiza la acción */
	private final Empleado empleado;
	/** Ventana que se muestra */
	private VentanaGestPedidos vista;
	/** Permiso requerido para realizar esta acción */
	private static Permiso requerido = Permiso.PEDIDOS;

	/**
	 * Constructor del controlador de gestionar pedidos
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 */
	public ControlGestPedidos(Tienda tienda, Empleado empleado) {
		this.tienda = tienda;
		this.empleado = empleado;
		if(!empleado.tienePermiso(requerido)) {
			new VentanaMensaje("No tiene el permiso para realizar esta acción", 1);
			return;
		}

		this.vista = new VentanaGestPedidos();
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes avanzar el estado de los pedidos pendientes";
	}
	
	@Override
	public void mostrar() {
		this.vista.vaciar();
		Pedido[] pedidos = tienda.getHistorial().getPedidosPendientes();
		for(Pedido p : pedidos) {
			new ControlPanelGestionarPedido(tienda, empleado, p, vista, this);
		}
		vista.revalidate();
		vista.repaint();
	}
}
