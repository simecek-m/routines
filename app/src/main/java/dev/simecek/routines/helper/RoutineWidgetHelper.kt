package dev.simecek.routines.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.R
import dev.simecek.routines.list.RoutineListAdapter
import dev.simecek.routines.listener.DeleteRoutineListener
import javax.inject.Inject

class RoutineWidgetHelper @Inject constructor(@ApplicationContext context: Context) {

    private var deleteRoutineListener: DeleteRoutineListener? = null

    private val deleteIcon: Bitmap by lazy {
        val icon = ContextCompat.getDrawable(context, R.drawable.ic_trash)!!
        icon.setTint(ContextCompat.getColor(context, R.color.error))
        icon.toBitmap(80, 80)
    }

    private val swipeToDeleteCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            deleteRoutineListener?.onDeleteRoutineFromPosition(viewHolder.adapterPosition)
        }

        // disable Titles ViewHolder swipe
        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return when(viewHolder.itemViewType) {
                RoutineListAdapter.TITLE_VIEW_TYPE -> 0
                else -> return super.getSwipeDirs(recyclerView, viewHolder)
            }
        }

        // draw trash icon while swiping
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            val view = viewHolder.itemView
            val topMargin = view.height / 2 - deleteIcon.height /2
            if(dX > deleteIcon.width) {
                c.drawBitmap(deleteIcon, view.left.toFloat(), view.top.toFloat() + topMargin, null)
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    fun swipeToDeleteGesture(deleteRoutineListener: DeleteRoutineListener): ItemTouchHelper {
        this.deleteRoutineListener = deleteRoutineListener
        return ItemTouchHelper(swipeToDeleteCallback)
    }

}