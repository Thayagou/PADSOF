package vistas.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class PanelArticulo extends PanelDisplay {

	private static final long serialVersionUID = 1L;
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	private static final int MAX_DESC = 40;
	private static final double CENTER_DIST = 0.7;

	private static final double AVATAR_SIZE_PERC = 0.05; /* Tamaño del avatar (5% del alto) */
	private static final double ROW_GAP_PERC = 0.005; /* Espacio vertical entre filas (0.5% del alto) */
	private static final double COLUMN_GAP_PERC = 0.01; /* Espacio horizontal entre columnas (1% del ancho) */
	private static final double INTERESADO_HEIGHT_PERC = 0.08; /* Altura del área de "interesado en" (8% del alto) */

    private static final double BTN_SOLIC_WIDTH = 0.12;
    
    private static final int NAME_MAX_CHARS = 50;
    private static final int USER_NAME_MAX_CHARS = 50;
    private static final int CATS_MAX_CHARS = 50;
    private static final int INTEREST_MAX_CHARS = 50;
	
	private int spaceBetween;
	private JPanel centerPanel;
	private GridBagConstraints gbc;
	private JButton boton;

	public PanelArticulo(String nombreUsuario, String fotoDePerfil, String nombre, String foto, String descripcion,
			String interesadoEn, double estimacion, String estado, String actionName, String... categorias) {
		/* Llamada al constructor de la superclase (PanelProducto) */
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, foto, actionName);

		TiendaFrame t = TiendaFrame.getInstance();
		int avatarSize = t.getPixelsHeight(AVATAR_SIZE_PERC);
		int rowGap = t.getPixelsHeight(ROW_GAP_PERC);
		int colGap = t.getPixelsWidth(COLUMN_GAP_PERC);
		int interesadoMaxHeight = t.getPixelsHeight(INTERESADO_HEIGHT_PERC);

		/* Contenedor principal con GridBagLayout para las dos columnas */
		JPanel contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new GridLayout(1, 2, colGap, 0));

		contentPanel.add(crearColumnaIzquierda(t, nombre, categorias, nombreUsuario, avatarSize, rowGap));

		contentPanel.add(crearColumnaDerecha(t, interesadoEn, rowGap, interesadoMaxHeight));

		this.add(contentPanel, BorderLayout.CENTER);
		
		JPanel wrapperEast = new JPanel();
		wrapperEast.setLayout(new BoxLayout(wrapperEast, BoxLayout.X_AXIS));
		wrapperEast.setOpaque(false);

		wrapperEast.add(Box.createHorizontalGlue());
		wrapperEast.add(Box.createHorizontalStrut(spaceBetween));
		
		JPanel rightPlaceHolder = crearEspacioBoton();
		
		wrapperEast.add(rightPlaceHolder);
		int gapSize = (int) (maxCompHeight * (1 - BOTON_PERC_H) / 2);
		wrapperEast.add(Box.createHorizontalStrut(gapSize));
		
		this.add(wrapperEast, BorderLayout.EAST);
	}
	
	private JPanel crearEspacioBoton() {
	    TiendaFrame t = TiendaFrame.getInstance();

	    int maxWidth = t.getPixelsWidth(BTN_SOLIC_WIDTH);
	    int height = (int) (maxCompHeight * BOTON_PERC_H);

	    JPanel espacio = new JPanel();
	    espacio.setOpaque(false);
	    espacio.setPreferredSize(new Dimension(maxWidth, height));
	    espacio.setMinimumSize(new Dimension(maxWidth, height));
	    espacio.setMaximumSize(new Dimension(maxWidth, height));

	    return espacio;
	}
	
	private String truncarTexto(String texto, int maxChars) {
		if (texto == null) return "";
		if (texto.length() <= maxChars) return texto;
		return texto.substring(0, maxChars - 3) + "...";
	}

	private JPanel crearColumnaIzquierda(TiendaFrame t, String nombre, String[] categorias, String nombreUsuario,
			int avatarSize, int rowGap) {
		JPanel columna = new JPanel();
		columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
		columna.setOpaque(false);

		/* Fila 1: Nombre del artículo */
		JLabel nombreLabel = ButtonFactory.newLabel(truncarTexto(nombre, NAME_MAX_CHARS), Fonts.BOLD);
		nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
		columna.add(nombreLabel);
		columna.add(Box.createVerticalStrut(rowGap));

		/* Fila 2: Categorías (en morado, una debajo de otra con separación reducida) */
		String cats = String.join(", ", categorias);
		
		if (!cats.isEmpty()) {
			JLabel categoriasLabel = new JLabel(truncarTexto(cats, CATS_MAX_CHARS));
			categoriasLabel.setFont(Fonts.TEXT.getFont());
			categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
			columna.add(categoriasLabel);
		}

		/* Fila 3: Avatar + Nombre de usuario en horizontal */
		JPanel filaUsuario = new JPanel();
		filaUsuario.setLayout(new BoxLayout(filaUsuario, BoxLayout.X_AXIS));
		filaUsuario.setOpaque(false);
		filaUsuario.setAlignmentX(LEFT_ALIGNMENT);

		JPanel avatarPanel = PanelFactory.buildAvatar();
		avatarPanel.setPreferredSize(new Dimension(avatarSize, avatarSize));
		avatarPanel.setMaximumSize(new Dimension(avatarSize, avatarSize));
		filaUsuario.add(avatarPanel);
		filaUsuario.add(Box.createHorizontalStrut(10)); /* Separación fija pequeña entre avatar y texto */

		JLabel usuarioLabel = ButtonFactory.newLabel(truncarTexto(nombreUsuario, USER_NAME_MAX_CHARS), Fonts.TEXT);
		usuarioLabel.setAlignmentX(LEFT_ALIGNMENT);
		filaUsuario.add(usuarioLabel);
		filaUsuario.add(Box.createHorizontalGlue()); /* Empuja a la izquierda */

		columna.add(filaUsuario);
		columna.add(Box.createVerticalGlue()); /* Empuja todo hacia arriba */

		return columna;
	}

	private JPanel crearColumnaDerecha(TiendaFrame t, String interesadoEn, int rowGap, int interesadoMaxHeight) {
		JPanel columna = new JPanel();
		columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
		columna.setOpaque(false);

		/* Fila 1: Etiqueta "Está buscando:" */
		JLabel etiqueta = ButtonFactory.newLabel("Está buscando:", Fonts.SMALL_BOLD);
		etiqueta.setAlignmentX(LEFT_ALIGNMENT);
		columna.add(etiqueta);
		columna.add(Box.createVerticalStrut(rowGap));

		/* Fila 2: Texto de "interesadoEn" con scroll si es necesario */
		JTextArea interesadoArea = new JTextArea(truncarTexto(interesadoEn, INTEREST_MAX_CHARS));
		interesadoArea.setFont(Fonts.SMALL.getFont());
		interesadoArea.setLineWrap(true);
		interesadoArea.setWrapStyleWord(true);
		interesadoArea.setEditable(false);
		interesadoArea.setOpaque(false);
		interesadoArea.setForeground(ColorPalette.DARK_GREY.getColor());
		interesadoArea.setAlignmentX(LEFT_ALIGNMENT);
		interesadoArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, interesadoMaxHeight));

		columna.add(interesadoArea);

		columna.add(Box.createVerticalGlue()); /* Empuja el contenido hacia arriba */

		return columna;
	}

	@Deprecated
	public PanelArticulo(String nombre, String foto, String descripcion, String interesadoEn, double estimacion,
			String estado, String actionName, String... categorias) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, foto, actionName);

		TiendaFrame t = TiendaFrame.getInstance();
		spaceBetween = t.getPixelsWidth(0.1f);
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

		JLabel nombreLabel = new JLabel(nombre);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		firstRow.add(nombreLabel, BorderLayout.WEST);

		info.add(firstRow);

		/* Segunda fila: descripcion */
		if (descripcion != null && descripcion.length() > MAX_DESC)
			descripcion = descripcion.substring(0, MAX_DESC) + "...";
		JLabel descripcionLabel = ButtonFactory.newLabel(descripcion, Fonts.SMALL);
		descripcionLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		info.add(descripcionLabel);

		/* Tercera file: interesado en */
		interesadoEn = "Interesado en: " + interesadoEn;
		if (interesadoEn.length() > MAX_DESC)
			interesadoEn = interesadoEn.substring(0, MAX_DESC) + "...";
		JLabel interesadoLabel = ButtonFactory.newLabel(interesadoEn, Fonts.SMALL);
		interesadoLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		info.add(interesadoLabel);

		/* Cuarta fila: categorias + precio */
		JPanel thirdRow = new JPanel();
		thirdRow.setLayout(new BorderLayout(10, 0));
		thirdRow.setOpaque(false);

		String cats = String.join(", ", categorias);

		if (!cats.isEmpty()) {
			if (cats.length() > MAX_DESC)
				cats = cats.substring(0, MAX_DESC) + "...";
			JLabel categoriasLabel = new JLabel(cats);
			categoriasLabel.setFont(Fonts.TEXT.getFont());
			categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
			thirdRow.add(categoriasLabel, BorderLayout.WEST);
		}

		String valoracion = estimacion < 0 ? "Pendiente de valoracion"
				: "Estado: " + estado + " " + "Estimacion: " + String.format("%.2f €", estimacion);
		JLabel valoracionLabel = ButtonFactory.newLabel(valoracion, Fonts.TEXT);
		valoracionLabel.setForeground(Color.BLACK);
		thirdRow.add(valoracionLabel, BorderLayout.CENTER);

		info.add(thirdRow);

		gbc.weightx = CENTER_DIST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(info, gbc);

		add(centerPanel, BorderLayout.CENTER);
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
		int maxWidth = t.getPixelsWidth(BTN_SOLIC_WIDTH);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int) (maxCompHeight * BOTON_PERC_H)));

		boton = ButtonFactory.newRoundedButton(nombre, (int) (maxCompHeight), maxWidth, 0.5f);
		boton.setActionCommand(nombre);
		ButtonFactory.paintButton(boton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(boton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		int gapSize = (int) (maxCompHeight * (1 - BOTON_PERC_H) / 2);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(boton);
		eastPanel.add(Box.createVerticalStrut(gapSize));

		wrapperEast.add(eastPanel);
		wrapperEast.add(Box.createHorizontalStrut(gapSize));

		add(wrapperEast, BorderLayout.EAST);
	}

	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		if (boton != null)
			boton.addActionListener(l);
	}
}
