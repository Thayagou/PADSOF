package vistas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelFactory {
	private double DF_GAP = 0.05;
	public PanelFactory() {}
	
	public JPanel gridBackLabelText(String[] labelNames, String[] textNames, int buttonHeight, double horProp, Fonts font) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		TiendaFrame t = TiendaFrame.getInstance();
		int hGap = t.getPixelsHeight(DF_GAP);
		int wGap = t.getPixelsWidth(DF_GAP);
		gbc.insets = new Insets(hGap, wGap, hGap, wGap);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		Dimension size = new Dimension(0, buttonHeight);
		
		JLabel left;
		JTextField right;
		ButtonFactory factory = new ButtonFactory();
		for (int i = 0; i < Math.min(labelNames.length, textNames.length); i++) {
			left = factory.newLabel(labelNames[i], font);
			left.setPreferredSize(size);
			right = factory.newTextField(textNames[i], font);
			right.setPreferredSize(size);
			
			gbc.gridx = 0; gbc.gridy = i; gbc.weightx = horProp;
			panel.add(left, gbc);

			gbc.gridx = 1; gbc.weightx = 1 - horProp; // el campo se expande
			panel.add(right, gbc);
		}
		
		return panel;
	}
}
