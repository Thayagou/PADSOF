package gui;

import javax.swing.*;

import exceptions.*;
import sistema.Tienda;
import venta.productos.*;
import java.awt.*;

public class VentanaBusqueda extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaBusqueda(Tienda tienda) {
		setTitle("Realizar Búsqueda");
		setSize(300, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
		JSpinner estrellas = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 5.0, 0.5));
		JTextField precioMin = new JTextField(15);
		JTextField precioMax = new JTextField(15);
		JList<Categoria> lista = new JList<Categoria>(tienda.getAlmacen().getCategorias());
		lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane scroll = new JScrollPane(lista);
		JButton boton = new JButton("Buscar");

		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.add(new JLabel("Estrellas minimas:"));
		panel.add(estrellas);
		panel.add(new JLabel("Precio minimo:"));
		panel.add(precioMin);
		panel.add(new JLabel("Precio maximo:"));
		panel.add(precioMax);
		panel.add(new JLabel("Categorias seleccionadas:"));
		panel.add(scroll);
		
		boton.addActionListener(e -> {
			try {
				double eMin = (double) estrellas.getValue();
				double pMin = Double.parseDouble(precioMin.getText());
				double pMax = Double.parseDouble(precioMax.getText());
				java.util.List<Categoria> selected = lista.getSelectedValuesList();
				
				Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(selected.toArray(new Categoria[0]), pMin, pMax, eMin);
				
				new VentanaResultados(tienda, productos);
				//dispose();
			} catch(CustomException ex) {
				new VentanaMensaje(ex.getMessage());
			} catch(NumberFormatException ex) {
				new VentanaMensaje("Introduce valores numéricos válidos");
			}

		});

		setLayout(new BorderLayout());
	    add(panel, BorderLayout.CENTER);
	    add(boton, BorderLayout.SOUTH);
		setVisible(true);
	}
}
