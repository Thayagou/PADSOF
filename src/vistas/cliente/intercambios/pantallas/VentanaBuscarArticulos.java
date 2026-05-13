package vistas.cliente.intercambios.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.displays.PanelArticulo;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Pantalla que muestra un listado de artículos de segunda mano disponibles para intercambio.
 */
public class VentanaBuscarArticulos extends JPanel implements VentanaConDisplay<PanelArticulo>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo articulos. Panel que contiene los artículos de segunda mano a mostrar. */
	private JPanel articulos = new JPanel();
	
	/**
	 * Instancia un nuevo Objeto VentanaBuscarArticulos.
	 * Construye la interfaz con el título y el área desplazable de artículos.
	 */
	public VentanaBuscarArticulos() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		articulos.setLayout(new BoxLayout(articulos, BoxLayout.Y_AXIS));
		articulos.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(articulos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Articulos de segunda mano", contenido));

		refreshList();
	}
	
	/**
	 * Establece Controlador.
	 *
	 * @param c controlador que manejará los eventos de los artículos de la ventana.
	 */
	public void setControlador(ActionListener c) {
		
	}
	
	/**
	 * refreshList.
	 * Refresca la interfaz para mostrar los cambios en el panel de artículos.
	 */
	private void refreshList() {
		articulos.revalidate();
		articulos.repaint();
	}

	/**
	 * anadirDisplay.
	 * Añade un artículo al panel de listado y refresca la vista.
	 *
	 * @param <K> subtipo de PanelArticulo del panel a añadir.
	 * @param panelDisplay Panel del artículo a añadir.
	 * @return valor de tipo PanelArticulo, el mismo panel que se añadió.
	 */
	@Override
	public <K extends PanelArticulo> PanelArticulo anadirDisplay(K panelDisplay) {
		articulos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}