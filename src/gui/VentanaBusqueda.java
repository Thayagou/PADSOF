package gui;

import javax.swing.*;

import exceptions.*;
import sistema.Tienda;
import venta.productos.*;
import java.awt.*;
import java.util.*;

public class VentanaBusqueda extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaBusqueda(Tienda tienda) {
		setTitle("Realizar Búsqueda");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JSpinner estrellas = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5));
		JTextField precioMin = new JTextField(15);
		JTextField precioMax = new JTextField(15);

		// Lista de categorias a seleccionar
		JPanel panelCategorias = new JPanel();
		panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
		java.util.List<JCheckBox> checkboxes = new ArrayList<>();
		java.util.Map<JCheckBox, Categoria> mapa = new HashMap<>();
		for (Categoria cat : tienda.getAlmacen().getCategorias()) {
			JCheckBox cb = new JCheckBox(cat.getNombre());
			checkboxes.add(cb);
			mapa.put(cb, cat);
			panelCategorias.add(cb);
		}
		JScrollPane scroll = new JScrollPane(panelCategorias);

		JButton boton = new JButton("Buscar");

		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("Estrellas minimas:"));
		panel.add(estrellas);
		panel.add(new JLabel("Precio minimo:"));
		panel.add(precioMin);
		panel.add(new JLabel("Precio maximo:"));
		panel.add(precioMax);

		boton.addActionListener(e -> {
			try {
				double eMin = (double) estrellas.getValue();
				double pMin = Double.parseDouble(precioMin.getText());
				double pMax = Double.parseDouble(precioMax.getText());
				java.util.List<Categoria> selected = new ArrayList<Categoria>();

				for (JCheckBox cb : checkboxes) {
					if (cb.isSelected()) {
						selected.add(mapa.get(cb));
					}
				}

				Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(selected.toArray(new Categoria[0]),
						pMin, pMax, eMin);

				new VentanaResultados(tienda, productos);
				// dispose();
			} catch (InvalidArgumentException ex) {
				new VentanaMensaje(ex.getMessage());
			} catch (NumberFormatException ex) {
				new VentanaMensaje("Introduce valores numéricos válidos");
			}

		});

		setLayout(new BorderLayout());
		add(panel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(boton, BorderLayout.SOUTH);
		setVisible(true);
	}
}
