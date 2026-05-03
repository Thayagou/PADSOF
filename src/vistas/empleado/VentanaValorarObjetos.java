package vistas.empleado;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaValorarObjetos extends JPanel implements VentanaConDisplay<PanelArticuloPendienteValoracion> {
	private static final long serialVersionUID = 1L;
	private ArrayList<PanelArticuloPendienteValoracion> articulos = new ArrayList<>();
	private JPanel listaPanel;

	public VentanaValorarObjetos() {
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		JPanel panelCentral = new JPanel();
		panelCentral.add(scroll);

		add(PanelFactory.getVentanaConCabecera("Artículos pendientes de valorar", panelCentral));
	}

	@Override
	public PanelArticuloPendienteValoracion anadirDisplay(PanelArticuloPendienteValoracion panelDisplay) {
		articulos.add(panelDisplay);
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
}
