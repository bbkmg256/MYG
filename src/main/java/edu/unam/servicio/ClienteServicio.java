/*
	Servicio de cliente (Esto valida la movida de las
	peticiones a BD desde la vista).
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.ClienteDAO;
import jakarta.persistence.EntityManager;

// VARIOS
import java.time.LocalDate;
import java.util.List;
import utilidades.ParametrosClienteTutor;

// SINGLETON EMF
import utilidades.EMFSingleton;

// ENTIDAD
import edu.unam.modelo.Cliente;


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
		if (this.obtenerCliente(cliente.getDni()) != null) {
			System.out.printf("[ ERROR ] > El cliente %d ya se encuentra en el sistema!%n", cliente.getDni());
			return; // SI EL OBJETO YA ESTÁ EN LA BD, MUESTRA EL MENSAJE Y SALE DEL METODO.
		}
		
		if (cliente.getNombre() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene un nombre asignado o este es nulo!%n", cliente.getDni());
			return;
		}
		
		if (cliente.getApellido() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene un apellido asignado o este es nulo!%n", cliente.getDni());
			return;
		}
		
		if (cliente.getFechaNacimiento() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una fecha de nacimiento asignada o este es nulo!%n", cliente.getDni());
			return;
		}
		
		if (cliente.getCiudad() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una ciudad asignada o este es nulo!%n", cliente.getDni());
			return;
		}
		
		if (cliente.getProvincia() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una provincia asignada o este es nulo!%n", cliente.getDni());
			return;
		}
		
		if (cliente.getFechaIngreso() == null) {
			System.out.printf("[ ERROR ] > El cliente %d no tiene una fecha de ingreso asignada o este es nulo!%n", cliente.getDni());
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
			System.out.printf("[ EXITO ] > El cliente %d cargado correctamente!%n", cliente.getDni());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el cliente!");
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
	public Cliente obtenerCliente(int dni) {
		Cliente regCliente = null;
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regCliente = this.clienteDao.obtenerEntidadCliente(this.manager, dni);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el cliente!");
		} finally {
			this.manager.close();
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
			System.out.println("[ ERROR ] > Falla al obtener los clientes!");
		} finally {
			this.manager.close();
		}
		
		return regClientes;
	}
	
	// Actualiza la información de un cliente
	/**
	 * Recibe un primitivo de tipo int y un objeto de tipo ParametrosClienteTutor, no retorna datos.
	 */
	public void actualizarEstadoCliente(int dni, ParametrosClienteTutor paramCli) {
		Cliente cli = this.obtenerCliente(dni);
		
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
			return;
		}

		// CONDICIONES PARA VERIFICAR CUALES ATRIBUTOS SE MODIFICAN, Y CUALES NO
		if (paramCli.nombre != null) {
			cli.setNombre(paramCli.nombre.toLowerCase());
		}
		
		if (paramCli.apellido != null) {
			cli.setApellido(paramCli.apellido.toLowerCase());
		}
		
		if (paramCli.sexo != 'x') {
			cli.setSexo(Character.toLowerCase(paramCli.sexo));
		}
		
		if (paramCli.ciudad != null) {
			cli.setCiudad(paramCli.ciudad.toLowerCase());
		}
		
		if (paramCli.provincia != null) {
			cli.setProvicia(paramCli.provincia.toLowerCase());
		}
		
		if (paramCli.codigoPostal != 0) {
			cli.setCodigoPostal(paramCli.codigoPostal);
		}
		
		if (paramCli.fechaNacimiento != null) {
			cli.setFechaNacimiento(paramCli.fechaNacimiento);
		}
		
		if (paramCli.fechaIngreso != null) {
			cli.setFechaIngreso(paramCli.fechaIngreso);
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();

		try {
			this.manager.getTransaction().begin();
			this.clienteDao.actualizarCliente(this.manager, cli);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El cliente %d actualizado correctamente!%n", cli.getDni());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el cliente!");
		} finally {
			this.manager.close();
		}
	}
	
	// Da de baja a un cliente del sistema
	/**
	 * Recibe un primitivo de tipo int, no retorna datos.
	 */
	public void eliminarCliente(int dni) {
		Cliente cli = this.obtenerCliente(dni);
		
		// En caso de que no se encuentre cliente en el sistema,
		// simplemente no hace nada y finaliza el metodo destruyendo el EMF
		if (cli == null) {
			System.out.printf("[ ERROR ] > El cliente %d no se encuentra en el sistema!%n", dni);
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			cli = this.manager.merge(cli); // RECONEXION DE LA ENTIDAD
			this.clienteDao.eliminarEntidadCliente(this.manager, cli);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El cliente %d eliminado correctamente!%n", cli.getDni());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el cliente!");
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
