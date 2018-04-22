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

### Java Application(Eclipse RCP)
- java8

### Eclipse Plugin
- java6 or later(java8 is recommended).

- eclipse 3.4 or later(eclipse 4.7 is recommended).

- GEF(MVC) 3.x（Eclipse Graphical Editing Framework）.

- (Optional) Xtend IDE 2.11 and JavaSE8 ExecutionEnvirnment(Eclipse 4.4 or later).If you use tmdmaker sphinx plugin.

## Download
https://osdn.jp/projects/tmdmaker/releases/

## Build

```
git clone https://github.com/tmdmaker/tmdmaker.git

cd tmdmaker

mvn clean verify
```

## Installation

### Java Application(Eclipse RCP)
1. Extract  tmdmaker.releng.product/target/products/`tmdmaker_`_**version**_-_**os**_._**ws**_._**arch**_.zip/tar.gz.
1. Run tmdmaker (run TMD-Maker.app, for macosx).

### Eclipse Plugin
#### use dropins
1. Install GEF3.x and Xtend from updatesite.
1. Unzip tmdmaker.releng.plugin/target/`tmdmaker_plugin_`_**version**_.zip.
1. Put it into dropins directory.
1. Run eclipse.

#### use updatesite
1. Select Help->Install New Software....
1. Click Add -> archive button.
1. Select `tmdmaker_plugin_updatesite`_**version**_.zip.
1. Check TMD-Maker plugin, and finish the updatesite.

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
