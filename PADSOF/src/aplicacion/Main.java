package aplicacion;

import java.util.*;
import sistema.*;
import usuario.*;
import venta.productos.*;

public class Main {
	private static Tienda tienda;
	private static char action;
	private static Scanner sc = new Scanner(System.in);
	
	static void cleanScreen() {
		for(int i = 0; i < 50 ; i++) System.out.println("\n");
	}
	
	static void showMessage(String message) {
		//cleanScreen();
		System.out.println("\n"+message);
	}
	
	static String getUserInputString(String message) {
		System.out.println("\n"+message);
		return sc.next();
	}
	
	static int getUserInputInt(String message) {
		System.out.println("\n"+message);
		return sc.nextInt(0);
	}
	
	static void getAction(String message) {
		System.out.println("\n"+message);
		action = sc.next().charAt(0);
	}

	public static void main(String[] args) {
		Tienda t = tienda;
		Usuario u;
		
		while(true) {
			loopSinRegistrar:
			while(true) {
				getAction("r: registrarse | i: iniciar sesión ");
				
				switch(action) {
				case 'r':
					String nombreR = getUserInputString("Introducir nombre: ");
					String contrasenaR = getUserInputString("Introducir contraseña: ");
					String confirmacionR = getUserInputString("Repetir contraseña: ");
					
					u = t.registrarse(nombreR, contrasenaR, confirmacionR);
					
					if(u == null) {
						showMessage("No se pudo crear el usuario");
						break;
					} else {
						break loopSinRegistrar;
					}
					
				case 'i':
					String nombreI = getUserInputString("Introducir nombre: ");
					String contrasenaI = getUserInputString("Introducir contraseña: ");
					
					u = t.iniciarSesion(nombreI, contrasenaI);
					
					if(u == null) {
						showMessage("No se pudo iniciar sesión");
						break;
					} else break loopSinRegistrar;
				}
			}
			
			
			
			loopC:
			while(true) {
				if(u == null) break loopC;
				ClienteRegistrado c = t.getCliente(u.getNombre());
				if(c == null) break loopC;
				
				getAction("b: buscar | r: recomendaciones | s: buscar segunda mano | w: cartera | c: carrito | a: cuenta | n: notificaciones");
				switch(action) {
				case 'b':
					List<Categoria> categorias = new ArrayList<Categoria>();
					for(Categoria cat : t.getAlmacen().getCategorias()) {
						getAction("Incluir categoria" + cat.getNombre() + "? s/n");
						if(action == 's') {
							categorias.add(cat);
						}
					}
					double precioMin = getUserInputInt("Precio minimo: ");
					double precioMax = getUserInputInt("Precio maximo: ");
					double estrellasMin = getUserInputInt("Estrellas minimas: ");
					
					try{
						t.getAlmacen().getProductosPorFiltros(categorias.toArray(new Categoria[0]), precioMin, precioMax, estrellasMin);
					}catch(IllegalArgumentException e){
						
					}
					
				case 'r':
				case 's':
				case 'w':
				case 'c':
				case 'a':
				case 'n':
				}
			}
			
			
			
			loopE:
			while(true) {
				if(u == null) break loopE;
				Empleado e = t.getEmpleado(u.getNombre());
				if(e == null) break loopE;
			}
			
			
			
			loopG:
			while(true) {
				if(u == null) break loopG;
				Gestor g = t.getGestor();
				if(g == null) break loopG;
				if(u != g) break loopG;
			}
		}
		
	}

}
