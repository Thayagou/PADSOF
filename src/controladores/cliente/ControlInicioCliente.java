package controladores.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.noRegistrado.*;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.cliente.BarraTareasCliente;
import vistas.common.TiendaFrame;
import vistas.cliente.*;

public class ControlInicioCliente implements ActionListener {

	private Tienda tienda;
	private VentanaInicioCliente vista;

	public ControlInicioCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

		// Barra de tareas superior
		ControlBarraTareasCliente ctrlBarraTareas = new ControlBarraTareasCliente(tienda, cliente);
		BarraTareasCliente barraTareas = new BarraTareasCliente(cliente.getNombre());
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);

		//Barra lateral vacía
		tiendaFrame.removeBarraLateral();
		
		// Obtener productos populares del modelo y construir la vista
		Producto[] populares = tienda.getAlmacen().getListaRecomendacion(cliente);
		this.vista = new VentanaInicioCliente();
		for(Producto p : populares) {
			new ControlPanelProductoCliente(tienda, cliente, p, vista);
		}
		vista.setControlador(this);
		tiendaFrame.setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "cartera":
			SwingUtilities.invokeLater(() -> new ControlManejoCartera(tienda));
		case "articulos":
			SwingUtilities.invokeLater(() -> new ControlBuscarSegundaMano(tienda));
		case "compras":
			SwingUtilities.invokeLater(() -> new ControlVerCompras(tienda));
		}
	}
}
