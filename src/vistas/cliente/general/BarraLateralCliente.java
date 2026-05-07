package vistas.cliente.general;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.JButton;

import controladores.ControlBarraLateral;
import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;

public class BarraLateralCliente extends BarraLateral {

	private static final long serialVersionUID = 1L;

	private JButton buscarProductos = new JButton("Buscar Productos");

	private JButton verCarrito = new JButton("Ver Carrito");

	private JButton buscarArticulos = new JButton("Buscar Artículos");

	private JButton verCartera = new JButton("Ver Cartera");
	private JButton anadirArticulo = new JButton("Añadir Artículo");
	private JButton verMisOfertas = new JButton("Ver mis ofertas");

	private JButton verCompras = new JButton("Ver mis compras");
	
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
