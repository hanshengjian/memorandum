package com.hy.memorandum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hy.bedone.BeDoneFragment
import com.hy.common.flutter.MemFlutterActivity
import com.hy.common.flutter.MemFlutterConstants
import com.hy.note.ui.NoteListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var viewpager: ViewPager? = null
    var tablayout:TabLayout?=null
     val images = intArrayOf(
        R.drawable.ic_tab_selector_cc,
        R.drawable.ic_tab_selector_pc
    )
     val tabs = arrayOf("笔记", "待办")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        viewpager = findViewById(R.id.viewpager)
        viewpager?.adapter = MyPagerAdapter(supportFragmentManager)
        tablayout= findViewById(R.id.tablayout)
        tablayout!!.setupWithViewPager(viewpager)
        tabs.forEach {
            var tabview:View = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
            var imageView = tabview.findViewById<ImageView>(R.id.tabImageView);
            var textView = tabview.findViewById<TextView>(R.id.tabTextView);
            textView.setText(it);
            imageView.setImageResource(images[tabs.indexOf(it)]);

            tablayout!!.getTabAt(tabs.indexOf(it))!!.setCustomView(tabview);
        }

        top_menu_iv.setOnClickListener {
            MemFlutterActivity.toSettingPage(this@MainActivity,"main", MemFlutterConstants.FLUTTER_ENGINE_ID_VERTICAL)
        }
    }



    class MyPagerAdapter(supportManager: FragmentManager) : FragmentPagerAdapter(
        supportManager
    ) {
        var fragments = mutableListOf<Fragment>(NoteListFragment(),BeDoneFragment())
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
           return fragments.size
        }
    }
}