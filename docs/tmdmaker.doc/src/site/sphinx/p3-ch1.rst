拡張機能の雛形プロジェクトのセットアップ
====================

まずは拡張機能を開発するための準備をしましょう。

拡張機能の雛形プロジェクトダウンロード
-------------------

tmdmaker\_extensionsのダウンロードサイトから
拡張機能の雛形プロジェクト、 `tmdmaker_extension_scaffold_x.y.z.zip <https://osdn.net/projects/tmdmaker/releases/p15798/>`_ をダウンロードします。

解凍後にフォルダを任意の名前に変更してください。拡張機能の名前と合わせた方が分かりやすいです。

Eclipseセットアップ
-------------

Eclise 4.x系であれば多分大丈夫ですが、なるべく最新版をご利用ください。


拡張機能プロジェクトの取り込み
---------------

EclipseのFile-ImportからExisting projects into WorkspaceでNextを選び
Select root directoryで拡張機能のフォルダを選択してFinishを選択します。
インポート後にプロジェクトフォルダをリファクタリングして拡張機能に名前を付けます。

拡張機能の依存関係解決
-----------

まだプロジェクトにエラーが出ていますのTMD-Makerの必須プラグインとの依存関係を解決しましょう。
プロジェクトフォルダ直下のplugin-development.targetを開き
右上のReload Target Platformをクリックしてください。
しばらく待つとエラーが消えます。

お疲れ様でした。拡張機能の開発準備が整いました。

雛形の説明
-----
雛形には以下のクラスが既に用意されています。これらのクラスはplugin.xmlで関連付けされています。

- tmdmaker.extensions.scaffold.Actovator

  拡張機能起動時に呼び出されます。雛形では特に何もやっていません。

- tmdmaker.extensions.scaffold.generator.Generator1

  tmdmaker.generatorsを実装するインターフェースを継承したクラス。これからサンプルを実装します。  

  - tmdmaker.extensions.scaffold.importer.FileImporter1

  tmdmaker.importersを実装するインターフェースを継承したクラス。次にサンプルを実装します。
