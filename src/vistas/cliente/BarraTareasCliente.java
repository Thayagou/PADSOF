package vistas.cliente;

import java.awt.*;
import javax.swing.*;
import vistas.common.BarraTareas;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelSizes;
import controladores.ControlBarraTareas;

public class BarraTareasCliente extends BarraTareas{
	private static final long serialVersionUID = 1L;
	
	private static double BTN_SEARCH_W = 0.2;
	private static double BTN_ACCOUNT_W = 0.1;
	private static double SPACE_BETWEEN = 0.01;
	
	private JButton home;
	private JButton notificaciones;
	private JButton buscar;
	private JButton carrito;
	private JButton cuenta;
	/*private ImageIcon getImageIcon(String route, int height, int width) {
		ImageIcon iconoOriginal = new ImageIcon(route);
		Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH);
		return new ImageIcon(imagenEscalada);
	}*/
	
	public BarraTareasCliente(String cliente) {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int searchW = t.getPixelsWidth(BTN_SEARCH_W);
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int homeW = btnH;
		int notisW = btnH;
		int carW = btnH;

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));

		ButtonFactory f = new ButtonFactory();
		
		/**=========================================================================
		 * ################     CREACION DE LOS BOTONES         ####################
		 * =======================================================================*/
		home = f.newIconButton("homeButton.png", btnH, homeW);
		f.paintButton(home, ColorPalette.BLUE, ColorPalette.WHITE);
		home.setActionCommand("Home");
		f.addMouseMecanics(home, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		
		notificaciones = f.newIconButton("notificaciones.png", btnH, notisW);
		f.paintButton(notificaciones, ColorPalette.BLUE, ColorPalette.WHITE);
		notificaciones.setActionCommand("Notificaciones");
		f.addMouseMecanics(notificaciones, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);

		buscar = f.newRoundedButton("Buscar", btnH, searchW, 1);
		f.paintButton(buscar, ColorPalette.WHITE, ColorPalette.BLACK);
		buscar.setActionCommand("Buscar productos");
		f.addMouseMecanics(buscar, ColorPalette.WHITE, ColorPalette.HOVER_BLUE);
		
		carrito = f.newIconButton("carrito.png", btnH, carW);
		f.paintButton(carrito, ColorPalette.BLUE, ColorPalette.WHITE);
		carrito.setActionCommand("Carrito");
		f.addMouseMecanics(carrito, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);

		cuenta = f.newRoundedButton(cliente, btnH, accountW, 0.25);
		f.paintButton(cuenta, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		cuenta.setActionCommand("Cuenta");
		f.addMouseMecanics(cuenta, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		/**=========================================================================
		 * ################     LAYOUT DE LA BARRA DE TAREAS   ####################
		 * =======================================================================*/
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(Box.createHorizontalStrut(spaceBetween));
		home.setMaximumSize(new Dimension(homeW, btnH));
		home.setPreferredSize(new Dimension(homeW, btnH));
		home.setMinimumSize(new Dimension(homeW, btnH));
		add(home);
		add(Box.createHorizontalStrut(spaceBetween));
		
		notificaciones.setMaximumSize(new Dimension(notisW, btnH));
		notificaciones.setPreferredSize(new Dimension(notisW, btnH));
		notificaciones.setMinimumSize(new Dimension(notisW, btnH));
		add(notificaciones);
		add(Box.createHorizontalStrut(spaceBetween));

		buscar.setMaximumSize(new Dimension(searchW, btnH));
		buscar.setPreferredSize(new Dimension(searchW, btnH));
		buscar.setMinimumSize(new Dimension(100, btnH));
		add(buscar);
		add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(spaceBetween));
		
		carrito.setMaximumSize(new Dimension(carW, btnH));
		carrito.setPreferredSize(new Dimension(carW, btnH));
		carrito.setMinimumSize(new Dimension(carW, btnH));
		add(carrito);
		add(Box.createHorizontalStrut(spaceBetween));
				
		cuenta.setMaximumSize(new Dimension(accountW, btnH));
		cuenta.setPreferredSize(new Dimension(accountW, btnH));
		cuenta.setMinimumSize(new Dimension(accountW, btnH));
		add(cuenta);
		add(Box.createHorizontalStrut(spaceBetween));
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		home.addActionListener(c);
		notificaciones.addActionListener(c);
		buscar.addActionListener(c);
		carrito.addActionListener(c);
		cuenta.addActionListener(c);		
	}
	
}
