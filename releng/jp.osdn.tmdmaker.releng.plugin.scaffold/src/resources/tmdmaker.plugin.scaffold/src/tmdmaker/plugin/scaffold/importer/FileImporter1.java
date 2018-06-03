package tmdmaker.plugin.scaffold.importer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.importer.FileImporter;

public class FileImporter1 implements FileImporter {

	public FileImporter1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImporterName() {
		// TODO Auto-generated method stub
		return "Importer雛形";
	}

	@Override
	public List<AbstractEntityModel> importEntities(String arg0) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
