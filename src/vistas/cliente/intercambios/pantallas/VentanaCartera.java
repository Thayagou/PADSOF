package vistas.cliente.intercambios.pantallas;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.cliente.intercambios.PanelArticuloEnCartera;
import vistas.common.*;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaCartera extends JPanel implements VentanaConDisplay<PanelArticuloEnCartera>{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel objetos = new JPanel();
	
	public VentanaCartera(String usr) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		if(usr == null) usr = "mi cartera";

		objetos.setLayout(new BoxLayout(objetos, BoxLayout.Y_AXIS));
		objetos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(objetos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Objetos de "+usr, contenido));

		refreshList();
	}
	
	private void refreshList() {
		objetos.revalidate();
		objetos.repaint();
	}

	@Override
	public <K extends PanelArticuloEnCartera> PanelArticuloEnCartera anadirDisplay(K panelDisplay) {
		objetos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}
