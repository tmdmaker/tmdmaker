# TMD-Maker

TMD-Makerは、佐藤正美氏が考案したTM(T字形ER手法)の表記法であるTMD(T字形ER図)を作成するツールです。

このプロジェクトは、TMによるシステム開発をサポートする関連ソフトウェアをオープンソースソフトウェアとして提供します。

TMとは、ビジネスを解析しながらデータベース構造も同時に作る手法です。TMとTMDの詳細は株式会社SDIのサイトを参照してください。

TMD-Maker is a tool to create a TMD (T-formed ER diagram).
T-formed ER diagram is the notation of the T-formed ER method Mr. Masami Sato was invented.
This method is a technique to make the database structure while at the same time to analyze the business.

- About TMD-Maker

 https://osdn.jp/projects/tmdmaker/

- About TMD(T-formed ERD)

 http://www.sdi-net.co.jp/english-index.htm

## Requirements

### plugin
- java8

- eclipse 3.x or later(4.5 is recommended)

### RCP(java application)
- java8

## Download
https://osdn.jp/projects/tmdmaker/releases/

## Build

```
git clone https://github.com/tmdmaker/tmdmaker.git

cd tmdmaker.releng

mvn clean install
```

## Installation
### plugin
1. Unzip tmdmaker.releng.plugin/target/tmdmaker__**version**_.zip.
1. Put it into dropins directory.
1. Run eclipse.

### RCP(java application)
1. Unzip  tmdmaker.releng.product/target/products/`tmdmaker _for_rcp_`_**version**_-_**os**_._**ws**_._**arch**_.zip.
1. Run tmdmaker (run TMD-Maker, for macosx).

## Usage
1. Create a project.
1. Create New->Other->TMD-Maker->TM Diagram.

## Issues
https://osdn.jp/projects/tmdmaker/ticket/

## Licence
ASL

## Alternatives
- [TER-MINE](https://www.its-inc.co.jp/products/index.html)
- [modebi](http://www.modebi.jp/)
- [freeThinker](http://members3.jcom.home.ne.jp/4054315601/tools.html)
