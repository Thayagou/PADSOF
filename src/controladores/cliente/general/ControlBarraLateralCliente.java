package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.cliente.intercambios.pantallas.ControlAnadirArticulo;
import controladores.cliente.intercambios.pantallas.ControlBuscarArticulos;
import controladores.cliente.intercambios.pantallas.ControlManejoCartera;
import controladores.cliente.intercambios.pantallas.ControlVerMisOfertas;
import controladores.cliente.venta.pantallas.ControlBuscarCliente;
import controladores.cliente.venta.pantallas.ControlManejoCarrito;
import controladores.cliente.venta.pantallas.ControlVerCompras;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.general.BarraLateralCliente;

/**
 * Tipo: Class ControlBarraLateralCliente.
 */
public class ControlBarraLateralCliente implements ActionListener {
	
	/** Campo tienda. */
	private Tienda tienda;
	
	/** Campo cliente. */
	private ClienteRegistrado cliente;
	
	/**
	 * Instancia un nuevo Objeto ControlBarraLateralCliente.
	 *
	 * @param tienda parámetro tienda
	 * @param cliente parámetro cliente
	 */
	public ControlBarraLateralCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
	}

	/**
	 * actionPerformed.
	 *
	 * @param e parámetro e
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
		case BarraLateralCliente.OFFERS:
			SwingUtilities.invokeLater(() -> new ControlVerMisOfertas(tienda, cliente));
			break;
		case BarraLateralCliente.COMPRAS:
			SwingUtilities.invokeLater(() -> new ControlVerCompras(tienda, cliente));
			break;
		}
	}
}
