package vistas;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ButtonFactory {
	private static String IMAGE_PATH = "resources/gui/";
	public ButtonFactory() {
		
	}
	
	public String getHTMLLabel(String label) {
		String finalLabel = "<html><center>";
		finalLabel = finalLabel.concat(label);
		finalLabel = finalLabel.concat("</center></html>");
		
		return finalLabel;
	}
	
	public ImageIcon getImageIcon(String imageName, int height, int width) {
		String path = IMAGE_PATH;
		path = path.concat(imageName);
		System.out.println(path);
		ImageIcon iconoOriginal = new ImageIcon(path);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH);
		
		
		return new ImageIcon(imagenEscalada);
	}
	
	private void setDefaultText(JButton button) {
		button.setVerticalTextPosition(SwingConstants.CENTER);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		
		button.setFont(TiendaFrame.getInstance().getTextFont());
	}
	
	public JButton newButton(String label, int height, int width) {
		String finalLabel = getHTMLLabel(label);
		JButton button = new JButton(finalLabel);
		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);
		setDefaultText(button);
		
		return button;
	}
	
	public JButton newIconButton(String imageName, int height, int width) {
		JButton button = new JButton(getImageIcon(imageName, height, width));
		
		setDefaultText(button);
		
		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);
		
		return button;
	}
	
	public JButton newIconButton(String label, int height, int width, String imageName) {
		JButton button = this.newIconButton(imageName, height, width);
		button.setText(getHTMLLabel(label));
		button.setIcon(getImageIcon(imageName, height, width));
		
		setDefaultText(button);
		
		return button;
	}
	
	
}
