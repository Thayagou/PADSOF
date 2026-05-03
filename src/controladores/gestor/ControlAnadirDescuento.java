package controladores.gestor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.gestor.VentanaAnadirDescuento;

public class ControlAnadirDescuento implements ActionListener{
	private Tienda tienda;
	private Gestor gestor;
	private VentanaAnadirDescuento vista;
	
	public ControlAnadirDescuento(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		vista = new VentanaAnadirDescuento();
		
		anadirCategorias();
		
		TiendaFrame.getInstance().setVistaActual(vista);
		
    }
	
	public void anadirProductos() {
		Producto[] catalogo = tienda.getAlmacen().getProductosCoincidentes("");
	
		for (Producto p: catalogo) {
			new ControlPanelProductoSeleccion();
		}
	}
	
	public void anadirCategorias() {
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
	
		for (Categoria c: categorias) {
			new ControlPanelCategoriaSeleccion(tienda, c, vista);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
