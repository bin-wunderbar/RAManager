package sgbdoo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import bll.IRAObject;

// ------------------------------------------------------------------------------------------------
/**
 * Entidad de alerta de usuario
 * 
 * @author G3
 *
 */
@Entity
public class Alert implements Serializable, IRAObject
{
	private static final long serialVersionUID = 3876907364736774112L;

	public static final int COLUMN_EVENT_TIME	= 0;
	public static final int COLUMN_NOTE			= 1;
	public static final int COLUMN_DONE			= 2;

	@Id @GeneratedValue
	private int id;
	
	private LocalDateTime eventTime;
	private String note;
	private boolean done;
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicia una alerta sin parametros
	 */
	public Alert ()
	{
		eventTime 	= LocalDateTime.now();
		note		= "";
		done		= false;
	}
	
	// --------------------------------------------------------------------------------------------
	/**
	 * Inicia una alerta con los parametros indicados
	 * 
	 * @param eventTime - Fecha de vencimiento de la alerta
	 * @param note - Nota o recordatorio
	 * @param done - Define si la alerta se encuentra realizada
	 */
	public Alert (LocalDateTime eventTime, String note, boolean done)
	{
		this.eventTime	= eventTime;
		this.note		= note;
		this.done		= done;
	}
	
	
	// --------------------------------------------------------------------------------------------
	@Override
	public int getId() {
		return id;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getValue(int index) {
		switch (index)
		{
			case COLUMN_EVENT_TIME:		return eventTime.toString();
			case COLUMN_NOTE:			return note;
			case COLUMN_DONE:			return done;
		}
		
		return null;
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public Object getShowValue(int index) {
		return getValue (index);
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public void setValue(int index, Object object) {
		switch (index)
		{
			case COLUMN_EVENT_TIME:		eventTime 	= LocalDateTime.parse ((String)object); break;
			case COLUMN_NOTE:			note 		= (String)object; break;
			case COLUMN_DONE:			done		= (boolean)object; break;
		}
		
	}

	// --------------------------------------------------------------------------------------------
	@Override
	public boolean containsFilter(String filter) {
		return eventTime.toString().toLowerCase().contains (filter) || note.toLowerCase().contains(filter);
	}

	// --------------------------------------------------------------------------------------------
	// GETTERS & SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * @return eventTime
	 */
	public LocalDateTime getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime
	 */
	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}

	/**
	 * @param eventTimeString
	 */
	public void setEventTime(String eventTimeString) {
		this.eventTime = LocalDateTime.parse (eventTimeString);
	}

	/**
	 * @return note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * @param id
	 */
	public void setId (int id) {
		this.id = id;
	}
	

}
