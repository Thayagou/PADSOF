package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelProductoCliente;
import controladores.noRegistrado.ControlPanelProductoNoRegistrado;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.noRegistrado.*;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelMultiopcion;

/**
 * Controlador de la ventana de resultados de búsqueda para clientes registrados.
 */
public class ControlResultadosCliente implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo resultados. Array de productos resultantes de la búsqueda. */
	private Producto[] resultados;
	
	/** Campo vista. Ventana de resultados no registrado asociada a este controlador. */
	private VentanaResultadosNoRegistrado vista;

	/**
	 * Instancia un nuevo Objeto ControlResultadosCliente.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que realiza la búsqueda.
	 * @param productos Array de productos resultantes de la búsqueda.
	 */
	public ControlResultadosCliente(Tienda tienda, ClienteRegistrado cliente, Producto[] productos) {
		this.tienda = tienda;
		this.vista = new VentanaResultadosNoRegistrado();
		this.vista.setControlador(this);
		this.resultados = productos;
		
		for(Producto p : productos) {
			new ControlPanelProductoCliente(tienda, cliente, p, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de resultados.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}
	
	/**
	 * ordenar.
	 * Ordena los productos según la opción seleccionada y actualiza la vista.
	 */
	private void ordenar() {
		Producto[] ordenados = Arrays.copyOf(resultados, resultados.length);
		switch (vista.getOpcionSeleccionada()) {
		case 0 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPuntuacionMedia).reversed());
		case 1 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPuntuacionMedia));
		case 2 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPrecio));
		case 3 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPrecio).reversed());
		case 4 -> Arrays.sort(ordenados, Comparator.comparing(Producto::getNombre));
		case 5 -> Arrays.sort(ordenados, Comparator.comparing(Producto::getNombre).reversed());
		}
		
		vista.vaciarLista();
		
		for(Producto p : ordenados) {
			new ControlPanelProductoNoRegistrado(tienda, p, vista);
		}
	}

	/**
	 * actionPerformed.
	 * Gestiona el cambio de opción en el panel de ordenación.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case PanelMultiopcion.CAMBIO_OPCION_ACTION:
			ordenar();
			break;
		}
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, instrucciones para añadir productos al carrito.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí se muestran los resultados de una búsqueda. Para añadir un producto al carrito, haz clic sobre él y luego pincha en \"Añadir al carrito\"";
	}
}