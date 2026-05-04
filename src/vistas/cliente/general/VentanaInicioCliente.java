package vistas.cliente.general;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.herramientas.*;
import vistas.common.*;

public class VentanaInicioCliente extends JPanel implements VentanaConDisplay<PanelProducto> {

	private static final long serialVersionUID = 1L;

	private JPanel recomendados = new JPanel();

	public VentanaInicioCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		recomendados.setLayout(new BoxLayout(recomendados, BoxLayout.Y_AXIS));
		recomendados.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(recomendados);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Productos recomendados", contenido));

		refreshList();
	}

	private void refreshList() {
		recomendados.revalidate();
		recomendados.repaint();
	}

	public void setControlador(ActionListener l) {

	}

	@Override
	public PanelProducto anadirDisplay(PanelProducto panelDisplay) {
		recomendados.add(panelDisplay);
		refreshList();

		return panelDisplay;
	}
}
