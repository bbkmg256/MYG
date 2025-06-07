/*
 * CLASE PARA ASIGNAR PARAMETROS DE FORMA DINAMICA AL METODO ACTUALIZAR DE
 * LA CAPA DE SERVICIO (Y POR CONSIGUIENTE, LA CAPA DE PERSISTENCIA) DEL SEGUIMIENTO.
 * 
 */

package utilidades;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;

// ENTIDADES
import edu.unam.modelo.Entrenamiento;
import edu.unam.modelo.Ejercicio;

public class ParametrosSeguimiento {
	public int cantSerieRealizado = 0;
	public int cantRepeticionesRealizado = 0;
	public Ejercicio ejercicioRealizado = null;
	public double pesoTrabajado = 0;
	public LocalDate fechaHoy = null;
	public Entrenamiento entrenamiento = null;
}
