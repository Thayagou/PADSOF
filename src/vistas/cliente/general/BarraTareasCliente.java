package vistas.cliente.general;

import java.awt.*;
import javax.swing.*;

import vistas.common.app.BarraTareas;
import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelSizes;
import controladores.ControlBarraTareas;

public class BarraTareasCliente extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static double BTN_SEARCH_W = 0.2;
	private static double BTN_ACCOUNT_W = 0.1;
	private static double SPACE_BETWEEN = 0.01;

	private JButton volver;
	private JButton home;
	private JButton notificaciones;
	private JButton buscar;
	private JButton carrito;
	private JButton cuenta;
	private JButton info;

	public BarraTareasCliente(String cliente) {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int searchW = t.getPixelsWidth(BTN_SEARCH_W);
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int squareW = btnH; // tamaño cuadrado para iconos y volver

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));

		volver = ButtonFactory.newIconButton("flechaAtras.png", btnH, squareW);
		ButtonFactory.paintButton(volver, ColorPalette.BLUE, ColorPalette.WHITE);
		volver.setActionCommand("Volver");
		volver.setToolTipText("Volver atrás");
		ButtonFactory.addMouseMecanics(volver, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(volver, "Volver atrás", 0);

		home = ButtonFactory.newIconButton("homeButton.png", btnH, squareW);
		ButtonFactory.paintButton(home, ColorPalette.BLUE, ColorPalette.WHITE);
		home.setActionCommand("Home");
		ButtonFactory.addMouseMecanics(home, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(home, "Ventana de Inicio", 0);

		notificaciones = ButtonFactory.newIconButton("notificaciones.png", btnH, squareW);
		ButtonFactory.paintButton(notificaciones, ColorPalette.BLUE, ColorPalette.WHITE);
		notificaciones.setActionCommand("Notificaciones");
		ButtonFactory.addMouseMecanics(notificaciones, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(notificaciones, "Notificaciones", 0);

		buscar = ButtonFactory.newRoundedButton("Buscar", btnH, searchW, 1);
		ButtonFactory.paintButton(buscar, ColorPalette.WHITE, ColorPalette.BLACK);
		buscar.setActionCommand("Buscar productos");
		ButtonFactory.addMouseMecanics(buscar, ColorPalette.WHITE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(buscar, "Buscar Productos", 0);
		
		info = ButtonFactory.newIconButton("interrogacion.png", btnH, squareW);
		ButtonFactory.paintButton(info, ColorPalette.BLUE, ColorPalette.WHITE);
		info.setActionCommand("Info");
		ButtonFactory.addMouseMecanics(info, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(info, "Información", 0);

		carrito = ButtonFactory.newIconButton("carrito.png", btnH, squareW);
		ButtonFactory.paintButton(carrito, ColorPalette.BLUE, ColorPalette.WHITE);
		carrito.setActionCommand("Carrito");
		ButtonFactory.addMouseMecanics(carrito, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(carrito, "Ver Carrito", 0);

		cuenta = ButtonFactory.newRoundedButton(cliente, btnH, accountW, 0.25);
		String usrName = Fonts.truncar(cliente, accountW, cuenta.getFont(), cuenta);
		cuenta.setText(usrName);
		
		ButtonFactory.paintButton(cuenta, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		cuenta.setActionCommand("Cuenta");
		ButtonFactory.addMouseMecanics(cuenta, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		ButtonFactory.addHoverInfo(cuenta, "Gestionar cuenta", 0);

		
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(Box.createHorizontalStrut(spaceBetween));
		fijarTamano(volver, squareW, btnH);
		add(volver);
		add(Box.createHorizontalStrut(spaceBetween));

		fijarTamano(home, squareW, btnH);
		add(home);
		add(Box.createHorizontalStrut(spaceBetween));

		fijarTamano(notificaciones, squareW, btnH);
		add(notificaciones);
		add(Box.createHorizontalStrut(spaceBetween));

		// Buscar se expande
		buscar.setMaximumSize(new Dimension(searchW, btnH));
		buscar.setPreferredSize(new Dimension(searchW, btnH));
		buscar.setMinimumSize(new Dimension(100, btnH));
		add(buscar);
		add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(spaceBetween));
		
		fijarTamano(info, squareW, btnH);
		add(info);
		add(Box.createHorizontalStrut(spaceBetween));

		fijarTamano(carrito, squareW, btnH);
		add(carrito);
		add(Box.createHorizontalStrut(spaceBetween));

		fijarTamano(cuenta, accountW, btnH);
		add(cuenta);
		add(Box.createHorizontalStrut(spaceBetween));
	}

	/** Fija las tres dimensiones a la vez para evitar que BoxLayout las ignore. */
	private static void fijarTamano(JButton btn, int w, int h) {
		Dimension d = new Dimension(w, h);
		btn.setMaximumSize(d);
		btn.setPreferredSize(d);
		btn.setMinimumSize(d);
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		volver.addActionListener(c);
		home.addActionListener(c);
		notificaciones.addActionListener(c);
		buscar.addActionListener(c);
		carrito.addActionListener(c);
		cuenta.addActionListener(c);
		info.addActionListener(c);
	}
}