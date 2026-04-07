package sistema;

import java.io.Serializable;

/**
 * Enum que representa posibles parámetros del sistema
 */
public enum ParametroSistema implements Serializable {
	CATEGORIA, UDS_COMPRADAS, PRECIO_COMPRA, VALORACIONES_PRODUCTO, PRODUCTO_RECOMENDADO, BUSQUEDA, DURACION_CARRITO, DURACION_OFERTA;
}