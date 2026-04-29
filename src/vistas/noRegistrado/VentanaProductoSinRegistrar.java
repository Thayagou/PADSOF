package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import vistas.common.*;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

// TODO: Auto-generated Javadoc
/**
 * Vista detallada de un producto (maqueta 5).
 * Layout: izquierda = panel de valoraciones/reseñas (scrolleable),
 *         derecha = foto grande + nombre + categorías + precio + descripción.
 */
public class VentanaProductoSinRegistrar extends JPanel {
    
    /** Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Constante REVIEWS_W_PERC. */
    private static final double REVIEWS_W_PERC = 0.33;
    
    /** Constante FOTO_H_PERC. */
    private static final double FOTO_H_PERC    = 0.35;
    
    /** Campo resenasPanel. */
    private JPanel resenasPanel = new JPanel();

    /**
     * Instancia un nuevo Objeto VentanaProductoSinRegistrar.
     *
     * @param nombre parámetro nombre
     * @param descripcion parámetro descripcion
     * @param puntuacionMedia parámetro puntuacionMedia
     * @param precio parámetro precio
     * @param categorias parámetro categorias
     */
    public VentanaProductoSinRegistrar(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
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
        lblValoraciones.setFont(Fonts.TITLE3.getFont());
        lblValoraciones.setForeground(ColorPalette.WHITE.getColor());
        lblValoraciones.setOpaque(true);
        lblValoraciones.setBackground(ColorPalette.BG_BLUE.getColor());
        lblValoraciones.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        leftWrapper.add(lblValoraciones, BorderLayout.NORTH);

        resenasPanel.setLayout(new BoxLayout(resenasPanel, BoxLayout.Y_AXIS));
        resenasPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

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
        rightPanel.add(buildEstrellas(t, puntuacionMedia));
        rightPanel.add(Box.createVerticalStrut(6));

        // Nombre
        JLabel nombreLabel = new JLabel(nombre);
        nombreLabel.setFont(Fonts.SUBTITLE.getFont());
        nombreLabel.setForeground(Color.BLACK);
        nombreLabel.setAlignmentX(LEFT_ALIGNMENT);
        rightPanel.add(nombreLabel);

        // Categorías
        for(String c : categorias) {
            JLabel catLabel = new JLabel(c);
            catLabel.setFont(Fonts.TEXT.getFont());
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
        fotoLbl.setFont(Fonts.TITLE3.getFont());
        fotoLbl.setForeground(ColorPalette.DARK_GREY.getColor());
        foto.add(fotoLbl);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(foto);

        // Precio
        JLabel precioLabel = new JLabel(String.format("Precio: %.2f €", precio));
        precioLabel.setFont(Fonts.TITLE3.getFont());
        precioLabel.setForeground(Color.BLACK);
        precioLabel.setAlignmentX(LEFT_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(precioLabel);

        // Descripción
        JTextArea desc = new JTextArea(descripcion);
        desc.setFont(Fonts.TEXT.getFont());
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
    
    /**
     * anadirPanelResena.
     *
     * @param puntuacion parámetro puntuacion
     * @param comentario parámetro comentario
     * @param usr parámetro usr
     */
    public void anadirPanelResena(double puntuacion, String comentario, String usr) {
    	resenasPanel.add(new PanelResena(puntuacion, comentario, usr));
    }

    /**
     * buildEstrellas.
     *
     * @param t parámetro t
     * @param val parámetro val
     * @return valor de tipo JPanel
     */
    // ── Fila de estrellas ─────────────────────────────────────────────────
    private JPanel buildEstrellas(TiendaFrame t, double val) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0));
        p.setOpaque(false);
        int llenas = (int) Math.round(val);
        for (int i = 1; i <= 5; i++) {
            JLabel s = new JLabel("★");
            s.setFont(Fonts.TITLE3.getFont());
            s.setForeground(i <= llenas
                    ? ColorPalette.YELLOW.getColor()
                    : ColorPalette.LIGHT_GREY.getColor());
            p.add(s);
        }
        return p;
    }
}
