/**
 * Este paquete contiene la interfaz que permite interactuar con la tienda
 */
package aplicacion;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import estadistica.*;
import exceptions.*;
import sistema.*;
import usuario.*;
import venta.descuentos.*;
import venta.productos.*;

/**
 * Clase ActionGestor, contiene todos los métodos que permiten que un gestor maneje la tienda
 * @author Tiago Oselka, Claudia Saiz
 */
public class ActionGestor {
	
	/**
     * Constructor privado para evitar instanciación.
     */
    private ActionGestor() {
    }
	
	/**
	 * Muestra el menú principal del gestor desde el que puede realizar diferentes tareas
	 * @param gestor Gestor de la tienda
	 */
	static void menuGestor(Gestor gestor) {
		if(gestor == null) return;
		while(!Main.action.equals("e")) {
			
			try {
				Main.getAction("ad: añadir descuentos | csi: configurar sistema | ce: consultar estadísticas | gp: gestionar productos y categorias | ge: gestionar empleados | cs: cerrar sesión | e: exit");
				switch(Main.action) {
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
					ActionEmpleado.actionGestionarProductos(gestor);
					break;

				case "ge":
					actionGestionarEmpleados(gestor);
					break;
				
				case "cs":
					return;
			}

		} catch (CustomException e) {
				Main.showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			} catch (IllegalArgumentException e) {
				Main.showMessage("Error: el valor introducido no pudo ser parseado correctamente");
			}
		}
	}
	
	/**
	 * Realiza la acción de añadir un descuento a un producto o categoría dentro de la tienda
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException
	 * @throws DoubleDiscountException
	 * @throws InvalidUserInputException 
	 */
	static void actionAnadirDescuento(Gestor gestor) throws InvalidArgumentException, DoubleDiscountException, InvalidUserInputException {
		Producto[] productos;
		Main.getAction("Aplicar descuento sobre (c: categoría | p: producto): ");
		int index;
		Categoria c = null;
		Producto p = null;

		switch (Main.action) {
			case "c":
				Categoria[] categorias = Main.tienda.getAlmacen().getCategorias();
				for (int i = 1; i < categorias.length; i++) {
					Main.showMessage(i+") "+ categorias[i-1].getNombre());
				}
				
				index = Main.getUserInputInt("Número de producto para aplicar el descuento: ");
				if (index < 1 || index > categorias.length) throw new InvalidArgumentException("Índice de producto inválido", "añadir descuento");
				c = categorias[index-1];
				break;
				
			case "p":
				String prod = Main.getUserInputLine("Enter para elegir entre todo el inventario o : ");
				productos = Main.tienda.getAlmacen().getProductosCoincidentes(prod);
				
				for (int i = 1; i <= productos.length; i++) {
					Main.showMessage(i + ") " + productos[i-1].getNombre());
				}
				
				index = Main.getUserInputInt("Número de producto para aplicar el descuento: ");
				if (index < 1 || index > productos.length) throw new InvalidArgumentException("Índice de producto inválido", "añadir descuento");
				p = productos[index-1];
				break;
		}
		
		double valorMin;
		CondicionDescuento condicion;
		String cond = Main.getUserInputString("Tipo de condición (c: cantidad | v: volumen | sc: sin condiciones): ");
		switch (cond) {
			case "c":
				valorMin = Main.getUserInputDouble("Valor mínimo para la compensación (número no negativo): ");
				condicion = CondicionDescuento.CANTIDAD;
				break;
			case "v":
				valorMin = Main.getUserInputDouble("Valor mínimo para la compensación (número no negativo): ");
				condicion = CondicionDescuento.VOLUMEN;
				break;
			default:
				condicion = CondicionDescuento.SIN_CONDICION;
				valorMin = 0;
		}
		
		LocalDateTime fechaInicio = null, fechaFin = null;
		
		try {	
			fechaInicio = Main.getUserInputLocalDateTime("Fecha de inicio (formato DD/MM/YYYY hh:mm) : ");
			fechaFin = Main.getUserInputLocalDateTime("Fecha de inicio (formato DD/MM/YYYY hh:mm) : ");
		} catch(DateTimeParseException e) {
			throw new InvalidArgumentException("Formato de fecha incorrecto", "añadir descuento");
		}

		
		String tipo = Main.getUserInputString("Tipo de compensación (d: dinero | p: porcentaje | r: regalo): ");
		switch (tipo) {
		case "d": 
			double precio = Main.getUserInputDouble("Precio a descontar: ");
			if (p != null) Main.tienda.getAlmacen().anadirDescuentoDinero(p, valorMin, fechaInicio, fechaFin, condicion, precio);
			else if (c != null) Main.tienda.getAlmacen().anadirDescuentoDinero(c, valorMin, fechaInicio, fechaFin, condicion, precio);
			break;
			
		case "p":
			double porcentaje = Main.getUserInputDouble("Porcentaje a descontar (Rango 0-100): ");
			if (porcentaje < 0 || porcentaje > 100) throw new InvalidArgumentException("Rango de porcentaje incorrecto", "añadir descuento");
			
			if (p != null) Main.tienda.getAlmacen().anadirDescuentoDinero(p, valorMin, fechaInicio, fechaFin, condicion, porcentaje);
			else if (c != null) Main.tienda.getAlmacen().anadirDescuentoDinero(c, valorMin, fechaInicio, fechaFin, condicion, porcentaje);
			break;
			
		case "r":
			String nombre = Main.getUserInputLine("Enter para elegir entre todo el inventario o : ");
			productos = Main.tienda.getAlmacen().getProductosCoincidentes(nombre);
			
			for (int i = 1; i <= productos.length; i++) {
				Main.showMessage(i + ") " + productos[i-1].getNombre());
			}
			
			index = Main.getUserInputInt("Número de producto para elegir como regalo: ");
			if (index < 1 || index > productos.length) throw new InvalidArgumentException("Índice de producto inválido", "añadir descuento");
			Producto regalo = productos[index-1];
			
			if (p != null) Main.tienda.getAlmacen().anadirDescuentoRegalo(p, valorMin, fechaInicio, fechaFin, condicion, regalo);
			else if (c != null) Main.tienda.getAlmacen().anadirDescuentoRegalo(c, valorMin, fechaInicio, fechaFin, condicion, regalo);
			break;
		}
	}
	
	/**
	 * Permite al gestor configurar los diferentes parámetros de la tienda
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException 
	 * @throws InvalidUserInputException 
	 */
	private static void actionConfigurarSistema(Gestor gestor) throws InvalidArgumentException, InvalidUserInputException {
		Main.showMessage("=== Ponderaciones de los parámetros del sistema ===");
		Main.showMessage("  Categoría:              " + Sistema.getInstancia().getPonderacionCategoria());
		Main.showMessage("  Unidades compradas:     " + Sistema.getInstancia().getPonderacionUdsCompra());
		Main.showMessage("  Precio de compra:       " + Sistema.getInstancia().getPonderacionPrecioCompra());
		Main.showMessage("  Valoraciones producto:  " + Sistema.getInstancia().getPonderacionValoracionesProducto());
		Main.showMessage("  Producto recomendado:   " + Sistema.getInstancia().getPonderacionProductoRecomendado());
		Main.showMessage("  Búsqueda:               " + Sistema.getInstancia().getPonderacionBusqueda());
		Main.showMessage("  Duración carrito:       " + Sistema.getInstancia().getTiempoCaducaCarrito());
		Main.showMessage("  Duración ofertas:       " + Sistema.getInstancia().getTiempoCaducaOferta());
		Main.showMessage("===================================================");
		
		String parametroString = Main.getUserInputString("Introducir el parámetro a modificar (c: categoría | uc: unidades compradas | pp: precio pagado | vp: valoraciones producto | pr: producto recomendado | b: búsqueda | tcc: tiempo caducidad carrito | tco: tiempo caducidad ofertas): ");
		double valor = -1;
		Duration duracion = null;
		if (parametroString.equals("tcc") || parametroString.equals("tco")) {
	    	duracion = Main.getUserInputDuration("Introducir la nueva duración en formato DD:mm:ss (días:meses:segundos): ");
	    } else {
	    	valor = Main.getUserInputDouble("Introducir el nuevo valor: ");
	    	if(valor < 1) throw new InvalidUserInputException("El nuevo parámtro debe ser positivo", "configurar sistema");
	    }
	    
        switch (parametroString) {
            case "c":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.CATEGORIA, valor);
            	break;
            case "uc":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.UDS_COMPRADAS, valor);
            	break;
            case "pp":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.PRECIO_COMPRA, valor);
            	break;
            case "vp":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.VALORACIONES_PRODUCTO, valor);
            	break;
            case "pr": 
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.PRODUCTO_RECOMENDADO, valor);
            	break;
            case "b":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.BUSQUEDA, valor);
            	break;
            case "tcc":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.DURACION_CARRITO, duracion);
            	break;
            case "tco":
            	Main.tienda.gestionarParametroDeSistema(gestor, ParametroSistema.DURACION_CARRITO, duracion);
            	break;
        }
	    
	}
	
	/**
	 * Permite al gestor consultar las estadísticas de la tienda
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	private static void actionConsultarEstadisticas(Gestor gestor) throws InvalidArgumentException, InvalidUserInputException {
		String tipo = Main.getUserInputString("Introducir la estadística a consultar (Productos mas vendidos en un periodo: p | Usuarios más activos: u | Ventas en un periodo: v | Intercambio en un periodo: i): ");
		YearMonth inicio = null, fin = null;
		int i = 1;
		switch (tipo) {
		case "p": 
			inicio = Main.getUserInputYearMonth("Introducir mes de inicio (formato MM/yyyy): ");
			fin = Main.getUserInputYearMonth("Introducir mes de final (formato MM/yyyy): ");
			
			List<Map.Entry<Producto, StatsMensual>> listaProductos = Main.tienda.getHistorial().getProductosMayorRecaudacion(inicio, fin);
			StatsMensual total = Main.tienda.getHistorial().getVentasEntreMesesAcumulado(inicio, fin);
			Main.showMessage("Productos más vendidos entre " + inicio + " y " + fin);
			Main.showMessage("  Total recaudado: " + total.getRecaudacion() + "\n  Numero de unidades vendidas: " + total.getUnidades());
			i = 1;
			for (Map.Entry<Producto, StatsMensual> par: listaProductos) {
				StatsMensual stats = par.getValue();
				Double porcVentas = (stats.getRecaudacion()/total.getRecaudacion()) * 100;
				Main.showMessage("  " + i++ +") "+ par.getKey().getNombre() + ": " + stats.getRecaudacion() + "€ ("+porcVentas+"%) " + stats.getUnidades() +" uds vendidas");
			}
			break;
			
		case "u":
			List<StatsUsuario> listaUsuarios = Main.tienda.getHistorial().getUsuariosMasActivos();
			i = 1;
			for (StatsUsuario stats: listaUsuarios) {
				Main.showMessage("  " + i++ +") "+ stats.getCliente().getNombre() + " Total: " + stats.getGastoTotal() + "€ " + "Productos comprados: " + stats.getUdsCompradas() + "Artículos intercambiados: " + stats.getUdsIntercambiadas());
			}
			break;
			
		case "v":
			inicio = Main.getUserInputYearMonth("Introducir mes de inicio (formato MM/yyyy): ");
			fin = Main.getUserInputYearMonth("Introducir mes de final (formato MM/yyyy): ");
			
			List<StatsMensual> ventasPorMeses = Main.tienda.getHistorial().getVentasEntreMeses(inicio, fin);
		
			for (StatsMensual stats: ventasPorMeses) {
				Main.showMessage(stats.getMes() + ") " + "Total: " + stats.getRecaudacion() + "Uds vendidas: " + stats.getUnidades());
			}
			break;
			
		case "i":
			inicio = Main.getUserInputYearMonth("Introducir mes de inicio (formato MM/yyyy): ");
			fin = Main.getUserInputYearMonth("Introducir mes de final (formato MM/yyyy): ");
			
			List<StatsMensual> intercambiosPorMeses = Main.tienda.getHistorial().getVentasEntreMeses(inicio, fin);
			
			for (StatsMensual stats: intercambiosPorMeses) {
				Main.showMessage(stats.getMes() + ") " + "Total: " + stats.getRecaudacion() + "€ Uds vendidas: " + stats.getUnidades());
			}
			break;
		default:
			throw new InvalidArgumentException("Tipo inválido de estadística", "consultar estadísticas");
		}
	}
	
	/**
	 * Permite al gestor crear empleados y gestionar los existentes
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	private static void actionGestionarEmpleados(Gestor gestor) throws InvalidArgumentException, InvalidUserInputException {
		String tipo = Main.getUserInputString("Introducir acción a realizar (Dar de alta nuevo empleado: da | Gestionar permisos empleados existentes: ge): ");
		
		switch(tipo) {
		case "da":
			actionDarDeAltaEmpleado(gestor);
			break;
		case "ge":
			actionGestionarEmpleadosExistentes(gestor);
			break;
		}
	}

	/**
	 * Permite dar de alta a un nuevo empleado en la tienda
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	private static void actionDarDeAltaEmpleado(Gestor gestor) throws InvalidArgumentException, InvalidUserInputException {
		String nombre = Main.getUserInputString("Introducir nombre del nuevo usuario: ");
		String contrasena = Main.getUserInputString("Introducir contraseña del nuevo usuario: ");
		Set<Permiso> permisos = getPermisosSeleccionados();
		
		Main.tienda.darDeAltaEmpleado(nombre, contrasena, permisos.toArray(new Permiso[0]));
	}

	/**
	 * Gestiona los empleados existentes
	 * @param gestor Gestor de la tienda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException 
	 */
	 private static void actionGestionarEmpleadosExistentes(Gestor gestor) throws InvalidArgumentException, InvalidUserInputException {
		 Empleado[] empleados = Main.tienda.getEmpleados();
			if (empleados.length < 1) throw new InvalidArgumentException("No hay empleados actualmente en la tienda", "gestionar empleados");
			
			int i = 1;
			for (Empleado e: empleados) {
				Main.showMessage("  " + i + ") " + e.getNombre());
				i++;
			}
			
			int index = Main.getUserInputInt("Introducir número de empleado que se desea gestionar (1 - "+ empleados.length +"): ");
			if (index < 1 || index > empleados.length) throw new InvalidArgumentException("Índice de empleado inválido", "gestionar empleados");
			
			String accion = Main.getUserInputString("Introducir accción a realizar con " + empleados[index-1].getNombre() + " (Dar de baja: db | Gestionar permisos: gp): ");
			switch (accion) {
			case "db": 
				Main.tienda.darDeBajaEmpleado(empleados[index-1].getNombre());
				break;
				
			case "gp":
				actionGestionarPermisos(empleados[index-1]);
				break;
			}
	}

	 /**
	  * Permite gestionar los permisos de un empleado
	  * @param empleado Empleado cuyos permisos se van a gestionar
	  * @throws InvalidArgumentException
	  * @throws InvalidUserInputException
	  */
	private static void actionGestionarPermisos(Empleado empleado) throws InvalidArgumentException, InvalidUserInputException {
		Main.showMessage("Información del empleado:\n"+empleado);
		
		Set<Permiso> permisos;
		String c = Main.getUserInputString("Quiere conceder o quitar permisos? (conceder: c | quitar: q): ");
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
	 * @throws InvalidUserInputException 
	 */
	private static Set<Permiso> getPermisosSeleccionados() throws InvalidArgumentException, InvalidUserInputException {
		Permiso[] permisosDisp = Permiso.values();
		int i = 1;
		for (Permiso p: permisosDisp) {
			Main.showMessage("  " + i++ + ") " + p.name());
		}
		List<Integer> listaPermisos = Main.getUserInputIntList("Introducir la lista de números de permisos (1 - " + permisosDisp.length + "): ");

		Set<Permiso> permisosConcedidos = new HashSet<>();
		for (Integer j: listaPermisos) {
			if (j < 1 || j > permisosDisp.length) throw new InvalidArgumentException("Índice de permiso inválido", "gestionar empleados");
			permisosConcedidos.add(permisosDisp[j-1]);
		}
		
		return permisosConcedidos;
	}
}
