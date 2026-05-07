package vistas.common;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import vistas.herramientas.*;

/**
 * Panel de detalle de un producto.
 *
 * Estructura identica a VentanaInfoArticulo, con las siguientes diferencias: -
 * El avatar y nombre de usuario se sustituyen por las estrellas de valoracion.
 * - El estado fisico desaparece. - La estimacion se sustituye por el precio. -
 * La seccion "Busca a cambio" se sustituye por "Caracteristicas".
 */
public class PanelInfoProducto extends JPanel {

	private static final long serialVersionUID = 1L;

	/* Proporciones generales */
	private static final double PADDING = 0.02;
	private static final double GAP = 0.015;

	/* Foto del producto */
	private static final double FOTO_W = 0.32;
	private static final double FOTO_H = 0.32;

	/* Estrellas */
	private static final int MAX_STARS = 5;
	private static final int STAR_HGAP = 2;
	private static final int STAR_VGAP = 0;

	/* Truncado de textos en la columna derecha */
	private static final double TEXT_MAX_W = 0.29;

	/* Numero maximo de lineas en las text areas */
	private static final int DESC_MAX_LINES = 8;
	private static final int CARACT_MAX_LINES = 5;

	/* Factor de reduccion del ancho para el scroll (deja margen para la barra) */
	private static final double SCROLL_W_MARGIN = 0.05;

	public PanelInfoProducto(String nombre, String descripcion, String image, double puntuacionMedia, double precio,
			String caracteristicas, String... categorias) {

		TiendaFrame t = TiendaFrame.getInstance();
		int pad = t.getPixelsWidth(PADDING);
		int gap = t.getPixelsWidth(GAP);

		setOpaque(true);
		setBackground(ColorPalette.WHITE.getColor());
		setLayout(new BorderLayout(gap, gap));

		JPanel contenido = buildContenido(t, nombre, descripcion, image, puntuacionMedia, precio, caracteristicas,
				categorias, gap);

		contenido.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

		add(contenido, BorderLayout.CENTER);
	}

	/* Construye el panel principal de dos columnas. */
	private JPanel buildContenido(TiendaFrame t, String nombre, String descripcion, String image,
			double puntuacionMedia, double precio, String caracteristicas, String[] categorias, int gap) {

		JPanel panel = new JPanel(new GridLayout(1, 2, gap, 0));
		panel.setOpaque(false);

		panel.add(buildColumnaIzquierda(t, image, descripcion, gap));
		panel.add(buildColumnaDerecha(t, nombre, puntuacionMedia, precio, caracteristicas, categorias, gap));

		return panel;
	}

	/* Columna izquierda: foto del producto + seccion descripcion. */
	private JPanel buildColumnaIzquierda(TiendaFrame t, String image, String descripcion, int gap) {

		JPanel columna = new JPanel(new GridLayout(2, 1));
		columna.setOpaque(false);

		/* Foto */
		JPanel fotoPanel = new JPanel();
		fotoPanel.setOpaque(false);
		fotoPanel.setLayout(new BoxLayout(fotoPanel, BoxLayout.Y_AXIS));
		fotoPanel.add(buildFotoProducto(t, image));
		fotoPanel.add(Box.createVerticalStrut(gap));
		columna.add(fotoPanel);

		/* Descripcion */
		JPanel descripcionPanel = new JPanel();
		descripcionPanel.setLayout(new BoxLayout(descripcionPanel, BoxLayout.Y_AXIS));
		descripcionPanel.setOpaque(false);

		JLabel lblDesc = ButtonFactory.newLabel("Descripcion:", Fonts.BOLD);
		lblDesc.setAlignmentX(LEFT_ALIGNMENT);
		descripcionPanel.add(lblDesc);
		descripcionPanel.add(Box.createVerticalStrut(gap));
		descripcionPanel.add(buildTextArea(t, descripcion, DESC_MAX_LINES));

		columna.add(descripcionPanel);

		return columna;
	}

	/* Columna derecha: estrellas, nombre, categorias, precio, caracteristicas. */
	private JPanel buildColumnaDerecha(TiendaFrame t, String nombre, double puntuacionMedia, double precio,
			String caracteristicas, String[] categorias, int gap) {

		JPanel columna = new JPanel(new GridLayout(2, 1));
		columna.setOpaque(false);

		/* Fila superior: estrellas, nombre, categorias, precio */
		JPanel fila1 = new JPanel(new GridLayout(5, 1));
		fila1.setOpaque(false);

		fila1.add(buildEstrellas(t, puntuacionMedia, STAR_HGAP, STAR_VGAP, MAX_STARS));

		fila1.add(buildTextoSimple(t, nombre, Fonts.SUBTITLE, ColorPalette.BLACK));

		fila1.add(buildTextoSimple(t, String.join(", ", categorias), Fonts.TEXT, ColorPalette.PURPLE));

		fila1.add(buildTextoSimple(t, String.format("Precio: %.2f EUR", precio), Fonts.BOLD, ColorPalette.BLACK));
		
		Dimension fillerDim = new Dimension(gap, gap);
		fila1.add(new Box.Filler(fillerDim, fillerDim, fillerDim));

		columna.add(fila1);

		/* Fila inferior: seccion caracteristicas */
		JPanel fila2 = new JPanel();
		fila2.setOpaque(false);
		fila2.setLayout(new BoxLayout(fila2, BoxLayout.Y_AXIS));

		JLabel lblCaract = ButtonFactory.newLeftAlignedLabel("Caracteristicas:", Fonts.BOLD);
		lblCaract.setForeground(ColorPalette.BLACK.getColor());
		lblCaract.setAlignmentX(LEFT_ALIGNMENT);
		fila2.add(lblCaract);
		fila2.add(Box.createVerticalStrut(gap));
		fila2.add(buildTextArea(t, caracteristicas, CARACT_MAX_LINES));

		columna.add(fila2);

		return columna;
	}

	/* Panel con imagen del producto centrada sobre fondo CARD_DARK. */
	private JPanel buildFotoProducto(TiendaFrame t, String image) {

		int fotoW = t.getPixelsWidth(FOTO_W);
		int fotoH = t.getPixelsHeight(FOTO_H);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(true);
		panel.setBackground(ColorPalette.CARD_DARK.getColor());
		panel.setPreferredSize(new Dimension(fotoW, fotoH));
		panel.setMaximumSize(new Dimension(fotoW, fotoH));
		panel.setMinimumSize(new Dimension(fotoW, fotoH));

		JLabel img = new JLabel(ButtonFactory.loadImageInBounds(image, fotoH, fotoW));
		img.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(img);

		return panel;
	}

	/* Etiqueta de texto simple truncada al ancho maximo definido. */
	private JPanel buildTextoSimple(TiendaFrame t, String texto, Fonts fuente, ColorPalette color) {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setAlignmentX(LEFT_ALIGNMENT);

		String truncado = Fonts.truncar(texto, t.getPixelsWidth(TEXT_MAX_W), fuente.getFont(), panel);

		JLabel lbl = ButtonFactory.newLeftAlignedLabel(truncado, fuente);
		lbl.setForeground(color.getColor());

		panel.add(lbl, BorderLayout.WEST);

		return panel;
	}

	/* Text area con scroll y ajuste dinamico al ancho del viewport. */
	private JScrollPane buildTextArea(TiendaFrame t, String texto, int maxLines) {

		int maxWidth = t.getPixelsWidth(TEXT_MAX_W - SCROLL_W_MARGIN);
		String truncado = truncarTextoMultilinea(texto, maxLines, maxWidth, Fonts.TEXT.getFont());

		JTextArea area = new FixedTextArea();
		area.setText(truncado);
		area.setEditable(false);
		area.setOpaque(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(Fonts.TEXT.getFont());
		area.setForeground(ColorPalette.DARK_GREY.getColor());
		area.setSize(maxWidth, Short.MAX_VALUE);

		JPanel areaContent = new JPanel(new BorderLayout());
		areaContent.setOpaque(true);
		areaContent.setBackground(ColorPalette.WHITE.getColor());
		areaContent.add(area, BorderLayout.CENTER);

		JScrollPane scroll = PanelFactory.getScroll(areaContent);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setAlignmentX(LEFT_ALIGNMENT);

		/* Ajusta el ancho del area al viewport cuando este se redimensiona */
		scroll.getViewport().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int newWidth = scroll.getViewport().getWidth();
				if (newWidth > 0) {
					area.setSize(newWidth, Short.MAX_VALUE);
					area.revalidate();
				}
			}
		});

		return scroll;
	}

	/* Fila de estrellas rellenas o vacias segun la puntuacion media. */
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

	/* Trunca un texto a un maximo de lineas respetando el ancho en pixeles. */
	private String truncarTextoMultilinea(String texto, int maxLines, int maxWidth, Font font) {

		if (texto == null) {
			return "";
		}

		FontMetrics fm = getFontMetrics(font);
		String[] palabras = texto.split(" ");
		StringBuilder resultado = new StringBuilder();
		String lineaActual = "";
		int lineas = 1;

		for (String palabra : palabras) {

			String candidata = lineaActual.isEmpty() ? palabra : lineaActual + " " + palabra;

			if (fm.stringWidth(candidata) <= maxWidth) {
				lineaActual = candidata;
			} else {

				if (lineas >= maxLines) {
					resultado.append(Fonts.truncar(lineaActual, maxWidth, font, this));
					resultado.append("...");
					return resultado.toString();
				}

				resultado.append(lineaActual).append("\n");
				lineaActual = palabra;
				lineas++;
			}
		}

		resultado.append(lineaActual);

		return resultado.toString();
	}
}