package vistas.common;

import java.awt.*;
import javax.swing.*;

import vistas.herramientas.*;

/**
 * Dialogo modal de mensaje para la aplicacion.
 *
 * Uso basico: new VentanaMensaje("Operacion completada."); new
 * VentanaMensaje("El campo no puede estar vacio.", "Error",
 * VentanaMensaje.ERROR);
 *
 * Tipos disponibles: VentanaMensaje.INFO, VentanaMensaje.ERROR,
 * VentanaMensaje.AVISO. El tipo solo afecta al color de la cabecera; el resto
 * del dialogo es siempre identico.
 */
public class VentanaMensaje {

	/* Tipos de mensaje */
	public static final int INFO = 0;
	public static final int ERROR = 1;
	public static final int AVISO = 2;

	/* Dimensiones del dialogo */
	private static final double DIALOG_W = 0.30;
	private static final double DIALOG_H = 0.22;

	/* Dimensiones del boton Aceptar */
	private static final double BTN_W = 0.10;
	private static final double BTN_H = 0.05;

	/* Padding del panel de contenido */
	private static final double PADDING_H = 0.025;
	private static final double PADDING_W = 0.02;

	/* Espacio entre el mensaje y el boton */
	private static final double GAP = 0.015;

	/* Titulos por defecto segun tipo */
	private static final String TITULO_INFO = "Informacion";
	private static final String TITULO_ERROR = "Error";
	private static final String TITULO_AVISO = "Aviso";

	/**
	 * Muestra un dialogo de informacion con titulo por defecto.
	 *
	 * @param mensaje Texto que se mostrara en el dialogo.
	 */
	public VentanaMensaje(String mensaje) {
		this(mensaje, INFO);
	}

	/**
	 * Muestra un dialogo con tipo definido y titulo por defecto segun el tipo.
	 *
	 * @param mensaje Texto que se mostrara en el dialogo.
	 * @param tipo    Tipo de mensaje: INFO, ERROR o AVISO.
	 */
	public VentanaMensaje(String mensaje, int tipo) {
		this(mensaje, tituloPorDefecto(tipo), tipo);
	}

	/**
	 * Muestra un dialogo con tipo y titulo personalizados.
	 *
	 * @param mensaje Texto que se mostrara en el dialogo.
	 * @param titulo  Texto de la barra de titulo y de la cabecera coloreada.
	 * @param tipo    Tipo de mensaje: INFO, ERROR o AVISO.
	 */
	public VentanaMensaje(String mensaje, String titulo, int tipo) {
		mostrar(mensaje, titulo, tipo);
	}

	/* Construye y muestra el dialogo. */
	private static void mostrar(String mensaje, String titulo, int tipo) {
		TiendaFrame t = TiendaFrame.getInstance();
		JDialog dialogo = new JDialog(t, titulo, true);

		int w = t.getPixelsWidth(DIALOG_W);
		int h = t.getPixelsHeight(DIALOG_H);
		int padH = t.getPixelsHeight(PADDING_H);
		int padW = t.getPixelsWidth(PADDING_W);
		int gap = t.getPixelsHeight(GAP);
		int btnW = t.getPixelsWidth(BTN_W);
		int btnH = t.getPixelsHeight(BTN_H);

		dialogo.setSize(w, h);
		dialogo.setResizable(false);
		dialogo.setLocationRelativeTo(t);
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogo.setLayout(new BorderLayout());
		dialogo.setUndecorated(true);

		/* Cabecera coloreada segun el tipo */
		JLabel cabecera = new JLabel("  " + titulo);
		cabecera.setFont(Fonts.BOLD.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(colorCabecera(tipo).getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		dialogo.add(cabecera, BorderLayout.NORTH);

		/* Cuerpo: mensaje centrado + boton Aceptar */
		JPanel cuerpo = new JPanel();
		cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
		cuerpo.setBackground(ColorPalette.CARD_LIGHT.getColor());
		cuerpo.setBorder(BorderFactory.createEmptyBorder(padH, padW, padH, padW));

		JLabel lblMensaje = ButtonFactory.newLabel(mensaje, Fonts.TEXT);
		lblMensaje.setForeground(ColorPalette.DARK_GREY.getColor());
		lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
		cuerpo.add(lblMensaje);

		cuerpo.add(Box.createVerticalStrut(gap));

		JButton btnAceptar = ButtonFactory.newRoundedButton("Aceptar", btnH, btnW, 1.0);
		ButtonFactory.paintButton(btnAceptar, colorCabecera(tipo), ColorPalette.WHITE);
		ButtonFactory.addHoverColorChange(btnAceptar);
		btnAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAceptar.addActionListener(e -> dialogo.dispose());
		cuerpo.add(btnAceptar);

		dialogo.add(cuerpo, BorderLayout.CENTER);
		dialogo.setVisible(true);
	}

	/* Devuelve el color de cabecera y boton segun el tipo de mensaje. */
	private static ColorPalette colorCabecera(int tipo) {
		return switch (tipo) {
		case ERROR -> ColorPalette.RED;
		case AVISO -> ColorPalette.LIGHT_PURPLE;
		default -> ColorPalette.DARK_BLUE;
		};
	}

	/* Devuelve el titulo por defecto segun el tipo. */
	private static String tituloPorDefecto(int tipo) {
		return switch (tipo) {
		case ERROR -> TITULO_ERROR;
		case AVISO -> TITULO_AVISO;
		default -> TITULO_INFO;
		};
	}
}