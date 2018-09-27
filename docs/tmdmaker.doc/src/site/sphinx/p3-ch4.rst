Importer拡張機能作成
==============
次はImporterを拡張したサンプルを作成します。
テキストファイルからラピュタ名を取得して一括作成してみます。

コンテキストメニューに表示する名前を決める
---------------------

以下のメソッドをオーバーライドするとコンテキストメニューに表示する名前が決まります。::

    @Override
    public String getImporterName() {
       return "ラピュタ一括作成";
    }

取り込み可能な拡張子を決める
--------------

以下のメソッドをオーバーライドして取り込み可能なファイルの拡張子を定義します。今回はテキスト形式のみとします。::

   @Override
   public String[] getAvailableExtensions() {
      return new String[] {"txt"};
   }

ラピュタ名をファイルから取得してモデルを作成する
------------------------

以下が実行時の処理です。ファイルを作成してモデルの情報を出力しています。::

   @Override
   public List<AbstractEntityModel> importEntities(String filePath) throws FileNotFoundException, IOException {
      BufferedReader reader = null;
      List<AbstractEntityModel> results = new ArrayList<AbstractEntityModel>();
      try {
         reader = new BufferedReader(new FileReader(filePath));
         String line = null;
         while ((line = reader.readLine()) != null) {
            Laputa l = Laputa.of(new ModelName(line));
            results.add(l);
         }
         
         return results;
      } catch (Exception e) {
         return Collections.emptyList();
      } finally {
         if (reader != null)
            reader.close();
      }
   }

TMD-Makerで動作確認
--------------

TMD-Makerへのインストールと動作確認はGeneratorと同じです。
モデルが作成されましたか？
モデルのAPIやEclipse Pluginの仕組みを理解すれば、より高度なデータ作成が実現可能です。