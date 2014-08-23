/*
 * Copyright 2009-2011 TMD-Maker Project <http://tmdmaker.sourceforge.jp/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.sourceforge.tmdmaker.model;

/**
 * リレーションシップを表すコネクションとのリレーションシップ
 * 
 * @author nakaG
 * 
 */
@SuppressWarnings("serial")
public class RelatedRelationship extends AbstractConnectionModel {
	/** ファクトリーは今のところ永続化対象外 */
	private transient RelationHelperFactory factory = null;

	private RelationHelperFactory getRelationHelperFactory() {
		if (factory == null) {
			factory = new RelationHelperFactory();
		}
		return factory;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param source
	 *            接続元
	 * @param target
	 *            接続先
	 */
	public RelatedRelationship(ConnectableElement source,
			ConnectableElement target) {
		setSource(source);
		setTarget(target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#isDeletable()
	 */
	@Override
	public boolean isDeletable() {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#getSourceName()
	 */
	@Override
	public String getSourceName() {
		return getRelationHelperFactory().getRelationHelper().getSourceName();
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see jp.sourceforge.tmdmaker.model.AbstractConnectionModel#getTargetName()
	 */
	@Override
	public String getTargetName() {
		return getRelationHelperFactory().getRelationHelper().getTargetName();
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * 対照表や対応表のリレーションのヘルパークラス
	 */
	private class TableRelationHelper extends RelationHelper {
		/**
		 * 表の作成元のリレーションを取得する
		 * 
		 * @return 作成元のリレーション
		 */
		private AbstractConnectionModel getConnection() {
			return (AbstractConnectionModel) getSource();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship.RelationHelper#getSourceName()
		 */
		@Override
		public String getSourceName() {
			return getConnection().getSourceName();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship.RelationHelper#getTargetName()
		 */
		@Override
		public String getTargetName() {
			return getConnection().getTargetName();
		}

	}

	/**
	 * 多値のANDのヘッダーディテールと相違マークとのリレーションのヘルパークラス
	 */
	private class MultivalueAnd2AggregatorRelationHelper extends RelationHelper {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship.RelationHelper#getTargetName()
		 */
		@Override
		public String getTargetName() {
			return getTarget().getModelTargetConnections().get(0)
					.getSourceName();
		}

	}

	/**
	 * 多値のANDのスーパーセットと相違マークとのリレーションのヘルパークラス
	 */
	private class MultivalueAndSuperset2AggregatorRelationHelper extends
			RelationHelper {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship.RelationHelper#getTargetName()
		 */
		@Override
		public String getTargetName() {
			AbstractConnectionModel h2a = getTarget()
					.getModelTargetConnections().get(1);
			AbstractConnectionModel d2a = getTarget()
					.getModelTargetConnections().get(2);
			return h2a.getSourceName() + "," + d2a.getSourceName();
		}

	}

	/**
	 * スーパーセットタイプとスーパーセットのリレーションのヘルパークラス
	 */
	private class VirtualSupersetType2VirtualSupersetRelationHelper extends
			RelationHelper {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship.RelationHelper#getSourceName()
		 */
		@Override
		public String getSourceName() {
			return getTarget().getName();
		}

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see jp.sourceforge.tmdmaker.model.RelatedRelationship.RelationHelper#getTargetName()
		 */
		@Override
		public String getTargetName() {
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			for (AbstractConnectionModel c : getSource()
					.getModelTargetConnections()) {
				if (first) {
					first = false;
				} else {
					builder.append(',');
				}
				builder.append(c.getSource().getName());
			}
			return builder.toString();
		}

	}

	/**
	 * リレーションのデフォルトヘルパークラス
	 */
	private class RelationHelper {
		/**
		 * 接続元のモデル名を取得する
		 * 
		 * @return 接続元のモデル名
		 */
		public String getSourceName() {
			return getSource().getName();
		}

		/**
		 * 接続先のモデル名を取得する
		 * 
		 * @return 接続先のモデル名
		 */
		public String getTargetName() {
			return getTarget().getName();
		}
	}

	/**
	 * リレーションのヘルパークラスのファクトリ
	 */
	private class RelationHelperFactory {

		public RelationHelper getRelationHelper() {
			ConnectableElement model = getSource();

			// 対照表・対応表
			if (model instanceof AbstractConnectionModel) {
				return new TableRelationHelper();
			}
			// 多値のANDのスーパーセット
			if (model instanceof MultivalueAndSuperset) {
				return new MultivalueAndSuperset2AggregatorRelationHelper();
			}
			model = getTarget();
			// 多値のAND
			if (model instanceof MultivalueAndAggregator) {
				return new MultivalueAnd2AggregatorRelationHelper();
			}
			// スーパーセット
			if (model instanceof VirtualSuperset) {
				return new VirtualSupersetType2VirtualSupersetRelationHelper();
			}
			return new RelationHelper();
		}
	}
}
