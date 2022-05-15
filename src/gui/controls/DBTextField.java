package gui.controls;

import javax.swing.JTextField;

// ------------------------------------------------------------------------------------------------
@SuppressWarnings("serial")
public class DBTextField <T> extends JTextField 
{
	private T entity;

	// --------------------------------------------------------------------------------------------
	public DBTextField ()
	{
		
	}
	
	// --------------------------------------------------------------------------------------------
	public DBTextField (T entity)
	{
		setEntity (entity);
	}
	
	// --------------------------------------------------------------------------------------------
	public void setEntity (T entity)
	{
		this.entity = entity;
		setText (entity == null ? "" : entity.toString());
	}
	
	// --------------------------------------------------------------------------------------------
	public T getEntity ()
	{
		return entity;
	}
}
