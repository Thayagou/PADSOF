package vistas.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelArticulo extends PanelDisplay {

	private static final long serialVersionUID = 1L;
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final int MAX_DESC = 120;
	private static final double CENTER_DIST = 0.7;

	private String nombre;
	private String descripcion;
	private String interesadoEn;
	private double estimacion;
	private int spaceBetween;
	private JPanel centerPanel;
	private GridBagConstraints gbc;
	private JButton boton;

	public PanelArticulo(String nombreUsuario, String fotoDePerfil, String nombre, String descripcion,
			String interesadoEn, double estimacion, String estado, String actionName, String... categorias) {
		this(nombre, descripcion, interesadoEn, estimacion, estado, actionName, categorias);

		JPanel usuario = new JPanel();
		usuario.setOpaque(false);
		usuario.setLayout(new BoxLayout(usuario, BoxLayout.Y_AXIS));

		ButtonFactory f = new ButtonFactory();
		JLabel iconoUsuario = anadirIcono(fotoDePerfil, 0.3f, BOTON_PERC_W);
		JLabel labelNombre = f.newLeftAlignedLabel(nombreUsuario, Fonts.TEXT);
		// labelNombre.setMaximumSize(maxSize);
		labelNombre.setAlignmentX(LEFT_ALIGNMENT);
		JButton verCartera = f.newButton("Ver cartera");
		verCartera.setFont(Fonts.BOLD.getFont());
		// f.addMouseMecanics(verCartera, null, null);
		verCartera.setMaximumSize(new Dimension(maxCompHeight, TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W)));
		// JButton iconoUsuario = f.newIconButton(nombreUsuario, maxCompHeight,
		// maxCompHeight/2, fotoDePerfil);
		// iconoUsuario.setMaximumSize(new Dimension(t.getPixelsWidth(BOTON_PERC_W),
		// maxCompHeight));
		int gapSize = (int) (maxCompHeight * (1 - 2 * BOTON_PERC_H) / 2);
		usuario.add(Box.createVerticalStrut(gapSize));
		// usuario.add(iconoUsuario);
		// usuario.setMaximumSize(new Dimension(t.getPixelsWidth(BOTON_PERC_W),
		// maxCompHeight));
		usuario.add(iconoUsuario);
		usuario.add(labelNombre);
		usuario.add(verCartera);
		usuario.add(Box.createVerticalStrut(gapSize));

		gbc.weightx = 1 - CENTER_DIST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(usuario, gbc);
		// statsPanel.add(Box.createHorizontalStrut(gapSize));
	}

	public PanelArticulo(String nombre, String descripcion, String interesadoEn, double estimacion, String estado,
			String actionName, String... categorias) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, "producto.png", actionName);
		// super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT, "Ver producto:");
		// setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.interesadoEn = interesadoEn;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.estimacion = estimacion;

		TiendaFrame t = TiendaFrame.getInstance();
		spaceBetween = t.getPixelsWidth(0.1f);
		// setOpaque(false);
		centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;

		/* Info: estrellas + nombre + descripción + precio + categorías */

		JPanel info = new JPanel();
		info.setOpaque(false);
		info.setLayout(new GridLayout(4, 1));

		/* Primera fila: estrellas + nombre */
		JPanel firstRow = new JPanel();
		firstRow.setOpaque(false);
		firstRow.setLayout(new BorderLayout(10, 0));
		// firstRow.add(buildEstrellas(t, puntuacionMedia), BorderLayout.CENTER);

		JLabel nombreLabel = new JLabel(nombre);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		firstRow.add(nombreLabel, BorderLayout.WEST);

		info.add(firstRow);

		/* Segunda fila: descripcion */
		if (descripcion != null && descripcion.length() > MAX_DESC)
			descripcion = descripcion.substring(0, MAX_DESC) + "...";
		ButtonFactory f = new ButtonFactory();
		JLabel descripcionLabel = f.newLabel(descripcion, Fonts.SMALL);
		descripcionLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		info.add(descripcionLabel);

		/* Tercera file: interesado en */
		interesadoEn = "Interesado en: " + interesadoEn;
		if (interesadoEn.length() > MAX_DESC)
			interesadoEn = interesadoEn.substring(0, MAX_DESC) + "...";
		JLabel interesadoLabel = f.newLabel(interesadoEn, Fonts.SMALL);
		interesadoLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		info.add(interesadoLabel);

		/* Cuarta fila: categorias + precio */
		JPanel thirdRow = new JPanel();
		thirdRow.setLayout(new BorderLayout(10, 0));
		thirdRow.setOpaque(false);

		String cats = String.join(", ", categorias);

		if (!cats.isEmpty()) {
			JLabel categoriasLabel = new JLabel(cats);
			categoriasLabel.setFont(Fonts.TEXT.getFont());
			categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
			thirdRow.add(categoriasLabel, BorderLayout.WEST);
		}

		String valoracion = estimacion < 0 ? "Pendiente de valoracion"
				: "Estado: " + estado + " " + "Estimacion: " + String.format("%.2f €", estimacion);
		JLabel valoracionLabel = f.newLabel(valoracion, Fonts.TEXT);
		valoracionLabel.setForeground(Color.BLACK);
		thirdRow.add(valoracionLabel, BorderLayout.CENTER);

		info.add(thirdRow);

		gbc.weightx = CENTER_DIST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(info, gbc);

		add(centerPanel, BorderLayout.CENTER);

		// add(foto, BorderLayout.WEST);
		// add(articulo, BorderLayout.CENTER);
	}

	public void inicializarBoton(String nombre) {
		JPanel wrapperEast = new JPanel();
		wrapperEast.setLayout(new BoxLayout(wrapperEast, BoxLayout.X_AXIS));
		wrapperEast.setOpaque(false);

		wrapperEast.add(Box.createHorizontalGlue());
		wrapperEast.add(Box.createHorizontalStrut(spaceBetween));

		TiendaFrame t = TiendaFrame.getInstance();
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));
		// eastPanel.setMaximumSize(new Dimension(maxWidth, (int)(maxCompHeight *
		// BOTON_PERC_H)));

		ButtonFactory f = new ButtonFactory();

		boton = f.newRoundedButton(nombre, (int) (maxCompHeight), maxCompHeight, 0.5f);
		boton.setActionCommand(nombre);
		// f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(boton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(boton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		int gapSize = (int) (maxCompHeight * (1 - BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(boton);
		eastPanel.add(Box.createVerticalStrut(gapSize));

		wrapperEast.add(eastPanel);
		wrapperEast.add(Box.createHorizontalStrut(gapSize));

		add(wrapperEast, BorderLayout.EAST);
	}

	private JLabel anadirIcono(String imageName, double fotoHPerc, double fotoWPerc) {
		//add(Box.createHorizontalGlue());
		//add(Box.createHorizontalStrut(spaceBetween));
		ButtonFactory factory = new ButtonFactory();
		TiendaFrame t = TiendaFrame.getInstance();
		int fotoW = t.getPixelsWidth(fotoWPerc);

		// JLabel fotoLabel = new JLabel("FOTO", JLabel.CENTER);
		JLabel fotoLabel = new JLabel(factory.loadImageInBounds(imageName, (int) (fotoHPerc * maxCompHeight), fotoW));
		fotoLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		fotoLabel.setFont(Fonts.BOLD.getFont());

		return fotoLabel;
	}
}
