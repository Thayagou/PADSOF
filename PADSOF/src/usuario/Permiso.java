package usuario;

import java.io.Serializable;

/**
 * Enum que representa los diferentes permisos que puede tener un usuario
 */
public enum Permiso implements Serializable {
	/** Permiso relacionado con la gestión de productos */
	PRODUCTOS,

	/** Permiso relacionado con la gestión de pedidos */
	PEDIDOS,

	/** Permiso relacionado con la gestión de intercambios */
	INTERCAMBIOS;
}
