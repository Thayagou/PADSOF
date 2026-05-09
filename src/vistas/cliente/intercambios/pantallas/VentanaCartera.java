package vistas.cliente.intercambios.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelArticulo;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Tipo: Class VentanaCartera.
 */
public class VentanaCartera extends JPanel implements VentanaConDisplay<PanelArticulo>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo objetos. */
	private JPanel objetos = new JPanel();
	
	/** Campo hacerOferta. */
	private JButton hacerOferta;
	
	/** Campo BTN_WIDTH. */
	private final double BTN_WIDTH = 0.1;
	
	/** Campo BTN_HEIGHT. */
	private final double BTN_HEIGHT = 0.06;
	
	/** Constante OFFER_ACTION. */
	public static final String OFFER_ACTION = "Hacer oferta";
	
	/**
	 * Instancia un nuevo Objeto VentanaCartera.
	 *
	 * @param usr parámetro usr
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
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		hacerOferta.addActionListener(c);
	}
	
	/**
	 * refreshList.
	 */
	private void refreshList() {
		objetos.revalidate();
		objetos.repaint();
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
		objetos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}
