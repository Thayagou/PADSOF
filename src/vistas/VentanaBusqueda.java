package vistas;

import javax.swing.*;

import controladores.ControlBuscar;

import java.awt.*;
import java.util.*;

public class VentanaBusqueda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JSpinner estrellas;
	private JTextField precioMin;
	private JTextField precioMax;
	private JButton botonBuscar;
	java.util.List<JCheckBox> checkboxes = new ArrayList<>();;

	public VentanaBusqueda(String[] categorias) {
		JLabel title = new JLabel("Realizar búsqueda");

		estrellas = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5));
		precioMin = new JTextField(15);
		precioMax = new JTextField(15);

		// Lista de categorias a seleccionar

		JPanel panelCategorias = new JPanel();
		panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
		Map<JCheckBox, String> mapa = new HashMap<>();
		for (String cat : categorias) {
			JCheckBox cb = new JCheckBox(cat);
			checkboxes.add(cb);
			mapa.put(cb, cat);
			panelCategorias.add(cb);
		}
		JScrollPane scroll = new JScrollPane(panelCategorias);

		botonBuscar = new JButton("Buscar");

		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(title);
		panel.add(new JLabel("Estrellas minimas:"));
		panel.add(estrellas);
		panel.add(new JLabel("Precio minimo:"));
		panel.add(precioMin);
		panel.add(new JLabel("Precio maximo:"));
		panel.add(precioMax);

		setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(botonBuscar, BorderLayout.SOUTH);
		setVisible(true);
	}

	// Asignar controlador a los botones
	public void setControlador(ControlBuscar c) {
		botonBuscar.addActionListener(c);
	}

	public double getEstrellas() {
		return (double)estrellas.getValue();
	}

	public double getPrecioMin() {
		return Double.parseDouble(precioMin.getText());
	}
	
	public double getPrecioMax() {
		return Double.parseDouble(precioMax.getText());
	}
	
	public Boolean[] getCategorias() {
		java.util.List<Boolean> selected = new LinkedList<>();
		for (JCheckBox cb : checkboxes) {
			selected.add(cb.isSelected());
		}
		
		return selected.toArray(new Boolean[0]);
	}
}
