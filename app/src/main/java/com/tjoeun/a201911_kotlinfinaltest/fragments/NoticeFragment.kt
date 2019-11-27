package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.adapter.NoticeAdapter
import com.tjoeun.a201911_kotlinfinaltest.data.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.util.ServerUtil
import kotlinx.android.synthetic.main.fragment_notice.*
import org.json.JSONObject

class NoticeFragment : BaseFragment() {

    var noticeList = ArrayList<NoticeData>()
    var noticeAdapter : NoticeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notice,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        ServerUtil.getNotice(requireContext(),object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {
                Log.d("로그 : json","${json.toString()}")
                var code = json.getInt("code")
                if(code == 200){
                    val data = json.getJSONObject("data")
                    val noticeArray = data.getJSONArray("notices")

                    for(i in 0..noticeArray.length()-1){
                        var noticeData = noticeArray.getJSONObject(i)
                        var id = noticeData.get("id").toString().toInt()
                        var title = noticeData.get("title").toString()
                        var content = noticeData.get("content").toString()
                        var createDate = noticeData.get("created_at").toString()

                        noticeList.add(NoticeData(id,title,content,createDate))
                    }

                }else{
                    Toast.makeText(requireContext(),"로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun setValues() {
        noticeAdapter = NoticeAdapter(requireContext(), noticeList)
        fragmentNoticeListView.adapter = noticeAdapter
    }

}