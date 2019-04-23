#!/bin/sh

if [ $# == 0 ]; then
echo "usage $0 x.y.z"
exit -1
fi

NEW_VERSION=$1

sed  -i "" -e "s/release-version>[0-9.*]\.[0-9.*]\.[0-9.*]/release-version>${NEW_VERSION}/g" releng/jp.osdn.tmdmaker.configuration/pom.xml
mvn tycho-versions:set-version
sed  -i "" -e "1,16 s/<version>[0-9.*]\.[0-9.*]\.[0-9.*]\-SNAPSHOT<\/version>/<version>${NEW_VERSION}-SNAPSHOT<\/version>/" pom.xml
sed  -i "" -e "16,100 s/<version>[0-9.*]\.[0-9.*]\.[0-9.*]\-SNAPSHOT<\/version>//" pom.xml
sed  -i "" -e "s/<version>[0-9.*]\.[0-9.*]\.[0-9.*]\-SNAPSHOT<\/version>/<version>${NEW_VERSION}-SNAPSHOT<\/version>/" releng/jp.osdn.tmdmaker.configuration/pom.xml
sed  -i "" -e "s/Ver\.[0-9.*]\.[0-9.*]\.[0-9.*]/Ver.${NEW_VERSION}/g" bundles/tmdmaker.rcp/about.mappings
sed  -i "" -e "s/Ver\.[0-9.*]\.[0-9.*]\.[0-9.*]/Ver.${NEW_VERSION}/g" bundles/tmdmaker.rcp/plugin.xml
sed  -i "" -e "s/^\(<product.* version=\)\(\"[0-9.*]\.[0-9.*]\.[0-9.*]\"\)/\1\"${NEW_VERSION}\"/g" bundles/tmdmaker.rcp/tmdmaker_rcp.product
sed  -i "" -e "s/^\(<product.* version=\)\(\"[0-9.*]\.[0-9.*]\.[0-9.*]\"\)/\1\"${NEW_VERSION}\"/g" releng/tmdmaker.releng.product/tmdmaker_rcp.product
sed  -i "" -e "s/version = \'[0-9.*]\.[0-9.*]\.[0-9.*]\'/version = \'${NEW_VERSION}\'/g" docs/tmdmaker.doc/src/site/sphinx/conf.py
sed  -i "" -e "s/release = \'[0-9.*]\.[0-9.*]\.[0-9.*]-SNAPSHOT\'/release = \'${NEW_VERSION}-SNAPSHOT\'/g" docs/tmdmaker.doc/src/site/sphinx/conf.py
