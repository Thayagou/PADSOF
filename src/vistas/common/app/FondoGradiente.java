package vistas.common.app;

import javax.swing.*;

import vistas.herramientas.ColorPalette;

import java.awt.*;

/**
 * Panel usado para el fondo gradiente de la tienda
 */
public class FondoGradiente extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Color del inicio derecho del gradiente */
	private Color colorInicio = ColorPalette.BG_PURPLE.getColor();
	
	/** Color del fin izquierdo del gradiente */
	private Color colorFin = ColorPalette.BG_BLUE.getColor();
	
	/** Radio de las esquinas */
	private int radio = 0;
	
	/**
	 * Instancia un nuevo Objeto FondoGradiente.
	 */
	public FondoGradiente() {
		setOpaque(false);
		setLayout(new BorderLayout());
	}

	/**
	 * Pinta el fondo con el gradiente establecido
	 *
	 * @param g Unidad gráfica del panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		// Gradiente
		GradientPaint gp;
		gp = new GradientPaint(0, 0, colorInicio, w, h, colorFin);
		g2.setPaint(gp);
		g2.fillRoundRect(0, 0, w, h, radio, radio);

		
		g2.dispose();
	}
}
