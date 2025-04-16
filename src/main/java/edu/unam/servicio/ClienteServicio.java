/*
	Servicio de cliente (Esto valida la movida de las
	peticiones a BD desde la vista).
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.ClienteDAO;

// VARIOS
import java.time.LocalDate;
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Cliente;

/**
*
* @Autor: BBKMG
*/
public class ClienteServicio {
	// JPAController
	private ClienteDAO cjpac;
	
	// Constructor
	public ClienteServicio() {
		cjpac = new ClienteDAO();
	}
	
	// Registra un cliente en el sistema
	public void cargarCliente(Cliente cliente) {
		// Si el metodo es diferente de null, es por que se encontró un cliente con el mismo DNI
		if (cjpac.obtenerEntidadCliente(cliente.getDni()) != null) {
			System.out.printf("[ ERROR ] > El cliente %d ya se encuentra en el sistema!%n", cliente.getDni());
			return; // SI EL OBJETO YA ESTÁ EN LA BD, MUESTRA EL MENSAJE Y SALE DEL METODO.
		}

		// MODIFICA TODOS LOS VALORES TEXTUALES QUE TENGA EL OBJETO, A MINUSCULA.
		cliente.setNombre(cliente.getNombre().toLowerCase());
		cliente.setApellido(cliente.getApellido().toLowerCase());
		cliente.setCiudad(cliente.getCiudad().toLowerCase());
		cliente.setProvicia(cliente.getProvincia().toLowerCase());
		cliente.setSexo(Character.toLowerCase(cliente.getSexo()));
		
		// CARGA EL OBJETO A LA BD
		cjpac.crearEntidadCliente(cliente);			
	}
	
	// Busca un cliente en el sistema
	public Cliente obtenerCliente(int dni) {
		Cliente cli = cjpac.obtenerEntidadCliente(dni);
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
		}
		
		return cli;
	}
	
	// Obtener todos los clientes del sistema
	public List<Cliente> obtenerTodosLosClientes(){
		// El nombre de la entidad en JPQL empieza con mayuscula
		// (ej: Cliente, Tutor) como las mismas clases
		List<Cliente> cli = cjpac.obtenerEntidadesCliente();
		if (cli == null) {
			System.out.printf("[ ERROR ] > No hay clientes en el sistema!%n");
		}
		
		return cli;
	}
	
	// Actualiza la información de un cliente
	public void actualizarInfCliente(int dni, String nombre, String apellido,
									LocalDate fechaNac, char sexo, String ciudad,
									String provincia, int codPost, LocalDate fechaIng) {
		
		Cliente cli = cjpac.obtenerEntidadCliente(dni);
		
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
			return;
		}
		
		// ALGUNOS PARAMETROS SE MODIFICAN A MINUSCULA
		cjpac.actualizarEntidadCliente(cli, nombre.toLowerCase(), apellido.toLowerCase(),
								fechaNac, Character.toLowerCase(sexo), ciudad.toLowerCase(),
								provincia.toLowerCase(), codPost, fechaIng);
	}
	
//	// ACTUALIZAR RELACION CON ENTRENAMIENTO
//	public void actualizarRelacionClienteEntrenamiento(Cliente entidadCliente, Entrenamiento entidadEntrenamiento) {
//		if (entidadCliente == null && entidadEntrenamiento == null) {
//			System.out.printf("[ ERROR ] > Alguno de los objetos ingresados es nulo o no es válido!%n");
//			return;
//		}
//		
//		cjpac.actualizarRelacion(entidadCliente, entidadEntrenamiento);
//	}
	
	// Da de baja a un cliente del sistema
	public void eliminarCliente(int dni) {
		Cliente cli = cjpac.obtenerEntidadCliente(dni);
		
		// En caso de que no se encuentre cliente en el sistema,
		// simplemente no hace nada y finaliza el metodo destruyendo el EMF
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
			return;
		}
		
		cjpac.eliminarEntidadCliente(cli);
	}
	
	// CERRAR CONEXION
	public void finalizarConexion() {
		if (cjpac != null && cjpac.hayConexion()) {
			cjpac.cerrarEMF();			
		}
	}
}
