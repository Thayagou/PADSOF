package controladores.cliente.venta;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.pantallas.ControlInfoProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;

public class ControlPanelProductoCliente implements ActionListener {
	protected Producto producto;
	protected Tienda tienda;
	protected PanelProducto panel;
	protected VentanaConDisplay<? super PanelProducto> vista;
	protected ClienteRegistrado cliente;

	protected static final String DF_PRODUCT_IMAGE = "producto.png";
	
	private static final String actionName = "Ver producto";

	public ControlPanelProductoCliente(Tienda tienda, ClienteRegistrado cliente, Producto producto,
			VentanaConDisplay<? super PanelProducto> vista) {
		this.producto = producto;
		this.tienda = tienda;
		this.cliente = cliente;
		this.vista = vista;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}

		String imageRoute;
		if (producto.getImagen() == null || producto.getImagen().isBlank())
			imageRoute = DF_PRODUCT_IMAGE;
		else
			imageRoute = producto.getImagen();

		panel = new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute,
				producto.getPuntuacionMedia(), producto.getPrecio(), actionName, categorias.toArray(new String[0]));

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case actionName:
			SwingUtilities.invokeLater(() -> {
				try {
					new ControlInfoProductoCliente(tienda, cliente, producto);
				} catch (Exception ex) {
					new VentanaMensaje("Producto no encontrado: " + producto.getNombre());
				}
			});
		}
	}
}
