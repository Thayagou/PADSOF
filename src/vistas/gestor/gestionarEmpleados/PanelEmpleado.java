package vistas.gestor.gestionarEmpleados;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.components.InvisibleCheckBox;
import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar la información de los empleados dentro de un scroll.
 */
public class PanelEmpleado extends PanelDisplay {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** ActionCommand de la acción de confirmar el cambio del empleado. */
	public static final String CONFIRMAR_ACTION = "Confirmar";
	
	/** ActionCommand de la acción de tratar de modificar el empleado. */
	public static final String MODIFICAR_ACTION = "Modificar información y permisos";
	
	/** ActionCommand de la acción de dar de alta/ baja. */
	public static final String DE_ALTA_ACTION = "Alta";

	/** Porcentaje de anchura de pantalla utilizado para la foto */
	private static final double FOTO_W_PERC = 0.09;
	
	/** Porcentaje de panel utilizado para la foto. */
	private static final double FOTO_H_PERC = 0.99;
	
	/** Porcentaje de altura de pantalla que ocupa el panel */
	private static final double MAX_HEIGHT = 0.16;

	/** Establece si el empleado está actualmente de alta */
	private boolean deAlta;
	
	/** Lista de permisos que tiene el empleado */
	private List<String> permisos = new ArrayList<>();
	
	/** Botón asociado a la acción de modificar al empleado. */
	private JButton modButton;
	
	/** Botón asociado a la acción de dar de alta */
	private JButton deAltaButton;
	
	/** Label de los permisos del empleado. Lo almacenamos para poder cambiarlo. */
	private JLabel permisosLabel;
	
	/** Label con el estado del empleado: de alta si true o de baja si false*/
	private JLabel estado;
	
	/** Panel correspondiente a los botones. */
	private JPanel eastPanel;
	
	/** Máxima anchura de los botones en pantalla */
	private int maxWidth;
	
	/** Botón asociado a la acción de confirmar el cambio. */
	private JButton confirmarButton;
	
	/** Boolean que representa si el panel se encuentra en estado expandido o no. */
	private boolean expanded = false;
	
	/** Panel correspondiente a la expansión de este para permitir la modificación de los permisos del empleado */
	private JPanel expandedPanel;
	
	/** Tamaño preferred original */
	private Dimension originalMaxSize;
	
	/** InvisibleCheckBox que, si seleccionada, indica si el empleado tiene el permiso de productos. */
	private InvisibleCheckBox permisoProducto = ButtonFactory.newInvisibleCheckBox("Productos", "Productos", ColorPalette.BLACK,ColorPalette.GREY);

	/** InvisibleCheckBox que, si seleccionada, indica si el empleado tiene el permiso de pedidos. */
	private InvisibleCheckBox permisoPedidos  = ButtonFactory.newInvisibleCheckBox("Pedidos", "Pedidos", ColorPalette.BLACK,ColorPalette.GREY);

	/** InvisibleCheckBox que, si seleccionada, indica si el empleado tiene el permiso de intercambios. */
	private InvisibleCheckBox permisoIntercambios  = ButtonFactory.newInvisibleCheckBox("Intercambios", "Intercambios", ColorPalette.BLACK,ColorPalette.GREY);


	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombre Nombre del empleado
	 * @param deAlta Boolean que representa si está de alta o no
	 * @param permisos Varargs de los permisos que tiene el empleado
	 */
	public PanelEmpleado(String nombre, boolean deAlta, String... permisos) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, "pfp.png", MODIFICAR_ACTION);
		this.deAlta = deAlta;
		// this.fotoDePerfil = fotoDePerfil;
		for (String p: permisos) this.permisos.add(p);

		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();

		/* Info: nombre + permisos */
		JPanel info = new JPanel();
		info.setOpaque(false);
		info.setLayout(new GridLayout(3, 1));

		/* Primera fila: nombre */
		JPanel firstRow = new JPanel();
		firstRow.setOpaque(false);
		firstRow.setLayout(new BorderLayout(10, 0));

		JLabel nombreLabel = new JLabel(nombre);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		firstRow.add(nombreLabel, BorderLayout.WEST);

		info.add(firstRow);

		/* Segunda fila: permisos */
		JPanel permisosRow = new JPanel();
		permisosRow.setLayout(new BorderLayout(10, 0));
		permisosRow.setOpaque(false);

		String permisosString = String.join(", ", this.permisos);

		if (permisosString.isBlank())
			permisosString = "sin permisos";
		permisosLabel = ButtonFactory.newLabel("Permisos: " + permisosString, Fonts.TEXT);
		permisosLabel.setForeground(ColorPalette.PURPLE.getColor());
		permisosRow.add(permisosLabel, BorderLayout.WEST);
		info.add(permisosRow);
		
		if (this.permisos.contains(PanelNuevoEmpleado.PERM_PEDIDOS)) permisoPedidos.setSeleccionado(true);
		if (this.permisos.contains(PanelNuevoEmpleado.PERM_PRODUCTOS)) permisoProducto.setSeleccionado(true);
		if (this.permisos.contains(PanelNuevoEmpleado.PERM_INTERCAMBIOS)) permisoIntercambios.setSeleccionado(true);

		/* Tercera fila: de alta */
		JPanel deAltaRow = new JPanel();
		deAltaRow.setLayout(new BorderLayout(10, 0));
		deAltaRow.setOpaque(false);

		estado = new JLabel(deAlta ? "Empleado de alta" : "Empleado de baja");
		estado.setFont(Fonts.BOLD.getFont());

		if (this.deAlta)
			estado.setForeground(ColorPalette.GREEN.getColor());
		else
			estado.setForeground(ColorPalette.RED.getColor());
		deAltaRow.add(estado, BorderLayout.WEST);
		info.add(deAltaRow);

		add(info, BorderLayout.CENTER);
		
		info.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxCompHeight));

		// Crea los botones y las añade a la parte EAST del panel
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		modButton = ButtonFactory.newRoundedButton(MODIFICAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxWidth, 0.75f);
		ButtonFactory.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		deAltaButton = ButtonFactory.newRoundedButton(deAlta ? "Dar de baja" : "Dar de alta", (int) (maxCompHeight * BOTON_PERC_H), maxWidth, 0.75f);
		deAltaButton.setActionCommand(DE_ALTA_ACTION);
		ButtonFactory.paintButton(deAltaButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(deAltaButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		anadirBotones();	
		
		Dimension tamano = deAltaButton.getPreferredSize();
		deAltaButton.setMinimumSize(tamano);
		deAltaButton.setMaximumSize(tamano);
		deAltaButton.setPreferredSize(tamano);
		modButton.setMinimumSize(tamano);
		modButton.setMaximumSize(tamano);
		modButton.setPreferredSize(tamano);
		
		// Botón que se muestra al modificar el empleado
		confirmarButton = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		ButtonFactory.paintButton(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
	}
	
	/**
	 * Añade los botones correspondientes al panel EAST
	 */
	private void anadirBotones() {
		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
		eastPanel.removeAll();
		
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(deAltaButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		eastPanel.revalidate();
		eastPanel.repaint();

		this.add(eastPanel, BorderLayout.EAST);	
		
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
			expandedPanel.setLayout(new BoxLayout(expandedPanel, BoxLayout.X_AXIS));

			int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
			expandedPanel.add(Box.createHorizontalGlue());
			expandedPanel.add(wrapperCheckBox(permisoProducto));
			expandedPanel.add(Box.createHorizontalStrut(gapSize));
			expandedPanel.add(wrapperCheckBox(permisoPedidos));
			expandedPanel.add(Box.createHorizontalStrut(gapSize));
			expandedPanel.add(wrapperCheckBox(permisoIntercambios));
			
			expandedPanel.add(confirmarButton);
			
			expandedPanel.setVisible(false);
			add(expandedPanel, BorderLayout.SOUTH);
		}

		expandedPanel.setVisible(true);
		
		if (originalMaxSize == null) {
	        originalMaxSize = getMaximumSize();
	    }
		int expandedHeight = (int)(originalMaxSize.height * 1.3);
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
	 * Getter de los permisos que tiene el empleado en String
	 *
	 * @return Lista de permisos
	 */
	public List<String> getPermisos() {
		List<String> listaPermisos = new ArrayList<>();
		
		if (permisoPedidos.isSelected()) listaPermisos.add(PanelNuevoEmpleado.PERM_PEDIDOS);
		if (permisoIntercambios.isSelected()) listaPermisos.add(PanelNuevoEmpleado.PERM_INTERCAMBIOS);
		if (permisoProducto.isSelected()) listaPermisos.add(PanelNuevoEmpleado.PERM_PRODUCTOS);
		
		return listaPermisos;
	}
	
	/**
	 * Establece los permisos del empleado.
	 *
	 * @param nuevosPermisos Permisos a establecer
	 */
	public void setPermisos(List<String> nuevosPermisos) {
		permisos.clear();
		permisos.addAll(nuevosPermisos);
		
		String permisosString = String.join(", ", this.permisos);

		if (permisosString.isBlank())
			permisosString = "sin permisos";
		permisosString = "Permisos: " + permisosString;
		
		permisosLabel.setText(permisosString);
	}

	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param l Control que es añadido a los componentes
	 */
	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		modButton.addActionListener(l);
		deAltaButton.addActionListener(l);
		confirmarButton.addActionListener(l);

	}

	/**
	 * Establece EstadoDeAlta.
	 *
	 * @param deAlta nuevo valor
	 */
	public void setEstadoDeAlta(boolean deAlta) {
		estado.setText(deAlta ? "Empleado de alta" : "Empleado de baja");
		estado.setForeground(deAlta ? ColorPalette.GREEN.getColor() : ColorPalette.RED.getColor());
		
		deAltaButton.setText(deAlta ? "Dar de baja" : "Dar de alta");
		anadirBotones();
	}
	
	/**
	 * Refresca el panel para cargar la información más reciente
	 */
	@Override
	public void refreshDisplay() {
		revalidate();
		repaint();
	}
}