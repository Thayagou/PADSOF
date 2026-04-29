package vistas.noRegistrado;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controladores.ControlBarraLateral;
import controladores.noRegistrado.ControlBarraNoRegistrado;
import vistas.BarraLateral;
import vistas.ButtonFactory;
import vistas.ColorPalette;
import vistas.TiendaFrame;

public class BarraNoRegistrado extends BarraLateral{
	private static final long serialVersionUID = 1L;

	private JButton iniciarSesion;
	private JButton registrarse;
	private JButton busqueda;

	public BarraNoRegistrado() {
        TiendaFrame frame = TiendaFrame.getInstance();
        int distFromLeft = frame.optionBarDistFromLeft();
        int btnHeigth = frame.btnHeight();

        setBackground(ColorPalette.CARD_LIGHT.getColor());
        setPreferredSize(new Dimension(distFromLeft, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));;

		ButtonFactory f = new ButtonFactory();
        iniciarSesion = addBtn(f, "Iniciar Sesión", btnHeigth, distFromLeft);
        registrarse = addBtn(f, "Registrarse", btnHeigth, distFromLeft);
        busqueda = addBtn(f, "Buscar productos", btnHeigth, distFromLeft);
        
        add(iniciarSesion);
        add(registrarse);
        add(busqueda);
    }

	private JButton addBtn(ButtonFactory f, String text, int heigth, int width) {
		JButton btn = f.newButton(text, heigth, width);
		btn.setBackground(ColorPalette.CARD_LIGHT.getColor());
		f.addMouseMecanics(btn, ColorPalette.CARD_LIGHT, ColorPalette.CARD_DARK);
		btn.setBorderPainted(false);

		return btn;
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		iniciarSesion.addActionListener(c);
		registrarse.addActionListener(c);
		busqueda.addActionListener(c);
	}
}
