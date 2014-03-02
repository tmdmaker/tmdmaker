package jp.sourceforge.tmdmaker.generate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.ForeignKey;
import org.apache.ddlutils.model.Reference;
import org.apache.ddlutils.model.Table;

/**
 * 外部キー制約
 * 
 * @author tohosaku <ny@cosmichorror.org>
 *
 */
public class ForeignConstraints {
	
    Table           table;
    Map<String, List<Reference>> foreignReferences;
    Map<String, List<Reference>> recursiveForeignReferences;
    
    
    /**
     * コンストラクタ
     * @param table
     *     制約を課すテーブル
     */
    public ForeignConstraints(Table table){
        this.table        = table;
        foreignReferences = new HashMap<String, List<Reference>>();
        recursiveForeignReferences = new HashMap<String, List<Reference>>();
    }
    
       
    /**
     * 
     * 外部キーの参照先を追加する
     * 
     * @param foreignTableName
     *        参照先のテーブル名
     * @param references
     *        参照するカラム
     * @param isRecursive
     *        参照元が再帰表かどうか
     */
    public void addForeignReference(String foreignTableName, List<Reference> references, boolean isRecursive){
    	System.out.println("addForeignReference() " + foreignTableName + " " + references.size() +" "+ isRecursive);
    	if (isRecursive) {
			addRecursiveForeignReference(foreignTableName, references);
		}
		else{
			addNonRecursiveForeignReference(foreignTableName, references);
		}
    }
    
    /**
     * 外部キーの参照先を追加(制約対象が再帰表ではない場合)
     * 
     * @param tableName
     * @param references
     */
    private void addNonRecursiveForeignReference(String tableName, List<Reference> references){
    	foreignReferences.put(tableName, references);
    }
    
    
    /**
     * 外部キーの参照先を追加(制約対象が再帰表の場合)
     * 
     * @param tableName
     * @param references
     */
    private void addRecursiveForeignReference(String tableName, List<Reference> references){
    	recursiveForeignReferences.put(tableName, references);
    }

    /**
     * テーブルに外部キー制約をかける
     * 
     * @param database
     */
    public void addForeignKeys(Database database){
        
		for (Map.Entry<String, List<Reference>> foreignmap
				: foreignReferences.entrySet()) {
			
			Table foreignTable = database.findTable(foreignmap.getKey());

			if (foreignTable == null){
				continue;
			}
			addForeignKey(foreignTable, foreignmap.getValue());
		}
		
		for (Map.Entry<String, List<Reference>> foreignmap
				: recursiveForeignReferences.entrySet()) {
			
			Table foreignTable = database.findTable(foreignmap.getKey());

			if (foreignTable == null){
				continue;
			}
			addForeignKey(foreignTable, foreignmap.getValue(), 1);
			addForeignKey(foreignTable, foreignmap.getValue(), 2);
		}
    }

    /**
     * 再帰表以外の外部キー設定
     * 
     * @param foreignTable
     * @param references
     */
    private void addForeignKey(Table foreignTable, List<Reference> references) {
    	addForeignKey(foreignTable, references, null);
	}

    /**
     * 再帰表の外部キー設定
     * 
     * @param foreignTable
     * @param references
     * @param no
     */
    private void addForeignKey(Table foreignTable, List<Reference> references, Integer no) {
    	String foreignKeyName;
    	if (no != null) {
    		foreignKeyName = "FK_" + no.toString() + foreignTable.getName();
    	} else {
    		foreignKeyName = "FK_" + foreignTable.getName();    		
    	}
    	ForeignKey foreignKey = new ForeignKey(foreignKeyName);

		for (Reference ref : references) {
			foreignKey.addReference(ref);
		}
		foreignKey.setForeignTable(foreignTable);
		table.addForeignKey(foreignKey);
	}
}
