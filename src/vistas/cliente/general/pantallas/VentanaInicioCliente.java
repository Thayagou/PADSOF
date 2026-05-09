package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.herramientas.*;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;

/**
 * Tipo: Class VentanaInicioCliente.
 */
public class VentanaInicioCliente extends JPanel implements VentanaConDisplay<PanelProducto> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo recomendados. */
	private JPanel recomendados = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInicioCliente.
	 */
	public VentanaInicioCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		recomendados.setLayout(new BoxLayout(recomendados, BoxLayout.Y_AXIS));
		recomendados.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(recomendados);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Productos recomendados", contenido));

		refreshList();
	}

	/**
	 * refreshList.
	 */
	private void refreshList() {
		recomendados.revalidate();
		recomendados.repaint();
	}

	/**
	 * Establece Controlador.
	 *
	 * @param l nuevo valor
	 */
	public void setControlador(ActionListener l) {

	}

	/**
	 * anadirDisplay.
	 *
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelProducto
	 */
	@Override
	public PanelProducto anadirDisplay(PanelProducto panelDisplay) {
		recomendados.add(panelDisplay);
		refreshList();

		return panelDisplay;
	}
}
