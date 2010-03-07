/**
 * 
 */
package jp.sourceforge.tmdmaker.dialect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ddlutils.PlatformFactory;

/**
 * @author nakaG
 *
 */
public class DdlUtilsDialectProvider implements DialectProvider {

	/**
	 * 
	 */
	public DdlUtilsDialectProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see jp.sourceforge.tmdmaker.dialect.DialectProvider#getDatabaseList()
	 */
	@Override
	public List<String> getDatabaseList() {
		List<String> result = new ArrayList<String>();
		for (String platform : PlatformFactory.getSupportedPlatforms()) {
			result.add(platform);
		}
		Collections.sort(result);
		return result;
	}

}
