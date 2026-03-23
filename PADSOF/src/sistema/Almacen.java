package sistema;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import javax.swing.ImageIcon;

import exceptions.*;
import venta.descuentos.*;
import venta.productos.*;
import wallapop.*;

/**
 * Clase que implementa el almacén de la tienda con funcionalidades de venta y gestion de productos
 * 
 * Autores: Juan Ibáñez, Tiago Oselka, Claudia Saiz
 */
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
	 * Método para obtener todas las categorías del almacen
	 * @return Array de categorías del almacen
	 */
	public Categoria[] getCategorias() {
		return categorias.values().toArray(new Categoria[0]);
	}
	
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
	public boolean anadirComic(int uds, String nombre, String descripcion, double precio, ImageIcon image, LocalDate fecha, String autor, int numPaginas, String editorial, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
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
	public boolean anadirJuego(int uds, String nombre, String descripcion, double precio, ImageIcon image, int numJugadores, String rangoEdad, TipoJuego tipo, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
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
	public boolean anadirFigura(int uds, String nombre, String descripcion, double precio, ImageIcon image, String dimensiones, String marca, String material, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
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
	public boolean anadirPack(int uds, String nombre, String descripcion, double precio, ImageIcon image, Stock[] productos, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
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
	public int getUnidades(Producto producto) throws InvalidArgumentException {
		if(producto == null) throw new InvalidArgumentException("El producto no puede ser null");
		
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
	public Stock getStock(Producto producto) throws InvalidArgumentException {
		if(producto == null) throw new InvalidArgumentException("El producto no puede ser null");
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
	public boolean eliminarProducto(Producto producto) throws InvalidArgumentException {
		if(producto == null) throw new InvalidArgumentException("El producto a eliminar no puede ser null");
		
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
	public boolean modificarProducto(Producto producto, int udsStock, String nombre, String desc, double precio, ImageIcon imagen, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(producto == null || nombre == null || desc == null || categorias == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		if(udsStock < 0) throw new InvalidArgumentException("Las unidades en stock no pueden ser negativas");
		if(precio < 0) throw new InvalidArgumentException("El precio del producto no puede ser negativo");
		
		
		if(!this.inventario.containsKey(producto.getNombre()))
			return false;
		
		producto.setCategorias(categorias);	/*Se puede lanzar una excepción aquí si las categorias son incompatibles*/
		
		Stock st = this.getStock(producto);
		st.setUdsEnStock(udsStock);
		
		this.inventario.remove(producto.getNombre());
		producto.setNombre(nombre);
		this.inventario.put(nombre, st);
		
		producto.setDescripcion(desc);
		producto.setPrecio(precio);
		producto.setImagen(imagen);
		
		return true;
	}
	
	/**
	 * Añade una lista de productos desde un fichero
	 * @param fProductos, nombre del fichero con datos de productos a añadir
	 * @return true en caso de que se añadan correctamente todos los productos, false en caso contrario
	 */
	public boolean anadirProductosDeFichero(String fProductos) throws DoubleDiscountException, InvalidArgumentException {
		if(fProductos == null) throw new InvalidArgumentException("El nombre del fichero de productos no se puede dejar vacío");
		String linea;
		
		try (BufferedReader br = new BufferedReader(new FileReader(fProductos))) {
			while((linea = br.readLine()) != null) {
				String partes[] = linea.split(";");
				
				String nombre = partes[1];
				String desc = partes[2];
				double precio = Double.parseDouble(partes[3]);
				int uds = Integer.parseInt(partes[4]);
				
				String nombreCateg[] = partes[5].split(",");
				List <Categoria> categorias = new ArrayList<>();
				for(String c : nombreCateg) {
					if(this.categorias.containsKey(c)) {
						categorias.add(this.categorias.get(c));
					} else {
						return false;
					}
				}
				
				if(partes[0].equals("C")) {
					int numPags = Integer.parseInt(partes[6]);
					String autor = partes[7];
					String editorial = partes[8];
					String fecha[] = partes[9].split(",");
					LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
					
					this.anadirComic(uds, nombre, desc, precio, null, fechaPublicacion, autor, numPags, editorial, categorias.toArray(new Categoria[0]));
				} else if(partes[0].equals("J")) {
					int numJugs = Integer.parseInt(partes[10]);
					String rangoEdad = partes[11];
					TipoJuego tipoJuego = TipoJuego.valueOf(partes[12]);
					
					this.anadirJuego(uds, nombre, desc, precio, null, numJugs, rangoEdad, tipoJuego, categorias.toArray(new Categoria[0]));
				} else if(partes[0].equals("F")) {
					String marca = partes[13];
					String material = partes[14];
					String dimensiones = partes[15];
					
					this.anadirFigura(uds, nombre, desc, precio, null, dimensiones, marca, material, categorias.toArray(new Categoria[0]));
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
	 * @return true si se pudo añadir, false si ya existe una categoria con ese nombre
	 */
	public boolean anadirCategoria(String nombre) throws InvalidArgumentException {
		if(nombre == null) throw new InvalidArgumentException("El nombre de la categoría no puede estar vacío");
		if(categorias.containsKey(nombre)) return false;
		
		this.categorias.put(nombre, new Categoria(nombre));
		return true;
	}
	
	/**
	 * Elimina una categoría del almacén
	 * @param nombre Nombre de la categoría
	 * @return true en caso de que se elimine correctamente, false en caso contrario
	 */
	public boolean eliminarCategoria(Categoria categoria) throws InvalidArgumentException {
		if(categoria == null) throw new InvalidArgumentException("La categoría a eliminar no puede ser null");
		
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
	public boolean anadirProductoACategoria(Producto producto, Categoria categoria)
			throws InvalidArgumentException, DoubleDiscountException {
		if(producto == null || categoria == null) throw new InvalidArgumentException("No se pueden pasar argumentos null");
		
		return producto.anadirCategorias(categoria);
	}
	
	/**
	 * Quita un producto de una categoría
	 * @param producto Producto que se quiere quitar
	 * @param categoria Categoría de la que se quiere quitar
	 * @return true en caso de que se quite correctamente, false en caso contrario
	 */
	public boolean quitarProductoDeCategoria(Producto producto, Categoria categoria) throws InvalidArgumentException {
		if(producto == null || categoria == null) throw new InvalidArgumentException("No se pueden pasar argumentos null");
		
		producto.quitarCategorias(categoria);
		return true;
	}
	
	/**
	 * Modifica el nombre de una categoría
	 * @param categoria Categoría que se quiere cambiar
	 * @param nuevoNombre Nuevo nombre para la categoría
	 * @return true en caso de que se modifique correctamente, false en caso contrario
	 */
	public boolean modificarCategoria(Categoria categoria, String nuevoNombre) throws InvalidArgumentException {
		if(categoria == null || nuevoNombre == null) throw new InvalidArgumentException("La categoría y el nombre no pueden ser null");
		
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
	public boolean anadirDescuentoDinero(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(producto == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
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
	public boolean anadirDescuentoDinero(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(categoria == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
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
	public boolean anadirDescuentoPorcentaje(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(producto == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
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
	public boolean anadirDescuentoPorcentaje(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(categoria == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
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
	public boolean anadirDescuentoRegalo(Producto producto, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(producto == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
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
	public boolean anadirDescuentoRegalo(Categoria categoria, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(categoria == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
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
		descuentos.removeIf(d -> !d.isCaducado());
		return true;
	}
	
	/**
	 * Añade un artículo de segunda mano ya creado al almacén
	 * @param articulo, Artículo que se añade
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean anadirArticuloSegundaMano(ArticuloSegundaMano articulo) throws IllegalArgumentException {
		if(articulo == null) throw new IllegalArgumentException();
		return articulos.add(articulo);
	}
	
	/**
	 * Elimina un artículo de segunda mano del almacén
	 * @param articulo, Artículo que se elimina
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 */
	public boolean eliminarArticuloSegundaMano(ArticuloSegundaMano articulo) {
		if(articulo == null) throw new IllegalArgumentException();
		return articulos.remove(articulo);
	}
	
	/**
	 * Devuelve una lista de productos que cumplen unas ciertas condiciones de categorías y precio
	 * @param categorias Categorías a las que deben pertenecer los productos
	 * @param precioMin Precio mínimo de los productos
	 * @param precioMax Precio máximo de los productos
	 * @return Producto[], un array de productos que cumplen las condiciones
	 */
	public Producto[] getProductosPorFiltros(Categoria[] categorias, double precioMin, double precioMax, double estrellasMin) throws IllegalArgumentException {
		if(categorias == null || precioMin < 0 || precioMax < 0 || estrellasMin < 0 || estrellasMin > 5) throw new IllegalArgumentException();
		
		List<Producto> productos = new ArrayList<>();
		for(Categoria c : categorias) {
			if(c == null) continue;
			
			for(Producto p : c.getProductos()) {
				if(p.getPrecio() >= precioMin && p.getPrecio() <= precioMax && p.getPuntuacionMedia() >= estrellasMin) {
					productos.add(p);
				}
			}
		}
		return productos.toArray(new Producto[0]);
	}
	
	public Producto[] getListaRecomendacion() {
		return null;
	}
}