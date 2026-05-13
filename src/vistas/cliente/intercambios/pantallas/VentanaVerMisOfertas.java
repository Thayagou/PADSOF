package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.cliente.intercambios.PanelOferta;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Pantalla que muestra el listado de ofertas de intercambio realizadas o recibidas por el usuario.
 */
public class VentanaVerMisOfertas extends JPanel implements VentanaConDisplay<PanelOferta>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo intercambios. Panel que contiene los paneles de oferta a mostrar. */
	JPanel intercambios;
	
	/**
	 * Instancia un nuevo Objeto VentanaVerMisOfertas.
	 * Construye la interfaz con el título especificado y el área desplazable de ofertas.
	 * 
	 * @param cabecera Titulo de la ventana que se muestra en la cabecera.
	 */
	public VentanaVerMisOfertas(String cabecera) {
		setLayout(new BorderLayout());
		
		intercambios = new JPanel();
		intercambios.setLayout(new BoxLayout(intercambios, BoxLayout.Y_AXIS));
		intercambios.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(intercambios);
		JPanel scrollPanel = new JPanel(new BorderLayout());
		scrollPanel.add(scroll);
		
		add(PanelFactory.getVentanaConCabecera(cabecera, scrollPanel));
		refreshList();
	}
	
	/**
	 * refreshList.
	 * Refresca la interfaz para mostrar los cambios en el panel de ofertas.
	 */
	public void refreshList() {
		intercambios.revalidate();
		intercambios.repaint();
	}
	
	/**
	 * Establece Controlador.
	 *
	 * @param c controlador que manejará los eventos de la ventana (actualmente sin acciones).
	 */
	public void setControlador(ActionListener c) {
		/* sin acciones para esta ventana */
	}

	/**
	 * anadirDisplay.
	 * Añade una oferta al panel de listado y refresca la vista.
	 *
	 * @param <K> subtipo de PanelOferta del panel a añadir.
	 * @param panelDisplay Panel de la oferta a añadir.
	 * @return valor de tipo PanelOferta, el mismo panel que se añadió.
	 */
	@Override
	public <K extends PanelOferta> PanelOferta anadirDisplay(K panelDisplay) {
		intercambios.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}