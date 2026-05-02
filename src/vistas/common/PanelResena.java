package vistas.common;

import java.awt.*;
import javax.swing.*;
import vistas.herramientas.*;

public class PanelResena extends JPanel {
	private static final long serialVersionUID = 1L;

	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();

	public PanelResena(double puntuacion, String comentario, String usr) {
		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();

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

		header.add(buildEstrellas(t, puntuacion));

		// ── Comentario ────────────────────────────────────────────────
		JTextArea comentTxt = new JTextArea(comentario);
		comentTxt.setFont(Fonts.SMALL.getFont());
		comentTxt.setLineWrap(true);
		comentTxt.setWrapStyleWord(true);
		comentTxt.setEditable(false);
		comentTxt.setOpaque(false);
		comentTxt.setForeground(ColorPalette.DARK_GREY.getColor());

		add(header, BorderLayout.NORTH);
		add(comentTxt, BorderLayout.CENTER);

		// ── Hover effect ──────────────────────────────────────────────
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				gradStart = ColorPalette.CARD_LIGHT_HOVER.getColor();
				gradEnd = ColorPalette.CARD_DARK_HOVER.getColor();
				repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				gradStart = ColorPalette.CARD_LIGHT.getColor();
				gradEnd = ColorPalette.CARD_DARK.getColor();
				repaint();
			}
		});
	}

	// ── Estrellas ────────────────────────────────────────────────────
	private JPanel buildEstrellas(TiendaFrame t, double valoracion) {
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

	// ── Fondo con gradiente ──────────────────────────────────────────
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