package vistas.cliente.general;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import controladores.ControlBarraLateral;
import vistas.common.BarraLateral;
import vistas.common.TiendaFrame;
import vistas.herramientas.ColorPalette;

public class BarraLateralCliente extends BarraLateral {

	private static final long serialVersionUID = 1L;

	private JButton buscarProductos;

	private JButton verCarrito;

	private JButton buscarArticulos;

	private JButton verCartera;
	private JButton anadirArticulo;
	private JButton verMisOfertas;

	private JButton verCompras;

	public BarraLateralCliente() {
		TiendaFrame frame = TiendaFrame.getInstance();
		int distFromLeft = frame.optionBarDistFromLeft();
		int distIndented = (int) (distFromLeft * 0.9);
		int btnHeight = frame.btnHeight();

		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setPreferredSize(new Dimension(distFromLeft, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		buscarProductos = addBtn("Buscar Productos", btnHeight, distFromLeft);

		verCarrito = addBtn("Ver Carrito", btnHeight, distFromLeft);

		buscarArticulos = addBtn("Buscar Artículos", btnHeight, distFromLeft);

		verCartera = addBtn("Ver Cartera", btnHeight, distFromLeft);
		anadirArticulo = addBtn("Añadir Artículo", btnHeight, distIndented);
		verMisOfertas = addBtn("Ver mis ofertas", btnHeight, distIndented);

		verCompras = addBtn("Ver mis compras", btnHeight, distFromLeft);
		
		for(JButton btn : new JButton[] {buscarProductos, verCarrito, buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verCompras}) {
			add(btn);
		}
	}

	@Override
	public void setControlador(ControlBarraLateral c) {
		for(JButton btn : new JButton[] {buscarProductos, verCarrito, buscarArticulos, verCartera, anadirArticulo, verMisOfertas, verCompras}) {
			btn.addActionListener(c);
		}
	}
}
