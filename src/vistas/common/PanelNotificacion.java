package vistas.common;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class PanelNotificacion extends PanelDisplay {
	
	private static final long serialVersionUID = 1L;
	
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
//	private static final int MAX_DESC = 120;
	private static final double BTN_WIDTH = 0.07;
	private static final double BTN_HEIGHT = 0.03;
	
	private static final double VERTICAL_GAP = 0.01;
	
	private String titulo;
	private String texto;
	private String fecha;
	
	private JButton marcarLeido;
	private JButton borrar;

	public PanelNotificacion(String titulo, String texto, LocalDate fecha, boolean leido) {
		super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT);
		this.titulo = titulo;
		this.texto = texto;
		this.fecha = getFechaFormat(fecha);
		
		TiendaFrame t = TiendaFrame.getInstance();
		JPanel contenido = new JPanel(new GridLayout(2, 1));
		contenido.setOpaque(false);
		
		Fonts font;
		if(leido == false) font = Fonts.BOLD;
		else font = Fonts.TEXT;
		
		JLabel cabecera = ButtonFactory.newLabel(this.fecha + ": " + this.titulo, font);
		cabecera.setOpaque(false);
		contenido.add(cabecera);
		JLabel panelTexto = ButtonFactory.newLabel(this.texto, Fonts.SMALL);
		panelTexto.setOpaque(false);
		contenido.add(panelTexto);
		
		add(contenido, BorderLayout.CENTER);
		
		JPanel botones;
		borrar = ButtonFactory.newRoundedButton("Borrar", t.getPixelsHeight(BTN_HEIGHT), t.getPixelsWidth(BTN_WIDTH), 1);
		borrar.setActionCommand("borrar");
		borrar.setMaximumSize(new Dimension(t.getPixelsWidth(BTN_WIDTH), t.getPixelsHeight(BTN_HEIGHT)));
		
		marcarLeido = ButtonFactory.newRoundedButton("Leído", t.getPixelsHeight(BTN_HEIGHT), t.getPixelsWidth(BTN_WIDTH), 1);
		marcarLeido.setActionCommand("leido");
		marcarLeido.setMaximumSize(new Dimension(t.getPixelsWidth(BTN_WIDTH), t.getPixelsHeight(BTN_HEIGHT)));

		if(leido == false) {
			botones = PanelFactory.getColumnaDeBotones(VERTICAL_GAP, marcarLeido, borrar);
		} else {
			botones = PanelFactory.getColumnaDeBotones(VERTICAL_GAP, borrar);
		}
		
		add(botones, BorderLayout.EAST);
	}
	
	private String getFechaFormat(LocalDate date) {
		return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear();
	}
	
	@Override
	public void setControlador(ActionListener c) {
		borrar.addActionListener(c);
		marcarLeido.addActionListener(c);
	}
}
