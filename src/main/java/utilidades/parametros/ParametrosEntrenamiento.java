/*
 * 
 * Clase para usar como parámetros en el metodo de actualizacion del servicio
 * de la entidad Entrenamiento.
 * 
 */

package utilidades;

// LIBRERIAS
// VARIOS
import java.time.LocalDate;

// ENTIDADES
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Rutina;
import edu.unam.modelo.Tutor;

public class ParametrosEntrenamiento {
	public LocalDate fechaInicio = null;
	public LocalDate fechaFin = null;
	public Cliente cliente = null;
	public Tutor tutor = null;
	public Rutina rutina = null;
	public int puntaje = 0;
	public int volumenEntrenamiento = 0;
}
