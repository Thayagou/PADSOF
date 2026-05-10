package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsMensual;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Reloj;
import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.consultarEstadisticas.PanelProductoEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasProductos;

/**
 * Clase controladora de la vista correspondiente a las estadísticas asociadas a los productos.
 */
public class ControlEstadisticasProductos implements ControladorPantalla {
	
	/** Tienda sobre la que se obtienen las estadísticas */
	private Tienda tienda;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaEstadisticasProductos vista;
	
	/** Pares de StatsMensual y PanelProductoEstadisticas que permite reordenación fácil para mostrar los elementos sin crear nuevos paneles. */
	private List<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>> panelesEstadisticas = new ArrayList<>();
	
	/** Orden establecido para mostrar las estadísticas de los productos. */
	private Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>> orden;
	

	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados a las estadísticas de los productos.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 */
	public ControlEstadisticasProductos(Tienda tienda) {
		this.tienda = tienda;
		
		this.vista = new VentanaEstadisticasProductos();
		vista.setControlador(this);
		vista.setInicio(Reloj.mesNow().minusMonths(1));
		vista.setFin(Reloj.mesNow());
		
		orden = getComparator(VentanaEstadisticasProductos.ORDENES[0]);
				
		cargarProductos();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	private void cargarProductos() {
		try {
			panelesEstadisticas.clear();
			
			YearMonth inicio = vista.getInicio();
			YearMonth fin = vista.getFin();
			
			List<Map.Entry<Producto, StatsMensual>> listaProductos = tienda.getHistorial().getProductosMayorRecaudacion(inicio, fin);
			StatsMensual total = tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			
			vista.vaciarLista();
			
			for (Map.Entry<Producto, StatsMensual> par : listaProductos) {
				Producto p = par.getKey();
				StatsMensual stats = par.getValue();
				ArrayList<String> categorias = new ArrayList<>();
				for(Categoria c : p.getCategorias()) {
					categorias.add(c.getNombre());
				}
				String imageName = p.getImagen() == null ? "producto.png" : p.getImagen();
				double porcentaje = stats.getRecaudacion()/total.getRecaudacion() * 100;
				
				PanelProductoEstadisticas panel = new PanelProductoEstadisticas(p.getNombre(), p.getDescripcion(), imageName, p.getPuntuacionMedia(), p.getPrecio(), stats.getRecaudacion(), stats.getUnidades(), porcentaje, categorias.toArray(new String[0]));
				panelesEstadisticas.add(new ParElementoPanel<>(stats, panel));
				
				vista.anadirDisplay(panel);
			}
			
			vista.refrescarLista();
		} catch(InvalidArgumentException e) {
			new VentanaMensaje(e.getMessage(), VentanaMensaje.ERROR);
		} catch(DateTimeParseException e) {
			new VentanaMensaje("Formato inválido de fecha. Correcto mm/yyyy", VentanaMensaje.ERROR);
			return;
		}
		
	}

	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * Pemite reordenar los elementos mostrados por pantalla y establecer el periodo en el que se busca 
	 * 
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case VentanaEstadisticasProductos.CAMBIO_ORDEN_ACTION -> {
				orden = getComparator(vista.getOpcionSeleccionadaOrden());
				panelesEstadisticas.sort(orden);
				
				vista.vaciarLista();
				
				for (ParElementoPanel<StatsMensual, PanelProductoEstadisticas> par : panelesEstadisticas) {
					vista.anadirDisplay(par.getPanel());
				}
				
				vista.refrescarLista();	
			}
			
			case VentanaEstadisticasProductos.CONFIRMAR_CAMBIO_FECHA_ACTION -> {
				cargarProductos();
			}
		}
		
	}
	
	/**
	 * Obtiene el comparator asociado al criterio actual de la vista.
	 *
	 * @param criterio String del panel de selección
	 * @return Comparador que nos permite ordenar los paneles
	 */
	private Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>> getComparator(String criterio) {
		switch(criterio) {
		case VentanaEstadisticasProductos.MAYOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p2.getElem().getRecaudacion(), p1.getElem().getRecaudacion());
	            }
			};
		case VentanaEstadisticasProductos.MENOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p1.getElem().getRecaudacion(), p2.getElem().getRecaudacion());
	            }
			};
		case VentanaEstadisticasProductos.MAS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p2.getElem().getUnidades(), p1.getElem().getUnidades());
	            }
			};
		case VentanaEstadisticasProductos.MENOS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsMensual, PanelProductoEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p1, ParElementoPanel<StatsMensual, PanelProductoEstadisticas> p2) {
	                return Double.compare(p1.getElem().getUnidades(), p2.getElem().getUnidades());
	            }
			};
		}
		
		return orden;
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
		return "En esta ventana se muestran, siguiendo un orden establecido, las estadísticas relacionadas con los productos de la tienda entre los meses de inicio y fin";
	}
	

}
