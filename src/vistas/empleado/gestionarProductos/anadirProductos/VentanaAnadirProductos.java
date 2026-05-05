package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelDisplay;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

public class VentanaAnadirProductos extends JPanel implements VentanaConDisplay<PanelDisplay>{
	private static final long serialVersionUID = 1L;
	private JPanel listaPanel = new JPanel();
	
	public VentanaAnadirProductos() {
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

		JPanel ventana = PanelFactory.getVentanaConCabecera("Añadir nuevos productos", panelCentral);
		ventana.setOpaque(false);
		add(ventana, BorderLayout.CENTER);
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}

}
