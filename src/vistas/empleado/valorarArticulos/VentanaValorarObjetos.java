package vistas.empleado.valorarArticulos;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa una ventana con artículos para valorar
 */
public class VentanaValorarObjetos extends JPanel implements VentanaConDisplay<PanelArticuloPendienteValoracion> {
	private static final long serialVersionUID = 1L;
	/** Panel con la lista de las valoraciones pendientes */
	private JPanel listaPanel;

	/**
	 * Constructor de la ventana de valorar artículos
	 */
	public VentanaValorarObjetos() {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setOpaque(false);
		
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setOpaque(false);
		panelCentral.add(BorderLayout.CENTER, scroll);
		
		JPanel ventana = PanelFactory.getVentanaConCabecera("Artículos pendientes de valorar", panelCentral);
		ventana.setOpaque(false);
		add(ventana, BorderLayout.CENTER);
	}

	@Override
	public PanelArticuloPendienteValoracion anadirDisplay(PanelArticuloPendienteValoracion panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
	
	public void vaciar() {
		listaPanel.removeAll();
		listaPanel.repaint();
		listaPanel.revalidate();
	}
}
