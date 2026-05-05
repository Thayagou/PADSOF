package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import vistas.common.PanelMultiopcion;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasProductos extends JPanel implements VentanaConDisplay<PanelProducto> {
	private static final long serialVersionUID = 1L;
	public static String MAYOR_RECAUDACION = "Mayor recaudación";
	public static String MENOR_RECAUDACION = "Menor recaudación";
	public static String MAS_UNIDADES = "Más unidades vendidas";
	public static String MENOS_UNIDADES = "Menos unidades vendidas";

	public static String[] ORDENES = { MAYOR_RECAUDACION, MENOR_RECAUDACION, MAS_UNIDADES, MENOS_UNIDADES };
	private static double MAX_HEIGHT_CABECERA = 0.05;
	private JPanel listaProductos;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelProducto> listaPaneles = new ArrayList<>();

	public VentanaEstadisticasProductos() {
		setOpaque(false);
		setLayout(new BorderLayout(30, 0));

		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);

		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());
		//cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);

		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Total recaudado", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Unidades vendidas", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Porcentaje de recaudación", size, ColorPalette.WHITE));
		//statsPanel.setMaximumSize(new Dimension(3*maxWidth, maxHeight));
		

		// Lista
		listaProductos = new JPanel();
		listaProductos.setLayout(new BoxLayout(listaProductos, BoxLayout.Y_AXIS));
		listaProductos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaProductos);
		//JPanel panelScroll = new JPanel();
		//panelScroll.add(scroll, BorderLayout.CENTER);
		JPanel wrapper = new JPanel();
		wrapper.setOpaque(false);
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
		wrapper.add(statsPanel);
		wrapper.add(Box.createHorizontalStrut(3*UIManager.getInt("ScrollBar.width")));
		
		cabecera.add(wrapper, BorderLayout.EAST);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);

		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}

	public void refrescarLista() {
		listaProductos.removeAll();

		for (PanelProducto panel : listaPaneles) {
			listaProductos.add(panel);
		}

		revalidate();
		repaint();
	}

	public List<PanelProducto> getListaPaneles() {
		return listaPaneles;
	}

	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}

	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaProductos.add(panelDisplay);
		return panelDisplay;
	}

}
