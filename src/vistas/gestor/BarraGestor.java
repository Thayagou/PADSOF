package vistas.gestor;

import java.awt.*;
import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class BarraGestor extends BarraLateral {
	private static final long serialVersionUID = 1L;

	private JButton gestionarProductos;
	private JButton anadirDescuento;
	private JButton gestionarEmpleados;
	private JButton configurarSistema;
	private JButton consultarEstadisticas;

	public BarraGestor() {
		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();
		int btnHeigth = frame.btnHeight();

		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		ButtonFactory f = new ButtonFactory();

		gestionarProductos = addBtn(f, "Gestionar productos y categorías", btnHeigth, distFromLeft);
		anadirDescuento = addBtn(f, "Añadir nuevo descuento", btnHeigth, distFromLeft);
		gestionarEmpleados = addBtn(f, "Gestionar empleados", btnHeigth, distFromLeft);
		configurarSistema = addBtn(f, "Configurar sistema", btnHeigth, distFromLeft);
		consultarEstadisticas = addBtn(f, "Consultar estadísticas", btnHeigth, distFromLeft);

		add(gestionarProductos);
		add(anadirDescuento);
		add(gestionarEmpleados);
		add(configurarSistema);
		add(consultarEstadisticas);
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		gestionarProductos.addActionListener(c);
		anadirDescuento.addActionListener(c);
		gestionarEmpleados.addActionListener(c);
		configurarSistema.addActionListener(c);
		consultarEstadisticas.addActionListener(c);
	}
}
