リレーションシップの検証表
===========================

.. list-table::
   :header-rows: 1

   * - 
#foreach($entity in $entities)
     - $entity.name
#end
#foreach($mapping in $mappings)
#set($isWrite = true)
   * - $mapping.key.name
#foreach($rm in $mapping.value)
#if($isWrite == true)
#if($rm.relationship == true)
     - ○
#else
     - ×
#end
#else
     - \-
#end
#if($rm.target == $mapping.key)
#set($isWrite = false)
#end
#end
#end