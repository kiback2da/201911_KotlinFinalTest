package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.adapter.NoticeAdapter
import com.tjoeun.a201911_kotlinfinaltest.data.NoticeData
import com.tjoeun.a201911_kotlinfinaltest.util.ServerUtil
import kotlinx.android.synthetic.main.activity_notice.*
import org.json.JSONObject

class NoticeListActivity : BaseActivity() {

    var noticeList = ArrayList<NoticeData>()
    var noticeAdapter : NoticeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        ServerUtil.getNotice(mContext,object : ServerUtil.JsonResponseHandler{
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
                    Toast.makeText(mContext,"로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun setValues() {
        noticeAdapter = NoticeAdapter(mContext, noticeList)
        noticeListView.adapter = noticeAdapter
    }
}
