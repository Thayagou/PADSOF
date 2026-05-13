package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.intercambios.ControlPanelArticuloSeleccionable;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador para crear una nueva oferta de intercambio entre dos usuarios.
 */
public class ControlHacerOferta implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	Tienda tienda;
	
	/** Campo cliente. Usuario que realiza la oferta. */
	ClienteRegistrado cliente;
	
	/** Campo receptor. Usuario que recibe la oferta. */
	ClienteRegistrado receptor;
	
	/** Campo vista. Ventana de selección de artículos para la oferta. */
	VentanaOfertaIntercambio vista;
	
	/** Campo articulos. Mapa que agrupa los artículos disponibles por cada usuario. */
	Map<ClienteRegistrado, List<ArticuloSegundaMano>> articulos = new HashMap<>();
	
	/** Campo controladores. Mapa que asocia cada artículo con su controlador de selección. */
	Map<ArticuloSegundaMano, ControlPanelArticuloSeleccionable> controladores = new HashMap<>();
	
	/** Constante BTN_CONFIRM. Comando de acción para el botón de confirmar. */
	private static final String BTN_CONFIRM = "Confirmar";
	
	/** Constante BTN_CANCEL. Comando de acción para el botón de cancelar. */
	private static final String BTN_CANCEL = "Cancelar";
	
	/**
	 * Instancia un nuevo Objeto ControlHacerOferta.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Usuario que realiza la oferta.
	 * @param receptor Usuario que recibe la oferta.
	 */
	public ControlHacerOferta(Tienda tienda, ClienteRegistrado cliente, ClienteRegistrado receptor) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.receptor = receptor;
		
		this.vista = new VentanaOfertaIntercambio(BTN_CANCEL, BTN_CONFIRM);
		vista.setControlador(this);
		
		articulos.put(cliente, new ArrayList<>());
		articulos.put(receptor, new ArrayList<>());
		
		anadirArticulos(cliente);
		anadirArticulos(receptor);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * anadirArticulos.
	 * Añade todos los artículos disponibles de un usuario a la ventana de selección.
	 *
	 * @param cliente Usuario cuyos artículos se añadirán.
	 */
	private void anadirArticulos(ClienteRegistrado cliente) {
		for(ArticuloSegundaMano a : cliente.getCartera().getArticulosDisponibles()) {
			ControlPanelArticuloSeleccionable c = new ControlPanelArticuloSeleccionable(tienda, this.cliente, a, vista);
			articulos.get(cliente).add(a);
			controladores.put(a, c);
		}
	}
	
	/**
	 * Obtiene ArticulosSeleccionados.
	 * Recupera los artículos seleccionados por un usuario en la ventana de oferta.
	 *
	 * @param cliente Usuario del que se quieren obtener los artículos seleccionados.
	 * @return valor de ArticulosSeleccionados, array con los artículos marcados.
	 */
	private ArticuloSegundaMano[] getArticulosSeleccionados(ClienteRegistrado cliente) {
		ArrayList<ArticuloSegundaMano> array = new ArrayList<>();
		
		for(ArticuloSegundaMano a : articulos.get(cliente)) {
			if(controladores.get(a).isSelected()) array.add(a);
		}
		return array.toArray(new ArticuloSegundaMano[0]);
	}

	/**
	 * actionPerformed.
	 * Gestiona la confirmación o cancelación de la creación de la oferta.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case BTN_CONFIRM:
			if(TiendaFrame.getConfirmacionUsuario("Cuando envíes la oferta tus objetos incluidos en ella quedarán bloqueados y no podrás incluirlos en nuevas ofertas ni podrán hacerte ofertas con ellos. Podrás cancelar la oferta más tarde si aún no ha sido aceptada.")) {
				try {
					tienda.hacerOfertaIntercambio(cliente, getArticulosSeleccionados(cliente), getArticulosSeleccionados(receptor));
					new VentanaMensaje("Su oferta se ha enviado con éxito");
					TiendaFrame.getInstance().volverAtras();
				} catch (Exception ex) {
					new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
				}
			}
			break;
		case BTN_CANCEL:
			TiendaFrame.getInstance().volverAtras();
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
	public boolean puedeVolver() {
		return false;
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de oferta.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene Explicacion.
	 *
	 * @return valor de Explicacion, instrucciones para usar la ventana.
	 */
	@Override
	public String getExplicacion() {
		return "En esta ventana se seleccionan los artículos para incluir en una oferta. Se muestran a un lado tus objetos y al otro los del otro usuario. Para incluir un artículo en la oferta, haz clic sobre un artículo que no esté seleccionado ya. Para sacar un artículo de la oferta, vuelve a clicar sobre un objeto seleccionado.";
	}

}