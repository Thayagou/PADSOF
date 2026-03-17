package sistema;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.ImageIcon;

import venta.descuentos.*;
import venta.productos.*;
import wallapop.*;

public class Almacen {
	private Map<String, Stock> inventario  = new HashMap<>();
	private Map<String, Categoria> categorias = new HashMap<>();
	private List<Descuento> descuentos = new LinkedList<>();
	private List<ArticuloSegundaMano> articulos = new LinkedList<>();
	
	/**
	 * Crea un nuevo almacen
	 */
	public Almacen() { }
	
	/**
	 * Crea y añade un nuevo cómic al inventario
	 * @param uds Unidades de producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param fecha Fecha de publicación del cómic
	 * @param autor Autor del cómic
	 * @param numPaginas Numero de páginas del cómic
	 * @param editorial Editorial del cómic
	 * @param categorias Categorías a las que pertenece el producto
	 * @return boolean, true en caso de correcta inserción , false en caso contrario
	 */
	public boolean anadirComic(int uds, String nombre, String descripcion, double precio, ImageIcon image, LocalDate fecha, String autor, int numPaginas, String editorial, Categoria...categorias) {
		if(inventario.containsKey(nombre)) 
			return false;
			
		Comic comic = new Comic(nombre, descripcion, precio, image, fecha, autor, numPaginas, editorial, categorias);
		this.inventario.put(nombre, new Stock(comic, uds));
		return true;
	}
	
	/**
	 * Crea y añade un nuevo juego al inventario
	 * @param uds Unidades de producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param numJugadores Número de jugadores del juego
	 * @param rangoEdad Rango de edad del juego
	 * @param tipo Tipo de juego
	 * @param categorias Categorías a las que pertenece el producto
	 * @return boolean, true en caso de correcta inserción , false en caso contrario
	 */
	public boolean anadirJuego(int uds, String nombre, String descripcion, double precio, ImageIcon image, int numJugadores, String rangoEdad, TipoJuego tipo, Categoria...categorias) {
		if(inventario.containsKey(nombre))
			return false;
			
		Juego juego = new Juego(nombre, descripcion, precio, image, numJugadores, rangoEdad, tipo, categorias);
		this.inventario.put(nombre, new Stock(juego, uds));
		return true;
	}
	
	/**
	 * Crea y añade una nueva figura al inventario
	 * @param uds Unidades de producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param dimensiones Dimensiones de la figura
	 * @param marca Marca de la figura
	 * @param material Material de la figura
	 * @param categorias Categorías a las que pertenece el producto
	 * @return boolean, true en caso de correcta inserción , false en caso contrario
	 */
	public boolean anadirFigura(int uds, String nombre, String descripcion, double precio, ImageIcon image, String dimensiones, String marca, String material, Categoria...categorias) {
		if(inventario.containsKey(nombre))
			return false;
	
		Figura figura = new Figura(nombre, descripcion, precio, image, dimensiones, material, marca, categorias);
		this.inventario.put(nombre, new Stock(figura, uds));
		return true;
	}
	
	/**
	 * Crea y añade un nuevo pack al inventario
	 * @param uds Unidades de producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param productos Productos contenidos en el pack
	 * @param categorias Categorías a las que pertenece el producto
	 * @return boolean, true en caso de correcta inserción , false en caso contrario
	 */
	public boolean anadirPack(int uds, String nombre, String descripcion, double precio, ImageIcon image, Stock[] productos, Categoria...categorias) {
		if(inventario.containsKey(nombre))
			return false;
		
		Pack pack= new Pack(productos, nombre, descripcion, precio, image, categorias);
		this.inventario.put(nombre, new Stock(pack, uds));
		return true;
	}
	
	/**
	 * Devuelve las unidades en stock de un producto concreto
	 * @param producto Producto del que se devuelve las unidades
	 * @return int, unidades en stock del producto, -1 en caso de error
	 */
	public int getUnidades(Producto producto) {
		Stock s = inventario.get(producto.getNombre());
		if(s == null)
			return -1;
		return s.getUdsEnStock();
	}
	
	/**
	 * Método para obtener el stock de un producto
	 * @param producto Producto del que se quiere el stock
	 * @return Stock del producto
	 */
	public Stock getStock(Producto producto) {
		return inventario.get(producto.getNombre());
	}
	
	/**
	 * Método para obtener el stock de un producto con su nombre
	 * @param nombre Nombre del producto
	 * @return Stock del producto con ese nombre
	 */
	public Stock getStock(String nombre) {
		return inventario.get(nombre);
	}
	
	/**
	 * Eliminar un producto del inventario
	 * @param producto Producto que se quiere eliminar
	 * @return true si se elimina correctamente
	 */
	public boolean eliminarProducto(Producto producto) {
		producto.eliminar();
		inventario.remove(producto.getNombre());
		return true;
	}
	
	/**
	 * Método para modificar los datos de un producto
	 * @param producto Producto a modificar
	 * @param udsStock Nuevas unidades en stock del producto
	 * @param nombre Nombre del producto
	 * @param desc Descripicón del producto
	 * @param precio Precio del producto
	 * @param imagen Imagen del producto
	 * @param categorias Nuevas categorias del producto
	 * @return ture si se pudo modificar, false si no se pudo
	 */
	public boolean modificarProducto(Producto producto, int udsStock, String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias) {
		if(!this.categorias.containsKey(producto.getNombre()))
			return false;
		
		Stock st = this.getStock(producto);
		st.setUdsEnStock(udsStock);
		
		this.inventario.remove(producto.getNombre());
		producto.setNombre(nombre);
		this.inventario.put(nombre, st);
		producto.setDescripcion(desc);
		producto.setPrecio(precio);
		producto.setImagen(imagen);
		producto.setCategorias(categorias); /* !!! Este metodo puede fallar parcialmente*/
		return true;
	}
	
	/**
	 * Añade una lista de productos desde un fichero
	 * @return true en caso de que se añadan correctamente, false en caso contrario
	 */
	public boolean anadirProductosDeFichero(String fProductos) {
		String linea;
		
		try (BufferedReader br = new BufferedReader(new FileReader(fProductos))) {
			while((linea = br.readLine()) != null) {
				String partes[] = linea.split(";");
				
				String nombre = partes[1];
				String desc = partes[2];
				double precio = Double.parseDouble(partes[3]);
				int uds = Integer.parseInt(partes[4]);
				String categorias[] = partes[5].split(",");
				
				if(partes[0].equals("C")) {
					int numPags = Integer.parseInt(partes[6]);
					String autor = partes[7];
					String editorial = partes[8];
					String fecha[] = partes[9].split(",");
					LocalDate fechaPublicacion = LocalDate.of(fecha[0], fecha[1], fecha[2]);
					
					Comic comic = new Comic(nombre, desc, precio, null, fechaPublicacion, autor, numPags, editorial, categorias);
					inventario.put(partes[1], new Stock(comic, uds));
				} else if(partes[0].equals("J")) {
					
				} else if(partes[0].equals("F")) {
					
				} else {
					return false;
				}
		    }
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Método para obtener una categoría con su nombre
	 * @param nombre Nombre de la categoría
	 * @return Categoría con el nombre que se introduce
	 */
	public Categoria getCategoria(String nombre) {
		return categorias.get(nombre);
	}
	
	/**
	 * Crea y añade una categoría al almacén
	 * @param nombre Nombre de la categoría
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirCategoria(String nombre) {
		if(categorias.containsKey(nombre))
			return false;
		
		this.categorias.put(nombre, new Categoria(nombre));
		return true;
	}
	
	/**
	 * Elimina una categoría del almacén
	 * @param nombre Nombre de la categoría
	 * @return true en caso de que se elimine correctamente, false en caso contrario
	 */
	public boolean eliminarCategoria(Categoria categoria) {
		categoria.eliminar();
		categorias.remove(categoria.getNombre());
		return true;
	}
	
	/**
	 * Añade un producto a una categoría
	 * @param producto Producto al que se quiere añadir
	 * @param categoria Categoría que se quiere añadir
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirProductoACategoria(Producto producto, Categoria categoria) {
			return producto.anadirCategorias(categoria);
	}
	
	/**
	 * Quita un producto de una categoría
	 * @param producto Producto que se quiere quitar
	 * @param categoria Categoría de la que se quiere quitar
	 * @return true en caso de que se quite correctamente, false en caso contrario
	 */
	public boolean quitarProductoDeCategoria(Producto producto, Categoria categoria) {
		producto.quitarCategorias(categoria);
		return true;
	}
	
	/**
	 * Modifica el nombre de una categoría
	 * @param categoria Categoría que se quiere cambiar
	 * @param nuevoNombre Nuevo nombre para la categoría
	 * @return true en caso de que se modifique correctamente, false en caso contrario
	 */
	public boolean modificarCategoria(Categoria categoria, String nuevoNombre) {
		if((!categorias.containsKey(categoria.getNombre())) || categorias.containsKey(nuevoNombre)) {
			return false;
		}
		categorias.remove(categoria.getNombre());
		categoria.setNombre(nuevoNombre);
		categorias.put(nuevoNombre, categoria);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de dinero a un producto
	 * @param producto Producto al que se asocia el descuento
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param precio Precio que se descuenta
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirDescuentoDinero(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) {
		Descuento descuento = new DescuentoDinero(valorMin, inicio, fin, condicion, precio);
		if(!producto.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de dinero a una categoría
	 * @param categoria Categoría a la que se asocia el descuento
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param precio Precio que se descuenta
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirDescuentoDinero(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) {
		Descuento descuento = new DescuentoDinero(valorMin, inicio, fin, condicion, precio);
		if(!categoria.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de porcentaje a un producto
	 * @param producto Producto al que se asocia el descuento
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param porcentaje Porcentaje que se descuenta
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirDescuentoPorcentaje(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) {
		Descuento descuento = new DescuentoPorcentaje(valorMin, inicio, fin, condicion, porcentaje);
		if(!producto.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de porcentaje a una categoría
	 * @param categoria Categoría a la que se asocia el descuento
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param porcentaje Porcentaje que se descuenta
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirDescuentoPorcentaje(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) {
		Descuento descuento = new DescuentoPorcentaje(valorMin, inicio, fin, condicion, porcentaje);
		if(!categoria.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de regalo a un producto
	 * @param producto Producto al que se asocia el descuento
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param regalo Regalo que se da
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirDescuentoRegalo(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) {
		Descuento descuento = new DescuentoRegalo(valorMin, inicio, fin, condicion, regalo);
		if(!producto.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de regalo a una categoría
	 * @param categoria Categoría a la que se asocia el descuento
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param regalo Regalo que se da
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirDescuentoRegalo(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) {
		Descuento descuento = new DescuentoRegalo(valorMin, inicio, fin, condicion, regalo);
		if(!categoria.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Elimina todos los descuentos que estén caducados
	 * @return true cuando todos los descuentos caducados hayan sido eliminados
	 */
	public boolean eliminarDescuentosCaducados() {
		for(Descuento d : descuentos) {
			if(!d.isVigente()) {
				descuentos.remove(d);
			}
		}
		return true;
	}
	
	/**
	 * Devuelve una lista de productos que cumplen unas ciertas condiciones de categorías y precio
	 * @param categorias Categorías a las que deben pertenecer los productos
	 * @param precioMin Precio mínimo de los productos
	 * @param precioMax Precio máximo de los productos
	 * @return Producto[], un array de productos que cumplen las condiciones
	 */
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
	
	/**
	 * Añade un artículo de segunda mano ya creado al almacén
	 * @param articulo, Artículo que se añade
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirArticuloSegundaMano(ArticuloSegundaMano articulo) {
		return articulos.add(articulo);
	}
	
	/**
	 * Elimina un artículo de segunda mano del almacén
	 * @param articulo, Artículo que se elimina
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean eliminarArticuloSegundaMano(ArticuloSegundaMano articulo) {
		return articulos.remove(articulo);
	}
}