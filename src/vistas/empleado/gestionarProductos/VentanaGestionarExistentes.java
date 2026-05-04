package vistas.empleado.gestionarProductos;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarExistentes extends JSplitPane implements VentanaConDisplay<PanelProducto> {

	private static final long serialVersionUID = 1L;
	private static final String NUEVA_CATEGORIA_ACTION = "Añadir nuevo producto";
	private static double GAP_PERC = 0.05;
	
	private JPanel listaProductos = new JPanel();

	public VentanaGestionarExistentes() {
		setLeftComponent(crearPanelNuevoProducto());

		setRightComponent(crearPanelProductos());
	}

	private JPanel crearPanelNuevoProducto() {
		JPanel panelNuevaCategoria = new JPanel();
		panelNuevaCategoria.setLayout(new BoxLayout(panelNuevaCategoria, BoxLayout.Y_AXIS));
		
		JButton nuevoEmpleadoButton = ButtonFactory.newRoundedButton(NUEVA_CATEGORIA_ACTION, 700, 300, 0.5f);
		ButtonFactory.paintButton(nuevoEmpleadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(nuevoEmpleadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		panelNuevaCategoria.add(Box.createVerticalStrut(TiendaFrame.getInstance().getPixelsHeight(GAP_PERC)));
		panelNuevaCategoria.add(nuevoEmpleadoButton);
		
		JPanel cabeceraNuevaCategoría = PanelFactory.getVentanaConCabecera("Nuevo producto  ", panelNuevaCategoria);

		return cabeceraNuevaCategoría;

	}

	private JPanel crearPanelProductos() {
		listaProductos.setLayout(new BoxLayout(listaProductos, BoxLayout.Y_AXIS));
		listaProductos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaProductos);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);
		
		return PanelFactory.getVentanaConCabecera("     Productos", contenido);

	}
	
	
	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaProductos.add(panelDisplay);
		listaProductos.revalidate();
		listaProductos.repaint();
		return panelDisplay;
	}

}
