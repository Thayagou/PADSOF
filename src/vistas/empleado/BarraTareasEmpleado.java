package vistas.empleado;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SpringLayout;

import controladores.ControlBarraTareas;
import vistas.BarraTareas;
import vistas.ButtonFactory;
import vistas.ColorPalette;
import vistas.PanelSizes;
import vistas.TiendaFrame;

public class BarraTareasEmpleado extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static final double SPACE_BETWEEN = 0.01;
	private static final double BTN_ACCOUNT_W = 0.13;

	private JButton btnCerrarSesion;
	private JButton btnHome;

	public BarraTareasEmpleado() {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
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

		btnCerrarSesion = f.newRoundedButton("Cerrar sesión", btnH, accountW, 0.25);
		btnCerrarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnCerrarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnCerrarSesion.setActionCommand("Cerrar sesión");
		f.addMouseMecanics(btnCerrarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		add(btnHome);
		add(btnCerrarSesion);

		// Posición: [space] [campoBusqueda expandido] [space] [btnBuscar] [space]
		// [btnIniciarSesion] [space]
		layout.putConstraint(SpringLayout.WEST, btnHome, spaceBetween, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, btnHome, spaceBetween, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.EAST, btnCerrarSesion, -spaceBetween, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, btnCerrarSesion, spaceBetween, SpringLayout.NORTH, this);
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		btnCerrarSesion.addActionListener(c);
		btnHome.addActionListener(c);
	}

}
