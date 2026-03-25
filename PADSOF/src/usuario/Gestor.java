package usuario;

import java.time.Duration;

import sistema.ParametroRecomendacion;
import sistema.Sistema;

public class Gestor extends Usuario {

	public Gestor(String nombre, String contrasena) 
		throws IllegalArgumentException {
		super(nombre, contrasena);
	}

	public boolean anadirPermisos(Empleado e, Permiso...permisos) {
		boolean status = true;
		for(Permiso p : permisos) {
			status = e.addPermiso(p);
		}
		return status;
	}
	
	public boolean quitarPermisos(Empleado e, Permiso...permisos) {
		boolean status = true;
		for(Permiso p : permisos) {
			status = e.quitarPermiso(p);
		}
		return status;
	}
	
	public boolean tienePermiso(Permiso p) {
		if(p == Permiso.PRODUCTOS)
			return true;
		else
			return false;
	}
	
	public boolean modificarTiempoCaducidadCarrito(Duration tiempo) {
		return Sistema.getInstancia().setTiempoCaducaCarrito(tiempo);
	}
	
	public boolean modificarTiempoCaducidadOferta(Duration tiempo) {
		return Sistema.getInstancia().setTiempoCaducaOferta(tiempo);
	}
	
	public boolean modificarPrecioValoracion(double precio) {
		return Sistema.getInstancia().setPrecioValoracion(precio);
	}
	
	public boolean establecerParametros(boolean activo, ParametroRecomendacion... parametros) {
		boolean status = true;
		for(ParametroRecomendacion p : parametros) {
			status = Sistema.getInstancia().gestionarParametro(p, activo);
		}
		return status;
	}
}
