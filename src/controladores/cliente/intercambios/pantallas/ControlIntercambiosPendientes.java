package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;

/**
 * Controlador de la ventana de intercambios pendientes de realizar.
 */
public class ControlIntercambiosPendientes extends ControlVerMisOfertas {
	
	/**
	 * Instancia un nuevo Objeto ControlIntercambiosPendientes.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza sus intercambios pendientes.
	 */
	public ControlIntercambiosPendientes(Tienda tienda, ClienteRegistrado cliente) {
		super(tienda, cliente, "Intercambios pendientes de realizar");
	}
	
	/**
	 * crearPaneles.
	 * Crea los paneles para cada intercambio pendiente aceptado.
	 */
	@Override
	public void crearPaneles() {
		Intercambio[] ofertas = cliente.getCartera().getIntercambiosAceptados();
		for(int i = ofertas.length - 1; i >= 0 ; i--) {
			new ControlPanelOferta(tienda, cliente, ofertas[i], vista, this);
		}
	}

	/**
	 * actionPerformed.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de intercambios pendientes.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, descripción de la funcionalidad de la ventana.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí se muestran los intercambios que han sido aceptados entre usted y otros usuarios que aún no han sido verificados por un empleado de la tienda.";
	}

}