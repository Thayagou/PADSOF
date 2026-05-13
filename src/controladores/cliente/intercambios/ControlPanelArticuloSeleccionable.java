package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.displays.PanelArticuloSeleccion;

/**
 * Controlador del panel de artículo seleccionable dentro de una oferta de intercambio.
 */
public class ControlPanelArticuloSeleccionable implements ActionListener {

	/** Campo panel. Panel de artículo seleccionable asociado a este controlador. */
	private PanelArticuloSeleccion panel;
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	@SuppressWarnings("unused")
	private Tienda tienda;
	
	/** Campo cliente. Cliente registrado que realiza la selección. */
	private ClienteRegistrado cliente;
	
	/** Campo articulo. Artículo de segunda mano que se puede seleccionar. */
	@SuppressWarnings("unused")
	private ArticuloSegundaMano articulo;
	
	/** Campo vista. Ventana de oferta donde se muestra el panel. */
	@SuppressWarnings("unused")
	private VentanaOfertaIntercambio vista;
	
	/** Constante FOTO_ARTICULO_DF. Ruta de la imagen por defecto del artículo. */
	private static final String FOTO_ARTICULO_DF = "articuloDefault.png";
	
	/** Constante actionName. Comando de acción para el clic sobre el artículo. */
	private static final String actionName = "clic";

	/**
	 * Instancia un nuevo Objeto ControlPanelArticuloSeleccionable.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que realiza la selección.
	 * @param articulo Artículo de segunda mano que se puede seleccionar.
	 * @param vista Ventana de oferta donde se añadirá el panel.
	 */
	public ControlPanelArticuloSeleccionable(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaOfertaIntercambio vista) {
		this.cliente = cliente;
		this.tienda = tienda;
		this.articulo = articulo;
		this.vista = vista;
		
		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			categorias.add(c.getNombre());
		}

		String estado;
		double estimacion;
		if (articulo.getValoracion() != null) {
			switch (articulo.getValoracion().getEstadoFisico()) {
			case PERFECTO:
				estado = "Perfecto";
				break;
			case MUY_BUENO:
				estado = "Muy bueno";
				break;
			case USO_LIGERO:
				estado = "Uso ligero";
				break;
			case USO_EVIDENTE:
				estado = "Uso evidente";
				break;
			case MUY_USADO:
				estado = "Muy usado";
				break;
			case DANADO:
				estado = "Dañado";
				break;
			case PENDIENTE:
				estado = "Pendiente de valoración";
				break;
			default:
				estado = "Error";
				break;
			}
			estimacion = articulo.getValoracion().getPrecioEstimado();
		} else {
			estado = "Sin valorar";
			estimacion = -1;
		}
		
		String foto;
		if(articulo.getImage() == null) foto = FOTO_ARTICULO_DF;
		else foto = articulo.getImage();

		this.panel = new PanelArticuloSeleccion(articulo.getNombre(), foto, articulo.getDescripcion(),
				articulo.getInteresadoEn(), estimacion, estado, actionName, categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		if(this.cliente.equals(articulo.getPropietario())) {
			vista.anadirMio(panel);
		} else {
			vista.anadirSuyo(panel);
		}
	}
	
	/**
	 * Comprueba si es Selected.
	 *
	 * @return true si es Selected, falso en caso contrario, indica si el artículo está marcado en la oferta.
	 */
	public boolean isSelected() {
		return panel.isSelected();
	}

	/**
	 * actionPerformed.
	 * Alterna la selección del artículo al hacer clic sobre él.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			panel.toggleSelection();
			break;
		}
	}
}