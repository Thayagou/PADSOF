package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.displays.PanelCategoria;
import vistas.common.displays.PanelCategoriaSeleccion;
import vistas.common.displays.VentanaConDisplay;

public class ControlPanelCategoriaSeleccion  implements ActionListener {
	private Categoria categoria;
	private Tienda tienda;
	private PanelCategoriaSeleccion panel;
	private ControlGestionSeleccion<? super Categoria> superControl;
	
	public ControlPanelCategoriaSeleccion(Tienda tienda, Categoria categoria, ControlGestionSeleccion<? super Categoria> superControl, VentanaConDisplay<? super PanelCategoria> vista) {
		this.tienda = tienda;
		this.categoria = categoria;
		this.superControl = superControl;
		
		panel = new PanelCategoriaSeleccion(categoria.getNombre());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaSeleccion.INCLUIR_ACTION:
			superControl.setSeleccionado(categoria, panel, !panel.isSeleccionado());
			break;
		}
	}
	
	public PanelCategoriaSeleccion getPanel() { return panel; }

}
