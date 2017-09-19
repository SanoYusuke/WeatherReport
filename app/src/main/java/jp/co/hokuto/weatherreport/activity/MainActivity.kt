package jp.co.hokuto.weatherreport.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import jp.co.hokuto.weatherreport.R
import jp.co.hokuto.weatherreport.activity.fragment.MainFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		val fragment : Fragment = MainFragment()
		val transaction = supportFragmentManager.beginTransaction()
		transaction.add(R.id.fragment_container, fragment)
		transaction.addToBackStack(null)
		transaction.commit()
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
