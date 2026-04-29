package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.cliente.ControlBarraTareasCliente;
import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.*;
import vistas.cliente.BarraTareasCliente;
import vistas.noRegistrado.BarraNoRegistrado;
import vistas.noRegistrado.BarraTareasNoRegistrado;
import vistas.noRegistrado.VentanaInicioSinRegistrar;

public class ControlInicioSinRegistrar implements ActionListener {

	private Tienda tienda;
	private VentanaInicioSinRegistrar vista;

	public ControlInicioSinRegistrar(Tienda tienda) {
		this.tienda = tienda;
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

		// Barra lateral
		ControlBarraNoRegistrado ctrlBarraLateral = new ControlBarraNoRegistrado(tienda);
		BarraLateral barraLateral = new BarraNoRegistrado();
		barraLateral.setControlador(ctrlBarraLateral);
		tiendaFrame.setBarraLateral(barraLateral);

		// Barra de tareas superior
		ControlBarraTareasNoRegistrado ctrlBarraTareas = new ControlBarraTareasNoRegistrado(tienda);
		BarraTareasNoRegistrado barraTareas = new BarraTareasNoRegistrado();
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);

		// Obtener productos populares del modelo y construir la vista
		Producto[] populares = tienda.getAlmacen().getProductosCoincidentes("");
		this.vista = new VentanaInicioSinRegistrar(populares);
		this.vista.setClickListener(this);
		tiendaFrame.setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd != null && cmd.startsWith("Ver producto:")) {
			String nombreProducto = cmd.substring("Ver producto:".length());
			SwingUtilities.invokeLater(() -> {
				try {
					Producto producto = tienda.getAlmacen().getStock(nombreProducto).getProducto();
					new ControlProductoSinRegistrar(tienda, producto);
				} catch (Exception ex) {
					new VentanaMensaje("Producto no encontrado: " + nombreProducto);
				}
			});
		}
	}
}