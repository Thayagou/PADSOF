package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import controladores.ControlBarraTareas;
import vistas.common.app.BarraTareas;
import vistas.common.app.TiendaFrame;
import vistas.herramientas.*;

/**
 * Define la barra de tareas de los usuarios no registrados.
 */
public class BarraTareasNoRegistrado extends BarraTareas {
	
	/** Constante ATRAS. */
	public static final String ATRAS = "Volver atras";
	
	/** Constante BUSCAR. */
	public static final String BUSCAR = "Buscar";
	
	/** Constante INICIAR. */
	public static final String INICIAR = "Iniciar";
	
	/** Constante HOME. */
	public static final String HOME = "Home";
	
	/** Constante INFO. */
	public static final String INFO = "Info";
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante SPACE_BETWEEN. */
	private static final double SPACE_BETWEEN = 0.01;
	
	/** Constante BTN_ACCOUNT_W. */
	private static final double BTN_ACCOUNT_W = 0.15;
	
	/** Constante BTN_SEARCH_W. */
	private static final double BTN_SEARCH_W = 0.35;
	
	/** Boton para volver atras. */
	private JButton volverAtras;

	/** Campo btnBuscar. */
	private JButton btnBuscar;
	
	/** Campo btnIniciarSesion. */
	private JButton btnIniciarSesion;
	
	/** Campo btnHome. */
	private JButton btnHome;
	
	/** Boton para ver la informacion. */
	private JButton info;

	/**
	 * Instancia un nuevo Objeto BarraTareasNoRegistrado.
	 */
	public BarraTareasNoRegistrado() {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int searchW = t.getPixelsWidth(BTN_SEARCH_W);
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int homeW = btnH;

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));

		volverAtras = ButtonFactory.newIconButton("flechaAtras.png", btnH, homeW);
		ButtonFactory.paintButton(volverAtras, ColorPalette.BLUE, ColorPalette.WHITE);
		volverAtras.setActionCommand(ATRAS);
		ButtonFactory.addMouseMecanics(volverAtras, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(volverAtras, "Volver atrás", 0);
		
		btnHome = ButtonFactory.newIconButton("homeButton.png", btnH, homeW);
		ButtonFactory.paintButton(btnHome, ColorPalette.BLUE, ColorPalette.WHITE);
		btnHome.setActionCommand(HOME);
		ButtonFactory.addMouseMecanics(btnHome, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(btnHome, "Ventana de Inicio", 0);

		btnBuscar = ButtonFactory.newRoundedButton("Buscar", btnH, searchW, 1);
		ButtonFactory.paintButton(btnBuscar, ColorPalette.WHITE, ColorPalette.BLACK);
		btnBuscar.setActionCommand(BUSCAR);
		ButtonFactory.addMouseMecanics(btnBuscar, ColorPalette.WHITE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(btnBuscar, "Buscar Productos", 0);
		
		info = ButtonFactory.newIconButton("interrogacion.png", btnH, btnH);
		ButtonFactory.paintButton(info, ColorPalette.BLUE, ColorPalette.WHITE);
		info.setActionCommand(INFO);
		ButtonFactory.addMouseMecanics(info, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(info, "Información", 0);

		btnIniciarSesion = ButtonFactory.newRoundedButton("Iniciar sesión / Registrarse", btnH, accountW, 0.25);
		ButtonFactory.paintButton(btnIniciarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		btnIniciarSesion.setActionCommand(INICIAR);
		ButtonFactory.addMouseMecanics(btnIniciarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		ButtonFactory.addHoverInfo(btnIniciarSesion, "Iniciar/Registrar", 0);
		
		
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(Box.createHorizontalStrut(spaceBetween));
		volverAtras.setMaximumSize(new Dimension(homeW, btnH));
		volverAtras.setPreferredSize(new Dimension(homeW, btnH));
		volverAtras.setMinimumSize(new Dimension(homeW, btnH));
		add(volverAtras);
		add(Box.createHorizontalStrut(spaceBetween));
		
		btnHome.setMaximumSize(new Dimension(homeW, btnH));
		btnHome.setPreferredSize(new Dimension(homeW, btnH));
		btnHome.setMinimumSize(new Dimension(homeW, btnH));
		add(btnHome);
		add(Box.createHorizontalStrut(spaceBetween));

		btnBuscar.setMaximumSize(new Dimension(searchW, btnH));
		btnBuscar.setPreferredSize(new Dimension(searchW, btnH));
		btnBuscar.setMinimumSize(new Dimension(100, btnH));
				
		btnIniciarSesion.setMaximumSize(new Dimension(accountW, btnH));
		btnIniciarSesion.setPreferredSize(new Dimension(accountW, btnH));

		add(btnBuscar);
		add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(spaceBetween));
		
		fijarTamano(info, btnH, btnH);
		add(info);
		add(Box.createHorizontalStrut(spaceBetween));

		add(btnIniciarSesion);
		add(Box.createHorizontalStrut(spaceBetween));
	}
	
	/**
	 * fijarTamano.
	 *
	 * @param btn parámetro btn
	 * @param w parámetro w
	 * @param h parámetro h
	 */
	private static void fijarTamano(JButton btn, int w, int h) {
		Dimension d = new Dimension(w, h);
		btn.setMaximumSize(d);
		btn.setPreferredSize(d);
		btn.setMinimumSize(d);
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	@Override
	public void setControlador(ControlBarraTareas c) {
		btnBuscar.addActionListener(c);
		btnIniciarSesion.addActionListener(c);
		btnHome.addActionListener(c);
		volverAtras.addActionListener(c);
		info.addActionListener(c);
	}
}
