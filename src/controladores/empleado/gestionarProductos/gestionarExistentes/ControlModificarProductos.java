package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Stock;
import modelo.venta.productos.caracteristicas.CaracteristicasComic;
import modelo.venta.productos.caracteristicas.CaracteristicasFigura;
import modelo.venta.productos.caracteristicas.CaracteristicasJuego;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductoIndividual;

public class ControlModificarProductos implements ControladorPantalla {
	private final Tienda tienda;
	private final Usuario usuario;
	private final Stock producto;
	private final VentanaAnadirProductoIndividual vista;

	public ControlModificarProductos(Tienda tienda, Usuario usuario, Stock producto) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.producto = producto;
		Producto p = producto.getProducto();

		List<String> categorias = new LinkedList<>();
		for (Categoria c : tienda.getAlmacen().getCategorias()) {
			categorias.add(c.getNombre());
		}

		List<String> productoCategorias = new LinkedList<>();
		for (Categoria c : p.getCategorias()) {
			productoCategorias.add(c.getNombre());
		}
		
		String[] tiposProductos = { "Comic", "Juego", "Figura" };
		String[] espComic = new CaracteristicasComic(null, null, 0, null).getNombresCaracteristicas();
		String[] espJuego = new CaracteristicasJuego(0, null, null).getNombresCaracteristicas();
		String[] espFigura = new CaracteristicasFigura(null, null, null).getNombresCaracteristicas();
		this.vista = new VentanaAnadirProductoIndividual(p.getNombre(), p.getDescripcion(),
				productoCategorias.toArray(new String[0]), categorias.toArray(new String[0]), p.getPrecio()+"",
				producto.getUdsEnStock()+"", p.getTipoProducto(), tiposProductos, p.getValoresCaracteristicas(), espComic, espJuego, espFigura, true);

		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
