package vistas;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;


public class TiendaFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static TiendaFrame instance;
	private int height;
	private int width;
	
	private static double TITLE_SIZE = 0.1;
	private static double SUBTITLE_SIZE = 0.06;
	private static double TITLE3_SIZE = 0.04;
	private static double TEXT_SIZE = 0.02;
	public static double TOOL_BAR_HEIGHT = 0.1;
	
	private static double TOOLBAR_HEIGHT = 0.1;
	public TiendaFrame() {
		setTitle("Android's Dungeon");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
		this.width = screen.width;
		this.height = screen.height;
		
		setSize(width, height);
	}
	
	public static TiendaFrame getInstance() {
		if(instance == null) instance = new TiendaFrame();
		return instance; 
	}
	
	public Font getTitleFont() { return new Font("Arial", Font.BOLD, (int) (height * TITLE_SIZE));}
	public Font getSubtitleFont() { return new Font("Arial", Font.BOLD, (int) (height * SUBTITLE_SIZE));}
	public Font getTitle3Font() { return new Font("Arial", Font.BOLD, (int) (height * TITLE3_SIZE));}
	public Font getTextFont() { return new Font("Arial", Font.BOLD, (int) (height * TEXT_SIZE));}
	
	public int getPixelsWidth(double percentage) { return (int)(width * percentage); }
	public int getPixelsHeight(double percentage) { return (int)(height * percentage); }
	
	public int toolBarDistFromTop() { return (int) (height * TOOLBAR_HEIGHT); }
}
