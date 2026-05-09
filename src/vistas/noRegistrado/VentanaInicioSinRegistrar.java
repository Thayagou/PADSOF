package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Pantalla de inicio para usuarios no registrados. Muestra una lista de
 * "Productos populares" (los mejor valorados).
 */
public class VentanaInicioSinRegistrar extends JPanel implements VentanaConDisplay<PanelProducto>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo listaPanel. */
	private JPanel listaPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInicioSinRegistrar.
	 */
	public VentanaInicioSinRegistrar() {
		setOpaque(false);
		setLayout(new BorderLayout());

		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		
		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(PanelFactory.getVentanaConCabecera("      Productos populares", contenido));
		
		refreshList();
	}
	
	/**
	 * refreshList.
	 */
	private void refreshList() {
		listaPanel.revalidate();
		listaPanel.repaint();
	}

	/**
	 * Listener disparado al hacer clic en un producto de la lista. ActionCommand:
	 * "Ver producto:<nombreProducto>"
	 *
	 * @param l nuevo valor
	 */
	public void setControlador(ActionListener l) {
		for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}
	}

	/**
	 * anadirDisplay.
	 *
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelProducto
	 */
	@Override
	public PanelProducto anadirDisplay(PanelProducto panelDisplay) {
		listaPanel.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}