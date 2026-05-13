package vistas.empleado.gestionarProductos.gestionarExistentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controladores.TiendaFrame;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa un panel con opciones de gestionar un producto
 */
public class PanelProductoGestionarProducto extends PanelProducto {
	private static final long serialVersionUID = 1L;
	/** Nombre de la acción asociada a modificar */
	public static final String MODIFICAR_ACTION = "Modificar";
	/** Nombre de la acción asociada a borrar */
	public static final String BORRAR_ACTION = "Borrar";
	/** Ancho en pixeles de los botones de la derecha */
	public static final int BTN_WIDTH = TiendaFrame.getInstance().getPixelsWidth(0.1);
	/** Botón de modificar */
	private JButton modButton;
	/** Botón de borrar */
	private JButton borrarButton;
	
	/**
	 * Constructor de un panel de gestionar un producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param imageName Imagen del producto
	 * @param puntuacionMedia Puntuación media del producto
	 * @param precio Precio del producto
	 * @param categorias Categorías del producto
	 */
	public PanelProductoGestionarProducto(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String...categorias) {
		super(nombre, descripcion, imageName, puntuacionMedia, precio, "", categorias);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setOpaque(false);
		
		modButton = ButtonFactory.newRoundedButton(MODIFICAR_ACTION, (int)(maxCompHeight), BTN_WIDTH, 0.5f);
		modButton.setMaximumSize(new Dimension(BTN_WIDTH, (int)(maxCompHeight* BOTON_PERC_H)));
		ButtonFactory.paintButton(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(modButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		
		borrarButton = ButtonFactory.newRoundedButton(BORRAR_ACTION, (int)(maxCompHeight), BTN_WIDTH, 0.5f);
		borrarButton.setMaximumSize(new Dimension(BTN_WIDTH, (int)(maxCompHeight* BOTON_PERC_H)));
		ButtonFactory.paintButton(borrarButton, ColorPalette.CARD_DARK, ColorPalette.DARK_GREY);
		ButtonFactory.addHoverColorChange(borrarButton);
		
		int gapSize = (int) (maxCompHeight * (1 - 2*BOTON_PERC_H) / 3);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(modButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		eastPanel.add(borrarButton);
		eastPanel.add(Box.createVerticalStrut(gapSize));
		
		this.add(PanelFactory.wrapHorizontal(eastPanel, gapSize), BorderLayout.EAST);
	}
	
	/**
	 * Asigna un controlador a los botones del panel
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		super.setControlador(c);
		modButton.addActionListener(c);
		borrarButton.addActionListener(c);
	}
}
