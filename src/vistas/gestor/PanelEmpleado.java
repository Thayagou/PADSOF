package vistas.gestor;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.venta.productos.Producto;
import vistas.common.PanelDisplay;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelEmpleado extends PanelDisplay {
	private static final long serialVersionUID = 1L;

	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	
	
	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();
	
	private String nombre;
	private String fotoDePerfil;
	private boolean deAlta;
	private List<String> permisos;

	public PanelEmpleado(String nombre, boolean deAlta, String...permisos) {
		super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT, FOTO_W_PERC, "producto.png", "");
		this.nombre = nombre;
		this.deAlta = deAlta;
		//this.fotoDePerfil = fotoDePerfil;
		this.permisos = List.of(permisos);
		
		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();
		
		
		ButtonFactory factory = new ButtonFactory();

		/* Info: nombre + permisos */
		JPanel info = new JPanel();
		info.setOpaque(false);
		info.setLayout(new GridLayout(3, 1));
		
		/*Primera fila: nombre */
		JPanel firstRow = new JPanel();
		firstRow.setOpaque(false);
		firstRow.setLayout(new BorderLayout(10, 0));
		
		JLabel nombreLabel = new JLabel(nombre);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		firstRow.add(nombreLabel, BorderLayout.WEST);

		info.add(firstRow);
		
		/*Segunda fila: permisos*/
		JPanel permisosRow = new JPanel();
		permisosRow.setLayout(new BorderLayout(10, 0));
		permisosRow.setOpaque(false);
		
		String permisosString = String.join(", ", this.permisos);
		
		if (permisosString.isBlank()) permisosString = "sin permisos";
		JLabel permisosLabel = factory.newLabel("Permisos: " + permisosString, Fonts.TEXT);
		permisosLabel.setForeground(ColorPalette.PURPLE.getColor());
		permisosRow.add(permisosLabel, BorderLayout.WEST);
		info.add(permisosRow);
		
		/*Tercera fila: de alta*/
		JPanel deAltaRow = new JPanel();
		deAltaRow.setLayout(new BorderLayout(10, 0));
		deAltaRow.setOpaque(false);
		
		JLabel estado = new JLabel(deAlta ? "Empleado de alta" : "Empleado de baja");
		estado.setFont(Fonts.BOLD.getFont());
		
		if (this.deAlta)estado.setForeground(ColorPalette.GREEN.getColor());
		else estado.setForeground(ColorPalette.RED.getColor());
		deAltaRow.add(estado, BorderLayout.WEST);
		info.add(deAltaRow);

		add(info, BorderLayout.CENTER);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H)));
		
		ButtonFactory f = new ButtonFactory();
		
		JButton modButton = f.newRoundedButton("Modificar información y permisos", (int)(maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
				//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		JButton borrarButton = f.newRoundedButton(deAlta ? "Dar de baja" : "Dar de alta", (int)(maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(borrarButton, ColorPalette.RED, ColorPalette.WHITE);
		f.addMouseMecanics(borrarButton, ColorPalette.RED, ColorPalette.LIGHT_RED);
		//modButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 3);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(borrarButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		this.add(eastPanel, BorderLayout.EAST);

		
	}
}