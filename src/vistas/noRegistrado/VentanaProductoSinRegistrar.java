package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import vistas.common.*;
import vistas.herramientas.*;

// TODO: Auto-generated Javadoc
/**
 * Vista detallada de un producto (maqueta 5). Layout: izquierda = panel de
 * valoraciones/reseñas (scrolleable), derecha = foto grande + nombre +
 * categorías + precio + descripción.
 */
public class VentanaProductoSinRegistrar extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante REVIEWS_W_PERC. */
	private static final double REVIEWS_W_PERC = 0.33;

	/** Constante FOTO_H_PERC. */
	private static final double FOTO_H_PERC = 0.35;

	/** Campo resenasPanel. */
	private JPanel resenasPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaProductoSinRegistrar.
	 *
	 * @param nombre          parámetro nombre
	 * @param descripcion     parámetro descripcion
	 * @param puntuacionMedia parámetro puntuacionMedia
	 * @param precio          parámetro precio
	 * @param categorias      parámetro categorias
	 */
	public VentanaProductoSinRegistrar(String nombre, String descripcion, String image, double puntuacionMedia, double precio,
			String... categorias) {
		TiendaFrame t = TiendaFrame.getInstance();

		resenasPanel.setLayout(new BoxLayout(resenasPanel, BoxLayout.Y_AXIS));
		resenasPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		setOpaque(false);
		setLayout(new BorderLayout());

		int reviewsW = t.getPixelsWidth(REVIEWS_W_PERC);

		/* Panel izquierdo - Valoraciones */
		JScrollPane scrollResenas = PanelFactory.getScroll(resenasPanel);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scrollResenas);

		JPanel resenas = PanelFactory.getVentanaConCabecera("Reseñas", contenido);
		resenas.setPreferredSize(new Dimension(reviewsW, 0));

		/* Panel derecho - Detalle del producto */
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(true);
		rightPanel.setBackground(ColorPalette.WHITE.getColor());
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

		// Estrellas
		rightPanel.add(buildEstrellas(t, puntuacionMedia));
		rightPanel.add(Box.createVerticalStrut(6));

		// Nombre
		JLabel nombreLabel = new JLabel(nombre);
		nombreLabel.setFont(Fonts.SUBTITLE.getFont());
		nombreLabel.setForeground(Color.BLACK);
		nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(nombreLabel);

		// Categorías
		for (String c : categorias) {
			JLabel catLabel = new JLabel(c);
			catLabel.setFont(Fonts.TEXT.getFont());
			catLabel.setForeground(ColorPalette.PURPLE.getColor());
			catLabel.setAlignmentX(LEFT_ALIGNMENT);
			rightPanel.add(Box.createVerticalStrut(4));
			rightPanel.add(catLabel);
		}

		// Foto placeholder
		int fotoH = t.getPixelsHeight(FOTO_H_PERC);
		JPanel foto = new JPanel();
		foto.add(new JLabel(new ButtonFactory().loadImageIconScaled(image, fotoH, fotoH)));
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(foto);

		// Precio
		JLabel precioLabel = new JLabel(String.format("Precio: %.2f €", precio));
		precioLabel.setFont(Fonts.TITLE3.getFont());
		precioLabel.setForeground(Color.BLACK);
		precioLabel.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(precioLabel);

		// Descripción
		JTextArea desc = new JTextArea(descripcion);
		desc.setFont(Fonts.TEXT.getFont());
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setEditable(false);
		desc.setOpaque(false);
		desc.setForeground(ColorPalette.DARK_GREY.getColor());
		desc.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(desc);

		add(resenas, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}

	/**
	 * anadirPanelResena.
	 *
	 * @param puntuacion parámetro puntuacion
	 * @param comentario parámetro comentario
	 * @param usr        parámetro usr
	 */
	public void anadirPanelResena(double puntuacion, String comentario, String usr) {
		resenasPanel.add(new PanelResena(puntuacion, comentario, usr));
	}

	/**
	 * buildEstrellas.
	 *
	 * @param t   parámetro t
	 * @param val parámetro val
	 * @return valor de tipo JPanel
	 */
	private JPanel buildEstrellas(TiendaFrame t, double val) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
		p.setOpaque(false);
		int llenas = (int) Math.round(val);
		for (int i = 1; i <= 5; i++) {
			JLabel s = new JLabel("★");
			s.setFont(Fonts.TITLE3.getFont());
			s.setForeground(i <= llenas ? ColorPalette.YELLOW.getColor() : ColorPalette.LIGHT_GREY.getColor());
			p.add(s);
		}
		return p;
	}
}
