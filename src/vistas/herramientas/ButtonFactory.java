package vistas.herramientas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import controladores.ButtonAdapter;

public class ButtonFactory {
	private static String IMAGE_PATH = "resources/gui/";

	public ButtonFactory() {

	}

	private String getHTMLCenteredLabel(String label) {
		return "<html><center>" + label + "</center></html>";
	}
	
	private String getHTMLLabel(String label) {
		return "<html>" + label + "</html>";
	}	

	public ImageIcon loadImageIconScaled(String imageName, int h, int w) {
		ImageIcon original = new ImageIcon(IMAGE_PATH + imageName);
		if (h <= 0 || w <= 0) return original;
	    Image img = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
	    return new ImageIcon(img);
	}

	private ImageIcon loadImageIcon(String imageName) {
		return new ImageIcon(IMAGE_PATH + imageName);
	}

	private void setDefault(JButton button) {
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setFont(Fonts.TEXT.getFont());
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	public void paintButton(JButton button, ColorPalette background, ColorPalette foreground) {
		button.setBackground(background.getColor());
		button.setForeground(foreground.getColor());
	}

	private void iconoDinamico(JButton button, ImageIcon original, double percIcono) {
		button.addComponentListener(new ButtonAdapter(button, percIcono, original));
	}
	
	public JButton newButton(String label) {
		JButton button = new JButton(getHTMLCenteredLabel(label));
		button.setActionCommand(label);
		
		return button;
	}
	
	public JButton newRoundedButton(String label, int height, int width, double roundness) {
		JButton button = new RoundedButton(getHTMLLabel(label), roundness);
		
		button.setActionCommand(label);
		button.setPreferredSize(new Dimension(width, height));

		setDefault(button);

		return button;
	}
	
	public JButton newRoundedIconButton(String label, int height, int width, double roundness, String imageName) {
		ImageIcon icon = loadImageIconScaled(imageName, height, width);
		JButton button = new RoundedButton(getHTMLLabel(label), roundness);

		button.setIcon(icon);
		button.setPreferredSize(new Dimension(width, height));
		button.setActionCommand(label);

		setDefault(button);
		iconoDinamico(button, icon, 0.6);

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
		ImageIcon icon = loadImageIconScaled(imageName, height, width);
		JButton button = new JButton(icon);

		// iconoDinamico(button, icon, 0.6);

		setDefault(button);

		Dimension size = new Dimension(width, height);
		button.setPreferredSize(size);

		return button;
	}

	public JButton newIconButton(String label, int height, int width, String imageName) {
		
		ImageIcon icon = loadImageIconScaled(imageName, height, width);
		JButton button = newButton(label);
		button.setIcon(icon);
		// JButton button = this.newIconButton(imageName, height, width);
		button.setText(getHTMLCenteredLabel(label));
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
	
	public JLabel newLabel(String text, Fonts font) {
		JLabel label = new JLabel(getHTMLCenteredLabel(text));
		label.setFont(font.getFont());
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		
		return label;
	}
	
	public JTextField newTextField(String text, Fonts font) {
		JTextField field = new JTextField(text);
		field.setForeground(ColorPalette.GREY.getColor());
		
		field.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            if (field.getText().equals(text)) {
	                field.setText("");
	                field.setForeground(ColorPalette.BLACK.getColor());
	            }
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (field.getText().isEmpty()) {
	                field.setText(text);
	                field.setForeground(ColorPalette.GREY.getColor());
	            }
	        }
	    });
		
		return field;
	}
	
	public JSpinner spinnerFecha(Fonts font) {
	    SpinnerDateModel modelo = new SpinnerDateModel();
	    JSpinner spinner = new JSpinner(modelo);
	    JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy HH:mm");
	    spinner.setEditor(editor);
	    spinner.setFont(font.getFont());
	    return spinner;
	}
	
	public <T> JComboBox<T> newComboBox(Fonts font, T...elementos) {
		JComboBox<T> comboBox = new JComboBox<T>(elementos);
		comboBox.setFont(font.getFont());
		
		return comboBox;
	}

}
