package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.venta.ControlPanelProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.cliente.general.BarraLateralCliente;
import vistas.cliente.general.BarraTareasCliente;
import vistas.cliente.general.VentanaInicioCliente;

public class ControlInicioCliente implements ActionListener, ControladorPantalla {

	@SuppressWarnings("unused")
	private Tienda tienda;
	private final VentanaInicioCliente vista;
	@SuppressWarnings("unused")
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
		tiendaFrame.resetearNavegacion(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {

		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
