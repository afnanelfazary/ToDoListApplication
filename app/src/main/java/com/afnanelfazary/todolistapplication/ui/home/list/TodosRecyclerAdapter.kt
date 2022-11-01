package com.afnanelfazary.todolistapplication.ui.home.list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.afnanelfazary.todolistapplication.R
import com.afnanelfazary.todolistapplication.database.model.Todo
import com.afnanelfazary.todolistapplication.ui.home.add.AddTodoBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.zerobranch.layout.SwipeLayout


class TodosRecyclerAdapter(var items:MutableList<Todo>?) :RecyclerView.Adapter<TodosRecyclerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    var view=LayoutInflater.from(parent.context)
     .inflate(R.layout.item_todo,parent,false)
        return ViewHolder(view)

     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item=items!!.get(position)
        holder.title.setText(item.name)
        holder.description.setText(item.details)
        holder.delete.setOnClickListener{ onDeleteClickListener?.onItemClick(position,item) }

        holder.swipeLayout.setOnActionsListener(object :SwipeLayout.SwipeActionsListener{
                override fun onClose() {
                }

                override fun onOpen(direction: Int, isContinuous: Boolean) {
                    if(direction == SwipeLayout.RIGHT){
                        //      Toast.makeText(holder.itemView.context,"right",Toast.LENGTH_LONG)
////                          //      .show()
                    }else if(direction==SwipeLayout.LEFT){
                    //    Toast.makeText( holder.itemView.context ,"left",Toast.LENGTH_LONG)
////                        .show()
                    }
                }
            })
        holder.markAsDone.setOnClickListener {
            onIsDoneClickListener?.onItemClick(position, item)
            if (item.isDone == true) {

                holder.title.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.font_green
                    )
                );
                // holder.doneText.setVisibility(View.VISIBLE)
                holder.doneText.isVisible = true
                holder.markAsDone.isVisible = false
                holder.verticalLine.setBackgroundColor(Color.GREEN)


            }
        }
    }

    var onDeleteClickListener: OnItemClickListener? =null
    var onIsDoneClickListener: OnItemClickListener? =null
    var onUpdateClickListener: OnItemClickListener ? =null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,item:Todo)

    }



    fun changeData(newItems:MutableList<Todo>){
        items=newItems
        notifyDataSetChanged()//notify adapter when data changed

    }
    override fun getItemCount(): Int=items?.size?:0
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val markAsDone: ImageView = itemView.findViewById(R.id.mark_as_done)
        var swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe_layout)
        var delete: LinearLayout = itemView.findViewById(R.id.delete)
        val doneText: TextView = itemView.findViewById(R.id.mark_as_done_text)
        val content: ConstraintLayout = itemView.findViewById(R.id.content)
        val verticalLine: View = itemView.findViewById(R.id.line)


    }




}