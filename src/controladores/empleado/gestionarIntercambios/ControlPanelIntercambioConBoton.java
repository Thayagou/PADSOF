package controladores.empleado.gestionarIntercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.Intercambio;
import vistas.common.PanelIntercambioConBoton;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarIntercambios.VentanaGestIntercambios;

public class ControlPanelIntercambioConBoton implements ActionListener {
	private final Tienda tienda;
	private final Empleado empleado;
	private final Intercambio intercambio;
	private final String ACTION_NAME = "Confirmar";

	public ControlPanelIntercambioConBoton(Tienda tienda, Empleado empleado, Intercambio intercambio, VentanaGestIntercambios vista) {
		this.tienda = tienda;
		this.empleado = empleado;
		this.intercambio = intercambio;
		
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
	
	private void intentarConfirmar() {
		try {
			tienda.getHistorial().validarIntercambio(empleado, intercambio);
		} catch (InvalidPermitException | InvalidArgumentException ex) {
			new VentanaMensaje(ex.getMessage());
		}
		
		new VentanaMensaje("El intercambio se ha confirmado correctamente");
		SwingUtilities.invokeLater(() -> new ControlGestIntercambios(tienda, empleado));
	}

}
