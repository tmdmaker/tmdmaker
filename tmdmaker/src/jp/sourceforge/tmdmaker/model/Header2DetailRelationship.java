package jp.sourceforge.tmdmaker.model;

/**
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class Header2DetailRelationship extends
		TransfarReuseKeysToTargetRelationship {
	/** 概念的 スーパーセット */
	private Superset superset;

	/** DTL */
	private Detail detail;

	/** MAエンティティのリレーションシップの集約箇所 */
	private MultivalueAndAggregator aggregator;

	private RelatedRelationship superset2aggregator;
	private RelatedRelationship header2aggregator;
	private RelatedRelationship detail2aggregator;

	/**
	 * コンストラクタ
	 * 
	 * @param header
	 *            HDRとなるエンティティ
	 */
	public Header2DetailRelationship(AbstractEntityModel header) {
		setSource(header);
		detail = new Detail();
		detail.setName(header.getName() + "DTL");
		detail.setEntityType(header.getEntityType());
		detail.setConstraint(header.getConstraint().getTranslated(100, 0));
		detail.setOriginalReusedIdentifier(header.createReusedIdentifier());
		detail.setDetailIdeitifierName(header.getName() + "明細番号");
		setTarget(detail);

		superset = new Superset();
		superset.setEntityType(header.getEntityType());
		superset.setConstraint(header.getConstraint().getTranslated(64, -80));
		superset.setName(header.getName());
		superset.addReusedIdentifier(header);
		superset.setDetail(detail);

		aggregator = new MultivalueAndAggregator();
		aggregator.setConstraint(header.getConstraint().getTranslated(75, -30));

		superset2aggregator = new RelatedRelationship();
		superset2aggregator.setSource(superset);
		superset2aggregator.setTarget(aggregator);

		header2aggregator = new RelatedRelationship();
		header2aggregator.setSource(header);
		header2aggregator.setTarget(aggregator);

		detail2aggregator = new RelatedRelationship();
		detail2aggregator.setSource(detail);
		detail2aggregator.setTarget(aggregator);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#connect()
	 */
	@Override
	public void connect() {
		super.connect();
		getSource().getDiagram().addChild(superset);
		getSource().getDiagram().addChild(aggregator);
		superset2aggregator.connect();
		header2aggregator.connect();
		detail2aggregator.connect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#disconnect()
	 */
	@Override
	public void disconnect() {
		detail2aggregator.disconnect();
		header2aggregator.disconnect();
		superset2aggregator.disconnect();
		getSource().getDiagram().removeChild(aggregator);
		getSource().getDiagram().removeChild(superset);
		super.disconnect();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#attachTarget()
	 */
	@Override
	public void attachTarget() {
		getSource().getDiagram().addChild(detail);
		super.attachTarget();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#detachTarget()
	 */
	@Override
	public void detachTarget() {
		super.detachTarget();
		getSource().getDiagram().removeChild(detail);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.TransfarReuseKeysToTargetRelationship#identifierChanged()
	 */
	@Override
	public void identifierChanged() {
		super.identifierChanged();
		superset.fireIdentifierChanged(this);
		// AbstractConnectionModel m = getTarget().getModelSourceConnections()
		// .get(0);
		// System.out.println("Source = " + m.getSource().getClass());
		// System.out.println("Source = " + m.getSource().getName());
		// System.out.println("Target = " + m.getTarget().getClass());
		// System.out.println("Target = " + m.getTarget().getName());
		// MultivalueAndAggregator aggregator = (MultivalueAndAggregator) m
		// .getTarget();
		// AbstractConnectionModel t =
		// aggregator.getModelTargetConnections().get(
		// 0);
		// System.out.println("Source = " + t.getSource().getClass());
		// System.out.println("Source = " + t.getSource().getName());
		// System.out.println("Target = " + t.getTarget().getClass());
		// System.out.println("Target = " + t.getTarget().getName());
		// t.getSource().firePropertyChange(AbstractEntityModel.PROPERTY_REUSED,
		// null, null);

	}

}
