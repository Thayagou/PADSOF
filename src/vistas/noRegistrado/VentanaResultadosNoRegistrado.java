package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

/**
 * Muestra los resultados de una búsqueda en forma de lista scrolleable. Incluye
 * combo de ordenación (igual que maqueta 4).
 */
public class VentanaResultadosNoRegistrado extends JPanel implements VentanaConDisplay<PanelProducto>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo listaPanel. */
	protected JPanel listaPanel;
	
	/** Campo ordenCombo. */
	protected PanelMultiopcion panelOpciones;
	
	/** Constante ORDENES. */
	protected static final String[] ORDENES = { "Mejor valorados", "Peor valorados", "Precio: menor a mayor",
			"Precio: mayor a menor", "Nombre A-Z", "Nombre Z-A" };

	/**
	 * Instancia un nuevo Objeto VentanaResultadosNoRegistrado.
	 */
	public VentanaResultadosNoRegistrado() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scroll);

		panelOpciones = new PanelMultiopcion("Resultados de búsqueda", contenido, ORDENES);
		
		add(panelOpciones);
		
		refrescarLista();
	}
	
	/**
	 * Devuelve la opción seleccionada en el panel de opciones
	 * @return La opción seleccionada
	 */
	public int getOpcionSeleccionada() {
		return panelOpciones.getOpcionSeleccionada();
	}

	/**
	 * refrescarLista.
	 */
	public void vaciarLista() {
		listaPanel.removeAll();
		refrescarLista();
	}
	
	private void refrescarLista() {
		listaPanel.revalidate();
		listaPanel.repaint();
	}

	/**
	 * Listener que se llama al hacer clic en una fila de producto.
	 *
	 * @param l nuevo valor
	 */
	public void setControlador(ActionListener l) {
		panelOpciones.setControlador(l);
	}

	/**
	 * anadirDisplay.
	 *
	 * @param <K> clave genérica
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelProducto
	 */
	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		refrescarLista();
		
		return panelDisplay;
	}
}
