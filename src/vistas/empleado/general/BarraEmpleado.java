package vistas.empleado.general;

import java.awt.*;

import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.app.BarraLateral;
import vistas.common.app.TiendaFrame;
import vistas.herramientas.ColorPalette;

/**
 * Esta clase representa la barra lateral en el menú de empleado
 */
public class BarraEmpleado extends BarraLateral {
	private static final long serialVersionUID = 1L;

	/** Botón de gestionar productos */
	private JButton gestionarProductos;
	/** Botón de gestionar pedidos */
	private JButton gestionarPedidos;
	/** Votón de valorar objetos */
	private JButton valorarObjetos;
	/** Botón de gestionar intercambios */
	private JButton gestionarIntercambios;

	/** Botón de añadir productos */
	private JButton anadirProducto;
	/** Botón de gestionar existentes */
	private JButton gestionarExistentes;
	/** Botón de gestionar categorías */
	private JButton gestionarCategorias;

	/**
	 * Constructor de la barra lateral de empleado
	 */
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