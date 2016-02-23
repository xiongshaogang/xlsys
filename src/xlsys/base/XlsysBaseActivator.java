package xlsys.base;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class XlsysBaseActivator implements BundleActivator
{
	private static BundleContext bundleContext;
	
	@Override
	public void start(BundleContext bundleContext) throws Exception
	{
		XlsysBaseActivator.bundleContext = bundleContext;
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		bundleContext = null;
	}

	public static BundleContext getBundleContext()
	{
		return bundleContext;
	}
	
	public static void setBundleContext(BundleContext bundleContext)
	{
		XlsysBaseActivator.bundleContext = bundleContext;
	}
}
