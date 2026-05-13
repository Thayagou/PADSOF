package controladores.cliente.intercambios.pantallas;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.cliente.intercambios.pantallas.VentanaInfoArticulo;
import vistas.common.app.TiendaFrame;

/**
 * Controlador de la ventana de información detallada de un artículo de segunda mano.
 */
public class ControlInfoArticulo implements ControladorPantalla {
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza el artículo. */
	private ClienteRegistrado cliente;
	
	/** Campo articulo. Artículo de segunda mano que se está visualizando. */
	private ArticuloSegundaMano articulo;
	
	/** Campo vista. Ventana de información del artículo. */
	private VentanaInfoArticulo vista;
	
	/** Campo USER_PFP. Ruta de la imagen de perfil por defecto del usuario. */
	private final String USER_PFP = "pfp.png";
	
	/** Constante actionOffer. Comando de acción para el botón de hacer oferta. */
	private static final String actionOffer = "Hacer oferta";
	
	/** Constante actionWallet. Comando de acción para el botón de ver cartera. */
	private static final String actionWallet = "Ver cartera";

	/**
	 * Instancia un nuevo Objeto ControlInfoArticulo.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza el artículo.
	 * @param articulo Artículo de segunda mano que se está visualizando.
	 */
	public ControlInfoArticulo(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo) {
		this.tienda = tienda;
		this.cliente = cliente;
		this.articulo = articulo;
		
		ClienteRegistrado dueno = articulo.getPropietario();
		
		String estado;
		double estimacion;
		if(articulo.getValoracion() != null) {
			switch(articulo.getValoracion().getEstadoFisico()) {
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
		
		boolean ajeno;
		if(cliente == dueno) ajeno = false;
		else ajeno = true;
		
		ArrayList<String> listCategorias = new ArrayList<>();
		for(Categoria c : articulo.getCategorias()) {
			listCategorias.add(c.getNombre());
		}
		String[] categorias = listCategorias.toArray(new String[0]);
		
		this.vista = new VentanaInfoArticulo(dueno.getNombre(), USER_PFP, articulo.getNombre(), articulo.getImage(), articulo.getDescripcion(), articulo.getInteresadoEn(), estado, estimacion, ajeno, actionOffer, actionWallet, categorias);
		this.vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	
	/**
	 * actionPerformed.
	 * Gestiona las acciones de hacer oferta o ver la cartera del propietario.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionWallet:
			SwingUtilities.invokeLater(() -> new ControlManejoCartera(tienda, cliente, articulo.getPropietario()));
			break;
		case actionOffer:
			SwingUtilities.invokeLater(() -> new ControlHacerOferta(tienda, cliente, articulo.getPropietario()));
			break;
		}
		
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista, el panel de la ventana de información del artículo.
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
		return "Aquí se ve la información sobre un artículo de segunda mano.";
	}
	
}