package controladores.gestor.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.gestor.general.BarraTareasGestor;

/**
 * Clase controladora de la barra de tareas superior del gestor
 */
public class ControlBarraTareasGestor implements ActionListener {
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private final Tienda tienda;
	
	/** Gestor de la tienda sobre la que estamos actuando. */
	private final Gestor gestor;
	
	/** Campo frame. */
	private TiendaFrame frame;
	
	/** Campo barraTareas. */
	private BarraTareasGestor barraTareas;
	
	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param gestor Gestor de la tienda sobre la que estamos actuando.
	 */
	public ControlBarraTareasGestor(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.frame = TiendaFrame.getInstance();
		
		barraTareas = new BarraTareasGestor();
		barraTareas.setControlador(this);
		frame.setBarraTareas(barraTareas);
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la barra de tareas del gestor
	 * 
	 * Se permite volver una pantalla atrás, ir a la ventana del inicio, cerrar sesión y ver la información de la ventana actual
	 * 
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case BarraTareasGestor.VOLVER_ACTION -> TiendaFrame.getInstance().volverAtras();
		case BarraTareasGestor.HOME_ACTION -> SwingUtilities.invokeLater(() -> new ControlInicioGestor(tienda, gestor));
		case BarraTareasGestor.CERRAR_SESION_ACTION -> SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		case BarraTareasGestor.INFO_ACTION -> new VentanaMensaje(TiendaFrame.getInstance().getInfo(), 0);
		}
		
	}

}
