package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import vistas.herramientas.*;
import vistas.cliente.venta.pantallas.starRating.StarRating;
import vistas.common.app.TiendaFrame;

/**
 * Pantalla emergente para añadir una reseña a un producto, con selector de estrellas y campo de comentario.
 */
public class VentanaAnadirResena extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constante PANEL_WIDTH. Anchura del panel de reseña como porcentaje de la pantalla. */
	private static final double PANEL_WIDTH = 0.4;
	
	/** Constante PANEL_HEIGHT. Altura del panel de reseña como porcentaje de la pantalla. */
	private static final double PANEL_HEIGHT = 0.65;
	
	/** Constante SPACING. Espaciado vertical entre componentes como porcentaje de la pantalla. */
	private static final double SPACING = 0.02;
	
	/** Constante TEXTAREA_HEIGHT. Altura del área de texto para el comentario como porcentaje de la pantalla. */
	private static final double TEXTAREA_HEIGHT = 0.2;
	
	/** Constante BTN_HEIGHT. Altura del botón de enviar como porcentaje de la pantalla. */
	private static final double BTN_HEIGHT = 0.05;
	
	/** Constante STARS_WRAP. Margen horizontal del contenedor de estrellas como porcentaje de la pantalla. */
	private static final double STARS_WRAP = 0.05;

	/** Constante CORNER_RADIUS_MACRO. Radio de las esquinas redondeadas del panel como porcentaje de la pantalla. */
	private static final double CORNER_RADIUS_MACRO = 0.065;

	/** Campo campoComentario. Área de texto donde el usuario escribe su reseña. */
	private JTextArea campoComentario;
	
	/** Campo puntuador. Componente de selección de puntuación por estrellas. */
	private StarRating puntuador = new StarRating(); 
	
	/** Campo btnEnviar. Botón para enviar la reseña. */
	private JButton btnEnviar;

	/**
	 * Instancia un nuevo Objeto VentanaAnadirResena.
	 * Construye la ventana con el selector de estrellas, campo de comentario y botón de envío.
	 *
	 * @param actionName Comando de acción para el botón de enviar reseña.
	 */
	public VentanaAnadirResena(String actionName) {
		setLayout(new GridBagLayout());
		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();

		int panelW = t.getPixelsWidth(PANEL_WIDTH);
		int panelH = t.getPixelsHeight(PANEL_HEIGHT);
		int spacing = t.getPixelsHeight(SPACING);
		int textH = t.getPixelsHeight(TEXTAREA_HEIGHT);
		int btnH = t.getPixelsHeight(BTN_HEIGHT);
		int pWrap = t.getPixelsWidth(STARS_WRAP);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setOpaque(false);
		contenido.setPreferredSize(new Dimension(panelW, panelH));
		
		JPanel puntuadorWrapper = PanelFactory.wrapHorizontal(puntuador, pWrap);

		JLabel lblComentario = ButtonFactory.newLabel("Comentario", Fonts.BOLD);
		lblComentario.setAlignmentX(CENTER_ALIGNMENT);
		JPanel comentarioLabel = new JPanel(new GridLayout(1, 1));
		comentarioLabel.setOpaque(false);
		comentarioLabel.add(lblComentario);

		campoComentario = new JTextArea();
		campoComentario.setFont(Fonts.TEXT.getFont());
		campoComentario.setLineWrap(true);
		campoComentario.setWrapStyleWord(true);
		campoComentario.setCaretColor(ColorPalette.PURPLE.getColor());
		campoComentario.setBackground(ColorPalette.WHITE.getColor());
		campoComentario.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		campoComentario.setForeground(ColorPalette.BLACK.getColor());

		JPanel campo = new JPanel(new BorderLayout());
		campo.add(campoComentario);

		JScrollPane scrollComentario = PanelFactory.getScroll(campo);
		scrollComentario.setPreferredSize(new Dimension(panelW, textH));
		scrollComentario.setBorder(BorderFactory.createLineBorder(ColorPalette.BLUE.getColor()));

		scrollComentario.getViewport().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int newWidth = scrollComentario.getViewport().getWidth();
				if (newWidth > 0) {
					campoComentario.setSize(newWidth, Short.MAX_VALUE);
					campoComentario.revalidate();
				}
			}
		});

		btnEnviar = ButtonFactory.newRoundedButton("Enviar reseña", btnH, panelW, 1);
		btnEnviar.setActionCommand(actionName);
		btnEnviar.setAlignmentX(CENTER_ALIGNMENT);
		ButtonFactory.paintButton(btnEnviar, ColorPalette.PURPLE, ColorPalette.WHITE);

		contenido.add(Box.createVerticalStrut(spacing));
		contenido.add(puntuadorWrapper);
		contenido.add(Box.createVerticalStrut(spacing));
		contenido.add(comentarioLabel);
		contenido.add(scrollComentario);
		contenido.add(Box.createVerticalStrut(spacing * 2));
		contenido.add(btnEnviar);
		contenido.add(Box.createVerticalStrut(spacing));

		contenido.setBorder(BorderFactory.createEmptyBorder(spacing * 2, spacing * 2, spacing * 2, spacing * 2));

		int cornerRadius = t.getPixelsWidth(CORNER_RADIUS_MACRO);
		RoundedPanel card = new RoundedPanel(cornerRadius);
		card.setOpaque(true);
		card.setBackground(ColorPalette.WHITE.getColor());
		card.setLayout(new BorderLayout());

		/* Cabecera */
		JLabel header = new JLabel("Añadir Reseña", JLabel.CENTER);
		header.setFont(Fonts.TITLE3.getFont());
		header.setForeground(ColorPalette.WHITE.getColor());
		header.setOpaque(true);
		header.setBackground(ColorPalette.BG_BLUE.getColor());

		int headerPadding = t.getPixelsHeight(0.01);
		header.setBorder(BorderFactory.createEmptyBorder(headerPadding, headerPadding, headerPadding, headerPadding));

		card.add(header, BorderLayout.NORTH);
		card.add(contenido, BorderLayout.CENTER);

		add(card);
	}

	/**
	 * Obtiene Comentario.
	 *
	 * @return valor de Comentario, el texto ingresado por el usuario como reseña.
	 */
	public String getComentario() {
		return campoComentario.getText();
	}

	/**
	 * Obtiene Valoracion.
	 *
	 * @return valor de Valoracion, la puntuación seleccionada mediante las estrellas (0-5).
	 */
	public double getValoracion() {
		return (double) puntuador.getStar();
	}

	/**
	 * Establece Controlador.
	 *
	 * @param l controlador que manejará los eventos del botón de enviar reseña.
	 */
	public void setControlador(ActionListener l) {
		btnEnviar.addActionListener(l);
	}
}