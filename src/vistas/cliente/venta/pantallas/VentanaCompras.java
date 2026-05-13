package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.displays.PanelPedido;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Pantalla que muestra el historial de pedidos realizados por el usuario.
 */
public class VentanaCompras extends JPanel implements VentanaConDisplay<PanelPedido> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo pedidos. Panel que contiene los pedidos realizados. */
	private JPanel pedidos = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaCompras.
	 * Construye la interfaz con el título y el área desplazable de pedidos.
	 */
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

	/**
	 * refreshList.
	 * Refresca la interfaz para mostrar los cambios en el panel de pedidos.
	 */
	private void refreshList() {
		pedidos.revalidate();
		pedidos.repaint();
	}
	
	/**
	 * limpiarPedidos.
	 * Elimina todos los pedidos del panel para actualizar la vista.
	 */
	public void limpiarPedidos() {
        pedidos.removeAll();
        refreshList();
    }

	/**
	 * anadirDisplay.
	 * Añade un pedido al panel de listado y refresca la vista.
	 *
	 * @param <K> subtipo de PanelPedido del panel a añadir.
	 * @param panelDisplay Panel del pedido a añadir.
	 * @return valor de tipo PanelPedido, el mismo panel que se añadió.
	 */
	@Override
	public <K extends PanelPedido> PanelPedido anadirDisplay(K panelDisplay) {
		pedidos.add(panelDisplay);
		refreshList();
		return panelDisplay;
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c controlador que manejará los eventos de la ventana (actualmente sin acciones).
	 */
	public void setControlador(ActionListener c) {
		/* Sin acciones en esta ventana */
	}
}