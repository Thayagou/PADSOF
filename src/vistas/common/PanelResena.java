package vistas.common;

import java.awt.*;
import javax.swing.*;
import modelo.venta.productos.Resena;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelResena extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int AVATAR_SIZE = 40;

	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();

	private final Resena resena;

	public PanelResena(Resena resena) {
		this.resena = resena;
		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();

		setLayout(new BorderLayout(10, 5));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.CARD_DARK.getColor()),
				BorderFactory.createEmptyBorder(8, 10, 8, 10)));

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		header.setOpaque(false);

		JPanel avatar = buildAvatar();
		header.add(avatar);

		JLabel usuario = new JLabel(resena.getUsuario().getNombre());
		usuario.setFont(t.getTextFont());
		usuario.setForeground(ColorPalette.DARK_GREY.getColor());
		header.add(usuario);

		header.add(buildEstrellas(t, resena.getPuntuacion()));

		// ── Comentario ────────────────────────────────────────────────
		JTextArea comentario = new JTextArea(resena.getComentario());
		comentario.setFont(t.getTextFont());
		comentario.setLineWrap(true);
		comentario.setWrapStyleWord(true);
		comentario.setEditable(false);
		comentario.setOpaque(false);
		comentario.setForeground(ColorPalette.DARK_GREY.getColor());

		add(header, BorderLayout.NORTH);
		add(comentario, BorderLayout.CENTER);

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

	private JPanel buildAvatar() {
		TiendaFrame t = TiendaFrame.getInstance();
		int size = t.getPixelsHeight(0.04);

		ButtonFactory f = new ButtonFactory();
		ImageIcon icon = f.loadImageIconScaled("pfp.png", size, size);

		return new JPanel() {
			private static final long serialVersionUID = 1L;

			{
				setOpaque(false);
				setPreferredSize(new Dimension(size, size));
				setMinimumSize(new Dimension(size, size));
				setMaximumSize(new Dimension(size, size));
			}

			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();

				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				Shape clip = new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight());
				g2.setClip(clip);

				g2.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), null);

				g2.dispose();
			}
		};
	}

	// ── Estrellas ────────────────────────────────────────────────────
	private JPanel buildEstrellas(TiendaFrame t, double valoracion) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
		p.setOpaque(false);

		int llenas = (int) Math.round(valoracion);
		for (int i = 1; i <= 5; i++) {
			JLabel star = new JLabel("★");
			star.setFont(t.getTextFont());
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