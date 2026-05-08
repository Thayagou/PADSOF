package controladores.empleado.gestionarProductos.anadirProductos;

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
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductoIndividual;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

public class ControlAnadirProductos implements ControladorPantalla {
	private final Tienda tienda;
	private final Usuario usuario;
	private VentanaAnadirProductos vista;
	
	public ControlAnadirProductos(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		
		List<String> categorias = new LinkedList<>();
		for(Categoria c : tienda.getAlmacen().getCategorias()) {
			categorias.add(c.getNombre());
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
		
		String[] tiposProductos = { "Comic", "Juego", "Figura", "Pack"};
		String[] espComic = new CaracteristicasComic(null, null, 0, null).getNombresCaracteristicas();
		String[] espJuego = new CaracteristicasJuego(0, null, null).getNombresCaracteristicas();
		String[] espFigura = new CaracteristicasFigura(null, null, null).getNombresCaracteristicas();
		String[] espPack = new CaracteristicasPack(new Stock[0]).getNombresCaracteristicas();
		
		this.vista = new VentanaAnadirProductos(categorias.toArray(new String[0]), tiposProductos, espComic, espJuego, espFigura, espPack, tiposJuego.toArray(new String[0]), paneles.toArray(new PanelProducto[0]));
		
		new ControlPanelCargarFichero(tienda, usuario, vista);
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaAnadirProductoIndividual.CONFIRMAR_ACTION:
			intentarAnadir();
			break;
		}
	}
	
	private void intentarAnadir() {
		int udsStock = Integer.parseInt(vista.getVentanaIndividual().getStock());

		String nombre = vista.getVentanaIndividual().getNombre();

		String desc = vista.getVentanaIndividual().getDescripcion();

		double precio = Double.parseDouble(vista.getVentanaIndividual().getPrecio());

		String imagen = vista.getVentanaIndividual().getImagen();

		CaracteristicasProducto caract = getCaracteristicasEsp();

		Categoria[] categorias = getCategorias();

		try {
			tienda.getAlmacen().anadirProducto(usuario, udsStock, nombre, desc, precio,
					imagen, caract, categorias);
		} catch (InvalidArgumentException | DoubleDiscountException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
			return;
		}
		SwingUtilities.invokeLater(() -> new ControlAnadirProductos(tienda, usuario));
		new VentanaMensaje("El producto se añadió correctamente");
	}
	
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
		for (String cat : vista.getVentanaIndividual().getCategorias()) {
			try {
				categorias.add(tienda.getAlmacen().getCategoria(cat));
			} catch (InvalidArgumentException e) {
				System.out.println(e);
			}
		}
		return categorias.toArray(new Categoria[0]);
	}
	
	private Stock[] getProductos(String prods) {
	    if (prods == null || prods.isEmpty()) return new Stock[0];
	    
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
		return "En esta ventana puedes añadir varios productos mediante un fichero de productos, pulsando sobre \"Cargar fichero de productos...\", o añadir un nuevo productos rellenando los campos que se ven";
	}
}
