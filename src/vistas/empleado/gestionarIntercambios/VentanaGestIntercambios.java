package vistas.empleado.gestionarIntercambios;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelIntercambioConBoton;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

public class VentanaGestIntercambios extends JPanel implements VentanaConDisplay<PanelIntercambioConBoton>{
	private static final long serialVersionUID = 1L;
	private JPanel listaPanel;

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
}
