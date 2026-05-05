package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.venta.PanelItemPedido;
import vistas.common.*;
import vistas.herramientas.*;

public class VentanaInfoPedidoCliente extends JPanel implements VentanaConDisplay<PanelItemPedido> {

	private static final long serialVersionUID = 1L;

	private JPanel items = new JPanel();

	public VentanaInfoPedidoCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
		items.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(items);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Detalle del pedido", contenido));

		refreshList();
	}

	private void refreshList() {
		items.revalidate();
		items.repaint();
	}

	@Override
	public <K extends PanelItemPedido> PanelItemPedido anadirDisplay(K panelDisplay) {
		items.add(panelDisplay);
		refreshList();
		return panelDisplay;
	}

	public void setControlador(ActionListener c) {
		// Reservado para acciones globales futuras (ej. botón "Volver")
	}
}
