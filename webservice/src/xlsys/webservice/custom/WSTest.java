package xlsys.webservice.custom;

import xlsys.base.util.ObjectUtil;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "xlsys.webservice.custom")
@SOAPBinding(style = SOAPBinding.Style.RPC)

public class WSTest
{
	@WebMethod(action="o2Str",operationName="objToString",exclude=false)
	@WebResult(name="returnStr")
	public java.lang.String objectToString(@WebParam(name="avg0")java.lang.Object avg0)
	{
		return xlsys.base.util.ObjectUtil.objectToString(avg0);
	}
}
