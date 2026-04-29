package vistas.herramientas;

import javax.swing.*;
import java.awt.*;

class RoundedButton extends JButton {
	private static final long serialVersionUID = 1L;
	private double roundness;

	public RoundedButton(String text, double roundness) {
		super(text);
		if (roundness < 0) roundness = 0;
		if (roundness > 1) roundness = 1;
		this.roundness = roundness;
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int dynamicRadius = (int) (Math.min(getHeight(), getWidth()) * roundness);

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), dynamicRadius, dynamicRadius);

		super.paintComponent(g);
		g2.dispose();
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		int dynamicRadius = (int) (Math.min(getHeight(), getWidth()) * roundness);

		g2.setColor(getForeground());
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, dynamicRadius, dynamicRadius);

		g2.dispose();
	}
}