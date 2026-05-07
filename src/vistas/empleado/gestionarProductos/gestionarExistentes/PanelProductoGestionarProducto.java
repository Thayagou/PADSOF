package vistas.empleado.gestionarProductos.gestionarExistentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelProductoGestionarProducto extends PanelProducto{
	private static final long serialVersionUID = 1L;
	public static final String MODIFICAR_ACTION = "Modificar";
	public static final String BORRAR_ACTION = "Borrar";
	private JButton modButton;
	private JButton borrarButton;
	
	public PanelProductoGestionarProducto(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, "", categorias);
		
		TiendaFrame t = TiendaFrame.getInstance();
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H)));
		
		modButton = ButtonFactory.newRoundedButton(MODIFICAR_ACTION, (int)(maxCompHeight), maxCompHeight, 0.5f);
		ButtonFactory.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		borrarButton = ButtonFactory.newRoundedButton(BORRAR_ACTION, (int)(maxCompHeight), maxCompHeight, 0.5f);
		ButtonFactory.paintButton(borrarButton, ColorPalette.RED, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(borrarButton, ColorPalette.RED, ColorPalette.LIGHT_RED);
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 3);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(borrarButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		this.add(eastPanel, BorderLayout.EAST);
	}
	
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		modButton.addActionListener(c);
		borrarButton.addActionListener(c);
	}
}
