package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Stock;
import modelo.venta.productos.TipoJuego;
import modelo.venta.productos.caracteristicas.CaracteristicasComic;
import modelo.venta.productos.caracteristicas.CaracteristicasFigura;
import modelo.venta.productos.caracteristicas.CaracteristicasJuego;
import modelo.venta.productos.caracteristicas.CaracteristicasPack;
import modelo.venta.productos.caracteristicas.CaracteristicasProducto;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
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

		List<String> tiposJuego = new LinkedList<>();
		for (TipoJuego t : TipoJuego.values()) {
			tiposJuego.add(t.name());
		}

		List<PanelProducto> paneles = new LinkedList<>();
		for (Stock s : tienda.getAlmacen().getInventario()) {
			Producto prod = s.getProducto();

			List<String> prodCategorias = new LinkedList<>();
			for (Categoria c : prod.getCategorias()) {
				prodCategorias.add(c.getNombre());
			}

			paneles.add(new PanelProducto(prod.getNombre(), prod.getDescripcion(), prod.getImagen(),
					prod.getPuntuacionMedia(), prod.getPrecio(), "Producto", prodCategorias.toArray(new String[0])));
		}

		String[] tiposProductos = { "Comic", "Juego", "Figura", "Pack" };
		String[] espComic = new CaracteristicasComic(null, null, 0, null).getNombresCaracteristicas();
		String[] espJuego = new CaracteristicasJuego(0, null, null).getNombresCaracteristicas();
		String[] espFigura = new CaracteristicasFigura(null, null, null).getNombresCaracteristicas();
		String[] espPack = new CaracteristicasPack(new Stock[0]).getNombresCaracteristicas();

		this.vista = new VentanaAnadirProductoIndividual(p.getNombre(), p.getDescripcion(),
				productoCategorias.toArray(new String[0]), categorias.toArray(new String[0]), p.getPrecio() + "",
				producto.getUdsEnStock() + "", p.getTipoProducto(), tiposProductos, p.getValoresCaracteristicas(),
				espComic, espJuego, espFigura, espPack, tiposJuego.toArray(new String[0]),
				paneles.toArray(new PanelProducto[0]), true);
		this.vista.setControlador(this);
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaAnadirProductoIndividual.CONFIRMAR_ACTION:
			intentarModificar();
			break;
		}

	}

	private void intentarModificar() {

		int udsStock = Integer.parseInt(vista.getStock());

		String nombre = vista.getNombre();

		String desc = vista.getDescripcion();

		double precio = Double.parseDouble(vista.getPrecio());

		String imagen = vista.getImagen();

		CaracteristicasProducto caract = getCaracteristicasEsp();

		Categoria[] categorias = getCategorias();

		try {
			tienda.getAlmacen().modificarProducto(usuario, producto.getProducto(), udsStock, nombre, desc, precio,
					imagen, caract, categorias);
		} catch (InvalidArgumentException | DoubleDiscountException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
			return;
		}
		SwingUtilities.invokeLater(() -> new ControlGestionarExistentes(tienda, usuario));
		new VentanaMensaje("El producto se modificó correctamente");
	}

	private CaracteristicasProducto getCaracteristicasEsp() {
		String tipo = vista.getTipo();
		String[] esp = vista.getEspecificaciones();
		switch (tipo) {
		case "Comic":

			LocalDate fecha = null;
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(esp[0]);
				fecha = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			} catch (Exception e) {
				e.printStackTrace();
			}

			int numPags = Integer.parseInt(esp[2]);

			return new CaracteristicasComic(fecha, esp[1], numPags, esp[3]);
		case "Juego":
			int numJug = Integer.parseInt(esp[0]);

			TipoJuego tipoJuego = null;
			for (TipoJuego t : TipoJuego.values()) {
				if (t.name().equals(esp[2])) {
					tipoJuego = t;
					break;
				}
			}

			return new CaracteristicasJuego(numJug, esp[1], tipoJuego);
		case "Figura":

			return new CaracteristicasFigura(esp[0], esp[1], esp[2]);
		case "Pack":
			Stock[] productos = getProductos(esp[0]);
			return new CaracteristicasPack(productos);
		}

		return null;
	}

	private Categoria[] getCategorias() {
		List<Categoria> categorias = new LinkedList<>();
		for (String cat : vista.getCategorias()) {
			try {
				categorias.add(tienda.getAlmacen().getCategoria(cat));
			} catch (InvalidArgumentException e) {
				System.out.println(e);
			}
		}
		return categorias.toArray(new Categoria[0]);
	}

	private Stock[] getProductos(String prods) {
		if (prods == null || prods.isEmpty())
			return new Stock[0];

		String[] nombres = prods.split(";");
		List<Stock> productos = new LinkedList<>();

		for (String n : nombres) {
			for (Stock s : tienda.getAlmacen().getInventario()) {
				if (s.getProducto().getNombre().equals(n.trim())) {
					productos.add(s);
					break;
				}
			}
		}

		return productos.toArray(new Stock[0]);
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
	
	
}
