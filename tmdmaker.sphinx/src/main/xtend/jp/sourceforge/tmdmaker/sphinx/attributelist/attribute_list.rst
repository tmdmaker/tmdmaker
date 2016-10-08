アトリビュートリスト
=====================

#foreach($attr in $attributes.entrySet())
$attr.key
-------------------------------------------------------

.. toctree::
   :maxdepth: 1

#foreach($entry in $attr.value.entrySet())
   attribute_list/${entry.value.createAttributeFileKey()}
#end

#end