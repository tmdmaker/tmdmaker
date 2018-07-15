TMD-Maker Pluginのインストール
=======================

TMD-Maker Pluginは、EclipseのPluginです。普段Eclipseを使用している人や、説明不足のマニュアル作成者に文句を言わずに自分で道を切り開ける人にお勧めします。少々脅してしまいましたが、あまり身構えずに一度深呼吸してから始めましょう。

動作環境
----

-  対応OS

   恐らくJava6以降が動作するOSなら動作します

-  Java 6 Runtime　または Java 6 SDK
   以降（セキュリティを考慮してJava8以降の最新版をお勧めします）

-  Eclipse 3.4 以上（4.7推奨）

   現在、Eclipse
   4.8.0(Photon)で開発しています。またPluginの動作確認では、バージョン3.4.2以降を使用していますので、多分バージョン3.5以降と4.2以降で動作します。もしかしたらまだバージョン3.3でも動作するかも知れません。

-  GEF(Eclipse Graphical Editing Framework) 

   GEF(MVC) 3.x系を事前にEclipseにインストールしておいてください。Eclipseのバージョンによっては呼び名が異なりますが、4.x系・5.x系では動作しません（今のところ・・・）。
   
新規インストール
--------

1. Java Runtime or SDKのインストール

   Runtimeをインストールする場合は、 `こちら <https://www.java.com/ja/download/>`_ から各自の環境に対応するファイルをダウンロードする。

   SDKをインストールする場合は、 `こちら <http://www.oracle.com/technetwork/java/javase/downloads/index.html>`_ から各自の環境に対応するファイルをダウンロードする。

   ダウンロードしたファイルをダブルクリックして、あとは指示に従って進めればインストールできるでしょう。

   既にインストール済みの人は、さっさと先に進みましょう。

2. Eclipseのインストール

   `Eclipseのサイト <http://www.eclipse.org/downloads/eclipse-packages/>`_ からダウンロードする。どのエディションを選んでも良いです。

   ダウンロードしたファイルを解凍して任意の場所に置く。

   既にインストール済みの人は、さっさと先に進みましょう。

3. GEFのインストール

   更新サイトからインストールするのが多分簡単です。

   Help->Install New Software からGEF(MVC) 3.x系を探してインストールしてください。

   既にインストール済みの人は、さっさと先に進みましょう。

4. TMD-Maker Pluginのインストール

   tmdmaker\_pluginのダウンロードサイトからtmdmaker\_plugin\_X.Y.Z.zip(X.Y.Zは任意のバージョン)をダウンロードする。

   ダウンロードしたファイルを解凍し、同梱されているjarファイルをeclipseのplugins（またはdropins）フォルダに置く。

   .. tip::
      TMD-Maker Pluginのお勧めインストール方法

      tmdmaker\_plugin\_X.Y.Z.zipを解凍すると、tmdmaker\_plugin\_X.Y.Z →
      plugins/features → tmdmaker～\_X.Y.Z.jar
      というフォルダ構成になっています。
      
      このtmdmaker\_plugin\_X.Y.Zフォルダをそのままdropinsフォルダに置けば、他のプラグインと混在することがないので、バージョンアップの際等にとても便利です。

5. Eclipseの起動

   Eclipseをインストールしたフォルダ内の、eclipse.exeをダブルクリックする。

   Eclipseが起動したらインストールされているプラグインを見る。tmdmakerがリストにあれば、おめでとう！インストールは完了です。

バージョンアップ
--------

1. 旧バージョンのプラグインを削除

   eclipseのplugins（またはdropins）フォルダに置いたTMD-Makerのjarファイルを削除します。

2. TMD-Maker Pluginの再インストール

   TMD-Maker
   Pluginのインストール手順で最新のファイルを新しいプラグインを上記方法でeclipseのplugins（またはdropins）フォルダに置く。

3. Eclipseの再起動

   Eclipseを-cleanオプションを付けて再起動する。

次はTMダイアグラムを作成する準備をしましょう。
