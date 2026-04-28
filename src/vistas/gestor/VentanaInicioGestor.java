package vistas.gestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import controladores.ControlInicioGestor;
import modelo.sistema.Tienda;
import vistas.*;

//import java.awt.*;

public class VentanaInicioGestor extends FondoGradiente {

	private static final long serialVersionUID = 1L;
	private static double GAP = 0.01;
	private JButton descuentos;
	private JButton sistema;
	private JButton estadisticas;
	private JButton prodYCats;
	private JButton empleados;
	
	public VentanaInicioGestor(Tienda tienda) {
		super();
		TiendaFrame t = TiendaFrame.getInstance();
		
		JPanel center = new JPanel(new GridLayout(2, 1, 0, 16));
		TitledBorder centerTitle = BorderFactory.createTitledBorder("Opciones");
		centerTitle.setTitleFont(t.getTitle3Font());
		center.setBorder(centerTitle);
		int totalHeight = t.getHeight();
		int buttonHeight = (int) ((totalHeight - barra.getHeight())/2 * 0.8);
		Dimension buttomDimension = new Dimension(0, buttonHeight);
		JPanel top = new JPanel(new GridLayout(1, 3, 16, 30));
		
		ButtonFactory factory = new ButtonFactory();
		descuentos = factory.newIconButton("Añadir descuento", totalHeight, 0, "descuento.png");
		descuentos.setFont(Fonts.SUBTITLE.getFont());
		
		sistema = factory.newIconButton("Configurar sistema", totalHeight, 0, "sistema.png");
		sistema.setFont(Fonts.SUBTITLE.getFont());
		
		estadisticas = factory.newIconButton("Consultar estadísticas", totalHeight, 0, "estadistica.png");
		estadisticas.setFont(Fonts.SUBTITLE.getFont());
		
		top.add(descuentos);
		top.add(sistema);
		top.add(estadisticas);

		
		JPanel bottom = new JPanel(new GridLayout(1, 2, 16, 30));
		bottom.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80)); // margen lateral
		
		prodYCats = new JButton("Gestionar productos y categorias");
		empleados = new JButton("Gestionar empleados");
		
		prodYCats.setFont(t.getSubtitleFont());
		empleados.setFont(t.getSubtitleFont());
		
		prodYCats.setPreferredSize(buttomDimension);
		empleados.setPreferredSize(buttomDimension);
		
		bottom.add(prodYCats); 
		bottom.add(empleados);

		center.add(top);
		center.add(bottom);
		
		center.setOpaque(true);
		top.setOpaque(true);
		bottom.setOpaque(true);
		center.setBackground(Color.WHITE);
		top.setBackground(Color.WHITE);
		bottom.setBackground(Color.WHITE);
		
		this.add(center, BorderLayout.CENTER);
	}

	public void setControlador(ControlInicioGestor controlInicioGestor) {
		descuentos.addActionListener(controlInicioGestor);
		sistema.addActionListener(controlInicioGestor);
		estadisticas.addActionListener(controlInicioGestor);
		prodYCats.addActionListener(controlInicioGestor);
		empleados.addActionListener(controlInicioGestor);
	}
}
