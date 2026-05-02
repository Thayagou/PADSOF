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
	private static final int MAX_DESC = 120;
	
	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();
	
	private String nombre;
	private String descripcion;
	private double puntuacionMedia;
	private double precio;

	//private final JButton clickArea; // botón invisible que ocupa todo el panel

	public PanelProducto(String nombre, String descripcion, String imageName, double puntuacionMedia, double precio, String actionName, String...categorias) {
		super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT, FOTO_W_PERC, imageName, actionName);
		//super(MAX_HEIGHT, FOTO_H_PERC*MAX_HEIGHT, "Ver producto:");
		
		this.puntuacionMedia = puntuacionMedia;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.precio = precio;
		
		//setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();
		/* Info: estrellas + nombre + descripción + precio + categorías */
			JPanel info = new JPanel();
			info.setOpaque(false);
			info.setLayout(new GridLayout(3, 1));
			
			/*Primera fila: estrellas + nombre */
			JPanel firstRow = new JPanel();
			firstRow.setOpaque(false);
			firstRow.setLayout(new BorderLayout(10, 0));
			firstRow.add(buildEstrellas(t, puntuacionMedia), BorderLayout.CENTER);
	
			JLabel nombreLabel = new JLabel(nombre);
			nombreLabel.setFont(Fonts.BOLD.getFont());
			nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
			firstRow.add(nombreLabel, BorderLayout.WEST);
	
			info.add(firstRow);
			
			/*Segunda fila: descripcion*/
			if (descripcion != null && descripcion.length() > MAX_DESC)
				descripcion = descripcion.substring(0, MAX_DESC) + "...";
			ButtonFactory f = new ButtonFactory();
			JLabel descripcionLabel = f.newLabel(descripcion, Fonts.SMALL);
					//new JLabel("<html>" + descripcion + "</html>");
			//descripcionLabel.setFont(Fonts.SMALL.getFont());
			descripcionLabel.setForeground(ColorPalette.DARK_GREY.getColor());
			info.add(descripcionLabel);
			
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
			
			info.add(thirdRow);

		//add(foto, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);
	}

	// ── Estrellas ──────────────────────────────────────────────────────────
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
