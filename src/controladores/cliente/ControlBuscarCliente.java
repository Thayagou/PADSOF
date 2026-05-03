package controladores.cliente;

import java.util.LinkedList;
import java.util.List;

import controladores.noRegistrado.ControlBuscar;
import modelo.exceptions.*;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.VentanaMensaje;

public class ControlBuscarCliente extends ControlBuscar {
	
	private ClienteRegistrado cliente;

	public ControlBuscarCliente(Tienda tienda, ClienteRegistrado cliente) {
		super(tienda);
		
		this.cliente = cliente;
	}

	@Override
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
				new VentanaMensaje(e.getMessage());
			}
		}

		try {
			Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(cliente, categorias.toArray(new Categoria[0]),
					pMin, pMax, eMin);
			new ControlResultadosCliente(tienda, cliente, productos);
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage());
		} catch (NumberFormatException ex) {
			new VentanaMensaje("Introduce valores numéricos válidos");
		}
	}
}
