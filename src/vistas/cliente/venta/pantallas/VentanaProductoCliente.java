package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelInfoProducto;
import vistas.common.displays.PanelResena;
import vistas.herramientas.*;

/**
 * Pantalla de detalle de producto para clientes registrados, con información del producto, reseñas y botón de compra.
 */
public class VentanaProductoCliente extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante REVIEWS_W_PERC. Anchura del panel de reseñas como porcentaje de la pantalla. */
	private static final double REVIEWS_W_PERC = 0.33;

	/** Constante COMPRAR_BTN_WIDTH. Anchura del botón de compra como porcentaje de la pantalla. */
	private static final double COMPRAR_BTN_WIDTH = 0.2;
	
	/** Constante COMPRAR_BTN_HEIGHT. Altura del botón de compra como porcentaje de la pantalla. */
	private static final double COMPRAR_BTN_HEIGHT = 0.06;
	
	/** Constante BUY_ACTION. Comando de acción para el botón de añadir al carrito. */
	public static final String BUY_ACTION = "comprar";

	/** boton comprar. Botón para añadir el producto al carrito de compras. */
	private JButton comprar;

	/** boton resenasPanel. Panel que contiene las reseñas del producto. */
	private JPanel resenasPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaProductoSinRegistrar.
	 * Construye la interfaz con el panel de reseñas a la izquierda y los detalles del producto a la derecha.
	 *
	 * @param nombre          Nombre del producto.
	 * @param descripcion     Descripción del producto.
	 * @param image           Ruta de la imagen del producto.
	 * @param puntuacionMedia Puntuación media del producto (0-5).
	 * @param precio          Precio del producto en euros.
	 * @param caracteristicas Características destacadas del producto.
	 * @param categorias      Categorías a las que pertenece el producto.
	 */
	public VentanaProductoCliente(String nombre, String descripcion, String image, double puntuacionMedia,
			double precio, String caracteristicas, String... categorias) {
		TiendaFrame t = TiendaFrame.getInstance();

		resenasPanel.setLayout(new BoxLayout(resenasPanel, BoxLayout.Y_AXIS));
		resenasPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		setOpaque(false);
		setLayout(new BorderLayout());

		int reviewsW = t.getPixelsWidth(REVIEWS_W_PERC);

		/* Panel izquierdo - Valoraciones */
		JScrollPane scrollResenas = PanelFactory.getScroll(resenasPanel);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scrollResenas, BorderLayout.CENTER);

		JPanel resenas = PanelFactory.getVentanaConCabecera("Reseñas", contenido);
		resenas.setPreferredSize(new Dimension(reviewsW, 0));

		/* Panel derecho - Detalle del producto */
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(BorderLayout.CENTER, new PanelInfoProducto(nombre, descripcion, image, puntuacionMedia, precio, caracteristicas, categorias));

		comprar = ButtonFactory.newRoundedButton("Añadir al carrito", t.getPixelsHeight(COMPRAR_BTN_HEIGHT),
				t.getPixelsWidth(COMPRAR_BTN_WIDTH), 1);
		comprar.setActionCommand(BUY_ACTION);
		comprar.setAlignmentX(CENTER_ALIGNMENT);

		JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		wrapper.setOpaque(true);
		wrapper.setBackground(ColorPalette.WHITE.getColor());
		wrapper.add(comprar);
		rightPanel.add(wrapper, BorderLayout.SOUTH);

		add(resenas, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		
		refreshList();
	}
	
	/**
	 * refreshList.
	 * Refresca la interfaz para mostrar los cambios en el panel de reseñas.
	 */
	private void refreshList() {
		resenasPanel.revalidate();
		resenasPanel.repaint();
	}

	/**
	 * anadirPanelResena.
	 * Añade una nueva reseña al panel de reseñas y refresca la vista.
	 *
	 * @param puntuacion Puntuación asignada (0-5).
	 * @param comentario Comentario escrito por el usuario.
	 * @param usr        Nombre del usuario que realiza la reseña.
	 */
	public void anadirPanelResena(double puntuacion, String comentario, String usr) {
		resenasPanel.add(new PanelResena(puntuacion, comentario, usr));
		refreshList();
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c controlador que manejará los eventos del botón de compra.
	 */
	public void setControlador(ActionListener c) {
		comprar.addActionListener(c);
	}
}