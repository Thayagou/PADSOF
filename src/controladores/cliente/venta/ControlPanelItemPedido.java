package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.pantallas.ControlAnadirResena;
import controladores.cliente.venta.pantallas.ControlInfoProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.StockExterno;
import vistas.cliente.venta.PanelItemPedido;
import vistas.common.displays.VentanaConDisplay;

public class ControlPanelItemPedido implements ActionListener {

	protected Tienda tienda;
	protected ClienteRegistrado cliente;
	protected StockExterno item;
	protected PanelItemPedido panel;
	protected VentanaConDisplay<? super PanelItemPedido> vista;
	
	private static final String seeProduct = "Ver producto";

	protected static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlPanelItemPedido(Tienda tienda, ClienteRegistrado cliente, StockExterno item,
			VentanaConDisplay<? super PanelItemPedido> vista) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.item = item;
		this.vista = vista;

		int unidades = item.getUdsEnStock();
		
		String imageRoute;
		if (item.getProducto().getImagen() == null || item.getProducto().getImagen().isBlank())
			imageRoute = DF_PRODUCT_IMAGE;
		else
			imageRoute = item.getProducto().getImagen();

		String[] categorias = java.util.Arrays.stream(item.getProducto().getCategorias()).map(c -> c.getNombre())
				.toArray(String[]::new);

		panel = new PanelItemPedido(item.getProducto().getNombre(), item.getProducto().getDescripcion(),
				imageRoute, item.getProducto().getPuntuacionMedia(), item.getProducto().getPrecio(),
				unidades, seeProduct, categorias);

		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case seeProduct:
			SwingUtilities.invokeLater(() -> new ControlInfoProductoCliente(tienda, cliente, item.getProducto()));
			break;
		case PanelItemPedido.VALORAR_ACTION:
			SwingUtilities.invokeLater(() -> new ControlAnadirResena(tienda, cliente, item.getProducto()));
			break;
		}
	}
}
