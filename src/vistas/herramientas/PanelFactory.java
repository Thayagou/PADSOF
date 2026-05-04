package vistas.herramientas;

import java.awt.*;
import javax.swing.*;

import vistas.common.TiendaFrame;

public class PanelFactory {
	private double DF_GAP = 0.05;

	public PanelFactory() {
	}

	public JPanel gridBackLabelText(String[] labelNames, String[] textNames, int buttonHeight, double horProp,
			Fonts font) {
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
		for (int i = 0; i < Math.min(labelNames.length, textNames.length); i++) {
			left = ButtonFactory.newLabel(labelNames[i], font);
			left.setPreferredSize(size);
			right = ButtonFactory.newTextField(textNames[i], font);
			right.setPreferredSize(size);

			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = horProp;
			panel.add(left, gbc);

			gbc.gridx = 1;
			gbc.weightx = 1 - horProp; // el campo se expande
			panel.add(right, gbc);
		}

		return panel;
	}

	public static JPanel getColumnaDeBotones(JButton... botones) {
		TiendaFrame t = TiendaFrame.getInstance();
		double VERTICAL_GAP = 0.07;

		JPanel btnPanel = new JPanel(new GridLayout(botones.length, 1, 0, t.getPixelsHeight(VERTICAL_GAP)));
		btnPanel.setOpaque(false);

		for (JButton btn : botones) {
			btnPanel.add(btn);
		}
		return wrapVertical(btnPanel, t.getPixelsHeight(VERTICAL_GAP));
	}
	
	public static JPanel getColumnaDeBotones(double VERTICAL_GAP_PERC, JButton... botones) {
		TiendaFrame t = TiendaFrame.getInstance();

		JPanel btnPanel = new JPanel(new GridLayout(botones.length, 1, 0, t.getPixelsHeight(VERTICAL_GAP_PERC)));
		btnPanel.setOpaque(false);

		for (JButton btn : botones) {
			btnPanel.add(btn);
		}
		return wrapVertical(btnPanel, t.getPixelsHeight(VERTICAL_GAP_PERC));
	}

	/**
	 * Devuelve un panel con el contenido y una cabecera
	 *
	 * @param title     Texto de la cabecera
	 * @param contenido Panel al que se añade la cabecera
	 * @return Panel con cabecera y contenido
	 */
	public static JPanel getVentanaConCabecera(String title, JPanel contenido) {
		JPanel ventana = new JPanel();
		ventana.setLayout(new BorderLayout());
		ventana.setOpaque(false);

		JLabel cabecera = new JLabel(title);
		cabecera.setFont(Fonts.TITLE3.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		ventana.add(BorderLayout.NORTH, cabecera);
		ventana.add(BorderLayout.CENTER, contenido);

		return ventana;
	}

	public static JPanel buildAvatar() {
		TiendaFrame t = TiendaFrame.getInstance();
		int size = t.getPixelsHeight(0.04);

		ImageIcon icon = ButtonFactory.loadImageIconScaled("pfp.png", size, size);

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

	/**
	 * Devuelve un scroll con el contenido de una lista
	 *
	 * @param lista Panel con los paneles que apareceran
	 * @return Scroll que recorre la lista
	 */
	public static JScrollPane getScroll(JPanel lista) {
		/* Ajustes del scroll */
		int speed = 16;//TiendaFrame.getInstance().getPixelsHeight(16/1080); /* Velocidad del scroll */
		double width = 0.011; /* Grosor de la barra vertical */
		Color bg = ColorPalette.DARK_GREY.getColor(); /* Color de fondo de la barra */
		Color fg = ColorPalette.PURPLE.getColor(); /* Color de frente de la barra */
		Color bc = ColorPalette.GREY.getColor(); /* Color del borde de la barra */
		double borderW = 0.003; /* Grosor del borde que rodea la barra */

		TiendaFrame t = TiendaFrame.getInstance();

		JScrollPane scroll = new JScrollPane(lista);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(speed);

		int borderPx = t.getPixelsWidth(borderW);

		JScrollBar vBar = scroll.getVerticalScrollBar();
		vBar.setPreferredSize(new Dimension(t.getPixelsWidth(width), 0));
		vBar.setBackground(bc);
		vBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = fg;
				this.trackColor = bg;
			}

			@Override
			protected JButton createDecreaseButton(int orientation) {
				return createZeroButton();
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return createZeroButton();
			}

			private JButton createZeroButton() {
				JButton b = new JButton();
				b.setPreferredSize(new Dimension(0, 0));
				b.setMinimumSize(new Dimension(0, 0));
				b.setMaximumSize(new Dimension(0, 0));
				return b;
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
				Graphics2D g2 = (Graphics2D) g.create();

				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2.setColor(thumbColor);

				// barra redondeada
				g2.fillRoundRect(r.x + borderPx, r.y + borderPx, r.width - borderPx * 2, r.height - borderPx * 2, 10,
						10);

				g2.dispose();
			}

			@Override
			protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
				Graphics2D g2 = (Graphics2D) g.create();

				int padding = borderPx;

				g2.setColor(trackColor);
				g2.fillRoundRect(r.x + padding, r.y + padding, r.width - 2 * padding, r.height - 2 * padding, 15, 15);

				g2.dispose();
			}
		});

		return scroll;
	}

	/**
	 * Añade un espacio arriba y abajo del panel
	 *
	 * @param panel Panel al que añadir espaciado
	 * @param space Espacio en píxeles que se añade arriba y abajo
	 * @return Panel con el Panel que se pasó con dos espacios arriba y abajo
	 */
	public static JPanel wrapVertical(JPanel panel, int space) {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setOpaque(false);

		wrapper.add(Box.createVerticalStrut(space));
		wrapper.add(panel);
		wrapper.add(Box.createVerticalStrut(space));

		return wrapper;
	}

	/**
	 * Añade un espacio a la izquierda y derecha del panel
	 *
	 * @param panel Panel al que añadir espaciado
	 * @param space Espacio en píxeles que se añade arriba y abajo
	 * @return Panel con el Panel que se pasó con dos espacios arriba y abajo
	 */
	public static JPanel wrapHorizontal(JPanel panel, int space) {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
		wrapper.setOpaque(false);

		wrapper.add(Box.createHorizontalStrut(space));
		wrapper.add(panel);
		wrapper.add(Box.createHorizontalStrut(space));

		return wrapper;
	}
}
