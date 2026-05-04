package controladores.empleado.gestionarProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import vistas.common.PanelCategoria;
import vistas.common.VentanaConDisplay;
import vistas.empleado.PanelCategoriaGestionarCategoria;

public class ControlPanelCategoriaGestionar implements ActionListener {
	private Categoria categoria;
	private Tienda tienda;
	private PanelCategoriaGestionarCategoria panel;
	
	public ControlPanelCategoriaGestionar(Tienda tienda, Categoria categoria, VentanaConDisplay<? super PanelCategoria> vista) {
		this.tienda = tienda;
		this.categoria = categoria;
		
		panel = new PanelCategoriaGestionarCategoria(categoria.getNombre());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaGestionarCategoria.BORRAR_ACTION:
			break;
		case PanelCategoriaGestionarCategoria.MODIFICAR_ACTION:
			break;
		}
	}

}
