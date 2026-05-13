package controladores.cliente.venta.pantallas;

import java.util.LinkedList;
import java.util.List;

import controladores.noRegistrado.ControlBuscar;
import modelo.exceptions.*;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de búsqueda de productos para clientes registrados.
 */
public class ControlBuscarCliente extends ControlBuscar {
	
	/** Campo cliente. Cliente registrado que realiza la búsqueda. */
	private ClienteRegistrado cliente;

	/**
	 * Instancia un nuevo Objeto ControlBuscarCliente.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que realiza la búsqueda.
	 */
	public ControlBuscarCliente(Tienda tienda, ClienteRegistrado cliente) {
		super(tienda);
		
		this.cliente = cliente;
	}

	/**
	 * intentarBusqueda.
	 * Realiza la búsqueda de productos aplicando los filtros seleccionados por el cliente.
	 */
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
				new VentanaMensaje(e.getMessage(), VentanaMensaje.ERROR);
			}
		}

		try {
			Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(cliente, categorias.toArray(new Categoria[0]),
					pMin, pMax, eMin);
			new ControlResultadosCliente(tienda, cliente, productos);
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
		} catch (NumberFormatException ex) {
			new VentanaMensaje("Introduce valores numéricos válidos", VentanaMensaje.ERROR);
		}
	}
}