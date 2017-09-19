package jp.co.tecotec.wizard.connect

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.net.HttpURLConnection
import java.net.URL

/**
 * Http通信で画像を取得し、保存する
 *
 * Created by 佐野 on 2017/09/06.
 */
class ImageFileDownloadTask(url : String, callback: CallbackTask) : AsyncTask<Void, Void, String>() {

	private val param = url

	private var mBmp: Bitmap? = null


	/** Task 終了時にコールバックする関数. */
	private val mCallbackTask : CallbackTask = callback


	/**
	 * 非同期処理
	 * 通信処理を行う
	 *
	 * @param params 空データ
	 * @return レスポンスボディデータ
	 */
	override fun doInBackground(vararg params: Void): String? {
		mBmp = downloadImage(param)

		return null
	}


	/**
	 * 非同期処理の後処理
	 * プログレスダイアログを非表示にする
	 *
	 * @param result 通信結果
	 */
	override fun onPostExecute(result: String?) {
		mCallbackTask.callback(mBmp)
	}


	/**
	 * HTTP通信をし、画像を取得する
	 *
	 * @param address
	 */
	private fun downloadImage(address: String): Bitmap? {
		try {
			val url = URL(address)

			// HttpURLConnection インスタンス生成
			val urlConnection = url.openConnection() as HttpURLConnection

			// タイムアウト設定
			urlConnection.readTimeout = 10000
			urlConnection.connectTimeout = 20000

			// リクエストメソッド
			urlConnection.requestMethod = "GET"

			// リダイレクトを自動で許可しない設定
			urlConnection.instanceFollowRedirects = false

			// ヘッダーの設定(複数設定可能)
			urlConnection.setRequestProperty("Accept-Language", "jp")

			// 接続
			urlConnection.connect()

			val resp = urlConnection.responseCode

			when (resp) {
				HttpURLConnection.HTTP_OK -> {
					val inputStream = urlConnection.inputStream
					mBmp = BitmapFactory.decodeStream(inputStream)
					inputStream.close()
				}
				HttpURLConnection.HTTP_UNAUTHORIZED -> {
				}
				else -> {
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
			return null
		}

		return mBmp
	}


	/*---------- Static Method ----------*/
	/**
	 * AsyncTask のコールバック用クラス
	 */
	open class CallbackTask {
		/**
		 * コールバックメソッド
		 * (ここでは実装せず、AsyncTask 使用元で処理終了時の実装をする)
		 *
		 * @param result doInBackground の結果
		 */
		open fun callback(result: Bitmap?) {}
	}
}