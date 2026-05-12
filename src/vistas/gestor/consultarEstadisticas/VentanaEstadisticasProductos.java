package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.UIManager;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

// TODO: Auto-generated Javadoc
/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de las estadísticas de los productos.
 */
public class VentanaEstadisticasProductos extends JPanel implements VentanaConDisplay<PanelProducto> {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de confirmar el cambio de fecha. */
	public static final String CONFIRMAR_CAMBIO_FECHA_ACTION = "Confirmar";
	
	/** ActionCommand de la acción de cambiar el orden según se muestran los paneles */
	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";
	
	/** Se ordenan los paneles de estadísticas según mayor recaudación. */
	public static final String MAYOR_RECAUDACION = "Mayor recaudación";
	
	/** Se ordenan los paneles de estadísticas según menor recaudación */
	public static final String MENOR_RECAUDACION = "Menor recaudación";
	
	/** Se ordenan los paneles de estadísticas según más unidades vendidad */
	public static final String MAS_UNIDADES = "Más unidades vendidas";
	
	/** Se ordenan los paneles de estadísticas según menos unidades vendidad */
	public static final String MENOS_UNIDADES = "Menos unidades vendidas";

	/** Array con todos los posibles criterios de ordenación. */
	public static String[] ORDENES = { MAYOR_RECAUDACION, MENOR_RECAUDACION, MAS_UNIDADES, MENOS_UNIDADES };
	
	/** Porcentaje de altura de pantalla dedicado a la cabecera de la ventana */
	private static double MAX_HEIGHT_CABECERA = 0.05;
	
	/** JSpinner que permite establecer correctar el valor del inicio del intervalo a consultar las estadísticas. */
	private JSpinner inicio;
	
	/** JSpinner que permite establecer correctar el valor  del fin del intervalo a consultar las estadísticas. */
	private JSpinner fin;
	
	/** Botón asociado a la acción de confirmar la consulta de estadísticas. */
	private JButton confirmar;
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaProductos;
	
	/** Panel con cabecera que permite la selección entre opciones de ordenar los paneles en la ventana. */
	private PanelMultiopcion panelOrdenacion;
	
	/** Lista de los paneles que está siendo actualmente almecenado en la ventana */
	private List<PanelProducto> listaPaneles = new ArrayList<>();

	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella.
	 */
	public VentanaEstadisticasProductos() {
		setOpaque(false);
		setLayout(new BorderLayout(30, 0));

		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);

		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());

		// Crea las columnas de las estadísticas de los productos
		JPanel statsPanel = new JPanel(new GridLayout(1, 3));
		statsPanel.setOpaque(false);

		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Total recaudado", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Unidades vendidas", size, ColorPalette.WHITE));
		statsPanel.add(PanelEstadisticasTienda.crearColumnaStat("Porcentaje de recaudación", size, ColorPalette.WHITE));

		// Crea el panel que se añade al scroll para mostrar los paneles de productos
		listaProductos = new JPanel();
		listaProductos.setLayout(new BoxLayout(listaProductos, BoxLayout.Y_AXIS));
		listaProductos.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaProductos);
		JPanel wrapper = new JPanel();
		wrapper.setOpaque(false);
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
		wrapper.add(statsPanel);
		wrapper.add(Box.createHorizontalStrut(3*UIManager.getInt("ScrollBar.width")));
		
		cabecera.add(wrapper, BorderLayout.EAST);
		JPanel contenido = new JPanel(new BorderLayout());
		
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		// Crea el panel multiopción con las opciones de ordenación
		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ORDENES);
		panelOrdenacion.setActionCommand(CAMBIO_ORDEN_ACTION);
		
		// Obtiene el panel de cabecera norte del PanelMultiopcion y le añade los spinners para elegir mes de inicio y fin 
		BorderLayout layout = (BorderLayout) panelOrdenacion.getLayout();
		JPanel norte = (JPanel) layout.getLayoutComponent(panelOrdenacion, BorderLayout.NORTH);
		
		// Crea los spinners de inicio y fin y los añade a la cabecera
		JLabel labelInicio = ButtonFactory.newLeftAlignedLabel("Inicio", Fonts.TITLE3);
		labelInicio.setForeground(ColorPalette.WHITE.getColor());
		inicio = ButtonFactory.spinnerFechaYearMonth(Fonts.TEXT);
		
		JLabel labelFin = ButtonFactory.newLeftAlignedLabel("Fin", Fonts.TITLE3);
		labelFin.setForeground(ColorPalette.WHITE.getColor());
		fin = ButtonFactory.spinnerFechaYearMonth(Fonts.TEXT);
		
		confirmar = ButtonFactory.newRoundedButton(CONFIRMAR_CAMBIO_FECHA_ACTION, maxHeight, TiendaFrame.getInstance().getPixelsWidth(0.08), maxHeight);
		
		
		int gap = TiendaFrame.getInstance().getPixelsWidth(0.07);
		norte.add(Box.createHorizontalStrut(gap));
		norte.add(labelInicio);
		norte.add(inicio);
		norte.add(Box.createHorizontalStrut(gap));
		norte.add(labelFin);
		norte.add(fin);
		norte.add(Box.createHorizontalStrut(gap));
		norte.add(confirmar);
		
		add(panelOrdenacion, BorderLayout.CENTER);

		refrescarLista();
	}
	
	/**
	 * Vacía la lista de paneles almacenados
	 */
	public void vaciarLista() {
		listaPaneles.clear();
	}

	/**
	 * Refresca la lista de paneles mostrados
	 */
	public void refrescarLista() {
		listaProductos.removeAll();

		for (PanelProducto panel : listaPaneles) {
			listaProductos.add(panel);
		}

		revalidate();
		repaint();
	}

	/**
	 * Getter de la opción actualmente seleccionada en el orden.
	 *
	 * @return String con la tag de la opción seleccionada
	 */
	public String getOpcionSeleccionadaOrden() {
		return ORDENES[panelOrdenacion.getOpcionSeleccionada()];
	}
	
	/**
	 * Getter del mes de inicio del periodo a consultar las estadísticas
	 *
	 * @return Valor del mes seleccionado
	 */
	public YearMonth getInicio() {
		return VentanaEstadisticasTienda.getMes(inicio);
	}
	
	/**
	 * Getter del mes de finalización del periodo a consultar las estadísticas
	 *
	 * @return Valor del mes seleccionado
	 */
	public YearMonth getFin() {
		return VentanaEstadisticasTienda.getMes(fin);
	}	
	
	/**
	 * Setter del mes de inicio del periodo a consultar las estadísticas
	 *
	 * @param inicio Valor de inicio del periodo
	 */
	public void setInicio(YearMonth inicio) {
		VentanaEstadisticasTienda.setMes(this.inicio, inicio);
	}
	
	/**
	 * Setter del mes de fin del periodo a consultar las estadísticas
	 *
	 * @param fin Valor de fin del periodo
	 */
	public void setFin(YearMonth fin) {
		VentanaEstadisticasTienda.setMes(this.fin, fin);
	}

	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param l Control que es añadido a los componentes
	 */
	public void setControlador(ActionListener l) {
		panelOrdenacion.setControlador(l);
		confirmar.addActionListener(l);
	}

	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaProductos.add(panelDisplay);
		return panelDisplay;
	}

}
