package vistas.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelProductoGestionarProducto extends PanelProducto{
	private static final long serialVersionUID = 1L;
	private static double BOTON_PERC = 1;
	
	public PanelProductoGestionarProducto(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, puntuacionMedia, precio, "", categorias);
		
		TiendaFrame t = TiendaFrame.getInstance();
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		int maxWidth = t.getPixelsWidth(BOTON_PERC_W);
		eastPanel.setPreferredSize(new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H)));
		
		ButtonFactory f = new ButtonFactory();
		
		JButton modButton = f.newRoundedButton("Editar", (int)(maxCompHeight * BOTON_PERC), maxCompHeight, 0.5f);
				//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		JButton borrarButton = f.newRoundedButton("Borrar", (int)(maxCompHeight * BOTON_PERC), maxCompHeight, 0.5f);
		//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(borrarButton, ColorPalette.RED, ColorPalette.WHITE);
		f.addMouseMecanics(borrarButton, ColorPalette.RED, ColorPalette.LIGHT_RED);
		//modButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 3);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(borrarButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		this.add(eastPanel, BorderLayout.EAST);
	}
}
