package vistas.common;

import java.awt.*;

import javax.swing.*;
import vistas.herramientas.*;

/**
 * Fila de producto para las listas de resultados / productos populares.
 * Muestra: foto, estrellas, nombre, descripción truncada, precio y categorías.
 * Al hacer clic sobre la fila se puede asignar un ActionListener externo.
 */
public class PanelProducto extends PanelDisplay {
	private static final long serialVersionUID = 1L;

	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.99;
	private static final double MAX_HEIGHT = 0.16;
	
	private static final double NAME_WIDTH = 0.5;
	private static final double DESC_WIDTH = NAME_WIDTH + 0.058;
	private static final int DESC_MAX_LINES = 3;
	
	private String nombre;
	private String descripcion;
	private double puntuacionMedia;
	private double precio;
	
	public PanelProducto(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String actionName, String...categorias) {
		super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT, FOTO_W_PERC, imageName, actionName);
		
		this.puntuacionMedia = puntuacionMedia;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.precio = precio;
		
		TiendaFrame t = TiendaFrame.getInstance();
		/* Info: estrellas + nombre + descripción + precio + categorías */
		JPanel info = new JPanel();
		info.setOpaque(false);
		info.setLayout(new BorderLayout());
		
		/*Primera fila: estrellas + nombre */
		JPanel firstRow = new JPanel();
		firstRow.setOpaque(false);
		firstRow.setLayout(new BorderLayout(10, 0));
		firstRow.add(buildEstrellas(t, puntuacionMedia), BorderLayout.WEST);

		JLabel nombreLabel = new JLabel();
		nombreLabel.setText(Fonts.truncar(nombre, t.getPixelsWidth(NAME_WIDTH), Fonts.BOLD.getFont(), nombreLabel));
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		firstRow.add(nombreLabel, BorderLayout.CENTER);

		info.add(firstRow, BorderLayout.NORTH);
		
		/*Segunda fila: descripcion*/
		JTextArea descripcionLabel = new FixedTextArea();
		descripcionLabel.setFont(Fonts.SMALL.getFont());
		descripcionLabel.setSize(t.getPixelsWidth(DESC_WIDTH), Short.MAX_VALUE);
		descripcionLabel.setText(Fonts.truncar(descripcion, t.getPixelsWidth(DESC_WIDTH)*DESC_MAX_LINES, Fonts.SMALL.getFont(), descripcionLabel));
		descripcionLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		
		info.add(descripcionLabel, BorderLayout.CENTER);
		
		/*Tercera fila: categorias + precio*/
		JPanel thirdRow = new JPanel();
		thirdRow.setLayout(new BorderLayout(10, 0));
		thirdRow.setOpaque(false);
		
		String cats = String.join(", ", categorias);
		
		if (!cats.isEmpty()) {
			JLabel categoriasLabel = new JLabel(cats);
			categoriasLabel.setFont(Fonts.TEXT.getFont());
			categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
			thirdRow.add(categoriasLabel, BorderLayout.WEST);
		}
		
		JLabel precioLabel = new JLabel(String.format("%.2f €", precio));
		precioLabel.setFont(Fonts.BOLD.getFont());
		precioLabel.setForeground(Color.BLACK);
		thirdRow.add(precioLabel, BorderLayout.CENTER);
		
		info.add(thirdRow, BorderLayout.SOUTH);
		
		add(info, BorderLayout.CENTER);
	}
	
	public PanelProducto(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String actionName, String mensajeDescuento, String...categorias) {
		this(nombre, descripcion, imageName, puntuacionMedia, precio, actionName, categorias);
		anadirDescuento(mensajeDescuento);
	}

	private JPanel buildEstrellas(TiendaFrame t, double valoracion) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
		p.setOpaque(false);
		int llenas = (int) Math.round(valoracion);
		for (int i = 1; i <= 5; i++) {
			JLabel star = new JLabel("★");
			star.setFont(Fonts.BOLD.getFont());
			star.setForeground(i <= llenas ? ColorPalette.YELLOW.getColor() : ColorPalette.LIGHT_GREY.getColor());
			p.add(star);
		}
		return p;
	}
	
	public void anadirDescuento(String mensaje) {
		JLabel descuento = ButtonFactory.newLabel(mensaje, Fonts.BOLD);
		descuento.setForeground(ColorPalette.RED.getColor());
		descuento.setOpaque(false);
		
		JPanel wrapper = new JPanel();
		wrapper.setOpaque(false);
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.add(Box.createVerticalGlue());
		wrapper.add(descuento);
		wrapper.add(Box.createVerticalGlue());
		wrapper.setPreferredSize(new Dimension(TiendaFrame.getInstance().getPixelsWidth(0.2), maxCompHeight));
		
		add(wrapper, BorderLayout.EAST);
	}
	
	public double getPuntuacionMedia() {
		return puntuacionMedia;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getPrecio() {
		return precio;
	}
	
}
