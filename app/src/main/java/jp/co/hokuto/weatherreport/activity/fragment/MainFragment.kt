package jp.co.hokuto.weatherreport.activity.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import jp.co.hokuto.weatherreport.R
import jp.co.hokuto.weatherreport.activity.adapter.RecyclerViewAdapter
import jp.co.hokuto.weatherreport.activity.data.ImageData
import jp.co.hokuto.weatherreport.activity.listener.RecyclerItemClickListener
import jp.co.tecotec.wizard.connect.ImageFileDownloadTask



/**
 * メイン画面
 *
 * Created by 佐野 on 2017/09/06.
 */
class MainFragment : Fragment() {

	private var mUrlList : ArrayList<String> = ArrayList()
	private var mImageList : ArrayList<ImageData> = ArrayList()

	private var mRecyclerView : RecyclerView ?= null
	private var mLayoutManager: RecyclerView.LayoutManager? = null

	private var mSwipeRefresh : SwipeRefreshLayout ?= null

	companion object {
		val FRAGMENT_NAME: String = MainFragment::class.java.name
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater?.inflate(R.layout.fragment_main, container, false)

		// RecyclerViewの取得
		mRecyclerView = view?.findViewById(R.id.recycler_view) as RecyclerView
		mLayoutManager = LinearLayoutManager(activity.applicationContext)
		mRecyclerView?.layoutManager = mLayoutManager
		mRecyclerView?.addOnItemTouchListener(RecyclerItemClickListener(activity.applicationContext, object : RecyclerItemClickListener.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				Toast.makeText(activity.applicationContext, "number = " + position, Toast.LENGTH_SHORT).show()
			}
		}))

		// SwipeRefreshの取得
		mSwipeRefresh = view.findViewById(R.id.swipe_refresh) as SwipeRefreshLayout
		mSwipeRefresh?.setOnRefreshListener {
			loadData()
		}

		return view
	}


	override fun onResume() {
		super.onResume()
	}


	override fun onStart() {
		super.onStart()

		loadData()
	}


	override fun onStop() {
		super.onStop()
	}


	override fun onDestroy() {
		super.onDestroy()
	}


	private fun loadData() {
		mUrlList.clear()

		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2016/09/summon_20161010_w2rbia.jpg")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/2017summer02_npl3q/summon_banner.png")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/2017summer_b0p2k/summon_banner.png")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/prisma_illya_nk6tn/summon_banner.png")
		mUrlList.add("http://news.fate-go.jp/wp-content/uploads/2017/kyoumafu2017_c8trc/top_banner.png")

		for (url in mUrlList) {
			connectImageFileDownload(url)
		}

		// 更新完了
		if (mSwipeRefresh?.isRefreshing == true) {
			mSwipeRefresh?.isRefreshing = false
		}
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

				// 画像ファイルから代表色を抽出する
				val palette : Palette = Palette.from(result).generate()

				val imageData : ImageData = ImageData()
				imageData.imageBitmap = result
				imageData.imageColor = palette.lightVibrantSwatch?.rgb ?:Color.WHITE

				mImageList.add(imageData)

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