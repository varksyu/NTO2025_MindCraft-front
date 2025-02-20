package ru.myitschool.work.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.work.R
import ru.myitschool.work.ui.login.LoginDestination
import ru.myitschool.work.ui.login.LoginFragment
import ru.myitschool.work.ui.qr.scan.QrScanDestination
import ru.myitschool.work.ui.qr.scan.QrScanFragment

// НЕ ИЗМЕНЯЙТЕ НАЗВАНИЕ КЛАССА!
@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val userRole = intent.getStringExtra("USER_ROLE")

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?



        val navController = navHostFragment?.navController ?: throw IllegalStateException("NavHostFragment not found")
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        //navController.setGraph(R.navigation.main_nav_graph)
        //navController.navigate(R.id.fragment_profile)
        Log.d("role", "$userRole")
        if (userRole == "ROLE_ADMIN") {
            navController.setGraph(R.navigation.main_nav_graph)
            navController.navigate(R.id.fragment_profile)
        } else {
            navController.setGraph(R.navigation.main_nav_graph)
            bottomNavigationView.menu.clear()
            navController.navigate(R.id.fragment_profile)

        }




        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onSupportNavigateUp()
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        val popBackResult = if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            false
        }
        return popBackResult || super.onSupportNavigateUp()
    }

}