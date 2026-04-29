package vistas.noRegistrado;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controladores.noRegistrado.ControlBuscar;
import vistas.common.TiendaFrame;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

import java.awt.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * Tipo: Class VentanaBusqueda.
 */
public class VentanaBusqueda extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo estrellas. */
	private JSpinner estrellas;
	
	/** Campo precioMin. */
	private JTextField precioMin;
	
	/** Campo precioMax. */
	private JTextField precioMax;
	
	/** Campo botonBuscar. */
	private JButton botonBuscar;
	
	/** Campo checkboxes. */
	java.util.List<JCheckBox> checkboxes = new ArrayList<>();;
	
	/** Campo PREFERRED_FILTER_SIZE. */
	private static double PREFERRED_FILTER_SIZE = 0.3;

	/**
	 * Instancia un nuevo Objeto VentanaBusqueda.
	 *
	 * @param categorias parámetro categorias
	 */
	public VentanaBusqueda(String[] categorias) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		TiendaFrame t = TiendaFrame.getInstance();
		int height = t.getHeight();
		int width = t.getWidth();
		JLabel cabecera = new JLabel("Realizar búsqueda", JLabel.CENTER);
		cabecera.setFont(t.getTitle3Font());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		estrellas = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5));
		estrellas.setPreferredSize(new Dimension((int)(0.08*width), (int)(0.03 * height)));
		estrellas.setFont(t.getTextFont());
		precioMin = new JTextField(10);
		precioMin.setFont(Fonts.TEXT.getFont());
		precioMax = new JTextField(10);
		precioMax.setFont(Fonts.TEXT.getFont());

		JPanel panelForm = new JPanel(new GridBagLayout());
		TitledBorder tb = BorderFactory.createTitledBorder("Filtros");
		tb.setTitleFont(t.getTitle3Font());
		panelForm.setBorder(tb);
		panelForm.setPreferredSize(new Dimension(t.getPixelsWidth(PREFERRED_FILTER_SIZE), 0));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JLabel minEstrellas = new JLabel("Estrellas mínimas:");
		minEstrellas.setFont(t.getTextFont());
		panelForm.add(minEstrellas, gbc);

		gbc.gridx = 1;
		panelForm.add(estrellas, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		JLabel minPrecio = new JLabel("Precio mínimo:");
		minPrecio.setFont(t.getTextFont());
		panelForm.add(minPrecio, gbc);

		gbc.gridx = 1;
		panelForm.add(precioMin, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		JLabel precioMaximo = new JLabel("Precio máximo:");
		precioMaximo.setFont(t.getTextFont());
		panelForm.add(precioMaximo, gbc);

		gbc.gridx = 1;
		panelForm.add(precioMax, gbc);

		JPanel panelCategorias = new JPanel();
		panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
		TitledBorder tbCategorias = BorderFactory.createTitledBorder("Categorías");
		tbCategorias.setTitleFont(t.getTitle3Font());
		panelCategorias.setBorder(tbCategorias);

		for (String cat : categorias) {
			JCheckBox cb = new JCheckBox(cat);
			cb.setFont(t.getTextFont());;
			checkboxes.add(cb);
			panelCategorias.add(cb);
		}

		JScrollPane scroll = new JScrollPane(panelCategorias);
		scroll.setPreferredSize(new Dimension(200, 250));

		botonBuscar = new JButton("Buscar");
		JPanel panelBoton = new JPanel();
		panelBoton.add(botonBuscar);

		//Contenido de esta ventana
		JPanel contenido = new JPanel(new BorderLayout(10, 10));
	    contenido.setOpaque(false);
	    contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
		JPanel panelNorte = new JPanel(new BorderLayout());
		panelNorte.setOpaque(false);
		panelNorte.add(cabecera, BorderLayout.CENTER);
		
		JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
		panelCentro.add(panelForm, BorderLayout.WEST);
		panelCentro.add(scroll, BorderLayout.CENTER);
		
		contenido.add(panelNorte, BorderLayout.NORTH);
		contenido.add(panelCentro, BorderLayout.CENTER);
		contenido.add(panelBoton, BorderLayout.SOUTH);
		
		this.add(contenido, BorderLayout.CENTER);

		//Hacer opacas algunas partes para que se vea el fondo
		panelForm.setOpaque(true);
		panelForm.setBackground(Color.WHITE);
		panelCategorias.setOpaque(true);
		panelCategorias.setBackground(Color.WHITE);
		scroll.setOpaque(true);
		scroll.getViewport().setOpaque(true);
		scroll.getViewport().setBackground(Color.WHITE);
		scroll.setBackground(Color.WHITE);
		panelCentro.setOpaque(false);
		panelBoton.setOpaque(false);
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
		return checkboxes.stream().filter(JCheckBox::isSelected).map(JCheckBox::getText).toArray(String[]::new);
	}
}
