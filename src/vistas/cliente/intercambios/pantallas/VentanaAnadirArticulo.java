package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelSelectorCajas;
import vistas.herramientas.*;

public class VentanaAnadirArticulo extends JPanel {
	private static final long serialVersionUID = 1L;

	/* Acciones para los botones */
	public static final String ACTION_CONFIRMAR = "Confirmar";
	public static final String ACTION_CANCELAR = "Cancelar";
	public static final String ACTION_SELECCIONAR_FOTO = "SeleccionarFoto";

	/* Macros de dimensiones relativas */
	private static final double FOTO_ANCHO = 0.2; /* Ancho de la foto (20% ancho pantalla) */
	private static final double FOTO_ALTO = 0.25; /* Alto de la foto (25% alto pantalla) */
	private static final double BOTON_ANCHO = 0.15; /* Ancho botones (15% ancho) */
	private static final double BOTON_ALTO = 0.05; /* Alto botones (5% alto) */
	private static final double PANEL_GAP = 0.02; /* Separación horizontal entre columnas (2% ancho) */

	/* Componentes de la vista */
	private JTextField nombreField;
	private JTextArea intercambioArea;
	private JTextArea descripcionArea;
	private PanelSelectorCajas selectorCategorias;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JButton btnFoto;
	private JLabel fotoPreviewLabel;
	private File imagenArchivo; /* Archivo temporal seleccionado, aún no guardado */

	public VentanaAnadirArticulo(String[] nombresCategorias) {
		TiendaFrame t = TiendaFrame.getInstance();

		int panelGap = t.getPixelsWidth(PANEL_GAP);

		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		/* Construir el contenido principal con tres columnas usando Grid */
		JPanel contenidoCentral = new JPanel(new GridLayout(1, 3));

		JPanel leftColumn = PanelFactory.wrapHorizontal(crearColumnaIzquierda(t), panelGap);
		contenidoCentral.add(leftColumn);

		contenidoCentral.add(crearColumnaCentral(t));

		contenidoCentral.add(crearColumnaDerecha(t, nombresCategorias));

		/* Envolver con cabecera */
		JPanel ventanaCompleta = PanelFactory.getVentanaConCabecera("Añadir artículo de segunda mano",
				PanelFactory.wrapVertical(contenidoCentral, panelGap));
		ventanaCompleta.setOpaque(true);
		
		add(ventanaCompleta, BorderLayout.CENTER);
	}

	private JPanel crearColumnaIzquierda(TiendaFrame t) {
		JPanel panel = new JPanel(new BorderLayout(0, t.getPixelsHeight(0.02)));
		panel.setOpaque(false);

		/* Bloque de la foto */
		panel.add(crearPanelFoto(t), BorderLayout.NORTH);

		/* Bloque de nombre + intercambio buscado */
		JPanel centro = new JPanel(new BorderLayout(0, t.getPixelsHeight(0.01)));
		centro.setOpaque(false);

		/* Nombre */
		JPanel nombrePanel = new JPanel(new BorderLayout(10, 0));
		nombrePanel.setOpaque(false);
		JLabel nombreLabel = ButtonFactory.newLabel("Nombre:", Fonts.TEXT);
		nombreField = new JTextField();
		nombreField.setFont(Fonts.TEXT.getFont());
		nombrePanel.add(nombreLabel, BorderLayout.NORTH);
		nombrePanel.add(nombreField, BorderLayout.CENTER);
		centro.add(nombrePanel, BorderLayout.NORTH);

		/* Intercambio buscado  */
		JPanel intercambioPanel = new JPanel(new BorderLayout());
		intercambioPanel.setOpaque(false);
		JLabel intercambioLabel = ButtonFactory.newLabel("Intercambio buscado:", Fonts.TEXT);
		intercambioLabel.setAlignmentX(LEFT_ALIGNMENT);
		intercambioArea = new JTextArea(3, 20);
		intercambioArea.setFont(Fonts.TEXT.getFont());
		intercambioArea.setLineWrap(true);
		intercambioArea.setWrapStyleWord(true);
		JScrollPane scrollIntercambio = new JScrollPane(intercambioArea);
		intercambioPanel.add(intercambioLabel, BorderLayout.NORTH);
		intercambioPanel.add(scrollIntercambio, BorderLayout.CENTER);
		centro.add(intercambioPanel, BorderLayout.CENTER);

		panel.add(centro, BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelFoto(TiendaFrame t) {
		int fotoW = t.getPixelsWidth(FOTO_ANCHO);
		int fotoH = t.getPixelsHeight(FOTO_ALTO);

		JPanel contenedor = new JPanel(new BorderLayout());
		contenedor.setOpaque(false);

		fotoPreviewLabel = new JLabel("Sin imagen", JLabel.CENTER);
		fotoPreviewLabel.setFont(Fonts.TEXT.getFont());
		fotoPreviewLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		fotoPreviewLabel.setBackground(ColorPalette.CARD_DARK.getColor());
		fotoPreviewLabel.setOpaque(true);
		fotoPreviewLabel.setPreferredSize(new Dimension(fotoW, fotoH));
		fotoPreviewLabel.setBorder(BorderFactory.createLineBorder(ColorPalette.GREY.getColor()));

		/* Botón transparente sobre la imagen (para seleccionar foto) */
		btnFoto = new JButton();
		btnFoto.setActionCommand(ACTION_SELECCIONAR_FOTO);
		btnFoto.setOpaque(false);
		btnFoto.setContentAreaFilled(false);
		btnFoto.setBorderPainted(false);
		btnFoto.setFocusPainted(false);
		btnFoto.setPreferredSize(new Dimension(fotoW, fotoH));

		/* Apilar label y botón usando JLayeredPane */
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(fotoW, fotoH));
		fotoPreviewLabel.setBounds(0, 0, fotoW, fotoH);
		btnFoto.setBounds(0, 0, fotoW, fotoH);
		layeredPane.add(fotoPreviewLabel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(btnFoto, JLayeredPane.PALETTE_LAYER);

		contenedor.add(layeredPane, BorderLayout.CENTER);
		return contenedor;
	}

	private JPanel crearColumnaCentral(TiendaFrame t) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		JLabel descLabel = ButtonFactory.newLabel("Descripción:", Fonts.TEXT);
		descripcionArea = new JTextArea(10, 30);
		descripcionArea.setFont(Fonts.TEXT.getFont());
		descripcionArea.setLineWrap(true);
		descripcionArea.setWrapStyleWord(true);
		JScrollPane scrollDesc = new JScrollPane(descripcionArea);

		panel.add(descLabel, BorderLayout.NORTH);
		panel.add(scrollDesc, BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearColumnaDerecha(TiendaFrame t, String[] nombresCategorias) {
		int topWrap = t.getPixelsHeight(0.02);
		
		JPanel panel = new JPanel(new BorderLayout(0, t.getPixelsHeight(0.02)));
		panel.setOpaque(false);
		
		/* Etiqueta arriba para bajar todo un poco */
		JPanel cabecera = new JPanel(new BorderLayout());
		cabecera.add(ButtonFactory.newLabel("Categorías:", Fonts.TEXT));
		panel.add(PanelFactory.wrapVertical(PanelFactory.wrapHorizontal(cabecera, topWrap), topWrap), BorderLayout.NORTH);

		/* Selector de categorías */
		selectorCategorias = new PanelSelectorCajas(nombresCategorias);
		panel.add(selectorCategorias, BorderLayout.CENTER);

		/* Botones: Cancelar y Confirmar */
		int btnW = t.getPixelsWidth(BOTON_ANCHO);
		int btnH = t.getPixelsHeight(BOTON_ALTO);

		btnConfirmar = ButtonFactory.newRoundedButton("Confirmar", btnH, btnW, 1);
		btnConfirmar.setActionCommand(ACTION_CONFIRMAR);
		ButtonFactory.paintButton(btnConfirmar, ColorPalette.PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnConfirmar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

		btnCancelar = ButtonFactory.newRoundedButton("Cancelar", btnH, btnW, 1);
		btnCancelar.setActionCommand(ACTION_CANCELAR);
		ButtonFactory.paintButton(btnCancelar, ColorPalette.CARD_DARK, ColorPalette.DARK_GREY);
		ButtonFactory.addMouseMecanics(btnCancelar, ColorPalette.CARD_DARK, ColorPalette.CARD_DARK_HOVER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, t.getPixelsWidth(0.02), 0));
		panelBotones.setOpaque(false);
		panelBotones.add(btnConfirmar);
		panelBotones.add(btnCancelar);
		panel.add(panelBotones, BorderLayout.SOUTH);

		return panel;
	}

	public void setControlador(ActionListener c) {
		btnConfirmar.addActionListener(c);
		btnCancelar.addActionListener(c);
		btnFoto.addActionListener(c);
	}

	public void actualizarPreview(String archivo) {
		TiendaFrame t = TiendaFrame.getInstance();
		int ancho = t.getPixelsWidth(FOTO_ANCHO);
		int alto = t.getPixelsHeight(FOTO_ALTO);
		ImageIcon icon = ButtonFactory.loadImageIconScaled(archivo, alto, ancho);
		fotoPreviewLabel.setIcon(icon);
		fotoPreviewLabel.setText(null);
	}

	/* Getters para obtener los datos del formulario */
	public String getNombre() {
		return nombreField.getText().trim();
	}

	public String getIntercambioBuscado() {
		return intercambioArea.getText().trim();
	}

	public String getDescripcion() {
		return descripcionArea.getText().trim();
	}

	public String[] getCategoriasSeleccionadas() {
		return selectorCategorias.getCategoriasSeleccionadas();
	}

	public File getImagenArchivo() {
		return imagenArchivo;
	}

	public void limpiarFormulario() {
		nombreField.setText("");
		intercambioArea.setText("");
		descripcionArea.setText("");
		selectorCategorias.limpiarSeleccion();
		imagenArchivo = null;
		fotoPreviewLabel.setIcon(null);
		fotoPreviewLabel.setText("Sin imagen");
	}
}