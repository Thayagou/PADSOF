package controladores;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	
	public ControlAnadirDescuento(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		VentanaAnadirDescuento vista = new VentanaAnadirDescuento(tienda);
		
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Configuración del descuento"));
        panel.setPreferredSize(new Dimension(300, 0));

        // -- Tipo de condición --
        panel.add(new JLabel("Tipo de condición:"));
        panel.add(Box.createVerticalStrut(4));
        JComboBox<String> tipoCondicion = new JComboBox<>(
            new String[]{"Cantidad/Volumen/Sin condiciones"});
        tipoCondicion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(tipoCondicion);
        panel.add(Box.createVerticalStrut(8));

        panel.add(new JLabel("Cantidad/volumen mínimo:"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(textField("Valor mínimo..."));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(8));

        // -- Tipo de compensación --
        panel.add(new JLabel("Tipo de compensación:"));
        panel.add(Box.createVerticalStrut(4));
        JComboBox<String> tipoComp = new JComboBox<>(
            new String[]{"Dinero/Porcentaje/Regalo"});
        tipoComp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(tipoComp);
        panel.add(Box.createVerticalStrut(8));

        panel.add(new JLabel("Valor de la compensación/Regalo:"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(textField("Valor (porcentaje o dinero)..."));
        panel.add(Box.createVerticalStrut(4));
        panel.add(textField("Seleccionar regalo..."));
        panel.add(new JSeparator());
        panel.add(Box.createVerticalStrut(8));

        // -- Fechas --
        panel.add(new JLabel("Inicio/Fin del descuento:"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(textField("Inicio del descuento..."));
        panel.add(Box.createVerticalStrut(4));
        panel.add(textField("Fin del descuento..."));

        // -- Glue empuja botones al fondo --
        panel.add(Box.createVerticalGlue());

        // -- Botones --
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        botones.add(roundButton("Cancelar", new Color(160, 0, 200)));
        botones.add(roundButton("Confirmar", new Color(160, 0, 200)));
        panel.add(botones);

        return panel;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
