package sistema;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	public boolean anadirComic(int uds, String nombre, String descripcion, double precio, ImageIcon image, LocalDate fecha, String autor, int numPaginas, String editorial, Categoria[] categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true) {
				inventario.get(nombre).getProducto().setEliminado(false);
				return true;
			}else
				return false;
		}
		Comic comic = new Comic(nombre, descripcion, precio, image, fecha, autor, numPaginas, editorial, categorias);
		for(Categoria c : categorias) {
			c.anadirProducto(comic);
		}
		this.inventario.put(nombre, new Stock(comic, uds));
		return true;
	}
	
	public boolean anadirJuego(int uds, String nombre, String descripcion, double precio, ImageIcon image, int numJugadores, String rangoEdad, TipoJuego tipo, Categoria[] categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true) {
				inventario.get(nombre).getProducto().setEliminado(false);
				return true;
			}else
				return false;
		}
		Juego juego = new Juego(nombre, descripcion, precio, image, numJugadores, rangoEdad, tipo, categorias);
		for(Categoria c : categorias) {
			c.anadirProducto(juego);
		}
		this.inventario.put(nombre, new Stock(juego, uds));
		return true;
	}
	
	public boolean anadirFigura(int uds, String nombre, String descripcion, double precio, ImageIcon image, String dimensiones, String marca, String material, Categoria[] categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true) {
				inventario.get(nombre).getProducto().setEliminado(false);
				return true;
			}else
				return false;
		}
		Figura figura = new Figura(nombre, descripcion, precio, image, dimensiones, material, marca, categorias);
		for(Categoria c : categorias) {
			c.anadirProducto(figura);
		}
		this.inventario.put(nombre, new Stock(figura, uds));
		return true;
	}
	
	public boolean anadirPack(int uds, String nombre, String descripcion, double precio, ImageIcon image, Stock[] productos, Categoria[] categorias) {
		if(inventario.containsKey(nombre)) {
			if(inventario.get(nombre).getProducto().isEliminado() == true) {
				inventario.get(nombre).getProducto().setEliminado(false);
				return true;
			}else
				return false;
		}
		
		Pack pack= new Pack(new HashSet<>(Arrays.asList(productos)), nombre, descripcion, precio, image, categorias);
		for(Categoria c : categorias) {
			c.anadirProducto(pack);
		}
		this.inventario.put(nombre, new Stock(pack, uds));
		return true;
	}
	
	public int getStock(String nombre) {
		return inventario.get(nombre).getUdsEnStock();
	}
	
	public boolean eliminarProducto(Producto producto) {
		producto.setEliminado(true);
		return true;
	}
	
	public boolean anadirProductosDeFichero() {
		return true;
	}
	
	public boolean anadirCategoria(String nombre) {
		if(categorias.containsKey(nombre)) {
			if(categorias.get(nombre).isEliminada() == true) {
				categorias.get(nombre).setEliminada(false);
				return true;
			} else
				return false;
		}
		
		this.categorias.put(nombre, new Categoria(nombre));
		return true;
	}
	
	public boolean eliminarCategoria(String nombre) {
		categorias.get(nombre).setEliminada(true);
		return true;
	}
	
	public boolean anadirProductoACategoria(Producto producto, Categoria categoria) {
			return categoria.anadirProducto(producto);
	}
	
	public boolean quitarProductoDeCategoria(Producto producto, Categoria categoria) {
		categoria.quitarProducto(producto);
		return true;
	}
	
	public boolean modificarCategoria(String nombre, String nuevoNombre) {
		Categoria categoria = categorias.get(nombre);
		if(categoria == null) {
			return false;
		}
		categoria.setNombre(nuevoNombre);
		return true;
	}
	
	public boolean anadirDescuentoDinero(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) {
		Descuento descuento = new DescuentoDinero(valorMin, inicio, fin, condicion, precio);
		if(!puedeAplicarseDescuento(producto))
			return false;
		producto.setDescuento(descuento);
		descuentos.add(descuento);
		return true;
	}
	
	public boolean anadirDescuentoDinero(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) {
		Descuento descuento = new DescuentoDinero(valorMin, inicio, fin, condicion, precio);
		if(!puedeAplicarseDescuento(categoria))
			return false;
		categoria.setDescuento(descuento);
		descuentos.add(descuento);
		return true;
	}
	
	public boolean anadirDescuentoPorcentaje(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) {
		Descuento descuento = new DescuentoPorcentaje(valorMin, inicio, fin, condicion, porcentaje);
		if(!puedeAplicarseDescuento(producto))
			return false;
		producto.setDescuento(descuento);
		descuentos.add(descuento);
		return true;
	}
	
	public boolean anadirDescuentoPorcentaje(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) {
		Descuento descuento = new DescuentoPorcentaje(valorMin, inicio, fin, condicion, porcentaje);
		if(!puedeAplicarseDescuento(categoria))
			return false;
		categoria.setDescuento(descuento);
		descuentos.add(descuento);
		return true;
	}
	
	public boolean anadirDescuentoRegalo(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) {
		Descuento descuento = new DescuentoRegalo(valorMin, inicio, fin, condicion, regalo);
		if(!puedeAplicarseDescuento(producto))
			return false;
		producto.setDescuento(descuento);
		descuentos.add(descuento);
		return true;
	}
	
	public boolean anadirDescuentoRegalo(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) {
		Descuento descuento = new DescuentoRegalo(valorMin, inicio, fin, condicion, regalo);
		if(!puedeAplicarseDescuento(categoria))
			return false;
		categoria.setDescuento(descuento);
		descuentos.add(descuento);
		return true;
	}
	
	private boolean puedeAplicarseDescuento(Producto producto) {
		if(producto.tieneDescuento())
			return false;
		return true;
	}
	
	private boolean puedeAplicarseDescuento(Categoria categoria) {
		if(categoria.tieneDescuento())
			return false;
		for(Producto p : categoria.getProductos()) {
			if(p.tieneDescuento())
				return false;
		}
		return true;
	}
	
	public boolean eliminarDescuentosCaducados() {
		for(Descuento d : descuentos) {
			d.isVigente();
		}
		return true;
	}
	
	public Producto[] getProductosPorFiltros(Categoria[] categorias, double precioMin, double precioMax) {
		List<Producto> productos = new ArrayList<>();
		for(Categoria c : categorias) {
			for(Producto p : c.getProductos()) {
				if(p.getPrecio() >= precioMin && p.getPrecio() <= precioMax) {
					productos.add(p);
				}
			}
		}
		return productos.toArray(new Producto[0]);
	}
	
	public boolean anadirArticuloSegundaMano(ArticuloSegundaMano articulo) {
		return articulos.add(articulo);
	}
	
	public boolean quitarArticulosSegundaMano(ArticuloSegundaMano articulo) {
		return articulos.remove(articulo);
	}
}
