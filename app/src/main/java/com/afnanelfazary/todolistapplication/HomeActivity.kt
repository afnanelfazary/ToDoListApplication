package com.afnanelfazary.todolistapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.afnanelfazary.todolistapplication.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationBarView


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding  //defining the binding class

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
             if (item.itemId==R.id.navigationList)
             {

              pushFragment(TodoListFragment())
         }
                 else if(item.itemId==R.id.navigationSettings)
             {
                 pushFragment(SettingsFragment())

             }
                 return@OnItemSelectedListener true
             })
        binding.bottomNavigationView.selectedItemId=R.id.navigationList


}

    private fun showAddBottomSheet() {
        val addBottomSheet = AddTodoBottomSheet()
        addBottomSheet.show(supportFragmentManager,"")
     }

    fun pushFragment(fragment: Fragment)
{

    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container,fragment)
        .commit()
}

}