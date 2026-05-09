package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.general.ControlBarraLateralCliente;
import controladores.cliente.general.ControlBarraTareasCliente;
import controladores.cliente.venta.ControlPanelProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.cliente.general.BarraLateralCliente;
import vistas.cliente.general.BarraTareasCliente;
import vistas.cliente.general.pantallas.VentanaInicioCliente;
import vistas.common.app.TiendaFrame;

/**
 * Tipo: Class ControlInicioCliente.
 */
public class ControlInicioCliente implements ControladorPantalla {

	/** Campo tienda. */
	@SuppressWarnings("unused")
	private Tienda tienda;
	
	/** Campo vista. */
	private final VentanaInicioCliente vista;
	
	/** Campo cliente. */
	private ClienteRegistrado cliente;

	/**
	 * Instancia un nuevo Objeto ControlInicioCliente.
	 *
	 * @param tienda parámetro tienda
	 * @param cliente parámetro cliente
	 */
	public ControlInicioCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

		ControlBarraTareasCliente ctrlBarraTareas = new ControlBarraTareasCliente(tienda, cliente);
		BarraTareasCliente barraTareas = new BarraTareasCliente(cliente.getNombre());
		barraTareas.setControlador(ctrlBarraTareas);
		tiendaFrame.setBarraTareas(barraTareas);
		
		ControlBarraLateralCliente ctrlBarraLateral = new ControlBarraLateralCliente(tienda, cliente);
		BarraLateralCliente barraLateral = new BarraLateralCliente();
		barraLateral.setControlador(ctrlBarraLateral);
		tiendaFrame.setBarraLateral(barraLateral);
		
		Producto[] populares = tienda.getAlmacen().getListaRecomendacion(cliente);
		this.vista = new VentanaInicioCliente();
		for(Producto p : populares) {
			new ControlPanelProductoCliente(tienda, cliente, p, vista);
		}
		vista.setControlador(this);
		tiendaFrame.resetearNavegacion(this);
	}

	/**
	 * actionPerformed.
	 *
	 * @param e parámetro e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			/* Sin acciones para esta ventana */
		}
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene la explicacion de la ventana.
	 *
	 * @return valor de Explicacion
	 */
	@Override
	public String getExplicacion() {
		return "¡Bienvenido " + cliente.getNombre() + "! Esta es la ventana de inicio. Aquí puedes ver los productos recomendados seleccionados para tí.";
	}
}
