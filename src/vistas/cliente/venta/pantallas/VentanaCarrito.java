package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.venta.PanelItemCarrito;
import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Tipo: Class VentanaCarrito.
 */
public class VentanaCarrito extends JPanel implements VentanaConDisplay<PanelItemCarrito> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante BTN_WIDTH. */
	private static final double BTN_WIDTH = 0.2;
	
	/** Constante BTN_HEIGHT. */
	private static final double BTN_HEIGHT = 0.5;
	
	/** Constante SPACE_AROUND. */
	private static final double SPACE_AROUND = 0.07;
	
	/** Constante PAY_ACTION. */
	public static final String PAY_ACTION = "Pagar";
	
	/** Constante CANCEL_ACTION. */
	public static final String CANCEL_ACTION = "Cancelar";

	/** Campo items. */
	private JPanel items = new JPanel();

	/** Campo pagar. */
	JButton pagar;
	
	/** Campo cancelar. */
	JButton cancelar;

	/**
	 * Instancia un nuevo Objeto VentanaCarrito.
	 *
	 * @param precio parámetro precio
	 */
	public VentanaCarrito(double precio) {
		setOpaque(false);
		setLayout(new BorderLayout());

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
		pagar = ButtonFactory.newRoundedButton("Finalizar y pagar", btnH, btnW, 0.5);
		pagar.setActionCommand(PAY_ACTION);
		cancelar = ButtonFactory.newRoundedButton("Cancelar compra", btnH, btnW, 0.5);
		cancelar.setActionCommand(CANCEL_ACTION);

		JPanel rightPanel = PanelFactory.getColumnaDeBotones(pagar, cancelar);
		rightPanel.setOpaque(false);

		this.add(BorderLayout.EAST, PanelFactory.wrapHorizontal(rightPanel, spaceAround));

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
	 * Establece Controlador.
	 *
	 * @param l nuevo valor
	 */
	public void setControlador(ActionListener l) {
		for (Component c : items.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}
		pagar.addActionListener(l);
		cancelar.addActionListener(l);
	}
	
	/**
	 * quitarDisplay.
	 *
	 * @param panel parámetro panel
	 */
	public void quitarDisplay(PanelItemCarrito panel) {
		if(panel != null) {
			items.remove(panel);
		}
		refreshList();
	}

	/**
	 * anadirDisplay.
	 *
	 * @param <K> clave genérica
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelItemCarrito
	 */
	@Override
	public <K extends PanelItemCarrito> PanelItemCarrito anadirDisplay(K panelDisplay) {
		items.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
