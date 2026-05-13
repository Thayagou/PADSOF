package vistas.common.displays;

import java.awt.*;
import javax.swing.*;

import vistas.herramientas.*;

/**
 * Panel que usamos para mostrar las reseñas de un producto
 */
public class PanelResena extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo gradStart. */
	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	
	/** Campo gradEnd. */
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param puntuacion Puntuación de la reseña
	 * @param comentario Comentario de la reseña
	 * @param usr Nombre del usuario que la ha realizado
	 */
	public PanelResena(double puntuacion, String comentario, String usr) {
		setOpaque(false);
		setLayout(new BorderLayout(30, 0));
		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.BLACK.getColor()),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		setLayout(new BorderLayout(10, 5));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.CARD_DARK.getColor()),
				BorderFactory.createEmptyBorder(8, 10, 8, 10)));

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		header.setOpaque(false);

		JPanel avatar = PanelFactory.buildAvatar();
		header.add(avatar);

		JLabel usuario = new JLabel(usr);
		usuario.setFont(Fonts.BOLD.getFont());
		usuario.setForeground(ColorPalette.DARK_GREY.getColor());
		header.add(usuario);

		header.add(buildEstrellas(puntuacion));

		JTextArea comentTxt = new JTextArea(comentario);
		comentTxt.setFont(Fonts.SMALL.getFont());
		comentTxt.setLineWrap(true);
		comentTxt.setWrapStyleWord(true);
		comentTxt.setEditable(false);
		comentTxt.setOpaque(false);
		comentTxt.setForeground(ColorPalette.DARK_GREY.getColor());

		add(header, BorderLayout.NORTH);
		add(comentTxt, BorderLayout.CENTER);
	}

	/**
	 * Contruye el panel de las estrellas  de la reseña
	 *
	 * @param valoracion parámetro valoracion
	 * @return valor de tipo JPanel
	 */
	private JPanel buildEstrellas(double valoracion) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
		p.setOpaque(false);

		int llenas = (int) Math.round(valoracion);
		for (int i = 1; i <= 5; i++) {
			JLabel star = new JLabel("★");
			star.setFont(Fonts.BOLD.getFont());
			star.setForeground(i <= llenas ? ColorPalette.YELLOW.getColor() : ColorPalette.LIGHT_GREY.getColor());
			p.add(star);
		}
		return p;
	}

	/**
	 * Añade el gradiente al fondo del panel
	 *
	 * @param g Unidad gráfica del panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		GradientPaint gp = new GradientPaint(0, 0, gradStart, 0, h, gradEnd);
		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h);

		g2.dispose();

		super.paintComponent(g);
	}
}