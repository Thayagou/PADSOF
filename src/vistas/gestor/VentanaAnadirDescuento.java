package vistas.gestor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import modelo.sistema.Tienda;
import vistas.ButtonFactory;
import vistas.FondoGradiente;

public class VentanaAnadirDescuento extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public VentanaAnadirDescuento(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		JPanel panel = this;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Configuración del descuento"));
        panel.setPreferredSize(new Dimension(300, 0));

        // -- Tipo de condición --
        panel.add(new JLabel("Tipo de condición:"));
        panel.add(Box.createVerticalStrut(4));
        JComboBox<String> tipoCondicion = new JComboBox<>(
            new String[]{"Cantidad", "Volumen", "Sin condiciones"});
        tipoCondicion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(tipoCondicion);
        panel.add(Box.createVerticalStrut(8));

        panel.add(new JLabel("Cantidad/volumen mínimo:"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(new TextField("Valor mínimo..."));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(8));

        // -- Tipo de compensación --
        panel.add(new JLabel("Tipo de compensación:"));
        panel.add(Box.createVerticalStrut(4));
        
        JComboBox<String> tipoComp = new JComboBox<>(
            new String[]{"Dinero","Porcentaje","Regalo"});
        tipoComp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(tipoComp);
        panel.add(Box.createVerticalStrut(8));

        panel.add(new JLabel("Valor de la compensación/Regalo:"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(new TextField("Valor (porcentaje o dinero)..."));
        panel.add(Box.createVerticalStrut(4));
        panel.add(new TextField("Seleccionar regalo..."));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(8));

        // -- Fechas --
        panel.add(new JLabel("Inicio/Fin del descuento:"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(new TextField("Inicio del descuento..."));
        panel.add(Box.createVerticalStrut(4));
        panel.add(new TextField("Fin del descuento..."));

        // -- Glue empuja botones al fondo --
        panel.add(Box.createVerticalGlue());

        // -- Botones --
        ButtonFactory factory = new ButtonFactory();
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        botones.add(factory.newButton("Add"));
        botones.add(factory.newButton("Cancelar"));
        botones.add(factory.newButton("Confirmar"));
        panel.add(botones);

        
	}

}
