package com.actonica.devmenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.dm_devmenu_activity.*

internal class DevMenuActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var devMenu: DevMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dm_devmenu_activity)

        this.title = this.getString(R.string.dmTitleDeveloperMenu)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.devMenu = DevMenuProvider.getMenu()
        this.lifecycle.addObserver(devMenu)

        this.devMenu.items.forEach {
            this.linearLayout.addView(it.getView(this.layoutInflater, this.linearLayout))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menuInflater.inflate(R.menu.dm_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            R.id.dmShare -> {
                val sendIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.type = "text/plain"
                    this.putExtra(Intent.EXTRA_TEXT, this@DevMenuActivity.devMenu.getData())
                }

                Log.d("DevMenuActivity", "data = ${this.devMenu.getData()}")

                startActivity(
                    Intent.createChooser(
                        sendIntent,
                        this.resources.getText(R.string.dmTitleShare)
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        DevMenuLauncher.startShakeDetection(this.applicationContext, this.devMenu)
    }

    override fun onStart() {
        super.onStart()
        DevMenuLauncher.stopShakeDetection()
    }
}