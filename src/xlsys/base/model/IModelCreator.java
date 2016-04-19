package xlsys.base.model;

import java.io.Serializable;
import java.util.Map;

import xlsys.base.database.IDataBase;
import xlsys.base.model.IModel;

public interface IModelCreator<M extends IModel, K extends Serializable>
{

	public Map<K, M> createAllModels(IDataBase dataBase);

	public M createModel(IDataBase dataBase, K key);
	
}
