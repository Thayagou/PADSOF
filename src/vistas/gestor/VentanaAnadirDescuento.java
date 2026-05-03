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

public class VentanaAnadirDescuento extends JSplitPane implements VentanaConDisplay<PanelDisplay>{
	private JTextField valorMinimo;
	private JComboBox<String> tipoComp;
	private JTextField valorCompensacion;
	private JButton regalo;
	private JSpinner inicio;
	private JSpinner fin;
	public static String TIPO_PRODUCTO = "Productos";
	public static String TIPO_CATEGORIA = "Categorias";
	private static String[] TIPOS_DESCONTADOS = {TIPO_PRODUCTO, TIPO_CATEGORIA};
	
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
        botones.add(roundButton("Cancelar", new Color(160, 0, 200)));
        botones.add(roundButton("Confirmar", new Color(160, 0, 200)));
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

    private JButton roundButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(120, 36));
        return btn;
    }

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaDescontados.add(panelDisplay);
		return panelDisplay;
	}
}