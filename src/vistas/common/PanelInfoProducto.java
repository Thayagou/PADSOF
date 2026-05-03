package vistas.common;

import java.awt.*;

import javax.swing.*;

import vistas.herramientas.*;

public class PanelInfoProducto extends JPanel {

	private static final long serialVersionUID = 1L;

	// ========== DEFINICIÓN DE MACROS (valores relativos para 1920x1080) ==========
	final double BORDER_HORIZONTAL_MACRO = 24.0 / 1920.0; /* 24px en 1920px → 0.0125 */
	final double BORDER_VERTICAL_MACRO = 20.0 / 1080.0; /* 20px en 1080px → 0.0185185 */

	/** Constante FOTO_H_PERC. */
	private static final double FOTO_H_PERC = 0.35;

	final double STRUT_AFTER_STARS_MACRO = 6.0 / 1080.0; /* 6px en 1080px → 0.005555 */
	final double STRUT_BETWEEN_CATEGORIES_MACRO = 4.0 / 1080.0; /* 4px en 1080px → 0.003703 */
	final double STRUT_BEFORE_IMAGE_MACRO = 10.0 / 1080.0; /* 10px en 1080px → 0.009259 */
	final double STRUT_BEFORE_PRICE_MACRO = 10.0 / 1080.0; /* igual al anterior */
	final double STRUT_BEFORE_DESCRIPTION_MACRO = 10.0 / 1080.0; /* igual al anterior */

	final double FLOW_HGAP_MACRO = 1.0 / 1920.0; /* 1px en 1920px → 0.0005208 */
	final double FLOW_VGAP_MACRO = 0.0; /* 0px (se mantiene 0, no requiere conversión) */

	final int MAX_STARS = 5; /* Cantidad de estrellas a mostrar */

	final String PRICE_FORMAT = "Precio: %.2f €"; /* Dos decimales fijos */

	public PanelInfoProducto(String nombre, String descripcion, String image, double puntuacionMedia, double precio,
			String... categorias) {

		TiendaFrame t = TiendaFrame.getInstance();

		int borderHor = t.getPixelsWidth(BORDER_HORIZONTAL_MACRO);
		int borderVer = t.getPixelsHeight(BORDER_VERTICAL_MACRO);
		int strutAfterStars = t.getPixelsHeight(STRUT_AFTER_STARS_MACRO);
		int strutBetweenCats = t.getPixelsHeight(STRUT_BETWEEN_CATEGORIES_MACRO);
		int strutBeforeImage = t.getPixelsHeight(STRUT_BEFORE_IMAGE_MACRO);
		int strutBeforePrice = t.getPixelsHeight(STRUT_BEFORE_PRICE_MACRO);
		int strutBeforeDesc = t.getPixelsHeight(STRUT_BEFORE_DESCRIPTION_MACRO);
		int flowHgap = t.getPixelsWidth(FLOW_HGAP_MACRO);
		int flowVgap = t.getPixelsHeight(FLOW_VGAP_MACRO);

		setOpaque(true);
		setBackground(ColorPalette.WHITE.getColor());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(borderVer, borderHor, borderVer, borderHor));

		// Estrellas
		add(buildEstrellas(t, puntuacionMedia, flowHgap, flowVgap, MAX_STARS));
		add(Box.createVerticalStrut(strutAfterStars));

		// Nombre
		JLabel nombreLabel = new JLabel(nombre);
		nombreLabel.setFont(Fonts.SUBTITLE.getFont());
		nombreLabel.setForeground(Color.BLACK);
		nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
		add(nombreLabel);

		// Categorías
		for (String c : categorias) {
			JLabel catLabel = new JLabel(c);
			catLabel.setFont(Fonts.TEXT.getFont());
			catLabel.setForeground(ColorPalette.PURPLE.getColor());
			catLabel.setAlignmentX(LEFT_ALIGNMENT);
			add(Box.createVerticalStrut(strutBetweenCats));
			add(catLabel);
		}

		// Foto placeholder
		int fotoH = t.getPixelsHeight(FOTO_H_PERC);
		JPanel foto = new JPanel();
		foto.add(new JLabel(new ButtonFactory().loadImageIconScaled(image, fotoH, fotoH)));
		add(Box.createVerticalStrut(strutBeforeImage));
		add(foto);

		// Precio
		JLabel precioLabel = new JLabel(String.format(PRICE_FORMAT, precio));
		precioLabel.setFont(Fonts.TITLE3.getFont());
		precioLabel.setForeground(Color.BLACK);
		precioLabel.setAlignmentX(LEFT_ALIGNMENT);
		add(Box.createVerticalStrut(strutBeforePrice));
		add(precioLabel);

		// Descripción
		JTextArea desc = new JTextArea(descripcion);
		desc.setFont(Fonts.TEXT.getFont());
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		desc.setEditable(false);
		desc.setOpaque(false);
		desc.setForeground(ColorPalette.DARK_GREY.getColor());
		desc.setAlignmentX(LEFT_ALIGNMENT);
		add(Box.createVerticalStrut(strutBeforeDesc));
		add(desc);
	}

	/**
	 * buildEstrellas.
	 *
	 * @param t   parámetro t
	 * @param val parámetro val
	 * @return valor de tipo JPanel
	 */
	private JPanel buildEstrellas(TiendaFrame t, double val, int hgap, int vgap, int maxStars) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, hgap, vgap));
		p.setOpaque(false);
		int llenas = (int) Math.round(val);
		for (int i = 1; i <= maxStars; i++) {
			JLabel s = new JLabel("★");
			s.setFont(Fonts.TITLE3.getFont());
			s.setForeground(i <= llenas ? ColorPalette.YELLOW.getColor() : ColorPalette.LIGHT_GREY.getColor());
			p.add(s);
		}
		return p;
	}
}
