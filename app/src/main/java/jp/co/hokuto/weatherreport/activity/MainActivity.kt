package jp.co.hokuto.weatherreport.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.co.hokuto.weatherreport.R

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
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
}
