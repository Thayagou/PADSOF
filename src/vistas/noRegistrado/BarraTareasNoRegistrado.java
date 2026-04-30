package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import controladores.ControlBarraTareas;
import vistas.common.*;
import vistas.herramientas.*;


// TODO: Auto-generated Javadoc
/**
 * Define la barra de tareas de los usuarios no registrados
 */
public class BarraTareasNoRegistrado extends BarraTareas {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante SPACE_BETWEEN. */
	private static final double SPACE_BETWEEN = 0.01;
	
	/** Constante BTN_ACCOUNT_W. */
	private static final double BTN_ACCOUNT_W = 0.15;
	
	/** Constante BTN_SEARCH_W. */
	private static final double BTN_SEARCH_W = 0.35;

	/** Campo btnBuscar. */
	private JButton btnBuscar;
	
	/** Campo btnIniciarSesion. */
	private JButton btnIniciarSesion;
	
	/** Campo btnHome. */
	private JButton btnHome;

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

		ButtonFactory f = new ButtonFactory();

		btnHome = f.newIconButton("homeButton.png", btnH, homeW);
		btnHome.setBackground(ColorPalette.BLUE.getColor());
		btnHome.setForeground(ColorPalette.WHITE.getColor());
		btnHome.setActionCommand("Home");
		f.addMouseMecanics(btnHome, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);

		btnBuscar = f.newRoundedButton("Buscar", btnH, searchW, 1);
		btnBuscar.setBackground(ColorPalette.WHITE.getColor());
		btnBuscar.setForeground(ColorPalette.BLACK.getColor());
		btnBuscar.setActionCommand("Buscar productos");
		f.addMouseMecanics(btnBuscar, ColorPalette.WHITE, ColorPalette.HOVER_BLUE);

		btnIniciarSesion = f.newRoundedButton("Iniciar sesión / Registrarse", btnH, accountW, 0.25);
		btnIniciarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnIniciarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnIniciarSesion.setActionCommand("Iniciar sesión");
		f.addMouseMecanics(btnIniciarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

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

		add(btnIniciarSesion);
		add(Box.createHorizontalStrut(spaceBetween));
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
	}
}
