package xlsys.base.dataset;

import xlsys.base.database.bean.ParamBean;

/**
 * 可存储数据集事件
 * @author Lewis
 *
 */
public class StorableDataSetEvent extends DataSetEvent
{
	private static final long serialVersionUID = -4397663295362036004L;
	
	public ParamBean selectBean;
	public IDataSet refDataSet;
	
	public StorableDataSetEvent(IStorableDataSet dataSet)
	{
		super(dataSet);
	}
}
