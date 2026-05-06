package vistas.common;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import vistas.herramientas.*;

/**
 * Panel reutilizable que combina una cabecera con título, un selector de
 * opciones (JComboBox) y un contenido central intercambiable.
 *
 * Uso típico: listas de resultados con ordenación, paneles con filtros por
 * tipo, etc.
 */
public class PanelMultiopcion extends JPanel {

	private static final long serialVersionUID = 1L;

	/* Macros de layout (relativos a resolución base 1920×1080) */
	private static final double HEADER_GAP_H_MACRO = 12.0 / 1920.0; /* Espacio horizontal entre elementos de cabecera */
	private static final double HEADER_GAP_V_MACRO = 6.0 / 1080.0; /* Espacio vertical interno de la cabecera */
	private static final double HEADER_PAD_MACRO = 8.0 / 1080.0; /* Padding vertical de la cabecera */

	public static final String CAMBIO_OPCION_ACTION = "Cambiar opcion";
	/* ── Estado interno ── */
	private final JComboBox<String> selector;

	/**
	 * Construye el panel con cabecera y selector de opciones 
	 *
	 * @param titulo    Texto que aparece en la cabecera.
	 * @param opciones  Array de strings con las opciones del selector.
	 */
	public PanelMultiopcion(String titulo, Fonts titleFont, Fonts selectorFont, String[] opciones) {
		setOpaque(false);
		setLayout(new BorderLayout());

		TiendaFrame t = TiendaFrame.getInstance();

		/* ── Conversión de macros a píxeles ── */
		int headerGapH = t.getPixelsWidth(HEADER_GAP_H_MACRO);
		int headerGapV = t.getPixelsHeight(HEADER_GAP_V_MACRO);
		int headerPad = t.getPixelsHeight(HEADER_PAD_MACRO);

		/* ── Selector ── */
		selector = new JComboBox<>(opciones);
		selector.setFont(selectorFont.getFont());
		selector.setActionCommand(CAMBIO_OPCION_ACTION);

		/* ── Cabecera ── */
		JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, headerGapH, headerGapV));
		cabecera.setBackground(ColorPalette.BG_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(headerPad, headerPad, headerPad, headerPad));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(titleFont.getFont());
		lblTitulo.setForeground(ColorPalette.WHITE.getColor());

		cabecera.add(lblTitulo);
		cabecera.add(selector);

		/* ── Composición ── */
		add(cabecera, BorderLayout.NORTH);
		
		JPanel relleno = new JPanel();
	    relleno.setOpaque(false);
	    add(relleno, BorderLayout.CENTER);
	}
	
	/**
	 * Construye el panel con cabecera, selector de opciones y contenido central.
	 *
	 * @param titulo    Texto que aparece en la cabecera.
	 * @param contenido Panel que ocupa la zona central del panel.
	 * @param opciones  Array de strings con las opciones del selector.
	 */
	public PanelMultiopcion(String titulo, JPanel contenido, String[] opciones) {
		this(titulo, Fonts.TITLE3, Fonts.TEXT, opciones);
		add(contenido, BorderLayout.CENTER);
	}
	
	/**
	 * Construye el panel con cabecera, selector de opciones y contenido central.
	 *
	 * @param titulo    Texto que aparece en la cabecera.
	 * @param contenido Panel que ocupa la zona central del panel.
	 * @param titleFont Font del título
	 * @param selectorFont Font del selector
	 * @param opciones  Array de strings con las opciones del selector.
	 */
	public PanelMultiopcion(String titulo, JPanel contenido, Fonts titleFont, Fonts selectorFont, String[] opciones) {
		this(titulo, titleFont, selectorFont, opciones);
		add(contenido, BorderLayout.CENTER);
	}
	
	/**
	 * Establece el Action Command del panel
	 * @param aCommand Nuevo Action Command
	 */
	public void setActionCommand(String aCommand) {
		selector.setActionCommand(aCommand);
	}
	
	/**
	 * Devuelve el índice de la opción actualmente seleccionada.
	 *
	 * @return Índice (0-based) de la opción seleccionada.
	 */
	public int getOpcionSeleccionada() {
		return selector.getSelectedIndex();
	}

	/**
	 * Devuelve el texto de la opción actualmente seleccionada.
	 *
	 * @return String con el texto de la opción seleccionada, o null si no hay
	 *         ninguna.
	 */
	public String getTextoSeleccionado() {
		return (String) selector.getSelectedItem();
	}

	/**
	 * Registra un listener que se ejecutará cada vez que el usuario cambie la
	 * opción.
	 *
	 * @param c Controlador que implementa ActionListener.
	 */
	public void setControlador(ActionListener c) {
		selector.addActionListener(c);
	}

	/**
	 * Sustituye el listener actual por uno nuevo, eliminando los anteriores. Útil
	 * cuando el contenido se refresca y se quiere evitar listeners duplicados.
	 *
	 * @param c Nuevo controlador que implementa ActionListener.
	 */
	public void reemplazarControlador(ActionListener c) {
		for (ActionListener al : selector.getActionListeners()) {
			selector.removeActionListener(al);
		}
		selector.addActionListener(c);
	}
}