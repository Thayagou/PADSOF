package controladores.cliente.intercambios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladores.cliente.intercambios.pantallas.ControlInfoArticulo;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.Categoria;
import modelo.wallapop.ArticuloSegundaMano;
import vistas.common.displays.PanelArticulo;
import vistas.common.displays.VentanaConDisplay;

/**
 * Controlador del panel de visualización de un artículo de segunda mano.
 */
public class ControlPanelArticulo implements ActionListener {

	/** Campo panel. Panel de artículo asociado a este controlador. */
	private PanelArticulo panel;
	
	/** Campo articulo. Artículo de segunda mano asociado a este controlador. */
	private ArticuloSegundaMano articulo;
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza el artículo. */
	private ClienteRegistrado cliente;
	
	/** Campo vista. Contenedor donde se muestra el panel de artículo. */
	@SuppressWarnings("unused")
	private VentanaConDisplay<PanelArticulo> vista;

	/** Campo FOTO_PERFIL. Ruta de la imagen de perfil por defecto del propietario. */
	private final String FOTO_PERFIL = "pfp.png";
	
	/** Campo FOTO_ARTICULO_DF. Ruta de la imagen por defecto del artículo. */
	private final String FOTO_ARTICULO_DF = "articuloDefault.png";

	/** Constante actionName. Comando de acción para el clic sobre el artículo. */
	private static final String actionName = "clic";

	/**
	 * Instancia un nuevo Objeto ControlPanelArticulo.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza el artículo.
	 * @param articulo Artículo de segunda mano a mostrar.
	 * @param vista Contenedor donde se añadirá el panel de artículo.
	 */
	public ControlPanelArticulo(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaConDisplay<PanelArticulo> vista) {
		ClienteRegistrado dueno = articulo.getDueno().getDueno();
		this.articulo = articulo;
		this.tienda = tienda;
		this.vista = vista;
		this.cliente = cliente;

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

		panel = new PanelArticulo(dueno.getNombre(), FOTO_PERFIL, articulo.getNombre(), foto, articulo.getDescripcion(),
				articulo.getInteresadoEn(), estimacion, estado, actionName, categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}

	/**
	 * actionPerformed.
	 * Abre la ventana de información detallada del artículo al hacer clic.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().endsWith(actionName)) {
			SwingUtilities.invokeLater(() -> new ControlInfoArticulo(tienda, cliente, articulo));
		}

	}

}