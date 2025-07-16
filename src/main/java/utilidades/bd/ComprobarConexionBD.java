/*
 * COMPRUEBA SI HAY CONEXIÓN CON LA BD (CLASE OBSOLETA, LA MISMA FUE MOVIDA A EMFSingleton)
 */

package utilidades.bd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/*
 * 
 * NOTA:
 * 
 * VERIFICAR QUE ESTO ESTE CORRECTO Y NO INCLUYA ALGUN PROBLEMA RESPECTO A LA CONEXION A BD
 * 
 */

public class ComprobarConexionBD {
	private static EntityManager em;
	
	public static boolean hayConexion(EntityManagerFactory emf) {
		try {
			em = emf.createEntityManager();
			em.createNativeQuery("SELECT 1").getSingleResult();
			return true; // HAY CONEXIÓN
		} catch (Exception e) {
			System.out.println(e);
			return false; // NO HAY CONEXIÓN
		} finally {
			// ES NECESARIO COMPROBAR QUE ESTÉ ABIERTO O SEA DISTINTO DE NULL, SINO SE ROMPE
			if (em != null && em.isOpen()) {
				em.close();				
			}
		}
	}
}
