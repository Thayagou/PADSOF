package vistas.cliente.general;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.JButton;

import controladores.ControlBarraLateral;
import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;

public class BarraLateralCliente extends BarraLateral {

	private static final long serialVersionUID = 1L;
	
	public static final String SEARCH_PRODUCTS = "Buscar Productos";
	public static final String SHOP_CAR = "Ver Carrito";
	public static final String SEARCH_ART = "Buscar Artículos";
	public static final String WALLET = "Ver Cartera";
	public static final String ADD_ART = "Añadir Artículo";
	public static final String OFFERS = "Ver mis ofertas";
	public static final String COMPRAS = "Ver mis compras";

	private JButton buscarProductos = new JButton(SEARCH_PRODUCTS);
	private JButton verCarrito = new JButton(SHOP_CAR);
	private JButton buscarArticulos = new JButton(SEARCH_ART);
	private JButton verCartera = new JButton(WALLET);
	private JButton anadirArticulo = new JButton(ADD_ART);
	private JButton verMisOfertas = new JButton(OFFERS);
	private JButton verCompras = new JButton(COMPRAS);
	
	private static final double MENU_WIDTH = 0.17;

	public BarraLateralCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		Map<String, List<JButton>> mapa = new TreeMap<>();
		
		mapa.put("Comprar", new ArrayList<JButton>(List.of(buscarProductos, verCarrito)));
		mapa.put("Segunda mano", new ArrayList<JButton>(List.of(buscarArticulos, verCartera, anadirArticulo, verMisOfertas)));
		mapa.put("Mis compras", new ArrayList<JButton>(List.of(verCompras)));
		
		MenuLateral menu = new MenuLateral(mapa, MENU_WIDTH);
		
		add(menu);
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		for(JButton btn : new JButton[] {buscarProductos, verCarrito, buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verCompras}) {
			btn.addActionListener(c);
		}
	}
}
