package controladores.noRegistrado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.venta.productos.*;
import vistas.common.app.TiendaFrame;
import vistas.noRegistrado.VentanaProductoSinRegistrar;

/**
 * Esta clase representa el controlador de la ventana de mostrar un producto a un cliente sin registrar
 */
public class ControlProductoSinRegistrar implements ActionListener, ControladorPantalla {
	/** Ventana que se muestra */
	private VentanaProductoSinRegistrar vista;

	/**
	 * Constructor del controlador de la ventana de productos sin registrar
	 * @param tienda Modelo de la tienda
	 * @param producto Producto que se está mostrando
	 */
	public ControlProductoSinRegistrar(Tienda tienda, Producto producto) {
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		String imageRoute = producto.getImagen();
		
		String caracteristicas = getCaracteristicas(producto);
		
		this.vista = new VentanaProductoSinRegistrar(producto.getNombre(), 
				producto.getDescripcion(), imageRoute, producto.getPuntuacionMedia(), 
				producto.getPrecio(), caracteristicas, categorias.toArray(new String[0]));
		for(Resena r : producto.getResenas()) {
			vista.anadirPanelResena(r.getPuntuacion(), r.getComentario(), r.getUsuario().getNombre());
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * Coge las características del producto
	 * @param prod Producto
	 * @return String con las características del producto
	 */
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
		/* sin acciones para esta ventana */
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana se muestra la información de un producto. Para poder añadirlo al producto, inicia sesión como cliente.";
	}
}
