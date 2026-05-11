package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import vistas.common.app.TiendaFrame;
import vistas.common.components.InvisibleCheckBox;
import vistas.common.displays.PanelProducto;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

/**
 * Esta clase representa una ventana que permite añadir o modificar un producto
 */
public class VentanaAnadirProductoIndividual extends JPanel {
	private static final long serialVersionUID = 1L;
	/** Nombre de la acción de confirmar */
	public static final String CONFIRMAR_ACTION = "Confirmar";
	/** Nombre de la acción de seleccionar foto */
	public static final String ACTION_SELECCIONAR_FOTO = "SeleccionarFoto";
	/** Constante FOTO_ANCHO */
	private static final double FOTO_ANCHO = 0.2;
	/** Constante FOTO_ALTO */
	private static final double FOTO_ALTO = 0.25;

	/** Botón de elegir foto */
	private JButton btnFoto;
	/** Preview de la foto del producto */
	private JLabel fotoPreviewLabel;
	/** Campo nombre del producto */
	private JTextField nombreField;
	/** Campo descripción del producto */
	private JTextArea descField;
	/** Campo precio del producto */
	private JTextField precioField;
	/** Campo unidades del producto */
	private JTextField stockField;
	/** Campo tipo de producto */
	private JComboBox<String> tipoProducto;
	/** Campo en caso de que el tipo de producto sea fijo*/
	private String tipoFijo;
	/** Selección de las categorías */
	private List<InvisibleCheckBox> checkCategorias = new ArrayList<>();
	/** Paneles de características específicas */
	private List<JComponent> especFields = new ArrayList<>();
	/** Botón de confirmar */
	private JButton btnConfirmar;
	/** Panel de características específicas */
	private JPanel especPanel;
	/** Nombre de las características específicas para cada tipo de producto */
	private String[] espComic, espJuego, espFigura, espPack;
	/** Paneles con los productos de la tienda */
	private PanelProductoAnadirAPack[] productos;
	/** Paneles con los productos que contiene un pack */
	private PanelProducto[] productosPack;
	/** Paneles de los productos ya seleccionados al modificar un pack */
	private PanelProducto[] productosSeleccionados;
	/** Indica si la ventana es para una modificar o añadir un producto */
	private boolean isModificacion;

	/**
	 * Constructor de la ventana de añadir un producto
	 * @param categorias Nombre de las categorías de la tienda
	 * @param tiposProductos Nombre de los tipos de productos de la tienda
	 * @param espComic Características específicas de un Comic
	 * @param espJuego Características específicas de un Juego
	 * @param espFigura Características específicas de una Figura
	 * @param espPack Características específicas de un Pack
	 * @param tiposJuego Nombres de los tipos de juegos
	 * @param productos Paneles con los productos de la tienda
	 */
	public VentanaAnadirProductoIndividual(String[] categorias, String[] tiposProductos, String[] espComic,
			String[] espJuego, String[] espFigura, String[] espPack, String[] tiposJuego, PanelProductoAnadirAPack[] productos) {
		this("Nombre", "Descripción", new String[0], categorias, "0.0 €", "0 uds", "", tiposProductos, new String[0],
				espComic, espJuego, espFigura, espPack, tiposJuego, productos, new PanelProducto[0], false);
	}

	/**
	 * Constructor de la ventana de modificar un producto
	 * @param nombre Nombre del producto
	 * @param desc Descripción del producto
	 * @param catSeleccionadas Categorías del producto
	 * @param categorias Nombre de las categorías de la tienda
	 * @param precio Precio del producto
	 * @param uds Unidades del producto
	 * @param tipo Tipo fijo del producto
	 * @param espValores Características específicas del producto
	 * @param espComic Características específicas de un Comic
	 * @param espJuego Características específicas de un Juego
	 * @param espFigura Características específicas de una Figura
	 * @param espPack Características específicas de un Pack
	 * @param tiposJuego Nombres de los tipos de juegos
	 * @param productos Paneles con los productos de la tienda
	 * @param isModificacion Indica si la ventana es para una modificar o añadir un producto
	 */
	public VentanaAnadirProductoIndividual(String nombre, String desc, String[] catSeleccionadas, String[] categorias,
			String precio, String uds, String tipo, String[] tiposProducto, String[] espValores, String[] espComic,
			String[] espJuego, String[] espFigura, String[] espPack, String[] tiposJuego, PanelProductoAnadirAPack[] productos,
			PanelProducto[] productosSeleccionados, boolean isModificacion) {

		this.espComic = espComic;
		this.espJuego = espJuego;
		this.espFigura = espFigura;
		this.espPack = espPack;
		this.isModificacion = isModificacion;
		this.productosSeleccionados = (productosSeleccionados != null) ? productosSeleccionados : new PanelProducto[0];
		this.tipoFijo = (tipo != null && !tipo.isEmpty()) ? tipo : "";

		setOpaque(false);
		setLayout(new BorderLayout(10, 10));

		// Panel izquierdo: url imagen, nombre, descripción
		JPanel izqda = new JPanel();
		izqda.setLayout(new BoxLayout(izqda, BoxLayout.Y_AXIS));
		izqda.setBackground(ColorPalette.CARD_LIGHT.getColor());
		izqda.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		// Crear panel foto
		int fotoW = TiendaFrame.getInstance().getPixelsWidth(FOTO_ANCHO);
		int fotoH = TiendaFrame.getInstance().getPixelsHeight(FOTO_ALTO);

		JPanel contenedorFoto = new JPanel(new BorderLayout());
		contenedorFoto.setOpaque(false);
		contenedorFoto.setMaximumSize(new Dimension(Integer.MAX_VALUE, fotoH));
		contenedorFoto.setAlignmentX(Component.LEFT_ALIGNMENT);

		fotoPreviewLabel = new JLabel("Sin imagen", JLabel.CENTER);
		fotoPreviewLabel.setFont(Fonts.TEXT.getFont());
		fotoPreviewLabel.setForeground(ColorPalette.DARK_GREY.getColor());
		fotoPreviewLabel.setBackground(ColorPalette.CARD_DARK.getColor());
		fotoPreviewLabel.setOpaque(true);
		fotoPreviewLabel.setPreferredSize(new Dimension(fotoW, fotoH));
		fotoPreviewLabel.setBorder(BorderFactory.createLineBorder(ColorPalette.GREY.getColor()));

		btnFoto = new JButton();
		btnFoto.setActionCommand(ACTION_SELECCIONAR_FOTO);
		btnFoto.setOpaque(false);
		btnFoto.setContentAreaFilled(false);
		btnFoto.setBorderPainted(false);
		btnFoto.setFocusPainted(false);
		btnFoto.setPreferredSize(new Dimension(fotoW, fotoH));

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(fotoW, fotoH));
		fotoPreviewLabel.setBounds(0, 0, fotoW, fotoH);
		btnFoto.setBounds(0, 0, fotoW, fotoH);
		layeredPane.add(fotoPreviewLabel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(btnFoto, JLayeredPane.PALETTE_LAYER);

		contenedorFoto.add(layeredPane, BorderLayout.CENTER);

		nombreField = ButtonFactory.newTextField(nombre, Fonts.TEXT);
		nombreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nombreField.getPreferredSize().height));
		nombreField.setAlignmentX(Component.LEFT_ALIGNMENT);

		descField = ButtonFactory.newTextArea(desc, Fonts.TEXT);
		descField.setFont(Fonts.SMALL.getFont());
		descField.setLineWrap(true);
		descField.setWrapStyleWord(true);
		descField.setRows(5);

		//String fotoSeleccionada = ControlCargaImagen.abrir("Producto");
		JLabel urlLabel = ButtonFactory.newLabel("Imagen del producto:", Fonts.BOLD);
		urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		izqda.add(Box.createVerticalStrut(8));
		izqda.add(urlLabel);
		izqda.add(Box.createVerticalStrut(4));
		izqda.add(contenedorFoto);
		izqda.add(Box.createVerticalStrut(8));

		JLabel nombreLabel = ButtonFactory.newLabel("Nombre:", Fonts.BOLD);
		nombreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		izqda.add(nombreLabel);
		izqda.add(Box.createVerticalStrut(4));
		izqda.add(nombreField);
		izqda.add(Box.createVerticalStrut(8));

		JLabel descLabel = ButtonFactory.newLabel("Descripción:", Fonts.BOLD);
		descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		izqda.add(descLabel);
		izqda.add(Box.createVerticalStrut(4));
		JScrollPane scrollDesc = new JScrollPane(descField);
		scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		izqda.add(scrollDesc);
		izqda.add(Box.createVerticalStrut(8));

		// Panel central: categorías, precio, stock
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setBackground(ColorPalette.CARD_LIGHT.getColor());
		centro.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel lblCats = ButtonFactory.newLabel("Seleccionar categorías:", Fonts.BOLD);
		lblCats.setAlignmentX(Component.LEFT_ALIGNMENT);
		centro.add(Box.createVerticalStrut(8));
		centro.add(lblCats);
		centro.add(Box.createVerticalStrut(4));

		List<String> catSelList = Arrays.asList(catSeleccionadas);
		for (String cat : categorias) {
			InvisibleCheckBox cb = ButtonFactory.newInvisibleCheckBox(cat, cat, ColorPalette.BLACK,
					ColorPalette.CARD_DARK);

			cb.setAlignmentX(Component.LEFT_ALIGNMENT);
			checkCategorias.add(cb);

			JPanel wrapper = new JPanel(new BorderLayout());
			wrapper.setOpaque(false);
			wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, cb.getPreferredSize().height));
			wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
			wrapper.add(cb, BorderLayout.CENTER);
			wrapper.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cb.toggleSelection();
				}
			});
			centro.add(wrapper);
		}
		for (InvisibleCheckBox cb : checkCategorias) {
			cb.setSeleccionado(catSelList.contains(cb.getText()));
		}

		JPanel panelCategorias = new JPanel();
		panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
		panelCategorias.setOpaque(false);
		for (InvisibleCheckBox cb : checkCategorias) {
			JPanel wrapper = new JPanel(new BorderLayout());
			wrapper.setOpaque(false);
			wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, cb.getPreferredSize().height));
			wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
			wrapper.add(cb, BorderLayout.CENTER);
			wrapper.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cb.toggleSelection();
				}
			});
			panelCategorias.add(wrapper);
		}

		JScrollPane scrollCategorias = PanelFactory.getScroll(panelCategorias);
		scrollCategorias.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollCategorias.setOpaque(false);
		scrollCategorias.getViewport().setOpaque(false);
		scrollCategorias.setBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()));
		scrollCategorias.setMaximumSize(new Dimension(Integer.MAX_VALUE, TiendaFrame.getInstance().getPixelsHeight(0.2)));
		centro.add(scrollCategorias);

		centro.add(Box.createVerticalStrut(16));

		centro.add(Box.createVerticalStrut(16));
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		centro.add(sep);
		centro.add(Box.createVerticalStrut(16));

		JLabel lblPrecio = ButtonFactory.newLabel("Precio:", Fonts.BOLD);
		lblPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);
		centro.add(lblPrecio);
		centro.add(Box.createVerticalStrut(4));
		precioField = ButtonFactory.newTextField(precio, Fonts.BOLD);
		precioField.setAlignmentX(Component.LEFT_ALIGNMENT);
		precioField.setMaximumSize(new Dimension(Integer.MAX_VALUE, precioField.getPreferredSize().height));
		centro.add(precioField);
		centro.add(Box.createVerticalStrut(12));

		JLabel lblStock = ButtonFactory.newLabel("Unidades en stock:", Fonts.BOLD);
		lblStock.setAlignmentX(Component.LEFT_ALIGNMENT);
		centro.add(lblStock);
		centro.add(Box.createVerticalStrut(4));
		stockField = ButtonFactory.newTextField(uds, Fonts.BOLD);
		stockField.setAlignmentX(Component.LEFT_ALIGNMENT);
		stockField.setMaximumSize(new Dimension(Integer.MAX_VALUE, stockField.getPreferredSize().height));
		centro.add(stockField);
		centro.add(Box.createVerticalGlue());

		// Panel derecho: tipo + especificaciones
		JPanel dcha = new JPanel();
		dcha.setLayout(new BoxLayout(dcha, BoxLayout.Y_AXIS));
		dcha.setBackground(ColorPalette.CARD_LIGHT.getColor());
		dcha.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		dcha.add(Box.createVerticalStrut(8));
		if (!tipoFijo.isEmpty()) {
			JLabel lblTipo = ButtonFactory.newLabel(tipoFijo, Fonts.TEXT);
			lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
			lblTipo.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblTipo.getPreferredSize().height));
			dcha.add(lblTipo);
			this.tipoProducto = null;
		} else {
			this.tipoProducto = ButtonFactory.newComboBox(Fonts.TEXT, tiposProducto);
			this.tipoProducto
					.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.tipoProducto.getPreferredSize().height));
			this.tipoProducto.setAlignmentX(Component.LEFT_ALIGNMENT);
			dcha.add(this.tipoProducto);
		}
		dcha.add(Box.createVerticalStrut(12));

		// Panel de especificaciones separado para poder reconstruirlo
		especPanel = new JPanel();
		especPanel.setLayout(new BoxLayout(especPanel, BoxLayout.Y_AXIS));
		especPanel.setOpaque(false);
		especPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		String tipoInicial = !tipoFijo.isEmpty() ? tipoFijo : (tiposProducto.length > 0 ? tiposProducto[0] : "");
		construirEspecificaciones(tipoInicial, espValores, tiposJuego, productos);

		dcha.add(especPanel);
		dcha.add(Box.createVerticalGlue());

		if (this.tipoProducto != null) {
			this.tipoProducto.addActionListener(e -> {
				String sel = (String) this.tipoProducto.getSelectedItem();
				construirEspecificaciones(sel, new String[0], tiposJuego, productos);
			});
		}

		// Botones
		JPanel dchaConBoton = new JPanel(new BorderLayout(0, 10));
		dchaConBoton.setOpaque(false);

		int height = TiendaFrame.getInstance().getHeight();
		int width = TiendaFrame.getInstance().getWidth();
		btnConfirmar = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (height * 0.08), (int) (width * 0.1),
				0.5);
		ButtonFactory.paintButton(btnConfirmar, ColorPalette.PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(btnConfirmar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

		// Panel del botón centrado
		JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
		botonPanel.setOpaque(false);
		botonPanel.add(btnConfirmar);

		dchaConBoton.add(dcha, BorderLayout.CENTER);
		dchaConBoton.add(botonPanel, BorderLayout.SOUTH);

		// Columnas
		JPanel columnas = new JPanel(new GridLayout(1, 3, 10, 0));
		columnas.setOpaque(false);
		columnas.add(izqda);
		columnas.add(centro);
		columnas.add(dchaConBoton);
		columnas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		if (isModificacion) {
			JPanel ventana = PanelFactory.getVentanaConCabecera("Modificar producto", columnas);
			ventana.setOpaque(false);
			add(ventana, BorderLayout.CENTER);
		} else {
			add(columnas, BorderLayout.CENTER);
		}
	}

	/**
	 * Cosntruye los paneles de características específicas del producto
	 * @param tipo Tipo de producto
	 * @param valores Valores de las características específicas del producto
	 * @param tiposJuego Nombres de los tipos de juegos
	 * @param productos Paneles con los productos en la tienda
	 */
	private void construirEspecificaciones(String tipo, String[] valores, String[] tiposJuego,
			PanelProducto[] productos) {
		especPanel.removeAll();
		especFields.clear();

		String[] espActuales;
		if (tipo.equalsIgnoreCase("Comic"))
			espActuales = espComic;
		else if (tipo.equalsIgnoreCase("Juego"))
			espActuales = espJuego;
		else if (tipo.equalsIgnoreCase("Figura"))
			espActuales = espFigura;
		else if (tipo.equalsIgnoreCase("Pack"))
			espActuales = espPack;
		else
			espActuales = new String[0];

		for (int i = 0; i < espActuales.length; i++) {

			JLabel lbl = ButtonFactory.newLabel(espActuales[i] + ":", Fonts.TEXT);
			lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
			String valor = (valores != null && i < valores.length) ? valores[i] : "";
			JComponent tf;

			if (espActuales[i].equals("Fecha publicación")) {
				JSpinner spinner = ButtonFactory.spinnerLocalDate(Fonts.TEXT);
				spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, spinner.getPreferredSize().height));
				spinner.setAlignmentX(Component.LEFT_ALIGNMENT);

				if (!valor.isEmpty()) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						spinner.setValue(sdf.parse(valor));
					} catch (Exception ignored) {
					}
				}
				tf = spinner;

			} else if (espActuales[i].equals("Tipo de juego")) {
				JComboBox<String> combo = ButtonFactory.newComboBox(Fonts.TEXT, tiposJuego);
				if (!valor.isEmpty()) {
					combo.setSelectedItem(valor);
				}
				combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, combo.getPreferredSize().height));
				combo.setAlignmentX(Component.LEFT_ALIGNMENT);
				tf = combo;

			} else if (espActuales[i].equals("Productos")) {
				if (isModificacion) {
					// Guardar referencia para extraerValor
					productosPack = productosSeleccionados;

					// Panel contenedor: boton + resumen (solo lectura)
					JPanel selectorPanelMod = new JPanel();
					selectorPanelMod.setLayout(new BoxLayout(selectorPanelMod, BoxLayout.Y_AXIS));
					selectorPanelMod.setOpaque(false);
					selectorPanelMod.setAlignmentX(Component.LEFT_ALIGNMENT);

					// Etiqueta resumen
					String textoResumenMod = productosSeleccionados.length == 0
							? "Ningun producto en el pack"
							: productosSeleccionados.length + " producto" + (productosSeleccionados.length > 1 ? "s" : "") + " en el pack";
					JLabel lblResumenMod = ButtonFactory.newLeftAlignedLabel(textoResumenMod, Fonts.TEXT);
					lblResumenMod.setAlignmentX(Component.LEFT_ALIGNMENT);
					lblResumenMod.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblResumenMod.getPreferredSize().height));

					// Boton para ver los productos del pack
					int btnHMod = (int) (TiendaFrame.getInstance().getHeight() * 0.05);
					int btnWMod = (int) (TiendaFrame.getInstance().getWidth() * 0.12);
					JButton btnVerProductos = ButtonFactory.newRoundedButton("Ver productos del pack", btnHMod, btnWMod, 0.5);
					ButtonFactory.paintButton(btnVerProductos, ColorPalette.PURPLE, ColorPalette.WHITE);
					ButtonFactory.addMouseMecanics(btnVerProductos, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);
					btnVerProductos.setAlignmentX(Component.LEFT_ALIGNMENT);

					btnVerProductos.addActionListener(e -> {
						JPanel productosPanel = new JPanel();
						productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
						productosPanel.setOpaque(false);
						for (PanelProducto p : productosSeleccionados) {
							JPanel fila = new JPanel(new BorderLayout(8, 0));
							fila.setOpaque(false);
							fila.add(p, BorderLayout.CENTER);
							productosPanel.add(fila);
							productosPanel.add(Box.createVerticalStrut(4));
						}

						JScrollPane scrollProductos = PanelFactory.getScroll(productosPanel);
						scrollProductos.setBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()));
						scrollProductos.getViewport().setOpaque(false);
						scrollProductos.setOpaque(false);

						int dBtnHMod = (int) (TiendaFrame.getInstance().getHeight() * 0.06);
						int dBtnWMod = (int) (TiendaFrame.getInstance().getWidth() * 0.1);
						JButton btnCerrarMod = ButtonFactory.newRoundedButton("Cerrar", dBtnHMod, dBtnWMod, 0.5);
						ButtonFactory.paintButton(btnCerrarMod, ColorPalette.PURPLE, ColorPalette.WHITE);
						ButtonFactory.addMouseMecanics(btnCerrarMod, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

						JPanel botonDialogoMod = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
						botonDialogoMod.setOpaque(false);
						botonDialogoMod.add(btnCerrarMod);

						JPanel contenidoDialogoMod = new JPanel(new BorderLayout(0, 8));
						contenidoDialogoMod.setOpaque(false);
						contenidoDialogoMod.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
						contenidoDialogoMod.add(scrollProductos, BorderLayout.CENTER);
						contenidoDialogoMod.add(botonDialogoMod, BorderLayout.SOUTH);

						JDialog dialogoMod = new JDialog(TiendaFrame.getInstance(), "Productos del pack", true);
						dialogoMod.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialogoMod.setContentPane(contenidoDialogoMod);
						java.awt.Rectangle boundsMod = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
						dialogoMod.setSize(boundsMod.width, boundsMod.height);
						dialogoMod.setMinimumSize(new Dimension(boundsMod.width, boundsMod.height));
						dialogoMod.setLocation(boundsMod.x, boundsMod.y);
						btnCerrarMod.addActionListener(ev -> dialogoMod.dispose());
						dialogoMod.setVisible(true);
					});

					selectorPanelMod.add(btnVerProductos);
					selectorPanelMod.add(Box.createVerticalStrut(4));
					selectorPanelMod.add(lblResumenMod);

					tf = selectorPanelMod;

				} else {
					// Los productos seleccionados se determinan por getUds() > 0 en cada panel
					this.productos = (PanelProductoAnadirAPack[]) productos;

					// Panel contenedor: boton + resumen
					JPanel selectorPanel = new JPanel();
					selectorPanel.setLayout(new BoxLayout(selectorPanel, BoxLayout.Y_AXIS));
					selectorPanel.setOpaque(false);
					selectorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

					// Etiqueta resumen de seleccion
					JLabel lblResumen = ButtonFactory.newLeftAlignedLabel("Ningun producto seleccionado", Fonts.TEXT);
					lblResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
					lblResumen.setMaximumSize(new Dimension(Integer.MAX_VALUE, lblResumen.getPreferredSize().height));

					// Boton para abrir el dialogo de seleccion
					int btnH = (int) (TiendaFrame.getInstance().getHeight() * 0.05);
					int btnW = (int) (TiendaFrame.getInstance().getWidth() * 0.12);
					JButton btnSeleccionar = ButtonFactory.newRoundedButton("Seleccionar productos", btnH, btnW, 0.5);
					ButtonFactory.paintButton(btnSeleccionar, ColorPalette.PURPLE, ColorPalette.WHITE);
					ButtonFactory.addMouseMecanics(btnSeleccionar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);
					btnSeleccionar.setAlignmentX(Component.LEFT_ALIGNMENT);

					btnSeleccionar.addActionListener(e -> {
						// Construir el scroll con los paneles de productos
						JPanel productosPanel = new JPanel();
						productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
						productosPanel.setOpaque(false);
						for (PanelProductoAnadirAPack p : this.productos) {
							JPanel fila = new JPanel(new BorderLayout(8, 0));
							fila.setOpaque(false);
							fila.add(p, BorderLayout.CENTER);
							productosPanel.add(fila);
							productosPanel.add(Box.createVerticalStrut(4));
						}

						JScrollPane scrollProductos = PanelFactory.getScroll(productosPanel);
						scrollProductos.setBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()));
						scrollProductos.getViewport().setOpaque(false);
						scrollProductos.setOpaque(false);

						// Boton de confirmar seleccion dentro del dialogo
						int dBtnH = (int) (TiendaFrame.getInstance().getHeight() * 0.06);
						int dBtnW = (int) (TiendaFrame.getInstance().getWidth() * 0.1);
						JButton btnCerrar = ButtonFactory.newRoundedButton("Confirmar seleccion", dBtnH, dBtnW, 0.5);
						ButtonFactory.paintButton(btnCerrar, ColorPalette.PURPLE, ColorPalette.WHITE);
						ButtonFactory.addMouseMecanics(btnCerrar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

						JPanel botonDialogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
						botonDialogo.setOpaque(false);
						botonDialogo.add(btnCerrar);

						JPanel contenidoDialogo = new JPanel(new BorderLayout(0, 8));
						contenidoDialogo.setOpaque(false);
						contenidoDialogo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
						contenidoDialogo.add(scrollProductos, BorderLayout.CENTER);
						contenidoDialogo.add(botonDialogo, BorderLayout.SOUTH);

						JDialog dialogo = new JDialog(TiendaFrame.getInstance(), "Seleccionar productos del pack", true);
						dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialogo.setContentPane(contenidoDialogo);
						java.awt.Rectangle bounds = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
						dialogo.setSize(bounds.width, bounds.height);
						dialogo.setMinimumSize(new Dimension(bounds.width, bounds.height));
						dialogo.setLocation(bounds.x, bounds.y);

						btnCerrar.addActionListener(ev -> dialogo.dispose());

						dialogo.setVisible(true);

						// Al cerrar el dialogo, actualizar el resumen
						long seleccionados = java.util.Arrays.stream(this.productos)
						        .filter(p -> p.getUds() > 0).count();
						if (seleccionados == 0) {
						    lblResumen.setText("Ningun producto seleccionado");
						} else {
						    double precioTotal = java.util.Arrays.stream(this.productos)
						            .filter(p -> p.getUds() > 0)
						            .mapToDouble(p -> p.getPrecio() * p.getUds())
						            .sum();
						    lblResumen.setText(seleccionados + " producto" + (seleccionados > 1 ? "s" : "")
						            + " seleccionado" + (seleccionados > 1 ? "s" : "")
						            + " — " + String.format("%.2f €", precioTotal));
						}
					});

					selectorPanel.add(btnSeleccionar);
					selectorPanel.add(Box.createVerticalStrut(4));
					selectorPanel.add(lblResumen);

					tf = selectorPanel;
				}
			} else {
				JTextField textField = ButtonFactory.newTextField(valor, Fonts.TEXT);
				textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
				textField.setAlignmentX(Component.LEFT_ALIGNMENT);
				tf = textField;
			}

			especFields.add(tf);
			especPanel.add(lbl);
			especPanel.add(Box.createVerticalStrut(4));
			especPanel.add(tf);
			especPanel.add(Box.createVerticalStrut(10));

		}

		especPanel.revalidate();
		especPanel.repaint();
	}

	/**
	 * Asigna un controlador a los componentes de la tienda
	 * @param c Controlador que se asigna
	 */
	public void setControlador(ActionListener c) {
		btnConfirmar.addActionListener(c);
		btnFoto.addActionListener(c);
	}

	/**
	 * Devuelve el nombre del producto
	 * @return Nombre del producto
	 */
	public String getNombre() {
		return nombreField.getText();
	}

	/**
	 * Devuelve la descripción del producto
	 * @return descripción del producto
	 */
	public String getDescripcion() {
		return descField.getText();
	}
	
	/**
	 * Actualiza la preview de la imagen del producto
	 * @param archivo Ruta de la imagen seleccionada
	 */
	public void actualizarPreview(String archivo) {
		TiendaFrame t = TiendaFrame.getInstance();
		int ancho = t.getPixelsWidth(FOTO_ANCHO);
		int alto = t.getPixelsHeight(FOTO_ALTO);
		ImageIcon icon = ButtonFactory.loadImageIconScaled(archivo, alto, ancho);
		fotoPreviewLabel.setIcon(icon);
		fotoPreviewLabel.setText(null);
		fotoPreviewLabel.revalidate();
		fotoPreviewLabel.repaint();
	}

	/**
	 * Devuelve el precio del producto
	 * @return El precio del producto
	 */
	public String getPrecio() {
		return precioField.getText().trim();
	}

	/**
	 * Devuelve las unidades del producto
	 * @return Unidades del producto
	 */
	public String getStock() {
		return stockField.getText().trim();
	}

	/**
	 * Devuelve el tipo de producto
	 * @return Tipo de producto
	 */
	public String getTipo() {
		if (tipoProducto != null)
			return (String) tipoProducto.getSelectedItem();
		return tipoFijo;
	}

	/**
	 * Devuelve un mapa con los paneles seleccionados del pack y sus unidades.
	 * Se consideran seleccionados aquellos cuyas unidades sean mayores de 0.
	 * @return Mapa de panel → unidades seleccionadas
	 */
	public Map<PanelProductoAnadirAPack, Integer> getProductosPackSeleccionados() {
		Map<PanelProductoAnadirAPack, Integer> seleccionados = new LinkedHashMap<>();
		if (productos != null) {
			for (PanelProductoAnadirAPack p : productos) {
				int uds = p.getUds();
				if (uds > 0) {
					seleccionados.put(p, uds);
				}
			}
		}
		return seleccionados;
	}

	/**
	 * Devuelve las características específicas del producto
	 * @return Valores de las especificaciones
	 */
	public String[] getEspecificaciones() {
		return especFields.stream().map(this::extraerValor).toArray(String[]::new);
	}

	/**
	 * Devuelve el valor de un componente
	 * @return Valor del componente
	 */
	private String extraerValor(JComponent c) {
		if (c instanceof JTextField)
			return ((JTextField) c).getText();
		if (c instanceof JComboBox)
			return (String) ((JComboBox<?>) c).getSelectedItem();
		if (c instanceof JSpinner)
			return new SimpleDateFormat("yyyy-MM-dd").format(((JSpinner) c).getValue());
		// Campos de productos del pack: en ambos modos tf es un JPanel
		if (c instanceof JPanel) {
			if (isModificacion && productosPack != null) {
				StringBuilder sb = new StringBuilder();
				for (PanelProducto p : productosPack) {
					sb.append(p.getNombre()).append(";");
				}
				return sb.toString();
			} else if (!isModificacion && productos != null) {
				StringBuilder sb = new StringBuilder();
				for (PanelProductoAnadirAPack p : productos) {
					if (p.getUds() > 0) {
						sb.append(p.getNombre()).append(";");
					}
				}
				return sb.toString();
			}
		}
		return "";
	}

	/**
	 * Devuelve las categorías del producto
	 * @return Categorías del producto
	 */
	public String[] getCategorias() {
		return checkCategorias.stream().filter(InvisibleCheckBox::isSelected).map(InvisibleCheckBox::getText)
				.toArray(String[]::new);
	}
}