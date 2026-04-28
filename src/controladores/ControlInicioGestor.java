package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaInicioGestor;

public class ControlInicioGestor implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaInicioGestor vista;
	
	public ControlInicioGestor(Tienda tienda) {
		this.tienda = tienda;
		
		this.vista = new VentanaInicioGestor(tienda);
		this.vista.setControlador(this);
		
		this.frame = TiendaFrame.getInstance();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(vista);
		this.frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
