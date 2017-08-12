package jp.sourceforge.tmdmaker.rcp;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "jp.sourceforge.tmdmaker.rcp.messages"; //$NON-NLS-1$
	public static String ApplicationWorkbenchWindowAdvisor_0;
	public static String ApplicationWorkbenchWindowAdvisor_2;
	public static String ApplicationWorkbenchWindowAdvisor_3;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
