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
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.consultarEstadisticas.PanelEstadisticasTienda;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasCliente;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasTienda;

public class ControlEstadisticasVentas implements ControladorPantalla {
	private static final String MAYOR_RECAUDACION = "Mayor recaudación";
	private static final String MENOR_RECAUDACION = "Menor recaudación";
	private static final String MAS_UNIDADES = "Más productos comprados";
	private static final String MENOS_UNIDADES = "Menos productos comprados";

	
	
	private static String[] COLUMNAS = {"Total recaudado", "Productos comprados", "Porcentaje recaudación"};
	private Tienda tienda;
	private Gestor gestor;
	private VentanaEstadisticasTienda vista;
	private List<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>> panelesEstadisticas = new ArrayList<>();
	private Comparator<ParElementoPanel<StatsMensual, PanelEstadisticasTienda>> orden;
	
	
	public ControlEstadisticasVentas(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaEstadisticasTienda(COLUMNAS);
		vista.setControlador(this);
		
		YearMonth inicio = YearMonth.of(2000, 1);
		YearMonth fin = YearMonth.now();
	
		try {
			List<StatsMensual> listaMeses = tienda.getHistorial().getVentasEntreMeses(inicio, fin);
			StatsMensual total = tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			
			for (StatsMensual stats: listaMeses) {
				double porcentaje = stats.getRecaudacion()/total.getRecaudacion() * 100;
				PanelEstadisticasTienda panel = new PanelEstadisticasTienda(stats.getMes(), stats.getRecaudacion(), stats.getUnidades(), porcentaje);
				panelesEstadisticas.add(new ParElementoPanel<>(stats, panel));
					
				vista.anadirDisplay(panel);
			}
			
			TiendaFrame.getInstance().navegarA(this);
		} catch(InvalidArgumentException e) {
			new VentanaMensaje(e.toString());
		}
	
	}
	
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(VentanaEstadisticasCliente.CAMBIO_ORDEN_ACTION)) {
			orden = getComparator(vista.getOpcionSeleccionadaOrden());
			panelesEstadisticas.sort(orden);
			
			vista.vaciarLista();
			
			for (ParElementoPanel<StatsMensual, PanelEstadisticasTienda> par : panelesEstadisticas) {
				vista.anadirDisplay(par.getPanel());
			}
			
			vista.refrescarLista();	
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
