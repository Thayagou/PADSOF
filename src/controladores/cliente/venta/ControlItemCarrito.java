package controladores.cliente.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.StockExterno;
import vistas.common.*;
import vistas.cliente.*;
import vistas.cliente.venta.PanelItemCarrito;

public class ControlItemCarrito implements ActionListener {
	protected Producto producto;
	protected Tienda tienda;
	protected PanelItemCarrito panel;
	protected VentanaConDisplay<? super PanelItemCarrito> vista;
	protected ClienteRegistrado cliente;
	private int unidades;

	protected static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlItemCarrito(Tienda tienda, ClienteRegistrado cliente, StockExterno stock,
			VentanaConDisplay<? super PanelItemCarrito> vista) {
		this.producto = stock.getProducto();
		this.unidades = stock.getUdsEnStock();
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

		panel = new PanelItemCarrito(producto.getNombre(), producto.getDescripcion(), imageRoute,
				producto.getPuntuacionMedia(), producto.getPrecio(), unidades, "Ver producto",
				categorias.toArray(new String[0]));

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "quitar":
			try {
				SwingUtilities.invokeLater(() -> new ControlManejoCarrito(tienda, cliente));
				tienda.quitarDeCarritoDe(cliente, producto);
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
			break;
		}
	}
}
