package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import vistas.common.displays.PanelDisplay;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa una ventana para añadir productos tanto mediante un fichero como explícitamente
 */
public class VentanaAnadirProductos extends JPanel implements VentanaConDisplay<PanelDisplay>{
	private static final long serialVersionUID = 1L;
	/** Panel que contiene ambos paneles de añadir objetos */
	private JPanel listaPanel = new JPanel();
	/** Ventana de añadir objeto individual */
	private final VentanaAnadirProductoIndividual vista;
	
	/**
	 * Constructor de la ventana de añadir productos
	 * @param categorias Categorías de la tienda
	 * @param tiposProducto Tipos de productos
	 * @param espComic Características específicas de Comic
	 * @param espJuego Características específicas de Juego
	 * @param espFigura Características específicas de Figura
	 * @param espPack Características específicas de Pack
	 * @param tiposJuego Tipos de juego
	 * @param productos Paneles con productos de la tienda
	 */
	public VentanaAnadirProductos(String[] categorias, String[] tiposProducto, String[] espComic, String[] espJuego, String[] espFigura, String[] espPack, String[] tiposJuego, PanelProducto[] productos) {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setOpaque(false);
		
		
		this.vista = new VentanaAnadirProductoIndividual(categorias, tiposProducto, espComic, espJuego, espFigura, espPack, tiposJuego, productos);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setOpaque(false);
		panelCentral.add(BorderLayout.NORTH, listaPanel);
		panelCentral.add(BorderLayout.CENTER, vista);

		JPanel ventana = PanelFactory.getVentanaConCabecera("Añadir nuevos productos", panelCentral);
		ventana.setOpaque(false);
		add(ventana, BorderLayout.CENTER);
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
	
	/**
	 * Asigna un controlador a los componentes de la ventana
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		vista.setControlador(c);
	}
	
	/**
	 * Devuelve la ventana que muestra por pantalla
	 * @return Ventana que muestra
	 */
	public VentanaAnadirProductoIndividual getVentanaIndividual() {
		return vista;
	}

}
