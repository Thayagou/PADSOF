package vistas;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controladores.ControlBuscar;

import java.awt.*;
import java.util.*;

public class VentanaBusqueda extends FondoGradiente {

	private static final long serialVersionUID = 1L;
	private JSpinner estrellas;
	private JTextField precioMin;
	private JTextField precioMax;
	private JButton botonBuscar;
	java.util.List<JCheckBox> checkboxes = new ArrayList<>();;
	private static double PREFERRED_FILTER_SIZE = 0.3;

	public VentanaBusqueda(String[] categorias) {
		super();
		TiendaFrame t = TiendaFrame.getInstance();
		JLabel title = new JLabel("Realizar búsqueda", JLabel.CENTER);
		title.setFont(t.getTitle3Font());

		estrellas = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5));
		estrellas.setPreferredSize(new Dimension(80, 30));
		estrellas.setFont(t.getTextFont());
		precioMin = new JTextField(10);
		precioMin.setFont(t.getTextFont());
		precioMax = new JTextField(10);
		precioMax.setFont(t.getTextFont());

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
		panelNorte.add(title, BorderLayout.CENTER);
		
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

	// Asignar controlador a los botones
	public void setControlador(ControlBuscar c) {
		botonBuscar.addActionListener(c);
	}

	public double getEstrellas() {
		return (double) estrellas.getValue();
	}

	public double getPrecioMin() {
		return Double.parseDouble(precioMin.getText());
	}

	public double getPrecioMax() {
		return Double.parseDouble(precioMax.getText());
	}

	public String[] getCategoriasSeleccionadas() {
		return checkboxes.stream().filter(JCheckBox::isSelected).map(JCheckBox::getText).toArray(String[]::new);
	}
}
