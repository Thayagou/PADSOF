package vistas.empleado.general;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import controladores.ControlBarraTareas;
import vistas.common.app.BarraTareas;
import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelSizes;

public class BarraTareasEmpleado extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static final double SPACE_BETWEEN = 0.01;
	private static final double BTN_ACCOUNT_W = 0.13;

	private JButton btnHome;
	private JButton btnVolver;
	private JButton btnNotificaciones;
	private JButton btnCerrarSesion;

	public BarraTareasEmpleado() {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int homeW = btnH;

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));
		
		btnHome = ButtonFactory.newIconButton("homeButton.png", btnH, homeW);
		btnHome.setBackground(ColorPalette.BLUE.getColor());
		btnHome.setForeground(ColorPalette.WHITE.getColor());
		btnHome.setActionCommand("Home");
		ButtonFactory.addMouseMecanics(btnHome, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		
		btnVolver = ButtonFactory.newIconButton("flechaAtras.png", btnH, homeW);
		btnVolver.setBackground(ColorPalette.BLUE.getColor());
		btnVolver.setForeground(ColorPalette.WHITE.getColor());
		btnVolver.setActionCommand("Volver");
		ButtonFactory.addMouseMecanics(btnVolver, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		
		btnNotificaciones = ButtonFactory.newIconButton("notificaciones.png", btnH, homeW);
		btnNotificaciones.setBackground(ColorPalette.BLUE.getColor());
		btnNotificaciones.setForeground(ColorPalette.WHITE.getColor());
		btnNotificaciones.setActionCommand("Notificaciones");
		ButtonFactory.addMouseMecanics(btnNotificaciones, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);

		btnCerrarSesion = ButtonFactory.newRoundedButton("Cerrar sesión", btnH, accountW, 0.25);
		btnCerrarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnCerrarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnCerrarSesion.setActionCommand("Cerrar sesión");
		ButtonFactory.addMouseMecanics(btnCerrarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(Box.createHorizontalStrut(spaceBetween));
		btnHome.setMaximumSize(new Dimension(homeW, btnH));
		btnHome.setPreferredSize(new Dimension(homeW, btnH));
		btnHome.setMinimumSize(new Dimension(homeW, btnH));
		add(btnHome);
		add(Box.createHorizontalStrut(spaceBetween));

		btnVolver.setMaximumSize(new Dimension(homeW, btnH));
		btnVolver.setPreferredSize(new Dimension(homeW, btnH));
		btnVolver.setMinimumSize(new Dimension(100, btnH));
				
		btnNotificaciones.setMaximumSize(new Dimension(homeW, btnH));
		btnNotificaciones.setPreferredSize(new Dimension(homeW, btnH));
		btnNotificaciones.setMinimumSize(new Dimension(100, btnH));
		
		btnCerrarSesion.setMaximumSize(new Dimension(accountW, btnH));
		btnCerrarSesion.setPreferredSize(new Dimension(accountW, btnH));

		add(btnVolver);
		add(Box.createHorizontalStrut(spaceBetween));
		add(btnNotificaciones);
		add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(spaceBetween));

		add(btnCerrarSesion);
		add(Box.createHorizontalStrut(spaceBetween));
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		btnHome.addActionListener(c);
		btnVolver.addActionListener(c);
		btnNotificaciones.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
	}

}
