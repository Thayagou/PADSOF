package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.herramientas.*;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;

/**
 * Pantalla de inicio para clientes, muestra una lista de productos recomendados.
 */
public class VentanaInicioCliente extends JPanel implements VentanaConDisplay<PanelProducto> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo recomendados. Panel que contiene los productos recomendados a mostrar. */
	private JPanel recomendados = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInicioCliente.
	 * Construye la interfaz con el título y el área desplazable de productos.
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
	 * Refresca la interfaz para mostrar los cambios en el panel de recomendados.
	 */
	private void refreshList() {
		recomendados.revalidate();
		recomendados.repaint();
	}

	/**
	 * Establece Controlador.
	 *
	 * @param l controlador que manejará los eventos de la ventana.
	 */
	public void setControlador(ActionListener l) {

	}

	/**
	 * anadirDisplay.
	 * Añade un producto al panel de recomendados y refresca la vista.
	 *
	 * @param panelDisplay Panel del producto a añadir.
	 * @return valor de tipo PanelProducto, el mismo panel que se añadió.
	 */
	@Override
	public PanelProducto anadirDisplay(PanelProducto panelDisplay) {
		recomendados.add(panelDisplay);
		refreshList();

		return panelDisplay;
	}
}