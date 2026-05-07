package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelArticulo;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaOfertaIntercambio extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton button1;
	private JButton button2;
	
	private JPanel mios;
	private JPanel suyos;
	
	private static final double BTN_WIDTH = 0.1;
	private static final double BTN_HEIGHT = 0.07;
	private static final double SPACE_BETWEEN = 0.04;
	
	public VentanaOfertaIntercambio(String btn1) {
		this(btn1, null);
	}

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
	
	private void refreshLists() {
		mios.revalidate();
		mios.repaint();
		
		suyos.revalidate();
		suyos.repaint();
	}
	
	public void anadirMio(PanelArticulo p) {
		mios.add(p);
	}
	
	public void anadirSuyo(PanelArticulo p) {
		suyos.add(p);
	}
	
	public void setControlador(ActionListener c) {
		if(button1 != null) button1.addActionListener(c);
		if(button2 != null) button2.addActionListener(c);
	}
}
