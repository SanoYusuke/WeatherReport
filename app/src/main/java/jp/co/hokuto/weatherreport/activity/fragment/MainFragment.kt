package jp.co.hokuto.weatherreport.activity.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import jp.co.hokuto.weatherreport.R
import jp.co.hokuto.weatherreport.activity.adapter.RecyclerViewAdapter
import jp.co.hokuto.weatherreport.activity.listener.RecyclerItemClickListener
import jp.co.tecotec.wizard.connect.ImageFileDownloadTask



/**
 * メイン画面
 *
 * Created by 佐野 on 2017/09/06.
 */
class MainFragment : Fragment() {

	private var mUrlList : ArrayList<String> = ArrayList()
	private var mImageList : ArrayList<Bitmap> = ArrayList()

	private var mRecyclerView : RecyclerView ?= null
	private var mLayoutManager: RecyclerView.LayoutManager? = null

	companion object {
		val FRAGMENT_NAME: String = MainFragment::class.java.name
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater?.inflate(R.layout.fragment_main, container, false)

		mRecyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView
		mLayoutManager = LinearLayoutManager(activity.applicationContext)
		mRecyclerView?.layoutManager = mLayoutManager

		mRecyclerView?.addOnItemTouchListener(RecyclerItemClickListener(activity.applicationContext, object : RecyclerItemClickListener.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				Toast.makeText(activity.applicationContext, "number = " + position, Toast.LENGTH_SHORT).show()
			}
		}))

		return view
	}


	override fun onResume() {
		super.onResume()
	}


	override fun onStart() {
		super.onStart()

//		for (i in 0..) {
//			mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2016/09/summon_20161010_w2rbia.jpg")
//		}
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2016/09/summon_20161010_w2rbia.jpg")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/2017summer02_npl3q/summon_banner.png")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/2017summer_b0p2k/summon_banner.png")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/prisma_illya_nk6tn/summon_banner.png")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/kyoumafu2017_c8trc/top_banner.png")

		for (url in mUrlList) {
			connectImageFileDownload(url)
		}

	}


	override fun onStop() {
		super.onStop()
	}


	override fun onDestroy() {
		super.onDestroy()
	}


	/**
	 * Http通信での画像取得 実施
	 */
	private fun connectImageFileDownload(url : String) {
		ImageFileDownloadTask( url, getImageFileDownloadCallBack()).execute()
	}


	/**
	 * Http通信での画像取得コールバック
	 */
	private fun getImageFileDownloadCallBack() : ImageFileDownloadTask.CallbackTask {
		val callback : ImageFileDownloadTask.CallbackTask = object : ImageFileDownloadTask.CallbackTask(){
			override fun callback(result : Bitmap?){
				if (result == null) {
					return
				}

				mImageList.add(result)

				Log.d("test", "(DEBUG)通信完了 = " + result)

				setListAdapter()
			}
		}
		return callback
	}


	private fun setListAdapter() {
		if (mImageList.size > 0) {
			val adapter = RecyclerViewAdapter(mImageList)

			mRecyclerView?.adapter = adapter
		}
	}
}