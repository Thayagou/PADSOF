package vistas.cliente.intercambios.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.displays.PanelArticulo;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Tipo: Class VentanaBuscarArticulos.
 */
public class VentanaBuscarArticulos extends JPanel implements VentanaConDisplay<PanelArticulo>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo articulos. */
	private JPanel articulos = new JPanel();
	
	/**
	 * Instancia un nuevo Objeto VentanaBuscarArticulos.
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
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		
	}
	
	/**
	 * refreshList.
	 */
	private void refreshList() {
		articulos.revalidate();
		articulos.repaint();
	}

	/**
	 * anadirDisplay.
	 *
	 * @param <K> clave genérica
	 * @param panelDisplay parámetro panelDisplay
	 * @return valor de tipo PanelArticulo
	 */
	@Override
	public <K extends PanelArticulo> PanelArticulo anadirDisplay(K panelDisplay) {
		articulos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
