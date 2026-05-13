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
import vistas.cliente.intercambios.pantallas.VentanaOfertaIntercambio;
import vistas.common.displays.PanelArticulo;

/**
 * Controlador del panel de artículo dentro de una oferta de intercambio.
 */
public class ControlPanelArticuloEnOferta implements ActionListener {

	/** Campo panel. Panel de artículo asociado a este controlador. */
	private PanelArticulo panel;
	
	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo cliente. Cliente registrado que visualiza la oferta. */
	private ClienteRegistrado cliente;
	
	/** Campo articulo. Artículo de segunda mano incluido en la oferta. */
	private ArticuloSegundaMano articulo;
	
	/** Constante FOTO_ARTICULO_DF. Ruta de la imagen por defecto del artículo. */
	private static final String FOTO_ARTICULO_DF = "articuloDefault.png";
	
	/** Constante actionName. Comando de acción para el clic sobre el artículo. */
	private static final String actionName = "clic";

	/**
	 * Instancia un nuevo Objeto ControlPanelArticuloEnOferta.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza la oferta.
	 * @param articulo Artículo de segunda mano incluido en la oferta.
	 * @param vista Contenedor donde se añadirá el panel según si es del oferente o del receptor.
	 */
	public ControlPanelArticuloEnOferta(Tienda tienda, ClienteRegistrado cliente, ArticuloSegundaMano articulo,
			VentanaOfertaIntercambio vista) {
		this.cliente = cliente;
		this.tienda = tienda;
		this.articulo = articulo;
		
		ArrayList<String> cats = new ArrayList<>();
		for (Categoria c : articulo.getCategorias()) {
			cats.add(c.getNombre());
		}
		String[] categorias = cats.toArray(new String[0]);

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
		
		String nombre = articulo.getNombre();
		String descripcion = articulo.getDescripcion();
		String interesadoEn = articulo.getInteresadoEn();

		this.panel = new PanelArticulo(nombre, foto, descripcion, interesadoEn, estimacion, estado, actionName, categorias);
		panel.setControlador(this);
		
		if(this.cliente.equals(articulo.getPropietario())) {
			vista.anadirMio(panel);
		} else {
			vista.anadirSuyo(panel);
		}
	}

	/**
	 * actionPerformed.
	 * Abre la ventana de información detallada del artículo al hacer clic.
	 *
	 * @param e Evento de acción recibido.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case actionName:
			SwingUtilities.invokeLater(() -> new ControlInfoArticulo(tienda, cliente, articulo));
			break;
		}
	}
}