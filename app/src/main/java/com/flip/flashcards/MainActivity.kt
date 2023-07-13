package com.flip.flashcards

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    //Create our four fragments object
    lateinit var homeFragment: HomeFragment
    lateinit var searchFragment: EventsFragment
    lateinit var newFragment: NewFragment
    lateinit var favFragment: FavFragment
    lateinit var profileFragment: ProfileFragment
    private lateinit var dialog: BottomSheetDialog

    //Holds cards, but will be held into CardSetsModels later.
    val cardModels = ArrayList<CardModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //now let's create our framelayout and bottomnav variables
        val bottomnav = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
        var frame = findViewById<FrameLayout>(R.id.frameLayout)
        //Now let's the default Fragment
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        //now we will need to create our different fragments
        //Now let's add the menu event listener
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
                    searchFragment = EventsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, searchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.new_set -> {
                    dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
                    val dialogView = layoutInflater.inflate(
                        R.layout.bottom_dia,
                        findViewById<LinearLayout>(R.id.create)
                    )
                    dialogView.findViewById<View>(R.id.buttonFolder).setOnClickListener {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("New Folder Name")

                        // set the custom layout
                        val customLayout: View =
                            layoutInflater.inflate(R.layout.folder_naming, null)
                        builder.setView(customLayout)

                        // add a button
                        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                            // send data from the AlertDialog to the Activity
                            val editText = customLayout.findViewById<EditText>(R.id.editText)
                            val message = editText.text.toString()
                            val toast =
                                Toast.makeText(this, "Folder Made: $message", Toast.LENGTH_SHORT)
                            toast.show()

                        }
                        //removes modal bottom sheet after user selects 'Create new folder' option.
                        dialog.dismiss()
                        // create and show the alert dialog
                        val dialog = builder.create()
                        dialog.show()

                    }

                    dialogView.findViewById<View>(R.id.buttonSet).setOnClickListener {
                        newFragment = NewFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frameLayout, newFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                        //removes modal bottom sheet after user selects a new card set
                        dialog.dismiss()

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
        // calling this activity's function to
        // use ActionBar utility methods
        val actionBar = supportActionBar

        // providing title for the ActionBar
        actionBar!!.title = "  GfG | Action Bar"

        // providing subtitle for the ActionBar
        actionBar.subtitle = "   Design a custom Action Bar"

        // adding icon in the ActionBar
        actionBar.setIcon(R.drawable.app_logo)

        // methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

    }

    // method to inflate the options menu when
    // the user opens the menu for the first time
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // methods to control the operations that will
    // happen when user clicks on the action buttons
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
            R.id.refresh -> Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show()
            R.id.more-> Toast.makeText(this, "Copy Clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}
