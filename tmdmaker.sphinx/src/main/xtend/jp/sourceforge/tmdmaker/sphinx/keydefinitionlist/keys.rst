$entity.name
$delimiter

テーブル設計
---------------

.. list-table::
   :header-rows: 1

   * - 列名
     - 実装名
     - データ型
     - Null

#foreach($c in $columns)
   * - $c.get("name")
     - $c.get("implementName")
     - $c.get("type")
     - $c.get("null")
     
#end

キーの定義書
---------------

#if($keys.size() > 0)
.. list-table::
   :header-rows: 1

   * - データ
#foreach($key in $keys)
#if($key.masterKey == true)
     - N/M
#else
     - $velocityCount
#end
#end
#foreach($mapping in $mappings)
   * - $mapping.key.name
#foreach($rm in $mapping.value)
     - $rm.keyOrder
#end
#end
#else
キーは定義されていません。
#end