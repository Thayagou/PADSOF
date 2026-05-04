package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.PanelItemPedido;
import vistas.common.*;

public class ControlPanelItemPedido implements ActionListener {

	protected Tienda tienda;
	protected ClienteRegistrado cliente;
	protected StockExterno item;
	protected PanelItemPedido panel;
	protected VentanaConDisplay<? super PanelItemPedido> vista;

	public ControlPanelItemPedido(Tienda tienda, ClienteRegistrado cliente, StockExterno item, VentanaConDisplay<? super PanelItemPedido> vista) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.item = item;
		this.vista = vista;

		int unidades = item.getUdsEnStock();

		String[] categorias = java.util.Arrays.stream(item.getProducto().getCategorias())
				.map(c -> c.getNombre())
				.toArray(String[]::new);

		panel = new PanelItemPedido(
				item.getProducto().getNombre(),
				item.getProducto().getDescripcion(),
				item.getProducto().getImagen(),
				item.getProducto().getPuntuacionMedia(),
				item.getProducto().getPrecio(),
				unidades,
				item.getProducto().getNombre(), // actionCommand
				categorias);

		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "valorar":
			SwingUtilities.invokeLater(() -> new ControlAnadirResena(tienda, cliente, item.getProducto()));
			break;
		}
	}
}
