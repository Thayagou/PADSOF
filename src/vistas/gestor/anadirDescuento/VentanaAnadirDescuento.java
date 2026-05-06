package vistas.gestor.anadirDescuento;

import javax.swing.*;

import vistas.common.PanelDisplay;
import vistas.common.PanelMultiopcion;
import vistas.common.TiendaFrame;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class VentanaAnadirDescuento extends JPanel implements VentanaConDisplay<PanelDisplay> {
	public static final String CAMBIO_CONDICION_ACTION = "Cambiar opcion condición";
	public static final String CAMBIO_COMPENSACION_ACTION = "Cambiar opcion compensacion";
	public static final String CAMBIO_TIPO_DESCONTADO_ACTION = "Cambiar tipo descontado";
	
	public static final String TIPO_PRODUCTO = "Productos";
	public static final String TIPO_CATEGORIA = "Categorias";
	private static final String[] TIPOS_DESCONTADOS = { TIPO_PRODUCTO, TIPO_CATEGORIA };
	
	public static final String COND_CANTIDAD = "Cantidad"; 
	public static final String COND_VOLUMEN ="Volumen";
	public static final String COND_SIN ="Sin condiciones";
	private static final String[] TIPOS_CONDICION = { COND_CANTIDAD, COND_VOLUMEN, COND_SIN};
	
	public static final String COMP_DINERO = "Dinero";
	public static final String COMP_PORCENTAJE = "Porcentaje";
	public static final String COMP_REGALO = "Regalo";
	private static final String[] TIPOS_COMPENSACION = { COMP_DINERO, COMP_PORCENTAJE, COMP_REGALO};
	
	
	
	public static final String CANCELAR_ACTION = "Cancelar";
	public static final String CONFIRMAR_ACTION = "Confirmar";
	
	private static double COND_WIDTH = 0.35;
	private static double BUTTON_HEIGHT = 0.07;
	
	private int maxWidthCond;
	private int buttonHeight;
	
	private JButton confirmar;
	private JButton cancelar;
	
	
	private PanelMultiopcion panelOpcionesDescontadas;
	private JPanel listaDescontados = new JPanel();
	
	private PanelMultiopcion panelOpcionesCondicion;
	private JPanel panelMinVolumen;
	private JSpinner valorMinVolumen;
	private JPanel panelMinCantidad;
	private JSpinner valorMinCantidad;
	
	private PanelMultiopcion panelOpcionesCompensacion;
	private JPanel panelPorcentaje;
	private JSpinner valorPorcentaje;
	private JPanel panelDinero;
	private JSpinner valorDinero;
	private JPanel panelRegalo;
	private JButton regalo;
	
	private JSpinner inicio;
	private JSpinner fin;

	private static final long serialVersionUID = 1L;

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
		regalo = ButtonFactory.newRoundedButton("Regalo", buttonHeight, maxWidthCond/2, 0.5);
		ButtonFactory.paintButton(regalo, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(regalo, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		regalo.setPreferredSize(sizeSpinner);
		
		JPanel regaloWrapper = new JPanel();
		regaloWrapper.setLayout(new BoxLayout(regaloWrapper, BoxLayout.Y_AXIS));
		regaloWrapper.add(Box.createVerticalGlue());
		regaloWrapper.add(regalo);
		regaloWrapper.add(Box.createVerticalGlue());
		
		panelRegalo = new JPanel(new BorderLayout());
		panelRegalo.add(labelRegalo, BorderLayout.WEST);
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
		
		JLabel labelFin = ButtonFactory.newLeftAlignedLabel("Inicio (dd/mm/yyyy HH:MM): ", Fonts.BOLD);
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
		
		
		// Confirmar y cancelar
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
	
	public void setVisibilidadVolumen(boolean visible) {
		panelMinVolumen.setVisible(visible);
	}
	
	public void setVisibilidadCantidad(boolean visible) {
		panelMinCantidad.setVisible(visible);
	}
	
	public void setVisibilidadPorcentaje(boolean visible) {
		panelPorcentaje.setVisible(visible);
	}
	
	public void setVisibilidadDinero(boolean visible) {
		panelDinero.setVisible(visible);
	}
	
	public void setVisibilidadRegalo(boolean visible) {
		panelRegalo.setVisible(visible);
	}

	public String getOpcionSeleccionadaDescontado() {
		return TIPOS_DESCONTADOS[panelOpcionesDescontadas.getOpcionSeleccionada()];
	}
	
	public String getOpcionSeleccionadaCondicion() {
		return TIPOS_CONDICION[panelOpcionesCondicion.getOpcionSeleccionada()];
	}
	
	public String getOpcionSeleccionadaCompensacion() {
		return TIPOS_COMPENSACION[panelOpcionesCompensacion.getOpcionSeleccionada()];
	}

	public void vaciarDescontados() {
		listaDescontados.removeAll();
	}

	public int getValorMinCantidad() {
		return (int) valorMinCantidad.getValue();
	}

	public double getValorMinVolumen() {
		return (double) valorMinVolumen.getValue();
	}
	
	public double getCompensacionDinero() {
		return (double) valorDinero.getValue();
	}

	public double getCompensacionPorcentaje() {
		return (double) valorPorcentaje.getValue();
	}
	
	public LocalDateTime getFechaInicio() {
		return getValorFecha(inicio);
	}
	
	public LocalDateTime getFechaFin() {
		return getValorFecha(fin);
	}
	
	private LocalDateTime getValorFecha(JSpinner spinnerFecha) {
		Date date = (Date) spinnerFecha.getValue();
		LocalDateTime ldt = date.toInstant()
		    .atZone(ZoneId.systemDefault())
		    .toLocalDateTime();
		return ldt;
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaDescontados.add(panelDisplay);
		return panelDisplay;
	}

	public void setControlador(ActionListener l) {
		panelOpcionesDescontadas.setControlador(l);
		panelOpcionesCondicion.setControlador(l);
		panelOpcionesCompensacion.setControlador(l);
		confirmar.addActionListener(l);
		cancelar.addActionListener(l);
		regalo.addActionListener(l);
	}
}