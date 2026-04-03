package aplicacion;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import estadistica.*;
import exceptions.*;
import sistema.*;
import usuario.*;
import venta.pedidos.Pedido;
import venta.productos.*;
import venta.productos.caracteristicas.*;
import venta.descuentos.*;
import wallapop.*;

public class Main {
	private static Tienda tienda = new Tienda();
	private static String action = "";
	private static Scanner sc = new Scanner(System.in);
	private static String filename = "tienda.dat";
	
	static void cleanScreen() {
		for(int i = 0; i < 50 ; i++) showMessage("\n");
	}
	
	static void showMessage(String message) {
		//cleanScreen();
		System.out.println("\n"+message);
	}
	
	static char getUserInputChar(String message) {
		showMessage(message);
		char c = sc.next().charAt(0);
		sc.nextLine();
		return c;
	}
	
	static String getUserInputString(String message) {
		showMessage(message);
		String s = sc.next().trim();
		sc.nextLine();
		return s;
	}
	
	static String getUserInputLine(String message) {
		showMessage(message);
		return sc.nextLine().trim();
	}
	
	static int getUserInputInt(String message) {
		showMessage(message);
		int n = sc.nextInt();
		sc.nextLine();
		return n;
	}
	
	static double getUserInputDouble(String message) {
		showMessage(message);
		double r = sc.nextDouble();
		sc.nextLine();
		return r;
	}
	
	static LocalDateTime getUserInputLocalDateTime(String message) {
		showMessage(message);
		String input = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime fecha = LocalDateTime.parse(input, formatter);
		
		return fecha;
	}
	
	static YearMonth getUserInputYearMonth(String message) {
		showMessage(message);
		String input = sc.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
		YearMonth fecha = YearMonth.parse(input, formatter);
		
		return fecha;		
	}
	
	static List<Integer> getUserInputIntList(String message) {
		showMessage(message);
		String input = sc.nextLine();
		String[] split = input.trim().split("\\s+");
		List<Integer> list = new ArrayList<>();
		for (String s: split) {
			list.add(Integer.parseInt(s));
		}
		
		return list;
	}
	
	
	static void getAction(String message) {
		showMessage(message);
		action = sc.next().trim();
	}
	
	/**
	 * Carga los datos de la tienda desde un fichero
	 */
	static void cargarTienda() {
		try {
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
	        tienda = (Tienda) ois.readObject();
	        ois.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Guarda los datos de la tienda en un fichero
	 */
	static void guardarTienda() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeObject(tienda);
			oos.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Usuario usuario;
		
		cargarTienda();
		
		try {
			while (!action.equals("e")) {

				usuario = menuInicio();
				if (usuario == null) break;
				
				if (usuario instanceof Gestor) {
			        menuGestor(tienda.getGestor());
			    } else if (usuario instanceof Empleado) {
			        menuEmpleado(tienda.getEmpleado(usuario.getNombre()));
			    } else if (usuario instanceof ClienteRegistrado) {
			        menuCliente(tienda.getCliente(usuario.getNombre()));
			    }
			}
			
		} catch (RuntimeException e) {
			showMessage("Error no manejado: " + e.getMessage());
			e.printStackTrace();
		}
		
		guardarTienda();
		
		return;
	}
	
	static Usuario menuInicio() {
		while(!action.equals("e")) {
			
			getAction("r: registrarse | i: iniciar sesión | b: buscar | e: exit ");
			try {

				switch(action) {
				case "r":
					String nombreR = getUserInputString("Introducir nombre: ");
					String contrasenaR = getUserInputString("Introducir contraseña: ");					
					String confirmacionR = getUserInputString("Repetir contraseña: ");
					
					Usuario usuario = tienda.registrarse(nombreR, contrasenaR, confirmacionR);
					
					return usuario;
					
				case "i":
					String nombreI = getUserInputString("Introducir nombre: ");
					String contrasenaI = getUserInputString("Introducir contraseña: ");
					
					usuario = tienda.iniciarSesion(nombreI, contrasenaI);
					
					return usuario;
					
				case "b":
					actionBuscarPorFiltros();
				}
			} catch (NotValidUserException | InvalidArgumentException e) {
				showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			}
		}
		return null;
	}
	
	static void menuCliente(ClienteRegistrado cliente) {
		if(cliente == null) return;
		while(!action.equals("e")) {
			
			getAction("b: buscar | r: recomendaciones | s: buscar segunda mano | w: cartera | c: carrito | a: cuenta | n: notificaciones | p: pedidos | e: exit");
			try {
				switch(action) {
				case "b":
					actionBuscarPorFiltros(cliente);
					break;
					
				case "r":
					actionRecomendaciones(cliente);
					break;
					
				case "s":
					actionVerSegundaMano(cliente);
					break;
					
				case "w":
					actionVerCartera(cliente);
					break;
					
				case "c":
					actionVerCarrito(cliente);
					break;
					
				case "a":
					actionVerCuenta(cliente);
					break;
					
				case "n":
					actionVerNotificaciones(cliente);
					break;
					
				}
			} catch (InvalidArgumentException e) {
				showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (IllegalArgumentException e) {
				showMessage("Error: el valor introducido no pudo ser parseado correctamente");
			}
		}
	}

	static void menuEmpleado(Empleado empleado) {
		if(empleado == null) return;
		while(!action.equals("e")) {
			
			getAction("v: valorar articulo | c: confirmar intercambio | pd: gestionar pedidos | pr: gestionar productos y categorias | cs: cerrar sesión |e: exit");
			try {
				switch(action) {
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
				showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (IllegalArgumentException e) {
				showMessage("Error: el valor introducido no pudo ser parseado correctamente");
			}
		}
	}
		
	static void menuGestor(Gestor gestor) {
		if(gestor == null) return;
		while(!action.equals("e")) {
			
			getAction("ad: añadir descuentos | csi: configurar sistema | ce: consultar estadísticas | gp: gestionar productos y categorias | ge: gestionar empleados | cs: cerrar sesión | e: exit");
			try {
				switch(action) {
				case "ad":
					actionAnadirDescuento(gestor);
					break;

				case "csi":
					actionConfigurarSistema(gestor);
					break;

				case "ce":
					actionConsultarEstadisticas(gestor);
					break;
					
				case "gp":
					actionGestionarProductos(gestor);
					break;

				case "ge":
					actionGestionarEmpleados(gestor);
					break;
				
				case "cs":
					return;
			}

		} catch (InvalidArgumentException | DoubleDiscountException | InvalidPermitException e) {
				showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (IllegalArgumentException e) {
				showMessage("Error: el valor introducido no pudo ser parseado correctamente");
			}
		}
	}


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
		Valoracion[] valoraciones = tienda.getHistorial().getValoracionesPendientes();
		if (valoraciones.length < 1) throw new InvalidArgumentException("No existen valoraciones pendientes en este momento", "valorar articulo");
		for(Valoracion v : valoraciones) {
			showMessage(i + ") " + v);
			i++;
		}
		int num = getUserInputInt("Escriba el número del artículo que desea valorar: ");
		double precio = getUserInputDouble("Precio estimado: ");
		EstadoFisicoArticulo est = EstadoFisicoArticulo.valueOf(getUserInputString("Estado del artículo: "));
		
		tienda.getHistorial().valorarArticulo(empleado, valoraciones[num-1].getArticulo(), precio, est);
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
		Intercambio[] intercambios = tienda.getHistorial().getIntercambiosPendientes();
		if(intercambios.length < 1) throw new InvalidArgumentException("No existen intercambios pendientes en este momento", "confirmar intercambio");
		for (Intercambio t : intercambios) {
			showMessage(i + ") " + t);
			i++;
		}
		int num = getUserInputInt("Escriba el número del intercambio que desea confirmar: ");
		tienda.getHistorial().validarIntercambio(empleado, intercambios[num-1]);
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
		Pedido[] pedidos= tienda.getHistorial().getPedidosPendientes();
		if(pedidos.length < 1) throw new InvalidArgumentException("No existen pedidos pendientes en este momento", "gestionar pedidos");
		for(Pedido p : pedidos) {
			showMessage(i + ") " + p);
			i++;
		}
		int num = getUserInputInt("Escriba el número del pedido que desea avanzar: ");
		tienda.getHistorial().avanzarEstadoPedido(empleado, pedidos[num-1]);
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
		
		getAction("a: añadir producto | c: cargar fichero de productos | mp: modificar producto | bp: borrar producto | cc: crear categorias | mc: modificar categorias | p: crear packs | e: exit");
		
		switch(action) {
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
	

	/**
	 * Realiza la acción de añadir un descuento a un producto o categoría dentro de la tienda
	 * 
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException Excepción lanzada en caso de que alguno de los argumentos introducidos no sean válidos
	 * @throws DoubleDiscountException
	 */
	static void actionAnadirDescuento(Gestor gestor) throws InvalidArgumentException, DoubleDiscountException {
		Producto[] productos;
		String aplicadoSobre = getUserInputString("Aplicar descuento sobre (c: categoría | p: producto): ");
		int index;
		Categoria c = null;
		Producto p = null;

		switch (aplicadoSobre) {
			case "c":
				Categoria[] categorias = tienda.getAlmacen().getCategorias();
				for (int i = 1; i < categorias.length; i++) {
					showMessage(i+") "+ categorias[i-1].getNombre());
				}
				
				index = getUserInputInt("Número de producto para aplicar el descuento: ");
				if (index <= 0 || index > categorias.length) throw new InvalidArgumentException("Índice de producto inválido", "añadir descuento");
				c = categorias[index-1];
				
			case "p":
				String prod = getUserInputLine("Enter para elegir entre todo el inventario o : ");
				productos = tienda.getAlmacen().getProductosCoincidentes(prod);
				
				for (int i = 1; i <= productos.length; i++) {
					showMessage(i + ") " + productos[i-1].getNombre());
				}
				
				index = getUserInputInt("Número de producto para aplicar el descuento: ");
				if (index <= 0 || index > productos.length) throw new InvalidArgumentException("Índice de producto inválido", "añadir descuento");
				// Mirar esto arriba!!!!!!!
				p = productos[index-1];
		}
		
		double valorMin;
		CondicionDescuento condicion;
		String cond = getUserInputString("Tipo de condición (c: cantidad | v: volumen | sc: sin condiciones): ");
		switch (cond) {
			case "c":
				valorMin = getUserInputDouble("Valor mínimo para la compensación (número no negativo): ");
				condicion = CondicionDescuento.CANTIDAD;
				break;
			case "v":
				valorMin = getUserInputDouble("Valor mínimo para la compensación (número no negativo): ");
				condicion = CondicionDescuento.VOLUMEN;
				break;
			default:
				condicion = CondicionDescuento.SIN_CONDICION;
				valorMin = 0;
		}
		
		LocalDateTime fechaInicio = null, fechaFin = null;
		
		try {	
			fechaInicio = getUserInputLocalDateTime("Fecha de inicio (formato DD/MM/YYYY hh:mm) : ");
			fechaFin = getUserInputLocalDateTime("Fecha de inicio (formato DD/MM/YYYY hh:mm) : ");
		} catch(DateTimeParseException e) {
			throw new InvalidArgumentException("Formato de fecha incorrecto", "añadir descuento");
		}

		
		String tipo = getUserInputString("Tipo de compensación (d: dinero | p: porcentaje | r: regalo): ");
		switch (tipo) {
		case "d": 
			double precio = getUserInputDouble("Precio a descontar: ");
			if (p != null) tienda.getAlmacen().anadirDescuentoDinero(p, valorMin, fechaInicio, fechaFin, condicion, precio);
			else if (c != null) tienda.getAlmacen().anadirDescuentoDinero(c, valorMin, fechaInicio, fechaFin, condicion, precio);
			
		case "p":
			double porcentaje = getUserInputDouble("Porcentaje a descontar (Rango 0-100): ");
			if (porcentaje < 0 || porcentaje > 100) throw new InvalidArgumentException("Rango de porcentaje incorrecto", "añadir descuento");
			
			if (p != null) tienda.getAlmacen().anadirDescuentoDinero(p, valorMin, fechaInicio, fechaFin, condicion, porcentaje);
			else if (c != null) tienda.getAlmacen().anadirDescuentoDinero(c, valorMin, fechaInicio, fechaFin, condicion, porcentaje);
			
		case "r":
			String nombre = getUserInputLine("Enter para elegir entre todo el inventario o : ");
			productos = tienda.getAlmacen().getProductosCoincidentes(nombre);
			
			for (int i = 1; i <= productos.length; i++) {
				showMessage(i + ") " + productos[i-1].getNombre());
			}
			
			index = getUserInputInt("Número de producto para elegir como regalo: ");
			if (index <= 0 || index > productos.length) throw new InvalidArgumentException("Índice de producto inválido", "añadir descuento");
			// Mirar esto arriba!!!!!!!
			Producto regalo = productos[index-1];
			
			if (p != null) tienda.getAlmacen().anadirDescuentoRegalo(p, valorMin, fechaInicio, fechaFin, condicion, regalo);
			else if (c != null) tienda.getAlmacen().anadirDescuentoRegalo(c, valorMin, fechaInicio, fechaFin, condicion, regalo);
			
		}
	}
	
	/**
	 * 
	 * @param gestor
	 * @throws InvalidArgumentException 
	 */
	private static void actionConfigurarSistema(Gestor gestor) throws InvalidArgumentException {
		showMessage("=== Ponderaciones de los parámetros del sistema ===");
		showMessage("  Categoría:              " + Sistema.getInstancia().getPonderacionCategoria());
		showMessage("  Unidades compradas:     " + Sistema.getInstancia().getPonderacionUdsCompra());
		showMessage("  Precio de compra:       " + Sistema.getInstancia().getPonderacionPrecioCompra());
		showMessage("  Valoraciones producto:  " + Sistema.getInstancia().getPonderacionValoracionesProducto());
		showMessage("  Producto recomendado:   " + Sistema.getInstancia().getPonderacionProductoRecomendado());
		showMessage("  Búsqueda:               " + Sistema.getInstancia().getPonderacionBusqueda());
		showMessage("===================================================");
		
		String parametroString = getUserInputString(
			"Introducir el parámetro a modificar (c: categoría | uc: unidades compradas | pp: precio pagado | vp: valoraciones producto | pr: producto recomendado | b: búsqueda): ");
	    double valor = getUserInputDouble("Introducir el nuevo valor: ");

	    
        switch (parametroString) {
            case "c":
            	tienda.gestionarParametroDeSistema(gestor, ParametroRecomendacion.CATEGORIA, valor);
            	break;
            case "uc":
            	tienda.gestionarParametroDeSistema(gestor, ParametroRecomendacion.UDS_COMPRADAS, valor);
            	break;
            case "pp":
            	tienda.gestionarParametroDeSistema(gestor, ParametroRecomendacion.PRECIO_COMPRA, valor);
            	break;
            case "vp":
            	tienda.gestionarParametroDeSistema(gestor, ParametroRecomendacion.VALORACIONES_PRODUCTO, valor);
            	break;
            case "pr": 
            	tienda.gestionarParametroDeSistema(gestor, ParametroRecomendacion.PRODUCTO_RECOMENDADO, valor);
            	break;
            case "b":
            	tienda.gestionarParametroDeSistema(gestor, ParametroRecomendacion.BUSQUEDA, valor);
            	break;
        }
	    
	}
	
	private static void actionConsultarEstadisticas(Gestor gestor) throws InvalidArgumentException {
		String tipo = getUserInputString(
				"Introducir la estadística a consultar (Productos mas vendidos en un periodo: p | Usuarios más activos: u | Ventas en un periodo: v | Intercambio en un periodo: i): ");
		YearMonth inicio = null, fin = null;
		int i = 1;
		switch (tipo) {
		case "p": 
			inicio = getUserInputYearMonth("Introducir mes de inicio (formato MM/yyyy): ");
			fin = getUserInputYearMonth("Introducir mes de final (formato MM/yyyy): ");
			
			List<Map.Entry<Producto, StatsMensual>> listaProductos = tienda.getHistorial().getProductosMayorRecaudacion(inicio, fin);
			StatsMensual total = tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			showMessage("Productos más vendidos entre " + inicio + " y " + fin);
			showMessage("  Total recaudado: " + total.getRecaudacion() + "\n  Numero de unidades vendidas: " + total.getUnidades());
			i = 1;
			for (Map.Entry<Producto, StatsMensual> par: listaProductos) {
				StatsMensual stats = par.getValue();
				Double porcVentas = (stats.getRecaudacion()/total.getRecaudacion()) * 100;
				showMessage("  " + i+") "+ par.getKey().getNombre() + ": " + stats.getRecaudacion() + "€ ("+porcVentas+"%) " + stats.getUnidades() +" uds vendidas");
				i++;
			}
			break;
		case "u":
			List<StatsUsuario> listaUsuarios = tienda.getHistorial().getUsuariosMasActivos();
			i = 1;
			for (StatsUsuario stats: listaUsuarios) {
				showMessage("  " +i+") "+ stats.getCliente().getNombre() + " Total: " + stats.getGastoTotal() + "€ " + "Productos comprados: " + stats.getUdsCompradas() + "Artículos intercambiados: " + stats.getUdsIntercambiadas());
				i++;
			}
			break;
		case "v":
			inicio = getUserInputYearMonth("Introducir mes de inicio (formato MM/yyyy): ");
			fin = getUserInputYearMonth("Introducir mes de final (formato MM/yyyy): ");
			
			List<StatsMensual> ventasPorMeses = tienda.getHistorial().getVentasEntreMeses(inicio, fin);
		
			for (StatsMensual stats: ventasPorMeses) {
				showMessage(stats.getMes() + ") " + "Total: " + stats.getRecaudacion() + "Uds vendidas: " + stats.getUnidades());
			}
			break;
			
		case "i":
			inicio = getUserInputYearMonth("Introducir mes de inicio (formato MM/yyyy): ");
			fin = getUserInputYearMonth("Introducir mes de final (formato MM/yyyy): ");
			
			List<StatsMensual> intercambiosPorMeses = tienda.getHistorial().getVentasEntreMeses(inicio, fin);
			
			for (StatsMensual stats: intercambiosPorMeses) {
				showMessage(stats.getMes() + ") " + "Total: " + stats.getRecaudacion() + "€ Uds vendidas: " + stats.getUnidades());
			}
			break;
		default:
			throw new InvalidArgumentException("Tipo inválido de estadística", "consultar estadísticas");
		}
	}
	
	private static void actionGestionarEmpleados(Gestor gestor) throws InvalidArgumentException {
		String tipo = getUserInputString("Introducir acción a realizar (Dar de alta nuevo empleado: da | Gestionar permisos empleados existentes: ge): ");
		
		switch(tipo) {
		case "da":
			actionDarDeAltaEmpleado(gestor);
			break;
		case "ge":
			actionGestionarEmpleadosExistentes(gestor);
			break;
		}
	}

	private static void actionDarDeAltaEmpleado(Gestor gestor) throws InvalidArgumentException {
		String nombre = getUserInputString("Introducir nombre del nuevo usuario: ");
		String contrasena = getUserInputString("Introducir contraseña del nuevo usuario: ");
		Set<Permiso> permisos = getPermisosSeleccionados();
		
		tienda.darDeAltaEmpleado(nombre, contrasena, permisos.toArray(new Permiso[0]));
	}

	/**
	 * Gestiona los empleados existentes
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan parámetros inválidos
	 */
	 private static void actionGestionarEmpleadosExistentes(Gestor gestor) throws InvalidArgumentException {
		 Empleado[] empleados = tienda.getEmpleados();
			if (empleados.length == 0) {
				showMessage("No hay empleados actualmente en la tienda");
				return;
			}
			
			int i = 1;
			for (Empleado e: empleados) {
				showMessage("  " + i + ") " + e.getNombre());
				i++;
			}
			
			int index = getUserInputInt("Introducir índice de empleado que se desea gestionar (1 - "+ empleados.length +"): ");
			if (index < 1 || index > empleados.length) throw new InvalidArgumentException("Índice de empleado inválido", "gestionar empleados");
			
			String accion = getUserInputString("Introducir accción a realizar con " + empleados[index-1].getNombre() + " (Dar de baja: db | Gestionar permisos: gp): ");
			switch (accion) {
			case "db": 
				tienda.darDeBajaEmpleado(empleados[index-1].getNombre());
				break;
				
			case "gp":
				actionGestionarPermisos(empleados[index-1]);
				break;
			}
	}

	private static void actionGestionarPermisos(Empleado empleado) throws InvalidArgumentException {
		showMessage("Información del empleado:\n"+empleado);
		
		Set<Permiso> permisos;
		String c = getUserInputString("Quiere conceder o quitar permisos? (conceder: c | quitar: q): ");
		switch (c) {
		case "c":
			permisos = getPermisosSeleccionados();
			
			for (Permiso p: permisos) {
				empleado.addPermiso(p);
			}
			break;
		case "q": 
			permisos = getPermisosSeleccionados();
			
			for (Permiso p: permisos) {
				empleado.quitarPermiso(p);
			}
			break;
		}
	}
	 
	/**
	 * Obtiene un set de permisos a partir del usuario
	 * @return set de permisos obtenidos
	 * @throws InvalidArgumentException Se lanza en caso de que se introduzcan datos inválidos
	 */
	private static Set<Permiso> getPermisosSeleccionados() throws InvalidArgumentException {
		Permiso[] permisosDisp = Permiso.values();
		int i = 1;
		for (Permiso p: permisosDisp) {
			showMessage("  " + i + ") " + p.name());
			i++;
		}
		List<Integer> listaPermisos = getUserInputIntList("Introducir la lista de números de permisos (1 - " + permisosDisp.length + "): ");

		Set<Permiso> permisosConcedidos = new HashSet<>();
		for (Integer j: listaPermisos) {
			if (j < 1 || j > permisosDisp.length) throw new InvalidArgumentException("Índice de permiso inválido", "gestionar empleados");
			permisosConcedidos.add(permisosDisp[j-1]);
		}
		
		return permisosConcedidos;
	}
		
	 /**
	  * Añade un nuevo producto por la interfaz
	  * @throws InvalidArgumentException
	  * @throws DoubleDiscountException
	  */
	static void actionAnadirProducto() throws InvalidArgumentException, DoubleDiscountException {
		char tipo = getUserInputChar("Tipo de producto (c: comic | j: juego | f: figura): ");
		String nombre = getUserInputLine("Nombre: ");
		String desc = getUserInputLine("Descripción: ");
		double precio = getUserInputDouble("Precio: ");
		int uds = getUserInputInt("Unidades: ");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : tienda.getAlmacen().getCategorias()) {
			getAction("Incluir categoria " + cat.getNombre() + "? s/n");
			if(action == "s") {
				categorias.add(cat);
			}
		}
		switch(tipo) {
		case 'c':
			int numPags = getUserInputInt("Número de páginas: ");
			String autor = getUserInputLine("Autor: ");
			String editorial = getUserInputLine("Editorial: ");
			String fecha[] = getUserInputString("Fecha(YYYY/MM/DD): ").split("/");
			LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
			
			tienda.getAlmacen().anadirComic(uds, nombre, desc, precio, null, fechaPublicacion, autor, numPags, editorial, categorias.toArray(new Categoria[0]));
			break;
		case 'j':
			int numJugs = getUserInputInt("Número de jugadores: ");
			String rangoEdad = getUserInputString("Rango de edad: ");
			TipoJuego tipoJuego = TipoJuego.valueOf(getUserInputString("Tipo de juego: "));
			
			tienda.getAlmacen().anadirJuego(uds, nombre, desc, precio, null, numJugs, rangoEdad, tipoJuego, categorias.toArray(new Categoria[0]));
			break;
		case 'f':
			String marca = getUserInputString("Marca: ");
			String material = getUserInputString("Material: ");
			String dimensiones = getUserInputString("Dimensiones: ");
			
			tienda.getAlmacen().anadirFigura(uds, nombre, desc, precio, null, dimensiones, marca, material, categorias.toArray(new Categoria[0]));
			break;
		}
	}
	
	/**
	 * Añade varios nuevos productos por un fichero
	 * @throws DoubleDiscountException
	 * @throws InvalidArgumentException
	 */
	static void actionCargarFicheroProductos() throws DoubleDiscountException, InvalidArgumentException {
		String fichero = getUserInputString("Nombre del archivo: ");
		tienda.getAlmacen().anadirProductosDeFichero(fichero);
	}
	
	static void actionModificarProducto() throws InvalidArgumentException, DoubleDiscountException {
		String nombre = getUserInputLine("Introduzca el nombre del producto que quiere modificar: " );
		Producto[] productos = tienda.getAlmacen().getProductosCoincidentes(nombre);
		if(productos.length < 1) throw new InvalidArgumentException("No se han encontrado productos con ese nombre", "modificar producto");
		
		int i = 1;
		for(Producto p : productos) {
			showMessage(i++ + ") " + p);
		}
		int num = getUserInputInt("Introduzca el número del producto que desea modificar: ");
		showMessage("Introduzca los nuevo datos del producto");
		
		String nombrePr = getUserInputLine("Nombre: ");
		String desc = getUserInputLine("Descripción: ");
		double precio = getUserInputDouble("Precio: ");
		int uds = getUserInputInt("Unidades: ");
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : tienda.getAlmacen().getCategorias()) {
			char inc = getUserInputChar("Incluir categoria " + cat.getNombre() + "? s/n");
			if(inc == 's') {
				categorias.add(cat);
			}
		}
		
		CaracteristicasProducto carargs;
		Producto producto = productos[num-1];
		if (producto instanceof Comic) {
			int numPags = getUserInputInt("Número de páginas: ");
			String autor = getUserInputLine("Autor: ");
			String editorial = getUserInputLine("Editorial: ");
			String fecha[] = getUserInputString("Fecha(YYYY/MM/DD): ").split("/");
			LocalDate fechaPublicacion = LocalDate.of(Integer.parseInt(fecha[0]), Month.of(Integer.parseInt(fecha[1])), Integer.parseInt(fecha[2]));
			
			carargs = new CaracteristicasComic(fechaPublicacion, autor, numPags, editorial);
		} else if (producto instanceof Juego) {
			int numJugs = getUserInputInt("Número de jugadores: ");
			String rangoEdad = getUserInputString("Rango de edad: ");
			TipoJuego tipoJuego = TipoJuego.valueOf(getUserInputString("Tipo de juego: "));
			
			carargs = new CaracteristicasJuego(numJugs, rangoEdad, tipoJuego);
		} else if (producto instanceof Figura) {
			String marca = getUserInputString("Marca: ");
			String material = getUserInputString("Material: ");
			String dimensiones = getUserInputString("Dimensiones: ");
			
			carargs = new CaracteristicasFigura(marca, material, dimensiones);
		} else if (producto instanceof Pack) {
			List<Stock> prods = new ArrayList<>();
			for(Stock s : tienda.getAlmacen().getInventario()) {
				char inc = getUserInputChar("Incluir producto " + s.getProducto().getNombre() + "? s/n");
				if(inc == 's') {
					prods.add(s);
				}
			}
			
			carargs = new CaracteristicasPack(prods.toArray(new Stock[0]));
		} else throw new IllegalArgumentException("Error con el tipo de producto");
		
		tienda.getAlmacen().modificarProducto(producto, uds, nombrePr, desc, precio, null, carargs, categorias.toArray(new Categoria[0]));
	}
	
	static void actionBorrarProducto() throws InvalidArgumentException {
		String nombre = getUserInputString("Introduzca el nombre del producto que quiere borrar: ");
		Producto[] productos = tienda.getAlmacen().getProductosCoincidentes(nombre);
		if(productos.length < 1) throw new InvalidArgumentException("No se han encontrado productos con ese nombre", "borrar producto");
		
		int i = 1;
		for(Producto p : productos) {
			showMessage(i++ + ") " + p);
		}
		int num = getUserInputInt("Introduzca el número del producto que desea borrar: ");
		tienda.getAlmacen().eliminarProducto(productos[num-1]);
	}
	
	static void actionCrearCategoria() throws InvalidArgumentException {
		String nuevo = getUserInputString("Introduzca el nombre de la nueva categoría: ");
		tienda.getAlmacen().anadirCategoria(nuevo);
	}
	
	static void actionModificarCategoria() throws InvalidArgumentException {
		String nombre = getUserInputLine("Introduzca el nombre de la categoría que desea modificar: ");
		Categoria[] categorias = tienda.getAlmacen().getCategoriasCoincidentes(nombre);
		
		if(categorias.length < 1) throw new InvalidArgumentException("No se han encontrado productos con ese nombre", "borrar producto");
		
		int i = 1;
		for(Categoria c : categorias) {
			showMessage(i++ + ") " + c + "\n");
		}
		int num = getUserInputInt("Introduzca el número del producto que desea modificar: ");
		String nuevo = getUserInputString("Introduzca el nuevo nombre de la categoría: ");
		tienda.getAlmacen().modificarCategoria(categorias[num-1], nuevo);
	}
	
	static void actionCrearPack() throws InvalidArgumentException, DoubleDiscountException {
		List<Stock> productos = new ArrayList<>();
		for(Stock s : tienda.getAlmacen().getInventario()) {
			char inc = getUserInputChar("Incluir producto " + s.getProducto().getNombre() + "? s/n");
			if(inc == 's') {
				productos.add(s);
			}
		}
		
		String nombre = getUserInputLine("Nombre: ");
		String desc = getUserInputLine("Descripción: ");
		double precio = getUserInputDouble("Precio: ");
		int uds = getUserInputInt("Unidades: ");
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : tienda.getAlmacen().getCategorias()) {
			char inc = getUserInputChar("Incluir categoria " + cat.getNombre() + "? s/n");
			if(inc == 's') {
				categorias.add(cat);
			}
		}
		
		tienda.getAlmacen().anadirPack(uds, nombre, desc, precio, null, productos.toArray(new Stock[0]), categorias.toArray(new Categoria[0]));
	}
	
	static Producto[] actionBuscarPorFiltros() throws InvalidArgumentException {
		List<Categoria> categorias = new ArrayList<Categoria>();
		for(Categoria cat : tienda.getAlmacen().getCategorias()) {
			getAction("Incluir categoria " + cat.getNombre() + "? s/n");
			if(action.equals("s")) {
				categorias.add(cat);
			}
		}
		double precioMin = getUserInputDouble("Precio minimo: ");
		double precioMax = getUserInputDouble("Precio maximo: ");
		double estrellasMin = getUserInputDouble("Estrellas minimas: ");
		
		Producto[] productos = tienda.getAlmacen().getProductosPorFiltros(categorias.toArray(new Categoria[0]), precioMin, precioMax, estrellasMin);
		int i = 1;
		showMessage(productos.length + "");
		for(Producto p : productos) {
			showMessage(i++ + ") " + p);
		}
		return productos;
	}
	
	static void actionBuscarPorFiltros(ClienteRegistrado cliente) throws InvalidArgumentException {
		Producto[] productos = actionBuscarPorFiltros();
		char dec = getUserInputChar("¿Desea añadir al carrito alguno de estos productos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = getUserInputInt("Introduzca el número del producto que desea añadir: ");
		
		cliente.getCarrito().anadirProducto(productos[num-1]);
		
	}
	
	static void actionRecomendaciones(ClienteRegistrado cliente) throws InvalidArgumentException {
		Producto[] productos = tienda.getAlmacen().getListaRecomendacion(cliente);
		int i = 1;
		for(Producto p : productos) {
			showMessage(i++ + ") " + p);
		}
		
		char dec = getUserInputChar("¿Desea añadir al carrito alguno de estos productos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = getUserInputInt("Introduzca el número del producto que desea añadir: ");
		cliente.getCarrito().anadirProducto(productos[num-1]);
	}
	
	static void actionVerSegundaMano(ClienteRegistrado cliente) throws InvalidArgumentException {
		ArticuloSegundaMano[] articulos = tienda.getAlmacen().getArticulosParaCliente(cliente);
		if(articulos.length < 1) throw new InvalidArgumentException("No hay artículos disponibles para intercambiar", "ver artículos de segunda mano");
		int i = 1;
		for (ArticuloSegundaMano a : articulos) {
			showMessage(i++ + ") " + a);
		}
		char dec = getUserInputChar("¿Desea ver la cartera del propietario de alguno de estos artículos? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		
		int num = getUserInputInt("Introduzca el número del artículo cuyo propietario quiere ver: ");
		showMessage("Cartera de " + articulos[num-1].getPropietario().getNombre() + ": ");
		i = 1;
		for(ArticuloSegundaMano a : articulos[num-1].getDueno().getArticulosDisponibles()) {
			showMessage(i++ + ") " + a);
		}
		dec = getUserInputChar("¿Desea realizar una oferta de intercambio a este usuario? (Pulsar 's' si lo desea)");
		if(dec != 's') return;
		List pide = getUserInputIntList("Indique los números de los artículos que desea pedir: (Números separados por espacios)");
		
		showMessage("Su cartera: ");
		for(ArticuloSegundaMano a : cliente.getCartera().getArticulosDisponibles()) {
			showMessage(i++ + ") " + a);
		}
		List ofrece = getUserInputIntList("Indique los números de los artículos que desea ofrecer: (Números separados por espacios)");
		
		ArticuloSegundaMano = 
	}
	
	static void actionVerCartera(ClienteRegistrado cliente) {
		
	}
	
	static void actionVerCarrito(ClienteRegistrado cliente) throws InvalidArgumentException {
		Stock[] productos= cliente.getCarrito().getContenido();
		int i = 1;
		for(Stock p : productos) {
			showMessage(i++ + ") " + p);
		}
		
		getAction("¿Qué desea hacer? (p: pagar | c: cancelar carrito | q: quitar producto | v: volver)");
		switch(action) {
		case "p":
			String numTarjeta = getUserInputString("Introduzca su número de tarjeta(16 dígitos): ");
			boolean st = tienda.pagarCarritoDe(cliente, numTarjeta);
			if (st) {
				showMessage("Su compra se ha efectuado correctamente");
			} else {
				showMessage("Ha ocurrido un error al intentar realizar la compra");
			}
			break;
			
		case "c":
			tienda.cancelarCarritoDe(cliente);
			break;
			
		case "q":
			int num = getUserInputInt("Introduzca el número deproducto que desea quitar: ");
			tienda.quitarDeCarritoDe(cliente, productos[num-1].getProducto());
			break;
			
		}
	}
	
	static void actionVerCuenta(ClienteRegistrado cliente) {
		
	}
	
	static void actionVerNotificaciones(ClienteRegistrado cliente) {
		
	}
}