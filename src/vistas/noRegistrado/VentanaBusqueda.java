package vistas.noRegistrado;

import javax.swing.*;

import controladores.noRegistrado.ControlBuscar;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelSelectorCajas;
import vistas.herramientas.*;

import java.awt.*;

/**
 * Tipo: Class VentanaBusqueda.
 */
public class VentanaBusqueda extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static final String BUSCAR_ACTION = "Buscar";

	/** Campo estrellas. */
	private JSpinner estrellas;

	/** Campo precioMin. */
	private JTextField precioMin;

	/** Campo precioMax. */
	private JTextField precioMax;

	/** Campo botonBuscar. */
	private JButton botonBuscar;

	/** Campo checkboxes. */
	private PanelSelectorCajas selectorCategorias;

	/** Campo PREFERRED_FILTER_SIZE. */
	private static double PREFERRED_FILTER_SIZE = 0.35;
	private static double PANELS_HEIGHT = 0.5;
	private static double SPACING = 0.03;

	/**
	 * Instancia un nuevo Objeto VentanaBusqueda.
	 *
	 * @param categorias parámetro categorias
	 */
	public VentanaBusqueda(String[] categorias) {
		final double MIN_STARS = 0.0;
		final double MAX_STARS = 5.0;
		final double STEP_STARS = 0.5;
		final double INIT_STARS = 0.0;

		final String DEFAULT_MIN_PRICE = "0.0";
		final String DEFAULT_MAX_PRICE = "999999.0";

		final double SPINNER_WIDTH_FACTOR = 0.08;
		final double SPINNER_HEIGHT_FACTOR = 0.03;

		final double BUTTON_HEIGHT_FACTOR = 0.05;
		final double BUTTON_WIDTH_FACTOR = 0.1;
		final int BUTTON_ROUND_RADIUS = 1;

		final double GBC_WEIGHTX = 0.5;

		setOpaque(false);
		setLayout(new BorderLayout());

		TiendaFrame t = TiendaFrame.getInstance();
		int height = t.getHeight();
		int width = t.getWidth();
		int spaceBetween = t.getPixelsWidth(SPACING);

		estrellas = new JSpinner(new SpinnerNumberModel(INIT_STARS, MIN_STARS, MAX_STARS, STEP_STARS));
		estrellas.setPreferredSize(
				new Dimension((int) (SPINNER_WIDTH_FACTOR * width), (int) (SPINNER_HEIGHT_FACTOR * height)));
		estrellas.setFont(Fonts.BOLD.getFont());
		precioMin = ButtonFactory.newTextField(DEFAULT_MIN_PRICE, Fonts.TEXT);
		precioMax = ButtonFactory.newTextField(DEFAULT_MAX_PRICE, Fonts.TEXT);

		JPanel contenidoFiltros = new JPanel();
		contenidoFiltros.setOpaque(true);
		contenidoFiltros.setBackground(ColorPalette.WHITE.getColor());
		contenidoFiltros.setLayout(new GridBagLayout());
		contenidoFiltros.setPreferredSize(
				new Dimension(t.getPixelsWidth(PREFERRED_FILTER_SIZE), t.getPixelsHeight(PANELS_HEIGHT)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(spaceBetween, spaceBetween, spaceBetween, spaceBetween);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = GBC_WEIGHTX;
		contenidoFiltros.add(ButtonFactory.newLabel("Estrellas mínimas:", Fonts.TEXT), gbc);
		gbc.gridx = 1;
		gbc.weightx = GBC_WEIGHTX;
		contenidoFiltros.add(estrellas, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = GBC_WEIGHTX;
		contenidoFiltros.add(ButtonFactory.newLabel("Precio mínimo:", Fonts.TEXT), gbc);
		gbc.gridx = 1;
		gbc.weightx = GBC_WEIGHTX;
		contenidoFiltros.add(precioMin, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = GBC_WEIGHTX;
		contenidoFiltros.add(ButtonFactory.newLabel("Precio máximo:", Fonts.TEXT), gbc);
		gbc.gridx = 1;
		gbc.weightx = GBC_WEIGHTX;
		contenidoFiltros.add(precioMax, gbc);

		JPanel filtros = PanelFactory.getVentanaConCabecera("Filtros", contenidoFiltros);

		/* Selector de categorias */
		selectorCategorias = new PanelSelectorCajas(categorias);
		JPanel panelCategorias = PanelFactory.getVentanaConCabecera("Categorías", selectorCategorias);

		/* Botón de buscar */
		botonBuscar = ButtonFactory.newRoundedButton(BUSCAR_ACTION, t.getPixelsHeight(BUTTON_HEIGHT_FACTOR),
				t.getPixelsWidth(BUTTON_WIDTH_FACTOR), BUTTON_ROUND_RADIUS);
		botonBuscar.setBackground(ColorPalette.PURPLE.getColor());
		botonBuscar.setForeground(ColorPalette.WHITE.getColor());
		ButtonFactory.addMouseMecanics(botonBuscar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBoton.setOpaque(false);
		panelBoton.add(botonBuscar);

		/* Layout general */
		JPanel contenido = new JPanel(new BorderLayout(0, spaceBetween));
		JPanel formulario = new JPanel();
		formulario.setLayout(new BoxLayout(formulario, BoxLayout.X_AXIS));
		formulario.add(Box.createHorizontalStrut(spaceBetween));
		formulario.add(filtros);
		formulario.add(Box.createHorizontalStrut(spaceBetween));
		formulario.add(panelCategorias);
		formulario.add(Box.createHorizontalStrut(spaceBetween));
		formulario.setOpaque(false);

		contenido.setOpaque(false);
		contenido.setBorder(BorderFactory.createEmptyBorder(spaceBetween, spaceBetween, spaceBetween, spaceBetween));

		contenido.add(BorderLayout.NORTH, formulario);
		contenido.add(BorderLayout.SOUTH, panelBoton);
		contenido.setOpaque(false);

		JPanel ventana = PanelFactory.getVentanaConCabecera("      Buscar Productos", contenido);
		ventana.setOpaque(false);
		this.add(ventana);
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	// Asignar controlador a los botones
	public void setControlador(ControlBuscar c) {
		botonBuscar.addActionListener(c);
	}

	/**
	 * Obtiene Estrellas.
	 *
	 * @return valor de Estrellas
	 */
	public double getEstrellas() {
		return (double) estrellas.getValue();
	}

	/**
	 * Obtiene PrecioMin.
	 *
	 * @return valor de PrecioMin
	 */
	public double getPrecioMin() {
		return Double.parseDouble(precioMin.getText());
	}

	/**
	 * Obtiene PrecioMax.
	 *
	 * @return valor de PrecioMax
	 */
	public double getPrecioMax() {
		return Double.parseDouble(precioMax.getText());
	}

	/**
	 * Obtiene CategoriasSeleccionadas.
	 *
	 * @return valor de CategoriasSeleccionadas
	 */
	public String[] getCategoriasSeleccionadas() {
		return selectorCategorias.getCategoriasSeleccionadas();
	}
}
