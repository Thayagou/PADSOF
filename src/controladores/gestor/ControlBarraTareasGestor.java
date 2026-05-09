package controladores.gestor;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import controladores.ControlBarraTareas;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.BarraTareasGestor;
import vistas.herramientas.ButtonFactory;

public class ControlBarraTareasGestor implements ControlBarraTareas {
	private static final String VOLVER_ACTION = "Volver";
	private static final String HOME_ACTION = "Home";
	private static final String CERRAR_SESION_ACTION = "Cerrar sesión";
	private static final String INFO = "Info";
	private final Tienda tienda;
	private final Gestor gestor;
	private TiendaFrame frame;
	private BarraTareasGestor barraTareas;
	
	public ControlBarraTareasGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.frame = TiendaFrame.getInstance();
		
		barraTareas = new BarraTareasGestor();
		barraTareas.setControlador(this);
		frame.setBarraTareas(barraTareas);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VOLVER_ACTION -> {
			TiendaFrame.getInstance().volverAtras();
		}
		case HOME_ACTION -> SwingUtilities.invokeLater(() -> new ControlInicioGestor(tienda, gestor));
		case CERRAR_SESION_ACTION -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		case INFO -> new VentanaMensaje(TiendaFrame.getInstance().getInfo(), 0);
		}
		
	}

}
