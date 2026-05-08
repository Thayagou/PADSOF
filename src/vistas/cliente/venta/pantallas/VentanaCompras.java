package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.displays.PanelPedido;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

public class VentanaCompras extends JPanel implements VentanaConDisplay<PanelPedido> {

	private static final long serialVersionUID = 1L;

	private JPanel pedidos = new JPanel();

	public VentanaCompras() {
		setOpaque(false);
		setLayout(new BorderLayout());

		pedidos.setLayout(new BoxLayout(pedidos, BoxLayout.Y_AXIS));
		pedidos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(pedidos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Mis compras", contenido));

		refreshList();
	}

	private void refreshList() {
		pedidos.revalidate();
		pedidos.repaint();
	}
	
	public void limpiarPedidos() {
        pedidos.removeAll();
        refreshList();
    }

	@Override
	public <K extends PanelPedido> PanelPedido anadirDisplay(K panelDisplay) {
		pedidos.add(panelDisplay);
		refreshList();
		return panelDisplay;
	}

	public void setControlador(ActionListener c) {
		// Reservado para acciones globales de la ventana si se añaden en el futuro
	}
}