package vistas.empleado.gestionarIntercambios;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelIntercambioConBoton;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaGestIntercambios extends JPanel implements VentanaConDisplay<PanelIntercambioConBoton>{
	private static final long serialVersionUID = 1L;
	private JPanel listaPanel;

	public VentanaGestIntercambios() {
		setLayout(new BorderLayout());
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.add(BorderLayout.CENTER, scroll);

		add(PanelFactory.getVentanaConCabecera("Intercambios pendientes de confirmar", panelCentral), BorderLayout.CENTER);
	}

	@Override
	public PanelIntercambioConBoton anadirDisplay(PanelIntercambioConBoton panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
}
