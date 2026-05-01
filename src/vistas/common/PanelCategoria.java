package vistas.common;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoria extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	
	public PanelCategoria(String nombreCategoria, String actionName) {
		super(0.08, 0.06, actionName);
		
		JLabel nombreLabel = new JLabel(nombreCategoria);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);
	}
}
