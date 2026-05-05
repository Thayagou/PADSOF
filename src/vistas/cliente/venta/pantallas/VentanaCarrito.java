package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.venta.PanelItemCarrito;
import vistas.common.*;
import vistas.herramientas.*;

public class VentanaCarrito extends JPanel implements VentanaConDisplay<PanelItemCarrito> {

	private static final long serialVersionUID = 1L;

	private static final double BTN_WIDTH = 0.2;
	private static final double BTN_HEIGHT = 0.5;
	private static final double SPACE_AROUND = 0.07;

	private JPanel items = new JPanel();

	JButton pagar;
	JButton cancelar;

	public VentanaCarrito(double precio) {
		setOpaque(false);
		setLayout(new BorderLayout());

		ButtonFactory bf = new ButtonFactory();
		TiendaFrame t = TiendaFrame.getInstance();

		int btnW = t.getPixelsWidth(BTN_WIDTH);
		int btnH = t.getPixelsHeight(BTN_HEIGHT);
		int spaceAround = t.getPixelsHeight(SPACE_AROUND);

		items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
		items.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(items);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera(String.format("Carrito Coste total: %.2f", precio), contenido));

		/* Botones de la izquierda */
		pagar = bf.newRoundedButton("Finalizar y pagar", btnH, btnW, 0.5);
		pagar.setActionCommand("pagar");
		cancelar = bf.newRoundedButton("Cancelar compra", btnH, btnW, 0.5);
		cancelar.setActionCommand("cancelar");

		JPanel rightPanel = PanelFactory.getColumnaDeBotones(pagar, cancelar);
		rightPanel.setOpaque(false);

		this.add(BorderLayout.EAST, PanelFactory.wrapHorizontal(rightPanel, spaceAround));

		refreshList();
	}

	private void refreshList() {
		items.revalidate();
		items.repaint();
	}

	public void setControlador(ActionListener l) {
		for (Component c : items.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}
		pagar.addActionListener(l);
		cancelar.addActionListener(l);
	}

	@Override
	public <K extends PanelItemCarrito> PanelItemCarrito anadirDisplay(K panelDisplay) {
		items.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
