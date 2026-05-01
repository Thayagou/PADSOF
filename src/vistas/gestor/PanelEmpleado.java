package vistas.gestor;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.venta.productos.Producto;
import vistas.common.PanelDisplay;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelEmpleado extends PanelDisplay {
	private static final long serialVersionUID = 1L;

	private static final double FOTO_W_PERC = 0.09;
	private static final double FOTO_H_PERC = 0.10;
	private static final int MAX_DESC = 120;
	
	private Color gradStart = ColorPalette.CARD_LIGHT.getColor();
	private Color gradEnd = ColorPalette.CARD_DARK.getColor();
	
	private String nombre;
	private String fotoDePerfil;
	private boolean deAlta;
	private List<String> permisos;

	private final JButton clickArea; // botón invisible que ocupa todo el panel

	public PanelEmpleado(String nombre, String fotoDePerfil, boolean deAlta, String...permisos) {
		
		this.nombre = nombre;
		this.deAlta = deAlta;
		this.fotoDePerfil = fotoDePerfil;
		this.permisos = List.of(permisos);
		
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
		fotoLabel.setFont(Fonts.BOLD.getFont());
		foto.add(fotoLabel);
		
		ButtonFactory factory = new ButtonFactory();

		/* Info: estrellas + nombre + descripción + precio + categorías */
			JPanel info = new JPanel();
			info.setOpaque(false);
			info.setLayout(new GridLayout(2, 1));
			
			/*Primera fila: estrellas + nombre */
			JPanel firstRow = new JPanel();
			firstRow.setOpaque(false);
			/*firstRow.setLayout(new BorderLayout(10, 0));
			firstRow.add(buildEstrellas(t, puntuacionMedia), BorderLayout.WEST);*/
	
			JLabel nombreLabel = new JLabel(nombre);
			nombreLabel.setFont(Fonts.BOLD.getFont());
			nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
			firstRow.add(nombreLabel);
	
			info.add(firstRow);
			
			/*Segunda fila: permisos*/
			JPanel permisosRow = new JPanel();
			permisosRow.setLayout(new BorderLayout(10, 0));
			permisosRow.setOpaque(false);
			
			String permisosString = String.join(", ", this.permisos);
			
			if (!permisosString.isBlank()) permisosString = "Sin permisos";
			JLabel permisosLabel = new JLabel(permisosString);
			permisosLabel.setFont(Fonts.TEXT.getFont());
			permisosLabel.setForeground(ColorPalette.PURPLE.getColor());
			permisosRow.add(permisosLabel, BorderLayout.WEST);
			
			/*Tercera fila: de alta*/
			JPanel deAltaRow = new JPanel();
			deAltaRow.setLayout(new BorderLayout(10, 0));
			deAltaRow.setOpaque(false);
			
			JLabel estado = new JLabel(deAlta ? "Empleado de alta" : "Empleado de baja");
			estado.setFont(Fonts.BOLD.getFont());
			
			if (this.deAlta)estado.setForeground(ColorPalette.GREEN.getColor());
			else estado.setForeground(ColorPalette.RED.getColor());
			deAltaRow.add(estado, BorderLayout.WEST);
			info.add(deAltaRow);

		add(foto, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);

		// Botón invisible para detectar clic en toda la fila
		clickArea = new JButton();
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
		});
	}
	
	/** Añade un listener que se dispara al hacer clic en la fila. */
	public void setControlador(java.awt.event.ActionListener l) {
		clickArea.addActionListener(l);
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