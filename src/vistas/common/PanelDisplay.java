package vistas.common;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelDisplay extends JPanel{
	private static final long serialVersionUID = 1L;

	protected static final double BOTON_PERC_H = 0.3;
	protected static final double BOTON_PERC_W = 0.2;
	protected static double HOR_GAP = 0.01;
	protected final JButton clickArea; // botón invisible que ocupa todo el panel
	protected int maxHeight;
	protected int maxCompHeight;
	private ColorPalette gradStart = ColorPalette.CARD_LIGHT;
	private ColorPalette gradEnd = ColorPalette.CARD_DARK;
	
	public PanelDisplay(double maxHPerc, double compHPerc, double fotoWPerc, String imageName, String actionName) {
		this(maxHPerc, compHPerc, actionName);
		
		anadirFoto(imageName, fotoWPerc);
	}
	
	public PanelDisplay(double maxHPerc, double compHPerc, String actionName) {
		setOpaque(false);

	
		TiendaFrame t = TiendaFrame.getInstance();
		maxCompHeight = t.getPixelsHeight(compHPerc);
		maxHeight = t.getPixelsHeight(maxHPerc);
		

		setLayout(new BorderLayout(30, 0));
		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.BLACK.getColor()),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));
		
		
		
		// Botón invisible para detectar clic en toda la fila
		clickArea = new JButton();
		clickArea.setOpaque(false);
		clickArea.setContentAreaFilled(false);
		clickArea.setBorderPainted(false);
		clickArea.setActionCommand(actionName);
		clickArea.setPreferredSize(new Dimension(0, 0));

		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
			    gradStart = ColorPalette.CARD_LIGHT_HOVER;
			    gradEnd = ColorPalette.CARD_DARK_HOVER;
			    repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
			    gradStart = ColorPalette.CARD_LIGHT;
			    gradEnd = ColorPalette.CARD_DARK;
			    repaint();
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				clickArea.doClick();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		// Gradiente (puedes ajustar colores)
		GradientPaint gp = new GradientPaint(0, 0, gradStart.getColor(), 0, h, gradEnd.getColor());

		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h);

		g2.dispose();

		super.paintComponent(g);
	}
	
	public void setControlador(ActionListener l) {
		clickArea.addActionListener(l);
	}
	
	public void anadirFoto(String imageName, double fotoWPerc) {
		TiendaFrame t = TiendaFrame.getInstance();
		int fotoW = t.getPixelsWidth(fotoWPerc);
		// — Foto placeholder —
		JPanel foto = new JPanel();
		foto.setBackground(ColorPalette.CARD_DARK.getColor());
		foto.setPreferredSize(new Dimension(fotoW, maxCompHeight));
		JLabel fotoLabel = new JLabel(ButtonFactory.loadImageIconScaled(imageName, maxCompHeight, fotoW));
		fotoLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		fotoLabel.setFont(Fonts.BOLD.getFont());
		foto.add(fotoLabel);
			
		add(foto, BorderLayout.WEST);
	}
	
	public void refreshDisplay() {
		revalidate();
		repaint();
	}
	
}
