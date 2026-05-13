package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.cliente.general.ControlBarraLateralCliente;
import controladores.cliente.general.ControlBarraTareasCliente;
import controladores.cliente.venta.ControlPanelProductoCliente;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import vistas.cliente.general.BarraLateralCliente;
import vistas.cliente.general.BarraTareasCliente;
import vistas.cliente.general.pantallas.VentanaInicioCliente;

/**
 * Controlador de la pantalla de inicio del cliente, gestiona la configuración de las barras y la carga de productos recomendados.
 */
public class ControlInicioCliente implements ControladorPantalla {

	/** Campo tienda. Referencia al modelo de la tienda. */
	@SuppressWarnings("unused")
	private Tienda tienda;
	
	/** Campo vista. Ventana de inicio asociada a este controlador. */
	private final VentanaInicioCliente vista;
	
	/** Campo cliente. Cliente registrado que ha iniciado sesión. */
	private ClienteRegistrado cliente;

	/**
	 * Instancia un nuevo Objeto ControlInicioCliente.
	 * Configura las barras de tareas y lateral, carga los productos recomendados y establece la navegación.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que ha iniciado sesión.
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
	 * Gestiona los eventos de acción de la ventana (actualmente sin acciones implementadas).
	 *
	 * @param e Evento de acción recibido.
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
	 * @return valor de Vista, el panel de la ventana de inicio.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene la explicacion de la ventana.
	 *
	 * @return valor de Explicacion, mensaje de bienvenida personalizado para el cliente.
	 */
	@Override
	public String getExplicacion() {
		return "¡Bienvenido, " + cliente.getNombre() + "! Esta es la ventana de inicio. Aquí puedes ver los productos recomendados seleccionados para tí.";
	}
}