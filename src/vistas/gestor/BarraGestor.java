package vistas.gestor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import controladores.ControlBarraLateral;
import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;
import vistas.common.app.TiendaFrame;
import vistas.herramientas.ColorPalette;

public class BarraGestor extends BarraLateral {
	private static final long serialVersionUID = 1L;

	/** Botón de añadir productos */
	private JButton anadirProducto = new JButton("Añadir productos");
	/** Botón de gestionar existentes */
	private JButton gestionarExistentes = new JButton("Gestionar productos existentes");
	/** Botón de gestionar categorías */
	private JButton gestionarCategorias = new JButton("Gestionar categorías");
	
	/** Botón de añadir nuevo descuento */
	private JButton anadirDescuento = new JButton("Añadir nuevo descuento");
	/** Botón de gestionar empleados */
	private JButton gestionarEmpleados = new JButton("Gestionar empleados");
	/** Botón de configurar sistema */
	private JButton configurarSistema = new JButton("Configurar sistema");
	
	/** Botón de consultar las estadísticas de los clientes */
	private JButton consultarStatsClientes = new JButton("Estadísticas clientes");
	/** Botón de consultar las estadísticas de los productos */
	private JButton consultarStatsProductos = new JButton("Estadísticas productos");
	/** Botón de consultar las estadísticas de las ventas */
	private JButton consultarStatsVentas = new JButton("Estadísticas ventas");
	/** Botón de consultar las estadísticas de los intercambios */
	private JButton consultarStatsIntercambios = new JButton("Estadísticas intercambios");

	private static final double MENU_WIDTH = 0.17;
	
	public BarraGestor() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		Map<String, List<JButton>> mapa = new TreeMap<>();
		
		mapa.put("Gestionar Catálogo", new ArrayList<JButton>(List.of(anadirProducto, gestionarExistentes, gestionarCategorias)));
		mapa.put("Añadir nuevo descuento", new ArrayList<JButton>(List.of(anadirDescuento)));
		mapa.put("Gestionar empleados", new ArrayList<JButton>(List.of(gestionarEmpleados)));
		mapa.put("Configurar sistema", new ArrayList<JButton>(List.of(configurarSistema)));
		mapa.put("Consultar estadísticas", new ArrayList<JButton>(List.of(consultarStatsProductos, consultarStatsClientes, consultarStatsVentas, consultarStatsIntercambios)));
		
		MenuLateral menu = new MenuLateral(mapa, MENU_WIDTH);
		
		add(menu);
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		anadirProducto.addActionListener(c);
		gestionarExistentes.addActionListener(c);
		gestionarCategorias.addActionListener(c);

		anadirDescuento.addActionListener(c);
		gestionarEmpleados.addActionListener(c);
		configurarSistema.addActionListener(c);
		
		consultarStatsProductos.addActionListener(c);
		consultarStatsClientes.addActionListener(c);
		consultarStatsVentas.addActionListener(c);
		consultarStatsIntercambios.addActionListener(c);
	}
}
