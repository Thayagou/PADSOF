package vistas;

import java.awt.*;
import javax.swing.*;

public class BarraTareas extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private static double TOOL_BAR_ACCOUNT_WIDTH = 0.1;
	private static int SPACE_BETWEEN = 10;
	
	private ImageIcon getImageIcon(String route, int height, int width) {
		ImageIcon iconoOriginal = new ImageIcon(route);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH);
		return new ImageIcon(imagenEscalada);
	}
	
	public BarraTareas() {
		TiendaFrame t = TiendaFrame.getInstance();
		int alturaBotones = t.getPixelsHeight(TiendaFrame.TOOL_BAR_HEIGHT) - 2*SPACE_BETWEEN;
		int notisW = alturaBotones;
		int carrW = alturaBotones;
		int cuentaW = t.getPixelsHeight(TOOL_BAR_ACCOUNT_WIDTH);
		int buscarW = t.getWidth() - 5*SPACE_BETWEEN - notisW - carrW - cuentaW;
		
		setBackground(ColorPalette.BLUE.getColor());
        setPreferredSize(new Dimension(0, t.getPixelsHeight(TiendaFrame.TOOL_BAR_HEIGHT)));
		
		/* Imagen del boton de notificaciones */
		
		
		JButton notificaciones = new JButton(getImageIcon("resources/gui/notificaciones.png", alturaBotones, notisW));
		JButton buscar = new JButton("Buscar...");
		JButton carrito = new JButton(getImageIcon("resources/gui/carrito.png", alturaBotones, carrW));
		JButton cuenta = new JButton("Cuenta");
		SpringLayout layout = new SpringLayout();
		
		
		
        /* Ajustar tamaño y color de los botones */
		
		notificaciones.setPreferredSize(new Dimension(notisW, alturaBotones));
		notificaciones.setBackground(ColorPalette.BLUE.getColor());
		
		buscar.setPreferredSize(new Dimension(buscarW, alturaBotones));
		buscar.setBackground(ColorPalette.WHITE.getColor());
		
		carrito.setPreferredSize(new Dimension(carrW, alturaBotones));
		carrito.setBackground(ColorPalette.BLUE.getColor());
		
		cuenta.setPreferredSize(new Dimension(cuentaW, alturaBotones));
		cuenta.setBackground(ColorPalette.BLUE.getColor());
		
		this.setLayout(layout);
		
		this.add(notificaciones);
		this.add(buscar);
		this.add(carrito);
		this.add(cuenta);
		
		/* Ajustar la posicion de los botones */
		layout.putConstraint(SpringLayout.WEST, notificaciones, SPACE_BETWEEN, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, notificaciones, SPACE_BETWEEN, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, buscar, SPACE_BETWEEN, SpringLayout.EAST, notificaciones);
		layout.putConstraint(SpringLayout.NORTH, buscar, 0,	SpringLayout.NORTH, notificaciones);
		
		layout.putConstraint(SpringLayout.WEST, carrito, SPACE_BETWEEN, SpringLayout.EAST, buscar);
		layout.putConstraint(SpringLayout.NORTH, carrito, 0,	SpringLayout.NORTH, buscar);
		
		layout.putConstraint(SpringLayout.WEST, cuenta, SPACE_BETWEEN, SpringLayout.EAST, carrito);
		layout.putConstraint(SpringLayout.NORTH, cuenta, 0,	SpringLayout.NORTH, carrito);
	}
	
}
