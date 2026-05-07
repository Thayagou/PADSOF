package vistas.cliente.general;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import controladores.ControlBarraLateral;
import vistas.common.app.BarraLateral;
import vistas.common.app.MenuLateral;

public class BarraLateralCliente extends BarraLateral {

	private static final long serialVersionUID = 1L;

	private JButton buscarProductos = new JButton("Buscar productos");

	private JButton verCarrito = new JButton("Ver carrito");

	private JButton buscarArticulos = new JButton("Buscar artículos");

	private JButton verCartera = new JButton("Ver cartera");
	private JButton anadirArticulo = new JButton("Añadir artículo");
	private JButton verMisOfertas = new JButton("Ver mis ofertas");

	private JButton verCompras = new JButton("Ver mis compras");

	public BarraLateralCliente() {
		Map<String, List<JButton>> mapa = new TreeMap<>();
		
		mapa.put("Comprar", new ArrayList<JButton>(List.of(buscarProductos, verCarrito)));
		mapa.put("Segunda mano", new ArrayList<JButton>(List.of(buscarArticulos, verCartera, anadirArticulo, verMisOfertas)));
		mapa.put("Mis compras", new ArrayList<JButton>(List.of(verCompras)));
		
		setOpaque(false);
		setLayout(new GridLayout(1,1));
		
		add(new MenuLateral(mapa));
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		for(JButton btn : new JButton[] {buscarProductos, verCarrito, buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verCompras}) {
			btn.addActionListener(c);
		}
	}
}
