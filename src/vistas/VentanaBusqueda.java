package vistas;

import javax.swing.*;

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

	public VentanaBusqueda(String[] categorias) {
		super();

		JLabel title = new JLabel("Realizar búsqueda", JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

		estrellas = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5));
		estrellas.setPreferredSize(new Dimension(80, 30));
		precioMin = new JTextField(10);
		precioMax = new JTextField(10);

		JPanel panelForm = new JPanel(new GridBagLayout());
		panelForm.setBorder(BorderFactory.createTitledBorder("Filtros"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelForm.add(new JLabel("Estrellas mínimas:"), gbc);

		gbc.gridx = 1;
		panelForm.add(estrellas, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		panelForm.add(new JLabel("Precio mínimo:"), gbc);

		gbc.gridx = 1;
		panelForm.add(precioMin, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		panelForm.add(new JLabel("Precio máximo:"), gbc);

		gbc.gridx = 1;
		panelForm.add(precioMax, gbc);

		JPanel panelCategorias = new JPanel();
		panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
		panelCategorias.setBorder(BorderFactory.createTitledBorder("Categorías"));

		for (String cat : categorias) {
			JCheckBox cb = new JCheckBox(cat);
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
