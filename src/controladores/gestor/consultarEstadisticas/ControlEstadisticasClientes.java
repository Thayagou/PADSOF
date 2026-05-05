package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.estadistica.StatsUsuario;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.consultarEstadisticas.PanelClienteEstadisticas;
import vistas.gestor.consultarEstadisticas.VentanaEstadisticasCliente;

public class ControlEstadisticasClientes implements ControladorPantalla {
	private Tienda tienda;
	private Gestor gestor;
	private VentanaEstadisticasCliente vista;
	
	public ControlEstadisticasClientes(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		
		this.vista = new VentanaEstadisticasCliente();
	
		List<StatsUsuario> listaUsuarios = tienda.getHistorial().getUsuariosMasActivos();

		for (StatsUsuario stats: listaUsuarios) {
			ClienteRegistrado cliente = stats.getCliente();
			vista.anadirDisplay(new PanelClienteEstadisticas(cliente.getNombre(), "producto.png", stats.getGastoTotal(), stats.getUdsCompradas(), stats.getUdsIntercambiadas()));
		}
		
		TiendaFrame.getInstance().navegarA(this);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
