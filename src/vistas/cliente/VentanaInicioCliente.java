package vistas.cliente;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.herramientas.*;
import vistas.common.*;

public class VentanaInicioCliente extends JPanel implements VentanaConDisplay<PanelProducto> {

	private static final long serialVersionUID = 1L;

	private static final double BTN_WIDTH = 0.2;
	private static final double BTN_HEIGHT = 0.5;
	private static final double SPACE_AROUND = 0.07;

	private JPanel recomendados = new JPanel();

	JButton cartera;
	JButton articulos;
	JButton compras;

	public VentanaInicioCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		ButtonFactory bf = new ButtonFactory();
		TiendaFrame t = TiendaFrame.getInstance();

		int btnW = t.getPixelsWidth(BTN_WIDTH);
		int btnH = t.getPixelsHeight(BTN_HEIGHT);
		int spaceAround = t.getPixelsHeight(SPACE_AROUND);

		recomendados.setLayout(new BoxLayout(recomendados, BoxLayout.Y_AXIS));
		recomendados.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(recomendados);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Productos recomendados", contenido));

		/* Botones de la derecha */
		cartera = bf.newRoundedButton("Ver mi cartera", btnH, btnW, 0.5);
		cartera.setActionCommand("cartera");
		articulos = bf.newRoundedButton("Buscar articulos de segunda mano", btnH, btnW, 0.5);
		articulos.setActionCommand("articulos");
		compras = bf.newRoundedButton("Ver mis compras", btnH, btnW, 0.5);
		compras.setActionCommand("compras");
		
		JPanel rightPanel = PanelFactory.getColumnaDeBotones(cartera, articulos, compras);
		rightPanel.setOpaque(false);

		this.add(BorderLayout.EAST, PanelFactory.wrapHorizontal(rightPanel, spaceAround));

		refreshList();
	}

	private void refreshList() {
		recomendados.revalidate();
		recomendados.repaint();
	}

	public void setControlador(ActionListener l) {
		for (Component c : recomendados.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}
		cartera.addActionListener(l);
		articulos.addActionListener(l);
		compras.addActionListener(l);
	}

	@Override
	public PanelProducto anadirDisplay(PanelProducto panelDisplay) {
		recomendados.add(panelDisplay);
		refreshList();

		return panelDisplay;
	}
}
