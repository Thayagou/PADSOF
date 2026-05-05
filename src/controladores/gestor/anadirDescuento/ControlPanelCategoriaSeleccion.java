package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.PanelCategoria;
import vistas.common.PanelCategoriaSeleccion;
import vistas.common.VentanaConDisplay;

public class ControlPanelCategoriaSeleccion  implements ActionListener {
	private Categoria categoria;
	private Tienda tienda;
	private PanelCategoriaSeleccion panel;
	private ControlAnadirDescuento superControl;
	
	public ControlPanelCategoriaSeleccion(Tienda tienda, Categoria categoria, ControlAnadirDescuento superControl, VentanaConDisplay<? super PanelCategoria> vista) {
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
			panel.toggleCheckBox();
			superControl.incluirCategoria(categoria, panel.isSeleccionada());
			break;
		}
	}

}
