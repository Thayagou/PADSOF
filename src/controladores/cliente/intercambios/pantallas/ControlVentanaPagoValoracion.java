package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.venta.pantallas.VentanaPago;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de pago para solicitar una valoración de artículo de segunda mano.
 */
public class ControlVentanaPagoValoracion implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	Tienda tienda;
	
	/** Campo cliente. Cliente registrado que solicita la valoración. */
	ClienteRegistrado cliente;
	
	/** Campo articulo. Artículo de segunda mano a valorar. */
	ArticuloSegundaMano articulo;
	
	/** Campo vista. Ventana de pago asociada a este controlador. */
	VentanaPago vista;

	/**
	 * Instancia un nuevo Objeto ControlVentanaPagoValoracion.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que solicita la valoración.
	 * @param articulo Artículo de segunda mano a valorar.
	 */
	public ControlVentanaPagoValoracion(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.articulo = articulo;
		
		this.vista = new VentanaPago();
		this.vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de pago.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * actionPerformed.
	 * Gestiona el pago para solicitar la valoración del artículo.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaPago.PAY_ACTION:
			try{
				if(tienda.solicitarValoracion(cliente, articulo, vista.getNumeroTarjeta())) {
					TiendaFrame.getInstance().volverAtras();
					new VentanaMensaje("Su valoración ha sido solicitada con éxito");
				} else {
					new VentanaMensaje("Número de tarjeta inválido. Introduce un número de 16 dígitos", VentanaMensaje.ERROR);
				}
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
			break;
		}
	}
	
	/**
	 * puedeVolver.
	 * Indica si se puede volver a la pantalla anterior desde esta ventana.
	 *
	 * @return true si la operación fue correcta, falso en caso contrario
	 */
	@Override
	public boolean puedeVolver() {return false;}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, descripción de la funcionalidad de la ventana.
	 */
	@Override
	public String getExplicacion() {
		return "Introduce tus datos para realizar el pago y que el pequeño Timmy pueda comer hoy.";
	}

}