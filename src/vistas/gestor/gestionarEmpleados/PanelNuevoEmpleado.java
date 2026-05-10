package vistas.gestor.gestionarEmpleados;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vistas.common.components.InvisibleCheckBox;
import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelNuevoEmpleado extends PanelDisplay {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de empezar el proceso de crear un nuevo empleado. */
	public final static String CREAR_ACTION = "Crear nuevo empleado";
	
	/** ActionCommand de la acción de confirmar la creación del nuevo empleado. */
	public final static String CONFIRMAR_ACTION = "Confirmar";
	
	/** Valor por defecto del nombre que debemos evitar al determinarlo */
	public static final String DF_NOMBRE = "Nombre";

	/** Valor por defecto de la contraseña que debemos evitar al determinarla */
	public static final String DF_CONTRASENA = "Contraseña";
	
	/** Constante que define el nombre del permiso de productos. */
	public static final String PERM_PRODUCTOS = "Productos";

	/** Constante que define el nombre del permiso de pedidos. */
	public static final String PERM_PEDIDOS = "Pedidos";

	/** Constante que define el nombre del permiso de intercambios. */
	public static final String PERM_INTERCAMBIOS = "Intercambios";
	
	/** JTextField que nos permite coger el nombre del nuevo empleado */
	private JTextField nombreEmpleado;
	
	/** JTextField que nos permite coger la contraseña del nuevo empleado. */
	private JTextField contrasenaEmpleado;
	
	/** Botón asociado a la acción de confirmar la creación del empleado. */
	private JButton confirmarButton;
	
	/** Variable boolean que determina si está o no expandido el panel de nuevo empleado */
	private boolean expanded = false;
	
	/** Panel correspondiente al panel expandido */
	private JPanel expandedPanel;
	
	/** Dimensión original del panel */
	private Dimension originalMaxSize;
	
	/** InvisibleCheckBox que, si seleccionada, indica si el empleado tiene el permiso de productos. */
	private InvisibleCheckBox permisoProducto = ButtonFactory.newInvisibleCheckBox("Productos", "Productos", ColorPalette.BLACK,ColorPalette.GREY);

	/** InvisibleCheckBox que, si seleccionada, indica si el empleado tiene el permiso de pedidos. */
	private InvisibleCheckBox permisoPedidos  = ButtonFactory.newInvisibleCheckBox("Pedidos", "Pedidos", ColorPalette.BLACK,ColorPalette.GREY);

	/** InvisibleCheckBox que, si seleccionada, indica si el empleado tiene el permiso de intercambios. */
	private InvisibleCheckBox permisoIntercambios  = ButtonFactory.newInvisibleCheckBox("Intercambios", "Intercambios", ColorPalette.BLACK,ColorPalette.GREY);

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 */
	public PanelNuevoEmpleado() {
		super(0.08, 0.06, CREAR_ACTION);
		
		JLabel label = new JLabel(CREAR_ACTION);
		label.setFont(Fonts.TITLE3.getFont());
		add(label, BorderLayout.CENTER);
		
		confirmarButton = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		ButtonFactory.paintButton(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		//super.getClickArea().addActionListener(e -> toggleExpand());
	}
	
	/**
	 * Cambia el estado de expansión
	 */
	public void toggleExpand() {
		if (expanded) {
			collapsePanel();
		} else {
			expandPanel();
		}
		expanded = !expanded;
	}

	/**
	 * Define el comportamiento de la expansión del panel
	 */
	private void expandPanel() {
		if (expandedPanel == null) {
			expandedPanel = new JPanel();
			expandedPanel.setOpaque(false);
			expandedPanel.setLayout(new BoxLayout(expandedPanel, BoxLayout.Y_AXIS));
			//expandedPanel.setLayout(new BoxLayout(expandedPanel, BoxLayout.X_AXIS));
			
			JPanel wrapper = new JPanel(new GridLayout(2,1));
			wrapper.setOpaque(false);
			
			JPanel firstRow = new JPanel();
			firstRow.setOpaque(false);
			firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));
			
			nombreEmpleado = ButtonFactory.newTextField(DF_NOMBRE, Fonts.TEXT);
			JLabel labelNombre = ButtonFactory.newLeftAlignedLabel(DF_CONTRASENA, Fonts.TEXT);
			
			contrasenaEmpleado = ButtonFactory.newTextField("Contraseña", Fonts.TEXT);
			JLabel labelContrasena = ButtonFactory.newLeftAlignedLabel("Introduzca la contraseña", Fonts.TEXT);
			
			
			JPanel textField = new JPanel(new GridLayout(2,2));
			textField.setOpaque(false);
			
			textField.add(labelNombre);
			textField.add(labelContrasena);
			textField.add(nombreEmpleado);
			textField.add(contrasenaEmpleado);
			
			firstRow.add(textField);
			int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
			firstRow.add(Box.createHorizontalStrut(gapSize));
			firstRow.add(confirmarButton);
			
			wrapper.add(firstRow);

			JPanel secondRow = new JPanel();
			secondRow.setOpaque(false);
			secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
			
			secondRow.add(Box.createHorizontalGlue());
			secondRow.add(wrapperCheckBox(permisoProducto));
			secondRow.add(Box.createHorizontalStrut(gapSize));
			secondRow.add(wrapperCheckBox(permisoPedidos));
			secondRow.add(Box.createHorizontalStrut(gapSize));
			secondRow.add(wrapperCheckBox(permisoIntercambios));
			secondRow.add(Box.createHorizontalGlue());
			
			wrapper.add(secondRow);
			
			expandedPanel.add(Box.createVerticalStrut(3*gapSize));
			expandedPanel.add(wrapper);
			expandedPanel.setVisible(false);
			
			add(expandedPanel, BorderLayout.SOUTH);
		}

		expandedPanel.setVisible(true);
		
		if (originalMaxSize == null) {
	        originalMaxSize = getMaximumSize();
	    }
		int expandedHeight = originalMaxSize.height * 3;
	    setMaximumSize(new Dimension(originalMaxSize.width, expandedHeight));

	    revalidate();
	    repaint();
	}
	
	/**
	 * Crea un panel que envuelve una InvisibleCheckbox
	 *
	 * @param cb InvisibleCheckBox de permiso
	 * @return JPanel con la InvisibleCheckBox en el interior
	 */
	private JPanel wrapperCheckBox(InvisibleCheckBox cb	) {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
		wrapper.setOpaque(false);
		wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, cb.getPreferredSize().height));
		wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
		wrapper.add(Box.createHorizontalGlue());
		wrapper.add(cb);
		wrapper.add(Box.createHorizontalGlue());
		wrapper.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cb.toggleSelection();
			}
		});
		
		return wrapper;
	}

	/**
	 * Cierra el panel expandido y lo vuelve al tamaño original
	 */
	private void collapsePanel() {
		if (expandedPanel != null) {
	        expandedPanel.setVisible(false);
	    }

		if (originalMaxSize != null && getParent() != null) {
	        setMaximumSize(originalMaxSize);
	    }

	    revalidate();
	    repaint();
	}
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param c parámetro c
	 */
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		confirmarButton.addActionListener(c);
	}
	
	/**
	 * Getter del nombre del empleado
	 *
	 * @return valor de NombreEmpleado
	 */
	public String getNombreEmpleado() {
		return nombreEmpleado.getText();
	}
	
	/**
	 * Getter de la contraseña añadida del empleado
	 *
	 * @return valor de ContrasenaEmpleado
	 */
	public String getContrasenaEmpleado() {
		return contrasenaEmpleado.getText();
	}
	
	/**
	 * Getter de los nuevos permisos del empleado
	 *
	 * @return Lista con los permisos
	 */
	public List<String> getPermisos() {
		List<String> listaPermisos = new ArrayList<>();
		
		if (permisoPedidos.isSelected()) listaPermisos.add(PERM_PEDIDOS);
		if (permisoIntercambios.isSelected()) listaPermisos.add(PERM_INTERCAMBIOS);
		if (permisoProducto.isSelected()) listaPermisos.add(PERM_PRODUCTOS);
		
		return listaPermisos;
	}

}
