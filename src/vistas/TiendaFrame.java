package vistas;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;


public class TiendaFrame extends JFrame {
	private int height;
	private int width;
	
	private static double TITLE_SIZE = 0.1;
	private static double SUBTITLE_SIZE = 0.8;
	private static double TITLE3_SIZE = 0.05;
	private static double TEXT_SIZE = 0.02;
	
	private static double TOOLBAR_HEIGHT = 0.1;
	public TiendaFrame() {
		setTitle("Android's Dungeon");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
		this.width = screen.width;
		this.height = screen.height;
	}
	
	public int getTitleFontSize() { return (int) (height * TITLE_SIZE);}
	public int getSubtitleFontSize() { return (int) (height * SUBTITLE_SIZE);}
	public int getTitle3FontSize() { return (int) (height * TITLE3_SIZE);}
	public int getTextFontSize() { return (int) (height * TEXT_SIZE);}
	
	public int toolBarDistFromTop() { return (int) (height * TOOLBAR_HEIGHT); }
}
