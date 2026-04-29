package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import modelo.venta.productos.Producto;
import modelo.venta.productos.Resena;
import vistas.*;
import vistas.herramientas.ColorPalette;

/**
 * Vista detallada de un producto (maqueta 5).
 * Layout: izquierda = panel de valoraciones/reseñas (scrolleable),
 *         derecha = foto grande + nombre + categorías + precio + descripción.
 */
public class VentanaProductoSinRegistrar extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final double REVIEWS_W_PERC = 0.33;
    private static final double FOTO_H_PERC    = 0.35;

    public VentanaProductoSinRegistrar(Producto producto) {
        TiendaFrame t = TiendaFrame.getInstance();

        setOpaque(false);
        setLayout(new BorderLayout());

        int reviewsW = t.getPixelsWidth(REVIEWS_W_PERC);

        // ════════════════════════════════════════════════════
        //  Panel izquierdo — Valoraciones
        // ════════════════════════════════════════════════════
        JPanel leftWrapper = new JPanel(new BorderLayout());
        leftWrapper.setPreferredSize(new Dimension(reviewsW, 0));
        leftWrapper.setBackground(ColorPalette.BG_BLUE.getColor());

        JLabel lblValoraciones = new JLabel("  Valoraciones");
        lblValoraciones.setFont(t.getTitle3Font());
        lblValoraciones.setForeground(ColorPalette.WHITE.getColor());
        lblValoraciones.setOpaque(true);
        lblValoraciones.setBackground(ColorPalette.BG_BLUE.getColor());
        lblValoraciones.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        leftWrapper.add(lblValoraciones, BorderLayout.NORTH);

        JPanel resenasPanel = new JPanel();
        resenasPanel.setLayout(new BoxLayout(resenasPanel, BoxLayout.Y_AXIS));
        resenasPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		// Poblar reseñas usando PanelResena
		Resena[] resenas = producto.getResenas();
		if (resenas != null && resenas.length > 0) {
			for (Resena r : resenas) {
				resenasPanel.add(new PanelResena(r));
			}
		} else {
			JLabel sinResenas = new JLabel("Aún no hay valoraciones");
			sinResenas.setFont(t.getTextFont());
			sinResenas.setForeground(ColorPalette.DARK_GREY.getColor());
			sinResenas.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
			resenasPanel.add(sinResenas);
		}

        JScrollPane scrollResenas = new JScrollPane(resenasPanel);
        scrollResenas.setBorder(BorderFactory.createEmptyBorder());
        scrollResenas.getVerticalScrollBar().setUnitIncrement(12);
        scrollResenas.getViewport().setBackground(ColorPalette.CARD_LIGHT.getColor());
        leftWrapper.add(scrollResenas, BorderLayout.CENTER);

        // ════════════════════════════════════════════════════
        //  Panel derecho — Detalle del producto
        // ════════════════════════════════════════════════════
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        // Estrellas
        rightPanel.add(buildEstrellas(t, producto.getPuntuacionMedia()));
        rightPanel.add(Box.createVerticalStrut(6));

        // Nombre
        JLabel nombre = new JLabel(producto.getNombre());
        nombre.setFont(t.getSubtitleFont());
        nombre.setForeground(Color.BLACK);
        nombre.setAlignmentX(LEFT_ALIGNMENT);
        rightPanel.add(nombre);

        // Categorías
        String cats = String.join(", ",
                java.util.Arrays.stream(producto.getCategorias())
                        .map(c -> c.getNombre())
                        .toArray(String[]::new));
        if (!cats.isEmpty()) {
            JLabel catLabel = new JLabel(cats);
            catLabel.setFont(t.getTextFont());
            catLabel.setForeground(ColorPalette.PURPLE.getColor());
            catLabel.setAlignmentX(LEFT_ALIGNMENT);
            rightPanel.add(Box.createVerticalStrut(4));
            rightPanel.add(catLabel);
        }

        // Foto placeholder
        int fotoH = t.getPixelsHeight(FOTO_H_PERC);
        JPanel foto = new JPanel(new GridBagLayout());
        foto.setBackground(ColorPalette.CARD_DARK.getColor());
        foto.setMaximumSize(new Dimension(Integer.MAX_VALUE, fotoH));
        foto.setPreferredSize(new Dimension(0, fotoH));
        foto.setAlignmentX(LEFT_ALIGNMENT);
        JLabel fotoLbl = new JLabel("FOTO");
        fotoLbl.setFont(t.getTitle3Font());
        fotoLbl.setForeground(ColorPalette.DARK_GREY.getColor());
        foto.add(fotoLbl);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(foto);

        // Precio
        JLabel precio = new JLabel(String.format("Precio: %.2f €", producto.getPrecio()));
        precio.setFont(t.getTitle3Font());
        precio.setForeground(Color.BLACK);
        precio.setAlignmentX(LEFT_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(precio);

        // Descripción
        JTextArea desc = new JTextArea(producto.getDescripcion());
        desc.setFont(t.getTextFont());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setForeground(ColorPalette.DARK_GREY.getColor());
        desc.setAlignmentX(LEFT_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(desc);

        add(leftWrapper, BorderLayout.WEST);
        add(rightPanel,  BorderLayout.CENTER);
    }

    // ── Fila de estrellas ─────────────────────────────────────────────────
    private JPanel buildEstrellas(TiendaFrame t, double val) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        p.setOpaque(false);
        int llenas = (int) Math.round(val);
        for (int i = 1; i <= 5; i++) {
            JLabel s = new JLabel("★");
            s.setFont(t.getTitle3Font());
            s.setForeground(i <= llenas
                    ? ColorPalette.YELLOW.getColor()
                    : ColorPalette.LIGHT_GREY.getColor());
            p.add(s);
        }
        return p;
    }
}
