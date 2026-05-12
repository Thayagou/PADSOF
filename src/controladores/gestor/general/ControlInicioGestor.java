package controladores.gestor.general;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.gestor.VentanaInicioGestor;

/**
 * Clase controladora de la vista correspondiente a la ventana de inicio del gestor	
 */
public class ControlInicioGestor implements ControladorPantalla {
	
	/** Gestor de la tienda sobre la que estamos actuando. */
	private Gestor gestor;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaInicioGestor vista;

	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param gestor Gestor de la tienda sobre la que estamos actuando.
	 */
	public ControlInicioGestor(Tienda tienda, Gestor gestor) {
		this.gestor = gestor;

		this.vista = new VentanaInicioGestor(tienda);

		/* Se crean las barras que se autogestionan y añaden al frame */
		new ControlBarraLateralGestor(tienda, gestor);
		new ControlBarraTareasGestor(tienda, gestor);
		
		TiendaFrame.getInstance().resetearNavegacion(this);
	}

	/**
	 * Controla las acciones posibles sobre el inicio del gestor, pero al ser solo una pantalla de bienvenida no se realizan acciones
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) { }

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
		return "Bienvenido " + gestor.getNombre() + "! Selecciona una opción para empezar";
	}

}
