package com.flip.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    //Create our four fragments object
    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: SearchFragment
    lateinit var newFragment: NewFragment
    lateinit var favFragment: FavFragment
    lateinit var profileFragment: ProfileFragment
    private lateinit var dialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //now let's create our framelayout and bottomnav variables
        var bottomnav = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
        var frame = findViewById<FrameLayout>(R.id.frameLayout)
        //Now let's the deffault Fragment
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        //now we will need to create our different fragemnts
        //Now let's add the menu evenet listener
        bottomnav.setOnNavigationItemSelectedListener { item ->
            //we will select each menu item and add an event when it's selected
            when (item.itemId) {
                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.search -> {
                    searchFragment = SearchFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, searchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.new_set -> {
                    dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
                    val dialogView = layoutInflater.inflate(R.layout.bottom_dia, findViewById<LinearLayout>(R.id.create))
                    dialogView.findViewById<View>(R.id.buttonFolder).setOnClickListener {
                        Toast.makeText(this, "Folder", Toast.LENGTH_SHORT).show()
                    }

                    dialogView.findViewById<View>(R.id.buttonSet).setOnClickListener {
                        newFragment = NewFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frameLayout, newFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    }
                    dialog.setContentView(dialogView)
                    dialog.show()
                }

                R.id.favorite -> {
                    favFragment = FavFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, favFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.profile -> {
                    profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

            true
        }
        //Now let's Run our App
    }
}
