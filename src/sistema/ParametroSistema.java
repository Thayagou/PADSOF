package sistema;

import java.io.Serializable;

/**
 * Enum que representa posibles parámetros del sistema
 */
public enum ParametroSistema implements Serializable {
	/** Categoría de un producto */
	CATEGORIA,

	/** Unidades compradas de un producto */
	UDS_COMPRADAS,

	/** Precio de compra de un producto */
	PRECIO_COMPRA,

	/** Valoraciones realizadas sobre un producto */
	VALORACIONES_PRODUCTO,

	/** Producto recomendado al usuario */
	PRODUCTO_RECOMENDADO,

	/** Búsqueda realizada por el usuario */
	BUSQUEDA,

	/** Duración del carrito de compra */
	DURACION_CARRITO,

	/** Duración de una oferta */
	DURACION_OFERTA;
}