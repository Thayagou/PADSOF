package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Resena;
import vistas.cliente.venta.pantallas.VentanaAnadirResena;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de añadir reseña a un producto.
 */
public class ControlAnadirResena implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	Tienda tienda;
	
	/** Campo cliente. Cliente registrado que añade la reseña. */
	ClienteRegistrado cliente;
	
	/** Campo producto. Producto al que se añade la reseña. */
	Producto producto;
	
	/** Campo vista. Ventana de añadir reseña asociada a este controlador. */
	VentanaAnadirResena vista;
	
	/** Constante actionName. Comando de acción para el botón de enviar reseña. */
	private static final String actionName = "enviar";

	/**
	 * Instancia un nuevo Objeto ControlAnadirResena.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que añade la reseña.
	 * @param producto Producto al que se añade la reseña.
	 */
	public ControlAnadirResena(Tienda tienda, ClienteRegistrado cliente, Producto producto) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.producto = producto;
		
		this.vista = new VentanaAnadirResena(actionName);
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}

	/**
	 * actionPerformed.
	 * Gestiona el envío de la reseña del producto.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			try{
				producto.anadirResena(new Resena(vista.getValoracion(), vista.getComentario(), cliente));
				TiendaFrame.getInstance().volverAtras();
			} catch(Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
		}
		
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de añadir reseña.
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
		return "En esta ventana se introducen los datos para añadir una reseña a un producto. Las estrellas marcadas serán la puntuación que se envíe.";
	}
}