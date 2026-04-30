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
	private static final double FOTO_H_PERC = 0.15;
	private static final int MAX_DESC = 120;
	
	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();
	
	private String nombre;
	private String descripcion;
	private double puntuacionMedia;
	private double precio;

	//private final JButton clickArea; // botón invisible que ocupa todo el panel

	public PanelProducto(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		super(1.01*FOTO_H_PERC, FOTO_H_PERC, FOTO_W_PERC, "Ver producto:");
		this.puntuacionMedia = puntuacionMedia;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.precio = precio;
		
		//setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();
		/*int fotoW = t.getPixelsWidth(FOTO_W_PERC);
		int fotoH = t.getPixelsHeight(FOTO_H_PERC);

		setLayout(new BorderLayout(10, 0));
		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.BLACK.getColor()),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, fotoH + 16));

		// — Foto placeholder —
		ButtonFactory factory = new ButtonFactory();
		JPanel foto = new JPanel();
		foto.setBackground(ColorPalette.CARD_DARK.getColor());
		foto.setPreferredSize(new Dimension(fotoW, fotoH));
		//JLabel fotoLabel = new JLabel("FOTO", JLabel.CENTER);
		JLabel fotoLabel = new JLabel(factory.loadImageIconScaled("producto.png", fotoH, fotoW));
		fotoLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		fotoLabel.setFont(Fonts.BOLD.getFont());
		foto.add(fotoLabel);*/

		/* Info: estrellas + nombre + descripción + precio + categorías */
			JPanel info = new JPanel();
			info.setOpaque(false);
			info.setLayout(new GridLayout(3, 1));
			
			/*Primera fila: estrellas + nombre */
			JPanel firstRow = new JPanel();
			firstRow.setOpaque(false);
			firstRow.setLayout(new BorderLayout(10, 0));
			firstRow.add(buildEstrellas(t, puntuacionMedia), BorderLayout.WEST);
	
			JLabel nombreLabel = new JLabel(nombre);
			nombreLabel.setFont(Fonts.BOLD.getFont());
			nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
			firstRow.add(nombreLabel, BorderLayout.CENTER);
	
			info.add(firstRow);
			
			/*Segunda fila: descripcion*/
			if (descripcion != null && descripcion.length() > MAX_DESC)
				descripcion = descripcion.substring(0, MAX_DESC) + "...";
			JLabel descripcionLabel = new JLabel("<html>" + descripcion + "</html>");
			descripcionLabel.setFont(Fonts.SMALL.getFont());
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

		// Botón invisible para detectar clic en toda la fila
		/*clickArea = new JButton();
		clickArea.setOpaque(false);
		clickArea.setContentAreaFilled(false);
		clickArea.setBorderPainted(false);
		clickArea.setActionCommand("Ver producto:");
		clickArea.setPreferredSize(new Dimension(0, 0));

		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
			    gradStart = ColorPalette.CARD_LIGHT_HOVER.getColor();
			    gradEnd = ColorPalette.CARD_DARK_HOVER.getColor();
			    repaint();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
			    gradStart = ColorPalette.CARD_LIGHT.getColor();
			    gradEnd = ColorPalette.CARD_DARK.getColor();
			    repaint();
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				clickArea.doClick();
			}
		});*/
	}
	
	/** Añade un listener que se dispara al hacer clic en la fila. */
	/*public void setControlador(java.awt.event.ActionListener l) {
		clickArea.addActionListener(l);
	}*/

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

	/*@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int w = getWidth();
		int h = getHeight();

		// Gradiente (puedes ajustar colores)
		GradientPaint gp = new GradientPaint(0, 0, gradStart, 0, h, gradEnd);

		g2.setPaint(gp);
		g2.fillRect(0, 0, w, h);

		g2.dispose();

		super.paintComponent(g); // importante: pinta hijos después
	}*/
	
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
