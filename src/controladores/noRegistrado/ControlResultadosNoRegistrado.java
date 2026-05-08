package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.venta.productos.Producto;
import vistas.common.app.TiendaFrame;
import vistas.noRegistrado.VentanaResultadosNoRegistrado;

public class ControlResultadosNoRegistrado implements ActionListener, ControladorPantalla {

	private Tienda tienda;
	private VentanaResultadosNoRegistrado vista;

	public ControlResultadosNoRegistrado(Tienda tienda, Producto[] productos) {
		this.tienda = tienda;
		this.vista = new VentanaResultadosNoRegistrado();
		
		for(Producto p : productos) {
			new ControlPanelProductoNoRegistrado(tienda, p, vista);
			/*ArrayList<String> categorias = new ArrayList<>();
			for(Categoria c : p.getCategorias()) {
				categorias.add(c.getNombre());
			}
			
			vista.anadirProducto(p.getNombre(), p.getDescripcion(), p.getPuntuacionMedia(), p.getPrecio(), categorias.toArray(new String[0]));*/
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd != null && cmd.startsWith("Ver producto:")) {
			String nombreProducto = cmd.substring("Ver producto:".length());
			this.mostrarProducto(nombreProducto);
		}
	}

	private void mostrarProducto(String nombreProducto) {
		SwingUtilities.invokeLater(() -> {
			try {
				Producto producto = tienda.getAlmacen().getStock(nombreProducto).getProducto();
				new ControlProductoSinRegistrar(tienda, producto);
			} catch (Exception ex) {
				new vistas.common.assets.VentanaMensaje("Producto no encontrado: " + nombreProducto);
			}
		});
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes ver los resultados de una búsqueda. Para añadir productos al carrito, inicia sesión o registrate como cliente.";
	}
}
