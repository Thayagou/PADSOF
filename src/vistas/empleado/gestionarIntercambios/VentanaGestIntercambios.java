package vistas.empleado.gestionarIntercambios;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelIntercambioConBoton;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa la ventana que muestra los intercambios pendientes a un empleado
 */
public class VentanaGestIntercambios extends JPanel implements VentanaConDisplay<PanelIntercambioConBoton>{
	private static final long serialVersionUID = 1L;
	/** Panel que contiene la lista de intercambios */
	private JPanel listaPanel;

	/**
	 * Constructor de la ventana de intercambios
	 */
	public VentanaGestIntercambios() {
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

		JPanel ventana = PanelFactory.getVentanaConCabecera("Intercambios pendientes de confirmar", panelCentral);
		ventana.setOpaque(false);
		
		add(ventana, BorderLayout.CENTER);
	}

	@Override
	public PanelIntercambioConBoton anadirDisplay(PanelIntercambioConBoton panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
	
	public void vaciar() {
		listaPanel.removeAll();
		listaPanel.revalidate();
		listaPanel.repaint();
	}
}
