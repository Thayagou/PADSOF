package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaEstadisticasCliente extends JPanel implements VentanaConDisplay<PanelClienteEstadisticas>{
	private static final long serialVersionUID = 1L;
	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";
	public static final String MAYOR_RECAUDACION = "Mayor recaudación";
	public static final String MENOR_RECAUDACION = "Menor recaudación";
	public static final String MAS_UNIDADES = "Más productos comprados";
	public static final String MENOS_UNIDADES = "Menos productos comprados";
	public static final String MAS_ARTICULOS = "Más artículos intercambiados";
	public static final String MENOS_ARTICULOS = "Menos artículos intercambiados";
	
	
	
	public static String[] ORDENES = {MAYOR_RECAUDACION, MENOR_RECAUDACION, 
			MAS_UNIDADES, MENOS_UNIDADES, MAS_ARTICULOS, MENOS_ARTICULOS};
	
	private static double MAX_HEIGHT_CABECERA = 0.05;
	
	private JPanel listaClientes;
	private PanelMultiopcion panelOrdenacion;
	private List<PanelClienteEstadisticas> listaPaneles = new ArrayList<>();
	
	public VentanaEstadisticasCliente() {
		setOpaque(false);
		setLayout(new BorderLayout(30, 0));

		// Crea una cabecera con las columnas
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);
		
		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);

		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Total gastado", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Productos comprados", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Artículos intercambiados", size, ColorPalette.WHITE));
		statsPanel.setMaximumSize(new Dimension(3*maxWidth, maxHeight));
		
		cabecera.add(statsPanel, BorderLayout.EAST);
		
		JPanel cliente = new JPanel();
		cliente.setOpaque(false);
		cliente.setLayout(new BoxLayout(cliente, BoxLayout.X_AXIS));
		cliente.add(Box.createHorizontalStrut(TiendaFrame.getInstance().getPixelsWidth(0.005)));
		cliente.add(PanelEstadisticasTienda.crearColumnaStat("Clientes", size, ColorPalette.WHITE));
		cabecera.add(cliente, BorderLayout.WEST);
		
		// Crea el Scroll donde se colocan los paneles de estadísticas
		listaClientes = new JPanel();
		listaClientes.setLayout(new BoxLayout(listaClientes, BoxLayout.Y_AXIS));
		listaClientes.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaClientes);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		// Crea el panel con las opciones de ordenación
		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);
		panelOrdenacion.setActionCommand(CAMBIO_ORDEN_ACTION);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void vaciarLista() {
		listaPaneles.clear();
	}
	
	public void refrescarLista() {
		listaClientes.removeAll();
		
		for (PanelClienteEstadisticas panel: listaPaneles) {
			listaClientes.add(panel);
		}
		
		revalidate();
		repaint();
	}
	
	public String getOpcionSeleccionadaOrden() {
		return ORDENES[panelOrdenacion.getOpcionSeleccionada()];
	}
	
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}
	
	@Override
	public <K extends PanelClienteEstadisticas> PanelClienteEstadisticas anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaClientes.add(panelDisplay);
		return panelDisplay;
	}

}
