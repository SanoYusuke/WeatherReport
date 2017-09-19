package jp.co.hokuto.weatherreport.activity.listener

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View


/**
 * RecyclerListViewのアイテム選択
 *
 * Created by 佐野 on 2017/09/14.
 */
class RecyclerItemClickListener(context: Context, private val mListener: RecyclerItemClickListener.OnItemClickListener?) : RecyclerView.OnItemTouchListener {
	override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

	}

	internal var mGestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
		override fun onSingleTapUp(e: MotionEvent): Boolean {
			return true
		}
	})

	override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
		// タッチした箇所のViewを取得
		val childView = view.findChildViewUnder(e.x, e.y)
		if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
			// onInterceptTouchEventのタイミングだとアイテムのtouch feedbackがつく前にonItemClickが呼ばれてしまうので、明示的にsetPressed(true)を呼んでいます。
			childView.isPressed = true
			mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
		}
		return false
	}

	override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {
		// Do nothing
	}

	interface OnItemClickListener {
		fun onItemClick(view: View, position: Int)
	}

}