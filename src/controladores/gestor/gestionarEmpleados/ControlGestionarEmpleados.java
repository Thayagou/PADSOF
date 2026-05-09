package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.gestor.gestionarEmpleados.VentanaGestionarEmpleados;

/**
 * Clase controladora de la vista correspondiente a.la gestión de los empleados de la tienda
 */
public class ControlGestionarEmpleados  implements ControladorPantalla{
	
	/** Tienda sobre la que se actúa y muestran datos. */
	private Tienda tienda;
	
	/** Gestor de la tienda sobre la que estamos actuando. */
	private Gestor gestor;
	
	/** Vista que muestra el controlador por pantalla. */
	private VentanaGestionarEmpleados vista;
	
	/**
	 * Instancia un nuevo Controlador ControlGestionarEmpleados, que crea la vista y todos los paneles asociados a cada uno de los empleados.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param gestor Gestor de la tienda sobre la que estamos actuando.
	 */
	public ControlGestionarEmpleados(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.vista = new VentanaGestionarEmpleados();
		
		cargarVista();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * Carga la información de los empleados por pantalla
	 */
	private void cargarVista() {
		// Panel para crear nuevo empleado
		new ControlPanelNuevoEmpleado(tienda, gestor, vista);
		
		// Paneles individuales de cada empleado
		for (Empleado e: tienda.getTodosEmpleados()) {
			new ControlPanelEmpleadoGestionar(tienda, e, vista);
		}
	}
	
	/**
	 * Método que se invoca para volver atrás correctamente y que se muestren los cambios reales
	 */
	@Override
	public void mostrar() {
		vista.vaciarLista();
		cargarVista();
		vista.refreshList();
	}

	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 *En este caso este controlador sirve para colocar los paneles por pantalla por lo que no realiza acciones
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

	/**
	 * Getter de la vista que controla este controlador.
	 *
	 * @return JPanel de la vista
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Getter de la información que se muestra al consultar la ayuda.
	 *
	 * @return the explicacion
	 */
	@Override
	public String getExplicacion() {
		return "En esta ventana se muestran los empleados de la tienda, permitiendo asignarle los permisos deseados y darles de alta o baja"; 
	}

}
