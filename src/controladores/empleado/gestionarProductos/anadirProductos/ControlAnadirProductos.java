package controladores.empleado.gestionarProductos.anadirProductos;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import controladores.ControlCargaImagen;
import controladores.ControladorPantalla;
import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Permiso;
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
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarProductos.anadirProductos.PanelProductoAnadirAPack;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductoIndividual;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

/**
 * Clase controladora de la vista correspondiente a anadir un nuevo producto
 */
public class ControlAnadirProductos implements ControladorPantalla {
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private final Tienda tienda;
	
	/** Usuario que realiza la acción */
	private final Usuario usuario;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaAnadirProductos vista;
	
	/** Permiso requerido para realizar esta acción */
	private static Permiso requerido = Permiso.PRODUCTOS;
	
	/** Nombre de la foto */
	private String fotoSeleccionada = "";
	
	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param usuario parámetro usuario
	 */
	public ControlAnadirProductos(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		
		if(!usuario.tienePermiso(requerido)) {
			new VentanaMensaje("No tiene el permiso para realizar esta acción", 1);
			return;
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * Recibe valores de entrada de las vistas, actúa sobre el modelo para obtener la respuesta y actualiza las ventanas correspondientes.
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaAnadirProductoIndividual.CONFIRMAR_ACTION:
			intentarAnadir();
			break;
		case VentanaAnadirProductoIndividual.ACTION_SELECCIONAR_FOTO:
			cogerImagen();
			break;
		}
	}
	
	private void cogerImagen() {
		String nuevaFoto = ControlCargaImagen.abrir("Producto");
		if(!(nuevaFoto == null)) fotoSeleccionada = nuevaFoto;
		vista.getVentanaIndividual().actualizarPreview(fotoSeleccionada);
	}
	
	/**
	 * intentarAnadir.
	 */
	private void intentarAnadir() {
		int udsStock;
		try {
			udsStock = Integer.parseInt(vista.getVentanaIndividual().getStock());
		} catch (Exception e) {
			new VentanaMensaje("Introduzca un número de unidades válido para el producto", 1);
			return;
		}

		String nombre = vista.getVentanaIndividual().getNombre();
		if(nombre.equals("Nombre") || nombre.length() < 1) {
			new VentanaMensaje("Introduzca un nombre válido para el producto", 1);
			return;
		}

		String desc = vista.getVentanaIndividual().getDescripcion();
		if(desc.equals("Descripción") || desc.length() < 1) {
			new VentanaMensaje("Introduzca una descripción válida para el producto", 1);
			return;
		}

		double precio;
		try {
			String stringPrecio = vista.getVentanaIndividual().getPrecio();
			stringPrecio = stringPrecio.replace(',', '.');
			precio = Double.parseDouble(stringPrecio);
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

		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas añadir este producto?")) {
			try {
				tienda.getAlmacen().anadirProducto(usuario, udsStock, nombre, desc, precio,
						fotoSeleccionada, caract, categorias);
			} catch (InvalidArgumentException | DoubleDiscountException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			TiendaFrame.getInstance().volverAtras();
			new VentanaMensaje("El producto se añadió correctamente");
		}
	}
	
	/**
	 * Obtiene CaracteristicasEsp.
	 *
	 * @return valor de CaracteristicasEsp
	 */
	private CaracteristicasProducto getCaracteristicasEsp() {
		String tipo = vista.getVentanaIndividual().getTipo();
		String[] esp = vista.getVentanaIndividual().getEspecificaciones();
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

			return new CaracteristicasJuego(numJug, rangoEdad, tipoJuego);
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

	/**
	 * Obtiene Categorias.
	 *
	 * @return valor de Categorias
	 */
	private Categoria[] getCategorias() {
		List<Categoria> categorias = new LinkedList<>();
		for (String cat : vista.getVentanaIndividual().getCategorias()) {
			try {
				categorias.add(tienda.getAlmacen().getCategoria(cat));
			} catch (InvalidArgumentException e) {
				new VentanaMensaje("Error al seleccionar categorías", 1);
				return null;
			}
		}
		return categorias.toArray(new Categoria[0]);
	}
	
	/**
	 * Obtiene Productos.
	 *
	 * @param prods parámetro prods
	 * @return valor de Productos
	 */
	private Stock[] getProductos(String prods) {
	    if (prods == null || prods.isEmpty()) return new Stock[0];
	    
	    Map<PanelProductoAnadirAPack, Integer> stocks = vista.getVentanaIndividual().getProductosPackSeleccionados();
	    List<Stock> productos = new LinkedList<>();

	    for (PanelProductoAnadirAPack panel : stocks.keySet()) {
	        for (Stock s : tienda.getAlmacen().getInventario()) {
	            if (panel.getNombre().equals(s.getProducto().getNombre())) {
	                try {
						productos.add(new Stock(s.getProducto(), stocks.get(panel)));
					} catch (InvalidArgumentException e) {
						new VentanaMensaje("Error al seleccionar productos", 1);
						return null;
					}
	                break;
	            }
	        }
	    }

	    return productos.toArray(new Stock[0]);
	}

	/**
	 * Getter de la vista que controla este controlador.
	 *
	 * @return JPanel de la vista
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Getter de la información que se muestra al consultar la ayuda.
	 *
	 * @return the explicacion
	 */
	@Override
	public String getExplicacion() {
		return "En esta ventana puedes añadir varios productos mediante un fichero de productos, pulsando sobre \"Cargar fichero de productos...\", o añadir un nuevo productos rellenando los campos que se ven";
	}
	
	@Override
	public void mostrar() {
		List<String> categorias = new LinkedList<>();
		for(Categoria c : tienda.getAlmacen().getCategorias()) {
			categorias.add(c.getNombre());
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
		
		String[] tiposProductos = { "Comic", "Juego", "Figura", "Pack"};
		String[] espComic = new CaracteristicasComic(null, null, 0, null).getNombresCaracteristicas();
		String[] espJuego = new CaracteristicasJuego(0, null, null).getNombresCaracteristicas();
		String[] espFigura = new CaracteristicasFigura(null, null, null).getNombresCaracteristicas();
		String[] espPack = new CaracteristicasPack(new Stock[0]).getNombresCaracteristicas();
		
		this.vista = new VentanaAnadirProductos(categorias.toArray(new String[0]), tiposProductos, espComic, espJuego, espFigura, espPack, tiposJuego.toArray(new String[0]), paneles.toArray(new PanelProductoAnadirAPack[0]));
		
		new ControlPanelSubirImagenes(vista, this);
		new ControlPanelCargarFichero(tienda, usuario, vista, this);
		vista.setControlador(this);
	}
}
