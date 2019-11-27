package com.tjoeun.a201911_kotlinfinaltest.ViewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tjoeun.a201911_kotlinfinaltest.fragments.NoticeFragment
import com.tjoeun.a201911_kotlinfinaltest.fragments.BlackListFragment

class ViewPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {

    //각 위치에 어떤 프래그먼트를 보여줄건지
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                NoticeFragment()
            } else -> {
                BlackListFragment()
            }
        }
    }

    //몇개의 페이지를 운영할 것인지
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> {
                "Notice"
            }else ->{
                "Black List"
            }
        }
    }
}