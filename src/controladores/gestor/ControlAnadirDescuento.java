package controladores.gestor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	
	public ControlAnadirDescuento(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		VentanaAnadirDescuento vista = new VentanaAnadirDescuento();
		
		frame.setVistaActual(vista);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
