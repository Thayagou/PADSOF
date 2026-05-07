package vistas.empleado.gestionarProductos.gestionarCategorias;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelDisplay;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarCategorias extends JPanel implements VentanaConDisplay<PanelDisplay> {

	private static final long serialVersionUID = 1L;
	
	private JPanel listaPanel = new JPanel();

	public VentanaGestionarCategorias() {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setOpaque(false);
		
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setOpaque(false);
		panelCentral.add(BorderLayout.CENTER, scroll);

		JPanel ventana = PanelFactory.getVentanaConCabecera("Gestionar categorías existentes", panelCentral);
		ventana.setOpaque(false);
		add(ventana);
		
		listaPanel.revalidate();
		listaPanel.repaint();
		
		
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
	
	
}
