package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelArticulo;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Tipo: Class VentanaOfertaIntercambio.
 */
public class VentanaOfertaIntercambio extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo button1. */
	private JButton button1;
	
	/** Campo button2. */
	private JButton button2;
	
	/** Campo mios. */
	private JPanel mios;
	
	/** Campo suyos. */
	private JPanel suyos;
	
	/** Constante BTN_WIDTH. */
	private static final double BTN_WIDTH = 0.1;
	
	/** Constante BTN_HEIGHT. */
	private static final double BTN_HEIGHT = 0.07;
	
	/** Constante SPACE_BETWEEN. */
	private static final double SPACE_BETWEEN = 0.04;
	
	/**
	 * Instancia un nuevo Objeto VentanaOfertaIntercambio.
	 *
	 * @param btn1 parámetro btn1
	 */
	public VentanaOfertaIntercambio(String btn1) {
		this(btn1, null);
	}

	/**
	 * Instancia un nuevo Objeto VentanaOfertaIntercambio.
	 *
	 * @param btn1 parámetro btn1
	 * @param btn2 parámetro btn2
	 */
	public VentanaOfertaIntercambio(String btn1, String btn2) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		/* Panel de scrolls de objetos */
		JPanel scrolls = new JPanel(new GridLayout(1, 2));
		
		mios = new JPanel();
		mios.setLayout(new BoxLayout(mios, BoxLayout.Y_AXIS));
		mios.setBackground(ColorPalette.WHITE.getColor());
		
		suyos = new JPanel();
		suyos.setLayout(new BoxLayout(suyos, BoxLayout.Y_AXIS));
		suyos.setBackground(ColorPalette.WHITE.getColor());
		
		JScrollPane scrollMios = PanelFactory.getScroll(mios);
		JPanel panelMios = new JPanel(new BorderLayout());
		panelMios.add(scrollMios);
		
		JScrollPane scrollSuyos = PanelFactory.getScroll(suyos);
		JPanel panelSuyos = new JPanel(new BorderLayout());
		panelSuyos.add(scrollSuyos);
		
		scrolls.add(PanelFactory.getVentanaConCabecera("Darás: ", panelMios));
		scrolls.add(PanelFactory.getVentanaConCabecera("Recibirás: ", panelSuyos));
		
		add(scrolls, BorderLayout.CENTER);
		if(btn2 != null)
			add(botonAceptarRechazar(btn1, btn2), BorderLayout.SOUTH);
		else
			add(botonCancelar(btn1), BorderLayout.SOUTH);
		
		refreshLists();
	}
	
	/**
	 * botonAceptarRechazar.
	 *
	 * @param btn1 parámetro btn1
	 * @param btn2 parámetro btn2
	 * @return valor de tipo JPanel
	 */
	private JPanel botonAceptarRechazar(String btn1, String btn2) {
		int spaceBetween = TiendaFrame.getInstance().getPixelsWidth(SPACE_BETWEEN);
		int btnW = TiendaFrame.getInstance().getPixelsWidth(BTN_WIDTH);
		int btnH = TiendaFrame.getInstance().getPixelsHeight(BTN_HEIGHT);

		/* Panel de botones de abajo */
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.setOpaque(true);
		btnPanel.setBackground(ColorPalette.DARK_BLUE.getColor());
		
		JPanel btnInterior = new JPanel(new GridLayout(1,2, spaceBetween, 0));
		button1 = ButtonFactory.newRoundedButton(btn1, btnH, btnW, 1);
		button1.setActionCommand(btn1);
		button2 = ButtonFactory.newRoundedButton(btn2, btnH, btnW, 1);
		button2.setActionCommand(btn2);
		btnInterior.add(button1);
		btnInterior.add(button2);
		btnInterior.setOpaque(false);
		
		btnPanel.add(btnInterior, BorderLayout.CENTER);
		
		JPanel finalP = new JPanel(new BorderLayout());
		finalP = PanelFactory.wrapVertical(PanelFactory.wrapHorizontal(btnPanel, spaceBetween), spaceBetween/3);
		finalP.setOpaque(true);
		finalP.setBackground(ColorPalette.DARK_BLUE.getColor());
		
		return finalP;
	}
	
	/**
	 * botonCancelar.
	 *
	 * @param btn1 parámetro btn1
	 * @return valor de tipo JPanel
	 */
	private JPanel botonCancelar(String btn1) {
		int spaceBetween = TiendaFrame.getInstance().getPixelsWidth(SPACE_BETWEEN);
		int btnW = TiendaFrame.getInstance().getPixelsWidth(BTN_WIDTH);
		int btnH = TiendaFrame.getInstance().getPixelsHeight(BTN_HEIGHT);

		/* Panel de botones de abajo */
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.setOpaque(true);
		btnPanel.setBackground(ColorPalette.DARK_BLUE.getColor());
		
		JPanel btnInterior = new JPanel();
		button1 = ButtonFactory.newRoundedButton(btn1, btnH, btnW, 1);
		button1.setActionCommand(btn1);
		btnInterior = PanelFactory.wrapVertical(PanelFactory.wrapHorizontal(button1, spaceBetween), spaceBetween/3);
		btnInterior.setOpaque(false);
		
		btnPanel.add(btnInterior, BorderLayout.CENTER);
		
		return btnPanel;
	}
	
	/**
	 * refreshLists.
	 */
	private void refreshLists() {
		mios.revalidate();
		mios.repaint();
		
		suyos.revalidate();
		suyos.repaint();
	}
	
	/**
	 * anadirMio.
	 *
	 * @param p parámetro p
	 */
	public void anadirMio(PanelArticulo p) {
		mios.add(p);
	}
	
	/**
	 * anadirSuyo.
	 *
	 * @param p parámetro p
	 */
	public void anadirSuyo(PanelArticulo p) {
		suyos.add(p);
	}
	
	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		if(button1 != null) button1.addActionListener(c);
		if(button2 != null) button2.addActionListener(c);
	}
}
