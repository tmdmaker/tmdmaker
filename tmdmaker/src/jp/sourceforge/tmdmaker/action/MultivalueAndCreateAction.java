package jp.sourceforge.tmdmaker.action;

import jp.sourceforge.tmdmaker.model.AbstractEntityModel;
import jp.sourceforge.tmdmaker.model.Cardinality;
import jp.sourceforge.tmdmaker.model.Detail;
import jp.sourceforge.tmdmaker.model.Diagram;
import jp.sourceforge.tmdmaker.model.Header2DetailRelationship;
import jp.sourceforge.tmdmaker.model.MultivalueAndAggregator;
import jp.sourceforge.tmdmaker.model.RelatedRelationship;
import jp.sourceforge.tmdmaker.model.Superset;
import jp.sourceforge.tmdmaker.model.command.ConnectionCreateCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 多値のAND(HDR-DTL)作成アクション
 * 
 * @author nakaG
 * 
 */
public class MultivalueAndCreateAction extends AbstractEntitySelectionAction {
	/** 多値のAND作成アクションを表す定数 */
	public static final String ID = "_MA";

	/**
	 * コンストラクタ
	 * 
	 * @param part
	 *            エディター
	 */
	public MultivalueAndCreateAction(IWorkbenchPart part) {
		super(part);
		setText("多値のAND(HDR-DTL)作成");
		setId(ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		System.out.println("Create Header Detail");
		AbstractEntityModel model = getModel();
		CompoundCommand ccommand = new CompoundCommand();

		// Detail作成
		Detail detail = new Detail();
		detail.setName(model.getName());
		detail.setConstraint(model.getConstraint().getTranslated(100, 0));

		// Detailを追加してHeaderと接続
		DetailAddCommand command1 = new DetailAddCommand(model, detail);
		ccommand.add(command1);

		// Supersetを追加してHeader-Detailと接続
		Superset superset = new Superset();
		superset.setConstraint(model.getConstraint().getTranslated(64, -80));
		superset.setName(model.getName());
		superset.addReuseKey(model);
		SupersetCreateCommand command2 = new SupersetCreateCommand(superset,
				model);
		ccommand.add(command2);

		MultivalueAndAggregator aggregator = new MultivalueAndAggregator();
		aggregator.setConstraint(model.getConstraint().getTranslated(75, -30));

		MultivalurAndAggregatorCreateCommand command11 = new MultivalurAndAggregatorCreateCommand(
				model.getDiagram(), aggregator);
		ccommand.add(command11);

		ConnectionCreateCommand command22 = new ConnectionCreateCommand();
		command22.setConnection(new RelatedRelationship());
		command22.setSource(superset);
		command22.setTarget(aggregator);
		ccommand.add(command22);

		ConnectionCreateCommand command3 = new ConnectionCreateCommand();
		command3.setConnection(new RelatedRelationship());
		command3.setSource(model);
		command3.setTarget(aggregator);
		ccommand.add(command3);

		ConnectionCreateCommand command4 = new ConnectionCreateCommand();
		command4.setConnection(new RelatedRelationship());
		command4.setSource(detail);
		command4.setTarget(aggregator);
		ccommand.add(command4);

		execute(ccommand);
	}

	/**
	 * 
	 * @author nakaG
	 * 
	 */
	private static class SupersetCreateCommand extends Command {
		private Diagram diagram;
		private Superset superset;
		private AbstractEntityModel header;

		// private MultivalueAndAggregator aggregator;
		// private Superset2AggregateRelationship relationship;
		// private RelatedRelationship relationship;
		public SupersetCreateCommand(Superset superset,
				AbstractEntityModel header) {
			this.diagram = header.getDiagram();
			this.header = header;
			this.superset = superset;
			// Rectangle constraint = header.getConstraint().getTranslated(50,
			// -50);
			// this.superset.setConstraint(constraint);
			// this.superset.setName(header.getName());
			// this.superset.addReuseKey(header);
			// this.aggregator = new MultivalueAndAggregator();
			// this.aggregator.setConstraint(model.getConstraint().getTranslated(0,
			// 20));
			// this.relationship = new Superset2AggregateRelationship();
			// this.relationship = new RelatedRelationship();
			// this.relationship.setSource(this.header);
			// this.relationship.setTarget(this.superset);
			// this.relationship.setTarget(this.aggregator);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(superset);
			// diagram.addChild(aggregator);
			// relationship.connect();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			// relationship.disConnect();
			// diagram.removeChild(aggregator);
			diagram.removeChild(superset);
		}

	}

	private static class DetailAddCommand extends Command {
		private Diagram diagram;
		private Detail model;
		private Header2DetailRelationship relationship;
		private AbstractEntityModel header;

		public DetailAddCommand(AbstractEntityModel header, Detail model) {
			this.diagram = header.getDiagram();
			this.header = header;
			this.model = model;
			// this.model.setConstraint(header.getConstraint().getTranslated(50,0));
			// this.model.setName(header.getName());
			relationship = new Header2DetailRelationship();
			relationship.setSource(header);
			relationship.setTarget(model);
			relationship.setTargetCardinality(Cardinality.MANY);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(model);
//			model.setDiagram(diagram);
			relationship.connect();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			relationship.disconnect();
//			model.setDiagram(null);
			diagram.removeChild(model);
		}

	}

	private static class MultivalurAndAggregatorCreateCommand extends Command {
		private Diagram diagram;
		private MultivalueAndAggregator model;

		public MultivalurAndAggregatorCreateCommand(Diagram diagram,
				MultivalueAndAggregator model) {
			super();
			this.diagram = diagram;
			this.model = model;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			diagram.addChild(model);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			diagram.removeChild(model);
		}

	}
}
