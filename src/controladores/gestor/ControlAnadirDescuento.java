package controladores.gestor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ActionListener{
	private Tienda tienda;
	private Gestor gestor;
	private TiendaFrame frame;
	
	public ControlAnadirDescuento(Tienda tienda, Gestor gestor) {
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
