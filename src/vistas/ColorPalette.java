package vistas;

import java.awt.*;

public enum ColorPalette {
	
	WHITE(255, 255, 255),
	PURPLE(140, 82, 255),
	LIGT_PURPLE(167, 0, 255),
	BG_PURPLE(169, 31, 187),
	BLUE(94, 23, 235),
	DARK_BLUE(83, 31, 187),
	BG_BLUE(85, 12, 191),
	CARD_DARK(208, 207, 187),
	CARD_LIGHT(254, 253, 242),
	YELLOW(255, 222, 89),
	DARK_GREY(88,88,88),
	GREY(166, 166, 166),
	LIGHT_GREY(205,205,205),
	RED(255, 87, 87),
	GREEN(0, 191, 99);

	private final Color color;

	ColorPalette(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}

	public Color getColor() {
		return color;
	}
}