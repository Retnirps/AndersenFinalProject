package com.majestadev.rickandmortyguide.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.majestadev.rickandmortyguide.R
import com.majestadev.rickandmortyguide.databinding.ActivityMainBinding
import com.majestadev.rickandmortyguide.main.fragment.main.ViewPagerListener

class MainActivity : AppCompatActivity(), ViewPagerListener {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root

        setSupportActionBar(binding?.toolbar)

        setContentView(view)
    }

    override fun onResume() {
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment_container))
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun pageChanged(stringId: Int) {
        supportActionBar?.title = resources.getString(stringId)
    }
}