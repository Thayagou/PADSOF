package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.List;

import modelo.estadistica.StatsMensual;
import modelo.estadistica.StatsUsuario;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasTienda;

public class ControlEstadisticasVentas implements ActionListener {
	private Tienda tienda;
	private Gestor gestor;
	private VentanaEstadisticasTienda vista;
	
	public ControlEstadisticasVentas(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaEstadisticasTienda();
	
		List<StatsMensual> listaMeses = tienda.getHistorial().getUsuariosMasActivos();

		for (StatsMensual stats: listaMeses) {
			YearMonth cliente = stats.getMes();
			vista.anadirDisplay();
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
