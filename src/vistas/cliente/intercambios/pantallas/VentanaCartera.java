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

public class VentanaCartera extends JPanel implements VentanaConDisplay<PanelArticulo>{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel objetos = new JPanel();
	private JButton hacerOferta;
	
	private final double BTN_WIDTH = 0.1;
	private final double BTN_HEIGHT = 0.06;
	
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
		hacerOferta.setActionCommand("Hacer oferta");
		if(ajeno) {
			JPanel panelBtn = new JPanel(new BorderLayout());
			panelBtn.add(hacerOferta, BorderLayout.CENTER);
			this.add(panelBtn, BorderLayout.SOUTH);
		}

		refreshList();
	}
	
	public void limpiarDisplays() {
		objetos.removeAll();
		refreshList();
		revalidate();
		repaint();
	}
	
	public void setControlador(ActionListener c) {
		hacerOferta.addActionListener(c);
	}
	
	private void refreshList() {
		objetos.revalidate();
		objetos.repaint();
	}

	@Override
	public <K extends PanelArticulo> PanelArticulo anadirDisplay(K panelDisplay) {
		objetos.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}
}
