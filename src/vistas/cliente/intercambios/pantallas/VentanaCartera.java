package vistas.cliente.intercambios.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controladores.TiendaFrame;
import vistas.common.displays.PanelArticulo;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Pantalla que muestra los artículos de segunda mano que posee un usuario, permitiendo hacer ofertas desde carteras ajenas.
 */
public class VentanaCartera extends JPanel implements VentanaConDisplay<PanelArticulo>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo objetos. Panel que contiene los artículos de segunda mano del usuario. */
	private JPanel objetos = new JPanel();
	
	/** Campo hacerOferta. Botón para iniciar una oferta desde una cartera ajena. */
	private JButton hacerOferta;
	
	/** Campo BTN_WIDTH. Anchura del botón de oferta como porcentaje de la pantalla. */
	private final double BTN_WIDTH = 0.1;
	
	/** Campo BTN_HEIGHT. Altura del botón de oferta como porcentaje de la pantalla. */
	private final double BTN_HEIGHT = 0.06;
	
	/** Constante OFFER_ACTION. Comando de acción para el botón de hacer oferta. */
	public static final String OFFER_ACTION = "Hacer oferta";
	
	/**
	 * Instancia un nuevo Objeto VentanaCartera.
	 * Construye la interfaz con la lista de artículos, mostrando el botón de oferta solo si se visualiza la cartera de otro usuario.
	 *
	 * @param usr Nombre del propietario de la cartera, o null si es la cartera del propio usuario.
	 */
	public VentanaCartera(String usr) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		TiendaFrame t = TiendaFrame.getInstance();
		
		boolean ajeno = true;
		if(usr == null) {
			ajeno = false;
			usr = "mi cartera";
		}

		objetos.setLayout(new BoxLayout(objetos, BoxLayout.Y_AXIS));
		objetos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(objetos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		this.add(BorderLayout.CENTER, PanelFactory.getVentanaConCabecera("      Objetos de "+usr, contenido));
		
		hacerOferta = ButtonFactory.newRoundedButton("Hacer oferta", t.getPixelsHeight(BTN_HEIGHT), t.getPixelsWidth(BTN_WIDTH), 1);
		hacerOferta.setActionCommand(OFFER_ACTION);
		if(ajeno) {
			JPanel panelBtn = new JPanel(new BorderLayout());
			panelBtn.add(hacerOferta, BorderLayout.CENTER);
			this.add(panelBtn, BorderLayout.SOUTH);
		}

		refreshList();
	}
	
	/**
	 * limpiarDisplays.
	 * Elimina todos los artículos del panel de objetos para actualizar la vista.
	 */
	public void limpiarDisplays() {
		objetos.removeAll();
		refreshList();
		revalidate();
		repaint();
	}
	
	/**
	 * Establece Controlador.
	 *
	 * @param c controlador que manejará los eventos del botón de hacer oferta.
	 */
	public void setControlador(ActionListener c) {
		hacerOferta.addActionListener(c);
	}
	
	/**
	 * refreshList.
	 * Refresca la interfaz para mostrar los cambios en el panel de objetos.
	 */
	private void refreshList() {
		objetos.revalidate();
		objetos.repaint();
	}

	/**
	 * anadirDisplay.
	 * Añade un artículo al panel de objetos y refresca la vista.
	 *
	 * @param <K> subtipo de PanelArticulo del panel a añadir.
	 * @param panelDisplay Panel del artículo a añadir.
	 * @return valor de tipo PanelArticulo, el mismo panel que se añadió.
	 */
	@Override
	public <K extends PanelArticulo> PanelArticulo anadirDisplay(K panelDisplay) {
		objetos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}