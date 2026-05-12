package vistas.common.displays;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFotoPerfil;
import vistas.common.components.FixedTextArea;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.Fonts;

/**
 * Subclase de PanelDisplay que usamos para mostrar los pedidos dentro de un scroll.
 */
public class PanelPedido extends PanelDisplay{

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje del panel utilizado para la foto. */
	private static final double FOTO_H_PERC = 0.99;
	
	/** Porcentaje de altura de pantalla que ocupa el panel. */
	private static final double MAX_HEIGHT = 0.16;
	
	/** Porcentaje de pantalla utilizado para el hueco entre componentes. */
	private static final double GAP_PERC = 0.1;
	
	/** Porcentaje de pantalla maximo para la descripcion del pedido */
	private static final double DESC_MAX_WIDTH = 0.4;
	
	/** Píxeles dejados de espacio */
	private int gap;
	
	/**
	 * Instancia un nuevo panel de pedido que se añadirá a una ventana .
	 *
	 * @param actionName Nombre de acción de pulsar el panel
	 * @param estado Estado del pedido
	 * @param productos String de los productos pedidos
	 */
	public PanelPedido(String actionName, String estado, String id, String...productos) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, actionName);
		gap = (int)(maxCompHeight *GAP_PERC);
		
		int descWidth = TiendaFrame.getInstance().getPixelsWidth(DESC_MAX_WIDTH);
		
		JPanel items = new JPanel();
		items.setOpaque(false);
		items.setLayout(new BorderLayout());
		
		/* Descripcion de los productos del pedido */
		String listaProductos = String.join("   ·   ", productos);
		FixedTextArea descripcion = new FixedTextArea(Fonts.truncar(listaProductos, descWidth, Fonts.TEXT.getFont(), items), Fonts.TEXT.getFont());
		items.add(descripcion, BorderLayout.CENTER);
		
		/* Estado del pedido + id */
		JPanel datos = new JPanel();
		datos.setLayout(new BoxLayout(datos, BoxLayout.X_AXIS));
		datos.setOpaque(false);
		datos.add(ButtonFactory.newLeftAlignedLabel("Código de pedido: " + id, Fonts.BOLD));
		datos.add(ButtonFactory.newLeftAlignedLabel("Estado: "+estado, Fonts.BOLD));
		
		add(items, BorderLayout.CENTER);
		add(datos, BorderLayout.SOUTH);
	}
	
	/**
	 * Instancia un nuevo panel de pedido que se añadirá a una ventana .
	 *
	 * @param actionName Nombre de acción de pulsar el panel
	 * @param estado Estado del pedido
	 * @param productos String de los productos pedidos
	 * @param nombreCliente Nombre del cliente que ha hecho el pedido
	 * @param imageName Nombre de la imagen de perfil del cliente
	 */
	public PanelPedido(String nombreCliente, String estado, String imageName, String id, String actionName, String...productos) {
		this(actionName, estado, id, productos);
		
		int fotoSize = maxCompHeight/2 - 2*gap;
		
		PanelFotoPerfil perfilEmisor = new PanelFotoPerfil(imageName, fotoSize);
		JLabel labelEmisor = ButtonFactory.newLabel(nombreCliente, Fonts.BOLD);

		JPanel emisor = new JPanel();
		emisor.setOpaque(false);
		emisor.setLayout(new BoxLayout(emisor, BoxLayout.X_AXIS));
		
		emisor.add(perfilEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		emisor.add(labelEmisor);
		emisor.add(Box.createHorizontalStrut(gap));
		
		add(emisor, BorderLayout.WEST);
	}
	
	
}
