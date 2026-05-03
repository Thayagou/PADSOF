package vistas.empleado;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import modelo.sistema.Tienda;
import vistas.common.PanelCategoria;
import vistas.common.PanelCategoriaSeleccion;
import vistas.common.PanelDisplay;
import vistas.common.PanelMultiopcion;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarCategorias extends JSplitPane implements VentanaConDisplay<PanelCategoria> {

	private static final long serialVersionUID = 1L;
	private static final String NUEVA_CATEGORIA_ACTION = "Añadir nueva categoría";
	private static double GAP_PERC = 0.05;
	
	private JPanel listaCategorias = new JPanel();

	public VentanaGestionarCategorias() {
		setLeftComponent(crearPanelNuevaCategoria());

		setRightComponent(crearPanelCategorias());
	}

	private JPanel crearPanelNuevaCategoria() {
		
		ButtonFactory f = new ButtonFactory();
		JPanel panelNuevaCategoria = new JPanel();
		panelNuevaCategoria.setLayout(new BoxLayout(panelNuevaCategoria, BoxLayout.Y_AXIS));
		
		JButton nuevoEmpleadoButton = f.newRoundedButton(NUEVA_CATEGORIA_ACTION, 700, 300, 0.5f);
		f.paintButton(nuevoEmpleadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(nuevoEmpleadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		panelNuevaCategoria.add(Box.createVerticalStrut(TiendaFrame.getInstance().getPixelsHeight(GAP_PERC)));
		panelNuevaCategoria.add(nuevoEmpleadoButton);
		
		JPanel cabeceraNuevaCategoría = PanelFactory.getVentanaConCabecera("Nueva categoría  ", panelNuevaCategoria);

		return cabeceraNuevaCategoría;

	}

	private JPanel crearPanelCategorias() {
		listaCategorias.setLayout(new BoxLayout(listaCategorias, BoxLayout.Y_AXIS));
		listaCategorias.setBackground(ColorPalette.CARD_LIGHT.getColor());
		// listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaCategorias);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);
		
		

		return PanelFactory.getVentanaConCabecera("     Categorías", contenido);

	}
	
	
	@Override
	public <K extends PanelCategoria> PanelCategoria anadirDisplay(K panelDisplay) {
		listaCategorias.add(panelDisplay);
		listaCategorias.revalidate();
		listaCategorias.repaint();
		
		return panelDisplay;
	}
}
