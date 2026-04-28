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
		
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
