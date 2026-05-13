package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.assets.PanelMultiopcion;
import vistas.noRegistrado.VentanaResultadosNoRegistrado;

/**
 * Esta clase representa el controlador de la ventana que muestra los resultados de una búsqueda
 */
public class ControlResultadosNoRegistrado implements ActionListener, ControladorPantalla {
	/** Ventana que se muestra */
	private VentanaResultadosNoRegistrado vista;
	/** Resultados de la búsqueda */
	private Producto[] resultados;
	/** Modelo de la tienda sobre el que se actúa */
	private Tienda tienda;

	/**
	 * Constructor del controlador de la ventana de resultados
	 * @param tienda Modelo de la tienda
	 * @param productos Resultados de la búsqueda
	 */
	public ControlResultadosNoRegistrado(Tienda tienda, Producto[] productos) {
		this.vista = new VentanaResultadosNoRegistrado();
		vista.setControlador(this);
		this.resultados = productos;
		this.tienda = tienda;
		
		ordenar();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * Ordena los productos según un parámetro
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

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case PanelMultiopcion.CAMBIO_OPCION_ACTION:
			ordenar();
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes ver los resultados de una búsqueda. Para añadir productos al carrito, inicia sesión o registrate como cliente.";
	}
}
