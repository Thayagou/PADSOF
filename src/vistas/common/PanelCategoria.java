package vistas.common;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoria extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	private static double MAX_H = 0.08;
	private static double MAX_H_COMP = 0.75;
	
	
	public PanelCategoria(String nombreCategoria, String actionName) {
		super(MAX_H, MAX_H * MAX_H_COMP, actionName);
		
		anadirLabel(nombreCategoria);
	}
	
	public PanelCategoria(String nombreCategoria) {
		super(MAX_H, MAX_H * MAX_H_COMP);
		
		anadirLabel(nombreCategoria);
	}
	
	private void anadirLabel(String nombreCategoria) {
		TiendaFrame t = TiendaFrame.getInstance();
		
		int height = t.getPixelsHeight(MAX_H*MAX_H_COMP);
		int gap = t.getPixelsHeight(MAX_H * (1 - MAX_H_COMP)/2);
		
		JLabel nombreLabel = new JLabel(nombreCategoria);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);
		
		JPanel panelNombre = new JPanel();
		panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.Y_AXIS));
		panelNombre.setOpaque(false);
		panelNombre.setPreferredSize(new Dimension(Integer.MAX_VALUE, height));
		panelNombre.add(Box.createVerticalStrut(gap));
		panelNombre.add(nombreLabel);
		panelNombre.add(Box.createVerticalStrut(gap));
		
		add(panelNombre, BorderLayout.WEST);
	}
}
