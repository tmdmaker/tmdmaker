package jp.sourceforge.tmdmaker.importer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;

public interface FileImporter {

	List<AbstractEntityModel> importEntities(String filePath)
			throws FileNotFoundException, IOException;

	String getImporterName();
}