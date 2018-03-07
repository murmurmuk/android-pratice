package murmur.toolbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.toolbar

class MainActivity : AppCompatActivity() {

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                Log.d("kanna", "touch home")
                true
            }
            R.id.menu_in_main_item1 -> {
                Log.d("kanna", "touch menu_in_main_item1")
                true
            }
            R.id.menu_in_main_item2 -> {
                Log.d("kanna", "touch menu_in_main_item2")
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


}
