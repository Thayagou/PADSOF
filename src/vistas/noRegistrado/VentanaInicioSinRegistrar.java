package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import modelo.venta.productos.Producto;
import vistas.common.PanelCategoriaSeleccion;
import vistas.common.PanelDisplay;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Pantalla de inicio para usuarios no registrados. Muestra una lista de
 * "Productos populares" (los mejor valorados).
 */
public class VentanaInicioSinRegistrar extends JPanel implements VentanaConDisplay<PanelDisplay>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo listaPanel. */
	private JPanel listaPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInicioSinRegistrar.
	 *
	 * @param populares parámetro populares
	 */
	public VentanaInicioSinRegistrar(Producto[] populares) {
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
		/*for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}*/
	}

	@Override
	public PanelDisplay anadirDisplay(PanelDisplay panelDisplay) {
		listaPanel.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}