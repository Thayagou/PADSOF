package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.cliente.intercambios.pantallas.ControlAnadirArticulo;
import controladores.cliente.intercambios.pantallas.ControlBuscarArticulos;
import controladores.cliente.intercambios.pantallas.ControlIntercambiosPendientes;
import controladores.cliente.intercambios.pantallas.ControlManejoCartera;
import controladores.cliente.intercambios.pantallas.ControlOfertasEntrantes;
import controladores.cliente.intercambios.pantallas.ControlVerMisOfertas;
import controladores.cliente.venta.pantallas.ControlBuscarCliente;
import controladores.cliente.venta.pantallas.ControlManejoCarrito;
import controladores.cliente.venta.pantallas.ControlVerCompras;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.general.BarraLateralCliente;

/**
 * Controlador que gestiona las acciones de la barra lateral del cliente, redirigiendo a las diferentes pantallas según la opción seleccionada.
 */
public class ControlBarraLateralCliente implements ActionListener {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo cliente. Cliente registrado que ha iniciado sesión. */
	private ClienteRegistrado cliente;
	
	/**
	 * Instancia un nuevo Objeto ControlBarraLateralCliente.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que ha iniciado sesión.
	 */
	public ControlBarraLateralCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
	}

	/**
	 * actionPerformed.
	 * Redirige a la pantalla correspondiente según el comando del botón pulsado.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case BarraLateralCliente.SEARCH_PRODUCTS:
			SwingUtilities.invokeLater(() -> new ControlBuscarCliente(tienda, cliente));
			break;
		case BarraLateralCliente.SHOP_CAR:
			SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
			break;
		case BarraLateralCliente.SEARCH_ART:
			SwingUtilities.invokeLater(() -> new ControlBuscarArticulos(tienda, cliente));
			break;
		case BarraLateralCliente.WALLET:
			SwingUtilities.invokeLater(() -> new ControlManejoCartera(tienda, cliente, cliente));
			break;
		case BarraLateralCliente.ADD_ART:
			SwingUtilities.invokeLater(() -> new ControlAnadirArticulo(tienda, cliente));
			break;
		case BarraLateralCliente.OFFERS_SENT:
			SwingUtilities.invokeLater(() -> new ControlVerMisOfertas(tienda, cliente));
			break;
		case BarraLateralCliente.OFFERS_RECIEVED:
			SwingUtilities.invokeLater(() -> new ControlOfertasEntrantes(tienda, cliente));
			break;
		case BarraLateralCliente.EXCHANGES:
			SwingUtilities.invokeLater(() -> new ControlIntercambiosPendientes(tienda, cliente));
			break;
		case BarraLateralCliente.COMPRAS:
			SwingUtilities.invokeLater(() -> new ControlVerCompras(tienda, cliente));
			break;
		}
	}
}