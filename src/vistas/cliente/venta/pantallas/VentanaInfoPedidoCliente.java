package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.venta.PanelItemPedido;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Tipo: Class VentanaInfoPedidoCliente.
 */
public class VentanaInfoPedidoCliente extends JPanel implements VentanaConDisplay<PanelItemPedido> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo items. */
	private JPanel items = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInfoPedidoCliente.
	 */
	public VentanaInfoPedidoCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
		items.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(items);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Detalles del pedido", contenido));

		refreshList();
	}

	/**
	 * refreshList.
	 */
	private void refreshList() {
		items.revalidate();
		items.repaint();
	}

	/**
	 * anadirDisplay.
	 *
	 * @param <K> clave genérica
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelItemPedido
	 */
	@Override
	public <K extends PanelItemPedido> PanelItemPedido anadirDisplay(K panelDisplay) {
		items.add(panelDisplay);
		refreshList();
		return panelDisplay;
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		/* Sin acciones para esta ventana */
	}
}
