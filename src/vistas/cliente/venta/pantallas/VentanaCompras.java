package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.displays.PanelPedido;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Tipo: Class VentanaCompras.
 */
public class VentanaCompras extends JPanel implements VentanaConDisplay<PanelPedido> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo pedidos. */
	private JPanel pedidos = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaCompras.
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
	 */
	private void refreshList() {
		pedidos.revalidate();
		pedidos.repaint();
	}
	
	/**
	 * limpiarPedidos.
	 */
	public void limpiarPedidos() {
        pedidos.removeAll();
        refreshList();
    }

	/**
	 * anadirDisplay.
	 *
	 * @param <K> clave genérica
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelPedido
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
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		/* Sin acciones en esta ventana */
	}
}