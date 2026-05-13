package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import controladores.TiendaFrame;
import vistas.cliente.venta.PanelItemCarrito;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Pantalla que muestra los productos añadidos al carrito de compras con el precio total y botones de acción.
 */
public class VentanaCarrito extends JPanel implements VentanaConDisplay<PanelItemCarrito> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante BTN_WIDTH. Anchura de los botones de acción como porcentaje de la pantalla. */
	private static final double BTN_WIDTH = 0.2;
	
	/** Constante BTN_HEIGHT. Altura de los botones de acción como porcentaje de la pantalla. */
	private static final double BTN_HEIGHT = 0.5;
	
	/** Constante SPACE_AROUND. Espacio alrededor de los botones como porcentaje de la pantalla. */
	private static final double SPACE_AROUND = 0.07;
	
	/** Constante PAY_ACTION. Comando de acción para el botón de pagar. */
	public static final String PAY_ACTION = "Pagar";
	
	/** Constante CANCEL_ACTION. Comando de acción para el botón de cancelar compra. */
	public static final String CANCEL_ACTION = "Cancelar";

	/** Campo items. Panel que contiene los ítems del carrito. */
	private JPanel items = new JPanel();

	/** Campo pagar. Botón para finalizar la compra. */
	JButton pagar;
	
	/** Campo cancelar. Botón para cancelar la compra. */
	JButton cancelar;

	/**
	 * Instancia un nuevo Objeto VentanaCarrito.
	 * Construye la interfaz con la lista de productos y el panel lateral con botones.
	 *
	 * @param precio Coste total del carrito a mostrar en la cabecera.
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
	 * Refresca la interfaz para mostrar los cambios en el panel de ítems.
	 */
	private void refreshList() {
		items.revalidate();
		items.repaint();
	}

	/**
	 * Establece Controlador.
	 *
	 * @param l controlador que manejará los eventos de los botones de pago y cancelación.
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
	 * Elimina un ítem específico del carrito y refresca la vista.
	 *
	 * @param panel Panel del ítem a eliminar del carrito.
	 */
	public void quitarDisplay(PanelItemCarrito panel) {
		if(panel != null) {
			items.remove(panel);
		}
		refreshList();
	}

	/**
	 * anadirDisplay.
	 * Añade un ítem al carrito y refresca la vista.
	 *
	 * @param <K> subtipo de PanelItemCarrito del panel a añadir.
	 * @param panelDisplay Panel del ítem a añadir.
	 * @return valor de tipo PanelItemCarrito, el mismo panel que se añadió.
	 */
	@Override
	public <K extends PanelItemCarrito> PanelItemCarrito anadirDisplay(K panelDisplay) {
		items.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}