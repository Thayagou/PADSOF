package vistas.gestor.anadirDescuento;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaSeleccionRegalo extends JPanel implements VentanaConDisplay<PanelProducto>{
	private static final long serialVersionUID = 1L;
	private JPanel listaRegalos = new JPanel();
	
	public VentanaSeleccionRegalo() {
		setOpaque(false);
		setLayout(new BorderLayout());
		listaRegalos.setLayout(new BoxLayout(listaRegalos, BoxLayout.Y_AXIS));
		listaRegalos.setBackground(ColorPalette.CARD_LIGHT.getColor());
		// listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaRegalos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		add(PanelFactory.getVentanaConCabecera("Seleccionar regalo", contenido));

	}

	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaRegalos.add(panelDisplay);
		revalidate();
		repaint();
		
		return panelDisplay;
	}
}
