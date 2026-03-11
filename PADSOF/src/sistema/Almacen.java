package sistema;

import java.time.LocalDate;
import java.util.*;
import javax.swing.ImageIcon;

import venta.*;
import wallapop.ArticuloSegundaMano;

public class Almacen {
	private Map<String, Stock> inventario  = new HashMap<>();
	private Map<String, Categoria> categorias = new HashMap<>();
	private List<Descuento> descuentos = new LinkedList<>();
	private List<ArticuloSegundaMano> articulos = new LinkedList<>();

	public Almacen() { }
	
	public boolean anadirComic(int uds, String nombre, String descripcion, double precio, ImageIcon image, LocalDate fecha, String autor, int numPaginas, String editorial, Categoria...categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true)
				inventario.get(nombre).getProducto().setEliminado(false);
			else
				return false;
		}
		Comic comic = new Comic(nombre, descripcion, precio, image, fecha, autor, numPaginas, editorial, categorias);
		this.inventario.put(nombre, new Stock(comic, uds));
		return true;
	}
	
	public boolean anadirJuego(int uds, String nombre, String descripcion, double precio, ImageIcon image, int numJugadores, String rangoEdad, TipoJuego tipo, Categoria...categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true)
				inventario.get(nombre).getProducto().setEliminado(false);
			else
				return false;
		}
		Juego juego = new Juego(nombre, descripcion, precio, image, numJugadores, rangoEdad, tipo, categorias);
		this.inventario.put(nombre, new Stock(juego, uds));
		return true;
	}
	
	public boolean anadirFigura(int uds, String nombre, String descripcion, double precio, ImageIcon image, String dimensiones, String marca, String material, Categoria...categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true)
				inventario.get(nombre).getProducto().setEliminado(false);
			else
				return false;
		}
		Figura figura = new Figura(nombre, descripcion, precio, image, dimensiones, material, marca, categorias);
		this.inventario.put(nombre, new Stock(figura, uds));
		return true;
	}
	
	public boolean anadirPack(int uds, String nombre, String descripcion, double precio, ImageIcon image, Stock... productos) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true)
				inventario.get(nombre).getProducto().setEliminado(false);
			else
				return false;
		}
		
		Set<Categoria> categorias = new HashSet<>();
		for(Stock s : productos) {
			for(Categoria c : s.getProducto().getCategorias()) {
				categorias.add(c);
			}
		}
		
		Pack pack= new Pack(new HashSet<>(Arrays.asList(productos)), nombre, descripcion, precio, image, categorias.toArray(new Categoria[0]));
		this.inventario.put(nombre, new Stock(pack, uds));
		return true;
	}

	public boolean anadirProductosDeFichero() {
		return true;
	}
	
	public boolean eliminarProducto(Producto producto) {
		producto.setEliminado(true);
		return true;
	}
	
	public boolean anadirCategoria(String nombre) {
		if(categorias.containsKey(nombre)) {
			if(categorias.get(nombre).isEliminada() == true)
				categorias.get(nombre).setEliminada(false);
			else
				return false;
		}
		
		this.categorias.put(nombre, new Categoria(nombre));
		return true;
	}
	
	public boolean eliminarCategoria(String nombre) {
		categorias.get(nombre).setEliminada(true);
		return true;
	}
	
	public boolean modificarCategoria() {
		return true;
	}
	
	private boolean anadirACategoria(String nombre, String nuevoNombre) {
		Categoria categoria = categorias.get(nombre);
		if(categoria == null) {
			return false;
		}
		categoria.setNombre(nuevoNombre);
		return true;
	}
	
	public boolean anadirDescuento(double valorMin, CondicionDescuento condicion) {
		return true;
	}
	
	public boolean eliminarDescuentosCaducados() {
		return true;
	}
	
	private boolean puedeAplicarseDescuento() {
		return true;
	}
	
	public Set<Producto> getProductosPorFiltros() {
		return null;
	}
	
	public int getStock(String nombre) {
		return inventario.get(nombre).getUdsEnStock();
	}
}
