package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.time.YearMonth;
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
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasTienda;

public class ControlEstadisticasWallapop implements ControladorPantalla {
	private VentanaEstadisticasTienda vista;
	private static String[] COLUMNAS = {"Total recaudado", "Artículos intercambiados", "Porcentaje recaudación"};
	
	public ControlEstadisticasWallapop(Tienda tienda, Gestor gestor) {
		this.vista = new VentanaEstadisticasTienda(COLUMNAS);
		
		YearMonth inicio = YearMonth.of(2000, 1);
		YearMonth fin = YearMonth.now();
	
		try {
			List<StatsMensual> listaMeses = tienda.getHistorial().getIntercambiosEntreMeses(inicio, fin);
			StatsMensual total = tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			
			for (StatsMensual stats: listaMeses) {
				double porcentaje = stats.getRecaudacion()/total.getRecaudacion() * 100;
				vista.anadirDisplay(new PanelEstadisticasTienda(stats.getMes(), stats.getRecaudacion(), stats.getUnidades(), porcentaje));
			}
			
			TiendaFrame.getInstance().navegarA(this);
		} catch(InvalidArgumentException e) {
			new VentanaMensaje(e.toString());
		}
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
