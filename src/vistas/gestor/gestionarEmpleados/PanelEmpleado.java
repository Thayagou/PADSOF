package vistas.gestor.gestionarEmpleados;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import vistas.common.InvisibleCheckBox;
import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelEmpleado extends PanelDisplay {
	private static final long serialVersionUID = 1L;

	public static final String CONFIRMAR_ACTION = "Confirmar";
	public static final String MODIFICAR_ACTION = "Modificar información y permisos";
	public static final String DE_ALTA_ACTION = "Alta";

	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;

	private boolean deAlta;
	private List<String> permisos = new ArrayList<>();
	private JButton modButton;
	private JButton deAltaButton;
	private JLabel permisosLabel;
	private JLabel estado;
	JPanel eastPanel;
	
	private JButton confirmarButton;
	private boolean expanded = false;
	private JPanel expandedPanel;
	private Dimension originalMaxSize;
	
	private InvisibleCheckBox permisoProducto = ButtonFactory.newInvisibleCheckBox("Productos", "Productos", ColorPalette.BLACK,ColorPalette.GREY);
	private InvisibleCheckBox permisoPedidos  = ButtonFactory.newInvisibleCheckBox("Pedidos", "Pedidos", ColorPalette.BLACK,ColorPalette.GREY);
	private InvisibleCheckBox permisoIntercambios  = ButtonFactory.newInvisibleCheckBox("Intercambios", "Intercambios", ColorPalette.BLACK,ColorPalette.GREY);


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

		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		modButton = ButtonFactory.newRoundedButton(MODIFICAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		// f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		ButtonFactory.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		deAltaButton = ButtonFactory.newRoundedButton(deAlta ? "Dar de baja" : "Dar de alta", (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		deAltaButton.setActionCommand(DE_ALTA_ACTION);
		// f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		ButtonFactory.paintButton(deAltaButton, ColorPalette.RED, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(deAltaButton, ColorPalette.RED, ColorPalette.LIGHT_RED);
		// modButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 3);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(deAltaButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));

		this.add(eastPanel, BorderLayout.EAST);		
		
		confirmarButton = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		ButtonFactory.paintButton(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
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
		int expandedHeight = (int)(originalMaxSize.height * 1.5);
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
	
	public List<String> getPermisos() {
		List<String> listaPermisos = new ArrayList<>();
		
		if (permisoPedidos.isSelected()) listaPermisos.add(PanelNuevoEmpleado.PERM_PEDIDOS);
		if (permisoIntercambios.isSelected()) listaPermisos.add(PanelNuevoEmpleado.PERM_INTERCAMBIOS);
		if (permisoProducto.isSelected()) listaPermisos.add(PanelNuevoEmpleado.PERM_PRODUCTOS);
		
		return listaPermisos;
	}
	
	public void setPermisos(List<String> nuevosPermisos) {
		permisos.clear();
		permisos.addAll(nuevosPermisos);
		
		String permisosString = String.join(", ", this.permisos);

		if (permisosString.isBlank())
			permisosString = "sin permisos";
		permisosString = "Permisos: " + permisosString;
		
		permisosLabel.setText(permisosString);
	}

	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		modButton.addActionListener(l);
		deAltaButton.addActionListener(l);
		confirmarButton.addActionListener(l);

	}

	public void setEstadoDeAlta(boolean deAlta) {
		estado.setText(deAlta ? "Empleado de alta" : "Empleado de baja");
		estado.setForeground(deAlta ? ColorPalette.GREEN.getColor() : ColorPalette.RED.getColor());
		
		deAltaButton.setText(deAlta ? "Dar de baja" : "Dar de alta");
		deAltaButton.setPreferredSize(new Dimension(maxCompHeight, (int) (maxCompHeight * BOTON_PERC_H)));
	}
	
	@Override
	public void refreshDisplay() {
		revalidate();
		repaint();
	}
}