package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.common.VentanaMensaje;
import vistas.noRegistrado.VentanaInicioSinRegistrar;

public class ControlPanelProductoNoRegistrado implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProducto panel;
	
	public ControlPanelProductoNoRegistrado(Tienda tienda, Producto producto, VentanaInicioSinRegistrar vista) {
		this.producto = producto;
		this.tienda = tienda;
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		panel = vista.anadirProductoRecomendado(producto.getNombre(), producto.getDescripcion(), producto.getPuntuacionMedia(), producto.getPrecio(), categorias.toArray(new String[0]));
		panel.setControlador(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Ver producto:":
			SwingUtilities.invokeLater(() -> {
				try {
					new ControlProductoSinRegistrar(tienda, producto);
				} catch (Exception ex) {
					new VentanaMensaje("Producto no encontrado: " + producto.getNombre());
				}
			});
		}		
	}
}
