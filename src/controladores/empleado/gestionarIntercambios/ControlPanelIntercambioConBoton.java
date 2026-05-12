package controladores.empleado.gestionarIntercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.Intercambio;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelIntercambioConBoton;
import vistas.empleado.gestionarIntercambios.VentanaGestIntercambios;

/**
 * Esta clase representa el controlador de un panel de intercambio con botón
 */
public class ControlPanelIntercambioConBoton implements ActionListener {
	/** Modelo de la tienda */
	private final Tienda tienda;
	/** Empleado que realiza la acción */
	private final Empleado empleado;
	/** Intercambio que se puede confirmar */
	private final Intercambio intercambio;
	/** Nombre de la acción confirmar*/
	private final String ACTION_NAME = "Confirmar";
	/** Controlador de la ventana padre en la que está */
	private final ControlGestIntercambios padre;

	/**
	 * Cosntructor del controlador de panel intercambio
	 * @param tienda Modelo de la tienda
	 * @param empleado Empleado que realiza la acción
	 * @param intercambio Intercambio que se puede confirmar
	 * @param vista Ventana en la que se muestra el panel
	 * @param padre Controlador de la ventana sobre la que se muestra el panel
	 */
	public ControlPanelIntercambioConBoton(Tienda tienda, Empleado empleado, Intercambio intercambio, VentanaGestIntercambios vista, ControlGestIntercambios padre) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.intercambio = intercambio;
		this.padre = padre;
		
		List<String> articulosOfrecidos = new LinkedList<>();
		for(ArticuloSegundaMano a : intercambio.getOfrecidos()) {
			articulosOfrecidos.add(a.getNombre());
		}
		
		List<String> articulosSolicitados = new LinkedList<>();
		for(ArticuloSegundaMano a : intercambio.getSolicitados()) {
			articulosSolicitados.add(a.getNombre());
		}
		
		PanelIntercambioConBoton panel = new PanelIntercambioConBoton(intercambio.getEmisor().getDueno().getNombre(), "pfp.png", articulosOfrecidos.toArray(new String[0]), ACTION_NAME, ACTION_NAME, intercambio.getReceptor().getDueno().getNombre(), "pfp.png", articulosSolicitados.toArray(new String[0]));
		vista.anadirDisplay(panel);
		panel.setControlador(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTION_NAME:
			intentarConfirmar();
			break;
		}
	}
	
	/**
	 * Acción una vez que se pulsa confirmar
	 */
	private void intentarConfirmar() {
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas confirmar intercambio?")) {
			try {
				tienda.getHistorial().validarIntercambio(empleado, intercambio);
			} catch (InvalidPermitException | InvalidArgumentException ex) {
				new VentanaMensaje(ex.getMessage(), 1);
				return;
			}
			padre.mostrar();
			new VentanaMensaje("El intercambio se ha confirmado correctamente");
		}
	}
}
