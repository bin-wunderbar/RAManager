package sgbdoo;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import bll.RAConfig;
import bll.RAManager;
import bll.RAManagerException;

// ------------------------------------------------------------------------------------------------
/**
 * Gestor de alertas de usuario
 * 
 * @author G3
 *
 */
public class AlertsManager 
{
	public static final String OBJECTDB_DIRECTORY 	= "objectdb";
	public static final String OBJECTDB_DATABASE 	= "alerts.odb"; 
	
	private EntityManager entityManager;
	
	// ------------------------------------------------------------------------------------------------
	/**
	 * Inicializa el gestor de alertas
	 * 
	 * @param config - Configuracion de la aplicacion
	 */
	public AlertsManager (RAConfig config)
	{
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory (
				config.getRAManagerPath() + File.separatorChar + OBJECTDB_DIRECTORY + File.separatorChar + OBJECTDB_DATABASE
				);
		 entityManager = entityManagerFactory.createEntityManager();
	}

	// ------------------------------------------------------------------------------------------------
	/**
	 * Devuelve el listado de alertas disponibles que coinciden con el filtro indicado 
	 * 
	 * @param filter - Filtro de texto aplicado a la fecha y la nota
	 * @return La lista de alertas
	 * 
	 * @throws RAManagerException
	 */
	public ArrayList <Alert> getAlerts (String filter) throws RAManagerException
	{
		ArrayList <Alert> alerts = new ArrayList <> ();
		
		try
		{
			TypedQuery <Alert> typedQuery = entityManager.createQuery("select a from Alert a", Alert.class);

			for (Alert alert : typedQuery.getResultList())
			{
				if (alert.containsFilter (filter)) alerts.add (alert);
			}
			
		} 
		catch (Exception exception)
		{
			throw new RAManagerException (exception.getMessage());
		}
		
		return alerts;
	}

	// ------------------------------------------------------------------------------------------------
	/**
	 * Devuelve el listado de alertas no realizadas que han expirado en la fecha actual 
	 * 
	 * @param filter - Filtro de texto aplicado a la fecha y la nota
	 * @return La lista de alertas
	 * 
	 * @throws RAManagerException
	 */
	public ArrayList <Alert> getExpiredAlerts (String filter) throws RAManagerException
	{
		ArrayList <Alert> expiredAlerts = new ArrayList <> ();

		LocalDateTime now = LocalDateTime.now ();
		
		for (Alert alert : getAlerts (filter))
		{
			if (!alert.isDone() && alert.getEventTime().isBefore(now))
			{
				expiredAlerts.add (alert);
			}
		}
		
		return expiredAlerts;
	}
	
	// ------------------------------------------------------------------------------------------------
	/**
	 * Registra de forma persistente una alerta
	 * 
	 * @param alert - Alerta a almacenar
	 * 
	 * @throws RAManagerException
	 */
	public void save (Alert alert) throws RAManagerException
	{
		try
		{

			entityManager.getTransaction().begin();

			if (alert.getId() > RAManager.NO_ID)
			{
				Query query = entityManager.createQuery("update Alert set eventTime = :pEventTime, note = :pNote, done = :pDone where id = :pId");
				query.setParameter ("pEventTime", alert.getEventTime());
				query.setParameter ("pNote", alert.getNote ());
				query.setParameter ("pDone", alert.isDone());
				query.setParameter ("pId", alert.getId());
				query.executeUpdate();
			}
			else
			{
				entityManager.persist(alert);
			}
			
			entityManager.getTransaction().commit();		
			entityManager.clear ();
		} 
		catch (Exception exception)
		{
			throw new RAManagerException (exception.getMessage());
		}
	}
	
	// ------------------------------------------------------------------------------------------------
	/**
	 * Elimina un listado de alertas
	 * 
	 * @param ids - Lista con los identificadores de las alertas a eliminar
	 *  
	 * @throws RAManagerException
	 */
	public void deleteAlerts(ArrayList<Integer> ids) throws RAManagerException 
	{
		try
		{
			entityManager.getTransaction().begin();

			for (int id : ids)
			{
				Query query = entityManager.createQuery("delete from Alert where id = :pId");
				query.setParameter ("pId", id);
				query.executeUpdate();
			}
			
			entityManager.getTransaction().commit();		
			entityManager.clear ();
		} 
		catch (Exception exception)
		{
			throw new RAManagerException (exception.getMessage());
		}
	}
	
}
