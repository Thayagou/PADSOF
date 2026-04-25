package vistas;

import javax.swing.*;

import controladores.ControlBuscar;

import java.awt.*;
import java.util.*;

public class VentanaBusqueda extends JPanel {

	private static final long serialVersionUID = 1L;
	private JSpinner estrellas;
	private JTextField precioMin;
	private JTextField precioMax;
	private JButton botonBuscar;
	java.util.List<JCheckBox> checkboxes = new ArrayList<>();;

	public VentanaBusqueda(String[] categorias) {

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

		JPanel centro = new JPanel(new BorderLayout(10, 10));
		centro.add(panelForm, BorderLayout.WEST);
		centro.add(scroll, BorderLayout.CENTER);

		botonBuscar = new JButton("Buscar");
		JPanel panelBoton = new JPanel();
		panelBoton.add(botonBuscar);

		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.add(title, BorderLayout.NORTH);
		this.add(centro, BorderLayout.CENTER);
		this.add(panelBoton, BorderLayout.SOUTH);
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
	    return checkboxes.stream()
	            .filter(JCheckBox::isSelected)
	            .map(JCheckBox::getText)
	            .toArray(String[]::new);
	}
}
