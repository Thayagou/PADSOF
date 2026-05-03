package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import modelo.venta.productos.Categoria;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaGestionarCategorias;

public class ControlGestionarCategorias implements ActionListener{
	private Tienda tienda;
	private Gestor gestor;
	private TiendaFrame frame;
	private VentanaGestionarCategorias vista;
	
	public ControlGestionarCategorias(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaGestionarCategorias();
		
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
		
		for (Categoria c: categorias) {
			new ControlPanelCategoriaGestionar(tienda, c, vista);
		}
		
		vista.revalidate();
		vista.repaint();
		
		frame.setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
