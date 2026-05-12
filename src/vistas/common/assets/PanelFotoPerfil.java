package vistas.common.assets;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;

/**
 * Subclase de panel que sirve para hacer display de una foto de perfil en forma circular
 */
public class PanelFotoPerfil extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Imagen cargada */
	private Image imagen;
	
	/** Circunferencia de la foto de perfil */
	private int size;

	/**
	 * Crea un panel en el que se ajusta la foto
	 * 
	 * @param imageName Nombre de la imagen
	 * @param size Tamaño de la imagen
	 */
	public PanelFotoPerfil(String imageName, int size) {
		this.size = size;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(size, size));
		this.setMaximumSize(new Dimension(size, size));

		ImageIcon icon = ButtonFactory.loadImageIcon(imageName);
		imagen = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
	}

	/**
	 * Pinta el panel de tal forma que la imagen es circular
	 *
	 * @param g Unidad gráfica del panel
	 */
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