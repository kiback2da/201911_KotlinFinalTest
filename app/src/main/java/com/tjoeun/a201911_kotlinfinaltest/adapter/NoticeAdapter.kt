package com.tjoeun.a201911_kotlinfinaltest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.data.NoticeData
import org.w3c.dom.Text

class NoticeAdapter(context: Context, res : Int, list : ArrayList<NoticeData>) : ArrayAdapter<NoticeData>(context, res, list) {

    var mContext = context
    var mList = list
    var inf = LayoutInflater.from(mContext)

    constructor(context: Context, list : ArrayList<NoticeData>) : this(context, R.layout.notice_list_item, list)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if(tempRow == null){
            tempRow = inf.inflate(R.layout.notice_list_item, null)
        }

        var row = tempRow!!
        var list = mList.get(position)

        var id = row.findViewById<TextView>(R.id.noticeListTxtID)
        var title = row.findViewById<TextView>(R.id.noticeListTxtTitle)
        var content = row.findViewById<TextView>(R.id.noticeListTxtContent)
        var date = row.findViewById<TextView>(R.id.noticeListTxtCreateDate)

        id.text = list.mId.toString()
        title.text = list.mTitle
        content.text = list.mContent
        date.text = list.mCreated_at

        return row
    }
}