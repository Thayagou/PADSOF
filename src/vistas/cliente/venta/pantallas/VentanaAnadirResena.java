package vistas.cliente.venta.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.herramientas.*;
import vistas.common.TiendaFrame;

public class VentanaAnadirResena extends JPanel {
	private static final long serialVersionUID = 1L;

	/* ─── Proporciones ───────────────────────── */
	private static final double PANEL_WIDTH = 0.4;
	private static final double PANEL_HEIGHT = 0.5;
	private static final double SPACING = 0.02;
	private static final double TEXTAREA_HEIGHT = 0.2;
	private static final double BTN_HEIGHT = 0.05;

	/* ─── Componentes ───────────────────────── */
	private JTextArea campoComentario;
	private JSpinner spinnerEstrellas;
	private JButton btnEnviar;

	public VentanaAnadirResena() {
		setLayout(new GridBagLayout()); /* centrar en pantalla */
		setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();

		int panelW = t.getPixelsWidth(PANEL_WIDTH);
		int panelH = t.getPixelsHeight(PANEL_HEIGHT);
		int spacing = t.getPixelsHeight(SPACING);
		int textH = t.getPixelsHeight(TEXTAREA_HEIGHT);
		int btnH = t.getPixelsHeight(BTN_HEIGHT);

		/* ─── Panel principal ───────────────────────── */
		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setBackground(ColorPalette.CARD_LIGHT.getColor());
		contenido.setPreferredSize(new Dimension(panelW, panelH));

		/* ─── Campo comentario ───────────────────────── */
		JLabel lblComentario = ButtonFactory.newLeftAlignedLabel("Comentario", Fonts.BOLD);

		campoComentario = new JTextArea();
		campoComentario.setFont(Fonts.TEXT.getFont());
		campoComentario.setLineWrap(true);
		campoComentario.setWrapStyleWord(true);
		campoComentario.setBackground(ColorPalette.WHITE.getColor());
		campoComentario.setForeground(ColorPalette.BLACK.getColor());

		JScrollPane scrollComentario = new JScrollPane(campoComentario);
		scrollComentario.setPreferredSize(new Dimension(panelW, textH));
		scrollComentario.setBorder(BorderFactory.createLineBorder(ColorPalette.LIGHT_GREY.getColor()));

		/* ─── Spinner estrellas ───────────────────────── */
		JLabel lblEstrellas = ButtonFactory.newLeftAlignedLabel("Valoración (0 - 5)", Fonts.BOLD);

		SpinnerNumberModel model = new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5);
		spinnerEstrellas = new JSpinner(model);
		spinnerEstrellas.setFont(Fonts.TEXT.getFont());

		/* ─── Botón enviar ───────────────────────── */
		btnEnviar = ButtonFactory.newRoundedButton("Enviar reseña", btnH, panelW, 1);
		btnEnviar.setActionCommand("enviar");
		ButtonFactory.paintButton(btnEnviar, ColorPalette.PURPLE, ColorPalette.WHITE);

		/* ─── Layout interno ───────────────────────── */
		contenido.add(Box.createVerticalStrut(spacing));
		contenido.add(lblComentario);
		contenido.add(Box.createVerticalStrut(spacing));
		contenido.add(scrollComentario);
		contenido.add(Box.createVerticalStrut(spacing));
		contenido.add(lblEstrellas);
		contenido.add(Box.createVerticalStrut(spacing));
		contenido.add(spinnerEstrellas);
		contenido.add(Box.createVerticalStrut(spacing * 2));
		contenido.add(btnEnviar);
		contenido.add(Box.createVerticalStrut(spacing));

		/* ─── Ventana con cabecera ───────────────────────── */
		JPanel ventana = PanelFactory.getVentanaConCabecera("Añadir Reseña", contenido);

		add(ventana);
	}

	/* ─── Getters útiles ───────────────────────── */

	public String getComentario() {
		return campoComentario.getText();
	}

	public double getValoracion() {
		return (double) spinnerEstrellas.getValue();
	}

	public void setControlador(ActionListener l) {
		btnEnviar.addActionListener(l);
	}
}