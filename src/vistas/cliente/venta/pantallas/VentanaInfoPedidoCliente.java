package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.venta.PanelItemPedido;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Pantalla que muestra los detalles de un pedido realizado por el cliente, con la lista de productos adquiridos.
 */
public class VentanaInfoPedidoCliente extends JPanel implements VentanaConDisplay<PanelItemPedido> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo items. Panel que contiene los ítems del pedido. */
	private JPanel items = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInfoPedidoCliente.
	 * Construye la interfaz con el título y el área desplazable de ítems del pedido.
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
	 * Refresca la interfaz para mostrar los cambios en el panel de ítems.
	 */
	private void refreshList() {
		items.revalidate();
		items.repaint();
	}

	/**
	 * anadirDisplay.
	 * Añade un ítem al panel de detalles del pedido y refresca la vista.
	 *
	 * @param <K> subtipo de PanelItemPedido del panel a añadir.
	 * @param panelDisplay Panel del ítem a añadir.
	 * @return valor de tipo PanelItemPedido, el mismo panel que se añadió.
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
	 * @param c controlador que manejará los eventos de la ventana (actualmente sin acciones).
	 */
	public void setControlador(ActionListener c) {
		/* Sin acciones para esta ventana */
	}
}