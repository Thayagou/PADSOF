package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.exceptions.CustomException;
import modelo.exceptions.InvalidArgumentException;
import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.assets.VentanaMensaje;
import vistas.noRegistrado.VentanaBusqueda;

/**
 * Esta clase representa el controlador de la ventana de búsqueda
 */
public class ControlBuscar implements ActionListener, ControladorPantalla {
	/** Modelo de la tienda sobre el que se actúa */
	protected Tienda tienda;
	/** Ventana que se muestra */
	protected VentanaBusqueda vista;

	/**
	 * Constructor del controlador de la ventana de búsqueda
	 * @param tienda Modelo de la tienda
	 */
	public ControlBuscar(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaBusqueda(Arrays.stream(tienda.getAlmacen().getCategorias()).map(Categoria::getNombre).toArray(String[]::new));
		this.vista.setControlador(this);
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(VentanaBusqueda.BUSCAR_ACTION))
			this.intentarBusqueda();
	}
	
	/**
	 * Acción que se realiza al intentar hacer una búsqueda
	 */
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
			new ControlResultadosNoRegistrado(tienda, productos);
		} catch (CustomException ex) {
			new VentanaMensaje(ex.getMessage());
		} catch (NumberFormatException ex) {
			new VentanaMensaje("Introduce valores numéricos válidos");
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana se introducen los parámetros de búsqueda para encontrar productos.";
	}
}