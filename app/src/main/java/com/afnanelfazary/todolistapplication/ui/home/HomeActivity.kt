package com.afnanelfazary.todolistapplication.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.afnanelfazary.todolistapplication.R
import com.afnanelfazary.todolistapplication.databinding.ActivityHomeBinding
import com.afnanelfazary.todolistapplication.ui.home.add.AddTodoBottomSheet
import com.afnanelfazary.todolistapplication.ui.home.list.TodoListFragment
import com.afnanelfazary.todolistapplication.ui.home.settings.SettingsFragment
import com.google.android.material.navigation.NavigationBarView


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding  //defining the binding class
val  todoListFragment = TodoListFragment()
    val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_home)
         binding = ActivityHomeBinding.inflate(layoutInflater) //initializing the binding class
         val view = binding.root
        setContentView(view) // we now set the contentview as the binding.root
           binding.add.setOnClickListener {
//            val intent = Intent(this , UpdateTodoActivity::class.java)
//               startActivity(intent)
           showAddBottomSheet()
           }
         binding.bottomNavigationView.setOnItemSelectedListener(
             NavigationBarView.OnItemSelectedListener  {item->
             if (item.itemId== R.id.navigationList)
             {

              pushFragment(todoListFragment)
         }
                 else if(item.itemId== R.id.navigationSettings)
             {
                 pushFragment(settingsFragment)

             }
                 return@OnItemSelectedListener true
             })
        binding.bottomNavigationView.selectedItemId= R.id.navigationList


}

    private fun showAddBottomSheet() {
        val addBottomSheet = AddTodoBottomSheet()
        addBottomSheet.show(supportFragmentManager,"")

        //callback
        addBottomSheet.onTodoAddedListener=object : AddTodoBottomSheet.OnTodoAddedListener
        {
            override fun OnTodoAdded()  {
                //refresh Todos list from database inside listfragment
               if (todoListFragment.isVisible)
               {  todoListFragment.reloadTodosListFromDB()

             }}
        }

     }

    fun pushFragment(fragment: Fragment)
{

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container,fragment)
        .commit()
}

}