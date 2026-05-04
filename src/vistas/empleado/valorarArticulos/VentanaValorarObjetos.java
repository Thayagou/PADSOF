package vistas.empleado.valorarArticulos;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaValorarObjetos extends JPanel implements VentanaConDisplay<PanelArticuloPendienteValoracion> {
	private static final long serialVersionUID = 1L;
	private JPanel listaPanel;

	public VentanaValorarObjetos() {
		setLayout(new BorderLayout());
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.add(BorderLayout.CENTER, scroll);

		add(PanelFactory.getVentanaConCabecera("Artículos pendientes de valorar", panelCentral), BorderLayout.CENTER);
	}

	@Override
	public PanelArticuloPendienteValoracion anadirDisplay(PanelArticuloPendienteValoracion panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
}
