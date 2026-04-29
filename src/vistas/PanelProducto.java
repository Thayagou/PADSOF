package vistas;

import java.awt.*;
import javax.swing.*;
import modelo.venta.productos.Producto;

/**
 * Fila de producto para las listas de resultados / productos populares.
 * Muestra: foto, estrellas, nombre, descripción truncada, precio y categorías.
 * Al hacer clic sobre la fila se puede asignar un ActionListener externo.
 */
public class PanelProducto extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.10;
	private static final int MAX_DESC = 120;
	
	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();

	private final Producto producto;
	private final JButton clickArea; // botón invisible que ocupa todo el panel

	public PanelProducto(Producto producto) {
		this.producto = producto;
		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();
		int fotoW = t.getPixelsWidth(FOTO_W_PERC);
		int fotoH = t.getPixelsHeight(FOTO_H_PERC);

		setLayout(new BorderLayout(10, 0));
		setBackground(ColorPalette.CARD_LIGHT.getColor());
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.CARD_DARK.getColor()),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, fotoH + 16));

		// — Foto placeholder —
		JPanel foto = new JPanel();
		foto.setBackground(ColorPalette.CARD_DARK.getColor());
		foto.setPreferredSize(new Dimension(fotoW, fotoH));
		JLabel fotoLabel = new JLabel("FOTO", JLabel.CENTER);
		fotoLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		fotoLabel.setFont(t.getTextFont());
		foto.add(fotoLabel);

		/* Info: estrellas + nombre + descripción + precio + categorías */
			JPanel info = new JPanel();
			info.setOpaque(false);
			info.setLayout(new GridLayout(3, 1));
			
			/*Primera fila: estrellas + nombre */
			JPanel firstRow = new JPanel();
			firstRow.setOpaque(false);
			firstRow.setLayout(new BorderLayout(10, 0));
			firstRow.add(buildEstrellas(t, producto.getPuntuacionMedia()), BorderLayout.WEST);
	
			JLabel nombre = new JLabel(producto.getNombre());
			nombre.setFont(t.getTextFont());
			nombre.setForeground(ColorPalette.DARK_GREY.getColor().darker());
			firstRow.add(nombre, BorderLayout.CENTER);
	
			info.add(firstRow);
			
			/*Segunda fila: descripcion*/
			String desc = producto.getDescripcion();
			if (desc != null && desc.length() > MAX_DESC)
				desc = desc.substring(0, MAX_DESC) + "...";
			JLabel descripcion = new JLabel("<html>" + desc + "</html>");
			descripcion.setFont(t.getTextFont());
			descripcion.setForeground(ColorPalette.DARK_GREY.getColor());
			info.add(descripcion);
			
			/*Tercera fila: categorias + precio*/
			JPanel thirdRow = new JPanel();
			thirdRow.setLayout(new BorderLayout(10, 0));
			thirdRow.setOpaque(false);
			
			String cats = String.join(", ", java.util.Arrays.stream(producto.getCategorias()).map(c -> c.getNombre()).toArray(String[]::new));
			if (!cats.isEmpty()) {
				JLabel categoriasLabel = new JLabel(cats);
				categoriasLabel.setFont(t.getTextFont());
				categoriasLabel.setForeground(ColorPalette.PURPLE.getColor());
				thirdRow.add(categoriasLabel, BorderLayout.WEST);
			}
			
			JLabel precio = new JLabel(String.format("%.2f €", producto.getPrecio()));
			precio.setFont(t.getTextFont());
			precio.setForeground(Color.BLACK);
			thirdRow.add(precio, BorderLayout.CENTER);
			
			info.add(thirdRow);

		add(foto, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);

		// Botón invisible para detectar clic en toda la fila
		clickArea = new JButton();
		clickArea.setOpaque(false);
		clickArea.setContentAreaFilled(false);
		clickArea.setBorderPainted(false);
		clickArea.setActionCommand("Ver producto:" + producto.getNombre());
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
		});
	}
	
	/** Añade un listener que se dispara al hacer clic en la fila. */
	public void addClickListener(java.awt.event.ActionListener l) {
		clickArea.addActionListener(l);
	}

	public Producto getProducto() {
		return producto;
	}

	// ── Estrellas ──────────────────────────────────────────────────────────
	private JPanel buildEstrellas(TiendaFrame t, double valoracion) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
		p.setOpaque(false);
		int llenas = (int) Math.round(valoracion);
		for (int i = 1; i <= 5; i++) {
			JLabel star = new JLabel("★");
			star.setFont(t.getTextFont());
			star.setForeground(i <= llenas ? ColorPalette.YELLOW.getColor() : ColorPalette.LIGHT_GREY.getColor());
			p.add(star);
		}
		return p;
	}

	@Override
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
	}
}
