package com.thinkdevs.smartpolicing

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_logout -> {
                areSurelogout()
                Apps.pref.islogin = false
                if (Apps.pref.islogin){
                    item.isVisible = true
                }
                item.isVisible = false
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun areSurelogout() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure?")
            .setContentText("You want to Logout ?")
            .setConfirmText("Yes")
            .setCancelText("No")
            .showCancelButton(true)
            .setConfirmClickListener {
                sDialog -> sDialog.dismissWithAnimation()
                onBackPressed()

            }
            .setCancelClickListener {
                    sDialog -> sDialog.dismissWithAnimation()
            }
            .show()
    }
}