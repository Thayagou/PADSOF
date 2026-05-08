package vistas.empleado.general;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelNotificacion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

public class VentanaNotificacionesEmpleado extends JPanel implements VentanaConDisplay<PanelNotificacion>{
	private static final long serialVersionUID = 1L;
	private JPanel listaPanel;

	public VentanaNotificacionesEmpleado() {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setOpaque(false);
		
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setOpaque(false);
		panelCentral.add(BorderLayout.CENTER, scroll);

		JPanel ventana = PanelFactory.getVentanaConCabecera("Notificaciones", panelCentral);
		ventana.setOpaque(false);
		
		add(ventana, BorderLayout.CENTER);
	}
	
	@Override
	public PanelNotificacion anadirDisplay(PanelNotificacion panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}

}
