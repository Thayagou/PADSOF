package vistas.common.assets;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;

public class PanelFotoPerfil extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image imagen;
	private int size;

	public PanelFotoPerfil(String imageName, int size) {
		this.size = size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(size, size));
		this.setMaximumSize(new Dimension(size, size));

		ButtonFactory f = new ButtonFactory();
		ImageIcon icon = f.loadImageIcon(imageName);
		imagen = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Clip circular
		g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));
		g2.drawImage(imagen, 0, 0, this);

		g2.dispose();
	}
}