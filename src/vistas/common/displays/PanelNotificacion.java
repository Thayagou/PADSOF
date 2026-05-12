package vistas.common.displays;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.components.FixedTextArea;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

/**
 * Subclase de PanelDisplay que usamos para mostrar las notificaciones recibidas dentro de un scroll.
 */
public class PanelNotificacion extends PanelDisplay {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de altura del panel utilizado para los componentes. */
	private static final double COMP_H_PERC = 0.99;
	
	/** Porcentaje de altura de pantalla que ocupa el panel de notificación. */
	private static final double MAX_HEIGHT = 0.16;
	
	/** Porcentaje de anchura de pantalla que ocupan los botones. */
	private static final double BTN_WIDTH = 0.05;
	
	/** Porcentaje de altura de pantalla que ocupan los botones. */
	private static final double BTN_HEIGHT = 0.03;
	
	/** Porcentaje de hueco vertical entre componentes */
	private static final double VERTICAL_GAP = 0.01;
	
	/** ActionCommand de la acción de marcar la notificación como leída. */
	public static final String READ_ACTION = "leido";
	
	/** ActionCommand de la acción de eliminar la notificación. */
	public static final String DELETE_ACTION = "borrar";
	
	/** Botón asociado a la acción de. */
	private JButton marcarLeido;
	
	/** Botón asociado a la acción de. */
	private JButton borrar;
	
	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param titulo parámetro titulo
	 * @param texto parámetro texto
	 * @param fecha parámetro fecha
	 * @param leido parámetro leido
	 * @param ponerBotones parámetro ponerBotones
	 */
	public PanelNotificacion(String titulo, String texto, LocalDate fecha, boolean leido, boolean ponerBotones) {
		super(MAX_HEIGHT, COMP_H_PERC*MAX_HEIGHT);
		String fechaString = getFechaFormat(fecha);
		
		TiendaFrame t = TiendaFrame.getInstance();
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.setOpaque(false);
		
		Fonts fontTitle;
		Fonts fontContent;
		ColorPalette fontColor;
		if(leido == false) {
			fontTitle = Fonts.BOLD;
			fontContent = Fonts.TEXT;
			fontColor = ColorPalette.BLACK;
		} else {
			fontTitle = Fonts.TEXT;
			fontContent = Fonts.TEXT;
			fontColor = ColorPalette.GREY;
		}
		
		JLabel cabecera = ButtonFactory.newLabel(fechaString + ": " + titulo, fontTitle);
		cabecera.setOpaque(false);
		cabecera.setForeground(fontColor.getColor());
		contenido.add(cabecera, BorderLayout.NORTH);
		JTextArea panelTexto = new FixedTextArea(texto, fontContent.getFont());
		panelTexto.setForeground(fontColor.getColor());
		panelTexto.setOpaque(false);
		contenido.add(panelTexto, BorderLayout.CENTER);
		
		add(contenido, BorderLayout.CENTER);
		
		JPanel botones;
		borrar = ButtonFactory.newRoundedButton("Borrar", t.getPixelsHeight(BTN_HEIGHT), t.getPixelsWidth(BTN_WIDTH), 1);
		ButtonFactory.paintButton(borrar, ColorPalette.CARD_DARK, ColorPalette.DARK_GREY);
		ButtonFactory.addMouseMecanics(borrar, ColorPalette.CARD_DARK);
		borrar.setActionCommand(DELETE_ACTION);
		borrar.setMaximumSize(new Dimension(t.getPixelsWidth(BTN_WIDTH), t.getPixelsHeight(BTN_HEIGHT)));
		
		marcarLeido = ButtonFactory.newRoundedButton("Leído", t.getPixelsHeight(BTN_HEIGHT), t.getPixelsWidth(BTN_WIDTH), 1);
		marcarLeido.setActionCommand(READ_ACTION);
		marcarLeido.setMaximumSize(new Dimension(t.getPixelsWidth(BTN_WIDTH), t.getPixelsHeight(BTN_HEIGHT)));

		if(leido == false) {
			botones = PanelFactory.getColumnaDeBotones(VERTICAL_GAP, marcarLeido, borrar);
		} else {
			botones = PanelFactory.getColumnaDeBotones(VERTICAL_GAP*4.5, borrar);
		}
		if(ponerBotones) {
			add(botones, BorderLayout.EAST);
		}
	}

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param titulo parámetro titulo
	 * @param texto parámetro texto
	 * @param fecha parámetro fecha
	 * @param leido parámetro leido
	 */
	public PanelNotificacion(String titulo, String texto, LocalDate fecha, boolean leido) {
		this(titulo, texto, fecha, leido, true);
	}
	
	/**
	 * Obtiene FechaFormat.
	 *
	 * @param date parámetro date
	 * @return valor de FechaFormat
	 */
	private String getFechaFormat(LocalDate date) {
		return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear();
	}
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param c parámetro c
	 */
	@Override
	public void setControlador(ActionListener c) {
		borrar.addActionListener(c);
		marcarLeido.addActionListener(c);
	}
}
