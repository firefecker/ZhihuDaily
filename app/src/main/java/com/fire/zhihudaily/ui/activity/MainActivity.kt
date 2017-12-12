package com.fire.zhihudaily.ui.activity

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.fire.zhihudaily.R
import com.fire.zhihudaily.R.id
import com.fire.zhihudaily.R.layout
import com.fire.zhihudaily.R.string
import com.fire.zhihudaily.base.BaseActivity
import com.fire.zhihudaily.event.EventBase
import com.fire.zhihudaily.event.RxBus
import com.fire.zhihudaily.rx.DefaultButtonTransformer
import com.fire.zhihudaily.rx.SchedulerTransformer
import com.fire.zhihudaily.ui.fragment.HomeFragment
import com.fire.zhihudaily.ui.fragment.OtherThemeFragment
import com.jakewharton.rxbinding2.view.RxView
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import android.net.Uri


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var homeFragment: Fragment? = null
    private var intetnetFragment2: Fragment? = null
    private var intetnetFragment3: Fragment? = null
    private var intetnetFragment4: Fragment? = null
    private var intetnetFragment5: Fragment? = null
    private var intetnetFragment6: Fragment? = null
    private var intetnetFragment7: Fragment? = null
    private var intetnetFragment8: Fragment? = null
    private var intetnetFragment9: Fragment? = null
    private var intetnetFragment10: Fragment? = null
    private var intetnetFragment11: Fragment? = null
    private var intetnetFragment12: Fragment? = null
    private var intetnetFragment13: Fragment? = null

    override fun getLayout(): Int {
        return layout.activity_main
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, string.navigation_drawer_open,
                string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        homeFragment = HomeFragment()
        intetnetFragment2 = OtherThemeFragment.Inner.newInstance("2")
        intetnetFragment3 = OtherThemeFragment.Inner.newInstance("3")
        intetnetFragment4 = OtherThemeFragment.Inner.newInstance("4")
        intetnetFragment5 = OtherThemeFragment.Inner.newInstance("5")
        intetnetFragment6 = OtherThemeFragment.Inner.newInstance("6")
        intetnetFragment7 = OtherThemeFragment.Inner.newInstance("7")
        intetnetFragment8 = OtherThemeFragment.Inner.newInstance("8")
        intetnetFragment9 = OtherThemeFragment.Inner.newInstance("9")
        intetnetFragment10 = OtherThemeFragment.Inner.newInstance("10")
        intetnetFragment11 = OtherThemeFragment.Inner.newInstance("11")
        intetnetFragment12 = OtherThemeFragment.Inner.newInstance("12")
        intetnetFragment13 = OtherThemeFragment.Inner.newInstance("13")
    }

    override fun initData() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.layout_frame, homeFragment)
                .commit()

        RxView.clicks(fab)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(DefaultButtonTransformer())
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe({
                    startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:1606561454@qq.com")))

                })

        RxBus.getDefault()
                .toObservable(EventBase::class.java)
                .compose(SchedulerTransformer<EventBase>())
                .compose(DefaultButtonTransformer<EventBase>())
                .subscribe({
                    Toast.makeText(this@MainActivity, it.arg2(), Toast.LENGTH_SHORT).show()
                })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.action_settings -> {
                Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                RxBus.getDefault().postEventBase(EventBase.Builder.arg2("1222").receiver(MainActivity::class.java).build())
                return true
            }else -> return super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("ResourceType")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            id.nav_news -> {
                toolbar.title = resources.getString(R.string.nav_home)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, homeFragment)
                        .commit()
            }
            id.nav_game -> {
                toolbar.title = resources.getString(R.string.nav_game)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment2)
                        .commit()
            }
            id.nav_movie -> {
                toolbar.title = resources.getString(R.string.nav_movie)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment3)
                        .commit()
            }
            id.nav_design -> {
                toolbar.title = resources.getString(R.string.nav_design)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment4)
                        .commit()
            }
            id.nav_bigcompany -> {
                toolbar.title = resources.getString(R.string.nav_bigcompany)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment5)
                        .commit()
            }
            id.nav_finance -> {
                toolbar.title = resources.getString(R.string.nav_finance)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment6)
                        .commit()
            }
            id.nav_music -> {
                toolbar.title = resources.getString(R.string.nav_music)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment7)
                        .commit()
            }
            id.nav_physical -> {
                toolbar.title = resources.getString(R.string.nav_physical)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment8)
                        .commit()
            }
            id.nav_anime -> {
                toolbar.title = resources.getString(R.string.nav_anime)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment9)
                        .commit()
            }
            id.nav_internet -> {
                toolbar.title = resources.getString(R.string.nav_internet)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment10)
                        .commit()
            }
            id.nav_unrogue -> {
                toolbar.title = resources.getString(R.string.nav_unrogue)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment11)
                        .commit()
            }
            id.nav_recommend -> {
                toolbar.title = resources.getString(R.string.nav_recommend)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment12)
                        .commit()
            }
            id.nav_dailypsychology -> {
                toolbar.title = resources.getString(R.string.nav_dailypsychology)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, intetnetFragment13)
                        .commit()
            }
            id.nav_theme -> {
                val boolean = getPreference().getBoolean("theme", false)
                if (boolean) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                getPreference().edit().putBoolean("theme", !boolean).apply();
                recreate()
            }
            id.nav_setting -> {
                startActivity(Intent(this@MainActivity,SettingActivity::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
