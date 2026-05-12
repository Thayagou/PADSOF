package vistas.herramientas;

import java.awt.*;

/**
 * Enumerado que define la paleta de colores utilizada en la aplicación.
 */
public enum ColorPalette {
	
	/** Color blanco. */
	WHITE(255, 255, 255),

	/** Color negro. */
	BLACK(0,0,0),

	/** Color morado principal. */
	PURPLE(140, 82, 255),

	/** Color morado claro. */
	LIGHT_PURPLE(167, 0, 255),

	/** Color de fondo morado. */
	BG_PURPLE(169, 31, 187),

	/** Color azul principal. */
	BLUE(94, 23, 235),

	/** Color azul oscuro. */
	DARK_BLUE(83, 31, 187),

	/** Color de fondo azul. */
	BG_BLUE(85, 12, 191),

	/** Color azul para efecto hover. */
	HOVER_BLUE(111, 176, 245),

	/** Color oscuro para tarjetas. */
	CARD_DARK(208, 207, 187),

	/** Color claro para tarjetas. */
	CARD_LIGHT(254, 253, 242),

	/** Color hover para tarjetas oscuras. */
	CARD_DARK_HOVER(238, 237, 217),

	/** Color hover para tarjetas claras. */
	CARD_LIGHT_HOVER(255, 255, 255),

	/** Color amarillo. */
	YELLOW(255, 222, 89),

	/** Gris oscuro. */
	DARK_GREY(88,88,88),

	/** Gris medio. */
	GREY(166, 166, 166),

	/** Gris claro. */
	LIGHT_GREY(205,205,205),

	/** Color rojo. */
	RED(230, 0, 0),

	/** Rojo claro. */
	LIGHT_RED(255, 160, 160),

	/** Color verde. */
	GREEN(0, 191, 99);
	
	/**
	 * Valor utilizado para aumentar el brillo del color
	 * en los efectos hover.
	 */
	private static final int CONTRAST = 20;

	/**
	 * Color asociado a cada constante.
	 */
	private final Color color;

	/**
	 * Constructor del enumerado.
	 * 
	 * @param r componente roja del color
	 * @param g componente verde del color
	 * @param b componente azul del color
	 */
	private ColorPalette(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}

	/**
	 * Devuelve el objeto asociado a la constante.
	 * 
	 * @return color correspondiente
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Genera un color más claro a partir del color recibido,
	 * utilizado normalmente para efectos hover.
	 * @param color color base
	 * @return nuevo color aclarado
	 */
	public static Color getHoverColor(Color color) {

	    int r = Math.min(color.getRed() + CONTRAST, 255);
	    int g = Math.min(color.getGreen() + CONTRAST, 255);
	    int b = Math.min(color.getBlue() + CONTRAST, 255);

	    return new Color(r, g, b, color.getAlpha());
	}
}