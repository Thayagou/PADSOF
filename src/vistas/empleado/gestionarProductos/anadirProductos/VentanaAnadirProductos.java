package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import vistas.common.PanelDisplay;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.PanelFactory;

public class VentanaAnadirProductos extends JPanel implements VentanaConDisplay<PanelDisplay>{
	private static final long serialVersionUID = 1L;
	private JPanel listaPanel = new JPanel();
	private final VentanaAnadirProductoIndividual vista;
	
	public VentanaAnadirProductos(String[] categorias, String[] tiposProducto, String[] espComic, String[] espJuego, String[] espFigura) {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setOpaque(false);
		
		
		this.vista = new VentanaAnadirProductoIndividual(categorias, tiposProducto, espComic, espJuego, espFigura);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setOpaque(false);
		panelCentral.add(BorderLayout.NORTH, listaPanel);
		panelCentral.add(BorderLayout.CENTER, vista);

		JPanel ventana = PanelFactory.getVentanaConCabecera("Añadir nuevos productos", panelCentral);
		ventana.setOpaque(false);
		add(ventana, BorderLayout.CENTER);
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaPanel.add(panelDisplay);
		listaPanel.revalidate();
		listaPanel.repaint();
		return panelDisplay;
	}
	
	public void setControlador(ActionListener c) {
		vista.setControlador(c);
	}

}
