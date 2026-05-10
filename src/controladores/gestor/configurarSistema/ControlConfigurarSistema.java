package controladores.gestor.configurarSistema;

import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.HashMap;
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
		
		mostrar();
		
		TiendaFrame.getInstance().navegarA(this);		
	}
	
	@Override
	public void mostrar() {
		Sistema sistema = Sistema.getInstancia();
		
		// Creación de cada uno de los paneles
		PanelParametroSistema numProductosRecomendados = new PanelParametroSistema("  Número de productos recomendados:              ", String.format("%d", sistema.getNumProductosRecomendados()), ParametroSistema.NUMERO_PRODUCTOS_RECOMENDADOS.name());
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
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador. En este caso corresponde a la confirmación de cambio de un parámetro del sistema
	 * 
	 * Recibe valores de entrada de los paneles, cambia los valores del sistema y actualiza las ventanas correspondientes.
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		String[] actionSplit = action.split("\s++");
		ParametroSistema param = ParametroSistema.valueOf(actionSplit[0]);
		
		if (actionSplit.length > 1 && actionSplit[1].trim().equals(PanelParametroSistema.INFO_ACTION)) {
			mostrarInfoPanel(param);
			return;
		}

		if (param == null) return;
		PanelParametroSistema panel = mapaPaneles.get(param);
		String valorText = panel.getValorTextField();
		
		if (!TiendaFrame.getConfirmacionUsuario("Estás seguro de que deseas modificar el valor del parámetro?")) return;
		
		try {
			switch (param) {
			
			case ParametroSistema.DURACION_CARRITO:
			case ParametroSistema.DURACION_OFERTA:
				try {
					String[] parts = panel.getValorTextField().split(":");
					if (parts.length < 4)
						throw new IllegalArgumentException();
	
					int days = Integer.parseInt(parts[0]);
					int hours = Integer.parseInt(parts[1]);
					int mins = Integer.parseInt(parts[2]);
					int secs = Integer.parseInt(parts[3]);
	
					Duration duracion = Duration.ofDays(days).plusHours(hours).plusMinutes(mins).plusSeconds(secs);
					tienda.gestionarParametroDeSistema(gestor, param, duracion);
					
				} catch (IllegalArgumentException ex) {
					new VentanaMensaje("Formato incorrecto de duración. Formato correcto DD:HH:MM:SS", 1);
					mostrar();
					return;
				}
				break;
			case ParametroSistema.NUMERO_PRODUCTOS_RECOMENDADOS:
				try {
					int valorInt = Integer.parseInt(valorText);
					if (valorInt < 0) throw new IllegalArgumentException();
					tienda.gestionarParametroDeSistema(gestor, ParametroSistema.NUMERO_PRODUCTOS_RECOMENDADOS, valorInt);
					
				} catch (IllegalArgumentException ex) {
					new VentanaMensaje("Formato incorrecto del parámetro. Debe ser un entero positivo", 1);
					mostrar();
					return;
				}
				break;
			default:
				try {
					double valor = Double.parseDouble(panel.getValorTextField());
					if (valor < 0) throw new IllegalArgumentException();
					tienda.gestionarParametroDeSistema(gestor, param, valor);
				} catch (IllegalArgumentException ex) {
					new VentanaMensaje("Formato incorrecto del parámetro. Debe ser un número positivo", 1);
					mostrar();
					return;
				}
				break;
			}
		} catch (InvalidArgumentException iae) {
			new VentanaMensaje(iae.getMessage(), 1);
			mostrar();
			return;
		}
		
		new VentanaMensaje("Se ha modificado el valor del parámetro correctamente", VentanaMensaje.INFO);
	}
	
	private void mostrarInfoPanel(ParametroSistema parametro) {
		String infoDePanel = "";
		
		switch(parametro)  {
		case BUSQUEDA:
			
			break;
		case CATEGORIA:
			infoDePanel = "Es la ponderación que tiene el hecho de que un producto pertenezca a una categoría para su vector de recomendación";
			break;
		case DURACION_CARRITO:
			infoDePanel = "Determina el tiempo que tarda en caducarse el carrito de un cliente desde que se le añade un último producto";
			break;
		case DURACION_OFERTA:
			infoDePanel = "Determina el tiempo que tarda en caducarse una oferta de intercambio desde que esta es realizada";
			break;
		case NUMERO_PRODUCTOS_RECOMENDADOS:
			infoDePanel = "Establece el número de productos que se muestran en la ventana de productos recomendados";
			break;
		case PRECIO_COMPRA:
			infoDePanel = "Es la ponderación que tiene el precio pagado por un producto a la hora de actualizar el vector de interés de un cliente tras pagar";
			break;
		case PRECIO_VALORACION:
			infoDePanel = "Es la ponderación que tiene la similitud entre un producto y el cliente (semejanza entre los vectores)";
			break;
		case PRODUCTO_RECOMENDADO:
			infoDePanel = "Es la ponderación que tiene la compatibilidad entre usuario y producto a la hora de calcular el valor de recomendación";
			break;
		case UDS_COMPRADAS:
			infoDePanel = "Es la ponderación que tiene el número de unidades compradas de un producto a la hora de actualizar el vector de interés de un cliente tras pagar";
			break;
		case VALORACIONES_PRODUCTO:
			infoDePanel = "Es la ponderación que tiene la media de puntuación de un producto a la hora de calcular su valor de compatibilidad con un determinado usuario";
			break;
		default:
			break;
		}
		
		new VentanaMensaje(infoDePanel, VentanaMensaje.INFO);
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
		return "En esta ventana se muestran los valores asociados a los parámetros del sistema y se permite modificar su valor rellenando el campo asociado y pulsando el botón de confirmación" ;
	}
	

}
