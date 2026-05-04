package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.estadistica.StatsUsuario;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.PanelClienteEstadisticas;
import vistas.gestor.VentanaEstadisticasCliente;

public class ControlEstadisticasClientes implements ActionListener {
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
		
		TiendaFrame.getInstance().setVistaActual(vista);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
