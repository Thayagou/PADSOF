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
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;

/**
 * Esta clase representa el controlador para un panel de producto que se muestra a un usuario no registrado
 */
public class ControlPanelProductoNoRegistrado implements ActionListener {
	/** Producto que se muestra */
	private Producto producto;
	/** Modelo de la tienda sobre el que se actúa */
	private Tienda tienda;
	/** Panel que se controla */
	private PanelProducto panel;
	/** Nombre de la acción de ver un producto */
	private static final String actionName = "Ver producto";

	/**
	 * Constructor del panel de mostrar producto
	 * @param tienda Modelo de la tienda
	 * @param producto Producto que se muestra
	 * @param vista Ventana en la que se muestra el panel
	 */
	public ControlPanelProductoNoRegistrado(Tienda tienda, Producto producto,
			VentanaConDisplay<? super PanelProducto> vista) {
		this.producto = producto;
		this.tienda = tienda;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}

		
		String imageRoute = producto.getImagen();
		if (producto.tieneDescuento()) {
			panel = new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute,
					producto.getPuntuacionMedia(), producto.getPrecio(), actionName, getMensajeDescuento(producto),
					categorias.toArray(new String[0]));
		} else {
			panel = new PanelProducto(producto.getNombre(), producto.getDescripcion(), imageRoute,
					producto.getPuntuacionMedia(), producto.getPrecio(), actionName, categorias.toArray(new String[0]));
		}

		vista.anadirDisplay(panel);

		panel.setControlador(this);
	}

	/**
	 * Devuelve el mensaje de descuento asociado al producto si lo tiene
	 * @param p Producto
	 * @return Mensaje de descuento
	 */
	private String getMensajeDescuento(Producto p) {
		Descuento desc = p.getDescuento();
		String mensaje = "¡Oferta!<br>";
		mensaje = mensaje + desc.getMessage();

		String stringCond = "";
		switch (desc.getCondicion()) {
		case CondicionDescuento.CANTIDAD -> {
			int valorMin = (int) desc.getValorMin();
			stringCond = " al comprar " + valorMin + (valorMin == 1 ? " unidad" : " unidades") + " de ";
		}
		case CondicionDescuento.VOLUMEN -> stringCond = " al gastar " + desc.getValorMin() + "€ en ";
		case CondicionDescuento.SIN_CONDICION -> stringCond = "";
		}

		mensaje = mensaje + stringCond;

		Categoria[] categorias = p.getCategorias();
		for (Categoria c : categorias) {
			if (c.tieneDescuento())
				return mensaje + c.getNombre();
		}

		return mensaje + p.getNombre();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case actionName:
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
