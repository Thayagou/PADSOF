package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import modelo.exceptions.CustomException;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
import vistas.noRegistrado.VentanaBusqueda;

public class ControlBuscar implements ActionListener {
	protected Tienda tienda;
	protected VentanaBusqueda vista;

	public ControlBuscar(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaBusqueda(Arrays.stream(tienda.getAlmacen().getCategorias()).map(Categoria::getNombre).toArray(String[]::new));
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Buscar") || e.getActionCommand().equals("Entrar")) 
			this.intentarBusqueda();
	}
	
	protected void intentarBusqueda() {
		double eMin = vista.getEstrellas();
		double pMin = vista.getPrecioMin();
		double pMax = vista.getPrecioMax();
		String[] selected = vista.getCategoriasSeleccionadas();
		List<Categoria> categorias = new LinkedList<>();
		for (String cat : selected) {
			try {
				categorias.add(tienda.getAlmacen().getCategoria(cat));
			} catch (InvalidArgumentException e) {
				System.out.println(e);
			}
		}

		try {
			Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(categorias.toArray(new Categoria[0]),
					pMin, pMax, eMin);
			// Navegar a la vista de resultados con su controlador
			new ControlResultadosNoRegistrado(tienda, productos);
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage());
		} catch (NumberFormatException ex) {
			new VentanaMensaje("Introduce valores numéricos válidos");
		}
	}
}