package vistas.common;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controladores.ControlBarraLateral;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public abstract class BarraLateral extends JPanel{
	private static final long serialVersionUID = 1L;

	public abstract void setControlador(ControlBarraLateral c);
	
	public JButton addBtn(ButtonFactory f, String text, int heigth, int width) {
    	JButton btn = f.newButtonLeft(text, heigth, width);
    	btn.setMaximumSize(new Dimension(width, heigth));
    	btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
    	btn.setHorizontalAlignment(SwingConstants.LEFT);
    	btn.setFont(Fonts.TEXT.getFont());
    	btn.setForeground(ColorPalette.DARK_GREY.getColor());
    	btn.setBackground(ColorPalette.CARD_LIGHT.getColor());
    	f.addMouseMecanics(btn, ColorPalette.CARD_LIGHT, ColorPalette.CARD_DARK);
    	btn.setBorderPainted(false);
    	
    	return btn;
    }
}
