package vistas.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.PanelProducto;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelProductoGestionarProducto extends PanelProducto{
	private static final long serialVersionUID = 1L;
	
	private static double BOTON_PERC = 1;
	
	public PanelProductoGestionarProducto(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, puntuacionMedia, precio, categorias);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		eastPanel.setPreferredSize(new Dimension(3*maxCompHeight, (int)(maxCompHeight * BOTON_PERC)));
		
		ButtonFactory f = new ButtonFactory();
		
		JButton modButton = f.newRoundedButton("Editar", (int)(maxCompHeight * BOTON_PERC), maxCompHeight, 0.5f);
				//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		JButton borrarButton = f.newRoundedButton("Borrar", (int)(maxCompHeight * BOTON_PERC), maxCompHeight, 0.5f);
		//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		f.paintButton(borrarButton, ColorPalette.LIGHT_RED, ColorPalette.WHITE);
		f.addMouseMecanics(borrarButton, ColorPalette.LIGHT_RED, ColorPalette.RED);
		//modButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		eastPanel.add(modButton);
		eastPanel.add(borrarButton);
		
		this.add(eastPanel, BorderLayout.EAST);
	}
}
