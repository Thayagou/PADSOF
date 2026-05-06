package vistas.empleado.general;

import java.awt.*;

import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ColorPalette;

public class BarraEmpleado extends BarraLateral {
	private static final long serialVersionUID = 1L;

	private JButton gestionarProductos;
	private JButton gestionarPedidos;
	private JButton valorarObjetos;
	private JButton gestionarIntercambios;

	private JButton anadirProducto;
	private JButton gestionarExistentes;
	private JButton gestionarCategorias;

	public BarraEmpleado() {
		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();
		int distIndented = (int) (distFromLeft * BarraLateral.PERC_INDENTED);
		int btnHeigth = frame.btnHeight();

		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gestionarProductos = addBtn("Gestionar productos y categorías", btnHeigth, distFromLeft);
		anadirProducto = addBtn("Añadir nuevo producto", btnHeigth, distIndented);
		gestionarExistentes = addBtn("Gestionar productos existentes", btnHeigth, distIndented);
		gestionarCategorias = addBtn("Gestionar categorías", btnHeigth, distIndented);
		addIndentedBtns(gestionarProductos, anadirProducto, gestionarCategorias, gestionarExistentes);

		gestionarPedidos = addBtn("Gestionar pedidos", btnHeigth, distFromLeft);
		valorarObjetos = addBtn("Valorar objetos de segunda mano", btnHeigth, distFromLeft);
		gestionarIntercambios = addBtn("Gestionar intercambios", btnHeigth, distFromLeft);

		add(gestionarProductos);
		add(anadirProducto);
		add(gestionarExistentes);
		add(gestionarCategorias);

		add(gestionarPedidos);
		add(valorarObjetos);
		add(gestionarIntercambios);
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		gestionarProductos.addActionListener(c);
		gestionarPedidos.addActionListener(c);
		valorarObjetos.addActionListener(c);
		gestionarIntercambios.addActionListener(c);

		anadirProducto.addActionListener(c);
		gestionarExistentes.addActionListener(c);
		gestionarCategorias.addActionListener(c);
	}

}