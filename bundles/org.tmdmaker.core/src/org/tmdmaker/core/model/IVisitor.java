/*
 * Copyright 2009-2019 TMD-Maker Project <https://www.tmdmaker.org>
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
package org.tmdmaker.core.model;

import org.tmdmaker.core.model.other.Memo;
import org.tmdmaker.core.model.other.TurboFile;

/**
 * Visitorパターンの訪問者側
 * 
 * @author tohosaku
 * 
 */
public interface IVisitor {
	void visit(ModelElement model);
	void visit(AbstractRelationship relationship);
	void visit(Attribute entity);
	void visit(CombinationTable entity);
	void visit(Detail entity);
	void visit(Diagram diagram);
	void visit(Entity entity);
	void visit(Event2EventRelationship relationship);
	void visit(Entity2SubsetTypeRelationship relationship);
	void visit(Entity2VirtualSupersetTypeRelationship relationship);
	void visit(IdentifierRef identifier);
	void visit(Identifier identifier);
	void visit(Laputa entity);
	void visit(MappingList entity);
	void visit(AbstractEntityModel entity);
	void visit(MultivalueAndSuperset entity);
	void visit(MultivalueAndAggregator aggregator);
	void visit(MultivalueOrEntity entity);
	void visit(RecursiveRelationship relationship);
	void visit(RecursiveTable entity);
	void visit(RelatedRelationship relationship);
	void visit(SubsetEntity entity);
	void visit(SubsetType type);
	void visit(VirtualEntity entity);
	void visit(VirtualSuperset entity);
	void visit(VirtualSupersetType type);
	void visit(Memo model);
	void visit(TurboFile entity);
	void visit(TurboFileRelationship relationship);
	void visit(VirtualSupersetType2VirtualSupersetRelationship relationship);
}