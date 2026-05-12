package vistas.empleado.gestionarProductos.gestionarCategorias;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelDisplay;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa una ventana donde gestionar las categorías
 */
public class VentanaGestionarCategorias extends JPanel implements VentanaConDisplay<PanelDisplay> {
	private static final long serialVersionUID = 1L;
	/** Panel con los paneles de categorías */
	private JPanel listaPanel = new JPanel();

	/**
	 * Constructor de la ventana para gestionar categorías
	 */
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
	
	/**
	 * Vacía la lista de paneles
	 */
	public void vaciar() {
		listaPanel.removeAll();
	}
}
