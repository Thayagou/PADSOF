package vistas.gestor;

import javax.swing.*;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

import java.awt.*;

public class VentanaAnadirDescuento extends JSplitPane {
	private JTextField valorMinimo;
	private JComboBox<String> tipoComp;
	private JTextField valorCompensacion;
	private JButton regalo;
	private JSpinner inicio;
	private JSpinner fin;
	

    private static final long serialVersionUID = 1L;

	public VentanaAnadirDescuento() {
        
        setLeftComponent(buildLeft());
        
        setRightComponent(buildRight());
    }

    private JPanel buildLeft() {
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

    // -------------------------------------------------------
    // Panel derecho: selector + lista de productos con scroll
    // -------------------------------------------------------
    private JPanel buildRight() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));

        // -- Barra superior --
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        topBar.setBackground(new Color(130, 0, 200));

        JLabel selLabel = new JLabel("Selección:");
        selLabel.setForeground(Color.WHITE);
        JComboBox<String> seleccion = new JComboBox<>(new String[]{"Por productos", "Por categorías"});

        JLabel todosLabel = new JLabel("Aplicar a todos los productos:");
        todosLabel.setForeground(Color.WHITE);
        JCheckBox todos = new JCheckBox();
        todos.setOpaque(false);

        topBar.add(selLabel);
        topBar.add(seleccion);
        topBar.add(Box.createHorizontalStrut(40));
        topBar.add(todosLabel);
        topBar.add(todos);

        panel.add(topBar, BorderLayout.NORTH);

        // -- Lista de productos con scroll --
        JPanel listaProductos = new JPanel();
        listaProductos.setLayout(new BoxLayout(listaProductos, BoxLayout.Y_AXIS));

        for (int i = 1; i <= 6; i++) {
            listaProductos.add(buildProductRow("NombreProducto" + i, i == 2));
            listaProductos.add(new JSeparator());
        }

        JScrollPane scroll = new JScrollPane(listaProductos);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // -------------------------------------------------------
    // Fila de producto individual
    // -------------------------------------------------------
    private JPanel buildProductRow(String nombre, boolean checked) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        // Foto
        JLabel foto = new JLabel("FOTO", SwingConstants.CENTER);
        foto.setPreferredSize(new Dimension(100, 110));
        foto.setOpaque(true);
        foto.setBackground(new Color(160, 160, 140));
        foto.setForeground(Color.WHITE);
        row.add(foto, BorderLayout.WEST);

        // Info central
        JPanel info = new JPanel(new BorderLayout(0, 4));
        info.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JLabel nombreLabel = new JLabel(nombre);
        nombreLabel.setFont(nombreLabel.getFont().deriveFont(Font.BOLD, 14f));

        JLabel desc = new JLabel("<html>Descripción, descripción, descripción, descripción, " +
            "descripción, descripción, descripción, descripción, descripción.</html>");
        desc.setForeground(Color.DARK_GRAY);

        JPanel bottomInfo = new JPanel(new BorderLayout());
        JLabel cats = new JLabel("<html><font color='#7722CC'>Categoría1, Categoría2</font></html>");
        JLabel precio = new JLabel("Precio: 100€");
        precio.setFont(precio.getFont().deriveFont(Font.BOLD));
        bottomInfo.add(cats, BorderLayout.WEST);
        bottomInfo.add(precio, BorderLayout.EAST);

        info.add(nombreLabel, BorderLayout.NORTH);
        info.add(desc, BorderLayout.CENTER);
        info.add(bottomInfo, BorderLayout.SOUTH);
        row.add(info, BorderLayout.CENTER);

        // Checkbox aplicar descuento
        JPanel checkPanel = new JPanel(new BorderLayout(0, 4));
        checkPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        JLabel checkLabel = new JLabel("<html>Aplicar<br>descuento:</html>", SwingConstants.CENTER);
        JCheckBox check = new JCheckBox();
        check.setSelected(checked);
        check.setHorizontalAlignment(SwingConstants.CENTER);
        checkPanel.add(checkLabel, BorderLayout.CENTER);
        checkPanel.add(check, BorderLayout.EAST);
        row.add(checkPanel, BorderLayout.EAST);

        return row;
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------
    private JTextField textField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setForeground(Color.GRAY);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return field;
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
}