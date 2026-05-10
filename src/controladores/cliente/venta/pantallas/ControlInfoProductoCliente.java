package controladores.cliente.venta.pantallas;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.venta.productos.*;
import vistas.cliente.venta.pantallas.VentanaProductoCliente;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlInfoProductoCliente implements ControladorPantalla {

	private Tienda tienda;
	private Producto producto;
	private ClienteRegistrado cliente;
	private VentanaProductoCliente vista;

	private static final String DF_PRODUCT_IMAGE = "producto.png";

	public ControlInfoProductoCliente(Tienda tienda, ClienteRegistrado cliente, Producto producto) {
		this.tienda = tienda;
		this.producto = producto;
		this.cliente = cliente;

		ArrayList<String> categorias = new ArrayList<>();
		for (Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}

		String imageRoute;
		if (producto.getImagen() == null || producto.getImagen().isBlank())
			imageRoute = DF_PRODUCT_IMAGE;
		else
			imageRoute = producto.getImagen();
		
		String caracteristicas = getCaracteristicas(producto);

		this.vista = new VentanaProductoCliente(producto.getNombre(), producto.getDescripcion(), imageRoute,
				producto.getPuntuacionMedia(), producto.getPrecio(), caracteristicas, categorias.toArray(new String[0]));
		vista.setControlador(this);
		
		for (Resena r : producto.getResenas()) {
			vista.anadirPanelResena(r.getPuntuacion(), r.getComentario(), r.getUsuario().getNombre());
		}

		TiendaFrame.getInstance().navegarA(this);
	}
	
	private String getCaracteristicas(Producto prod) {
		StringBuilder caracteristicas = new StringBuilder();
		if(prod instanceof Comic) {
			Comic comic = (Comic)prod;
			String[] caracList = comic.getValoresCaracteristicas();
			if(caracList.length < 4) return "Caracteristicas erróneas";
			
			caracteristicas.append("Fecha publicación: " + caracList[0]);
			caracteristicas.append("\nAutor: " + caracList[1]);
			caracteristicas.append("\nNúmero de páginas: " + caracList[2]);
			caracteristicas.append("\nEditorial: " + caracList[3]);
		} else if (prod instanceof Figura) {
			Figura figura = (Figura)prod;
			String[] caracList = figura.getValoresCaracteristicas();
			if(caracList.length < 3) return "Caracteristicas erróneas";
			
			caracteristicas.append("Dimensiones: " + caracList[0]);
			caracteristicas.append("\nMarca: " + caracList[1]);
			caracteristicas.append("\nMaterial: " + caracList[2]);
		} else if (prod instanceof Juego ){
			Juego juego = (Juego)prod;
			String[] caracList = juego.getValoresCaracteristicas();
			if(caracList.length < 3) return "Caracteristicas erróneas";
			
			caracteristicas.append("Número de jugadores: " + caracList[0]);
			caracteristicas.append("\nRango de edad: " + caracList[1]);
			caracteristicas.append("\nTipo de juego: " + caracList[2]);
		} else if (prod instanceof Pack){
			Pack pack = (Pack)prod;
			String[] caracList = pack.getValoresCaracteristicas();
			
			caracteristicas.append("Productos incluidos en el pack:");
			for(String p : caracList) {
				caracteristicas.append("\n"+p);
			}
		} else {
			caracteristicas.append(prod.getCaracteristicas());
		}
		return caracteristicas.toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaProductoCliente.BUY_ACTION:
			try {
				if(TiendaFrame.getConfirmacionUsuario("¿Desea añadir " + producto.getNombre() + " al carrito?")) {
					tienda.anadirACarritoDe(cliente, producto);
				}
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage(), VentanaMensaje.ERROR);
			}
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "";
	}
}
