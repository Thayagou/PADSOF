package vistas.gestor;

import java.awt.*;
import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ColorPalette;

public class BarraGestor extends BarraLateral {
	private static final long serialVersionUID = 1L;

	private JButton gestionarProductos;
	private JButton gestionarCategorias;
	private JButton anadirDescuento;
	private JButton gestionarEmpleados;
	private JButton configurarSistema;
	private JButton consultarEstadisticas;
	private JButton consultarStatsProd;
	private JButton consultarStatsTienda;

	public BarraGestor() {
		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();
		int btnHeigth = frame.btnHeight();

		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gestionarProductos = addBtn("Gestionar productos", btnHeigth, distFromLeft);
		gestionarCategorias = addBtn("Gestionar categorías", btnHeigth, distFromLeft);
		anadirDescuento = addBtn("Añadir nuevo descuento", btnHeigth, distFromLeft);
		gestionarEmpleados = addBtn("Gestionar empleados", btnHeigth, distFromLeft);
		configurarSistema = addBtn("Configurar sistema", btnHeigth, distFromLeft);
		consultarEstadisticas = addBtn("Consultar estadísticas clientes", btnHeigth, distFromLeft);
		consultarStatsProd = addBtn("Consultar estadísticas productos", btnHeigth, distFromLeft);
		consultarStatsTienda = addBtn("Consultar estadísticas tienda", btnHeigth, distFromLeft);
		
		add(gestionarProductos);
		add(gestionarCategorias);
		add(anadirDescuento);
		add(gestionarEmpleados);
		add(configurarSistema);
		add(consultarEstadisticas);
		add(consultarStatsProd);
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		gestionarProductos.addActionListener(c);
		gestionarCategorias.addActionListener(c);
		anadirDescuento.addActionListener(c);
		gestionarEmpleados.addActionListener(c);
		configurarSistema.addActionListener(c);
		consultarEstadisticas.addActionListener(c);
		consultarStatsProd.addActionListener(c);
		consultarStatsTienda.addActionListener(c);
	}
}
