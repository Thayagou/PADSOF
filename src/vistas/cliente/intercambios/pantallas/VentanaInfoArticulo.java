package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFotoPerfil;
import vistas.common.components.FixedTextArea;
import vistas.herramientas.*;

public class VentanaInfoArticulo extends JPanel {

	private static final long serialVersionUID = 1L;

	/* Proporciones generales */
	private static final double PADDING = 0.02;
	private static final double GAP = 0.015;

	/* Foto */
	private static final double FOTO_W = 0.32;
	private static final double FOTO_H = 0.32;

	/* Avatar */
	private static final double AVATAR_SIZE = 0.055;

	/* Botones */
	private static final double BTN_W = 0.18;
	private static final double BTN_H = 0.05;
	private static final double BTN_ROUNDNESS = 0.5;

	/* Truncado */
	private static final double TEXT_MAX_W = 0.35;

	/* TextAreas */
	private static final int DESC_MAX_LINES = 8;
	private static final int INTERES_MAX_LINES = 5;

	private JButton btnOferta;
	private JButton btnCartera;

	public VentanaInfoArticulo(String usrName, String fotoPerfil, String nombreArticulo, String fotoArticulo,
			String descripcion, String interesadoEn, String estado, double estimacion, boolean ajeno,
			String actionOffer, String actionWallet, String... categorias) {

		TiendaFrame t = TiendaFrame.getInstance();

		int pad = t.getPixelsWidth(PADDING);
		int gap = t.getPixelsWidth(GAP);

		setOpaque(true);
		setBackground(ColorPalette.WHITE.getColor());
		setLayout(new BorderLayout(gap, gap));

		JPanel contenido = buildContenido(t, usrName, fotoPerfil, nombreArticulo, fotoArticulo, descripcion,
				interesadoEn, estado, estimacion, categorias, gap);

		contenido.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

		add(contenido, BorderLayout.CENTER);

		if (ajeno) {
			add(buildBotones(t, actionOffer, actionWallet, gap), BorderLayout.SOUTH);
		}
	}

	private JPanel buildContenido(TiendaFrame t, String usrName, String fotoPerfil, String nombreArticulo,
			String fotoArticulo, String descripcion, String interesadoEn, String estado, double estimacion,
			String[] categorias, int gap) {

		JPanel panel = new JPanel(new GridLayout(1, 2, gap, 0));
		panel.setOpaque(false);

		panel.add(buildColumnaIzquierda(t, fotoArticulo, descripcion, gap));

		panel.add(buildColumnaDerecha(t, usrName, fotoPerfil, nombreArticulo, estado, estimacion, interesadoEn,
				categorias, gap));

		return panel;
	}

	private JPanel buildColumnaIzquierda(TiendaFrame t, String fotoArticulo, String descripcion, int gap) {

		JPanel columna = new JPanel();
		columna.setOpaque(false);
		columna.setLayout(new GridLayout(2, 1));

		JPanel fotoPanel = new JPanel();
		fotoPanel.setOpaque(false);
		fotoPanel.setLayout(new BoxLayout(fotoPanel, BoxLayout.Y_AXIS));
		
		fotoPanel.add(buildFotoArticulo(t, fotoArticulo));
		fotoPanel.add(Box.createVerticalStrut(gap));

		JPanel descripcionPanel = new JPanel();
		descripcionPanel.setLayout(new BoxLayout(descripcionPanel, BoxLayout.Y_AXIS));
		descripcionPanel.setOpaque(false);
		
		JLabel lblDescripcion = ButtonFactory.newLabel("Descripción:", Fonts.BOLD);
		lblDescripcion.setAlignmentX(LEFT_ALIGNMENT);

		descripcionPanel.add(lblDescripcion);
		descripcionPanel.add(Box.createVerticalStrut(gap));

		descripcionPanel.add(buildTextArea(t, descripcion, DESC_MAX_LINES));
		
		columna.add(fotoPanel);
		columna.add(descripcionPanel);

		return columna;
	}

	private JPanel buildColumnaDerecha(TiendaFrame t, String usrName, String fotoPerfil, String nombreArticulo,
			String estado, double estimacion, String interesadoEn, String[] categorias, int gap) {
		
		JPanel columna = new JPanel(new GridLayout(2, 1));
		columna.setOpaque(false);

		JPanel fila1 = new JPanel();
		fila1.setOpaque(false);
		fila1.setLayout(new BoxLayout(fila1, BoxLayout.Y_AXIS));

		fila1.add(buildUsuario(t, usrName, fotoPerfil, gap));

		fila1.add(Box.createVerticalStrut(gap));

		fila1.add(buildTextoSimple(t, nombreArticulo, Fonts.SUBTITLE, ColorPalette.BLACK));

		fila1.add(Box.createVerticalStrut(gap));

		if (categorias.length > 0) {
			fila1.add(buildTextoSimple(t, String.join(", ", categorias), Fonts.TEXT, ColorPalette.PURPLE));

			fila1.add(Box.createVerticalStrut(gap));
		}

		fila1.add(buildTextoSimple(t, "Estado: " + estado, Fonts.TEXT, ColorPalette.DARK_GREY));
		fila1.add(Box.createVerticalStrut(gap));

		String textoEstimacion = estimacion < 0 ? "Estimación: Sin valorar"
				: String.format("Estimación: %.2f €", estimacion);
		fila1.add(buildTextoSimple(t, textoEstimacion, Fonts.BOLD, ColorPalette.BLACK));
		fila1.add(Box.createVerticalStrut(gap * 2));
		
		columna.add(fila1);
		
		JPanel fila2 = new JPanel();
		fila2.setOpaque(false);
		fila2.setLayout(new BoxLayout(fila2, BoxLayout.Y_AXIS));

		JLabel lblInteres = ButtonFactory.newLeftAlignedLabel("Busca a cambio:", Fonts.BOLD);
		lblInteres.setForeground(ColorPalette.BLACK.getColor());
		lblInteres.setAlignmentX(LEFT_ALIGNMENT);

		fila2.add(lblInteres);
		fila2.add(Box.createVerticalStrut(gap));

		fila2.add(buildTextArea(t, interesadoEn, INTERES_MAX_LINES));
		
		columna.add(fila2);

		return columna;
	}

	private JPanel buildUsuario(TiendaFrame t, String usrName, String fotoPerfil, int gap) {

		JPanel fila = new JPanel(new BorderLayout(gap, 0));
		fila.setOpaque(false);
		fila.setAlignmentX(LEFT_ALIGNMENT);

		int avatarSize = t.getPixelsHeight(AVATAR_SIZE);

		PanelFotoPerfil avatar = new PanelFotoPerfil(fotoPerfil, avatarSize);

		JLabel lblUsuario = ButtonFactory.newLeftAlignedLabel(
				Fonts.truncar(usrName, t.getPixelsWidth(TEXT_MAX_W), Fonts.TEXT.getFont(), this), Fonts.TEXT);

		lblUsuario.setForeground(ColorPalette.DARK_GREY.getColor());

		fila.add(avatar, BorderLayout.WEST);
		fila.add(lblUsuario, BorderLayout.CENTER);

		return fila;
	}

	private JPanel buildFotoArticulo(TiendaFrame t, String fotoArticulo) {

		int fotoW = t.getPixelsWidth(FOTO_W);
		int fotoH = t.getPixelsHeight(FOTO_H);

		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(true);
		panel.setBackground(ColorPalette.CARD_DARK.getColor());

		panel.setPreferredSize(new Dimension(fotoW, fotoH));
		panel.setMaximumSize(new Dimension(fotoW, fotoH));
		panel.setMinimumSize(new Dimension(fotoW, fotoH));

		JLabel img = new JLabel(ButtonFactory.loadImageInBounds(fotoArticulo, fotoH, fotoW));
		img.setAlignmentX(LEFT_ALIGNMENT);
		
		panel.add(img);
		panel.setBorder(BorderFactory.createLineBorder(ColorPalette.GREY.getColor()));

		return panel;
	}

	private JPanel buildTextoSimple(TiendaFrame t, String texto, Fonts fuente, ColorPalette color) {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setAlignmentX(LEFT_ALIGNMENT);

		String truncado = Fonts.truncar(texto, t.getPixelsWidth(TEXT_MAX_W), fuente.getFont(), panel);

		JLabel lbl = ButtonFactory.newLeftAlignedLabel(truncado, fuente);
		lbl.setForeground(color.getColor());

		panel.add(lbl, BorderLayout.WEST);

		return panel;
	}

	private JScrollPane buildTextArea(TiendaFrame t, String texto, int maxLines) {
	    int maxWidth = t.getPixelsWidth(TEXT_MAX_W - 0.05);
	    String truncado = truncarTextoMultilinea(texto, maxLines, maxWidth, Fonts.TEXT.getFont());

	    JTextArea area = new FixedTextArea(truncado);
	    area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	    area.setEditable(false);
	    area.setOpaque(false);
	    area.setLineWrap(true);
	    area.setWrapStyleWord(true);
	    area.setFont(Fonts.TEXT.getFont());
	    area.setForeground(ColorPalette.DARK_GREY.getColor());

	    // Evitar que el JTextArea intente crecer horizontalmente
	    area.setSize(maxWidth, Short.MAX_VALUE); // Ancho fijo inicial

	    JPanel areaContent = new JPanel(new BorderLayout());
	    areaContent.setOpaque(true);
	    areaContent.setBackground(ColorPalette.CARD_LIGHT.getColor());
	    areaContent.add(area, BorderLayout.CENTER);

	    JScrollPane scroll = PanelFactory.getScroll(areaContent);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    scroll.setBorder(BorderFactory.createEmptyBorder());
	    scroll.setOpaque(false);
	    scroll.getViewport().setOpaque(false);
	    scroll.setAlignmentX(LEFT_ALIGNMENT);
	    scroll.setBorder(BorderFactory.createLineBorder(ColorPalette.LIGHT_GREY.getColor()));

	    // Listener para que el JTextArea se ajuste al ancho real del viewport
	    // (teniendo en cuenta si aparece la barra vertical)
	    scroll.getViewport().addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            int newWidth = scroll.getViewport().getWidth();
	            if (newWidth > 0) {
	                area.setSize(newWidth, Short.MAX_VALUE);
	                area.revalidate();
	            }
	        }
	    });

	    return scroll;
	}

	private JPanel buildBotones(TiendaFrame t, String actionOffer, String actionWallet, int gap) {

		int btnW = t.getPixelsWidth(BTN_W);
		int btnH = t.getPixelsHeight(BTN_H);

		btnOferta = ButtonFactory.newRoundedButton(actionOffer, btnH, btnW, BTN_ROUNDNESS);

		btnOferta.setActionCommand(actionOffer);

		ButtonFactory.paintButton(btnOferta, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);

		ButtonFactory.addMouseMecanics(btnOferta, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		btnCartera = ButtonFactory.newRoundedButton(actionWallet, btnH, btnW, BTN_ROUNDNESS);

		btnCartera.setActionCommand(actionWallet);

		ButtonFactory.paintButton(btnCartera, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);

		ButtonFactory.addMouseMecanics(btnCartera, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, 0));
		botones.setOpaque(false);

		botones.add(btnOferta);
		botones.add(btnCartera);

		return PanelFactory.wrapVertical(botones, gap);
	}

	private String truncarTextoMultilinea(String texto, int maxLines, int maxWidth, Font font) {

		if (texto == null) {
			return "";
		}

		FontMetrics fm = getFontMetrics(font);

		String[] palabras = texto.split(" ");
		StringBuilder resultado = new StringBuilder();

		String lineaActual = "";
		int lineas = 1;

		for (String palabra : palabras) {

			String candidata = lineaActual.isEmpty() ? palabra : lineaActual + " " + palabra;

			if (fm.stringWidth(candidata) <= maxWidth) {
				lineaActual = candidata;
			}

			else {

				if (lineas >= maxLines) {
					resultado.append(Fonts.truncar(lineaActual, maxWidth, font, this));

					resultado.append("...");
					return resultado.toString();
				}

				resultado.append(lineaActual).append("\n");

				lineaActual = palabra;
				lineas++;
			}
		}

		resultado.append(lineaActual);

		return resultado.toString();
	}

	public void setControlador(ActionListener c) {

		if (btnOferta != null) {
			btnOferta.addActionListener(c);
		}

		if (btnCartera != null) {
			btnCartera.addActionListener(c);
		}
	}
}