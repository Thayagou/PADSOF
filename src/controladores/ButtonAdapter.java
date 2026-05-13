package controladores;

import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Adapa el tamaño de los botones al tamaño de la pantalla
 */
public class ButtonAdapter extends ComponentAdapter{
	private JButton button;
	private double percIcono;
	private ImageIcon original;
	private float fontRatio;
	private int origHeight = -1;
	
	/**
	 * Constructor de un adaptador de tamaño de los botones
	 * @param button Botón que se adapta
	 * @param percIcono Porcentaje del icono
	 * @param original Imagen original
	 */
	public ButtonAdapter(JButton button, double percIcono, ImageIcon original) {
		this.button = button;
		this.percIcono = percIcono;
		this.original = original;
	}
	
	private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
		if (width <= 0 || height <= 0) return icon;
		Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		int w = button.getWidth();
		int h = button.getHeight();
		if (w <= 0 || h <= 0) return;
		
		if (origHeight <= 0) {
			origHeight = h;
			fontRatio = button.getFont().getSize2D() / origHeight;
		}
		int iconSize = (int) Math.min(h * percIcono, w * percIcono);
		button.setIcon(scaleIcon(original, iconSize, iconSize));
		
		float fontSize = fontRatio * h ;
		button.setFont(button.getFont().deriveFont(fontSize));
	}
}
