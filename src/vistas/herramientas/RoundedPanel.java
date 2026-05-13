package vistas.herramientas;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Clase Rounded Panel que representa un panel circular en la tienda
 */
public class RoundedPanel extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Radio del panel */
	private int radius;

    /**
     * Instancia un nuevo Objeto RoundedPanel.
     *
     * @param radius Radio a establecer
     */
    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    /**
     * Establece el dibujo redondo al panel
     *
     * @param Unidad gráfica del panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
    }
}
