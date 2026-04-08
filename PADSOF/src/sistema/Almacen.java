package sistema;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import javax.swing.ImageIcon;
import venta.productos.caracteristicas.*;
import estadistica.ObservadorProducto;
import exceptions.*;
import usuario.ClienteRegistrado;
import usuario.Empleado;
import usuario.Permiso;
import venta.descuentos.*;
import venta.productos.*;
import wallapop.*;

/**
 * Clase que implementa el almacén de la tienda con funcionalidades de venta y gestion de productos
 */
public class Almacen implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Inventario de productos*/
	private Map<String, Stock> inventario  = new HashMap<>();
	/**Categorias existentes*/
	private Map<String, Categoria> categorias = new HashMap<>();
	/**Descuentos existentes*/
	private List<Descuento> descuentos = new LinkedList<>();
	/**Articulos existentes*/
	private List<ArticuloSegundaMano> articulos = new LinkedList<>();
	/**Observador que permite añadir productos a las estadísticas*/
	private ObservadorProducto observador;
	
	/**
	 * Crea un nuevo almacen
	 * @param obs ObservadorProducto
	 */
	public Almacen(ObservadorProducto obs) {
		observador = obs;
	}
	
	/**
	 * Método para añadir un nuevo producto al almacén
	 * @param empleado Empleado que trata de añadir el Producto a la tienda
	 * @param uds Número de unidades del producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param caracteristicas Objeto que contiene las características específicas del producto
	 * @param categorias Array de categorías del producto
	 * @throws InvalidArgumentException Si alguno de los argumentos es inválido
	 * @throws DoubleDiscountException Si las categorías son incompatibles entre sí o con el producto por descuentos
	 * @throws InvalidPermitException 
	 */
	public void anadirProducto(Empleado empleado, int uds, String nombre, String descripcion, double precio, ImageIcon image, CaracteristicasProducto caracteristicas, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException, InvalidPermitException {
		if (empleado.tienePermiso(Permiso.PRODUCTOS) == false) throw new InvalidPermitException("", descripcion, null, empleado);
		if(inventario.containsKey(nombre)) throw new InvalidArgumentException("Ya existe un producto con el mismo nombre en el almacén");
		Producto p = caracteristicas.crearProducto(nombre, descripcion, precio, image, categorias);
		this.inventario.put(nombre, new Stock(p, uds));
		observador.guardarProducto(p);
	}
	
	/**
	 * Crea y añade un nuevo cómic al inventario
	 * @param empleado Empleado que trata de añadir el comic a la tienda
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
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public void anadirComic(Empleado empleado, int uds, String nombre, String descripcion, double precio, ImageIcon image, LocalDate fecha, String autor, int numPaginas, String editorial, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		anadirProducto(empleado, uds, nombre, descripcion, precio, image, new CaracteristicasComic(fecha, autor, numPaginas, editorial), categorias);
	}
	
	/**
	 * Crea y añade un nuevo juego al inventario
	 * @param empleado Empleado que trata de añadir el juego a la tienda
	 * @param uds Unidades de producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripción del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param numJugadores Número de jugadores del juego
	 * @param rangoEdad Rango de edad del juego
	 * @param tipo Tipo de juego
	 * @param categorias Categorías a las que pertenece el producto
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public void anadirJuego(Empleado empleado, int uds, String nombre, String descripcion, double precio, ImageIcon image, int numJugadores, String rangoEdad, TipoJuego tipo, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		anadirProducto(empleado, uds, nombre, descripcion, precio, image, new CaracteristicasJuego(numJugadores, rangoEdad, tipo), categorias);
	}
	
	/**
	 * Crea y añade una nueva figura al inventario
	 * @param empleado Empleado que trata de añadir la figura a la tienda
	 * @param uds Unidades de producto
	 * @param nombre Nombre del producto
	 * @param descripcion Descripcion del producto
	 * @param precio Precio del producto
	 * @param image Imagen del producto
	 * @param dimensiones Dimensiones de la figura
	 * @param marca Marca de la figura
	 * @param material Material de la figura
	 * @param categorias Categorías a las que pertenece el producto
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public void anadirFigura(Empleado empleado, int uds, String nombre, String descripcion, double precio, ImageIcon image, String dimensiones, String marca, String material, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		anadirProducto(empleado, uds, nombre, descripcion, precio, image, new CaracteristicasFigura(dimensiones, marca, material), categorias);
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
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public void anadirPack(Empleado empleado, int uds, String nombre, String descripcion, double precio, ImageIcon image, Stock[] productos, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException {
		anadirProducto(empleado, uds, nombre, descripcion, precio, image, new CaracteristicasPack(productos), categorias);
	}
	
	/**
	 * Devuelve las unidades en stock de un producto concreto
	 * @param producto Producto del que se devuelve las unidades
	 * @return int, unidades en stock del producto, -1 en caso de error
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public int getUnidades(Producto producto) throws InvalidArgumentException {
		if(producto == null) throw new InvalidArgumentException("El producto no puede ser null");
		
		Stock s = inventario.get(producto.getNombre());
		if(s == null)
			return -1;
		return s.getUdsEnStock();
	}
	
	/**
	 * Método para obtener todas las categorías del almacen
	 * @return Array de categorías del almacen
	 */
	public Categoria[] getCategorias() {
		return categorias.values().toArray(new Categoria[0]);
	}
	
	/**
	 * Método para obtener el stock de un producto
	 * @param producto Producto del que se quiere el stock
	 * @return Stock del producto
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public Stock getStock(Producto producto) throws InvalidArgumentException {
		if(producto == null) throw new InvalidArgumentException("El producto no puede ser null");
		return inventario.get(producto.getNombre());
	}
	
	/**
	 * Método para obtener el stock de un producto con su nombre
	 * @param nombre Nombre del producto
	 * @return Stock del producto con ese nombre
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public Stock getStock(String nombre) throws InvalidArgumentException {
		if(!inventario.containsKey(nombre)) throw new InvalidArgumentException("El producto no se encuantra en la tienda");
		return inventario.get(nombre);
	}
	
	/**
	 * Método para obtener el inventario total de la tienda
	 * @return array de Stock, el inventario total de la tienda
	 */
	public Stock[] getInventario() {
		return inventario.values().toArray(new Stock[0]);
	}
	
	/**
	 * Eliminar un producto del inventario
	 * @param producto Producto que se quiere eliminar
	 * @return true si se elimina correctamente
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
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
	 * @param caracteristicas Caracteristicas concretas de cada tipo de producto
	 * @param categorias Nuevas categorias del producto
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 * @throws InvalidPermitException Se lanza en caso de que el empleado no tenga los permisos adecuados
	 */
	public void modificarProducto(Empleado empleado, Producto producto, int udsStock, String nombre, String desc, double precio, ImageIcon imagen, CaracteristicasProducto caracteristicas, Categoria...categorias) 
			throws InvalidArgumentException, DoubleDiscountException, InvalidPermitException {
		if (empleado.tienePermiso(Permiso.PRODUCTOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "gestionar pedidos", Permiso.PRODUCTOS, empleado);
		
		if(producto == null || nombre == null || desc == null || caracteristicas == null || categorias == null)
			throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		if(udsStock < 0) throw new InvalidArgumentException("Las unidades en stock no pueden ser negativas");
		if(precio < 0) throw new InvalidArgumentException("El precio del producto no puede ser negativo");
		
		
		if(!this.inventario.containsKey(producto.getNombre()))
			throw new InvalidArgumentException("El producto no existe en el almacén");
		
		Categoria[] categoriasViejas = producto.getCategorias();
		producto.setCategorias(categorias);	/*Se puede lanzar una excepción aquí si las categorias son incompatibles*/
		
		try {
			producto.setCaracteristicas(caracteristicas);
			
			Stock st = this.getStock(producto);
			st.setUdsEnStock(udsStock);
			
			this.inventario.remove(producto.getNombre());
			producto.setNombre(nombre);
			this.inventario.put(nombre, st);
			
			producto.setDescripcion(desc);
			producto.setPrecio(precio);
			producto.setImagen(imagen);
		} catch(InvalidArgumentException e) {
			producto.setCategorias(categoriasViejas);
			throw new InvalidArgumentException("Algo falló al intentar modificar el producto por argumentos inválidos");
		}
	}
	
	/**
	 * Añade una lista de productos desde un fichero
	 * @param fProductos, nombre del fichero con datos de productos a añadir
	 * @return true en caso de que se añadan correctamente todos los productos, false en caso contrario
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws InvalidPermitException Se lanza en caso de que el empleado no tenga los permisos adecuados
	 */
	public boolean anadirProductosDeFichero(Empleado empleado, String fProductos) throws DoubleDiscountException, InvalidArgumentException, InvalidPermitException {
		if (empleado.tienePermiso(Permiso.PRODUCTOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "gestionar pedidos", Permiso.PRODUCTOS, empleado);
		
		if(fProductos == null) throw new InvalidArgumentException("El nombre del fichero de productos no se puede dejar vacío");
		String linea;
		
		try (BufferedReader br = new BufferedReader(new FileReader(fProductos))) {
			while((linea = br.readLine()) != null) {
				String partes[] = linea.split(";");
				String nombre = partes[1];
				String desc = partes[2];
				double precio;
				int uds;
				try {
	                precio = Double.parseDouble(partes[3]);
	                uds    = Integer.parseInt(partes[4]);
	            } catch (NumberFormatException e) {
	                throw new InvalidArgumentException("Datos de producto inválidos: " + e.getMessage(), "cargar fichero de productos");
	            }
				
				String nombreCateg[] = partes[5].split(",");
				List <Categoria> categorias = new ArrayList<>();
				for(String c : nombreCateg) {
					if(this.categorias.containsKey(c)) {
						categorias.add(this.categorias.get(c));
					} else {
						throw new InvalidArgumentException("Categoría no existente: " + c, "cargar fichero de productos");
					}
				}
				
				switch (partes[0]) {
					case "C" -> {
						try {
							int numPags = Integer.parseInt(partes[6]);
							String autor = partes[7];
							String editorial = partes[8];
							String fecha[] = partes[9].split(",");
							LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
							
							this.anadirComic(empleado, uds, nombre, desc, precio, null, fechaPublicacion, autor, numPags, editorial, categorias.toArray(new Categoria[0]));
						} catch (IllegalArgumentException | DateTimeException e) {
	                        throw new InvalidArgumentException("Datos de cómic inválidos: " + e.getMessage(), "cargar fichero de productos");
	                    }
						break;
					}
					case "J" ->  {
						try {
							int numJugs = Integer.parseInt(partes[10]);
							String rangoEdad = partes[11];
							TipoJuego tipoJuego = TipoJuego.valueOf(partes[12]);
							
							this.anadirJuego(empleado, uds, nombre, desc, precio, null, numJugs, rangoEdad, tipoJuego, categorias.toArray(new Categoria[0]));
						} catch(IllegalArgumentException e) {
							throw new InvalidArgumentException("Datos de juego inválidos: " + e.getMessage(), "cargar fichero de productos");
						}
						break;
					}
					case "F" ->  {
						try {
							String marca = partes[13];
							String material = partes[14];
							String dimensiones = partes[15];
							
							this.anadirFigura(empleado, uds, nombre, desc, precio, null, dimensiones, marca, material, categorias.toArray(new Categoria[0]));
						} catch (IllegalArgumentException e) {
							throw new InvalidArgumentException("Datos de figura inválidos: " + e.getMessage(), "cargar fichero de productos");
						}
						break;
					}
					default ->  {
						throw new InvalidArgumentException("Tipo de producto no existente", "cargar fichero de productos");
					}
				}
		    }
		} catch (FileNotFoundException e) {
	        throw new InvalidArgumentException("Fichero no encontrado: " + fProductos, "cargar fichero de productos");
	    } catch (IOException e) {
	        throw new InvalidArgumentException("Error de lectura en el fichero: " + e.getMessage(), "cargar fichero de productos");
	    }
		return true;
	}
	
	/**
	 * Método para obtener una categoría con su nombre
	 * @param nombre Nombre de la categoría
	 * @return Categoría con el nombre que se introduce
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public Categoria getCategoria(String nombre) throws InvalidArgumentException {
		if(!categorias.containsKey(nombre)) throw new InvalidArgumentException("La categoria no se encuentra en la tienda");
		return categorias.get(nombre);
	}
	
	/**
	 * Devuelve una lista de productos que coinciden con el nombre insertado
	 * @param nombre Nombre de producto a coincidir
	 * @return La lista de productos en formato de array de productos
	 */
	public Producto[] getProductosCoincidentes(String nombre) {
		if(nombre.isBlank()) {
			Producto[] productos = new Producto[inventario.size()];

			int i = 0;
			for (Stock s : inventario.values()) {
			    productos[i++] = s.getProducto();
			}
			return productos;
		}
		
		List<Producto> lista = new ArrayList<>();
		
		for(Stock st: inventario.values()) {
			Producto p = st.getProducto();
			if (p.isEliminado()) continue;
			String nombreProd = p.getNombre();
			if (nombreProd.toLowerCase().contains(nombre.toLowerCase())) 
				lista.add(p);			
		}
		return lista.toArray(new Producto[0]);
	}
	
	/**
	 * Devuelve una lista de categorias que coinciden con el nombre insertado
	 * @param nombre Nombre de categorias a coincidir
	 * @return La lista de categorias en formato de array
	 */
	public Categoria[] getCategoriasCoincidentes(String nombre) {
		if(nombre.isBlank()) 
			return categorias.values().toArray(new Categoria[0]);
		
		List<Categoria> lista = new ArrayList<>();
		
		for(Categoria c: categorias.values()) {
			if (c.isEliminada()) continue;
			String nombreCat = c.getNombre();
			if (nombreCat.toLowerCase().contains(nombre.toLowerCase())) 
				lista.add(c);			
		}
		
		return lista.toArray(new Categoria[0]);
	}
	
	/**
	 * Crea y añade una categoría al almacén
	 * @param nombre Nombre de la categoría
	 * @return true si se pudo añadir, false si ya existe una categoria con ese nombre
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public boolean anadirCategoria(String nombre) throws InvalidArgumentException {
		if(nombre == null) throw new InvalidArgumentException("El nombre de la categoría no puede estar vacío");
		if(categorias.containsKey(nombre)) return false;
		
		this.categorias.put(nombre, new Categoria(nombre));
		return true;
	}
	
	/**
	 * Elimina una categoría del almacén
	 * @param categoria Categoria que se borra
	 * @return true en caso de que se elimine correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
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
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
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
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
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
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
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
	 * Crea y añade un descuento de dinero a una categoría
	 * @param desc Instancia de una clase que implemente la interfaz Descontable
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param precio Precio que se descuenta
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public boolean anadirDescuentoDinero(Descontable desc, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double precio) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(desc == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
		Descuento descuento = new DescuentoDinero(valorMin, inicio, fin, condicion, precio);
		if(!desc.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de porcentaje a una categoría
	 * @param desc Instancia de una clase que implemente la interfaz Descontable
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param porcentaje Porcentaje que se descuenta
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public boolean anadirDescuentoPorcentaje(Descontable desc, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, double porcentaje) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(desc == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
		Descuento descuento = new DescuentoPorcentaje(valorMin, inicio, fin, condicion, porcentaje);
		if(!desc.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Crea y añade un descuento de regalo a un producto
	 * @param desc Instancia de una clase que implemente la interfaz Descontable
	 * @param valorMin Valor mínimo para ser aplicado
	 * @param inicio Fecha de inicio del descuento
	 * @param fin Fecha de fin del descuento
	 * @param condicion Tipo de condición para el descuento
	 * @param regalo Regalo que se da
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 * @throws DoubleDiscountException Se lanza cuando se produce una colisión de descuentos
	 */
	public boolean anadirDescuentoRegalo(Descontable desc, double valorMin, LocalDateTime inicio, LocalDateTime fin, CondicionDescuento condicion, Producto regalo) 
			throws InvalidArgumentException, DoubleDiscountException {
		if(desc == null || inicio == null || fin == null || condicion == null) throw new InvalidArgumentException("No se pueden dejar atributos vacíos");
		
		Descuento descuento = new DescuentoRegalo(valorMin, inicio, fin, condicion, regalo);
		if(!desc.anadirDescuento(descuento))
			return false;
		descuentos.add(descuento);
		return true;
	}
	
	/**
	 * Elimina todos los descuentos que estén caducados
	 * @return true cuando todos los descuentos caducados hayan sido eliminados
	 */
	public boolean eliminarDescuentosCaducados() {
		descuentos.removeIf(d -> d.isCaducado());
		return true;
	}
	
	/**
	 * Añade un artículo de segunda mano ya creado al almacén
	 * @param articulo, Artículo que se añade
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public boolean anadirArticuloSegundaMano(ArticuloSegundaMano articulo) throws InvalidArgumentException {
		if(articulo == null) throw new InvalidArgumentException("El articulo no puede ser null");
		return articulos.add(articulo);
	}
	
	/**
	 * Elimina un artículo de segunda mano del almacén
	 * @param articulo, Artículo que se elimina
	 * @return true en caso de que se añada correctamente, false en caso contrario
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public boolean eliminarArticuloSegundaMano(ArticuloSegundaMano articulo) throws InvalidArgumentException {
		if(articulo == null) throw new InvalidArgumentException("El articulo no puede ser null");
		return articulos.remove(articulo);
	}
	
	/**
	 * Devuelve los articulos que hay disponibles para un cliente
	 * @param cliente Cliente que desea ver el los articulos disponibles para él
	 * @return Array de ArticuloSegundaMano que se pueden ver
	 */
	public ArticuloSegundaMano[] getArticulosParaCliente(ClienteRegistrado cliente) {
		return articulos.stream().filter(a -> (a.isDisponible() && !a.getPropietario().getNombre().equals(cliente.getNombre()))).toArray(ArticuloSegundaMano[]::new);
	}
	
	/**
	 * Devuelve una lista de productos que cumplen unas ciertas condiciones de categorías y precio
	 * @param cliente Cliente del cual se desea actualizar el vector de intereses
	 * @param categorias Categorías a las que deben pertenecer los productos
	 * @param precioMin Precio mínimo de los productos
	 * @param precioMax Precio máximo de los productos
	 * @param estrellasMin Mínimo número de estrellas que debe tener
	 * @return Producto[], un array de productos que cumplen las condiciones
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public Producto[] getProductosPorFiltros(ClienteRegistrado cliente,Categoria[] categorias, double precioMin, double precioMax, double estrellasMin)
			throws InvalidArgumentException {
		if(categorias == null || precioMin < 0 || precioMax < 0 || estrellasMin < 0 || estrellasMin > 5) throw new InvalidArgumentException("Parametros incorrectos para busqueda por filtros");
		
		cliente.actualizarVectorInteresesPorBusqueda(categorias);
		
		return getProductosPorFiltros(categorias, precioMin, precioMax, estrellasMin);
	}
	
	/**
	 * Devuelve una lista de productos que cumplen unas ciertas condiciones de categorías y precio
	 * @param categorias Categorías a las que deben pertenecer los productos
	 * @param precioMin Precio mínimo de los productos
	 * @param precioMax Precio máximo de los productos
	 * @param estrellasMin Mínimo número de estrellas que debe tener
	 * @return Producto[], un array de productos que cumplen las condiciones
	 * @throws InvalidArgumentException Se lanza cuando el argumento es inválido
	 */
	public Producto[] getProductosPorFiltros(Categoria[] categorias, double precioMin, double precioMax, double estrellasMin)
			throws InvalidArgumentException {
		if(categorias == null || precioMin < 0 || precioMax < precioMin || estrellasMin < 0 || estrellasMin > 5) throw new InvalidArgumentException("Parametros incorrectos para busqueda por filtros");
		
		Set<Producto> productos = new HashSet<>();
		for(Categoria c : categorias) {
			if(c == null) continue;
		
			for(Producto p : c.getProductos()) {
				if(p == null) break;
				if(p.getPrecio() >= precioMin && p.getPrecio() <= precioMax && p.getPuntuacionMedia() >= estrellasMin && p.isEliminado() == false) {
					productos.add(p);
				}
			}
		}
		return productos.toArray(new Producto[0]);
	}
	
	/**
	 * Devuelve una lista de productos ordenada según su compatibilidad con el cliente 
	 * @param cliente Cliente de la tienda al cual se le va a calcular la lista de recomendación
	 * @return dicha lista de productos
	 */
	public Producto[] getListaRecomendacion(ClienteRegistrado cliente) {
		Map<Producto, Double> recomendacion = new HashMap<>();
		Map<ClienteRegistrado, Double> similaridadEntreClientes = new HashMap<>(); // Usamos este map para evitar recalcular el grado de similaridad entre usuarios
		PriorityQueue<Producto> pq = new PriorityQueue<>(Comparator.comparingDouble(p->recomendacion.get(p)));
		Set<ParametroSistema> parametros = Sistema.getInstancia().getParametros();
		Producto p;
		double pondValoraciones = Sistema.getInstancia().getPonderacionValoracionesProducto();
		double pondProdRecomendado = Sistema.getInstancia().getPonderacionProductoRecomendado();
		double valoracionPrevista, compatibilidadPrevista, valorAsociado;
		int numValoraciones;
		int nElements = Sistema.getInstancia().getNumProductosRecomendados();
		boolean usarValoraciones = parametros.contains(ParametroSistema.VALORACIONES_PRODUCTO);
		
		
		for (Stock st: inventario.values()) {
			if (st.disponible() == false) continue;
			p = st.getProducto();
			if (p.isEliminado()) continue;

			// En vez de considerar únicamente la puntuación, hace una media ponderada de la puntuación teniendo en cuenta los usuarios más similares a este mismo
			if (usarValoraciones && cliente.getNormaVectorRecomendaciones() != 0) {
				valoracionPrevista = 0;
				numValoraciones = 0;
				
				for (Resena r: p.getResenas()) {
					if(similaridadEntreClientes.containsKey(r.getUsuario()) == false) similaridadEntreClientes.put(r.getUsuario(), cliente.getCompatibilidad(r.getUsuario().getVectorRecomendacion(), r.getUsuario().getNormaVectorRecomendaciones()));
					valoracionPrevista += r.getPuntuacion()*similaridadEntreClientes.get(r.getUsuario());
					numValoraciones++;
				}
				valoracionPrevista /= numValoraciones;	
				
			} else {
				valoracionPrevista = p.getPuntuacionMedia();
			}
			
			
			compatibilidadPrevista = cliente.getCompatibilidad(p.getVectorRecomendacion(), p.getNormaVector());
			valorAsociado = compatibilidadPrevista * pondProdRecomendado + valoracionPrevista* pondValoraciones;
			
			recomendacion.put(p, valorAsociado);
			
			pq.offer(p);
			if (pq.size() > nElements) pq.poll();
		}
		
		return pq.toArray(new Producto[0]);
		
	}
}