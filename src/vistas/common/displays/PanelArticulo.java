package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class PanelArticulo extends PanelDisplay {

	private static final long serialVersionUID = 1L;
	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;

	private static final double AVATAR_SIZE_PERC = 0.05; /* Tamaño del avatar (5% del alto) */
	private static final double AVATAR_NAME_SPACE = 0.01;
	private static final double ROW_GAP_PERC = 0.005; /* Espacio vertical entre filas (0.5% del alto) */
	private static final double COLUMN_GAP_PERC = 0.01; /* Espacio horizontal entre columnas (1% del ancho) */
	private static final double INTERESADO_HEIGHT_PERC = 0.08; /* Altura del área de "interesado en" (8% del alto) */

    private static final double BTN_SOLIC_WIDTH = 0.12;
    
    private static final double NAME_MAX_WIDTH = 0.27;
    private static final double USER_NAME_WIDTH = NAME_MAX_WIDTH - AVATAR_NAME_SPACE - AVATAR_SIZE_PERC - COLUMN_GAP_PERC;
    private static final double CATS_MAX_WIDTH = NAME_MAX_WIDTH;
    private static final double INTEREST_MAX_WIDTH = NAME_MAX_WIDTH;
    private static final int INTEREST_LINES_MAX = 5;
	
	private int spaceBetween;
	private JButton boton;
	
	private PanelDisplay parentPanel;

	/**
	 * Constructor para usar en carteras y busqueda
	 *
	 * @param nombreUsuario
	 * @param fotoDePerfil
	 * @param nombre
	 * @param foto
	 * @param descripcion
	 * @param interesadoEn
	 * @param estimacion
	 * @param estado
	 * @param actionName
	 * @param categorias
	 */
	public PanelArticulo(String nombreUsuario, String fotoDePerfil, String nombre, String foto, String descripcion,
			String interesadoEn, double estimacion, String estado, String actionName, String... categorias) {
		/* Llamada al constructor de la superclase (PanelProducto) */
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, foto, actionName);
		
		parentPanel = this;

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

		contentPanel.add(crearColumnaDerecha(t, interesadoEn, estimacion, rowGap, interesadoMaxHeight));

		this.add(contentPanel, BorderLayout.CENTER);
		
		JPanel wrapperEast = new JPanel();
		wrapperEast.setLayout(new BoxLayout(wrapperEast, BoxLayout.X_AXIS));
		wrapperEast.setOpaque(false);

		wrapperEast.add(Box.createHorizontalGlue());
		wrapperEast.add(Box.createHorizontalStrut(spaceBetween));
		
		JPanel rightPlaceHolder = panelEstado(estado);
		
		wrapperEast.add(rightPlaceHolder);
		int gapSize = (int) (maxCompHeight * (1 - BOTON_PERC_H) / 2);
		wrapperEast.add(Box.createHorizontalStrut(gapSize));
		
		this.add(wrapperEast, BorderLayout.EAST);
	}
	
	private JPanel panelEstado(String estado) {
	    TiendaFrame t = TiendaFrame.getInstance();

	    int maxWidth = t.getPixelsWidth(BTN_SOLIC_WIDTH);
	    int height = (int) (maxCompHeight * BOTON_PERC_H);

	    JPanel espacio = new JPanel();
	    espacio.setOpaque(false);
	    espacio.setLayout(new BorderLayout());
	    espacio.setPreferredSize(new Dimension(maxWidth, height));
	    espacio.setMinimumSize(new Dimension(maxWidth, height));
	    espacio.setMaximumSize(new Dimension(maxWidth, height));
	    
	    JLabel estadoPanel = new JLabel(estado);
	    estadoPanel.setFont(Fonts.TEXT.getFont());
	    espacio.add(estadoPanel);

	    return espacio;
	}

	private JPanel crearColumnaIzquierda(TiendaFrame t, String nombre, String[] categorias, String nombreUsuario,
			int avatarSize, int rowGap) {
		int nameWidth = t.getPixelsWidth(NAME_MAX_WIDTH);
		int catsWidth = t.getPixelsWidth(CATS_MAX_WIDTH);
		int usrNameWidth = t.getPixelsWidth(USER_NAME_WIDTH);
		
		JPanel columna = new JPanel();
		columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
		columna.setOpaque(false);

		/* Fila 1: Nombre del artículo */
		JLabel nombreLabel = ButtonFactory.newLabel(nombre, Fonts.BOLD);
		nombreLabel.setText(Fonts.truncar(nombre, nameWidth, Fonts.BOLD.getFont(), nombreLabel));
		nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
		columna.add(nombreLabel);
		columna.add(Box.createVerticalStrut(rowGap));

		/* Fila 2: Categorías */
		String cats = String.join(", ", categorias);
		
		if (!cats.isEmpty()) {
			JLabel categoriasLabel = ButtonFactory.newLabel(cats, Fonts.TEXT);
			categoriasLabel.setText(Fonts.truncar(cats, catsWidth, Fonts.TEXT.getFont(), categoriasLabel));
			categoriasLabel.setForeground(ColorPalette.LIGHT_PURPLE.getColor());
			nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
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
		filaUsuario.add(Box.createHorizontalStrut(t.getPixelsWidth(AVATAR_NAME_SPACE)));

		JLabel usuarioLabel = ButtonFactory.newLabel(nombreUsuario, Fonts.TEXT);
		usuarioLabel.setText(Fonts.truncar(nombreUsuario, usrNameWidth, Fonts.TEXT.getFont(), usuarioLabel));
		usuarioLabel.setAlignmentX(LEFT_ALIGNMENT);
		filaUsuario.add(usuarioLabel);
		filaUsuario.add(Box.createHorizontalGlue());

		columna.add(filaUsuario);
		columna.add(Box.createVerticalGlue());

		return columna;
	}

	private JPanel crearColumnaDerecha(TiendaFrame t, String interesadoEn, double estimacion, int rowGap, int interesadoMaxHeight) {
		int interesWidth = t.getPixelsWidth(INTEREST_MAX_WIDTH);
		
		JPanel columna = new JPanel(new BorderLayout());
		columna.setOpaque(false);
		
		JPanel panelIntereses = new JPanel();
		panelIntereses.setLayout(new BoxLayout(panelIntereses, BoxLayout.Y_AXIS));
		panelIntereses.setOpaque(false);

		/* Fila 1: Etiqueta "Está buscando:" */
		JLabel etiqueta = ButtonFactory.newLabel("Está buscando:", Fonts.SMALL_BOLD);
		etiqueta.setAlignmentX(LEFT_ALIGNMENT);
		panelIntereses.add(etiqueta);
		panelIntereses.add(Box.createVerticalStrut(rowGap));

		/* Fila 2: Texto de intereses */
		JTextArea interesadoArea = new JTextArea(interesadoEn);
		interesadoArea.setText(Fonts.truncar(interesadoEn, interesWidth * INTEREST_LINES_MAX, Fonts.TEXT.getFont(), interesadoArea));
		interesadoArea.setFont(Fonts.SMALL.getFont());
		interesadoArea.setLineWrap(true);
		interesadoArea.setWrapStyleWord(true);
		interesadoArea.setEditable(false);
		interesadoArea.setOpaque(false);
		interesadoArea.setForeground(ColorPalette.DARK_GREY.getColor());
		interesadoArea.setAlignmentX(LEFT_ALIGNMENT);
		interesadoArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, interesadoMaxHeight));
		
		interesadoArea.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Redirige el evento al panel que debería recibir el hover
		        parentPanel.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, parentPanel));
		    }
		    @Override
		    public void mouseExited(MouseEvent e) {
		        parentPanel.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, parentPanel));
		    }
		});

		panelIntereses.add(interesadoArea);
		panelIntereses.add(Box.createVerticalGlue());
		
		columna.add(panelIntereses, BorderLayout.CENTER);
		
		JPanel panelEstimacion = new JPanel();
		panelEstimacion.setLayout(new BoxLayout(panelEstimacion, BoxLayout.Y_AXIS));
		panelEstimacion.setOpaque(false);
		panelEstimacion.add(Box.createVerticalStrut(rowGap));
		
		String estimado;
		if(estimacion < 0) estimado = "Sin estimación";
		else estimado = estimacion + " €";
		
		JLabel etiquetaEst = ButtonFactory.newLabel("Estimación: " + estimado, Fonts.BOLD);
		etiquetaEst.setAlignmentX(LEFT_ALIGNMENT);
		panelEstimacion.add(etiquetaEst);
		panelEstimacion.add(Box.createVerticalStrut(rowGap));
		
		columna.add(panelEstimacion, BorderLayout.SOUTH);

		return columna;
	}

	/**
	 * Constructor para usar en Intercambios
	 *
	 * @param nombre
	 * @param foto
	 * @param descripcion
	 * @param interesadoEn
	 * @param estimacion
	 * @param estado
	 * @param actionName
	 * @param categorias
	 */
	public PanelArticulo(String nombre, String foto, String descripcion, String interesadoEn, double estimacion, String estado,
			String actionName, String[] categorias) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, foto, actionName);
		int nameWidth = TiendaFrame.getInstance().getPixelsWidth(NAME_MAX_WIDTH);
		int catsWidth = TiendaFrame.getInstance().getPixelsWidth(CATS_MAX_WIDTH);
		
		parentPanel = this;

		TiendaFrame t = TiendaFrame.getInstance();
		int rowGap = t.getPixelsHeight(ROW_GAP_PERC);

		JPanel contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		contentPanel.setLayout(new GridLayout(3, 1, rowGap, 0));
		
		/* Fila 1: Nombre del artículo */
		JLabel nombreLabel = ButtonFactory.newLabel(nombre, Fonts.BOLD);
		nombreLabel.setText(Fonts.truncar(nombre, nameWidth, Fonts.BOLD.getFont(), nombreLabel));
		nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
		contentPanel.add(nombreLabel);

		/* Fila 2: Categorías */
		String cats = String.join(", ", categorias);
		
		if (!cats.isEmpty()) {
			JLabel categoriasLabel = ButtonFactory.newLabel(cats, Fonts.TEXT);
			categoriasLabel.setText(Fonts.truncar(cats, catsWidth, Fonts.TEXT.getFont(), categoriasLabel));
			categoriasLabel.setForeground(ColorPalette.LIGHT_PURPLE.getColor());
			nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
			contentPanel.add(categoriasLabel);
		}
		
		/* Fila 3: Estado y estimación */
		JPanel thirdRow = new JPanel();
		thirdRow.setOpaque(false);
		thirdRow.setLayout(new GridLayout(1, 2));
		
		String estadoString = "Estado: " + estado;
		JLabel estadoLabel = ButtonFactory.newLabel(estadoString, Fonts.BOLD);
		estadoLabel.setText(Fonts.truncar(estadoString, nameWidth/2, Fonts.BOLD.getFont(), estadoLabel));
		estadoLabel.setAlignmentX(LEFT_ALIGNMENT);
		thirdRow.add(estadoLabel);
		
		String estimacionStr = "Estimación: " + estimacion + " €";
		if(estimacion < 0) estimacionStr = "Sin estimación";
		
		JLabel estimacionLbl = ButtonFactory.newLabel(estimacionStr, Fonts.BOLD);
		estimacionLbl.setText(Fonts.truncar(estimacionStr, nameWidth/2, Fonts.BOLD.getFont(), estimacionLbl));
		estimacionLbl.setAlignmentX(LEFT_ALIGNMENT);
		thirdRow.add(estimacionLbl);
		
		contentPanel.add(thirdRow);

		this.add(contentPanel, BorderLayout.CENTER);
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
