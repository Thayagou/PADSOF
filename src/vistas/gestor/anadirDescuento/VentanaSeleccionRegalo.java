package vistas.gestor.anadirDescuento;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Subclase de JPanel que se usa para mostrar por pantalla la ventana de selección del regalo para el descuento a añadir.
 */
public class VentanaSeleccionRegalo extends JPanel implements VentanaConDisplay<PanelProducto>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaRegalos = new JPanel();
	
	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella 
	 */
	public VentanaSeleccionRegalo() {
		setOpaque(false);
		setLayout(new BorderLayout());
		listaRegalos.setLayout(new BoxLayout(listaRegalos, BoxLayout.Y_AXIS));
		listaRegalos.setBackground(ColorPalette.CARD_LIGHT.getColor());
		// listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaRegalos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		add(PanelFactory.getVentanaConCabecera("Seleccionar regalo", contenido));

	}

	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaRegalos.add(panelDisplay);
		revalidate();
		repaint();
		
		return panelDisplay;
	}
}
