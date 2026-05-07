package vistas.common.assets;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.*;

/**
 * Panel que muestra un preview de la imagen seleccionada junto con un botón
 * para abrir el explorador de archivos y otro para confirmar.
 *
 * Este panel se usa dentro de un JDialog modal, por lo que el hilo llamante
 * queda bloqueado hasta que el usuario confirme o cancele.
 */
public class PanelCargaImagen extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final double PREVIEW_W = 0.20;
	private static final double PREVIEW_H = 0.22;
	private static final double BTN_W = 0.14;
	private static final double BTN_H = 0.04;
	private static final double GAP = 0.015;
	private static final double PADDING = 0.02;

	private JButton btnSeleccionar;
	private JButton btnConfirmar;
	private JButton btnCancelar;

	private JLabel lblPreview;
	private JLabel lblNombreFichero;

	/**
	 * @param tipo Tipo de objeto para mostrar en el título (ej. "Producto").
	 * @param id   ID del objeto para mostrar en el título.
	 */
	public PanelCargaImagen(String tipo, String id) {
		TiendaFrame t = TiendaFrame.getInstance();

		int previewW = t.getPixelsWidth(PREVIEW_W);
		int previewH = t.getPixelsHeight(PREVIEW_H);
		int btnW = t.getPixelsWidth(BTN_W);
		int btnH = t.getPixelsHeight(BTN_H);
		int gap = t.getPixelsWidth(GAP);
		int pad = t.getPixelsWidth(PADDING);

		setOpaque(false);
		setLayout(new BorderLayout(gap, gap));
		setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));

		add(buildCabecera(tipo, id), BorderLayout.NORTH);
		add(buildCentro(previewW, previewH, gap), BorderLayout.CENTER);
		add(buildBotones(btnW, btnH, gap), BorderLayout.SOUTH);
	}

	private JPanel buildCabecera(String tipo, String id) {
		JLabel titulo = ButtonFactory.newLabel("Cargar imagen para " + tipo + " (id: " + id + ")", Fonts.BOLD);
		titulo.setForeground(ColorPalette.DARK_GREY.getColor());

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setOpaque(false);
		panel.add(titulo);
		return panel;
	}

	private JPanel buildCentro(int previewW, int previewH, int gap) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);

		/* Preview de la imagen seleccionada */
		lblPreview = new JLabel("Sin imagen seleccionada", JLabel.CENTER);
		lblPreview.setFont(Fonts.TEXT.getFont());
		lblPreview.setForeground(ColorPalette.DARK_GREY.getColor());
		lblPreview.setBackground(ColorPalette.CARD_DARK.getColor());
		lblPreview.setOpaque(true);
		lblPreview.setPreferredSize(new Dimension(previewW, previewH));
		lblPreview.setMinimumSize(new Dimension(previewW, previewH));
		lblPreview.setMaximumSize(new Dimension(previewW, previewH));
		lblPreview.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(lblPreview);

		panel.add(Box.createVerticalStrut(gap));

		/* Nombre del fichero seleccionado */
		lblNombreFichero = ButtonFactory.newLabel("Ningún fichero seleccionado", Fonts.SMALL);
		lblNombreFichero.setForeground(ColorPalette.GREY.getColor());
		lblNombreFichero.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(lblNombreFichero);

		return panel;
	}

	private JPanel buildBotones(int btnW, int btnH, int gap) {
		btnSeleccionar = ButtonFactory.newRoundedButton("Seleccionar archivo", btnH, btnW, 0.5);
		ButtonFactory.paintButton(btnSeleccionar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnSeleccionar, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		btnSeleccionar.setActionCommand("Seleccionar");

		btnConfirmar = ButtonFactory.newRoundedButton("Confirmar", btnH, btnW, 0.5);
		ButtonFactory.paintButton(btnConfirmar, ColorPalette.PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnConfirmar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);
		btnConfirmar.setActionCommand("Confirmar");
		btnConfirmar.setEnabled(false);

		btnCancelar = ButtonFactory.newRoundedButton("Cancelar", btnH, btnW, 0.5);
		ButtonFactory.paintButton(btnCancelar, ColorPalette.CARD_DARK, ColorPalette.DARK_GREY);
		ButtonFactory.addMouseMecanics(btnCancelar, ColorPalette.CARD_DARK, ColorPalette.CARD_DARK_HOVER);
		btnCancelar.setActionCommand("Cancelar");

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, 0));
		panel.setOpaque(false);
		panel.add(btnSeleccionar);
		panel.add(btnConfirmar);
		panel.add(btnCancelar);
		return panel;
	}

	/**
	 * Actualiza el preview con la imagen dada y activa el botón Confirmar.
	 *
	 * @param imagen     Imagen a mostrar en el preview.
	 * @param nombreFich Nombre del fichero para mostrarlo bajo el preview.
	 */
	public void setPreview(ImageIcon imagen, String nombreFich) {
		lblPreview.setIcon(imagen);
		lblPreview.setText(null);
		lblNombreFichero.setText(nombreFich);
		btnConfirmar.setEnabled(true);
		revalidate();
		repaint();
	}

	/** Resetea el preview al estado inicial y desactiva el botón Confirmar. */
	public void limpiarPreview() {
		lblPreview.setIcon(null);
		lblPreview.setText("Sin imagen seleccionada");
		lblNombreFichero.setText("Ningún fichero seleccionado");
		btnConfirmar.setEnabled(false);
		revalidate();
		repaint();
	}

	/** Conecta el controlador a los tres botones. */
	public void setControlador(ActionListener c) {
		btnSeleccionar.addActionListener(c);
		btnConfirmar.addActionListener(c);
		btnCancelar.addActionListener(c);
	}
}
