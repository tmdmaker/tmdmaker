Generator拡張機能作成
===============

それでは拡張機能を開発していきましょう。
まずはGeneratorを拡張した簡単なファイル出力サンプルを作成します。

コンテキストメニューに表示する名前を決める
---------------------

以下のメソッドをオーバーライドするとコンテキストメニューに表示する名前が決まります。グループ名はコンテキストメニュー表示される順番に影響します。::

    @Override
    public String getGeneratorName() {
       return "エンティティ一覧";
    }

    @Override
    public String getGroupName() {
       // TODO Auto-generated method stub
       return "雛形グループ";
    }

モデルの情報をファイルに出力する
----------------

以下が実行時の処理です。ファイルを作成してモデルの情報を出力しています。::

    @Override
    public void execute(String rootDir, List<AbstractEntityModel> models) {
        File file = new File(rootDir, "entity_name.txt");
        PrintWriter writer = null;
      
        try {
           writer = new PrintWriter(file, "UTF-8");
           for (AbstractEntityModel model : models) {
              writer.print(model.getName());
              writer.print(",");
              writer.println(model.getEntityType());
           }
        } catch (Exception e) {
            // TODO: 例外をthrow
        } finally {
           if (writer != null) {
              writer.flush();
              writer.close();
           }
        }
     }


これだけです。モデルの構成やEclipse Pluginの仕組みを理解すれば、より高度なデータ出力が実現可能です。