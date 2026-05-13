package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.cliente.intercambios.ControlPanelOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.Intercambio;
import vistas.cliente.intercambios.pantallas.VentanaVerMisOfertas;

/**
 * Controlador de la ventana de ofertas de intercambio realizadas por el usuario.
 */
public class ControlVerMisOfertas implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	protected Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza sus ofertas. */
	protected ClienteRegistrado cliente;
	
	/** Campo vista. Ventana de visualización de ofertas. */
	protected VentanaVerMisOfertas vista;
	
	/** Campo cabecera. Título de la ventana. */
	protected String cabecera;
	
	/**
	 * crearPaneles.
	 * Crea los paneles para cada oferta pendiente solicitada por el usuario.
	 */
	public void crearPaneles() {
		Intercambio[] ofertas = cliente.getCartera().getIntercambiosPendientesSolicitados();
		for(int i = ofertas.length - 1; i >= 0 ; i--) {
			new ControlPanelOferta(tienda, cliente, ofertas[i], vista, this);
		}
	}
	
	/**
	 * Instancia un nuevo Objeto ControlVerMisOfertas.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza sus ofertas.
	 */
	public ControlVerMisOfertas(Tienda tienda, ClienteRegistrado cliente) {
		this(tienda, cliente, "Ver mis ofertas de intercambio");
	}
	
	/**
	 * Instancia un nuevo Objeto ControlVerMisOfertas.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza sus ofertas.
	 * @param cabecera Título de la ventana.
	 */
	protected ControlVerMisOfertas(Tienda tienda, ClienteRegistrado cliente, String cabecera) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.cabecera = cabecera;
		
		vista = new VentanaVerMisOfertas(cabecera);
		vista.setControlador(this);
		
		crearPaneles();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * refrescar.
	 * Recarga la pantalla completa con los datos actualizados del cliente.
	 */
	public void refrescar() {
		vista = new VentanaVerMisOfertas(cabecera);
		vista.setControlador(this);
		
		crearPaneles();
		
		TiendaFrame.getInstance().recargarPantallaActual(this);
	}
	
	/**
	 * mostrar.
	 * Actualiza la vista cuando se muestra la pantalla.
	 */
	@Override
	public void mostrar() {
		vista = new VentanaVerMisOfertas(cabecera);
		vista.setControlador(this);
		
		crearPaneles();
		
		TiendaFrame.getInstance().refresh();
	}

	/**
	 * actionPerformed.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/* Sin acciones para esta ventana */
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de ofertas.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, instrucciones para gestionar las ofertas realizadas.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí se muestran las ofertas que usted ha realizado. Para cancelar una oferta, haz clic sobre ella y continúa en la nueva ventana o usa los botones que se muestran a la derecha.";
	}

}