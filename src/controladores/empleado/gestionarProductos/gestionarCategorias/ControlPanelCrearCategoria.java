package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarProductos.gestionarCategorias.PanelCrearCategoria;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;

public class ControlPanelCrearCategoria implements ActionListener {
	private final Tienda tienda;
	private final Usuario usuario;
	private final PanelCrearCategoria panel;
	
	public ControlPanelCrearCategoria(Tienda tienda, Usuario usuario, VentanaGestionarCategorias vista) {
		this.tienda = tienda;
		this.usuario = usuario;
		
		panel = new PanelCrearCategoria();
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCrearCategoria.CONFIRMAR_ACTION:
			intentarCrear();
			break;
		}
	}
	
	private void intentarCrear() {
		String nombre = panel.getNombreCategoria();
		if(nombre.equals("Nombre") || nombre.length() < 1) {
			new VentanaMensaje("Introduzca un nombre para la nueva categoría");
			return;
		}
		
		try {
			tienda.getAlmacen().anadirCategoria(usuario, nombre);
		} catch (InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
		}
		SwingUtilities.invokeLater(() -> new ControlGestionarCategorias(tienda, usuario));
		new VentanaMensaje("La categoría se ha añadido correctamente");
	}

}
