package vistas.empleado.gestionarPedidos;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa la ventana donde un empleado gestiona los pedidos
 */
public class VentanaGestPedidos extends JPanel implements VentanaConDisplay<PanelPedidoGestionarPedido>{
	private static final long serialVersionUID = 1L;
	/** Panel con la lista de pedidos */
	private JPanel listaPanel;

	/**
	 * Constructor de la ventana de gestión de pedidos
	 */
	public VentanaGestPedidos() {
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

		JPanel ventana = PanelFactory.getVentanaConCabecera("Pedidos pendientes de avanzar", panelCentral);
		ventana.setOpaque(false);
		add(ventana, BorderLayout.CENTER);
	}

	@Override
	public PanelPedidoGestionarPedido anadirDisplay(PanelPedidoGestionarPedido panelDisplay) {
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
