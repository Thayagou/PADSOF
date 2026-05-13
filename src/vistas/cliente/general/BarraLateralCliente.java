package vistas.cliente.general;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;

import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;

/**
 * Barra lateral específica para clientes registrados, con opciones de compra, segunda mano y pendientes.
 */
public class BarraLateralCliente extends BarraLateral {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante SEARCH_PRODUCTS. Comando de acción para buscar productos en la tienda. */
	public static final String SEARCH_PRODUCTS = "Buscar Productos";
	
	/** Constante SHOP_CAR. Comando de acción para ver el carrito de compras. */
	public static final String SHOP_CAR = "Ver Carrito";
	
	/** Constante SEARCH_ART. Comando de acción para buscar artículos de segunda mano. */
	public static final String SEARCH_ART = "Buscar Artículos";
	
	/** Constante WALLET. Comando de acción para ver la cartera de artículos de segunda mano. */
	public static final String WALLET = "Ver Cartera";
	
	/** Constante ADD_ART. Comando de acción para añadir un nuevo artículo de segunda mano. */
	public static final String ADD_ART = "Añadir Artículo";
	
	/** Constante OFFERS. Comando de acción para ver las ofertas enviadas por el usuario. */
	public static final String OFFERS_SENT = "Ver mis ofertas";
	
	/** Action command para ir a la ventana de ofertas recividas */
	public static final String OFFERS_RECIEVED = "Ofertas Entrantes";
	
	/** Action command para ir a la ventana de intercambios pendientes */
	public static final String EXCHANGES = "Intercambios pendientes";
	
	/** Constante COMPRAS. Comando de acción para ver el historial de compras realizadas. */
	public static final String COMPRAS = "Ver mis compras";

	/** boton buscarProductos. Botón para acceder a la búsqueda de productos de la tienda. */
	private JButton buscarProductos = new JButton(SEARCH_PRODUCTS);
	
	/** boton verCarrito. Botón para acceder al carrito de compras. */
	private JButton verCarrito = new JButton(SHOP_CAR);
	
	/** boton buscarArticulos. Botón para acceder a la búsqueda de artículos de segunda mano. */
	private JButton buscarArticulos = new JButton(SEARCH_ART);
	
	/** boton verCartera. Botón para acceder a la cartera de artículos del usuario. */
	private JButton verCartera = new JButton(WALLET);
	
	/** boton anadirArticulo. Botón para añadir un nuevo artículo de segunda mano. */
	private JButton anadirArticulo = new JButton(ADD_ART);
	
	/** boton verMisOfertas. Botón para ver las ofertas enviadas por el usuario. */
	private JButton verMisOfertas = new JButton(OFFERS_SENT);
	
	/** boton para ver las ofertas entrantes */
	private JButton verOfertasEnt = new JButton(OFFERS_RECIEVED);
	
	/** boton para ver los intercambios pendientes */
	private JButton verIntercambios = new JButton(EXCHANGES);
	
	/** boton verCompras. Botón para ver el historial de compras realizadas. */
	private JButton verCompras = new JButton(COMPRAS);
	
	/** Constante MENU_WIDTH. Anchura del menú lateral como porcentaje de la pantalla. */
	private static final double MENU_WIDTH = 0.17;

	/**
	 * Instancia un nuevo Objeto BarraLateralCliente.
	 * Construye el menú lateral agrupando los botones por categorías (Comprar, Segunda mano, Pendientes).
	 */
	public BarraLateralCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		Map<String, List<JButton>> mapa = new TreeMap<>();
		
		mapa.put("Comprar", new ArrayList<JButton>(List.of(buscarProductos, verCarrito)));
		mapa.put("Segunda mano", new ArrayList<JButton>(List.of(buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verOfertasEnt)));
		mapa.put("Pendientes", new ArrayList<JButton>(List.of(verCompras, verIntercambios)));
		
		MenuLateral menu = new MenuLateral(mapa, MENU_WIDTH);
		
		add(menu);
	}

	/**
	 * Establece Controlador.
	 * Asigna el mismo controlador a todos los botones de la barra lateral.
	 *
	 * @param c controlador que manejará los eventos de los botones.
	 */
	@Override
	public void setControlador(ActionListener c) {
		for(JButton btn : new JButton[] {buscarProductos, verCarrito, buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verOfertasEnt, verIntercambios, verCompras}) {
			btn.addActionListener(c);
		}
	}
}