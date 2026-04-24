package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import modelo.exceptions.CustomException;
import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.*;

public class ControlBuscar implements ActionListener {
	private Tienda tienda;
	private VentanaBusqueda vista;
	private TiendaFrame frame;

	public ControlBuscar(Tienda tienda, TiendaFrame frame) {
		this.tienda = tienda;
		this.vista = new VentanaBusqueda(Arrays.stream(tienda.getAlmacen().getCategorias()).map(Categoria::getNombre).toArray(String[]::new));
		this.vista.setControlador(this);

		this.frame = frame;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(vista);
		this.frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Entrar"))
			this.intentarBusqueda();
	}
	
	private void intentarBusqueda() {
		double eMin = vista.getEstrellas();
		double pMin = vista.getPrecioMin();
		double pMax = vista.getPrecioMax();
		Boolean[] selected = vista.getCategorias();
		List<Categoria> categorias = new LinkedList<>();
		//for(Categoria c : tienda.getAlmacen().getCategorias()) {
			//if(selected)
		//}

		/*try {
			Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(selected.toArray(new Categoria[0]), pMin, pMax, eMin);
			// AÑADIR AL CARD_LAYOUT
			// LUEGO MANDAMOS A VENTANA QUE DEJE COMPRAR??
			new VentanaResultados(tienda, productos);
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage());
		} catch (NumberFormatException ex) {
			new VentanaMensaje("Introduce valores numéricos válidos");
		}*/
	}

}
