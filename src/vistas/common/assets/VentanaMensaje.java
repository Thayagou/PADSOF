package vistas.common.assets;

import java.awt.*;
import javax.swing.*;

import vistas.common.app.TiendaFrame;
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
public class VentanaMensaje extends JDialog {

	private static final long serialVersionUID = 1L;
	
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
	private static final double BORDER_WIDTH = 8.0/1080.0;

	/* Espacio entre el mensaje y el boton */
	private static final double GAP = 0.015;

	/* Titulos por defecto segun tipo */
	private static final String TITULO_INFO = "Informacion";
	private static final String TITULO_ERROR = "Error";
	private static final String TITULO_AVISO = "Aviso";
	
	protected JPanel btnPanel;

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
	
	public VentanaMensaje(String mensaje, String titulo, int tipo) {
		this(mensaje, tipo, titulo);
		mostrar();
	}

	/**
	 * Constructor privado que solo forma el dialogo sin mostrarlo
	 *
	 * @param mensaje
	 * @param tipo
	 * @param titulo
	 */
	protected VentanaMensaje(String mensaje, int tipo, String titulo) {
		super(TiendaFrame.getInstance(), titulo, true);
		
		TiendaFrame t = TiendaFrame.getInstance();
		int w = t.getPixelsWidth(DIALOG_W);
		int h = t.getPixelsHeight(DIALOG_H);
		int padH = t.getPixelsHeight(PADDING_H);
		int padW = t.getPixelsWidth(PADDING_W);
		int gap = t.getPixelsHeight(GAP);
		int btnW = t.getPixelsWidth(BTN_W);
		int btnH = t.getPixelsHeight(BTN_H);
		int grosorBorde = t.getPixelsWidth(BORDER_WIDTH);

		this.setSize(w, h);
		this.setResizable(false);
		this.setLocationRelativeTo(t);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setUndecorated(true);

		/* Cabecera coloreada segun el tipo */
		JLabel cabecera = new JLabel("  " + titulo);
		cabecera.setFont(Fonts.BOLD.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(colorCabecera(tipo).getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(grosorBorde, grosorBorde, grosorBorde, grosorBorde));
		this.add(cabecera, BorderLayout.NORTH);

		/* Cuerpo: mensaje centrado*/
		JPanel cuerpo = new JPanel();
		cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
		cuerpo.setBackground(ColorPalette.CARD_LIGHT.getColor());
		cuerpo.setBorder(BorderFactory.createEmptyBorder(padH, padW, padH, padW));

		JLabel lblMensaje = ButtonFactory.newLabel(mensaje, Fonts.TEXT);
		lblMensaje.setForeground(ColorPalette.DARK_GREY.getColor());
		lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
		cuerpo.add(lblMensaje);

		cuerpo.add(Box.createVerticalStrut(gap));
		this.add(cuerpo, BorderLayout.CENTER);

		/* Boton */
		btnPanel = new JPanel(new FlowLayout());
		JButton btnAceptar = ButtonFactory.newRoundedButton("Aceptar", btnH, btnW, 1.0);
		ButtonFactory.paintButton(btnAceptar, colorCabecera(tipo), ColorPalette.WHITE);
		ButtonFactory.addHoverColorChange(btnAceptar);
		btnAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAceptar.addActionListener(e -> this.dispose());
		btnPanel.add(btnAceptar);
		btnPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		this.add(btnPanel, BorderLayout.SOUTH);
	}
	
	public void mostrar() {
		this.setVisible(true);
	}

	/* Devuelve el color de cabecera y boton segun el tipo de mensaje. */
	protected static ColorPalette colorCabecera(int tipo) {
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