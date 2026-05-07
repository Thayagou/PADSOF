package vistas.common.assets;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.*;

/**
 * Panel reutilizable que muestra una tarjeta con cabecera, campos de texto y un
 * botón de acción. Uso típico: formularios de login, registro, búsqueda, etc.
 */
public class PanelFormulario extends JPanel {

	private static final long serialVersionUID = 1L;

	/* ── Macros de layout (relativos a resolución base 1920×1080) ── */
	private static final double CORNER_RADIUS_MACRO = 50.0 / 1920.0;

	private static final double HEADER_PADDING_TOP_MACRO = 12.0 / 1080.0;
	private static final double HEADER_PADDING_LEFT_MACRO = 16.0 / 1920.0;
	private static final double HEADER_PADDING_BOTTOM_MACRO = 12.0 / 1080.0;
	private static final double HEADER_PADDING_RIGHT_MACRO = 16.0 / 1920.0;

	private static final double BODY_PADDING_TOP_MACRO = 16.0 / 1080.0;
	private static final double BODY_PADDING_LEFT_MACRO = 32.0 / 1920.0;
	private static final double BODY_PADDING_BOTTOM_MACRO = 8.0 / 1080.0;
	private static final double BODY_PADDING_RIGHT_MACRO = 32.0 / 1920.0;

	private static final double STRUT_LABEL_FIELD_MACRO = 4.0 / 1080.0;
	private static final double STRUT_BETWEEN_FIELDS_MACRO = 12.0 / 1080.0;
	private static final double STRUT_BEFORE_BUTTON_MACRO = 4.0 / 1080.0;
	private static final double STRUT_AFTER_BUTTON_MACRO = 16.0 / 1080.0;

	private static final double BTN_HEIGHT_MACRO = 50.0 / 1080.0;
	private static final double BTN_WIDTH_MACRO = 150.0 / 1920.0;
	private static final double BTN_ROUNDNESS = 1.0;

	/* ── Estado interno ── */
	private final JTextField[] fields;
	private final JButton boton;

	private boolean arrayContainsValue(Integer[] array, int value) {
		for (Integer i : array) {
			if (i.equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Construye el formulario con campos visibles y ocultos
	 *
	 * @param titulo     Texto de la cabecera de la tarjeta.
	 * @param botonTexto Texto del botón de acción.
	 * @param passIndexes Array con las posiciones de los campos ocultos, empezando desde 1
	 * @param labels     Etiquetas de cada campo de texto, en orden.
	 */
	public PanelFormulario(String titulo, String botonTexto, Integer[] passIndexes, String... labels) {
		setOpaque(false);
		setLayout(new GridBagLayout()); /* Centra la tarjeta en el panel */

		TiendaFrame t = TiendaFrame.getInstance();

		/* ── Conversión de macros a píxeles ── */
		int cornerRadius = t.getPixelsWidth(CORNER_RADIUS_MACRO);
		int headerTop = t.getPixelsHeight(HEADER_PADDING_TOP_MACRO);
		int headerLeft = t.getPixelsWidth(HEADER_PADDING_LEFT_MACRO);
		int headerBottom = t.getPixelsHeight(HEADER_PADDING_BOTTOM_MACRO);
		int headerRight = t.getPixelsWidth(HEADER_PADDING_RIGHT_MACRO);
		int bodyTop = t.getPixelsHeight(BODY_PADDING_TOP_MACRO);
		int bodyLeft = t.getPixelsWidth(BODY_PADDING_LEFT_MACRO);
		int bodyBottom = t.getPixelsHeight(BODY_PADDING_BOTTOM_MACRO);
		int bodyRight = t.getPixelsWidth(BODY_PADDING_RIGHT_MACRO);
		int strutLabelField = t.getPixelsHeight(STRUT_LABEL_FIELD_MACRO);
		int strutFields = t.getPixelsHeight(STRUT_BETWEEN_FIELDS_MACRO);
		int strutBeforeBtn = t.getPixelsHeight(STRUT_BEFORE_BUTTON_MACRO);
		int strutAfterBtn = t.getPixelsHeight(STRUT_AFTER_BUTTON_MACRO);
		int btnHeight = t.getPixelsHeight(BTN_HEIGHT_MACRO);
		int btnWidth = t.getPixelsWidth(BTN_WIDTH_MACRO);

		/* ── Campos de texto ── */
		fields = new JTextField[labels.length];
		for (int i = 0; i < labels.length; i++) {
			if (arrayContainsValue(passIndexes, i + 1)) {
				fields[i] = new JPasswordField(15);
			} else {
				fields[i] = new JTextField(15);
			}
			fields[i].setFont(Fonts.TEXT.getFont());
			fields[i].setAlignmentX(CENTER_ALIGNMENT);
		}

		/* ── Botón ── */
		boton = ButtonFactory.newRoundedButton(botonTexto, btnHeight, btnWidth, BTN_ROUNDNESS);
		boton.setActionCommand(botonTexto);
		boton.setBackground(ColorPalette.PURPLE.getColor());
		boton.setForeground(ColorPalette.WHITE.getColor());
		// boton.setOpaque(true);
		boton.setBorderPainted(false);
		ButtonFactory.addMouseMecanics(boton, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

		/* ── Tarjeta redondeada ── */
		RoundedPanel card = new RoundedPanel(cornerRadius);
		card.setBackground(ColorPalette.WHITE.getColor());
		card.setLayout(new BorderLayout());

		/* ── Cabecera ── */
		JLabel header = new JLabel(titulo, JLabel.CENTER);
		header.setFont(Fonts.TITLE3.getFont());
		header.setForeground(ColorPalette.WHITE.getColor());
		header.setOpaque(true);
		header.setBackground(ColorPalette.BG_BLUE.getColor());
		header.setBorder(BorderFactory.createEmptyBorder(headerTop, headerLeft, headerBottom, headerRight));

		/* ── Cuerpo ── */
		JPanel body = new JPanel();
		body.setOpaque(false);
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
		body.setBorder(BorderFactory.createEmptyBorder(bodyTop, bodyLeft, bodyBottom, bodyRight));

		for (int i = 0; i < labels.length; i++) {
			JLabel lbl = new JLabel(labels[i]);
			lbl.setFont(Fonts.TEXT.getFont());
			lbl.setAlignmentX(LEFT_ALIGNMENT);

			fields[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, fields[i].getPreferredSize().height));

			body.add(lbl);
			body.add(Box.createVerticalStrut(strutLabelField));
			body.add(fields[i]);
			body.add(Box.createVerticalStrut(strutFields));
		}

		boton.setAlignmentX(CENTER_ALIGNMENT);
		body.add(Box.createVerticalStrut(strutBeforeBtn));
		body.add(boton);
		body.add(Box.createVerticalStrut(strutAfterBtn));

		card.add(header, BorderLayout.NORTH);
		card.add(body, BorderLayout.CENTER);

		add(card); /* GridBagLayout lo centra automáticamente */
	}
	
	/**
	 * Constructor con todos los campos visibles
	 *
	 * @param titulo Titulo que pondrá en la cabecera
	 * @param botonTexto Texto del botón
	 * @param labels Etiquetas de los campos
	 */
	public PanelFormulario(String titulo, String botonTexto, String... labels) {
		this(titulo, botonTexto, new Integer[] {}, labels);
	}

	/**
	 * Registra el listener que se ejecutará al pulsar el botón.
	 *
	 * @param c Controlador que implementa ActionListener.
	 */
	public void setControlador(ActionListener c) {
		boton.addActionListener(c);
	}

	/**
	 * Devuelve el texto del campo indicado por índice.
	 *
	 * @param index Posición del campo (mismo orden que el array labels).
	 * @return Texto introducido por el usuario.
	 * @throws ArrayIndexOutOfBoundsException si el índice no es válido.
	 */
	public String getCampo(int index) {
		return fields[index].getText();
	}

	/**
	 * Devuelve el texto de todos los campos como array.
	 *
	 * @return Array con el contenido de cada campo, en el mismo orden que labels.
	 */
	public String[] getCampos() {
		String[] valores = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			valores[i] = fields[i].getText();
		}
		return valores;
	}

	/**
	 * Limpia todos los campos de texto.
	 */
	public void limpiar() {
		for (JTextField field : fields) {
			field.setText("");
		}
	}
}