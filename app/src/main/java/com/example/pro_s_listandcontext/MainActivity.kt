package com.example.pro_s_listandcontext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val listData1 = mutableListOf<String>()
    val listTotal = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. set the Button's function
        // add the item in the listView
        button.setOnClickListener {
            listData1.add(editText.text.toString())

            for (i in listData1.indices){
                val map = HashMap<String, Any>()

                map["data1"] = listData1[i]

                listTotal.add(map)
            }
            val keys = arrayOf("data1")
            val viewPosition = intArrayOf(R.id.list_textView)

            val adapter1 = SimpleAdapter(this, listTotal, R.layout.list_item, keys, viewPosition)
            list_view.adapter = adapter1
            listData1.clear()
        }
        // set the contextView on the listView
        registerForContextMenu(list_view)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // 3. val for list_item index
        var position = 0

        // 4. when you click the contextMenu, get the position of list_item index
        when(item.itemId){
            R.id.context_delete, R.id.context_write -> {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                position = info.position
            }
        }

        when(item.itemId){
            // if you click the delete menu form ContextMenu
            // 5. set the function for each ContextView
            R.id.context_delete -> {
                // 6. set the function for each list_view item
                // match the position that you clicked and list_item index
                for (i in listTotal.indices){
                    if (position == i){
                        // 7. if Step.6 is right, delete that list_item
                            // remove the list item data from totalListData
                        listTotal.removeAt(position)
                        // 8. rebuild the listView
                        val keys = arrayOf("data1")
                        val viewPosition = intArrayOf(R.id.list_textView)

                        val adapter1 = SimpleAdapter(this, listTotal, R.layout.list_item, keys, viewPosition)
                        list_view.adapter = adapter1
                    }
                }
            }
            // if you click the write menu form ContextMenu
            R.id.context_write -> {

            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        when(v?.id){
            R.id.list_view -> {
                // 2. val for item information in listView
                val info = menuInfo as AdapterView.AdapterContextMenuInfo

                menu?.setHeaderTitle("상세메뉴${info.position}")
                menuInflater.inflate(R.menu.context_menu, menu)
            }
        }
    }
}