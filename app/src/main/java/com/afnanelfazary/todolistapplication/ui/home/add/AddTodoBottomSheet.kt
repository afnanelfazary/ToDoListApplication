package com.afnanelfazary.todolistapplication.ui.home.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Update
import com.afnanelfazary.todolistapplication.R
import com.afnanelfazary.todolistapplication.clearTime
import com.afnanelfazary.todolistapplication.database.MyDataBase
import com.afnanelfazary.todolistapplication.database.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class AddTodoBottomSheet  :BottomSheetDialogFragment() {
 lateinit var titleLayout: TextInputLayout
    lateinit var detailsLayout: TextInputLayout
    lateinit var chooseDate: TextView
    lateinit var addTodo: Button
    lateinit var todo_title: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()


    }

    private fun  initViews() {
        titleLayout = requireView().findViewById(R.id.title_layout)
        detailsLayout = requireView().findViewById(R.id.details_layout)
        chooseDate = requireView().findViewById(R.id.chooseDate)
        todo_title  =requireView().findViewById(R.id.todo_title)
        chooseDate.setText(  ""+ calendar.get(Calendar.DAY_OF_MONTH)+"/"+
                (calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR))


        addTodo = requireView().findViewById(R.id.add_btn)
        chooseDate.setOnClickListener { showDatePicker() }
        addTodo.setOnClickListener {
            if (validateForm())
            {
                //form is valid and insert Todo in database
                val title = titleLayout.editText?.text.toString()
                val details = detailsLayout.editText?.text.toString()
                insertTodo(title,details)
            }
        }
    }



      fun insertTodo(title: String, details: String) {
      val  todo = Todo(
          name=title,
          details = details,
          date=calendar.clearTime().time, //time is included
          isDone = false

      )

        MyDataBase.getInstance(requireContext().applicationContext)
            .getTaskDao()
             .insertTodo(todo)

        Toast.makeText(requireContext(),"Todo added successfully",Toast.LENGTH_LONG)
            .show()
        //call back to ACtivity to notify  i nsertion
        onTodoAddedListener?.OnTodoAdded()
        dismiss()
    }


    var onTodoAddedListener: OnTodoAddedListener?=null
         interface  OnTodoAddedListener{
             fun OnTodoAdded()



         }
    fun validateForm():Boolean{
        var isValid = true
        if (titleLayout.editText?.text.toString().isBlank()) {
            titleLayout.error = "please enter todo title"
            isValid=false
        }
        else{
            titleLayout.error=null
        }

        if (detailsLayout.editText?.text.toString().isBlank()) {
            detailsLayout.error = "please enter todo details"
            isValid=false

        }
        else{
            detailsLayout.error=null
        }
return isValid

    }
    val calendar=Calendar.getInstance()

    fun showDatePicker(){
         val datePicker=DatePickerDialog(
             requireContext(),
             { p0, year, month, day ->
                 calendar.set(Calendar.DAY_OF_MONTH,day)
                 calendar.set(Calendar.MONTH,month)
                 calendar.set(Calendar.YEAR,year)

                //month +1 ->(month start from  0)
                 chooseDate.setText(""+day+"/"+(month+1)+"/"+year)

             },

           calendar.get( Calendar.YEAR),
             calendar.get( Calendar.MONTH),
             calendar.get( Calendar.DAY_OF_MONTH),
          )

          datePicker.show()

     }
}