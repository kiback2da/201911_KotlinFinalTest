package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tjoeun.a201911_kotlinfinaltest.EditBlackListActivty
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.adapter.BlackLIstAdapter
import com.tjoeun.a201911_kotlinfinaltest.adapter.NoticeAdapter
import com.tjoeun.a201911_kotlinfinaltest.data.BlackListData
import com.tjoeun.a201911_kotlinfinaltest.util.ServerUtil
import kotlinx.android.synthetic.main.fragment_blacklist.*
import kotlinx.android.synthetic.main.fragment_blacklist.fragmentNoticeBtnAddNotice
import kotlinx.android.synthetic.main.fragment_notice.*
import org.json.JSONObject

class BlackListFragment : BaseFragment() {

    var blackList = ArrayList<BlackListData>()
    var blackListAdapter : BlackLIstAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blacklist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getBlackListFromServer()
    }

    override fun setupEvents() {
        fragmentNoticeBtnAddNotice.setOnClickListener {
            val intent = Intent(requireContext(), EditBlackListActivty::class.java)
            startActivity(intent)
        }
    }

    override fun setValues(){
        blackListAdapter = BlackLIstAdapter(requireContext(), blackList)
        blackListView.adapter = blackListAdapter
    }

    fun getBlackListFromServer(){
        ServerUtil.getBlackList(requireContext(), object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                blackList.clear()

                var data = json.getJSONObject("data")
                var blackListArray = data.getJSONArray("black_lists")

                for(i in 0..blackListArray.length()-1){
                    blackList.add(BlackListData(blackListArray.getJSONObject(i)))
                }

                activity!!.runOnUiThread {
                    blackListAdapter?.notifyDataSetChanged()
                }
            }
        })
    }
}