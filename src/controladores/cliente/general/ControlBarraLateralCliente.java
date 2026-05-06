package controladores.cliente.general;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import controladores.cliente.intercambios.pantallas.ControlAnadirArticulo;
import controladores.cliente.intercambios.pantallas.ControlBuscarArticulos;
import controladores.cliente.intercambios.pantallas.ControlManejoCartera;
import controladores.cliente.intercambios.pantallas.ControlVerMisOfertas;
import controladores.cliente.venta.pantallas.ControlBuscarCliente;
import controladores.cliente.venta.pantallas.ControlManejoCarrito;
import controladores.cliente.venta.pantallas.ControlVerCompras;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;

public class ControlBarraLateralCliente implements ControlBarraLateral {
	
	private Tienda tienda;
	private ClienteRegistrado cliente;
	
	public ControlBarraLateralCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Buscar Productos":
			SwingUtilities.invokeLater(() -> new ControlBuscarCliente(tienda, cliente));
			break;
		case "Ver Carrito":
			SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
			break;
		case "Buscar Artículos":
			SwingUtilities.invokeLater(() -> new ControlBuscarArticulos(tienda, cliente));
			break;
		case "Ver Cartera":
			SwingUtilities.invokeLater(() -> new ControlManejoCartera(tienda, cliente, cliente));
			break;
		case "Añadir Artículo":
			SwingUtilities.invokeLater(() -> new ControlAnadirArticulo(tienda, cliente));
			break;
		case "Ver mis ofertas":
			SwingUtilities.invokeLater(() -> new ControlVerMisOfertas(tienda, cliente));
			break;
		case "Ver mis compras":
			SwingUtilities.invokeLater(() -> new ControlVerCompras(tienda, cliente));
			break;
		}
	}
}
