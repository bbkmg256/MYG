/*
	Servicio de cliente (Esto valida la movida de las
	peticiones a BD desde la vista).
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.ClienteDAO;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
// VARIOS
//import java.time.LocalDate;
import java.util.List;

import utilidades.bd.EMFSingleton;
import utilidades.parametros.ParametrosClienteTutor;
// ENTIDAD
import edu.unam.modelo.Cliente;
import edu.unam.modelo.Entrenamiento;


/*
 * 
 * NOTA:
 * 
 * TENGO QUE COMPROBAR QUE ACTUALIZAR DE LA MANERA EN QUE ACTUALIZO LOS DATOS EN ESTE SERVICIO
 * NO TRAERIA CONFUSION AL USUARIO, EN REALIDAD CREO QUE NO DEBERIA IMPACTAR LOS CAMBIOS SI POR
 * LO MENOS 1 DE ESOS FALLA.
 * 
 * SOLUCIONA: SOY UN PELOTUDO Y ME OLVIDE QUE A LOS OBJETOS LOS PUEDO MODIFICAR EN MEMORIA!
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class ClienteServicio {
	// ATRIBUTOS
	// ADMINISTRADOR DE ENTIDADES
	private EntityManager manager;
	
	// JPAController
	private ClienteDAO clienteDao;
	
	// Constructor
	public ClienteServicio() {
		clienteDao = new ClienteDAO();
	}
	
	
	// Registra un cliente en el sistema
	/**
	 * Recibe un objeto de tipo Cliente, no retorna datos.
	 */
	public void cargarCliente(Cliente cliente) {
		// Si el metodo es diferente de null, es por que se encontró un cliente con el mismo DNI
		if (this.obtenerCliente(cliente.getDni(), false) != null) {
			System.out.printf("[ ERROR ] > El cliente %d ya se encuentra en el sistema!%n", cliente.getDni());
			return; // SI EL OBJETO YA ESTÁ EN LA BD, MUESTRA EL MENSAJE Y SALE DEL METODO.
		}
		
		if (cliente.getNombre() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene un nombre asignado o este es nulo!%n", cliente.getDni()); // LOG
			return;
		}
		
		if (cliente.getApellido() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene un apellido asignado o este es nulo!%n", cliente.getDni()); // LOG
			return;
		}
		
		if (cliente.getFechaNacimiento() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una fecha de nacimiento asignada o este es nulo!%n", cliente.getDni()); // LOG
			return;
		}
		
		if (cliente.getCiudad() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una ciudad asignada o este es nulo!%n", cliente.getDni()); // LOG
			return;
		}
		
		if (cliente.getProvincia() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una provincia asignada o este es nulo!%n", cliente.getDni()); // LOG
			return;
		}
		
		if (cliente.getFechaIngreso() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una fecha de ingreso asignada o este es nulo!%n", cliente.getDni()); // LOG
			return;
		}
		
		// MODIFICA TODOS LOS VALORES TEXTUALES QUE TENGA EL OBJETO, A MINUSCULA.
		cliente.setNombre(cliente.getNombre().toLowerCase());
		cliente.setApellido(cliente.getApellido().toLowerCase());
		cliente.setCiudad(cliente.getCiudad().toLowerCase());
		cliente.setProvicia(cliente.getProvincia().toLowerCase());
		cliente.setSexo(Character.toLowerCase(cliente.getSexo()));
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		// CARGA EL OBJETO A LA BD
		try {
			this.manager.getTransaction().begin();
			this.clienteDao.crearEntidadCliente(this.manager, cliente);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El cliente %d cargado correctamente!%n", cliente.getDni()); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el cliente!"); // LOG
		} finally {
			this.manager.close();
		}
		
		// SIN IMPORTANCIA ;)
		this.egg(cliente);
	}
	
	// Busca un cliente en el sistema
	/**
	 * Recibe un primitivo de tipo int, retorna un objeto de tipo Cliente.
	 */
	public Cliente obtenerCliente(int dni, boolean mostrarLog) {
		Cliente regCliente = null;
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regCliente = this.clienteDao.obtenerEntidadCliente(this.manager, dni);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el cliente!"); // LOG
		} finally {
			this.manager.close();
		}
		
		if (mostrarLog && regCliente == null) {
			System.out.printf("[ ERROR ] > Cliente %d no encontrado%n", dni); // LOG
		}
		
		return regCliente;
	}
	
	// Obtener todos los clientes del sistema
	/**
	 * No recibe parametros, retorna una lista de tipo Cliente.
	 */
	public List<Cliente> obtenerTodosLosClientes(){		
		String consulta = String.format("SELECT %c FROM %s %c", 'c', "Cliente", 'c'); // Consulta JPQL
		List<Cliente> regClientes = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();

		try {
			regClientes = this.clienteDao.obtenerEntidadesCliente(this.manager, consulta);
		} catch (Exception e) {
			System.out.print(e);
			System.out.println("[ ERROR ] > Falla al obtener los clientes!"); // LOG
		} finally {
			this.manager.close();
		}
		
		return regClientes;
	}
	
	// OBTIENE LOS CLIENTES Y SUS OBJETOS
//	public List<Cliente> obtenerTodosLosClientesYSusObjetos(){		
////		String consulta = String.format("SELECT %c FROM %s %c", 'c', "Cliente", 'c'); // Consulta JPQL
//		String consulta =
//				"SELECT c FROM Cliente c " +
//				"JOIN FETCH c.entrenamientos";
//		
//		List<Cliente> regClientes = null;
//		
//		// ADMINISTRADOR DE ENTIDADES
//		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
//
//		try {
//			regClientes = this.clienteDao.obtenerEntidadesCliente(this.manager, consulta);
//		} catch (Exception e) {
//			System.out.print(e);
//			System.out.println("[ ERROR ] > Falla al obtener los clientes!"); // LOG
//		} finally {
//			this.manager.close();
//		}
//		
//		return regClientes;
//	}
	
	// NO ME ACUERDO POR QUE HICE ESTO XD
	
	public List<Entrenamiento> obtenerListaDeEntrenamientsos(int id) {
		List<Entrenamiento> lista = new ArrayList<>();
		Cliente regCli = this.obtenerCliente(id, false);
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();

		try {
			lista.addAll(this.manager.merge(regCli).getEntrenamientos());
		} catch (Exception e) {
			System.err.print(e);
			System.err.println("[ ERROR ] > Falla al obtener la lista de entrenamientos!");
		} finally {
			this.manager.close();
		}
		
		return lista;
	}
	
	// Actualiza la información de un cliente
	/**
	 * Recibe un primitivo de tipo int y un objeto de tipo ParametrosClienteTutor, no retorna datos.
	 */
	public boolean actualizarEstadoCliente(int dni, ParametrosClienteTutor paramCli) {
		Cliente cli = this.obtenerCliente(dni, false);
		
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni); // LOG
			return false;
		}

		// CONDICIONES PARA VERIFICAR CUALES ATRIBUTOS SE MODIFICAN, Y CUALES NO
		if (paramCli.nombre != null && paramCli.nombre != cli.getNombre()) {
			cli.setNombre(paramCli.nombre.toLowerCase());
		}
		
		if (paramCli.apellido != null && paramCli.apellido != cli.getApellido()) {
			cli.setApellido(paramCli.apellido.toLowerCase());
		}
		
		if (paramCli.sexo != 'x' && paramCli.sexo != cli.getSexo()) {
			cli.setSexo(Character.toLowerCase(paramCli.sexo));
		}
		
		if (paramCli.ciudad != null && paramCli.ciudad != cli.getCiudad()) {
			cli.setCiudad(paramCli.ciudad.toLowerCase());
		}
		
		if (paramCli.provincia != null && paramCli.provincia != cli.getProvincia()) {
			cli.setProvicia(paramCli.provincia.toLowerCase());
		}
		
		if (paramCli.codigoPostal != 0 && paramCli.codigoPostal != cli.getCodPost()) {
			cli.setCodigoPostal(paramCli.codigoPostal);
		}
		
		if (paramCli.fechaNacimiento != null && !paramCli.fechaNacimiento.isEqual(cli.getFechaNacimiento())) {
			cli.setFechaNacimiento(paramCli.fechaNacimiento);
		}
		
		if (paramCli.fechaIngreso != null && !paramCli.fechaIngreso.isEqual(cli.getFechaIngreso())) {
			cli.setFechaIngreso(paramCli.fechaIngreso);
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();

		try {
			this.manager.getTransaction().begin();
			this.clienteDao.actualizarCliente(this.manager, cli);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El cliente %d actualizado correctamente!%n", cli.getDni()); // LOG
			return true;
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el cliente!"); // LOG
			return false;
		} finally {
			this.manager.close();
		}
	}
	
	// Da de baja a un cliente del sistema
	/**
	 * Recibe un primitivo de tipo int, no retorna datos.
	 */
	public void eliminarCliente(int dni) {
		Cliente cli = this.obtenerCliente(dni, false);
		
		// En caso de que no se encuentre cliente en el sistema,
		// simplemente no hace nada y finaliza el metodo destruyendo el EMF
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni); // LOG
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			cli = this.manager.merge(cli); // RECONEXION DE LA ENTIDAD
			this.clienteDao.eliminarEntidadCliente(this.manager, cli);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El cliente %d eliminado correctamente!%n", cli.getDni()); // LOG
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el cliente!"); // LOG
		} finally {
			this.manager.close();
		}
	}










	// YOU DO TOO MANY SEARCHES, BILLY!
	private void egg(Cliente cli) {
		if (cli.getNombre() == "juan" && cli.getApellido() == "salvo") {
			System.out.println(
					"[ ! ] > El héroe verdadero es el héroe en grupo, el héroe solidario. " +
					"Nunca el héroe individual, el héroe solo..."
			);
		}
	}
}
