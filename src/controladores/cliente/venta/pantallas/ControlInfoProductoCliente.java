package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Resena;
import vistas.cliente.venta.pantallas.VentanaProductoCliente;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlInfoProductoCliente implements ControladorPantalla {

	private Tienda tienda;
	private Producto producto;
	private ClienteRegistrado cliente;
	private VentanaProductoCliente vista;

	private static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlInfoProductoCliente(Tienda tienda, ClienteRegistrado cliente, Producto producto) {
		this.tienda = tienda;
		this.producto = producto;
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

		this.vista = new VentanaProductoCliente(producto.getNombre(), producto.getDescripcion(), imageRoute,
				producto.getPuntuacionMedia(), producto.getPrecio(), producto.getCaracteristicas(), categorias.toArray(new String[0]));
		vista.setControlador(this);
		
		for (Resena r : producto.getResenas()) {
			vista.anadirPanelResena(r.getPuntuacion(), r.getComentario(), r.getUsuario().getNombre());
		}

		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ("comprar"):
			try {
				tienda.anadirACarritoDe(cliente, producto);
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "";
	}
}
