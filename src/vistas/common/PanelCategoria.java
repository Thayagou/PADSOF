package vistas.common;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoria extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	private static double MAX_H = 0.08;
	private static double MAX_H_COMP = 0.75;
	
	
	public PanelCategoria(String nombreCategoria, String actionName) {
		super(MAX_H, MAX_H * MAX_H_COMP, actionName);
		
		JLabel nombreLabel = new JLabel(nombreCategoria);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);
	}
}
