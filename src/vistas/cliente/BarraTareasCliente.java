package vistas.cliente;

import java.awt.*;
import javax.swing.*;
import vistas.*;
import vistas.common.BarraTareas;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelSizes;
import controladores.ControlBarraTareas;

public class BarraTareasCliente extends BarraTareas{
	private static final long serialVersionUID = 1L;
	
	private static double TOOL_BAR_ACCOUNT_WIDTH = 0.1;
	private static double SPACE_BETWEEN = 0.01;
	
	private JButton notificaciones;
	private JButton buscar;
	private JButton carrito;
	private JButton cuenta;
	/*private ImageIcon getImageIcon(String route, int height, int width) {
		ImageIcon iconoOriginal = new ImageIcon(route);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH);
		return new ImageIcon(imagenEscalada);
	}*/
	
	public BarraTareasCliente() {
		TiendaFrame t = TiendaFrame.getInstance();
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int alturaBotones = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT) - 2*spaceBetween;
		int notisW = alturaBotones;
		int carrW = alturaBotones;
		int cuentaW = t.getPixelsHeight(TOOL_BAR_ACCOUNT_WIDTH);
		int buscarW = t.getWidth() - 5*spaceBetween - notisW - carrW - cuentaW;
		
		setBackground(ColorPalette.BLUE.getColor());
        setPreferredSize(new Dimension(0, t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT)));
		
		/* Imagen del boton de notificaciones */
		ButtonFactory factory = new ButtonFactory();
		
		notificaciones = factory.newIconButton("notificaciones.png", alturaBotones, notisW);
		//new JButton(getImageIcon("resources/gui/notificaciones.png", alturaBotones, notisW));
		buscar = factory.newButton("Buscar...", alturaBotones, buscarW);
		//new JButton("Buscar...");
		carrito = factory.newIconButton("carrito.png", alturaBotones, carrW);
		//new JButton(getImageIcon("resources/gui/carrito.png", alturaBotones, carrW));
		cuenta = factory.newButton("Cuenta", alturaBotones, cuentaW);
		//new JButton("Cuenta");
		SpringLayout layout = new SpringLayout();
		
		
		
        /* Ajustar tamaño y color de los botones */
		
		notificaciones.setPreferredSize(new Dimension(notisW, alturaBotones));
		notificaciones.setBackground(ColorPalette.BLUE.getColor());
		
		//buscar.setPreferredSize(new Dimension(buscarW, alturaBotones));
		buscar.setBackground(ColorPalette.WHITE.getColor());
		
		//carrito.setPreferredSize(new Dimension(carrW, alturaBotones));
		carrito.setBackground(ColorPalette.BLUE.getColor());
		
		//cuenta.setPreferredSize(new Dimension(cuentaW, alturaBotones));
		cuenta.setBackground(ColorPalette.BLUE.getColor());
		
		this.setLayout(layout);
		
		this.add(notificaciones);
		this.add(buscar);
		this.add(carrito);
		this.add(cuenta);
		
		/* Ajustar la posicion de los botones */
		layout.putConstraint(SpringLayout.WEST, notificaciones, spaceBetween, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, notificaciones, spaceBetween, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, buscar, spaceBetween, SpringLayout.EAST, notificaciones);
		layout.putConstraint(SpringLayout.NORTH, buscar, 0,	SpringLayout.NORTH, notificaciones);
		
		layout.putConstraint(SpringLayout.WEST, carrito, spaceBetween, SpringLayout.EAST, buscar);
		layout.putConstraint(SpringLayout.NORTH, carrito, 0,	SpringLayout.NORTH, buscar);
		
		layout.putConstraint(SpringLayout.WEST, cuenta, spaceBetween, SpringLayout.EAST, carrito);
		layout.putConstraint(SpringLayout.NORTH, cuenta, 0,	SpringLayout.NORTH, carrito);
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		notificaciones.addActionListener(c);
		buscar.addActionListener(c);
		carrito.addActionListener(c);
		cuenta.addActionListener(c);		
	}
	
}
