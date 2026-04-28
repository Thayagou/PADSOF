package vistas;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import controladores.ButtonAdapter;

public class ButtonFactory {
	private static String IMAGE_PATH = "resources/gui/";

	public ButtonFactory() {

	}

	private String getHTMLLabel(String label) {
		return "<html><center>" + label + "</center></html>";
	}

	private ImageIcon loadImageIcon(String imageName) {
		// ImageIcon iconoOriginal = new ImageIcon(IMAGE_PATH + imageName);
		// Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(height,
		// width, Image.SCALE_SMOOTH);

		return new ImageIcon(IMAGE_PATH + imageName);
		// return new ImageIcon(imagenEscalada);
	}

	private void setDefault(JButton button) {
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
	}

	private void iconoDinamico(JButton button, ImageIcon original, double percIcono) {
		button.addComponentListener(new ButtonAdapter(button, percIcono, original));
	}
	
	public JButton newButton(String label) {
		JButton button = new JButton(getHTMLLabel(label));
		button.setActionCommand(label);
		
		return button;
	}

	public JButton newButton(String label, int height, int width) {
		JButton button = newButton(label);
		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);
		setDefault(button);

		return button;
	}

	public JButton newIconButton(String imageName, int height, int width) {
		ImageIcon icon = loadImageIcon(imageName);
		JButton button = new JButton(icon);

		// iconoDinamico(button, icon, 0.6);

		setDefault(button);

		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);

		return button;
	}

	public JButton newIconButton(String label, int height, int width, String imageName) {
		
		ImageIcon icon = loadImageIcon(imageName);
		JButton button = newButton(label);
		button.setIcon(icon);
		// JButton button = this.newIconButton(imageName, height, width);
		button.setText(getHTMLLabel(label));
		setDefault(button);
		iconoDinamico(button, icon, 0.6);

		return button;
	}

	public void addMouseMecanics(JButton btn, ColorPalette defaultC, ColorPalette pressedC) {
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBackground(pressedC.getColor());
			}

			public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBackground(defaultC.getColor());
			}
		});

	}

}
