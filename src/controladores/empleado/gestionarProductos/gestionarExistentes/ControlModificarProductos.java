package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControlCargaImagen;
import controladores.ControladorPantalla;
import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Pack;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Stock;
import modelo.venta.productos.TipoJuego;
import modelo.venta.productos.caracteristicas.CaracteristicasComic;
import modelo.venta.productos.caracteristicas.CaracteristicasFigura;
import modelo.venta.productos.caracteristicas.CaracteristicasJuego;
import modelo.venta.productos.caracteristicas.CaracteristicasPack;
import modelo.venta.productos.caracteristicas.CaracteristicasProducto;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.empleado.gestionarProductos.anadirProductos.PanelProductoAnadirAPack;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductoIndividual;

public class ControlModificarProductos implements ControladorPantalla {
	private final Tienda tienda;
	private final Usuario usuario;
	private final Stock producto;
	private VentanaAnadirProductoIndividual vista;
	private String fotoSeleccionada;

	public ControlModificarProductos(Tienda tienda, Usuario usuario, Stock producto) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.producto = producto;
		this.fotoSeleccionada = producto.getProducto().getImagen();

		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaAnadirProductoIndividual.CONFIRMAR_ACTION:
			intentarModificar();
			break;
		case VentanaAnadirProductoIndividual.ACTION_SELECCIONAR_FOTO:
			cogerImagen();
			break;
		}

	}
	
	private void cogerImagen() {
		fotoSeleccionada = ControlCargaImagen.abrir("Producto");
		vista.actualizarPreview(fotoSeleccionada);
	}

	private void intentarModificar() {
		int udsStock;
		try {
			udsStock = Integer.parseInt(vista.getStock());
		} catch (Exception e) {
			new VentanaMensaje("Introduzca un número de unidades válido para el producto", 1);
			return;
		}

		String nombre = vista.getNombre();
		if(nombre.length() < 1) {
			new VentanaMensaje("Introduzca un nombre válido para el producto", 1);
			return;
		}

		String desc = vista.getDescripcion();
		if(desc.length() < 1) {
			new VentanaMensaje("Introduzca una descripción válida para el producto", 1);
			return;
		}

		double precio;
		try {
			precio = Double.parseDouble(vista.getPrecio());
		} catch (Exception e) {
			new VentanaMensaje("Introduzca un precio válido para el producto", 1);
			return;
		}

		if(fotoSeleccionada.equals("Selecciona la imagen") || fotoSeleccionada.length() < 1) {
			new VentanaMensaje("Introduzca una imagen válida para el producto", 1);
			return;
		}

		CaracteristicasProducto caract = getCaracteristicasEsp();
		if(caract == null) {
			return;
		}

		Categoria[] categorias = getCategorias();
		if(categorias.length < 1) {
			new VentanaMensaje("Introduzca al menos una categoría para el producto", 1);
			return;
		}

		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas modificar este producto?")) {
			try {
				tienda.getAlmacen().modificarProducto(usuario, producto.getProducto(), udsStock, nombre, desc, precio,
						fotoSeleccionada, caract, categorias);
			} catch (InvalidArgumentException | DoubleDiscountException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			TiendaFrame.getInstance().volverAtras();
			new VentanaMensaje("El producto se modificó correctamente");
		}
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
				new VentanaMensaje("Introduzca una fecha de publicación válida para el Comic", 1);
				return null;
			}
			
			if(esp[1].length() < 1) {
				new VentanaMensaje("Introduzca un autor válido para el Comic", 1);
				return null;
			}

			int numPags = Integer.parseInt(esp[2]);
			try {
				numPags = Integer.parseInt(esp[2]);
			} catch (Exception e) {
				new VentanaMensaje("Introduzca un número de páginas válido para el Comic", 1);
				return null;
			}
			
			if(esp[3].length() < 1) {
				new VentanaMensaje("Introduzca una editorial válida para el Comic", 1);
				return null;
			}

			return new CaracteristicasComic(fecha, esp[1], numPags, esp[3]);
		case "Juego":
			int numJug = Integer.parseInt(esp[0]);
			try {
				numJug = Integer.parseInt(esp[0]);
			} catch (Exception e) {
				new VentanaMensaje("Introduzca un número de jugadores válido para el Juego", 1);
				return null;
			}
			
			String rangoEdad = esp[1];
			if(rangoEdad.length() < 1) {
				new VentanaMensaje("Introduzca un rango de edad válido para el Juego", 1);
				return null;
			}

			TipoJuego tipoJuego = null;
			for (TipoJuego t : TipoJuego.values()) {
				if (t.name().equals(esp[2])) {
					tipoJuego = t;
					break;
				}
			}
			if(tipoJuego == null) {
				new VentanaMensaje("Introduzca un tipo de juego válido para el Juego", 1);
				return null;
			}

			return new CaracteristicasJuego(numJug, esp[1], tipoJuego);
		case "Figura":
			
			if(esp[0].length() < 1) {
				new VentanaMensaje("Introduzca dimensiones válidas para la Figura", 1);
				return null;
			}
			
			if(esp[1].length() < 1) {
				new VentanaMensaje("Introduzca una marca válida para la Figura", 1);
				return null;
			}
			
			if(esp[2].length() < 1) {
				new VentanaMensaje("Introduzca un material válido para la Figura", 1);
				return null;
			}

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

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes modificar la información del producto que desees, cambiando los valores establecidos en los campos";
	}
	
	@Override
	public void mostrar() {
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

		List<PanelProductoAnadirAPack> paneles = new LinkedList<>();
		for (Stock s : tienda.getAlmacen().getInventario()) {
			Producto prod = s.getProducto();

			List<String> prodCategorias = new LinkedList<>();
			for (Categoria c : prod.getCategorias()) {
				prodCategorias.add(c.getNombre());
			}

			paneles.add(new PanelProductoAnadirAPack(prod.getNombre(), prod.getDescripcion(), prod.getImagen(),
					prod.getPuntuacionMedia(), prod.getPrecio(), prodCategorias.toArray(new String[0])));
		}
		
		List<PanelProducto> productosPack = new LinkedList<>();
		if(p.getTipoProducto().equals("Pack")) {
			Pack pack = (Pack)p;
			for (Stock s : pack.getProductos()) {
				Producto pr = s.getProducto();

				List<String> prodCategorias = new LinkedList<>();
				for (Categoria c : pr.getCategorias()) {
					prodCategorias.add(c.getNombre());
				}

				productosPack.add(new PanelProducto(pr.getNombre(), pr.getDescripcion(), pr.getImagen(),
						pr.getPuntuacionMedia(), pr.getPrecio(), "", prodCategorias.toArray(new String[0])));
			}
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
				paneles.toArray(new PanelProductoAnadirAPack[0]), productosPack.toArray(new PanelProducto[0]), true);
		vista.actualizarPreview(p.getImagen());
		this.vista.setControlador(this);
	}
	
	@Override
	public boolean puedeVolver() {
		return false;
	}
	
}
