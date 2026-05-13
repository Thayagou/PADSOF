package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.cliente.intercambios.ControlPanelArticuloEnCartera;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaCartera;

/**
 * Controlador de la ventana de cartera de artículos de segunda mano.
 */
public class ControlManejoCartera implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza la cartera. */
	private ClienteRegistrado cliente;
	
	/** Campo dueno. Propietario de la cartera que se está visualizando. */
	private ClienteRegistrado dueno;
	
	/** Campo vista. Ventana de cartera asociada a este controlador. */
	private VentanaCartera vista;

	/**
	 * Instancia un nuevo Objeto ControlManejoCartera.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza la cartera.
	 * @param dueno Propietario de la cartera que se está visualizando.
	 */
	public ControlManejoCartera(Tienda tienda, ClienteRegistrado cliente, ClienteRegistrado dueno) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.dueno = dueno;
		
		if(dueno == cliente)
			this.vista = new VentanaCartera(null);
		else 
			this.vista = new VentanaCartera(dueno.getNombre());
		
		vista.setControlador(this);
		
		cargarArticulos();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * cargarArticulos.
	 * Carga los artículos del propietario en la vista, mostrando todos si es la propia cartera o solo los disponibles si es ajena.
	 */
	private void cargarArticulos() {
		vista.limpiarDisplays();
		
		if(cliente.equals(dueno)) {
			for(ArticuloSegundaMano a : dueno.getCartera().getArticulos()) {
				new ControlPanelArticuloEnCartera(tienda, cliente, a, vista);
			}
		} else {
			for(ArticuloSegundaMano a : dueno.getCartera().getArticulosDisponibles()) {
				new ControlPanelArticuloEnCartera(tienda, cliente, a, vista);
			}
		}
	}

	/**
	 * actionPerformed.
	 * Gestiona la acción de hacer una oferta al propietario de la cartera.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaCartera.OFFER_ACTION:
			SwingUtilities.invokeLater(() -> new ControlHacerOferta(tienda, cliente, dueno));
			break;
		}
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de cartera.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}
	
	/**
	 * mostrar.
	 * Recarga los artículos cada vez que se muestra la ventana.
	 */
	@Override
	public void mostrar() {
		cargarArticulos();
		TiendaFrame.getInstance().refresh();
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, descripción de la funcionalidad de la ventana.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí se muestran los artículos de una cartera. Puedes ver los objetos del dueño de la cartera.";
	}
}