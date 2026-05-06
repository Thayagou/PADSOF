package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.PanelArticulo;
import vistas.common.TiendaFrame;
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

	public VentanaOfertaIntercambio(String btn1, String btn2) {
		int btnW = TiendaFrame.getInstance().getPixelsWidth(BTN_WIDTH);
		int btnH = TiendaFrame.getInstance().getPixelsHeight(BTN_HEIGHT);
		int spaceBetween = TiendaFrame.getInstance().getPixelsWidth(SPACE_BETWEEN);
		
		setOpaque(false);
		setLayout(new BorderLayout());
		
		/* Panel de scrolls de objetos */
		JPanel scrolls = new JPanel(new GridLayout(1, 2));
		
		mios = new JPanel();
		mios.setLayout(new BoxLayout(mios, BoxLayout.Y_AXIS));
		mios.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		suyos = new JPanel();
		suyos.setLayout(new BoxLayout(suyos, BoxLayout.Y_AXIS));
		suyos.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scrollMios = PanelFactory.getScroll(mios);
		JPanel panelMios = new JPanel(new BorderLayout());
		panelMios.add(scrollMios);
		
		JScrollPane scrollSuyos = PanelFactory.getScroll(suyos);
		JPanel panelSuyos = new JPanel(new BorderLayout());
		panelSuyos.add(scrollSuyos);
		
		scrolls.add(PanelFactory.getVentanaConCabecera("Darás: ", panelMios));
		scrolls.add(PanelFactory.getVentanaConCabecera("Recibirás: ", panelSuyos));
		
		/* Panel de botones de abajo */
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.setOpaque(true);
		btnPanel.setBackground(ColorPalette.WHITE.getColor());
		
		JPanel btnInterior = new JPanel(new GridLayout(1,2, spaceBetween, 0));
		button1 = ButtonFactory.newRoundedButton(btn1, btnH, btnW, 1);
		button1.setActionCommand(btn1);
		button2 = ButtonFactory.newRoundedButton(btn2, btnH, btnW, 1);
		button2.setActionCommand(btn2);
		btnInterior.add(button1);
		btnInterior.add(button2);
		
		btnPanel.add(btnInterior, BorderLayout.CENTER);
		
		add(scrolls, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
		
		refreshLists();
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
		button1.addActionListener(c);
		button2.addActionListener(c);
	}
}
