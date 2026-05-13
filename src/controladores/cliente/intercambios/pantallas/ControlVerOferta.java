package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticuloEnOferta;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import modelo.wallapop.Intercambio;
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de visualización de una oferta de intercambio.
 */
public class ControlVerOferta implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza la oferta. */
	ClienteRegistrado cliente;
	
	/** Campo intercambio. Intercambio que se está visualizando. */
	Intercambio intercambio;
	
	/** Campo vista. Ventana de visualización de la oferta. */
	VentanaOfertaIntercambio vista;
	
	/** Constante BTN_ACCEPT. Comando de acción para el botón de aceptar oferta. */
	private static final String BTN_ACCEPT = "Aceptar";
	
	/** Constante BTN_REJECT. Comando de acción para el botón de rechazar oferta. */
	private static final String BTN_REJECT = "Rechazar";
	
	/** Constante BTN_CANCEL. Comando de acción para el botón de cancelar oferta. */
	private static final String BTN_CANCEL = "Cancelar";
	
	/**
	 * Instancia un nuevo Objeto ControlVerOferta.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza la oferta.
	 * @param intercambio Intercambio que se está visualizando.
	 */
	public ControlVerOferta(Tienda tienda, ClienteRegistrado cliente, Intercambio intercambio) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.intercambio = intercambio;
		
		if(!cliente.equals(intercambio.getEmisor().getDueno()))
			this.vista = new VentanaOfertaIntercambio(BTN_REJECT, BTN_ACCEPT);
		else 
			this.vista = new VentanaOfertaIntercambio(BTN_CANCEL);
		
		vista.setControlador(this);
		
		anadirArticulos(intercambio.getOfrecidos());
		anadirArticulos(intercambio.getSolicitados());
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * anadirArticulos.
	 * Añade los artículos del intercambio a la ventana de visualización.
	 *
	 * @param articulos parámetro articulos, array de artículos a añadir.
	 */
	private void anadirArticulos(ArticuloSegundaMano[] articulos) {
		for(ArticuloSegundaMano a : articulos)
			new ControlPanelArticuloEnOferta(tienda, this.cliente, a, vista);
	}

	/**
	 * actionPerformed.
	 * Gestiona las acciones de aceptar, rechazar o cancelar la oferta según el botón pulsado.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case BTN_ACCEPT:
			if(TiendaFrame.getConfirmacionUsuario("Una vez aceptes la oferta, tus objetos incluidos en ella desaparecerán de tu cartera.")) {
				try {
					tienda.aceptarIntercambio(cliente, intercambio);
					new VentanaMensaje("Has aceptado la oferta");
					TiendaFrame.getInstance().volverAtras();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		case BTN_REJECT:
			if(TiendaFrame.getConfirmacionUsuario("Una vez rechazada, esta oferta no volverá a aparecer entre tus ofertas.")) {
				try {
					tienda.rechazarIntercambio(cliente, intercambio);
					new VentanaMensaje("Has rechazado la oferta");
					TiendaFrame.getInstance().volverAtras();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		case BTN_CANCEL:
			if(TiendaFrame.getConfirmacionUsuario("¿Quieres cancelar la oferta?")) {
				try{
					tienda.cancelarIntercambio(cliente, intercambio);
					new VentanaMensaje("Has cancelado la oferta");
					TiendaFrame.getInstance().volverAtras();
				} catch(Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		}
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de visualización de la oferta.
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
		return "Aquí puedes ver la información de una oferta. Se muestran los artículos que se intercambiarán en caso de que la oferta sea aceptada.";
	}

}