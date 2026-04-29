package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import controladores.ControlBarraTareas;
import vistas.*;

public class BarraTareasNoRegistrado extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static final double SPACE_BETWEEN = 0.01;
	private static final double BTN_ACCOUNT_W = 0.13;
	private static final double BTN_SEARCH_W = 0.55;

	private JButton btnBuscar;
	private JButton btnIniciarSesion;

	public BarraTareasNoRegistrado() {
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
		btnBuscar.addActionListener(c);
		btnIniciarSesion.addActionListener(c);
	}
}
