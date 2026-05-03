package vistas.common;

import javax.swing.JButton;
import javax.swing.JPanel;

import controladores.ControlBarraLateral;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public abstract class BarraLateral extends JPanel{
	private static final long serialVersionUID = 1L;

	public abstract void setControlador(ControlBarraLateral c);
	
	public JButton addBtn(ButtonFactory f, String text, int heigth, int width) {
    	JButton btn = f.newButton(text, heigth, width);
    	btn.setFont(Fonts.TEXT.getFont());
    	btn.setForeground(ColorPalette.DARK_GREY.getColor());
    	btn.setBackground(ColorPalette.CARD_LIGHT.getColor());
    	f.addMouseMecanics(btn, ColorPalette.CARD_LIGHT, ColorPalette.CARD_DARK);
    	btn.setBorderPainted(false);
    	
    	return btn;
    }
}
