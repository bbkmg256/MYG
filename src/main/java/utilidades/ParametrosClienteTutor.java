/*
 * CLASE PARA ASIGNAR PARAMETROS DE FORMA DINAMICA AL METODO ACTUALIZAR DE
 * LA CAPA DE SERVICIO (Y POR CONSIGUIENTE, LA CAPA DE PERSISTENCIA) DEL CLIENTE Y DEL TUTOR,
 * BASICAMENTE POR QUE SON SIMILARES RESPECTO A LOS PARAMETROS.
 */

package utilidades;

import java.time.LocalDate;

public class ParametrosClienteTutor {
	public int dni = 0; // MODIFICAR CON CUIDADO
	public String nombre = null;
	public String apellido = null;
	public char sexo = 'x'; // ASUMIENDO 'X' COMO VALOR NULO PARA ESTE ATRIBUTO
	public String ciudad = null;
	public String provincia = null;
	public int codigoPostal = 0; // ASUMIENDO QUE NO EXISTA UN CODIGO POSTAL 0, XD
	public LocalDate fechaNacimiento = null;
	public LocalDate fechaIngreso = null; // SOLO PARA CLIENTE
}
