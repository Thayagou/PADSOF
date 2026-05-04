package vistas.empleado;

import java.awt.*;
import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class BarraEmpleado extends BarraLateral {
	private static final long serialVersionUID = 1L;

	private JButton gestionarProductos;
	private JButton gestionarPedidos;
	private JButton valorarObjetos;
	private JButton gestionarIntercambios;

	private JButton anadirProducto;
	private JButton cargarFichero;
	private JButton gestionarExistentes;
	private JButton anadirCategoria;
	private JButton gestionarCategorias;
	private JButton crearPacks;

	public BarraEmpleado() {
		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();
		int distIndented = (int) (distFromLeft * 0.9);
		int btnHeigth = frame.btnHeight();

		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		ButtonFactory f = new ButtonFactory();
		gestionarProductos = addBtn(f, "Gestionar productos y categorías", btnHeigth, distFromLeft);
		anadirProducto = addBtn(f, "Añadir nuevo producto", btnHeigth, distIndented);
		cargarFichero = addBtn(f, "Cargar fichero de productos", btnHeigth, distIndented);
		gestionarExistentes = addBtn(f, "Gestionar productos existentes", btnHeigth, distIndented);
		anadirCategoria = addBtn(f, "Añadir nueva categoría", btnHeigth, distIndented);
		gestionarCategorias = addBtn(f, "Gestionar categorías existentes", btnHeigth, distIndented);
		crearPacks = addBtn(f, "Crear packs de productos", btnHeigth, distIndented);
		
		gestionarPedidos = addBtn(f, "Gestionar pedidos", btnHeigth, distFromLeft);
		valorarObjetos = addBtn(f, "Valorar objetos de segunda mano", btnHeigth, distFromLeft);
		gestionarIntercambios = addBtn(f, "Gestionar intercambios", btnHeigth, distFromLeft);

		add(gestionarProductos);
		add(anadirProducto);
		add(cargarFichero);
		add(gestionarExistentes);
		add(anadirCategoria);
		add(gestionarCategorias);
		add(crearPacks);
		
		add(gestionarPedidos);
		add(valorarObjetos);
		add(gestionarIntercambios);
		
		setInvisibleGestProductos();
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		gestionarProductos.addActionListener(c);
		gestionarPedidos.addActionListener(c);
		valorarObjetos.addActionListener(c);
		gestionarIntercambios.addActionListener(c);

		anadirProducto.addActionListener(c);
		cargarFichero.addActionListener(c);
		gestionarExistentes.addActionListener(c);
		anadirCategoria.addActionListener(c);
		gestionarCategorias.addActionListener(c);
		crearPacks.addActionListener(c);
	}

	public void setVisibleGestProductos() {
		anadirProducto.setVisible(true);
		cargarFichero.setVisible(true);
		gestionarExistentes.setVisible(true);
		anadirCategoria.setVisible(true);
		gestionarCategorias.setVisible(true);
		crearPacks.setVisible(true);
	}
	
	public void setInvisibleGestProductos() {
		anadirProducto.setVisible(false);
		cargarFichero.setVisible(false);
		gestionarExistentes.setVisible(false);
		anadirCategoria.setVisible(false);
		gestionarCategorias.setVisible(false);
		crearPacks.setVisible(false);
	}
	
}