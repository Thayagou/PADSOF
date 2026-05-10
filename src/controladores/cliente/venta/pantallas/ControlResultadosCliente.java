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

public class ControlResultadosCliente implements ControladorPantalla {
	
	private Tienda tienda;
	private Producto[] resultados;
	private VentanaResultadosNoRegistrado vista;

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

	@Override
	public JPanel getVista() {
		return vista;
	}
	
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
	public String getExplicacion() {
		return "Aquí se muestran los resultados de una búsqueda. Para añadir un producto al carrito, haz clic sobre él y luego pincha en \"Añadir al carrito\"";
	}
}
