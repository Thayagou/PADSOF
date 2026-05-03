package vistas.cliente;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.*;
import vistas.herramientas.*;

public class VentanaProductoCliente extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante REVIEWS_W_PERC. */
	private static final double REVIEWS_W_PERC = 0.33;
	
	private static final double COMPRAR_BTN_WIDTH = 0.1;

	private JButton comprar;

	/** Campo resenasPanel. */
	private JPanel resenasPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaProductoSinRegistrar.
	 *
	 * @param nombre          parámetro nombre
	 * @param descripcion     parámetro descripcion
	 * @param puntuacionMedia parámetro puntuacionMedia
	 * @param precio          parámetro precio
	 * @param categorias      parámetro categorias
	 */
	public VentanaProductoCliente(String nombre, String descripcion, String image, double puntuacionMedia, double precio,
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
		JPanel rightPanel = new PanelInfoProducto(nombre, descripcion, image, puntuacionMedia, precio, categorias);
		
		comprar = new ButtonFactory().newRoundedButton("Añadir al carrito", reviewsW, t.getPixelsWidth(COMPRAR_BTN_WIDTH), 1);
		comprar.setActionCommand("comprar");
		rightPanel.add(comprar);

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
	
	public void setControlador(ActionListener c) {
		comprar.addActionListener(c);
	}
}
