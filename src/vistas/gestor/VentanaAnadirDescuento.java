package vistas.gestor;

import javax.swing.*;

import vistas.common.PanelDisplay;
import vistas.common.PanelMultiopcion;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaAnadirDescuento extends JSplitPane implements VentanaConDisplay<PanelDisplay>{
	public static final String TIPO_PRODUCTO = "Productos";
	public static final String TIPO_CATEGORIA = "Categorias";
	public static final String CANCELAR_ACTION = "Cancelar";
	public static final String CONFIRMAR_ACTION = "Confirmar";
	
	private static String[] TIPOS_DESCONTADOS = {TIPO_PRODUCTO, TIPO_CATEGORIA};
	
	private JTextField valorMinimo;
	private JComboBox<String> tipoComp;
	private JTextField valorCompensacion;
	private JButton regalo;
	private JSpinner inicio;
	private JSpinner fin;
	
	private JButton confirmar;
	private JButton cancelar;
	private JPanel listaDescontados = new JPanel();
	private PanelMultiopcion panelOpciones;
	

    private static final long serialVersionUID = 1L;

	public VentanaAnadirDescuento() {
        
        setLeftComponent(crearPanelParametros());
        
        setRightComponent(crearPanelDescontados());
    }

    private JPanel crearPanelParametros() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Configuración del descuento"));
        panel.setOpaque(false);
        
        ButtonFactory factory = new ButtonFactory();

        // -- Tipo de condición --
        panel.add(factory.newLabel("Tipo de condición:", Fonts.TEXT));
        panel.add(Box.createVerticalStrut(4));
        JComboBox<String> tipoCondicion = factory.newComboBox(Fonts.TEXT, "Cantidad", "Volumen", "Sin condiciones");
        
        panel.add(tipoCondicion);
        //panel.add(Box.createVerticalStrut(8));

        panel.add(factory.newLabel("Cantidad/volumen mínimo:", Fonts.TEXT));
        //panel.add(Box.createVerticalStrut(4));
        panel.add(factory.newTextField("Valor mínimo...", Fonts.TEXT));
        //panel.add(new JSeparator());
        //panel.add(Box.createVerticalStrut(8));

        // -- Tipo de compensación --
        panel.add(new JLabel("Tipo de compensación:"));
        panel.add(Box.createVerticalStrut(4));
        tipoComp = factory.newComboBox(Fonts.TEXT, "Dinero","Porcentaje","Regalo");
        panel.add(tipoComp);
        //panel.add(Box.createVerticalStrut(8));

        
        panel.add(factory.newLabel("Valor de la compensación/Regalo:", Fonts.TEXT));
        //panel.add(Box.createVerticalStrut(4));
        panel.add(factory.newTextField("Valor (porcentaje o dinero)...", Fonts.TEXT));
        //panel.add(Box.createVerticalStrut(4));
        panel.add(factory.newTextField("Seleccionar regalo...", Fonts.TEXT));
        //panel.add(new JSeparator());
        //panel.add(Box.createVerticalStrut(8));

        // -- Fechas --
        panel.add(factory.newLabel("Inicio/Fin del descuento:", Fonts.TEXT));
        //panel.add(Box.createVerticalStrut(4));
        panel.add(factory.newLabel("Fecha inicial", Fonts.TEXT));
        panel.add(factory.spinnerFecha(Fonts.TEXT));
        //panel.add(factory.newTextField("Inicio del descuento...", Fonts.TEXT));
        //panel.add(Box.createVerticalStrut(4));
        panel.add(factory.newLabel("Fecha final", Fonts.TEXT));
        panel.add(factory.spinnerFecha(Fonts.TEXT));
        //panel.add(factory.newTextField("Fin del descuento...", Fonts.TEXT));

        // -- Glue empuja botones al fondo --
        panel.add(Box.createVerticalGlue());

        // -- Botones --
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        
        confirmar = factory.newRoundedButton(CONFIRMAR_ACTION, 36, 400, 0.5f);
        cancelar = factory.newRoundedButton(CANCELAR_ACTION, 36, 400, 0.5f);
        
        botones.add(cancelar);
        botones.add(confirmar);
        panel.add(botones);

        return panel;
    }

    private JPanel crearPanelDescontados() {
		listaDescontados.setLayout(new BoxLayout(listaDescontados, BoxLayout.Y_AXIS));
		listaDescontados.setBackground(ColorPalette.CARD_LIGHT.getColor());
		//listaEmpleados.setOpaque(false);

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

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaDescontados.add(panelDisplay);
		return panelDisplay;
	}
	
	
	public void setControlador(ActionListener l) {
		panelOpciones.setControlador(l);
	}
}