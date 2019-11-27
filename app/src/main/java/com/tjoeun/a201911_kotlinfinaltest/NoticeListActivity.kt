package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import com.tjoeun.a201911_kotlinfinaltest.ViewPager.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_notice.*

class NoticeListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
    }

    override fun setValues() {
        finalViewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        finalTabLayout.setupWithViewPager(finalViewPager)
    }
}
