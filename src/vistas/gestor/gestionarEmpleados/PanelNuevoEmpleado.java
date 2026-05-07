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

import vistas.common.InvisibleCheckBox;
import vistas.common.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelNuevoEmpleado extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	public final static String CREAR_ACTION = "Crear nuevo empleado";
	public final static String CONFIRMAR_ACTION = "Confirmar";
	
	public static final String DF_NOMBRE = "Nombre";
	public static final String DF_CONTRASENA = "Contraseña";
	public static final String PERM_PRODUCTOS = "Productos";
	public static final String PERM_PEDIDOS = "Pedidos";
	public static final String PERM_INTERCAMBIOS = "Intercambios";
	
	private JTextField nombreEmpleado;
	private JTextField contrasenaEmpleado;
	private JButton confirmarButton;
	private boolean expanded = false;
	private JPanel expandedPanel;
	private Dimension originalMaxSize;
	private InvisibleCheckBox permisoProducto = ButtonFactory.newInvisibleCheckBox("Productos", "Productos", ColorPalette.BLACK,ColorPalette.GREY);
	private InvisibleCheckBox permisoPedidos  = ButtonFactory.newInvisibleCheckBox("Pedidos", "Pedidos", ColorPalette.BLACK,ColorPalette.GREY);
	private InvisibleCheckBox permisoIntercambios  = ButtonFactory.newInvisibleCheckBox("Intercambios", "Intercambios", ColorPalette.BLACK,ColorPalette.GREY);

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
	
	public void toggleExpand() {
		if (expanded) {
			collapsePanel();
		} else {
			expandPanel();
		}
		expanded = !expanded;
	}

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
	
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		confirmarButton.addActionListener(c);
	}
	
	public String getNombreEmpleado() {
		return nombreEmpleado.getText();
	}
	
	public String getContrasenaEmpleado() {
		return contrasenaEmpleado.getText();
	}
	
	public List<String> getPermisos() {
		List<String> listaPermisos = new ArrayList<>();
		
		if (permisoPedidos.isSelected()) listaPermisos.add(PERM_PEDIDOS);
		if (permisoIntercambios.isSelected()) listaPermisos.add(PERM_INTERCAMBIOS);
		if (permisoProducto.isSelected()) listaPermisos.add(PERM_PRODUCTOS);
		
		return listaPermisos;
	}

}
