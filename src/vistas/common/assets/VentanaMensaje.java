package vistas.common.assets;

import java.awt.*;
import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.*;

/**
 * Dialogo modal de mensaje para la aplicacion.
 *
 * Tipos disponibles: VentanaMensaje.INFO, VentanaMensaje.ERROR,
 * VentanaMensaje.AVISO. El tipo solo afecta al color de la cabecera; el resto
 * del dialogo es siempre identico.
 */
public class VentanaMensaje extends JDialog {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Tipo para mensajes informativos. */
	public static final int INFO = 0;
	
	/** Tipo para mensajes de error. */
	public static final int ERROR = 1;
	
	/** Tipo para mensajes de advertencia. */
	public static final int AVISO = 2;

	/** Anchura del diálogo como porcentaje de la pantalla. */
	private static final double DIALOG_W = 0.30;
	
	/** Altura del diálogo como porcentaje de la pantalla. */
	private static final double DIALOG_H = 0.30;

	/** Anchura del botón como porcentaje de la pantalla. */
	private static final double BTN_W = 0.10;
	
	/** Altura del botón como porcentaje de la pantalla. */
	private static final double BTN_H = 0.05;

	/** Padding vertical del contenido. */
	private static final double PADDING_H = 0.025;
	
	/** Padding horizontal del contenido. */
	private static final double PADDING_W = 0.02;
	
	/** Grosor del borde de la cabecera. */
	private static final double BORDER_WIDTH = 8.0/1080.0;

	/** Espacio vertical entre el mensaje y el botón. */
	private static final double GAP = 0.015;

	/** Título por defecto para mensajes informativos. */
	private static final String TITULO_INFO = "Informacion";
	
	/** Título por defecto para mensajes de error. */
	private static final String TITULO_ERROR = "Error";
	
	/** Título por defecto para mensajes de advertencia. */
	private static final String TITULO_AVISO = "Aviso";
	
	/** Panel que contiene el botón de aceptar. */
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
	
	/**
	 * Instancia un nuevo Objeto VentanaMensaje.
	 *
	 * @param mensaje Texto que se mostrara en el dialogo.
	 * @param titulo Título personalizado de la ventana.
	 * @param tipo Tipo de mensaje: INFO, ERROR o AVISO.
	 */
	public VentanaMensaje(String mensaje, String titulo, int tipo) {
		this(mensaje, tipo, titulo);
		mostrar();
	}

	/**
	 * Constructor privado que solo forma el dialogo sin mostrarlo.
	 *
	 * @param mensaje Texto que se mostrara en el dialogo.
	 * @param tipo Tipo de mensaje: INFO, ERROR o AVISO.
	 * @param titulo Título de la ventana.
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
	
	/**
	 * mostrar.
	 * Hace visible el diálogo modal.
	 */
	public void mostrar() {
		this.setVisible(true);
	}

	/**
	 * colorCabecera.
	 * Devuelve el color de cabecera y boton segun el tipo de mensaje.
	 *
	 * @param tipo Tipo de mensaje (INFO, ERROR, AVISO).
	 * @return valor de tipo ColorPalette, el color correspondiente.
	 */
	/* Devuelve el color de cabecera y boton segun el tipo de mensaje. */
	protected static ColorPalette colorCabecera(int tipo) {
		return switch (tipo) {
		case ERROR -> ColorPalette.RED;
		case AVISO -> ColorPalette.LIGHT_PURPLE;
		default -> ColorPalette.DARK_BLUE;
		};
	}

	/**
	 * tituloPorDefecto.
	 * Devuelve el titulo por defecto segun el tipo.
	 *
	 * @param tipo Tipo de mensaje (INFO, ERROR, AVISO).
	 * @return valor de tipo String, el título correspondiente.
	 */
	/* Devuelve el titulo por defecto segun el tipo. */
	private static String tituloPorDefecto(int tipo) {
		return switch (tipo) {
		case ERROR -> TITULO_ERROR;
		case AVISO -> TITULO_AVISO;
		default -> TITULO_INFO;
		};
	}
}