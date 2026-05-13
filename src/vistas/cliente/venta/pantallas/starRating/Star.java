package vistas.cliente.venta.pantallas.starRating;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 * Componente de estrella individual para el selector de puntuación.
 */
public class Star extends JButton {

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/**
	 * Configura la estrella sin fondo pintado, con cursor de mano y colores por defecto.
	 */
	public Star() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(new Color(200, 200, 200));
        setForeground(Color.yellow);
    }

    /**
     * Dibuja la estrella utilizando la forma Star2D.
     *
     * @param grphcs contexto gráfico donde se pintará la estrella.
     */
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) / 2;
        int x = width / 2;
        int y = height / 2;
        Star2D s = new Star2D(x, y, size / 2, size, 5);
        g2.setColor(getBackground());
        g2.fill(s);
        if (isSelected()) {
            g2.setColor(getForeground());
            g2.fill(s);
        }
        g2.dispose();
    }
}