package vistas.gestor.consultarEstadisticas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de las estadísticas de la tienda
 */
public class VentanaEstadisticasTienda extends JPanel implements VentanaConDisplay<PanelEstadisticasTienda>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de confirmar. */
	public static final String CONFIRMAR_CAMBIO_FECHA_ACTION = "Confirmar";

	/** ActionCommand de la acción de cambiar el orden de los paneles. */
	public static final String CAMBIO_ORDEN_ACTION = "Cambiar orden";

	
	/** Posibles criterios de ordenación */
	private String[] ordenes;
	
	/** JSpinner que permite establecer correctar el valor del inicio del intervalo a consultar las estadísticas. */
	private JSpinner inicio;
	
	/** JSpinner que permite establecer correctar el valor  del fin del intervalo a consultar las estadísticas. */
	private JSpinner fin;
	
	/** Botón asociado a la acción de confirmar la consulta de estadísticas. */
	private JButton confirmar;
	
	/** Porcentaje de altura de pantalla dedicado a la cabecera de la ventana */
	private static double MAX_HEIGHT_CABECERA = 0.05;
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaStats;
	
	/** Panel con cabecera que permite la selección entre opciones de criterios de ordenación. */
	private PanelMultiopcion panelOrdenacion;
	
	/** Lista de los paneles que está siendo actualmente almecenado en la ventana */
	private List<PanelEstadisticasTienda> listaPaneles = new ArrayList<>();
	
	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella.
	 *
	 * @param ordenes Criterios de ordenación posibles
	 * @param columnas Columnas de estadísticas que se establecen
	 */
	public VentanaEstadisticasTienda(String[] ordenes, String...columnas) {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		this.ordenes = ordenes;
		
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(PanelClienteEstadisticas.LABEL_WIDTH);
		int maxHeight = TiendaFrame.getInstance().getPixelsHeight(MAX_HEIGHT_CABECERA);
		Dimension size = new Dimension(maxWidth, maxHeight);
		
		// Crea una cabecera para la ventana
		JPanel cabecera = PanelFactory.getCabecera();
		cabecera.setLayout(new BorderLayout());
		cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

		// Crea las columnas solicitadas 
		JPanel statsPanel = new JPanel(new GridLayout(1, columnas.length, 20, 0));
		statsPanel.setOpaque(false);

		for (String col: columnas) {
			statsPanel.add(PanelEstadisticasTienda.crearColumnaStat(col, size, ColorPalette.WHITE));
		}
		
		cabecera.add(statsPanel, BorderLayout.EAST);
		
		// Crea el panel que actúa como lista de paneles de estadísticas 
		listaStats = new JPanel();
		listaStats.setLayout(new BoxLayout(listaStats, BoxLayout.Y_AXIS));
		listaStats.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaStats);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(cabecera, BorderLayout.NORTH);
		contenido.add(scroll, BorderLayout.CENTER);

		panelOrdenacion = new PanelMultiopcion("Ordenar por", contenido, ordenes);
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
		listaStats.removeAll();

		for (PanelEstadisticasTienda panel : listaPaneles) {
			listaStats.add(panel);
		}

		revalidate();
		repaint();
	}

	/**
	 * Getter del mes de inicio del periodo a consultar las estadísticas
	 *
	 * @return Valor del mes seleccionado
	 */
	public YearMonth getInicio() {
		return getMes(inicio);
	}
	
	/**
	 * Getter del mes de finalización del periodo a consultar las estadísticas
	 *
	 * @return Valor del mes seleccionado
	 */
	public YearMonth getFin() {
		return getMes(fin);
	}	
	
	/**
	 * Setter del mes de inicio del periodo a consultar las estadísticas
	 *
	 * @param inicio Valor de inicio del periodo
	 */
	public void setInicio(YearMonth inicio) {
		setMes(this.inicio, inicio);
	}
	
	/**
	 * Setter del mes de fin del periodo a consultar las estadísticas
	 *
	 * @param fin Valor de inicio del periodo
	 */
	public void setFin(YearMonth fin) {
		setMes(this.fin, fin);
	}
	
	/**
	 * Obtiene la fecha en formato YearMonth a partir de un Spinner que almacena Date
	 *
	 * @param spinner JSpinner del que extraemos la fecha
	 * @return Mes almacenado en el valor del Spinner
	 */
	public static YearMonth getMes(JSpinner spinner) {
		Date date = (Date) spinner.getValue();
	    return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()));
	}
	
	/**
	 * Obtiene la fecha en formato YearMonth a partir de un Spinner que almacena Date
	 *
	 * @param spinner JSpinner del que extraemos la fecha
	 * @param mes Mes en formato YearMonth a establecer
	 */
	public static void setMes(JSpinner spinner, YearMonth mes) {
		Date date = Date.from(mes.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
	    spinner.setValue(date);
	}
	
	/**
	 * Getter de la opción actualmente seleccionada en el criterio de orden.
	 *
	 * @return String con la tag de la opción seleccionada
	 */
	public String getOpcionSeleccionadaOrden() {
		return ordenes[panelOrdenacion.getOpcionSeleccionada()];
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
	public <K extends PanelEstadisticasTienda> PanelEstadisticasTienda anadirDisplay(K panelDisplay) {
		listaPaneles.add(panelDisplay);
		listaStats.add(panelDisplay);
		return panelDisplay;
	}

}
