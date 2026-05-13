package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsMensual;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Reloj;
import modelo.sistema.Tienda;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.consultarEstadisticas.PanelEstadisticasTienda;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasTienda;

/**
 * Clase controladora de la vista correspondiente a las estadísticas asociadas a los intercambios y productos de segunda mano de la tienda.
 */
public class ControlEstadisticasWallapop implements ControladorPantalla {
	
	/** Constante label de ordenación: por mayor recaudación. */
	private static final String MAYOR_RECAUDACION = "Mayor recaudación";
	
	/** Constante label de ordenación: por menor recaudación. */
	private static final String MENOR_RECAUDACION = "Menor recaudación";
	
	/** Constante label de ordenación: por más productos comprados. */
	private static final String MAS_UNIDADES = "Más artículos intercambiados";
	
	/** Constante label de ordenación: por menos productos comprados. */
	private static final String MENOS_UNIDADES = "Menos artículos intercambiados";

	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaEstadisticasTienda vista;
	
	/** Lista de pares de estadísticas y paneles que nos permite reordenar los paneles siguiendo parámetros de las estadísticas sin la necesidad de generar nuevos paneles. */
	private List<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>> panelesEstadisticas = new ArrayList<>();
	
	/** Comparador que está siendo utilizado actualmente en la vista. */
	private Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>> orden;

	/** Columnas de estadísticas que serán mostradas. */
	private static String[] COLUMNAS = {"Total recaudado", "Artículos intercambiados", "Porcentaje recaudación"};
	

	/**
	 * Instancia un nuevo controlador ControlEstadisticasWallapop que crea la vista y paneles asociados a las estadísticas d elos intercambios.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 */
	public ControlEstadisticasWallapop(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaEstadisticasTienda(new String[] {MAYOR_RECAUDACION, MENOR_RECAUDACION, MAS_UNIDADES, MENOS_UNIDADES},COLUMNAS);
		vista.setControlador(this);
		vista.setInicio(Reloj.mesNow().minusMonths(1));
		vista.setFin(Reloj.mesNow());
		
		orden = getComparator(vista.getOpcionSeleccionadaOrden());
		
		cargarResultados();
	
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * Carga los resultados de la consulta de estadísticas por pantalla a partir del intervalo de fechas y orden establecido
	 */
	private void cargarResultados() {	
		try {
			YearMonth inicio = vista.getInicio();
			YearMonth fin = vista.getFin();
			panelesEstadisticas.clear();
			vista.vaciarLista();
			
			List<StatsMensual> listaMeses = tienda.getHistorial().getIntercambiosEntreMeses(inicio, fin);
			StatsMensual total = tienda.getHistorial().getIntercambiosEntreMesesAcumulado(inicio, fin);
			
			for (StatsMensual stats: listaMeses) {
				double porcentaje = stats.getRecaudacion()/total.getRecaudacion() * 100;
				PanelEstadisticasTienda panel = new PanelEstadisticasTienda(stats.getMes(), stats.getRecaudacion(), stats.getUnidades(), porcentaje);
				panelesEstadisticas.add(new ParElementoPanel<>(stats, panel));
			}
			
			panelesEstadisticas.sort(orden);
			
			for (ParElementoPanel<StatsMensual, PanelEstadisticasTienda> par: panelesEstadisticas) {
				vista.anadirDisplay(par.getPanel());
			}
			
			vista.refrescarLista();
		} catch(InvalidArgumentException e) {
			new VentanaMensaje(e.getMessage());
		}
		
	}

	/**
	 * Obtiene el comparator asociado al criterio actual de la vista.
	 *
	 * @param criterio String del panel de selección
	 * @return Comparador que nos permite ordenar los paneles
	 */
	private Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>> getComparator(String criterio) {
		switch(criterio) {
		case MAYOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p1, ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p2) {
	                return Double.compare(p2.getElem().getRecaudacion(), p1.getElem().getRecaudacion());
	            }
			};
		case MENOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p1, ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p2) {
	                return Double.compare(p1.getElem().getRecaudacion(), p2.getElem().getRecaudacion());
	            }
			};
		case MAS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p1, ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p2) {
	                return Double.compare(p2.getElem().getUnidades(), p1.getElem().getUnidades());
	            }
			};
		case MENOS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>>() {
				@Override
				 public int compare(ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p1, ParElementoPanel<StatsMensual, PanelEstadisticasTienda> p2) {
	                return Double.compare(p1.getElem().getUnidades(), p2.getElem().getUnidades());
	            }
			};

		}
		
		return orden;
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
		switch(e.getActionCommand()) {
		case VentanaEstadisticasTienda.CAMBIO_ORDEN_ACTION -> {
			orden = getComparator(vista.getOpcionSeleccionadaOrden());
			panelesEstadisticas.sort(orden);
			
			vista.vaciarLista();
			
			for (ParElementoPanel<StatsMensual, PanelEstadisticasTienda> par : panelesEstadisticas) {
				vista.anadirDisplay(par.getPanel());
			}
			
			vista.refrescarLista();	
		}
		case VentanaEstadisticasTienda.CONFIRMAR_CAMBIO_FECHA_ACTION -> {
			cargarResultados();
		}
	}
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
		return "En esta ventana se muestran, siguiendo un orden establecido, las estadísticas mensuales de intercambios de la tienda entre los meses de inicio y fin";
	}

}
