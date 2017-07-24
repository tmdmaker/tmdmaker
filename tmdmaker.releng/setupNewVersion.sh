#!/bin/sh

if [ $1 == "" ]; then
echo "usage $0 x.y.z"
fi

NEW_VERSION=$1

sed -i -e "s/newVersion>[0-9.*]\.[0-9.*]\.[0-9.*]/newVersion>${NEW_VERSION}/g" pom.xml
sed -i -e "s/release-version>[0-9.*]\.[0-9.*]\.[0-9.*]/release-version>${NEW_VERSION}/g" pom.xml
mvn tycho-versions:set-version
sed  -i -e "s/Ver\.[0-9.*]\.[0-9.*]\.[0-9.*]/Ver.${NEW_VERSION}/g" ../tmdmaker.rcp/about.mappings
sed  -i -e "s/Ver\.[0-9.*]\.[0-9.*]\.[0-9.*]/Ver.${NEW_VERSION}/g" ../tmdmaker.rcp/plugin.xml
sed  -i -e "s/^\(<product.* version=\)\(\"[0-9.*]\.[0-9.*]\.[0-9.*]\"\)/\1\"${NEW_VERSION}\"/g" ../tmdmaker.rcp/tmdmaker_rcp.product
sed  -i -e "s/^\(<product.* version=\)\(\"[0-9.*]\.[0-9.*]\.[0-9.*]\"\)/\1\"${NEW_VERSION}\"/g" ../tmdmaker.releng.product/tmdmaker_rcp.product
sed  -i -e "s/version = \'[0-9.*]\.[0-9.*]\.[0-9.*]\'/version = \'${NEW_VERSION}\'/g" ../tmdmaker.doc/src/site/sphinx/conf.py
sed  -i -e "s/release = \'[0-9.*]\.[0-9.*]\.[0-9.*]-SNAPSHOT\'/release = \'${NEW_VERSION}-SNAPSHOT\'/g" ../tmdmaker.doc/src/site/sphinx/conf.py
