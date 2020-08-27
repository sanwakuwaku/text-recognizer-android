# text-recognizer-android
カメラで撮影した画像にOCR処理をかけて、認識したテキストを表示するサンプルです。  
OCR処理はAzure Cognitive Services の Computer Vision APIを使用しています。

<img src="https://github.com/sanwakuwaku/img/blob/a2e3759c4f1bdc9fc207cdd138a0a49744b9f63c/text-recognizer-android/text_recog_and_1.jpg?raw=true" width="200px" />      <img src="https://github.com/sanwakuwaku/img/blob/master/text-recognizer-android/text_recog_and_2.jpg?raw=true" width="200px" />

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

