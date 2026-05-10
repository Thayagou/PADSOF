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

/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de consultar las estadísticas de los clientes.
 */
public class VentanaEstadisticasCliente extends JPanel implements VentanaConDisplay<PanelClienteEstadisticas>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de cambiar el orden de las estadísticas. */
	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";
	
	/** Se ordenan los paneles de estadísticas según mayor recaudación. */
	public static final String MAYOR_RECAUDACION = "Mayor recaudación";
	
	/** Se ordenan los paneles de estadísticas según menor recaudación */
	public static final String MENOR_RECAUDACION = "Menor recaudación";
	
	/** Se ordenan los paneles de estadísticas según más productos comprados */
	public static final String MAS_UNIDADES = "Más productos comprados";
	
	/** Se ordenan los paneles de estadísticas según menos productos comprados*/
	public static final String MENOS_UNIDADES = "Menos productos comprados";
	
	/** Se ordenan los paneles de estadísticas según más artículos intercambiados */
	public static final String MAS_ARTICULOS = "Más artículos intercambiados";
	
	/** Se ordenan los paneles de estadísticas según menos artículos intercambiados*/
	public static final String MENOS_ARTICULOS = "Menos artículos intercambiados";
	
	
	
	/** Array con todos los posibles criterios de ordenación. */
	public static String[] ORDENES = {MAYOR_RECAUDACION, MENOR_RECAUDACION, 
			MAS_UNIDADES, MENOS_UNIDADES, MAS_ARTICULOS, MENOS_ARTICULOS};
	
	/** Porcentaje de altura de pantalla que ocupa la cabecera de la ventana */
	private static double MAX_HEIGHT_CABECERA = 0.05;
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaClientes;
	
	/** Panel con cabecera que permite la selección entre opciones de ordenación */
	private PanelMultiopcion panelOrdenacion;
	
	/** Lista de los paneles actualmente en la vista */
	private List<PanelClienteEstadisticas> listaPaneles = new ArrayList<>();
	
	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella.
	 */
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

		// Crea las columnas de estadísticas de los clientes
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Total gastado", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Productos comprados", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Artículos intercambiados", size, ColorPalette.WHITE));
		statsPanel.setMaximumSize(new Dimension(3*maxWidth, maxHeight));
		
		cabecera.add(statsPanel, BorderLayout.EAST);
		
		// Columna con el nombre "Clientes"
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
	
	/**
	 * Vacía la lista en la que están almacenados los paneles
	 */
	public void vaciarLista() {
		listaPaneles.clear();
	}
	
	/**
	 * Elimina los paneles PanelDisplay del panel de donde se muestran y los reemplaza por los que estén actualmente almecanados en la lista
	 */
	public void refrescarLista() {
		listaClientes.removeAll();
		
		for (PanelClienteEstadisticas panel: listaPaneles) {
			listaClientes.add(panel);
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Getter de la opción actualmente seleccionada para ordenar los paneles de estadísticas.
	 *
	 * @return String con la tag de la opción seleccionada
	 */
	public String getOpcionSeleccionadaOrden() {
		return ORDENES[panelOrdenacion.getOpcionSeleccionada()];
	}
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param l Control que es añadido a los componentes
	 */
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
	}
	
	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	@Override
	public <K extends PanelClienteEstadisticas> PanelClienteEstadisticas anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaClientes.add(panelDisplay);
		return panelDisplay;
	}

}
