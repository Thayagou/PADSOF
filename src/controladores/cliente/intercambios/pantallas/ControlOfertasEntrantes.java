package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;

/**
 * Controlador de la ventana de ofertas de intercambio entrantes (recibidas por el usuario).
 */
public class ControlOfertasEntrantes extends ControlVerMisOfertas {
	
	/**
	 * Instancia un nuevo Objeto ControlOfertasEntrantes.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que recibe las ofertas.
	 */
	public ControlOfertasEntrantes(Tienda tienda, ClienteRegistrado cliente) {
		super(tienda, cliente, "Ofertas de intercambio entrantes");
	}
	
	/**
	 * crearPaneles.
	 * Crea los paneles para cada oferta pendiente recibida por el usuario.
	 */
	@Override
	public void crearPaneles() {
		Intercambio[] ofertas = cliente.getCartera().getIntercambiosPendientesRecibidos();
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
	 * @return valor de Vista, el panel de la ventana de ofertas entrantes.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, instrucciones para gestionar las ofertas recibidas.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí se muestran las ofertas que usted ha recibido. Para aceptar/rechazar una oferta, haz clic sobre ella y continúa en la nueva ventana o usa los botones que se muestran a la derecha.";
	}

}