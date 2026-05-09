package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsUsuario;
import modelo.sistema.Reloj;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.common.app.TiendaFrame;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasCliente;

/**
 * Clase controladora de la vista correspondiente a mostrar .
 */
public class ControlEstadisticasClientes implements ControladorPantalla {
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaEstadisticasCliente vista;
	
	/** Lista de pares de estadísticas y paneles que nos permite reordenar los paneles siguiendo parámetros de las estadísticas sin la necesidad de generar nuevos paneles. */
	private List<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>> panelesEstadisticas = new ArrayList<>();
	
	/** Comparador que está siendo utilizado actualmente en la vista. */
	private Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>> orden;
	
	
	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados a las estadísticas de los clientes.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 */
	public ControlEstadisticasClientes(Tienda tienda) {
		
		this.vista = new VentanaEstadisticasCliente();
		vista.setControlador(this);
		
		orden = getComparator(VentanaEstadisticasCliente.ORDENES[0]);
	
		List<StatsUsuario> listaUsuarios = tienda.getHistorial().getUsuariosMasActivos();

		for (StatsUsuario stats: listaUsuarios) {
			ClienteRegistrado cliente = stats.getCliente();
			PanelClienteEstadisticas panel = new PanelClienteEstadisticas(cliente.getNombre(), "producto.png", stats.getGastoTotal(), stats.getUdsCompradas(), stats.getUdsIntercambiadas());
			panelesEstadisticas.add(new ParElementoPanel<>(stats, panel));
			
			vista.anadirDisplay(panel);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * Permite cambiar el orden en que se muestran los paneles
	 * 
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(VentanaEstadisticasCliente.CAMBIO_ORDEN_ACTION)) {
			orden = getComparator(vista.getOpcionSeleccionadaOrden());
			panelesEstadisticas.sort(orden);
			
			vista.vaciarLista();
			
			for (ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> par : panelesEstadisticas) {
				vista.anadirDisplay(par.getPanel());
			}
			
			vista.refrescarLista();	
		}	
	}
	
	/**
	 * Obtiene el comparator asociado al criterio actual de la vista.
	 *
	 * @param criterio String del panel de selección
	 * @return Comparador que nos permite ordenar los paneles
	 */
	private Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>> getComparator(String criterio) {
		switch(criterio) {
		case VentanaEstadisticasCliente.MAYOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p1, ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p2) {
	                return Double.compare(p2.getElem().getGastoTotal(), p1.getElem().getGastoTotal());
	            }
			};
		case VentanaEstadisticasCliente.MENOR_RECAUDACION:
			return new Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p1, ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p2) {
	                return Double.compare(p1.getElem().getGastoTotal(), p2.getElem().getGastoTotal());
	            }
			};
		case VentanaEstadisticasCliente.MAS_ARTICULOS:
			return new Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p1, ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p2) {
	                return Double.compare(p2.getElem().getUdsIntercambiadas(), p1.getElem().getUdsIntercambiadas());
	            }
			};
		case VentanaEstadisticasCliente.MENOS_ARTICULOS:
			return new Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p1, ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p2) {
	                return Double.compare(p1.getElem().getUdsIntercambiadas(), p2.getElem().getUdsIntercambiadas());
	            }
			};
		case VentanaEstadisticasCliente.MAS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p1, ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p2) {
	                return Double.compare(p2.getElem().getUdsCompradas(), p1.getElem().getUdsCompradas());
	            }
			};
		case VentanaEstadisticasCliente.MENOS_UNIDADES:
			return new Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>>() {
				@Override
				 public int compare(ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p1, ParElementoPanel<StatsUsuario, PanelClienteEstadisticas> p2) {
	                return Double.compare(p1.getElem().getUdsCompradas(), p2.getElem().getUdsCompradas());
	            }
			};
			
			
		}
		return null;
		
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
		return "En esta ventana se muestran, siguiendo un orden establecido, las estadísticas relacionadas con los clientes de la tienda";
	}

}
