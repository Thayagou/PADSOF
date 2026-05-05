package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.*;
import vistas.herramientas.*;

public class VentanaNotificacionesCliente extends JPanel implements VentanaConDisplay<PanelNotificacion> {

	private static final long serialVersionUID = 1L;
	
	private JPanel notificaciones = new JPanel();
	
	public VentanaNotificacionesCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		notificaciones.setLayout(new BoxLayout(notificaciones, BoxLayout.Y_AXIS));
		notificaciones.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(notificaciones);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Notificaciones", contenido));

		refreshList();
	}
	
	private void refreshList() {
		notificaciones.revalidate();
		notificaciones.repaint();
	}

	@Override
	public <K extends PanelNotificacion> PanelNotificacion anadirDisplay(K panelDisplay) {
		notificaciones.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
	
	public void setControlador(ActionListener c) {
		
	}

}
