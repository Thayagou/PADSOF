package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.intercambios.PanelOferta;
import vistas.common.*;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaVerMisOfertas extends JPanel implements VentanaConDisplay<PanelOferta>{
	
	private static final long serialVersionUID = 1L;
	
	JPanel intercambios;
	
	public VentanaVerMisOfertas() {
		setLayout(new BorderLayout());
		
		intercambios = new JPanel();
		intercambios.setLayout(new BoxLayout(intercambios, BoxLayout.Y_AXIS));
		intercambios.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(intercambios);
		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.add(scroll);
		
		add(PanelFactory.getVentanaConCabecera("Mis ofertas de intercambio", scrollPanel));
		refreshList();
	}
	
	private void refreshList() {
		intercambios.revalidate();
		intercambios.repaint();
	}
	
	public void setControlador(ActionListener c) {
		/* sin acciones para esta ventana */
	}

	@Override
	public <K extends PanelOferta> PanelOferta anadirDisplay(K panelDisplay) {
		intercambios.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
