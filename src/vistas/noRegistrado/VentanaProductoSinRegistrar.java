package vistas.noRegistrado;

import java.awt.*;

import javax.swing.*;

import controladores.TiendaFrame;
import vistas.common.assets.PanelInfoProducto;
import vistas.common.displays.PanelResena;
import vistas.herramientas.*;

/**
 * Vista detallada de un producto (maqueta 5). Layout: izquierda = panel de
 * valoraciones/reseñas (scrolleable), derecha = foto grande + nombre +
 * categorías + precio + descripción.
 */
public class VentanaProductoSinRegistrar extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante REVIEWS_W_PERC. */
	private static final double REVIEWS_W_PERC = 0.33;

	/** Campo resenasPanel. */
	private JPanel resenasPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaProductoSinRegistrar.
	 *
	 * @param nombre          parámetro nombre
	 * @param descripcion     parámetro descripcion
	 * @param image parámetro image
	 * @param puntuacionMedia parámetro puntuacionMedia
	 * @param precio          parámetro precio
	 * @param caracteristicas parámetro caracteristicas
	 * @param categorias      parámetro categorias
	 */
	public VentanaProductoSinRegistrar(String nombre, String descripcion, String image, double puntuacionMedia, double precio, String caracteristicas,
			String... categorias) {
		TiendaFrame t = TiendaFrame.getInstance();

		resenasPanel.setLayout(new BoxLayout(resenasPanel, BoxLayout.Y_AXIS));
		resenasPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		setOpaque(false);
		setLayout(new BorderLayout());

		int reviewsW = t.getPixelsWidth(REVIEWS_W_PERC);

		/* Panel izquierdo - Valoraciones */
		JScrollPane scrollResenas = PanelFactory.getScroll(resenasPanel);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scrollResenas);

		JPanel resenas = PanelFactory.getVentanaConCabecera("Reseñas", contenido);
		resenas.setPreferredSize(new Dimension(reviewsW, 0));

		/* Panel derecho - Detalle del producto */
		JPanel rightPanel = new PanelInfoProducto(nombre, descripcion, image, puntuacionMedia, precio, caracteristicas, categorias);
		
		add(resenas, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}

	/**
	 * anadirPanelResena.
	 *
	 * @param puntuacion parámetro puntuacion
	 * @param comentario parámetro comentario
	 * @param usr        parámetro usr
	 */
	public void anadirPanelResena(double puntuacion, String comentario, String usr) {
		resenasPanel.add(new PanelResena(puntuacion, comentario, usr));
	}
}
