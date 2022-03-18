package com.hy.memorandum

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import cody.bus.ElegantBus
import cody.bus.ObserverWrapper
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.hy.bedone.BeDoneFragment
import com.hy.common.HyVariable
import com.hy.common.base.BaseActivity
import com.hy.common.eventbus.RefreshBedone
import com.hy.common.eventbus.RefreshNote
import com.hy.common.flutter.MemFlutterActivity
import com.hy.common.flutter.MemFlutterConstants
import com.hy.common.navigator.BedoneNavigator
import com.hy.common.navigator.NavigatorManager
import com.hy.common.navigator.NoteNavigator
import com.hy.common.widget.DicPopupWin
import com.hy.memorandum.databinding.ActivityMainBinding
import com.hy.note.ui.NoteListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus


class MainActivity : BaseActivity<ActivityMainBinding>() {
    lateinit var mainViewModel: MainViewModel
    var viewpager: ViewPager? = null
    var tablayout: TabLayout? = null
    val images = intArrayOf(
        R.drawable.ic_tab_selector_cc,
        R.drawable.ic_tab_selector_pc
    )
    val tabs = arrayOf("笔记", "待办")
    var toolbar: Toolbar? = null
    var currentIndex: Int = 0
    var collapsingtoolbarlayout: CollapsingToolbarLayout? = null
    var preNoteType: String? = "全部笔记";
    var preBedoneType: String? = "全部代办";
    var bedoneType: Int? = HyVariable.ALL_TYPE
    var noteType: Int? = HyVariable.ALL_TYPE

    override fun initView() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.run {
            viewModel = mainViewModel
        }


        viewpager = findViewById(R.id.viewpager)
        viewpager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                refreshMenuItem(position)
                currentIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        viewpager?.adapter = MyPagerAdapter(supportFragmentManager)
        tablayout = findViewById(R.id.tablayout)
        tablayout!!.setupWithViewPager(viewpager)
        tabs.forEach {
            var tabview: View = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
            var imageView = tabview.findViewById<ImageView>(R.id.tabImageView);
            var textView = tabview.findViewById<TextView>(R.id.tabTextView);
            textView.setText(it);
            imageView.setImageResource(images[tabs.indexOf(it)]);

            tablayout!!.getTabAt(tabs.indexOf(it))!!.setCustomView(tabview);
        }

        collapsingtoolbarlayout = findViewById(R.id.collapsingtoolbarlayout)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.type_popup -> {
                    var dicPopupWindow = DicPopupWin(currentIndex, this) { type ->
                        //重新刷新数据
                        collapsingtoolbarlayout?.title = type?.content
                        if (currentIndex == 0) {
                            preNoteType = type?.content
                            noteType = type?.id
                            EventBus.getDefault().post(RefreshNote(type!!))
                            mainViewModel.getDicTypeSize(noteType!!, 0)
                        } else {
                            preBedoneType = type?.content
                            bedoneType = type?.id
                            EventBus.getDefault().post(RefreshBedone(type!!))
                            mainViewModel.getDicTypeSize(bedoneType!!, 1)
                        }
                    }
                    dicPopupWindow.show(toolbar!!)
                }
                R.id.action_settings -> {
                    MemFlutterActivity.toFlutterPage(
                        this@MainActivity,
                        "setting_page",
                        MemFlutterConstants.FLUTTER_ENGINE_ID_VERTICAL
                    )
                }
                else -> {

                }
            }
            true
        }

        fab.setOnClickListener {
            if (currentIndex == 0) {
                NavigatorManager.getNavigator(NoteNavigator::class.java)?.enterEditPage(0)
            } else {
                NavigatorManager.getNavigator(BedoneNavigator::class.java)?.getBedoneService()
                    ?.addBedone(this, bedoneType!!)
            }
        }
        refreshMenuItem(0)
    }


    fun refreshMenuItem(type: Int) {
        if (type == 0) {
            toolbar?.menu?.clear()
            toolbar?.inflateMenu(R.menu.menu_note);
            collapsingtoolbarlayout?.title = preNoteType
            mainViewModel.getDicTypeSize(noteType!!, 0)
        } else {
            toolbar?.menu?.clear()
            toolbar?.inflateMenu(R.menu.menu_bedone);
            collapsingtoolbarlayout?.title = preBedoneType
            mainViewModel.getDicTypeSize(bedoneType!!, 1)
        }

    }


    class MyPagerAdapter(supportManager: FragmentManager) : FragmentPagerAdapter(
        supportManager
    ) {
        var fragments = mutableListOf<Fragment>(NoteListFragment(), BeDoneFragment())
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }

    override fun onCreateLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        ElegantBus.getDefault("noteSaveState")
            .observe(this, object : ObserverWrapper<Any>() {
                override fun onChanged(value: Any?) {
                    mainViewModel.getDicTypeSize(noteType!!, 0)
                }
            })
        ElegantBus.getDefault("bedoneSaveState")
            .observe(this, object : ObserverWrapper<Any>() {
                override fun onChanged(value: Any?) {
                    if (value is Int) {
                        mainViewModel.getDicTypeSize(bedoneType!!, 1)
                    }

                }
            })
    }
}