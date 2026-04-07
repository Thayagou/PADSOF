/**
 * Este paquete contiene la interfaz que permite interactuar con la tienda
 */
package aplicacion;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import exceptions.*;
import sistema.*;
import usuario.*;
import venta.productos.*;

/**
 * Clase Main, ejecuta la aplicación de la tienda
 * @author Juan Ibañéz, Tiago Oselka, Claudia Saiz
 */
public class Main {
	protected static Tienda tienda = new Tienda();
	protected static String action = "";
	private static Scanner sc = new Scanner(System.in);
	private static String filename = "tienda.dat";
	
	/**
     * Constructor privado para el main singleton
     */
    private Main() {}
	
	/**
	 * Limpia la pantalla actual
	 */
	static void cleanScreen() {
		for(int i = 0; i < 50 ; i++) showMessage("\n");
	}
	
	/**
	 * Muestra un mensaje por la pantalla
	 * @param message Mensaje que se muestra por la pantalla
	 */
	protected static void showMessage(String message) {
		//cleanScreen();
		System.out.println("\n"+message);
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en formato de char
	 * @param message Mensaje que se muestra
	 * @return char Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static char getUserInputChar(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			char c = sc.next().charAt(0);
			return c;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una letra", "lectura");
		} finally {
			sc.nextLine();
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en formato de palabra
	 * @param message Mensaje que se muestra
	 * @return String Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static String getUserInputString(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			String s = sc.next().trim();
			return s;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una palabra", "lectura");
		} finally {
			sc.nextLine();
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de frase
	 * @param message Mensaje que se muestra
	 * @return String Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static String getUserInputLine(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			return sc.nextLine().trim();
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una frase", "lectura");
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de entero
	 * @param message Mensaje que se muestra
	 * @return int Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static int getUserInputInt(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			int n = sc.nextInt();
			return n;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser un int", "lectura");
		} finally {
			sc.nextLine();
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de double
	 * @param message Mensaje que se muestra
	 * @return double Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static double getUserInputDouble(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			double r = sc.nextDouble();
			return r;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser un double", "lectura");
		} finally {
			sc.nextLine();
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de fecha con hora
	 * @param message Mensaje que se muestra
	 * @return LocalDateTime Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static LocalDateTime getUserInputLocalDateTime(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			String input = sc.nextLine();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			LocalDateTime fecha = LocalDateTime.parse(input, formatter);
			return fecha;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una fecha", "lectura");
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de fecha
	 * @param message Mensaje que se muestra
	 * @return YearMonth Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static YearMonth getUserInputYearMonth(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			String input = sc.nextLine();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
			YearMonth fecha = YearMonth.parse(input, formatter);
			return fecha;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una fecha", "lectura");
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de fecha
	 * @param message Mensaje que se muestra
	 * @return Duration Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static Duration getUserInputDuration(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			String[] parts = sc.nextLine().split(":");
			if (parts.length < 3) throw new IllegalArgumentException();
			
			int days = Integer.parseInt(parts[0]);
			int hours = Integer.parseInt(parts[1]);
			int mins = Integer.parseInt(parts[2]);
			
			Duration duracion = Duration.ofDays(days).plusHours(hours).plusMinutes(mins);
			
			return duracion;			
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una duración DD:hh:mm", "lectura");
		}
	}
	
	/**
	 * Muestra un mensaje y recoje una respuesta en forma de lista de enteros
	 * @param message Mensaje que se muestra
	 * @return List<Integer> Respuesta del usuario
	 * @throws InvalidUserInputException
	 */
	protected static List<Integer> getUserInputIntList(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			String input = sc.nextLine();
			String[] split = input.trim().split("\\s+");
			List<Integer> list = new ArrayList<>();
			for (String s: split) {
				list.add(Integer.parseInt(s));
			}
			return list;
		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser una lista de números", "lectura");
		}
	}
	
	/**
	 * Muestra un mensaje y recoje la acción del usuario
	 * @param message Mensaje que se muestra
	 * @throws InvalidUserInputException
	 */
	protected static void getAction(String message) throws InvalidUserInputException {
		showMessage(message);
		try {
			action = sc.next().trim();

		} catch (IllegalArgumentException | InputMismatchException e) {
			throw new InvalidUserInputException("El valor introducido debe ser un comando", "lectura");
		} finally {
			sc.nextLine();
		}
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

	/**
	 * Programa principal que ejecuta la tienda
	 * @param args Argumentos de entrada
	 */
	public static void main(String[] args) {
		Usuario usuario;
		
		cargarTienda();
		GestorCaducidad.getInstancia().iniciar(1, TimeUnit.MINUTES);
				
		try {
			while (!action.equals("e")) {

				usuario = menuInicio();
				if (usuario == null) break;
				
				if (usuario instanceof Gestor) {
			        ActionGestor.menuGestor(tienda.getGestor());
			    } else if (usuario instanceof Empleado) {
			        ActionEmpleado.menuEmpleado(tienda.getEmpleado(usuario.getNombre()));
			    } else if (usuario instanceof ClienteRegistrado) {
			        ActionCliente.menuCliente(tienda.getCliente(usuario.getNombre()));
			    }
			}
			
		} catch (RuntimeException e) {
			showMessage("Error no manejado: " + e.getMessage());
			e.printStackTrace();
		}
		
		showMessage("Saliendo de la tienda...");
		
		GestorCaducidad.getInstancia().detener();
		guardarTienda();
		
		showMessage("Datos guardados con éxito!");
		
		return;
	}
	
	/**
	 * Menú que permite iniciar sesión en la tienda
	 * @return Usuario con el que se ha iniciado sesión
	 */
	static Usuario menuInicio() {
		while(!action.equals("e")) {
			try {
				getAction("r: registrarse | i: iniciar sesión | b: buscar | e: exit ");

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
			} catch (NotValidUserException | InvalidArgumentException | InvalidUserInputException e) {
				showMessage("\u001B[31m" + e.getMessage() + "\u001B[0m");
			}
		}
		return null;
	}
	
	/**
	 * Permite realizar una búsqueda por filtros sin opción de ver más información
	 * @return Array de productos que coinciden con la búsqueda
	 * @throws InvalidArgumentException
	 * @throws InvalidUserInputException
	 */
	static Producto[] actionBuscarPorFiltros() throws InvalidArgumentException, InvalidUserInputException {
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
}