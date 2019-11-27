package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.data.BlackListData
import com.tjoeun.a201911_kotlinfinaltest.util.ServerUtil
import org.json.JSONObject

class BlackListFragment : Fragment() {

    var blackList = ArrayList<BlackListData>()

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

    fun setupEvents() {
        ServerUtil.getBlackList(requireContext(), object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {
                var data = json.getJSONObject("data")
                var blackListArray = data.getJSONArray("black_lists")

                for(i in 0..blackListArray.length()-1){
                    blackList.add(BlackListData(blackListArray.getJSONObject(i)))
                }
            }
        })
    }

    fun setValues(){

    }
}