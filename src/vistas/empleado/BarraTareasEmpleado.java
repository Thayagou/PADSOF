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
	private static final double BTN_SEARCH_W = 0.55;

	private JButton btnBuscar;
	private JButton btnIniciarSesion;

	public BarraTareasEmpleado() {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int searchW = t.getPixelsWidth(BTN_SEARCH_W);
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));

		ButtonFactory f = new ButtonFactory();

		btnBuscar = f.newRoundedButton("Buscar", btnH, searchW, 1);
		btnBuscar.setBackground(ColorPalette.WHITE.getColor());
		btnBuscar.setForeground(ColorPalette.BLACK.getColor());
		btnBuscar.setActionCommand("Buscar productos");
		f.addMouseMecanics(btnBuscar, ColorPalette.WHITE, ColorPalette.LIGHT_PURPLE);

		btnIniciarSesion = f.newButton("Iniciar sesión", btnH, accountW);
		btnIniciarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnIniciarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnIniciarSesion.setActionCommand("Iniciar sesión");
		f.addMouseMecanics(btnIniciarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		add(btnBuscar);
		add(btnIniciarSesion);

		// Posición: [space] [campoBusqueda expandido] [space] [btnBuscar] [space]
		// [btnIniciarSesion] [space]
		layout.putConstraint(SpringLayout.WEST, btnBuscar, spaceBetween, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, btnBuscar, spaceBetween, SpringLayout.NORTH, this);

		layout.putConstraint(SpringLayout.WEST, btnIniciarSesion, spaceBetween, SpringLayout.EAST, btnBuscar);
		layout.putConstraint(SpringLayout.NORTH, btnIniciarSesion, spaceBetween, SpringLayout.NORTH, this);
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		// TODO Auto-generated method stub

	}

}
