package vistas.common.assets;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import controladores.TiendaFrame;
import vistas.common.components.FixedTextArea;
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

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante PADDING. */
	/* Proporciones generales */
	private static final double PADDING = 0.02; /* Padding interior del panel */
	
	/** Constante GAP. */
	private static final double GAP = 0.015; /* Espacio entre componentes */

	/** Constante FOTO_W. */
	/* Foto del producto */
	private static final double FOTO_W = 0.32; /* Anchura de la foto del producto */
	
	/** Constante FOTO_H. */
	private static final double FOTO_H = 0.37; /* Altura de la foto del producto */

	/** Constante MAX_STARS. */
	/* Estrellas */
	private static final int MAX_STARS = 5; /* Número máximo de estrellas */
	
	/** Constante STAR_HGAP. */
	private static final int STAR_HGAP = 2; /* Espaciado horizontal entre estrellas */
	
	/** Constante STAR_VGAP. */
	private static final int STAR_VGAP = 0; /* Espaciado vertical entre estrellas */

	/** Constante TEXT_MAX_W. */
	/* Truncado de textos en la columna derecha */
	private static final double TEXT_MAX_W = 0.29; /* Anchura máxima para textos truncados */

	/** Constante DESC_MAX_LINES. */
	/* Numero maximo de lineas en las text areas */
	private static final int DESC_MAX_LINES = 8; /* Líneas máximas para la descripción */
	
	/** Constante CARACT_MAX_LINES. */
	private static final int CARACT_MAX_LINES = 5; /* Líneas máximas para las características */
	
	/** Constante BW. */
	private static final int BW = TiendaFrame.getInstance().getPixelsWidth(8.0/1080.0); /* Padding interno del text area */

	/** Constante SCROLL_W_MARGIN. */
	/* Factor de reduccion del ancho para el scroll (deja margen para la barra) */
	private static final double SCROLL_W_MARGIN = 0.05; /* Margen para el scroll horizontal */

	/**
	 * Instancia un nuevo Objeto PanelInfoProducto.
	 *
	 * @param nombre Nombre del producto.
	 * @param descripcion Descripción detallada del producto.
	 * @param image Ruta de la imagen del producto.
	 * @param puntuacionMedia Puntuación media del producto (0-5).
	 * @param precio Precio del producto en euros.
	 * @param caracteristicas Características destacadas del producto.
	 * @param categorias Categorías a las que pertenece el producto.
	 */
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

	/**
	 * buildContenido.
	 * Construye el panel principal de dos columnas.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param nombre Nombre del producto.
	 * @param descripcion Descripción del producto.
	 * @param image Ruta de la imagen del producto.
	 * @param puntuacionMedia Puntuación media del producto.
	 * @param precio Precio del producto.
	 * @param caracteristicas Características del producto.
	 * @param categorias Categorías del producto.
	 * @param gap Espacio entre columnas en píxeles.
	 * @return valor de tipo JPanel, el panel con dos columnas.
	 */
	/* Construye el panel principal de dos columnas. */
	private JPanel buildContenido(TiendaFrame t, String nombre, String descripcion, String image,
			double puntuacionMedia, double precio, String caracteristicas, String[] categorias, int gap) {

		JPanel panel = new JPanel(new GridLayout(1, 2, gap, 0));
		panel.setOpaque(false);

		panel.add(buildColumnaIzquierda(t, image, descripcion, gap));
		panel.add(buildColumnaDerecha(t, nombre, puntuacionMedia, precio, caracteristicas, categorias, gap));

		return panel;
	}

	/**
	 * buildColumnaIzquierda.
	 * Columna izquierda: foto del producto + seccion descripcion.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param image Ruta de la imagen del producto.
	 * @param descripcion Descripción del producto.
	 * @param gap Espacio entre componentes en píxeles.
	 * @return valor de tipo JPanel, la columna izquierda.
	 */
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

	/**
	 * buildColumnaDerecha.
	 * Columna derecha: estrellas, nombre, categorias, precio, caracteristicas.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param nombre Nombre del producto.
	 * @param puntuacionMedia Puntuación media del producto.
	 * @param precio Precio del producto.
	 * @param caracteristicas Características del producto.
	 * @param categorias Categorías del producto.
	 * @param gap Espacio entre componentes en píxeles.
	 * @return valor de tipo JPanel, la columna derecha.
	 */
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

	/**
	 * buildFotoProducto.
	 * Panel con imagen del producto centrada sobre fondo CARD_DARK.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param image Ruta de la imagen del producto.
	 * @return valor de tipo JPanel, el panel con la foto.
	 */
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
		panel.setBorder(BorderFactory.createLineBorder(ColorPalette.GREY.getColor()));

		return panel;
	}

	/**
	 * buildTextoSimple.
	 * Etiqueta de texto simple truncada al ancho maximo definido.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param texto Texto a mostrar.
	 * @param fuente Fuente del texto.
	 * @param color Color del texto.
	 * @return valor de tipo JPanel, el panel con la etiqueta.
	 */
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

	/**
	 * buildTextArea.
	 * Text area con scroll y ajuste dinamico al ancho del viewport.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param texto Texto a mostrar.
	 * @param maxLines Número máximo de líneas.
	 * @return valor de tipo JScrollPane, el scroll con el área de texto.
	 */
	/* Text area con scroll y ajuste dinamico al ancho del viewport. */
	private JScrollPane buildTextArea(TiendaFrame t, String texto, int maxLines) {

		int maxWidth = t.getPixelsWidth(TEXT_MAX_W - SCROLL_W_MARGIN);
		String truncado = truncarTextoMultilinea(texto, maxLines, maxWidth, Fonts.TEXT.getFont());

		JTextArea area = new FixedTextArea(truncado);
		area.setBorder(BorderFactory.createEmptyBorder(BW, BW, BW, BW));
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
		scroll.setBorder(BorderFactory.createLineBorder(ColorPalette.LIGHT_GREY.getColor()));

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

	/**
	 * buildEstrellas.
	 * Fila de estrellas rellenas o vacias segun la puntuacion media.
	 *
	 * @param t Referencia a TiendaFrame.
	 * @param val Valor de puntuación (0-5).
	 * @param hgap Espaciado horizontal entre estrellas.
	 * @param vgap Espaciado vertical entre estrellas.
	 * @param maxStars Número máximo de estrellas.
	 * @return valor de tipo JPanel, el panel con las estrellas.
	 */
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

	/**
	 * truncarTextoMultilinea.
	 * Trunca un texto a un maximo de lineas respetando el ancho en pixeles.
	 *
	 * @param texto Texto original a truncar.
	 * @param maxLines Número máximo de líneas permitidas.
	 * @param maxWidth Anchura máxima en píxeles.
	 * @param font Fuente utilizada para medir el texto.
	 * @return valor de tipo String, el texto truncado.
	 */
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