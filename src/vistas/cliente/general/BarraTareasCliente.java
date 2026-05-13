package vistas.cliente.general;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import controladores.TiendaFrame;
import vistas.common.app.BarraTareas;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelSizes;

/**
 * Barra de tareas superior para clientes registrados, con botones de navegación, búsqueda y acceso a cuenta.
 */
public class BarraTareasCliente extends BarraTareas {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo BTN_SEARCH_W. Anchura del botón de búsqueda como porcentaje de la pantalla. */
	private static double BTN_SEARCH_W = 0.2;
	
	/** Campo BTN_ACCOUNT_W. Anchura del botón de cuenta como porcentaje de la pantalla. */
	private static double BTN_ACCOUNT_W = 0.1;
	
	/** Campo SPACE_BETWEEN. Espacio entre botones como porcentaje de la pantalla. */
	private static double SPACE_BETWEEN = 0.01;
	
	/** Constante INFO_ACTION. Comando de acción para el botón de información. */
	public static final String INFO_ACTION = "Info";
	
	/** Constante VOLVER_ACTION. Comando de acción para el botón de volver atrás. */
	public static final String VOLVER_ACTION = "Volver";
	
	/** Constante HOME_ACTION. Comando de acción para el botón de inicio. */
	public static final String HOME_ACTION = "Home";
	
	/** Constante NOTIFICACIONES_ACTION. Comando de acción para el botón de notificaciones. */
	public static final String NOTIFICACIONES_ACTION = "Notificaciones";
	
	/** Constante CUENTA_ACTION. Comando de acción para el botón de gestión de cuenta. */
	public static final String CUENTA_ACTION = "Cuenta";
	
	/** Constante BUSCAR_PRODUCTOS_ACTION. Comando de acción para el botón de búsqueda de productos. */
	public static final String BUSCAR_PRODUCTOS_ACTION = "Buscar productos";
	
	/** Constante CARRITO_ACTION. Comando de acción para el botón del carrito de compras. */
	public static final String CARRITO_ACTION = "Carrito";

	/** Campo volver. Botón para navegar a la pantalla anterior. */
	private JButton volver;
	
	/** Campo home. Botón para ir a la pantalla de inicio. */
	private JButton home;
	
	/** Campo notificaciones. Botón para ver las notificaciones del usuario. */
	private JButton notificaciones;
	
	/** Campo buscar. Botón para acceder a la búsqueda de productos. */
	private JButton buscar;
	
	/** Campo carrito. Botón para acceder al carrito de compras. */
	private JButton carrito;
	
	/** Campo cuenta. Botón para acceder a la gestión de la cuenta del usuario. */
	private JButton cuenta;
	
	/** Campo info. Botón para acceder a la información de la aplicación. */
	private JButton info;

	/**
	 * Instancia un nuevo Objeto BarraTareasCliente.
	 * Construye la barra con los botones de navegación, búsqueda, carrito, notificaciones y acceso a cuenta.
	 *
	 * @param cliente Nombre del cliente para mostrar en el botón de cuenta.
	 */
	public BarraTareasCliente(String cliente) {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int searchW = t.getPixelsWidth(BTN_SEARCH_W);
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int squareW = btnH;

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));

		volver = ButtonFactory.newIconButton("flechaAtras.png", btnH, squareW);
		ButtonFactory.paintButton(volver, ColorPalette.BLUE, ColorPalette.WHITE);
		volver.setActionCommand(VOLVER_ACTION);
		volver.setToolTipText("Volver atrás");
		ButtonFactory.addMouseMecanics(volver, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(volver, "Volver atrás", 0);

		home = ButtonFactory.newIconButton("homeButton.png", btnH, squareW);
		ButtonFactory.paintButton(home, ColorPalette.BLUE, ColorPalette.WHITE);
		home.setActionCommand(HOME_ACTION);
		ButtonFactory.addMouseMecanics(home, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(home, "Ventana de Inicio", 0);

		notificaciones = ButtonFactory.newIconButton("notificaciones.png", btnH, squareW);
		ButtonFactory.paintButton(notificaciones, ColorPalette.BLUE, ColorPalette.WHITE);
		notificaciones.setActionCommand(NOTIFICACIONES_ACTION);
		ButtonFactory.addMouseMecanics(notificaciones, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(notificaciones, "Notificaciones", 0);

		buscar = ButtonFactory.newRoundedButton("Buscar", btnH, searchW, 1);
		ButtonFactory.paintButton(buscar, ColorPalette.WHITE, ColorPalette.BLACK);
		buscar.setActionCommand(BUSCAR_PRODUCTOS_ACTION);
		ButtonFactory.addMouseMecanics(buscar, ColorPalette.WHITE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(buscar, "Buscar Productos", 0);
		
		info = ButtonFactory.newIconButton("interrogacion.png", btnH, squareW);
		ButtonFactory.paintButton(info, ColorPalette.BLUE, ColorPalette.WHITE);
		info.setActionCommand(INFO_ACTION);
		ButtonFactory.addMouseMecanics(info, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(info, "Información", 0);

		carrito = ButtonFactory.newIconButton("carrito.png", btnH, squareW);
		ButtonFactory.paintButton(carrito, ColorPalette.BLUE, ColorPalette.WHITE);
		carrito.setActionCommand(CARRITO_ACTION);
		ButtonFactory.addMouseMecanics(carrito, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(carrito, "Ver Carrito", 0);

		cuenta = ButtonFactory.newRoundedButton(cliente, btnH, accountW, 0.25);
		String usrName = Fonts.truncar(cliente, accountW, cuenta.getFont(), cuenta);
		cuenta.setText(usrName);
		
		ButtonFactory.paintButton(cuenta, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		cuenta.setActionCommand(CUENTA_ACTION);
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

	/**
	 * Fija las tres dimensiones a la vez para evitar que BoxLayout las ignore.
	 * Define el tamaño mínimo, preferido y máximo del botón.
	 *
	 * @param btn Botón al que se le fijan las dimensiones.
	 * @param w Anchura en píxeles.
	 * @param h Altura en píxeles.
	 */
	private static void fijarTamano(JButton btn, int w, int h) {
		Dimension d = new Dimension(w, h);
		btn.setMaximumSize(d);
		btn.setPreferredSize(d);
		btn.setMinimumSize(d);
	}

	/**
	 * Establece Controlador.
	 * Asigna el mismo controlador a todos los botones de la barra de tareas.
	 *
	 * @param c controlador que manejará los eventos de los botones.
	 */
	@Override
	public void setControlador(ActionListener c) {
		volver.addActionListener(c);
		home.addActionListener(c);
		notificaciones.addActionListener(c);
		buscar.addActionListener(c);
		carrito.addActionListener(c);
		cuenta.addActionListener(c);
		info.addActionListener(c);
	}
}