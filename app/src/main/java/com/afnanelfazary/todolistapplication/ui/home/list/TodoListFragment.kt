package com.afnanelfazary.todolistapplication.ui.home.list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afnanelfazary.todolistapplication.R
import com.afnanelfazary.todolistapplication.base.BaseFragment
import com.afnanelfazary.todolistapplication.clearTime
import com.afnanelfazary.todolistapplication.database.MyDataBase
import com.afnanelfazary.todolistapplication.database.model.Todo
import com.afnanelfazary.todolistapplication.ui.home.add.AddTodoBottomSheet
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

import java.util.*


class TodoListFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

     lateinit var recyclerView: RecyclerView
     lateinit var calendarView:MaterialCalendarView
     val adapter = TodosRecyclerAdapter(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    override fun onStart() {
        super.onStart()//once fragment is visible
    }
  override fun onResume(){
      super.onResume()//once user interact
      reloadTodosListFromDB()
  }
    //todos by date
      var calendar= Calendar.getInstance()



      fun reloadTodosListFromDB() {
      //    if (context==null)return
         val todosList=MyDataBase.getInstance(requireContext())
            .getTaskDao().getTodoByDate(calendar.clearTime().time) //calendar.time (return time inn millisecond
        adapter.changeData(todosList.toMutableList())
     }


     private fun initViews() {
        recyclerView=requireView().findViewById(R.id.todo_recycler)
        calendarView=requireView().findViewById(R.id.calendarView)
          calendarView.selectedDate= CalendarDay.today()
         recyclerView.adapter=adapter
         calendarView.setOnDateChangedListener {
                 widget,calendarDay,selected->
              Log.e("calendar day ",""+calendarDay.month)
             Log.e("calendar inside class ",""+calendar.get(Calendar.MONTH))

             calendar.set(Calendar.DAY_OF_MONTH,calendarDay.day)
             calendar.set(Calendar.MONTH,calendarDay.month-1)
             calendar.set(Calendar.YEAR,calendarDay.year)
             reloadTodosListFromDB()

             adapter.onDeleteClickListener = object :TodosRecyclerAdapter.OnItemClickListener{
                 override fun onItemClick(pos: Int, item: Todo) {
                     deleteTask(item)
                 }
             }
             adapter.onIsDoneClickListener=object :TodosRecyclerAdapter.OnItemClickListener{
                 override fun onItemClick(pos: Int, item: Todo) {
                    isDoneTask(item)
                 }
             }
             adapter.onUpdateClickListener=object :TodosRecyclerAdapter.OnItemClickListener{
                 override fun onItemClick(pos: Int, item: Todo) {

                     updateTask(item)
                  }
             }
         }}


    fun deleteTask(task:Todo){

        showMessage("Are you sure you want to delete this task ?",
            posActionTitle = "yes",
            posAction = { dialogInterface, i ->
                dialogInterface?.dismiss()
                MyDataBase.getInstance(requireContext())
                    .getTaskDao()
                    .deleteTodo(task)
                reloadTodosListFromDB() },
            negActionTitle = "cancel",
            negAction = DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface?.dismiss()
            }
        )



    }
    fun isDoneTask(task: Todo){
        task.isDone=true
        MyDataBase.getInstance(requireContext())
            .getTaskDao()
            .updateTodo(task)
        reloadTodosListFromDB() }
    fun updateTask(task: Todo){
        MyDataBase.getInstance(requireContext())
            .getTaskDao()
            .updateTodo(task)
            reloadTodosListFromDB()




        //callback
       // addBottomSheet.onTodoAddedListener=object : AddTodoBottomSheet.OnTodoAddedListener
       // {
        //    override fun OnTodoAdded()  {
                //refresh Todos list from database inside listfragment
//                if (TodoListFragment.isVisible)
//                {  TodoListFragment.reloadTodosListFromDB()
//
//                }}
       // }

    }

}



