package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelInfoProducto;
import vistas.common.displays.PanelResena;
import vistas.herramientas.*;

/**
 * Tipo: Class VentanaProductoCliente.
 */
public class VentanaProductoCliente extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante REVIEWS_W_PERC. */
	private static final double REVIEWS_W_PERC = 0.33;

	/** Constante COMPRAR_BTN_WIDTH. */
	private static final double COMPRAR_BTN_WIDTH = 0.2;
	
	/** Constante COMPRAR_BTN_HEIGHT. */
	private static final double COMPRAR_BTN_HEIGHT = 0.06;
	
	/** Constante BUY_ACTION. */
	public static final String BUY_ACTION = "comprar";

	/** boton comprar. */
	private JButton comprar;

	/** boton resenasPanel. */
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
	 */
	private void refreshList() {
		resenasPanel.revalidate();
		resenasPanel.repaint();
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
		refreshList();
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		comprar.addActionListener(c);
	}
}
