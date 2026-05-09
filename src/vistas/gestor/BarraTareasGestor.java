package vistas.gestor;

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

public class BarraTareasGestor extends BarraTareas {
	private static final long serialVersionUID = 1L;

	private static final double SPACE_BETWEEN = 0.01;
	private static final double BTN_ACCOUNT_W = 0.13;

	private JButton volver;
	private JButton btnHome;
	private JButton btnCerrarSesion;
	private JButton info;

	public BarraTareasGestor() {
		TiendaFrame t = TiendaFrame.getInstance();

		int h = t.getPixelsHeight(PanelSizes.TOOLBAR_HEIGHT);
		int spaceBetween = t.getPixelsHeight(SPACE_BETWEEN);
		int btnH = h - 2 * spaceBetween;
		int accountW = t.getPixelsWidth(BTN_ACCOUNT_W);
		int homeW = btnH;

		setBackground(ColorPalette.BLUE.getColor());
		setPreferredSize(new Dimension(0, h));
		
		volver = ButtonFactory.newIconButton("flechaAtras.png", btnH, homeW);
		ButtonFactory.paintButton(volver, ColorPalette.BLUE, ColorPalette.WHITE);
		volver.setActionCommand("Volver");
		volver.setToolTipText("Volver atrás");
		ButtonFactory.addMouseMecanics(volver, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(volver, "Volver atrás", 0);
		
		btnHome = ButtonFactory.newIconButton("homeButton.png", btnH, homeW);
		btnHome.setBackground(ColorPalette.BLUE.getColor());
		btnHome.setForeground(ColorPalette.WHITE.getColor());
		btnHome.setActionCommand("Home");
		ButtonFactory.addMouseMecanics(btnHome, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(btnHome, "Volver a la pantalla principal", 0);
	
		btnCerrarSesion = ButtonFactory.newRoundedButton("Cerrar sesión", btnH, accountW, 0.25);
		btnCerrarSesion.setBackground(ColorPalette.LIGHT_PURPLE.getColor());
		btnCerrarSesion.setForeground(ColorPalette.WHITE.getColor());
		btnCerrarSesion.setActionCommand("Cerrar sesión");
		ButtonFactory.addMouseMecanics(btnCerrarSesion, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		ButtonFactory.addHoverInfo(btnCerrarSesion, "Cerrar sesión", 0);

		info = ButtonFactory.newIconButton("interrogacion.png", btnH, homeW);
		ButtonFactory.paintButton(info, ColorPalette.BLUE, ColorPalette.WHITE);
		info.setActionCommand("Info");
		ButtonFactory.addMouseMecanics(info, ColorPalette.BLUE, ColorPalette.HOVER_BLUE);
		ButtonFactory.addHoverInfo(info, "Información", 0);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

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
	
	public JButton getInfoButton() { return info;}

	@Override
	public void setControlador(ControlBarraTareas c) {
		btnHome.addActionListener(c);
		btnCerrarSesion.addActionListener(c);
		volver.addActionListener(c);
		info.addActionListener(c);
	}

}
