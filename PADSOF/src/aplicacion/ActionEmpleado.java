package aplicacion;

import java.time.*;
import java.util.*;
import exceptions.*;
import usuario.*;
import venta.pedidos.Pedido;
import venta.productos.*;
import venta.productos.caracteristicas.*;
import wallapop.*;

public class ActionEmpleado {
	
	static void menuEmpleado(Empleado empleado) {
		if(empleado == null) return;
		while(!Main.action.equals("e")) {
			
			Main.getAction("v: valorar articulo | c: confirmar intercambio | pd: gestionar pedidos | pr: gestionar productos y categorias | cs: cerrar sesión |e: exit");
			try {
				switch(Main.action) {
				case "v":
					actionValorarArticulo(empleado);
					break;
					
				case "c":
					actionConfirmarIntercambio(empleado);
					break;
					
				case "pd":
					actionGestionarPedidos(empleado);
					break;
					
				case "pr":
					actionGestionarProductos(empleado);
					break;
				
				case "cs":
					return;
				}
			} catch (InvalidArgumentException | InvalidPermitException | ArticuloSinValoracionException | DoubleDiscountException e ) {
				Main.showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (IllegalArgumentException e) {
				Main.showMessage("Error: el valor introducido no pudo ser parseado correctamente");
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/// MÉTODOS ACCIONES DE EMPLEADO
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Realiza la acción de valorar un artículo de segunda mano
	 * @param empleado Empleado que desea valorar un artículo
	 * @throws InvalidArgumentException
	 * @throws ArticuloSinValoracionException Se lanza en caso de que el artículo que se intenta valorar no tenga una valoración solicitada
	 * @throws InvalidPermit 
	 */
	static void actionValorarArticulo(Empleado empleado) throws InvalidArgumentException, InvalidPermitException, ArticuloSinValoracionException {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "valorar artículo", "Intercambios");
		int i = 1;
		Valoracion[] valoraciones = Main.tienda.getHistorial().getValoracionesPendientes();
		if (valoraciones.length < 1) throw new InvalidArgumentException("No existen valoraciones pendientes en este momento", "valorar articulo");
		for(Valoracion v : valoraciones) {
			Main.showMessage(i + ") " + v);
			i++;
		}
		int num = Main.getUserInputInt("Escriba el número del artículo que desea valorar: ");
		double precio = Main.getUserInputDouble("Precio estimado: ");
		EstadoFisicoArticulo est = EstadoFisicoArticulo.valueOf(Main.getUserInputString("Estado del artículo: "));
		
		Main.tienda.getHistorial().valorarArticulo(empleado, valoraciones[num-1].getArticulo(), precio, est);
	}
	
	/**
	 * Realiza la acción de confirmar un intercambio
	 * @param empleado Empleado que desea confirmar un intercambio
	 * @throws InvalidArgumentException
	 * @throws InvalidPermit 
	 */
	static void actionConfirmarIntercambio(Empleado empleado) throws InvalidArgumentException, InvalidPermitException {
		if (empleado.tienePermiso(Permiso.INTERCAMBIOS) == false) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "validar intercambio", "Intercambios");
		int i = 1;
		Intercambio[] intercambios = Main.tienda.getHistorial().getIntercambiosPendientes();
		if(intercambios.length < 1) throw new InvalidArgumentException("No existen intercambios pendientes en este momento", "confirmar intercambio");
		for (Intercambio t : intercambios) {
			Main.showMessage(i + ") " + t);
			i++;
		}
		int num = Main.getUserInputInt("Escriba el número del intercambio que desea confirmar: ");
		Main.tienda.getHistorial().validarIntercambio(empleado, intercambios[num-1]);
	}
	
	/**
	 * Realiza la acción de gestionar pedidos pendientes
	 * @param empleado Empleado que desea gestionar pedidos
	 * @throws InvalidArgumentException
	 * @throws InvalidPermit 
	 */
	static void actionGestionarPedidos(Empleado empleado) throws InvalidArgumentException, InvalidPermitException {
		if(!empleado.tienePermiso(Permiso.PEDIDOS)) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "gestionar pedidos", "Pedidos");
		int i = 1;
		Pedido[] pedidos= Main.tienda.getHistorial().getPedidosPendientes();
		if(pedidos.length < 1) throw new InvalidArgumentException("No existen pedidos pendientes en este momento", "gestionar pedidos");
		for(Pedido p : pedidos) {
			Main.showMessage(i + ") " + p);
			i++;
		}
		int num = Main.getUserInputInt("Escriba el número del pedido que desea avanzar: ");
		Main.tienda.getHistorial().avanzarEstadoPedido(empleado, pedidos[num-1]);
	}
	
	/**
	 * Realiza la acción de gestionar productos 
	 * @param empleado Empleado que desea gestionar productos
	 * @throws InvalidArgumentException
	 * @throws DoubleDiscountException
	 * @throws InvalidPermit 
	 */
	static void actionGestionarProductos(Usuario usuario) throws InvalidArgumentException, DoubleDiscountException, InvalidPermitException {
		if(!usuario.tienePermiso(Permiso.PRODUCTOS)) throw new InvalidPermitException("No tienes el permiso para hacer esta acción", "gestionar productos", "Productos");
		
		Main.getAction("a: añadir producto | c: cargar fichero de productos | mp: modificar producto | bp: borrar producto | cc: crear categorias | mc: modificar categorias | p: crear packs | e: exit");
		
		switch(Main.action) {
		case "a":
			actionAnadirProducto();
			break;
			
		case "c":
			actionCargarFicheroProductos();
			break;
			
		case "mp":
			actionModificarProducto();
			break;
			
		case "bp":
			actionBorrarProducto();
			break;
			
		case "cc":
			actionCrearCategoria();
			break;
			
		case "mc":
			actionModificarCategoria();
			break;
			
		case "p":
			actionCrearPack();
			break;
			
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/// MÉTODOS GESTIONAR PRODUCTO
	/////////////////////////////////////////////////////////////////////////////////////////////////////
		
	 /**
	  * Añade un nuevo producto por la interfaz
	  * @throws InvalidArgumentException
	  * @throws DoubleDiscountException
	  */
	static void actionAnadirProducto() throws InvalidArgumentException, DoubleDiscountException {
		char tipo = Main.getUserInputChar("Tipo de producto (c: comic | j: juego | f: figura): ");
		String nombre = Main.getUserInputLine("Nombre: ");
		String desc = Main.getUserInputLine("Descripción: ");
		double precio = Main.getUserInputDouble("Precio: ");
		int uds = Main.getUserInputInt("Unidades: ");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : Main.tienda.getAlmacen().getCategorias()) {
			Main.getAction("Incluir categoria " + cat.getNombre() + "? s/n");
			if(Main.action == "s") {
				categorias.add(cat);
			}
		}
		switch(tipo) {
		case 'c':
			int numPags = Main.getUserInputInt("Número de páginas: ");
			String autor = Main.getUserInputLine("Autor: ");
			String editorial = Main.getUserInputLine("Editorial: ");
			String fecha[] = Main.getUserInputString("Fecha(YYYY/MM/DD): ").split("/");
			LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
			
			Main.tienda.getAlmacen().anadirComic(uds, nombre, desc, precio, null, fechaPublicacion, autor, numPags, editorial, categorias.toArray(new Categoria[0]));
			break;
		case 'j':
			int numJugs = Main.getUserInputInt("Número de jugadores: ");
			String rangoEdad = Main.getUserInputString("Rango de edad: ");
			TipoJuego tipoJuego = TipoJuego.valueOf(Main.getUserInputString("Tipo de juego: "));
			
			Main.tienda.getAlmacen().anadirJuego(uds, nombre, desc, precio, null, numJugs, rangoEdad, tipoJuego, categorias.toArray(new Categoria[0]));
			break;
		case 'f':
			String marca = Main.getUserInputString("Marca: ");
			String material = Main.getUserInputString("Material: ");
			String dimensiones = Main.getUserInputString("Dimensiones: ");
			
			Main.tienda.getAlmacen().anadirFigura(uds, nombre, desc, precio, null, dimensiones, marca, material, categorias.toArray(new Categoria[0]));
			break;
		}
	}
	
	/**
	 * Añade varios nuevos productos por un fichero
	 * @throws DoubleDiscountException
	 * @throws InvalidArgumentException
	 */
	static void actionCargarFicheroProductos() throws DoubleDiscountException, InvalidArgumentException {
		String fichero = Main.getUserInputString("Nombre del archivo: ");
		Main.tienda.getAlmacen().anadirProductosDeFichero(fichero);
	}
	
	static void actionModificarProducto() throws InvalidArgumentException, DoubleDiscountException {
		String nombre = Main.getUserInputLine("Introduzca el nombre del producto que quiere modificar: " );
		Producto[] productos = Main.tienda.getAlmacen().getProductosCoincidentes(nombre);
		if(productos.length < 1) throw new InvalidArgumentException("No se han encontrado productos con ese nombre", "modificar producto");
		
		int i = 1;
		for(Producto p : productos) {
			Main.showMessage(i++ + ") " + p);
		}
		int num = Main.getUserInputInt("Introduzca el número del producto que desea modificar: ");
		Main.showMessage("Introduzca los nuevo datos del producto");
		
		String nombrePr = Main.getUserInputLine("Nombre: ");
		String desc = Main.getUserInputLine("Descripción: ");
		double precio = Main.getUserInputDouble("Precio: ");
		int uds = Main.getUserInputInt("Unidades: ");
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : Main.tienda.getAlmacen().getCategorias()) {
			char inc = Main.getUserInputChar("Incluir categoria " + cat.getNombre() + "? s/n");
			if(inc == 's') {
				categorias.add(cat);
			}
		}
		
		CaracteristicasProducto carargs;
		Producto producto = productos[num-1];
		if (producto instanceof Comic) {
			int numPags = Main.getUserInputInt("Número de páginas: ");
			String autor = Main.getUserInputLine("Autor: ");
			String editorial = Main.getUserInputLine("Editorial: ");
			String fecha[] = Main.getUserInputString("Fecha(YYYY/MM/DD): ").split("/");
			LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
			
			carargs = new CaracteristicasComic(fechaPublicacion, autor, numPags, editorial);
		} else if (producto instanceof Juego) {
			int numJugs = Main.getUserInputInt("Número de jugadores: ");
			String rangoEdad = Main.getUserInputString("Rango de edad: ");
			TipoJuego tipoJuego = TipoJuego.valueOf(Main.getUserInputString("Tipo de juego: "));
			
			carargs = new CaracteristicasJuego(numJugs, rangoEdad, tipoJuego);
		} else if (producto instanceof Figura) {
			String marca = Main.getUserInputString("Marca: ");
			String material = Main.getUserInputString("Material: ");
			String dimensiones = Main.getUserInputString("Dimensiones: ");
			
			carargs = new CaracteristicasFigura(marca, material, dimensiones);
		} else if (producto instanceof Pack) {
			List<Stock> prods = new ArrayList<>();
			for(Stock s : Main.tienda.getAlmacen().getInventario()) {
				char inc = Main.getUserInputChar("Incluir producto " + s.getProducto().getNombre() + "? s/n");
				if(inc == 's') {
					prods.add(s);
				}
			}
			
			carargs = new CaracteristicasPack(prods.toArray(new Stock[0]));
		} else throw new IllegalArgumentException("Error con el tipo de producto");
		
		Main.tienda.getAlmacen().modificarProducto(producto, uds, nombrePr, desc, precio, null, carargs, categorias.toArray(new Categoria[0]));
	}
	
	static void actionBorrarProducto() throws InvalidArgumentException {
		String nombre = Main.getUserInputString("Introduzca el nombre del producto que quiere borrar: ");
		Producto[] productos = Main.tienda.getAlmacen().getProductosCoincidentes(nombre);
		if(productos.length < 1) throw new InvalidArgumentException("No se han encontrado productos con ese nombre", "borrar producto");
		
		int i = 1;
		for(Producto p : productos) {
			Main.showMessage(i++ + ") " + p);
		}
		int num = Main.getUserInputInt("Introduzca el número del producto que desea borrar: ");
		Main.tienda.getAlmacen().eliminarProducto(productos[num-1]);
	}
	
	static void actionCrearCategoria() throws InvalidArgumentException {
		String nuevo = Main.getUserInputString("Introduzca el nombre de la nueva categoría: ");
		Main.tienda.getAlmacen().anadirCategoria(nuevo);
	}
	
	static void actionModificarCategoria() throws InvalidArgumentException {
		String nombre = Main.getUserInputLine("Introduzca el nombre de la categoría que desea modificar: ");
		Categoria[] categorias = Main.tienda.getAlmacen().getCategoriasCoincidentes(nombre);
		
		if(categorias.length < 1) throw new InvalidArgumentException("No se han encontrado productos con ese nombre", "borrar producto");
		
		int i = 1;
		for(Categoria c : categorias) {
			Main.showMessage(i++ + ") " + c + "\n");
		}
		int num = Main.getUserInputInt("Introduzca el número del producto que desea modificar: ");
		String nuevo = Main.getUserInputString("Introduzca el nuevo nombre de la categoría: ");
		Main.tienda.getAlmacen().modificarCategoria(categorias[num-1], nuevo);
	}
	
	static void actionCrearPack() throws InvalidArgumentException, DoubleDiscountException {
		List<Stock> productos = new ArrayList<>();
		for(Stock s : Main.tienda.getAlmacen().getInventario()) {
			char inc = Main.getUserInputChar("Incluir producto " + s.getProducto().getNombre() + "? s/n");
			if(inc == 's') {
				productos.add(s);
			}
		}
		
		String nombre = Main.getUserInputLine("Nombre: ");
		String desc = Main.getUserInputLine("Descripción: ");
		double precio = Main.getUserInputDouble("Precio: ");
		int uds = Main.getUserInputInt("Unidades: ");
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : Main.tienda.getAlmacen().getCategorias()) {
			char inc = Main.getUserInputChar("Incluir categoria " + cat.getNombre() + "? s/n");
			if(inc == 's') {
				categorias.add(cat);
			}
		}
		
		Main.tienda.getAlmacen().anadirPack(uds, nombre, desc, precio, null, productos.toArray(new Stock[0]), categorias.toArray(new Categoria[0]));
	}
}
