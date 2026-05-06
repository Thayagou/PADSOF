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

public class VentanaAnadirDescuento extends JPanel implements VentanaConDisplay<PanelDisplay> {
	public static final String TIPO_PRODUCTO = "Productos";
	public static final String TIPO_CATEGORIA = "Categorias";
	public static final String CANCELAR_ACTION = "Cancelar";
	public static final String CONFIRMAR_ACTION = "Confirmar";

	private static String[] TIPOS_DESCONTADOS = { TIPO_PRODUCTO, TIPO_CATEGORIA };
	private static double COND_WIDTH = 0.2;
	private static double BUTTON_HEIGHT = 0.07;
	private JSpinner valorMinCantidad;
	private JSpinner valorMinVolumen;
	private JComboBox<String> tipoComp;
	private JTextField valorCompensacion;
	private JSpinner inicio;
	private JSpinner fin;
	private int maxWidthCond;
	private int buttonHeight;
	private JButton confirmar;
	private JButton cancelar;
	private JButton regalo;
	private JPanel listaDescontados = new JPanel();
	private PanelMultiopcion panelOpciones;
	private PanelMultiopcion tipoCondicion;
	private PanelMultiopcion tipoCompensacion;
	

	private static final long serialVersionUID = 1L;

	public VentanaAnadirDescuento() {
		TiendaFrame t = TiendaFrame.getInstance();
	    int windowWidth = t.getPixelsWidth(1) - t.optionBarDistFromLeft();
	    maxWidthCond = (int)(COND_WIDTH * windowWidth);
	    buttonHeight = t.getPixelsHeight(BUTTON_HEIGHT);

	    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	        crearPanelParametros(),
	        crearPanelDescontados());
	    split.setDividerLocation(maxWidthCond); // posición fija en píxeles
	    split.setEnabled(false);               // el usuario no puede moverlo
	    split.setDividerSize(0);              // sin barra visible
	    split.setOpaque(false);

	    this.setOpaque(false);
	    this.setLayout(new BorderLayout());
	    this.add(split);
	}

	private JPanel crearPanelParametros() {
		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setOpaque(false);

		// -- Tipo de condición --
		tipoCondicion = new PanelMultiopcion("Tipo de condición:", Fonts.BOLD, Fonts.TEXT, new String[] {"Cantidad", "Volumen", "Sin condiciones"});
		
		
		/*contenido.add(ButtonFactory.newLabel("Tipo de condición:", Fonts.TEXT));
		contenido.add(Box.createVerticalStrut(4));
		JComboBox<String> tipoCondicion = ButtonFactory.newComboBox(Fonts.TEXT, "Cantidad", "Volumen", "Sin condiciones");*/

		contenido.add(tipoCondicion);
		/*System.out.println(maxWidthCond + " " + buttonHeight);
		Dimension tamanoCampo = new Dimension(maxWidthCond, buttonHeight);
		Dimension tamanoSpinner = new Dimension ((int)(0.4*maxWidthCond), buttonHeight);
		JPanel panelMinCantidad = new JPanel(new BorderLayout());
		JLabel labelMinCantidad = ButtonFactory.newLeftAlignedLabel("Valor mínimo (€): ", Fonts.BOLD);
		valorMinCantidad = ButtonFactory.spinnerEntero(Fonts.TEXT, 1, 1, Integer.MAX_VALUE, 1);
		//valorMinCantidad.setPreferredSize(tamanoSpinner);
		panelMinCantidad.add(labelMinCantidad, BorderLayout.WEST);
		panelMinCantidad.add(valorMinCantidad, BorderLayout.EAST);
		//panelMinCantidad.setPreferredSize(tamanoCampo);
		panelMinCantidad.setMaximumSize(tamanoCampo);
		contenido.add(panelMinCantidad);
		
		JPanel panelMinVolumen = new JPanel(new BorderLayout());
		JLabel labelMinVolumen = ButtonFactory.newLeftAlignedLabel("Unidades mínimas (uds): ", Fonts.BOLD);
		valorMinVolumen = ButtonFactory.spinnerDouble(Fonts.TEXT, 1f, 1f, Double.MAX_VALUE, 0.5);
		//valorMinVolumen.setPreferredSize(tamanoSpinner);
		panelMinVolumen.add(labelMinVolumen, BorderLayout.WEST);
		panelMinVolumen.add(valorMinVolumen, BorderLayout.EAST);
		//panelMinVolumen.setPreferredSize(tamanoCampo);
		panelMinVolumen.setMaximumSize(tamanoCampo);
		contenido.add(panelMinVolumen);*/
		
		
		Dimension sizeSpinner = new Dimension(10, buttonHeight);
		JPanel panelMinCantidad = new JPanel(new BorderLayout());
		JLabel labelMinCantidad = ButtonFactory.newLeftAlignedLabel("Valor mínimo (€): ", Fonts.BOLD);
		//labelMinCantidad.setPreferredSize(new Dimension((int)maxWidthCond* 2/3, buttonHeight));
		valorMinCantidad = ButtonFactory.spinnerEntero(Fonts.TEXT, 1, 1, Integer.MAX_VALUE, 1);
		valorMinCantidad.setPreferredSize(sizeSpinner);
		panelMinCantidad.add(labelMinCantidad, BorderLayout.WEST);
		panelMinCantidad.add(valorMinCantidad, BorderLayout.CENTER);
		//panelMinCantidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight));
		//panelMinCantidad.setPreferredSize(new Dimension(maxWidthCond, buttonHeight));
		contenido.add(panelMinCantidad);

		JPanel panelMinVolumen = new JPanel(new BorderLayout());
		JLabel labelMinVolumen = ButtonFactory.newLeftAlignedLabel("Unidades mínimas (uds): ", Fonts.BOLD);
		//labelMinVolumen.setPreferredSize(new Dimension((int)maxWidthCond* 2/3, buttonHeight));
		valorMinVolumen = ButtonFactory.spinnerDouble(Fonts.TEXT, 1f, 1f, Double.MAX_VALUE, 0.5);
		valorMinVolumen.setPreferredSize(sizeSpinner);
		panelMinVolumen.add(labelMinVolumen, BorderLayout.WEST);
		panelMinVolumen.add(valorMinVolumen, BorderLayout.CENTER);
		//panelMinVolumen.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight));
		//panelMinVolumen.setPreferredSize(new Dimension(maxWidthCond, buttonHeight));
		contenido.add(panelMinVolumen);
		
		// panel.add(Box.createVerticalStrut(8));

		/*contenido.add(ButtonFactory.newLabel("Cantidad/volumen mínimo:", Fonts.TEXT));
		// panel.add(Box.createVerticalStrut(4));
		contenido.add(ButtonFactory.newTextField("Valor mínimo...", Fonts.TEXT));*/
		// panel.add(new JSeparator());
		// panel.add(Box.createVerticalStrut(8));

		// -- Tipo de compensación --
		tipoCompensacion = new PanelMultiopcion("Tipo de compensación:", Fonts.BOLD, Fonts.TEXT, new String[] {"Dinero", "Porcentaje", "Regalo"});
		
		contenido.add(tipoCompensacion);
		
		regalo = ButtonFactory.newButton("Regalo");
		contenido.add(regalo);
		// panel.add(Box.createVerticalStrut(8));

		contenido.add(ButtonFactory.newLabel("Valor de la compensación/Regalo:", Fonts.TEXT));
		// panel.add(Box.createVerticalStrut(4));
		contenido.add(ButtonFactory.newTextField("Valor (porcentaje o dinero)...", Fonts.TEXT));
		// panel.add(Box.createVerticalStrut(4));
		contenido.add(ButtonFactory.newTextField("Seleccionar regalo...", Fonts.TEXT));
		// panel.add(new JSeparator());
		// panel.add(Box.createVerticalStrut(8));

		// -- Fechas --
		contenido.add(ButtonFactory.newLabel("Inicio/Fin del descuento:", Fonts.TEXT));
		// panel.add(Box.createVerticalStrut(4));
		contenido.add(ButtonFactory.newLabel("Fecha inicial", Fonts.TEXT));
		contenido.add(ButtonFactory.spinnerFecha(Fonts.TEXT));
		// panel.add(factory.newTextField("Inicio del descuento...", Fonts.TEXT));
		// panel.add(Box.createVerticalStrut(4));
		contenido.add(ButtonFactory.newLabel("Fecha final", Fonts.TEXT));
		contenido.add(ButtonFactory.spinnerFecha(Fonts.TEXT));
		// panel.add(factory.newTextField("Fin del descuento...", Fonts.TEXT));

		// -- Glue empuja botones al fondo --
		contenido.add(Box.createVerticalGlue());

		// -- Botones --
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));

		confirmar = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, 36, 400, 0.5f);
		cancelar = ButtonFactory.newRoundedButton(CANCELAR_ACTION, 36, 400, 0.5f);

		botones.add(cancelar);
		botones.add(confirmar);
		contenido.add(botones);

		return PanelFactory.getVentanaConCabecera("Configuración de descuento", contenido);
	}

	private JPanel crearPanelDescontados() {
		listaDescontados.setLayout(new BoxLayout(listaDescontados, BoxLayout.Y_AXIS));
		listaDescontados.setBackground(ColorPalette.CARD_LIGHT.getColor());
		// listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaDescontados);
		scroll.getVerticalScrollBar().setUnitIncrement(10);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		panelOpciones = new PanelMultiopcion("      Elementos a descontar", contenido, TIPOS_DESCONTADOS);

		return panelOpciones;
	}

	public String getOpcionSeleccionada() {
		return TIPOS_DESCONTADOS[panelOpciones.getOpcionSeleccionada()];
	}

	public void vaciarDescontados() {
		listaDescontados.removeAll();
	}
	
	public int getValorMinCantidad() {
		return (int) valorMinCantidad.getValue();
	}
	
	public double getValorMinVolumen() {
		return (double) valorMinCantidad.getValue();
	}
	
	
	

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaDescontados.add(panelDisplay);
		return panelDisplay;
	}

	public void setControlador(ActionListener l) {
		panelOpciones.setControlador(l);
		confirmar.addActionListener(l);
		cancelar.addActionListener(l);
		regalo.addActionListener(l);
	}
}