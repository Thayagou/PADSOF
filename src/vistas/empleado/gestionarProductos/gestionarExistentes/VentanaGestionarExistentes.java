package vistas.empleado.gestionarProductos.gestionarExistentes;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa una ventana para gestionar productos existentes
 */
public class VentanaGestionarExistentes extends JPanel implements VentanaConDisplay<PanelProducto> {
	private static final long serialVersionUID = 1L;
	/** Panel que contiene los paneles de los productos */
	private JPanel listaPanel = new JPanel();

	/**
	 * Cosntructor de una ventana de gestionar productos existentes 
	 */
	public VentanaGestionarExistentes() {
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

		JPanel ventana = PanelFactory.getVentanaConCabecera("Gestionar productos existentes", panelCentral);
		ventana.setOpaque(false);
		add(ventana);
		
		listaPanel.revalidate();
		listaPanel.repaint();
		
	}
	
	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
	
	public void vaciar() {
		listaPanel.removeAll();
	}

}
