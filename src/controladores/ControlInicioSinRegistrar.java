package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.*;
import vistas.*;
import vistas.noRegistrado.VentanaInicioSinRegistrar;

public class ControlInicioSinRegistrar implements ActionListener {

	private VentanaInicioSinRegistrar vista;

	public ControlInicioSinRegistrar(Tienda tienda) {

        ControlBarraNoRegistrado ctrlBarra = new ControlBarraNoRegistrado(tienda);

        this.vista = new VentanaInicioSinRegistrar(ctrlBarra);
        TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
