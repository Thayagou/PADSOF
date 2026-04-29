package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import controladores.ControlBarraTareas;
import vistas.*;
import vistas.common.BarraTareas;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelSizes;

public class BarraTareasNoRegistrado extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static final double SPACE_BETWEEN = 0.01;
	private static final double BTN_ACCOUNT_W = 0.13;
	private static final double BTN_SEARCH_W = 0.35;

	private JButton btnBuscar;
	private JButton btnIniciarSesion;
	private JButton btnHome;

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

//		SpringLayout layout = new SpringLayout();
//		setLayout(layout);
//
//		add(btnHome);
//		add(btnBuscar);
//		add(btnIniciarSesion);
//
//		// Posición: [space] [campoBusqueda expandido] [space] [btnBuscar] [space]
//		// [btnIniciarSesion] [space]
//		layout.putConstraint(SpringLayout.WEST, btnHome, spaceBetween, SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.NORTH, btnHome, spaceBetween, SpringLayout.NORTH, this);
//		
//		layout.putConstraint(SpringLayout.WEST, btnBuscar, spaceBetween, SpringLayout.EAST, btnHome);
//		layout.putConstraint(SpringLayout.NORTH, btnBuscar, spaceBetween, SpringLayout.NORTH, this);
//		layout.putConstraint(SpringLayout.EAST, btnBuscar, -spaceBetween, SpringLayout.WEST, btnIniciarSesion);
//
//		layout.putConstraint(SpringLayout.EAST, btnIniciarSesion, -spaceBetween, SpringLayout.EAST, this);
//		layout.putConstraint(SpringLayout.NORTH, btnIniciarSesion, spaceBetween, SpringLayout.NORTH, this);
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		btnBuscar.addActionListener(c);
		btnIniciarSesion.addActionListener(c);
		btnHome.addActionListener(c);
	}
}
