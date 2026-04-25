package vistas;

import javax.swing.*;
import java.awt.*;

public class FondoGradiente extends JPanel {

	private static final long serialVersionUID = 1L;
	private Color colorInicio = new Color(80, 0, 200);
	private Color colorFin = new Color(160, 0, 180);
	private Color colorBorde = Color.WHITE;
	private int grosorBorde = 2;
	private int radio = 20;
	
	private BarraTareas barra = new BarraTareas();

	public FondoGradiente() {
		setOpaque(false);
		setLayout(new BorderLayout());
	    add(barra, BorderLayout.NORTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		// Gradiente
		GradientPaint gp;
		gp = new GradientPaint(0, 0, colorInicio, 0, h, colorFin);
		g2.setPaint(gp);
		g2.fillRoundRect(0, 0, w, h, radio, radio);

		// Borde
		g2.setColor(colorBorde);
		g2.setStroke(new BasicStroke(grosorBorde));
		g2.drawRoundRect(grosorBorde / 2, grosorBorde / 2, w - grosorBorde, h - grosorBorde, radio, radio);

		g2.dispose();
	}
}
