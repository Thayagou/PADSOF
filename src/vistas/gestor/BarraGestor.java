package vistas.gestor;

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
 * Vista de la Barra lateral del gestor. Tiene los botones necesarios para la navegación entre ventanas
 */
public class BarraGestor extends BarraLateral {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** ActionCommand de la acción de añadir un nuevo producto. */
	public static final String ANADIR_PRODUCTO_ACTION = "Añadir productos";
	
	/** Botón de añadir productos. */
	private JButton anadirProducto = new JButton(ANADIR_PRODUCTO_ACTION);
	
	/** ActionCommand de la acción de gestionar productos. */
	public static final String GESTIONAR_PRODUCTOS_ACTION = "Gestionar productos";
	
	/** Botón de gestionar existentes. */
	private JButton gestionarExistentes = new JButton(GESTIONAR_PRODUCTOS_ACTION);
	
	/** ActionCommand de la acción de gestionar categorías. */
	public static final String GESTIONAR_CATEGORIAS_ACTION = "Gestionar categorías";
	
	/** Botón de gestionar categorías. */
	private JButton gestionarCategorias = new JButton(GESTIONAR_CATEGORIAS_ACTION);
	
	/** ActionCommand de la acción de añadir un nuevo descuento. */
	public static final String ANADIR_DESCUENTO_ACTION = "Añadir nuevo descuento";
	
	/** Botón de añadir nuevo descuento. */
	private JButton anadirDescuento = new JButton(ANADIR_DESCUENTO_ACTION);
	
	/** ActionCommand de la acción de gestionar empleados. */
	public static final String GESTIONAR_EMPLEADOS_ACTION = "Gestionar empleados";
	
	/** Botón de gestionar empleados. */
	private JButton gestionarEmpleados = new JButton(GESTIONAR_EMPLEADOS_ACTION);
	
	/** ActionCommand de la acción de configurar el sistema. */
	public static final String CONFIGURAR_SISTEMA_ACTION = "Configurar sistema";
	
	/** Botón de configurar sistema. */
	private JButton configurarSistema = new JButton(CONFIGURAR_SISTEMA_ACTION);
	
	/** ActionCommand de la acción de consultar las estadísticas de los clientes. */
	public static final String STATS_CLIENTES_ACTION = "Estadísticas clientes";
	
	/** Botón de consultar las estadísticas de los clientes. */
	private JButton consultarStatsClientes = new JButton(STATS_CLIENTES_ACTION);
	
	/** ActionCommand de la acción de consultar las estadísticas de los productos. */
	public static final String STATS_PRODUCTOS_ACTION = "Estadísticas productos";
	
	/** Botón de consultar las estadísticas de los productos. */
	private JButton consultarStatsProductos = new JButton(STATS_PRODUCTOS_ACTION);
	
	/** ActionCommand de la acción de  consultar las estadísticas de las ventas de la tienda. */
	public static final String STATS_VENTAS_ACTION = "Estadísticas ventas";
	
	/** Botón de consultar las estadísticas de las ventas. */
	private JButton consultarStatsVentas = new JButton(STATS_VENTAS_ACTION);
	
	/** ActionCommand de la acción de  consultar las estadísticas de los intercambios. */
	public static final String STATS_INTERCAMBIO_ACTION = "Estadísticas intercambios";
	
	/** Botón de consultar las estadísticas de los intercambios. */
	private JButton consultarStatsIntercambios = new JButton(STATS_INTERCAMBIO_ACTION);

	/** Anchura de la barra de tareas del gestor. */
	private static final double MENU_WIDTH = 0.17;
	
	/**
	 * Instancia la nueva barra del gestor, estableciendo todos sus parámetros.
	 */
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
	
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param c Control de barra lateral añadido
	 */
	@Override
	public void setControlador(ActionListener c) {
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
