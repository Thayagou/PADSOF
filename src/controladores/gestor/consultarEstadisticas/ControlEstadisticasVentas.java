package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsMensual;
import modelo.estadistica.StatsUsuario;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;
import vistas.gestor.consultarEstadisticas.PanelEstadisticasTienda;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasTienda;

public class ControlEstadisticasVentas implements ControladorPantalla {
	private Tienda tienda;
	private Gestor gestor;
	private VentanaEstadisticasTienda vista;
	
	public ControlEstadisticasVentas(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaEstadisticasTienda();
		
		YearMonth inicio = YearMonth.of(2000, 1);
		YearMonth fin = YearMonth.now();
	
		try {
			List<StatsMensual> listaMeses = tienda.getHistorial().getVentasEntreMeses(inicio, fin);
			StatsMensual total = tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			
			for (StatsMensual stats: listaMeses) {
				vista.anadirDisplay(new PanelEstadisticasTienda(stats.getMes(), stats.getRecaudacion(), stats.getUnidades(), stats.getRecaudacion()/total.getRecaudacion()));
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
