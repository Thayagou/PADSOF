package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.PanelMultiopcion;
import vistas.common.TiendaFrame;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasTienda extends JPanel implements VentanaConDisplay<PanelEstadisticasTienda>{
	private static final long serialVersionUID = 1L;
	public static String MAYOR_RECAUDACION = "Mayor recaudación";
	public static String MENOR_RECAUDACION = "Menor recaudación";
	public static String MAS_UNIDADES = "Más productos comprados";
	public static String MENOS_UNIDADES = "Menos productos comprados";
	public static String MAS_ARTICULOS = "Más artículos intercambiados";
	public static String MENOS_ARTICULOS = "Menos artículos intercambiados";
	
	
	public static String[] ORDENES = {MAYOR_RECAUDACION, MENOR_RECAUDACION, 
			MAS_UNIDADES, MENOS_UNIDADES, MAS_ARTICULOS, MENOS_ARTICULOS};
	private static double MAX_HEIGHT_CABECERA = 0.05;
	private JPanel listaClientes;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelEstadisticasTienda> listaPaneles = new ArrayList<>();
	
	public VentanaEstadisticasTienda() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);
		
		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

		JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
		statsPanel.setOpaque(false);

		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Total recaudado", size));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Productos comprados", size));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Artículos intercambiados", size));

		cabecera.add(statsPanel, BorderLayout.EAST);
		
		// Lista 
		listaClientes = new JPanel();
		listaClientes.setLayout(new BoxLayout(listaClientes, BoxLayout.Y_AXIS));
		listaClientes.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaClientes);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void refrescarLista() {
		listaClientes.removeAll();
		
		for (PanelEstadisticasTienda panel: listaPaneles) {
			listaClientes.add(panel);
		}
		
		revalidate();
		repaint();
	}
	
	public List<PanelEstadisticasTienda> getListaPaneles() {
		return listaPaneles;
	}
	
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}
	
	@Override
	public <K extends PanelEstadisticasTienda> PanelEstadisticasTienda anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaClientes.add(panelDisplay);
		return panelDisplay;
	}

}
