package jp.co.hokuto.weatherreport.activity

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import jp.co.hokuto.weatherreport.R
import jp.co.hokuto.weatherreport.activity.fragment.MainFragment


class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		// TODO:指紋認証テスト
		if (Build.VERSION.SDK_INT >= 23) {
			val fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

			try {
				if (fingerprintManager.isHardwareDetected) {
					if (fingerprintManager.hasEnrolledFingerprints()) {
						Toast.makeText(this.applicationContext, "指紋登録されています。", Toast.LENGTH_SHORT).show()

						fingerprintManager.authenticate(null, null, 0, fingerManagerCallback(), null)

					} else {
						Toast.makeText(this.applicationContext, "指紋登録されていません。", Toast.LENGTH_SHORT).show()
					}
				} else {
					Toast.makeText(this.applicationContext, "指紋認証が搭載されていない端末です。", Toast.LENGTH_SHORT).show()
				}

			} catch (e : SecurityException) {
				Log.e("test", "エラー：" + e)
			}

		} else {
			goToMainFragment()
		}

	}


	override fun onResume() {
		super.onResume()
	}


	override fun onStart() {
		super.onStart()
	}


	/**
	 * Activity 破棄時にコール
	 */
	override fun onDestroy() {
		super.onDestroy()
	}


	/**
	 * メインFragment画面への遷移
	 */
	private fun goToMainFragment() {
		val fragment : Fragment = MainFragment()
		val transaction = supportFragmentManager.beginTransaction()
		transaction.add(R.id.fragment_container, fragment)
		transaction.addToBackStack(null)
		transaction.commit()
	}


	private fun fingerManagerCallback() : FingerprintManager.AuthenticationCallback {
		val listener : FingerprintManager.AuthenticationCallback = object : FingerprintManager.AuthenticationCallback() {
			/**
			 * エラー
			 */
			override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
				super.onAuthenticationError(errorCode, errString)

				Log.d("test", "(DEBUG)エラー")
			}

			/**
			 * 指紋認証 失敗
			 */
			override fun onAuthenticationFailed() {
				super.onAuthenticationFailed()

				Log.d("test", "(DEBUG)指紋認証 失敗")
			}

			/**
			 * 指紋認証 成功
			 */
			override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
				super.onAuthenticationSucceeded(result)

				Log.d("test", "(DEBUG)指紋認証 成功")

				goToMainFragment()
			}

			override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
				super.onAuthenticationHelp(helpCode, helpString)
			}
		}

		return listener
	}

}
