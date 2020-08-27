# text-recognizer-android
カメラで撮影した画像をOCR処理して認識したテキストを表示するサンプルです。
OCR処理はazure computer visionを使用しています。

## ビルド方法
Android Studio経由でビルドするかターミナルからgradle wapperを叩いてビルドしてください。

`$./gradlew installDebug`

## アプリ実行に必要なもの
- カメラ撮影するので実機が必要
- Azureサブスクリプション (無料でアカウント作成できる)  
  - アカウント作成方法はこのページの前提条件を参照  
  https://docs.microsoft.com/ja-jp/azure/cognitive-services/computer-vision/quickstarts-sdk/client-library?pivots=programming-language-java

Azureのsubscription key と endpointをgradle.propertiesに設定してください。
設定しなくてもアプリの起動はできますが認識に失敗します。

