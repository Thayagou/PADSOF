package vistas.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import modelo.aplicacion.GuiExe;
import vistas.herramientas.PanelSizes;


public class TiendaFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static TiendaFrame instance;
	private Component vistaActual;
	private BarraLateral barraLateral;
	private BarraTareas barraTareas;
	private FondoGradiente fondo;
	private int height;
	private int width;
	
	private TiendaFrame() {
		setTitle("Android's Dungeon");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		
		Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
		this.width = screen.width;
		this.height = screen.height;
		
		setSize(width, height);
		
		fondo = new FondoGradiente();
		fondo.setVisible(true);
		add(fondo);
	}
	
	public static TiendaFrame getInstance() {
		if(instance == null) {
			instance = new TiendaFrame();
			instance.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

			instance.addWindowListener(new WindowAdapter() {
			    @Override
			    public void windowClosing(WindowEvent e) {
			        GuiExe.guardarTienda();
			        instance.dispose();
			        System.exit(0);
			    }
			});
		}
		instance.setVisible(true);
		return instance; 
	}
	
	public void setBarraTareas(BarraTareas barraTareas) {
		if (this.barraTareas != null) fondo.remove(this.barraTareas);
		fondo.add(barraTareas, BorderLayout.NORTH);
		this.barraTareas = barraTareas;
		revalidate();
	    repaint();
	}
	
	public void setBarraLateral(BarraLateral barraLateral) {
		if (this.barraLateral != null) fondo.remove(this.barraLateral);
		fondo.add(barraLateral, BorderLayout.WEST);
		this.barraLateral = barraLateral;
		revalidate();
	    repaint();
	}
	
	public void removeBarraLateral() {
		if (this.barraLateral != null) fondo.remove(this.barraLateral);
		revalidate();
	    repaint();
	}
	
	public void setFondo(FondoGradiente fondo) {
		if (this.fondo != null) remove(this.fondo);
		fondo.add(barraLateral, BorderLayout.WEST);
		fondo.add(barraTareas, BorderLayout.NORTH);
		fondo.add(vistaActual, BorderLayout.CENTER);
		add(fondo);
		revalidate();
	    repaint();
	}
	
	public void setVistaActual(Component vista) {
		if(vistaActual != null) fondo.remove(vistaActual);
		vistaActual = vista;
		fondo.add(vista, BorderLayout.CENTER);
		revalidate();
	    repaint();
	}
	
	public Component getVistaActual() {
		return vistaActual;
	}
	
	public int getPixelsWidth(double percentage) { return (int)(width * percentage); }
	public int getPixelsHeight(double percentage) { return (int)(height * percentage); }
	
	public int toolBarDistFromTop() { return (int) (height * PanelSizes.TOOLBAR_HEIGHT); }
	public int optionBarDistFromLeft() { return (int) (width * PanelSizes.OPTION_BAR_WIDTH); }
	public int btnHeight() { return (int) (height * PanelSizes.BTN_HEIGHT); }
}
