package vistas.herramientas;

import java.awt.Font;

import vistas.TiendaFrame;

public enum Fonts {
	TITLE("Arial", Font.BOLD, 0.05),
	SUBTITLE("Arial", Font.BOLD, 0.04),
	TITLE3("Arial", Font.BOLD, 0.03),
	TEXT("Arial", Font.BOLD, 0.02);
	
	private Font font;
	
	private Fonts(String name, int style, double relativeSize) {
		TiendaFrame frame= TiendaFrame.getInstance();
		this.font = new Font(name, style, (int) (frame.getHeight() * relativeSize));
	}

	public Font getFont() { return this.font;}
}


