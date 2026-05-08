package controladores.gestor.configurarSistema;

import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.ParametroSistema;
import modelo.sistema.Sistema;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.configurarSistema.PanelParametroSistema;
import vistas.gestor.configurarSistema.VentanaGestionarParametrosSistema;

/**
 * Clase controladora de la vista correspondiente a la configuración de los parámetros del sistema, permitiendo su visualización y modificación
 */
public class ControlConfigurarSistema implements ControladorPantalla{
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Gestor de la tienda sobre la que estamos actuando. */
	private Gestor gestor;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaGestionarParametrosSistema vista;
	
	/** Mapa que asocia a cada parámetro del sistema su panel correspondiente de la vista*/
	private Map<ParametroSistema, PanelParametroSistema> mapaPaneles = new HashMap<>();
	
	/**
	 * Instancia un nuevo Objeto ControlConfigurarSistema. Crea cada panel asociado a cada uno de los paneles de configuración y se establece como su controlador
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param gestor Gestor de la tienda sobre la que estamos actuando.
	 */
	public ControlConfigurarSistema(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaGestionarParametrosSistema();
		
		Sistema sistema = Sistema.getInstancia();
		
		// Creación de cada uno de los paneles
		PanelParametroSistema numProductosRecomendados = new PanelParametroSistema("  Número de productos recomendados:              ", String.format("%.2f", sistema.getPonderacionCategoria()), ParametroSistema.CATEGORIA.name());
		numProductosRecomendados.setControlador(this);
		vista.anadirDisplay(numProductosRecomendados);
		mapaPaneles.put(ParametroSistema.NUMERO_PRODUCTOS_RECOMENDADOS, numProductosRecomendados);
		
		PanelParametroSistema categoria = new PanelParametroSistema("  Categoría:              ", String.format("%.2f", sistema.getPonderacionCategoria()), ParametroSistema.CATEGORIA.name());
		categoria.setControlador(this);
		vista.anadirDisplay(categoria);
		mapaPaneles.put(ParametroSistema.CATEGORIA, categoria);
		
		PanelParametroSistema precioDeCompra = new PanelParametroSistema("  Precio de compra:       ", String.format("%.2f", sistema.getPonderacionPrecioCompra()), ParametroSistema.PRECIO_COMPRA.name());
		precioDeCompra.setControlador(this);
		vista.anadirDisplay(precioDeCompra);
		mapaPaneles.put(ParametroSistema.PRECIO_COMPRA, precioDeCompra);
		
		PanelParametroSistema valoracionesProducto = new PanelParametroSistema("  Valoraciones producto:  ", String.format("%.2f", sistema.getPonderacionValoracionesProducto()), ParametroSistema.VALORACIONES_PRODUCTO.name());
		valoracionesProducto.setControlador(this);
		vista.anadirDisplay(valoracionesProducto);
		mapaPaneles.put(ParametroSistema.VALORACIONES_PRODUCTO, valoracionesProducto);
		
		PanelParametroSistema productoRecomendado = new PanelParametroSistema("  Producto recomendado:   ", String.format("%.2f", sistema.getPonderacionProductoRecomendado()), ParametroSistema.PRODUCTO_RECOMENDADO.name());
		productoRecomendado.setControlador(this);
		vista.anadirDisplay(productoRecomendado);
		mapaPaneles.put(ParametroSistema.PRODUCTO_RECOMENDADO, productoRecomendado);
		
		PanelParametroSistema busqueda = new PanelParametroSistema("  Búsqueda:               ", String.format("%.2f", sistema.getPonderacionBusqueda()), ParametroSistema.BUSQUEDA.name());
		busqueda.setControlador(this);
		vista.anadirDisplay(busqueda);
		mapaPaneles.put(ParametroSistema.BUSQUEDA, busqueda);
		
		PanelParametroSistema duracionCarrito = new PanelParametroSistema("  Duración carrito:       ", stringDuracion(sistema.getTiempoCaducaCarrito()), ParametroSistema.DURACION_CARRITO.name());
		duracionCarrito.setControlador(this);
		vista.anadirDisplay(duracionCarrito);
		mapaPaneles.put(ParametroSistema.DURACION_CARRITO, duracionCarrito);
		
		PanelParametroSistema duracionOferta = new PanelParametroSistema("  Duración ofertas:       ", stringDuracion(sistema.getTiempoCaducaOferta()), ParametroSistema.DURACION_OFERTA.name());
		duracionOferta.setControlador(this);
		vista.anadirDisplay(duracionOferta);
		mapaPaneles.put(ParametroSistema.DURACION_OFERTA, duracionOferta);
		
		PanelParametroSistema precioValoracion = new PanelParametroSistema("  Precio valoracion:       ", String.format("%.2f", sistema.getPrecioValoracion()), ParametroSistema.PRECIO_VALORACION.name());
		precioValoracion.setControlador(this);
		vista.anadirDisplay(precioValoracion);
		mapaPaneles.put(ParametroSistema.PRECIO_VALORACION, precioValoracion);
		
		TiendaFrame.getInstance().navegarA(this);		
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador. En este caso corresponde a la confirmación de cambio de un parámetro del sistema
	 * 
	 * Recibe valores de entrada de las vistas, actúa sobre el modelo para obtener la respuesta y actualiza las ventanas correspondientes.
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ParametroSistema param = ParametroSistema.valueOf(e.getActionCommand());
		if (param == null) return;
		PanelParametroSistema panel = mapaPaneles.get(param);
		String valorText = panel.getValorTextField();
		
		try {
			switch (param) {
			
			case ParametroSistema.DURACION_CARRITO:
			case ParametroSistema.DURACION_OFERTA:
				String[] parts = panel.getValorTextField().split(":");
				if (parts.length < 4) throw new IllegalArgumentException();
				
				int days = Integer.parseInt(parts[0]);
				int hours = Integer.parseInt(parts[1]);
				int mins = Integer.parseInt(parts[2]);
				int secs = Integer.parseInt(parts[3]);
				
				Duration duracion = Duration.ofDays(days).plusHours(hours).plusMinutes(mins).plusSeconds(secs);
				tienda.gestionarParametroDeSistema(gestor, param, duracion);
			
				break;
			case ParametroSistema.NUMERO_PRODUCTOS_RECOMENDADOS:
				int valorInt = Integer.parseInt(valorText);
				tienda.gestionarParametroDeSistema(gestor, param, valorInt);
				break;
			default:
				double valor = Double.parseDouble(panel.getValorTextField());
				tienda.gestionarParametroDeSistema(gestor, param, valor);
				break;
			}
		} catch (IllegalArgumentException | InputMismatchException ex) {
			new VentanaMensaje(ex.getMessage(), 1);
		} catch (InvalidArgumentException iae) {
			new VentanaMensaje(iae.getMessage(), 1);
		}
	}
	
	/**
	 * Método para obtener un de manera formateada la duración
	 *
	 * @param duracion Duración a formatear
	 * @return String formateado de la duración
	 */
	private String stringDuracion(Duration duracion) {
		long days    = duracion.toDays();
	    long hours   = duracion.toHoursPart();
	    long minutes = duracion.toMinutesPart();
	    long seconds = duracion.toSecondsPart();

	    return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
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
		return "En esta ventana se muestran los valores asociados a los parámetros del sistema y se permite modificar su valor rellenando el campo asociado y pulsando el botón de confirmación";
	}

}
