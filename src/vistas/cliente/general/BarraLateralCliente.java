package vistas.cliente.general;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;

import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;

/**
 * Tipo: Class BarraLateralCliente.
 */
public class BarraLateralCliente extends BarraLateral {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante SEARCH_PRODUCTS. */
	public static final String SEARCH_PRODUCTS = "Buscar Productos";
	
	/** Constante SHOP_CAR. */
	public static final String SHOP_CAR = "Ver Carrito";
	
	/** Constante SEARCH_ART. */
	public static final String SEARCH_ART = "Buscar Artículos";
	
	/** Constante WALLET. */
	public static final String WALLET = "Ver Cartera";
	
	/** Constante ADD_ART. */
	public static final String ADD_ART = "Añadir Artículo";
	
	/** Constante OFFERS. */
	public static final String OFFERS_SENT = "Ver mis ofertas";
	
	/** Action command para ir a la ventana de ofertas recividas */
	public static final String OFFERS_RECIEVED = "Ofertas Entrantes";
	
	/** Action command para ir a la ventana de intercambios pendientes */
	public static final String EXCHANGES = "Intercambios pendientes";
	
	/** Constante COMPRAS. */
	public static final String COMPRAS = "Ver mis compras";

	/** boton buscarProductos. */
	private JButton buscarProductos = new JButton(SEARCH_PRODUCTS);
	
	/** boton verCarrito. */
	private JButton verCarrito = new JButton(SHOP_CAR);
	
	/** boton buscarArticulos. */
	private JButton buscarArticulos = new JButton(SEARCH_ART);
	
	/** boton verCartera. */
	private JButton verCartera = new JButton(WALLET);
	
	/** boton anadirArticulo. */
	private JButton anadirArticulo = new JButton(ADD_ART);
	
	/** boton verMisOfertas. */
	private JButton verMisOfertas = new JButton(OFFERS_SENT);
	
	/** boton para ver las ofertas entrantes */
	private JButton verOfertasEnt = new JButton(OFFERS_RECIEVED);
	
	/** boton para ver los intercambios pendientes */
	private JButton verIntercambios = new JButton(EXCHANGES);
	
	/** boton verCompras. */
	private JButton verCompras = new JButton(COMPRAS);
	
	/** Constante MENU_WIDTH. */
	private static final double MENU_WIDTH = 0.17;

	/**
	 * Instancia un nuevo Objeto BarraLateralCliente.
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
	 *
	 * @param c nuevo valor
	 */
	@Override
	public void setControlador(ActionListener c) {
		for(JButton btn : new JButton[] {buscarProductos, verCarrito, buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verOfertasEnt, verIntercambios, verCompras}) {
			btn.addActionListener(c);
		}
	}
}
