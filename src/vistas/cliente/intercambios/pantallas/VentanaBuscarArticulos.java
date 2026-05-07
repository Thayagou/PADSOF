package vistas.cliente.intercambios.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.*;
import vistas.common.displays.PanelArticulo;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaBuscarArticulos extends JPanel implements VentanaConDisplay<PanelArticulo>{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel articulos = new JPanel();
	
	public VentanaBuscarArticulos() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		articulos.setLayout(new BoxLayout(articulos, BoxLayout.Y_AXIS));
		articulos.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(articulos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Articulos de segunda mano", contenido));

		refreshList();
	}
	
	public void setControlador(ActionListener c) {
		
	}
	
	private void refreshList() {
		articulos.revalidate();
		articulos.repaint();
	}

	@Override
	public <K extends PanelArticulo> PanelArticulo anadirDisplay(K panelDisplay) {
		articulos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
