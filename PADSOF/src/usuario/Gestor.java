package usuario;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import sistema.ParametroRecomendacion;
import sistema.Sistema;

public class Gestor extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set<Permiso> permisos  = new HashSet<>();

	public Gestor(String nombre, String contrasena) 
		throws IllegalArgumentException {
		super(nombre, contrasena);
		permisos.add(Permiso.SISTEMA);
		permisos.add(Permiso.PRODUCTOS);
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
		return permisos.contains(p);
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
