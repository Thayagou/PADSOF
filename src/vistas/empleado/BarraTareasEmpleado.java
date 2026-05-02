package vistas.empleado;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import controladores.ControlBarraTareas;
import vistas.common.BarraTareas;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelSizes;

public class BarraTareasEmpleado extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static final double SPACE_BETWEEN = 0.01;
	private static final double BTN_ACCOUNT_W = 0.13;

	private JButton btnHome;
	private JButton btnCuenta;
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

		ButtonFactory f = new ButtonFactory();
		
		btnHome = f.newIconButton("homeButton.png", btnH, homeW);
		btnHome.setBackground(ColorPalette.BLUE.getColor());
		btnHome.setForeground(ColorPalette.WHITE.getColor());
		btnHome.setActionCommand("Home");
		f.addMouseMecanics(btnHome, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		
		btnCuenta = f.newIconButton("cuentaButton.png", btnH, homeW);
		btnCuenta.setBackground(ColorPalette.BLUE.getColor());
		btnCuenta.setForeground(ColorPalette.WHITE.getColor());
		btnCuenta.setActionCommand("Cuenta");
		f.addMouseMecanics(btnCuenta, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);

		btnCerrarSesion = f.newRoundedButton("Cerrar sesión", btnH, accountW, 0.25);
		btnCerrarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnCerrarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnCerrarSesion.setActionCommand("Cerrar sesión");
		f.addMouseMecanics(btnCerrarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(Box.createHorizontalStrut(spaceBetween));
		btnHome.setMaximumSize(new Dimension(homeW, btnH));
		btnHome.setPreferredSize(new Dimension(homeW, btnH));
		btnHome.setMinimumSize(new Dimension(homeW, btnH));
		add(btnHome);
		add(Box.createHorizontalStrut(spaceBetween));

		btnCuenta.setMaximumSize(new Dimension(homeW, btnH));
		btnCuenta.setPreferredSize(new Dimension(homeW, btnH));
		btnCuenta.setMinimumSize(new Dimension(100, btnH));
				
		btnCerrarSesion.setMaximumSize(new Dimension(accountW, btnH));
		btnCerrarSesion.setPreferredSize(new Dimension(accountW, btnH));

		add(btnCuenta);
		add(Box.createHorizontalGlue());
		add(Box.createHorizontalStrut(spaceBetween));

		add(btnCerrarSesion);
		add(Box.createHorizontalStrut(spaceBetween));
	}

	@Override
	public void setControlador(ControlBarraTareas c) {
		btnHome.addActionListener(c);
		btnCuenta.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
	}

}
