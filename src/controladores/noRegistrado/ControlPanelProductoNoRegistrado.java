package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import modelo.sistema.Tienda;
import modelo.venta.descuentos.CondicionDescuento;
import modelo.venta.descuentos.Descuento;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.common.VentanaConDisplay;
import vistas.common.VentanaMensaje;
public class ControlPanelProductoNoRegistrado implements ActionListener {
	private Producto producto;
	private Tienda tienda;
	private PanelProducto panel;
	
	private static final String DF_PRODUCT_IMAGE = "producto.png";
	
	public ControlPanelProductoNoRegistrado(Tienda tienda, Producto producto, VentanaConDisplay<? super PanelProducto> vista) {
		this.producto = producto;
		this.tienda = tienda;
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		String imageRoute;
		if(producto.getImagen() == null || producto.getImagen().isBlank()) imageRoute = DF_PRODUCT_IMAGE;
		else imageRoute = producto.getImagen();

		if (producto.tieneDescuento()) {
			panel = new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), "Ver producto", getMensajeDescuento(producto), categorias.toArray(new String[0]));
		} else {
			panel = new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), producto.getPrecio(), "Ver producto", categorias.toArray(new String[0]));
		}
		
		vista.anadirDisplay(panel);
		
		panel.setControlador(this);
	}
	
	private String getMensajeDescuento(Producto p) {
		Descuento desc = p.getDescuento();
		String mensaje = "¡Oferta!<br>";
		mensaje = mensaje + desc.getMessage();
		
		String stringCond = "";
		switch (desc.getCondicion()) {
			case CondicionDescuento.CANTIDAD -> {
				int valorMin = (int) desc.getValorMin();
				stringCond = " al comprar " + valorMin + (valorMin == 1 ? " unidad":" unidades") + " de ";
			}
		case CondicionDescuento.VOLUMEN -> stringCond = " al gastar " + desc.getValorMin() + "€ en ";
			case CondicionDescuento.SIN_CONDICION-> stringCond = ""; 
		}
		
		mensaje = mensaje + stringCond;
		
		Categoria[] categorias = p.getCategorias();
		for (Categoria c: categorias) {
			if (c.tieneDescuento()) return mensaje + c.getNombre();
		}
		
		return mensaje + p.getNombre();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Ver producto":
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
