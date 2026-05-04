package vistas.empleado.gestionarProductos;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import vistas.common.PanelCategoria;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarCategorias extends JSplitPane implements VentanaConDisplay<PanelCategoria> {

	private static final long serialVersionUID = 1L;
	
	private JPanel listaPanel = new JPanel();

	public VentanaGestionarCategorias() {
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

		JPanel ventana = PanelFactory.getVentanaConCabecera("Gestionar categorías existentes", panelCentral);
		ventana.setOpaque(false);
		add(ventana, BorderLayout.CENTER);
	}
	
	@Override
	public PanelCategoria anadirDisplay(PanelCategoria panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
}
