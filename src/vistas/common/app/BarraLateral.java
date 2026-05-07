package vistas.common.app;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controladores.ControlBarraLateral;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public abstract class BarraLateral extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final double PERC_INDENTED=0.9;

	public abstract void setControlador(ControlBarraLateral c);
	
	public JButton addBtn(String text, int heigth, int width) {
    	JButton btn = ButtonFactory.newButtonLeft(text, heigth, width);
    	btn.setMaximumSize(new Dimension(width, heigth));
    	btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
    	btn.setHorizontalAlignment(SwingConstants.LEFT);
    	btn.setFont(Fonts.TEXT.getFont());
    	btn.setForeground(ColorPalette.DARK_GREY.getColor());
    	btn.setBackground(ColorPalette.CARD_LIGHT.getColor());
    	ButtonFactory.addMouseMecanics(btn, ColorPalette.CARD_LIGHT, ColorPalette.CARD_DARK);
    	btn.setBorderPainted(false);
    	
    	return btn;
    }
	
	public void addIndentedBtns(JButton parent, JButton... children) {
		for(JButton j : children ) {
        	j.setVisible(false);
        }
	    parent.addMouseListener(new MouseAdapter() {
	        public void mouseEntered(MouseEvent e) {
	            for(JButton j : children ) {
	            	j.setVisible(true);
	            }
	        }
	        public void mouseExited(MouseEvent e) {
	            Component destino = SwingUtilities.getDeepestComponentAt(
	                parent.getParent(),
	                e.getX() + parent.getX(),
	                e.getY() + parent.getY()
	            );
	            if (!esHijo(destino, children)) {
	            	for(JButton j : children ) {
		            	j.setVisible(false);
		            }
	            }
	        }
	    });

	    MouseAdapter ocultarAlSalir = new MouseAdapter() {
	        public void mouseExited(MouseEvent e) {
	            Component destino = SwingUtilities.getDeepestComponentAt(
	                parent.getParent(),
	                e.getX() + ((Component) e.getSource()).getX(),
	                e.getY() + ((Component) e.getSource()).getY()
	            );
	            if (destino != parent && !esHijo(destino, children)) {
	            	for(JButton j : children ) {
		            	j.setVisible(false);
		            }
	            }
	        }
	    };

	    for (JButton child : children) {
	        child.addMouseListener(ocultarAlSalir);
	    }
	}

	private boolean esHijo(Component c, JButton[] children) {
	    for (JButton child : children) {
	        if (c == child) return true;
	    }
	    return false;
	}
	
	public void setVisibles(JButton...botones) {
		for (JButton b: botones) 
			b.setVisible(true);
	}
	
	public void setInvisibles(JButton...botones) {
		for (JButton b: botones) 
			b.setVisible(false);
	}
}
