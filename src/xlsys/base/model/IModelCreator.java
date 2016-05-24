package xlsys.base.model;

import java.io.Serializable;
import java.util.Map;

import xlsys.base.database.IDataBase;

public interface IModelCreator<M extends IModel, K extends Serializable>
{
	public int getAllCount(IDataBase dataBase);

	public Map<K, M> createAllModels(IDataBase dataBase);

	public M createModel(IDataBase dataBase, K key);

	public IModel createModelObject(IDataBase dataBase, Serializable key);
	
	public Map<? extends Serializable, ? extends IModel> createAllModelObjects(IDataBase dataBase);
}
