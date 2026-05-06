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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import vistas.common.InvisibleCheckBox;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;
import vistas.herramientas.PanelFactory;

public class VentanaAnadirProductoIndividual extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String CONFIRMAR_ACTION = "Confirmar";

	private JTextField nombreField;
	private JTextArea descField;
	private JTextField precioField;
	private JTextField stockField;
	private JTextField imagenField;
	private JComboBox<String> tipoProducto;
	private String tipoFijo;
	private List<InvisibleCheckBox> checkCategorias = new ArrayList<>();
	private List<JComponent> especFields = new ArrayList<>();
	private JButton btnConfirmar;
	private JPanel especPanel;
	private String[] espComic, espJuego, espFigura, espPack;
	private List<JCheckBox> checkProductosPack = new ArrayList<>();
	private PanelProducto[] productos;
	private PanelProducto[] productosPack;
	private boolean isModificacion;

	public VentanaAnadirProductoIndividual(String[] categorias, String[] tiposProductos, String[] espComic,
			String[] espJuego, String[] espFigura, String[] espPack, String[] tiposJuego, PanelProducto[] productos) {
		this("Nombre", "Descripción", new String[0], categorias, "0.0", "0", "", tiposProductos, new String[0],
				espComic, espJuego, espFigura, espPack, tiposJuego, productos, false);
	}

	public VentanaAnadirProductoIndividual(String nombre, String desc, String[] catSeleccionadas, String[] categorias,
			String precio, String uds, String tipo, String[] tiposProducto, String[] espValores, String[] espComic,
			String[] espJuego, String[] espFigura, String[] espPack, String[] tiposJuego, PanelProducto[] productos,
			boolean isModificacion) {

		this.espComic = espComic;
		this.espJuego = espJuego;
		this.espFigura = espFigura;
		this.espPack = espPack;
		this.isModificacion = isModificacion;
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

		imagenField = ButtonFactory.newTextField("URL imagen", Fonts.TEXT);
		imagenField.setMaximumSize(new Dimension(Integer.MAX_VALUE, imagenField.getPreferredSize().height));
		imagenField.setAlignmentX(Component.LEFT_ALIGNMENT);

		nombreField = ButtonFactory.newTextField(nombre, Fonts.TEXT);
		nombreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nombreField.getPreferredSize().height));
		nombreField.setAlignmentX(Component.LEFT_ALIGNMENT);

		descField = ButtonFactory.newTextArea(desc, Fonts.TEXT);
		descField.setFont(Fonts.SMALL.getFont());
		descField.setLineWrap(true);
		descField.setWrapStyleWord(true);
		descField.setRows(5);

		JLabel urlLabel = ButtonFactory.newLabel("URL de la imagen:", Fonts.BOLD);
		urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		izqda.add(Box.createVerticalStrut(8));
		izqda.add(urlLabel);
		izqda.add(Box.createVerticalStrut(4));
		izqda.add(imagenField);
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
				JSpinner spinner = ButtonFactory.spinnerLocalDate(Fonts.BOLD);
				spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, spinner.getPreferredSize().height));
				spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
				System.out.println("Valor fecha: " + valor);
				System.out.println("Modelo: " + spinner.getModel().getClass());
				System.out.println("Valor actual tipo: " + spinner.getValue().getClass());

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
					JPanel productosPanel = new JPanel();
					productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
					productosPanel.setOpaque(false);
					
					List<PanelProducto> nuevosProductos = new LinkedList<>();
					for (PanelProducto p : productos) {
						if (!valor.isEmpty() && valor.contains(p.getNombre())) {
							JPanel fila = new JPanel(new BorderLayout(8, 0));
							fila.setOpaque(false);
							fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, p.getPreferredSize().height));
							fila.setAlignmentX(Component.LEFT_ALIGNMENT);
							fila.add(p, BorderLayout.CENTER);
							productosPanel.add(fila);
							productosPanel.add(Box.createVerticalStrut(4));
							nuevosProductos.add(p);
						}
					}

					JScrollPane scrollProductos = PanelFactory.getScroll(productosPanel);
					scrollProductos.setAlignmentX(Component.LEFT_ALIGNMENT);
					scrollProductos.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) (HEIGHT * 0.3)));
					scrollProductos.setBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()));
					scrollProductos.getViewport().setOpaque(false);
					scrollProductos.setOpaque(false);

					productosPack = nuevosProductos.toArray(new PanelProducto[0]);
					tf = scrollProductos;

				} else {
					JPanel productosPanel = new JPanel();
					productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
					productosPanel.setOpaque(false);

					List<JCheckBox> checkProductos = new ArrayList<>();

					for (PanelProducto p : productos) {
						JPanel fila = new JPanel(new BorderLayout(8, 0));
						fila.setOpaque(false);
						fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, p.getPreferredSize().height));
						fila.setAlignmentX(Component.LEFT_ALIGNMENT);

						JCheckBox cb = new JCheckBox();
						cb.setOpaque(false);
						cb.setFocusPainted(false);
						checkProductos.add(cb);

						fila.add(cb, BorderLayout.WEST);
						fila.add(p, BorderLayout.CENTER);
						productosPanel.add(fila);
						productosPanel.add(Box.createVerticalStrut(4));
					}

					JScrollPane scrollProductos = PanelFactory.getScroll(productosPanel);
					scrollProductos.setAlignmentX(Component.LEFT_ALIGNMENT);
					scrollProductos.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) (HEIGHT * 0.3)));
					scrollProductos.setBorder(BorderFactory.createLineBorder(ColorPalette.PURPLE.getColor()));
					scrollProductos.getViewport().setOpaque(false);
					scrollProductos.setOpaque(false);

					// Guardar referencia a los checks para el getter
					this.checkProductosPack = checkProductos;
					this.productos = productos;

					tf = scrollProductos;
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

	public void setControlador(ActionListener c) {
		btnConfirmar.addActionListener(c);
	}

	public String getNombre() {
		return nombreField.getText();
	}

	public String getDescripcion() {
		return descField.getText();
	}

	public String getImagen() {
		return imagenField.getText();
	}

	public String getPrecio() {
		return precioField.getText().trim();
	}

	public String getStock() {
		return stockField.getText().trim();
	}

	public String getTipo() {
		if (tipoProducto != null)
			return (String) tipoProducto.getSelectedItem();
		return tipoFijo;
	}

	public PanelProducto[] getProductosPackSeleccionados() {
		List<PanelProducto> seleccionados = new ArrayList<>();
		for (int i = 0; i < checkProductosPack.size(); i++) {
			if (checkProductosPack.get(i).isSelected()) {
				seleccionados.add(productos[i]);
			}
		}
		return seleccionados.toArray(new PanelProducto[0]);
	}

	public String[] getEspecificaciones() {
		return especFields.stream().map(this::extraerValor).toArray(String[]::new);
	}

	private String extraerValor(JComponent c) {
		if (c instanceof JTextField)
			return ((JTextField) c).getText();
		if (c instanceof JComboBox)
			return (String) ((JComboBox<?>) c).getSelectedItem();
		if (c instanceof JSpinner)
			return new SimpleDateFormat("yyyy-MM-dd").format(((JSpinner) c).getValue());
		if (c instanceof JScrollPane) {
			if(isModificacion) {
				StringBuilder sb = new StringBuilder();
				for(PanelProducto p : productosPack) {
					sb.append(p.getNombre());
					sb.append(";");
				}
				return sb.toString();
			} else {
				Stream<String> ids = IntStream.range(0, checkProductosPack.size())
						.filter(j -> checkProductosPack.get(j).isSelected())
						.mapToObj(j -> (String) productos[j].getNombre());
				return ids.collect(Collectors.joining(";"));
			}
		}
		return "";
	}

	public String[] getCategorias() {
		return checkCategorias.stream().filter(InvisibleCheckBox::isSelected).map(InvisibleCheckBox::getText)
				.toArray(String[]::new);
	}
}