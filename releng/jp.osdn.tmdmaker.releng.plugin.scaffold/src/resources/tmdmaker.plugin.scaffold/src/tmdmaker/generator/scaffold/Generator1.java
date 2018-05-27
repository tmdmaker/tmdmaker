package tmdmaker.generator.scaffold;

import java.util.List;

import jp.sourceforge.tmdmaker.TMDPlugin;
import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.generate.Generator;

public class Generator1 implements Generator {

	public Generator1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(String arg0, List<AbstractEntityModel> arg1) {
		// TODO Auto-generated method stub
		TMDPlugin.showMessageDialog("雛形です");
	}

	@Override
	public String getGeneratorName() {
		// TODO Auto-generated method stub
		return "雛形";
	}

	@Override
	public String getGroupName() {
		// TODO Auto-generated method stub
		return "雛形";
	}

	@Override
	public boolean isImplementModelOnly() {
		// TODO Auto-generated method stub
		return false;
	}

}
