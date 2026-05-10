package vistas.empleado.general;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;

/**
 * Esta clase representa la barra lateral en el menú de empleado
 */
public class BarraEmpleado extends BarraLateral {
	private static final long serialVersionUID = 1L;

	/** Botón de gestionar pedidos */
	private JButton gestionarPedidos = new JButton("Gestionar pedidos");
	/** Votón de valorar objetos */
	private JButton valorarObjetos = new JButton("Valorar artículos de segunda mano");
	/** Botón de gestionar intercambios */
	private JButton gestionarIntercambios = new JButton("Gestionar intercambios");
	/** Botón de añadir productos */
	private JButton anadirProducto = new JButton("Añadir productos");
	/** Botón de gestionar existentes */
	private JButton gestionarExistentes = new JButton("Gestionar productos existentes");
	/** Botón de gestionar categorías */
	private JButton gestionarCategorias = new JButton("Gestionar categorías");
	/** Ancho del menú */
	private static final double MENU_WIDTH = 0.17;

	/**
	 * Constructor de la barra lateral de empleado
	 */
	public BarraEmpleado() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		Map<String, List<JButton>> mapa = new TreeMap<>();
		
		mapa.put("Gestionar Catálogo", new ArrayList<JButton>(List.of(anadirProducto, gestionarExistentes, gestionarCategorias)));
		mapa.put("Gestionar Pedidos", new ArrayList<JButton>(List.of(gestionarPedidos)));
		mapa.put("Valorar Artículos", new ArrayList<JButton>(List.of(valorarObjetos)));
		mapa.put("Gestionar Intercambios", new ArrayList<JButton>(List.of(gestionarIntercambios)));
		
		MenuLateral menu = new MenuLateral(mapa, MENU_WIDTH);
		
		add(menu);
	}

	@Override
	public void setControlador(ActionListener c) {
		gestionarPedidos.addActionListener(c);
		valorarObjetos.addActionListener(c);
		gestionarIntercambios.addActionListener(c);
		anadirProducto.addActionListener(c);
		gestionarExistentes.addActionListener(c);
		gestionarCategorias.addActionListener(c);
	}

}