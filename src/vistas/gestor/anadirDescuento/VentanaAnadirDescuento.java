package vistas.gestor.anadirDescuento;

import javax.swing.*;

import controladores.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.PanelDisplay;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de añadir un nuevo descuento a la tienda.
 */
public class VentanaAnadirDescuento extends JPanel implements VentanaConDisplay<PanelDisplay> {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de cambiar la condición del descuento. */
	public static final String CAMBIO_CONDICION_ACTION = "Cambiar opcion condición";
	
	/** ActionCommand de la acción de cambiar la compensación del descuento. */
	public static final String CAMBIO_COMPENSACION_ACTION = "Cambiar opcion compensacion";
	
	/** ActionCommand de la acción de cambiar el tipo descontado del descuento. */
	public static final String CAMBIO_TIPO_DESCONTADO_ACTION = "Cambiar tipo descontado";
	
	/** Clase siendo descontada actualmente: Producto. */
	public static final String TIPO_PRODUCTO = "Productos";
	
	/** Clase siendo descontada actualmente: Categoria. */
	public static final String TIPO_CATEGORIA = "Categorias";
	
	/** Opciones de descontables a descontar. */
	private static final String[] TIPOS_DESCONTADOS = { TIPO_PRODUCTO, TIPO_CATEGORIA };
	
	/** Condición del descuento: cantidad. */
	public static final String COND_CANTIDAD = "Cantidad"; 
	
	/** Condición del descuento: volumen. */
	public static final String COND_VOLUMEN ="Volumen";
	
	/** Condición del descuento: sin condición. */
	public static final String COND_SIN ="Sin condiciones";
	
	/** Array con los tipos de condiciones. */
	private static final String[] TIPOS_CONDICION = { COND_CANTIDAD, COND_VOLUMEN, COND_SIN};
	
	/** Compensación del descuento: dinero. */
	public static final String COMP_DINERO = "Dinero";
	
	/** Compensación del descuento: porcentaje de descuento. */
	public static final String COMP_PORCENTAJE = "Porcentaje";
	
	/** Compensación del descuento: producto como regalo. */
	public static final String COMP_REGALO = "Regalo";
	
	/** Array con los tipos de compensación. */
	private static final String[] TIPOS_COMPENSACION = { COMP_DINERO, COMP_PORCENTAJE, COMP_REGALO};
	
	private static final String DF_REGALO_NOMBRE = "Nada seleccionado";
	
	/** ActionCommand de la acción de cancelar el proceso de añadir el descuento. */
	public static final String CANCELAR_ACTION = "Cancelar";
	
	/** ActionCommand de la acción de confirmar añadir el descuento. */
	public static final String CONFIRMAR_ACTION = "Confirmar";
	
	/** Porcentaje de anchura de pantalla que ocupa el panel de condiciones. */
	private static double COND_WIDTH = 0.35;
	
	/** Porcentaje de altura de pantalla que ocupan los botones. */
	private static double BUTTON_HEIGHT = 0.07;
	
	/** Píxeles que ocupa el campo de condiciones del descuento. */
	private int maxWidthCond;
	
	/** Píxeles de la altura de lo botones. */
	private int buttonHeight;
	
	/** Botón asociado a la acción de confirmar añadir el descuento. */
	private JButton confirmar;
	
	/** Botón asociado a la acción de cancelar el proceso de añadir el intercambio. */
	private JButton cancelar;
	
	
	/** Panel con cabecera que permite la selección entre opciones de los tipos descontados: productos o categorías. */
	private PanelMultiopcion panelOpcionesDescontadas;
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaDescontados = new JPanel();
	
	/** Panel con cabecera que permite la selección entre opciones de. */
	private PanelMultiopcion panelOpcionesCondicion;
	
	/** Panel correspondiente a la condición de volumen mínimo. */
	private JPanel panelMinVolumen;
	
	/** JSpinner que permite establecer correctar el valor del mínimo volmen. */
	private JSpinner valorMinVolumen;
	
	/** Panel correspondiente a la condición de cantidad mínima . */
	private JPanel panelMinCantidad;
	
	/** JSpinner que permite establecer correctamente el valor de la mínima cantidad. */
	private JSpinner valorMinCantidad;
	
	/** Panel con cabecera que permite la selección entre opciones de compensación del descuento. */
	private PanelMultiopcion panelOpcionesCompensacion;
	
	/** Panel correspondiente a la compensación percentual. */
	private JPanel panelPorcentaje;
	
	/** JSpinner que permite establecer correctamente el valor del porcentaje a compensar. */
	private JSpinner valorPorcentaje;
	
	/** Panel correspondiente a la compensación de dinero. */
	private JPanel panelDinero;
	
	/** JSpinner que permite establecer correctamente el valor del valor de dinero a compensar. */
	private JSpinner valorDinero;
	
	/** Panel correspondiente a la compensación de regalo. */
	private JPanel panelRegalo;
	
	/** Label correspondiente al nombre del regalo a descontar */
	private JLabel nombreRegalo;
	
	/** Botón asociado a la acción de seleccionar el regalo. */
	private JButton regalo;
	
	/** JSpinner que permite establecer correctamente el valor del inicio del intervalo del descuento. */
	private JSpinner inicio;
	
	/** JSpinner que permite establecer correctamente el valor del final del intervalo del descuento. */
	private JSpinner fin;

	/**
	 * Instancia un nuevo Objeto VentanaAnadirDescuento.
	 */
	public VentanaAnadirDescuento() {
		TiendaFrame t = TiendaFrame.getInstance();
		int windowWidth = t.getPixelsWidth(1) - t.optionBarDistFromLeft();
		maxWidthCond = (int) (COND_WIDTH * windowWidth);
		buttonHeight = t.getPixelsHeight(BUTTON_HEIGHT);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelParametros(), crearPanelDescontados());
		split.setDividerLocation(maxWidthCond);
		split.setEnabled(false);
		split.setDividerSize(0);
		split.setOpaque(false);

		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(split);
	}

	/**
	 * Crea el panel izquiero de parámetros del descuento.
	 *
	 * @return Jpanel con el panel completo
	 */
	private JPanel crearPanelParametros() {
		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setOpaque(false);
		
		Dimension sizeSpinner = new Dimension(10, buttonHeight/3);
		
		// Panel mínima cantidad
		JLabel labelMinVolumen = ButtonFactory.newLeftAlignedLabel("Valor mínimo (€): ", Fonts.BOLD);
		labelMinVolumen.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		valorMinVolumen = ButtonFactory.spinnerDouble(Fonts.TEXT, 1f, 1f, Double.MAX_VALUE, 0.5);
		valorMinVolumen.setPreferredSize(sizeSpinner);
		
		JPanel volumenMinWrapper = new JPanel();
		volumenMinWrapper.setLayout(new BoxLayout(volumenMinWrapper, BoxLayout.Y_AXIS));
		volumenMinWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		volumenMinWrapper.add(valorMinVolumen);
		volumenMinWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		
		panelMinVolumen= new JPanel(new BorderLayout());
		panelMinVolumen.add(labelMinVolumen, BorderLayout.WEST);
		panelMinVolumen.add(volumenMinWrapper, BorderLayout.CENTER);

		// Panel mínimo volumen
		JLabel labelMinCantidad = ButtonFactory.newLeftAlignedLabel("Unidades mínimas (uds): ", Fonts.BOLD);
		labelMinCantidad.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		valorMinCantidad = ButtonFactory.spinnerEntero(Fonts.TEXT, 1, 1, Integer.MAX_VALUE, 1);
		valorMinCantidad.setPreferredSize(sizeSpinner);
		
		
		JPanel cantidadMinWrapper = new JPanel();
		cantidadMinWrapper.setLayout(new BoxLayout(cantidadMinWrapper, BoxLayout.Y_AXIS));
		cantidadMinWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		cantidadMinWrapper.add(valorMinCantidad);
		cantidadMinWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		
		panelMinCantidad= new JPanel(new BorderLayout());
		panelMinCantidad.add(labelMinCantidad, BorderLayout.WEST);
		panelMinCantidad.add(cantidadMinWrapper, BorderLayout.CENTER);
		
		panelMinVolumen.setVisible(false);
		
		// Wrapper de los dos para el tipo condición multiopción
		JPanel panelContenidoOpcionesCondicion = new JPanel();
		panelContenidoOpcionesCondicion.setLayout(new BoxLayout(panelContenidoOpcionesCondicion, BoxLayout.Y_AXIS));

		panelContenidoOpcionesCondicion.add(panelMinVolumen);
		panelContenidoOpcionesCondicion.add(panelMinCantidad);
		
		// Panel multiopción tipo condición
		panelOpcionesCondicion = new PanelMultiopcion("Tipo de condición:", panelContenidoOpcionesCondicion, Fonts.BOLD, Fonts.TEXT, 
				TIPOS_CONDICION);
		panelOpcionesCondicion.setActionCommand(CAMBIO_CONDICION_ACTION);
		
		contenido.add(panelOpcionesCondicion);
		
		
		// Panel porcentaje
		JLabel labelPorcentaje = ButtonFactory.newLeftAlignedLabel("Porcentaje descontado (%): ", Fonts.BOLD);
		labelPorcentaje.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		valorPorcentaje = ButtonFactory.spinnerDouble(Fonts.TEXT, 10.00, 1f, Double.MAX_VALUE, 5f);
		valorPorcentaje.setPreferredSize(sizeSpinner);
		
		JPanel porcentajeWrapper = new JPanel();
		porcentajeWrapper.setLayout(new BoxLayout(porcentajeWrapper, BoxLayout.Y_AXIS));
		porcentajeWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		porcentajeWrapper.add(valorPorcentaje);
		porcentajeWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		
		panelPorcentaje = new JPanel(new BorderLayout());
		panelPorcentaje.add(labelPorcentaje, BorderLayout.WEST);
		panelPorcentaje.add(porcentajeWrapper, BorderLayout.CENTER);
		
		// Panel dinero
		JLabel labelDinero = ButtonFactory.newLeftAlignedLabel("Valor descontado (€): ", Fonts.BOLD);
		labelDinero.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		valorDinero = ButtonFactory.spinnerDouble(Fonts.TEXT, 1f, 0.01, Double.MAX_VALUE, 0.5);
		valorDinero.setPreferredSize(sizeSpinner);
		
		JPanel dineroWrapper = new JPanel();
		dineroWrapper.setLayout(new BoxLayout(dineroWrapper, BoxLayout.Y_AXIS));
		dineroWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		dineroWrapper.add(valorDinero);
		dineroWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		
		panelDinero = new JPanel(new BorderLayout());
		panelDinero.add(labelDinero, BorderLayout.WEST);
		panelDinero.add(dineroWrapper, BorderLayout.CENTER);
		
		// Panel regalo
		JLabel labelRegalo = ButtonFactory.newLeftAlignedLabel("Elegir regalo: ", Fonts.BOLD);
		labelRegalo.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		nombreRegalo = ButtonFactory.newLeftAlignedLabel(DF_REGALO_NOMBRE, Fonts.TEXT);
		nombreRegalo.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		regalo = ButtonFactory.newRoundedButton("Regalo", buttonHeight, maxWidthCond/2, 0.5);
		ButtonFactory.paintButton(regalo, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(regalo, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		regalo.setPreferredSize(sizeSpinner);
		
		JPanel regaloLabels = new JPanel();
		regaloLabels.setOpaque(false);
		regaloLabels.setLayout(new BoxLayout(regaloLabels, BoxLayout.Y_AXIS));
		regaloLabels.add(Box.createVerticalGlue());
		regaloLabels.add(labelRegalo);
		regaloLabels.add(nombreRegalo);
		regaloLabels.add(Box.createVerticalGlue());
		
		JPanel regaloWrapper = new JPanel();
		regaloWrapper.setLayout(new BoxLayout(regaloWrapper, BoxLayout.Y_AXIS));
		regaloWrapper.add(Box.createVerticalGlue());
		regaloWrapper.add(regalo);
		regaloWrapper.add(Box.createVerticalGlue());
		
		panelRegalo = new JPanel(new BorderLayout());
		panelRegalo.add(regaloLabels, BorderLayout.WEST);
		panelRegalo.add(regaloWrapper, BorderLayout.CENTER);
		
		// Wrapper de los tres para el tipo compensación multiopción
		JPanel panelContenidoOpcionesCompensacion= new JPanel();
		panelContenidoOpcionesCompensacion.setLayout(new BoxLayout(panelContenidoOpcionesCompensacion, BoxLayout.Y_AXIS));

		panelContenidoOpcionesCompensacion.add(panelPorcentaje);
		panelContenidoOpcionesCompensacion.add(panelDinero);
		panelContenidoOpcionesCompensacion.add(panelRegalo);
		
		panelPorcentaje.setVisible(false);
		panelRegalo.setVisible(false);
		
		panelOpcionesCompensacion = new PanelMultiopcion("Tipo de compensación:", panelContenidoOpcionesCompensacion, Fonts.BOLD, Fonts.TEXT,
				TIPOS_COMPENSACION);
		panelOpcionesCompensacion.setActionCommand(CAMBIO_COMPENSACION_ACTION);
		contenido.add(panelOpcionesCompensacion);

		// Panel fechas
		JLabel labelInicio = ButtonFactory.newLeftAlignedLabel("Inicio (dd/mm/yyyy HH:MM): ", Fonts.BOLD);
		labelInicio.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		inicio = ButtonFactory.spinnerFecha(Fonts.TEXT);
		inicio.setPreferredSize(sizeSpinner);
		
		JPanel inicioWrapper = new JPanel();
		inicioWrapper.setLayout(new BoxLayout(inicioWrapper, BoxLayout.Y_AXIS));
		inicioWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		inicioWrapper.add(inicio);
		inicioWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		
		JPanel panelInicio = new JPanel(new BorderLayout());
		panelInicio.add(labelInicio, BorderLayout.WEST);
		panelInicio.add(inicioWrapper, BorderLayout.CENTER);
		
		JLabel labelFin = ButtonFactory.newLeftAlignedLabel("Fin (dd/mm/yyyy HH:MM): ", Fonts.BOLD);
		labelFin.setPreferredSize(new Dimension((int) maxWidthCond * 2 / 3, buttonHeight));
		fin = ButtonFactory.spinnerFecha(Fonts.TEXT);
		fin.setPreferredSize(sizeSpinner);
		
		JPanel finWrapper = new JPanel();
		finWrapper.setLayout(new BoxLayout(finWrapper, BoxLayout.Y_AXIS));
		finWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		finWrapper.add(fin);
		finWrapper.add(Box.createVerticalStrut(buttonHeight/3));
		
		JPanel panelFin = new JPanel(new BorderLayout());
		panelFin.add(labelFin, BorderLayout.WEST);
		panelFin.add(finWrapper, BorderLayout.CENTER);
		
		JPanel contenidoDuracion = new JPanel();
		contenidoDuracion.setLayout(new BoxLayout(contenidoDuracion, BoxLayout.Y_AXIS));
		
		contenidoDuracion.add(panelInicio);
		contenidoDuracion.add(panelFin);
		
		contenido.add(PanelFactory.getVentanaConCabecera("  Duración del descuento", contenidoDuracion, Fonts.BOLD));
		
		
		// Botones de confirmar y cancelar
		JPanel botones = new JPanel(new GridLayout(1,2));

		confirmar = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, buttonHeight, maxWidthCond/3, 0.5f);
		cancelar = ButtonFactory.newRoundedButton(CANCELAR_ACTION, buttonHeight, maxWidthCond/3, 0.5f);
		ButtonFactory.paintButton(confirmar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		ButtonFactory.paintButton(cancelar, ColorPalette.LIGHT_RED, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(cancelar, ColorPalette.LIGHT_RED, ColorPalette.RED);
		
		botones.add(cancelar);
		botones.add(confirmar);
		contenido.add(botones);

		return PanelFactory.getVentanaConCabecera("Configuración de descuento", contenido);
	}

	/**
	 * Crea el panel derecho de los elementos a descontar.
	 *
	 * @return JPanel con el esquema del panel
	 */
	private JPanel crearPanelDescontados() {
		listaDescontados.setLayout(new BoxLayout(listaDescontados, BoxLayout.Y_AXIS));
		listaDescontados.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = PanelFactory.getScroll(listaDescontados);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		panelOpcionesDescontadas = new PanelMultiopcion("      Elementos a descontar", contenido, TIPOS_DESCONTADOS);
		panelOpcionesDescontadas.setActionCommand(CAMBIO_TIPO_DESCONTADO_ACTION);
		return panelOpcionesDescontadas;
	}
	
	/**
	 * Establece la visibilidad del panel asociado a la condición de volumen.
	 *
	 * @param visible true si se desea mostrar, false si no
	 */
	public void setVisibilidadVolumen(boolean visible) {
		panelMinVolumen.setVisible(visible);
	}
	
	/**
	 * Establece la visibilidad del panel asociado a la condición de cantidad.
	 *
	 * @param visible true si se desea mostrar, false si no
	 */
	public void setVisibilidadCantidad(boolean visible) {
		panelMinCantidad.setVisible(visible);
	}
	
	/**
	 * Establece la visibilidad del panel asociado a la compensación de porcentaje.
	 *
	 * @param visible true si se desea mostrar, false si no
	 */
	public void setVisibilidadPorcentaje(boolean visible) {
		panelPorcentaje.setVisible(visible);
	}
	
	/**
	 * Establece la visibilidad del panel asociado a la compensación de dinero.
	 *
	 * @param visible true si se desea mostrar, false si no
	 */
	public void setVisibilidadDinero(boolean visible) {
		panelDinero.setVisible(visible);
	}
	
	/**
	 * Establece la visibilidad del panel asociado a la compensación de regalo.
	 *
	 * @param visible true si se desea mostrar, false si no
	 */
	public void setVisibilidadRegalo(boolean visible) {
		panelRegalo.setVisible(visible);
	}

	
	/**
	 * Getter de la opción actualmente seleccionada del tipo descontado.
	 *
	 * @return String con la tag de la opción seleccionada
	 */
	public String getOpcionSeleccionadaDescontado() {
		return TIPOS_DESCONTADOS[panelOpcionesDescontadas.getOpcionSeleccionada()];
	}
	

	/**
	 * Getter de la opción actualmente seleccionada de condición del descuento.
	 *
	 * @return String con la tag de la opción seleccionada
	 */
	public String getOpcionSeleccionadaCondicion() {
		return TIPOS_CONDICION[panelOpcionesCondicion.getOpcionSeleccionada()];
	}
	
	/**
	 * Getter de la opción actualmente seleccionada de compensación del descuento .
	 *
	 * @return String con la tag de la opción seleccionada
	 */
	public String getOpcionSeleccionadaCompensacion() {
		return TIPOS_COMPENSACION[panelOpcionesCompensacion.getOpcionSeleccionada()];
	}

	/**
	 * Vacía el panel de los elementos descontados
	 */
	public void vaciarDescontados() {
		listaDescontados.removeAll();
	}

	/**
	 * Obtiene el valor mínimo de la condición de cantidad.
	 *
	 * @return valor de ValorMinCantidad
	 */
	public int getValorMinCantidad() {
		return (int) valorMinCantidad.getValue();
	}

	/**
	 * Obtiene el valor mínimo de la condición de volumen.
	 *
	 * @return valor de ValorMinCantidad
	 */
	public double getValorMinVolumen() {
		return (double) valorMinVolumen.getValue();
	}
	
	/**
	 * Getter del valor asociado a la compensación de dinero
	 *
	 * @return valor de CompensacionDinero
	 */
	public double getCompensacionDinero() {
		return (double) valorDinero.getValue();
	}

	/**
	 * Getter del valor asociado a la compensación de porcentaje
	 *
	 * @return valor de CompensacionDinero
	 */
	public double getCompensacionPorcentaje() {
		return (double) valorPorcentaje.getValue();
	}
	
	/**
	 * Getter del la fecha de inicio del descuento
	 *
	 * @return valor del inicio
	 */
	public LocalDateTime getFechaInicio() {
		return getValorFecha(inicio);
	}
	
	/**
	 * Getter del la fecha de fin del descuento
	 *
	 * @return valor del fin
	 */
	public LocalDateTime getFechaFin() {
		return getValorFecha(fin);
	}
	
	public void setNombreRegalo(boolean sel, String nombre) {
		if (!sel) {
			nombreRegalo.setText(DF_REGALO_NOMBRE);
		} else {
			nombreRegalo.setText(Fonts.truncar(nombre, maxWidthCond/3, nombreRegalo.getFont(), nombreRegalo));
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Conversor de Date almacenado en el spinner a LocalDateTime.
	 *
	 * @param spinnerFecha JSpinner a extraer la fecha
	 * @return fecha asociada con el JSpinner
	 */
	private LocalDateTime getValorFecha(JSpinner spinnerFecha) {
		Date date = (Date) spinnerFecha.getValue();
		LocalDateTime ldt = date.toInstant()
		    .atZone(ZoneId.systemDefault())
		    .toLocalDateTime();
		return ldt;
	}

	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaDescontados.add(panelDisplay);
		return panelDisplay;
	}

	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param l Control que es añadido a los componentes
	 */
	public void setControlador(ActionListener l) {
		panelOpcionesDescontadas.setControlador(l);
		panelOpcionesCondicion.setControlador(l);
		panelOpcionesCompensacion.setControlador(l);
		confirmar.addActionListener(l);
		cancelar.addActionListener(l);
		regalo.addActionListener(l);
	}
}