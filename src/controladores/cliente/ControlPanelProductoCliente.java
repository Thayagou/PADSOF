package controladores.cliente;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.common.VentanaMensaje;

public class ControlPanelProductoCliente implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProducto panel;
	private ClienteRegistrado cliente;

	private static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlPanelProductoCliente(Tienda tienda, ClienteRegistrado cliente, Producto producto,
			VentanaConDisplay<? super PanelProducto> vista) {
		this.producto = producto;
		this.tienda = tienda;
		this.cliente = cliente;

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
				producto.getPuntuacionMedia(), producto.getPrecio(), "Ver producto", categorias.toArray(new String[0]));

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Ver producto":
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
