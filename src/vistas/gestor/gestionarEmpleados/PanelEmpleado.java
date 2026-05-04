package vistas.gestor.gestionarEmpleados;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import javax.swing.*;
import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelEmpleado extends PanelDisplay {
	private static final long serialVersionUID = 1L;

	public static final String MODIFICAR_ACTION = "Modificar información y permisos";
	public static final String DE_ALTA_ACTION = "Alta";

	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;

	private boolean deAlta;
	private List<String> permisos;
	private JButton modButton;
	private JButton deAltaButton;
	private JLabel estado;
	JPanel eastPanel;

	public PanelEmpleado(String nombre, boolean deAlta, String... permisos) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, "producto.png", "");
		this.deAlta = deAlta;
		// this.fotoDePerfil = fotoDePerfil;
		this.permisos = List.of(permisos);

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
		JLabel permisosLabel = ButtonFactory.newLabel("Permisos: " + permisosString, Fonts.TEXT);
		permisosLabel.setForeground(ColorPalette.PURPLE.getColor());
		permisosRow.add(permisosLabel, BorderLayout.WEST);
		info.add(permisosRow);

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
	}

	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		modButton.addActionListener(l);
		deAltaButton.addActionListener(l);

	}

	public void setEstadoDeAlta(boolean deAlta) {
		estado.setText(deAlta ? "Empleado de alta" : "Empleado de baja");
		estado.setForeground(deAlta ? ColorPalette.GREEN.getColor() : ColorPalette.RED.getColor());
		
		deAltaButton.setText(deAlta ? "Dar de baja" : "Dar de alta");
		deAltaButton.setPreferredSize(new Dimension(maxCompHeight, (int) (maxCompHeight * BOTON_PERC_H)));
	}
	
	@Override
	public void refreshDisplay() {
		super.refreshDisplay();
	}
}