package vistas.gestor;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import vistas.common.app.BarraTareas;
import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelSizes;

// TODO: Auto-generated Javadoc
/**
 * Tipo: Class BarraTareasGestor.
 */
public class BarraTareasGestor extends BarraTareas {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante VOLVER_ACTION que representa la acción de retroceder. */
	public static final String VOLVER_ACTION = "Volver";
	
	/** Constante HOME_ACTION que representa la acción de volver a la ventana principal. */
	public static final String HOME_ACTION = "Home";
	
	/** Constante CERRAR_SESION_ACTION que representa la acción de cerrar sesión. */
	public static final String CERRAR_SESION_ACTION = "Cerrar sesión";
	
	/** Constante INFO que representa la acción de ver la información. */
	public static final String INFO_ACTION = "Info";
	
	/** Constante correspondiente al porcentaje de pantalla dejado para la separación lateral. */
	private static final double SPACE_BETWEEN = 0.01;

	/* Porcentaje de pantalla que ocupa el botón de cerrar sesión */
	private static final double BTN_ACCOUNT_W = 0.13;

	/** Botón asociado a la acción de volver a la anterior ventana. */
	private JButton volver;
	
	/** Botón asociado a la acción de volver a la pantalla del inicio. */
	private JButton btnHome;
	
	/** Botón asociado a la acción de cerrar la sesión. */
	private JButton btnCerrarSesion;
	
	/** Botón asociado a la acción de ver la información de la ventana. */
	private JButton info;

	/**
	 * Instancia un nuevo Objeto BarraTareasGestor.
	 */
	public BarraTareasGestor() {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int homeW = btnH;

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));
		
		// Botón de volver atrás
		volver = ButtonFactory.newIconButton("flechaAtras.png", btnH, homeW);
		ButtonFactory.paintButton(volver, ColorPalette.BLUE, ColorPalette.WHITE);
		volver.setActionCommand(VOLVER_ACTION);
		volver.setToolTipText("Volver atrás");
		ButtonFactory.addMouseMecanics(volver, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(volver, "Volver atrás", 0);
		
		// Botón de volver a la ventana de inicio del gestor
		btnHome = ButtonFactory.newIconButton("homeButton.png", btnH, homeW);
		btnHome.setBackground(ColorPalette.BLUE.getColor());
		btnHome.setForeground(ColorPalette.WHITE.getColor());
		btnHome.setActionCommand(HOME_ACTION);
		ButtonFactory.addMouseMecanics(btnHome, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(btnHome, "Volver a la pantalla principal", 0);
	
		//Botón de cerrar sesión
		btnCerrarSesion = ButtonFactory.newRoundedButton("Cerrar sesión", btnH, accountW, 0.25);
		btnCerrarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnCerrarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnCerrarSesion.setActionCommand(CERRAR_SESION_ACTION);
		ButtonFactory.addMouseMecanics(btnCerrarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		ButtonFactory.addHoverInfo(btnCerrarSesion, "Cerrar sesión", 0);

		// Botón de ver la información de la pantalla
		info = ButtonFactory.newIconButton("interrogacion.png", btnH, homeW);
		ButtonFactory.paintButton(info, ColorPalette.BLUE, ColorPalette.WHITE);
		info.setActionCommand(INFO_ACTION);
		ButtonFactory.addMouseMecanics(info, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(info, "Ver información de la ventana", 0);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// Añade todos los botones a la barra de tareas
		add(Box.createHorizontalStrut(spaceBetween));
		btnHome.setMaximumSize(new Dimension(homeW, btnH));
		btnHome.setPreferredSize(new Dimension(homeW, btnH));
		btnHome.setMinimumSize(new Dimension(homeW, btnH));
		add(btnHome);
		add(Box.createHorizontalStrut(spaceBetween));
				
		btnCerrarSesion.setMaximumSize(new Dimension(accountW, btnH));
		btnCerrarSesion.setPreferredSize(new Dimension(accountW, btnH));

		add(volver);
		add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(spaceBetween));
		
		add(info);
		add(Box.createHorizontalStrut(spaceBetween));

		add(btnCerrarSesion);
		add(Box.createHorizontalStrut(spaceBetween));
	}
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param c Control de barra de tareas que se añade
	 */
	@Override
	public void setControlador(ActionListener c) {
		btnHome.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		volver.addActionListener(c);
		info.addActionListener(c);
	}

}
