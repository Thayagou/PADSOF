package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsUsuario;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasCliente;

public class ControlEstadisticasClientes implements ControladorPantalla {
	private VentanaEstadisticasCliente vista;
	private List<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>> panelesEstadisticas = new ArrayList<>();
	private Comparator<ParElementoPanel<StatsUsuario, PanelClienteEstadisticas>> orden;
	
	
	public ControlEstadisticasClientes(Tienda tienda, Gestor gestor) {
		
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

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran, siguiendo un orden establecido, las estadísticas relacionadas con los clientes de la tienda";
	}

}
