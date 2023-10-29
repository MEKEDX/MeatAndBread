package com.kh.mo.meatandbread

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.kh.mo.meatandbread.util.makeGone
import com.kh.mo.meatandbread.util.makeVisible
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var bottomBar: SmoothBottomBar
    private lateinit var controller: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        bottomBar = findViewById(R.id.bottomBar)
        attachBottomBarToNavGraph()
        disappearAndShowBottomNavigation()
    }

    private fun attachBottomBarToNavGraph() {
        val popupMenu = PopupMenu(this, bottomBar)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        controller = navHostFragment.navController
        bottomBar.setupWithNavController(menu, controller)

    }

    private fun disappearAndShowBottomNavigation() {
        controller.addOnDestinationChangedListener { _: NavController?,
                                                     destination: NavDestination,
                                                     _: Bundle? ->
            val destinationId = destination.id
            if (destinationId == R.id.homeF ||
                destinationId == R.id.cart||
                destinationId== R.id.waiting
            ) {
                bottomBar.makeVisible()
            } else {
                bottomBar.makeGone()

            }
        }
    }

}
