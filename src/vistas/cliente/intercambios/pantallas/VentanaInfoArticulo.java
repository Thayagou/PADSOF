package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.PanelFotoPerfil;
import vistas.common.TiendaFrame;
import vistas.herramientas.*;

public class VentanaInfoArticulo extends JPanel {

	private static final long serialVersionUID = 1L;

	/* ── Proporciones relativas a la pantalla ─────────────────────────── */
	private static final double FOTO_ARTICULO_W = 0.35;
	private static final double FOTO_ARTICULO_H = 0.35;
	private static final double PERFIL_SIZE = 0.06;
	private static final double PADDING = 0.02;
	private static final double BTN_H = 0.045;
	private static final double BTN_W = 0.18;
	private static final double GAP = 0.015;

	/* ── Botones expuestos para que el controlador los conecte ─────────── */
	private JButton btnHacerOferta;
	private JButton btnVerCartera;

	/**
	 * @param usrName        Nombre del usuario propietario del artículo.
	 * @param fotoPerfil     Nombre del fichero de imagen del perfil
	 * @param nombreArticulo Nombre del artículo.
	 * @param fotoArticulo   Nombre del fichero de imagen del artículo
	 * @param descripcion    Descripción larga del artículo.
	 * @param buscaACambio   Texto que describe qué busca a cambio el propietario.
	 * @param estado         Estado del artículo (ej. "Como nuevo", "Usado").
	 * @param estimacion     Estimación de valor en euros. Si es negativa, "Sin valorar".
	 * @param ajeno          Si {@code true}, muestra los botones de oferta e intercambio.
	 * @param categorias     Nombres de las categorías a las que pertenece el artículo.
	 */
	public VentanaInfoArticulo(String usrName, String fotoPerfil, String nombreArticulo, String fotoArticulo,
			String descripcion, String buscaACambio, String estado, double estimacion, boolean ajeno,
			String... categorias) {

		TiendaFrame t = TiendaFrame.getInstance();

		int pad = t.getPixelsWidth(PADDING);
		int gap = t.getPixelsWidth(GAP);
		int fotoW = t.getPixelsWidth(FOTO_ARTICULO_W);
		int fotoH = t.getPixelsHeight(FOTO_ARTICULO_H);
		int btnH = t.getPixelsHeight(BTN_H);
		int btnW = t.getPixelsWidth(BTN_W);

		setOpaque(false);
		setLayout(new BorderLayout());

		/* ── Scroll general ──────────────────────────────────────────── */
		JPanel contenido = buildContenido(t, usrName, fotoPerfil, nombreArticulo, fotoArticulo, descripcion,
				buscaACambio, estado, estimacion, ajeno, categorias, pad, gap, fotoW, fotoH, btnH, btnW);

		JScrollPane scroll = PanelFactory.getScroll(contenido);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);

		add(scroll, BorderLayout.CENTER);
	}

	private JPanel buildContenido(TiendaFrame t, String usrName, String fotoPerfil, String nombreArticulo,
			String fotoArticulo, String descripcion, String buscaACambio, String estado, double estimacion,
			boolean ajeno, String[] categorias, int pad, int gap, int fotoW, int fotoH, int btnH, int btnW) {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

		/* 1. Fila superior: foto + info ─────────────────────────────── */
		panel.add(buildFilaSuperior(t, usrName, fotoPerfil, nombreArticulo, fotoArticulo, estado, estimacion,
				categorias, gap, fotoW, fotoH));

		panel.add(Box.createVerticalStrut(gap * 2));

		/* 2. Descripción ────────────────────────────────────────────── */
		panel.add(buildTextoArea(descripcion, t));

		panel.add(Box.createVerticalStrut(gap * 2));

		/* 3. Busca a cambio ─────────────────────────────────────────── */
		panel.add(buildBuscaACambio(buscaACambio, t));

		/* 4. Botones (solo si el artículo es ajeno) ─────────────────── */
		if (ajeno) {
			panel.add(Box.createVerticalStrut(gap * 2));
			panel.add(buildBotones(usrName, btnH, btnW, gap));
		}

		return panel;
	}

	private JPanel buildFilaSuperior(TiendaFrame t, String usrName, String fotoPerfil, String nombreArticulo,
			String fotoArticulo, String estado, double estimacion, String[] categorias, int gap, int fotoW, int fotoH) {

		JPanel fila = new JPanel(new BorderLayout(gap * 2, 0));
		fila.setOpaque(false);
		fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, fotoH));

		/* Foto del artículo */
		fila.add(buildFotoArticulo(fotoArticulo, fotoW, fotoH), BorderLayout.WEST);

		/* Columna derecha: usuario + datos */
		fila.add(buildColumnaInfo(t, usrName, fotoPerfil, nombreArticulo, estado, estimacion, categorias, gap),
				BorderLayout.CENTER);

		return fila;
	}

	/** Imagen del artículo con placeholder si no hay fichero. */
	private JPanel buildFotoArticulo(String fotoArticulo, int w, int h) {
		if (fotoArticulo == null || fotoArticulo.isBlank())
			fotoArticulo = "articuloDefault.png";

		JPanel fotoPanel = new JPanel(new GridBagLayout());
		fotoPanel.setBackground(ColorPalette.CARD_DARK.getColor());
		fotoPanel.setPreferredSize(new Dimension(w, h));
		fotoPanel.setMinimumSize(new Dimension(w, h));
		fotoPanel.setMaximumSize(new Dimension(w, h));

		ImageIcon img = ButtonFactory.loadImageInBounds(fotoArticulo, h, w);
		fotoPanel.add(new JLabel(img));

		return fotoPanel;
	}

	/**
	 * Columna derecha: avatar+nombre usuario, nombre artículo, categorías, estado,
	 * estimación.
	 */
	private JPanel buildColumnaInfo(TiendaFrame t, String usrName, String fotoPerfil, String nombreArticulo,
			String estado, double estimacion, String[] categorias, int gap) {

		JPanel col = new JPanel();
		col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
		col.setOpaque(false);

		int perfilSize = t.getPixelsHeight(PERFIL_SIZE);

		/* Avatar + nombre de usuario en fila horizontal */
		JPanel filaUsuario = new JPanel();
		filaUsuario.setLayout(new BoxLayout(filaUsuario, BoxLayout.X_AXIS));
		filaUsuario.setOpaque(false);
		filaUsuario.setAlignmentX(LEFT_ALIGNMENT);

		PanelFotoPerfil avatar = new PanelFotoPerfil(fotoPerfil, perfilSize);
		JLabel lblUsuario = ButtonFactory.newLabel(usrName, Fonts.TEXT);
		lblUsuario.setForeground(ColorPalette.DARK_GREY.getColor());

		filaUsuario.add(avatar);
		filaUsuario.add(Box.createHorizontalStrut(gap));
		filaUsuario.add(lblUsuario);

		col.add(filaUsuario);
		col.add(Box.createVerticalStrut(gap * 2));

		/* Nombre del artículo */
		JLabel lblNombre = ButtonFactory.newLabel(nombreArticulo, Fonts.SUBTITLE);
		lblNombre.setForeground(ColorPalette.BLACK.getColor());
		lblNombre.setAlignmentX(LEFT_ALIGNMENT);
		col.add(lblNombre);
		col.add(Box.createVerticalStrut(gap));

		/* Categorías en morado */
		if (categorias.length > 0) {
			String cats = String.join(", ", categorias);
			JLabel lblCats = ButtonFactory.newLabel(cats, Fonts.TEXT);
			lblCats.setForeground(ColorPalette.PURPLE.getColor());
			lblCats.setAlignmentX(LEFT_ALIGNMENT);
			col.add(lblCats);
			col.add(Box.createVerticalStrut(gap));
		}

		/* Estado */
		JLabel lblEstado = ButtonFactory.newLabel("Estado: " + estado, Fonts.TEXT);
		lblEstado.setForeground(ColorPalette.DARK_GREY.getColor());
		lblEstado.setAlignmentX(LEFT_ALIGNMENT);
		col.add(lblEstado);
		col.add(Box.createVerticalStrut(gap));

		/* Estimación: negativa → "Sin valorar" */
		String estimacionTxt = (estimacion < 0) ? "Estimación: Sin valorar"
				: String.format("Estimación: %.0f €", estimacion);
		JLabel lblEstimacion = ButtonFactory.newLabel(estimacionTxt, Fonts.SUBTITLE);
		lblEstimacion.setForeground(ColorPalette.BLACK.getColor());
		lblEstimacion.setAlignmentX(LEFT_ALIGNMENT);
		col.add(lblEstimacion);

		return col;
	}

	private JPanel buildTextoArea(String texto, TiendaFrame t) {
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setOpaque(false);
		wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		JTextArea area = new JTextArea(texto);
		area.setFont(Fonts.TEXT.getFont());
		area.setForeground(ColorPalette.DARK_GREY.getColor());
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		area.setOpaque(false);

		wrapper.add(area, BorderLayout.CENTER);
		return wrapper;
	}

	private JPanel buildBuscaACambio(String buscaACambio, TiendaFrame t) {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		wrapper.setOpaque(false);

		/* Título en negrita */
		JLabel titulo = ButtonFactory.newLeftAlignedLabel("Busca a cambio:", Fonts.BOLD);
		titulo.setForeground(ColorPalette.BLACK.getColor());
		titulo.setAlignmentX(LEFT_ALIGNMENT);
		wrapper.add(titulo);
		wrapper.add(Box.createVerticalStrut(4));

		/* Texto del intercambio deseado */
		JTextArea area = new JTextArea(buscaACambio);
		area.setFont(Fonts.TEXT.getFont());
		area.setForeground(ColorPalette.DARK_GREY.getColor());
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		area.setOpaque(false);
		area.setAlignmentX(LEFT_ALIGNMENT);

		wrapper.add(area);
		return wrapper;
	}

	private JPanel buildBotones(String usrName, int btnH, int btnW, int gap) {
		btnHacerOferta = ButtonFactory.newRoundedButton("Hacer oferta a " + usrName, btnH, btnW, 0.5);
		ButtonFactory.paintButton(btnHacerOferta, ColorPalette.PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnHacerOferta, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);
		btnHacerOferta.setActionCommand("Hacer oferta");

		btnVerCartera = ButtonFactory.newRoundedButton("Ver cartera de " + usrName, btnH, btnW, 0.5);
		ButtonFactory.paintButton(btnVerCartera, ColorPalette.CARD_DARK, ColorPalette.DARK_GREY);
		ButtonFactory.addMouseMecanics(btnVerCartera, ColorPalette.CARD_DARK, ColorPalette.CARD_DARK_HOVER);
		btnVerCartera.setActionCommand("Ver cartera");

		JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, gap, 0));
		filaBotones.setOpaque(false);
		filaBotones.add(btnHacerOferta);
		filaBotones.add(btnVerCartera);

		return filaBotones;
	}

	/**
	 * Conecta el controlador a los botones interactivos de la vista. Solo tiene
	 * efecto si el artículo es ajeno (los botones existen).
	 *
	 * @param c ActionListener del controlador.
	 */
	public void setControlador(ActionListener c) {
		if (btnHacerOferta != null)
			btnHacerOferta.addActionListener(c);
		if (btnVerCartera != null)
			btnVerCartera.addActionListener(c);
	}
}