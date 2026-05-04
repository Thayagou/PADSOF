package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.cliente.venta.ControlPanelProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.cliente.*;
import vistas.cliente.general.BarraLateralCliente;
import vistas.cliente.general.BarraTareasCliente;
import vistas.cliente.general.VentanaInicioCliente;

public class ControlInicioCliente implements ActionListener {

	private Tienda tienda;
	private VentanaInicioCliente vista;
	private ClienteRegistrado cliente;

	public ControlInicioCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

		// Barra de tareas superior
		ControlBarraTareasCliente ctrlBarraTareas = new ControlBarraTareasCliente(tienda, cliente);
		BarraTareasCliente barraTareas = new BarraTareasCliente(cliente.getNombre());
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);

		//Barra lateral
		
		ControlBarraLateralCliente ctrlBarraLateral = new ControlBarraLateralCliente(tienda, cliente);
		BarraLateralCliente barraLateral = new BarraLateralCliente();
		barraLateral.setControlador(ctrlBarraLateral);
		tiendaFrame.setBarraLateral(barraLateral);
		
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

		}
	}
}
