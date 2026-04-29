package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.cliente.ControlBarraTareasCliente;
import modelo.sistema.*;
import vistas.*;
import vistas.noRegistrado.BarraNoRegistrado;
import vistas.noRegistrado.VentanaInicioSinRegistrar;

public class ControlInicioSinRegistrar implements ActionListener {

	private VentanaInicioSinRegistrar vista;

	public ControlInicioSinRegistrar(Tienda tienda) {
		TiendaFrame tiendaFrame = TiendaFrame.getInstance();

        ControlBarraNoRegistrado ctrlBarraLateral = new ControlBarraNoRegistrado(tienda);
        BarraLateral barraLatera = new BarraNoRegistrado();
        barraLatera.setControlador(ctrlBarraLateral);
        tiendaFrame.setBarraLateral(barraLatera);
        
        ControlBarraTareasCliente ctrlBarraTareas = new ControlBarraTareasCliente();
        BarraTareasCliente barraTareas = new BarraTareasCliente();
        barraTareas.setControlador(ctrlBarraTareas);
        tiendaFrame.setBarraTareas(barraTareas);
        
        this.vista = new VentanaInicioSinRegistrar();
        TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
