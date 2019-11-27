package com.tjoeun.a201911_kotlinfinaltest.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tjoeun.a201911_kotlinfinaltest.R
import com.tjoeun.a201911_kotlinfinaltest.R.layout.board_list_item
import com.tjoeun.a201911_kotlinfinaltest.data.BlackListData

class BlackLIstAdapter(context: Context, res : Int, list : ArrayList<BlackListData>) : ArrayAdapter<BlackListData>(context, res, list){

    var mContext = context
    var mList = list
    var inf = LayoutInflater.from(mContext)

    constructor(context: Context, list : ArrayList<BlackListData>) : this(context, board_list_item ,list)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if(tempRow == null){
            tempRow = inf.inflate(board_list_item, null)
        }

        var row = tempRow!!

        var data = mList.get(position).mJson
        var id = data.getInt("id")
        var phone = data.getString("phone_num")
        var title = data.getString("title")
        var content = data.getString("content")
        var createDate = data.getString("created_at")

        var writerData = data.getJSONObject("writer")
        var wId = writerData.getInt("id")
        var wLoginId = writerData.getString("login_id")
        var wName = writerData.getString("name")
        var wPhone = writerData.getString("phone")
        var wMemo = writerData.getString("memo")
        var wAdmin = writerData.getBoolean("is_admin")
        var wStartDate = writerData.getString("start_date")
        var wExpireDate = writerData.getString("expire_date")
        var wCreateDate = writerData.getString("created_at")

        var categoryData = writerData.getJSONObject("category")
        var cId = categoryData.getInt("id")
        var cTitle = categoryData.getString("title")
        var cColor = categoryData.getString("color")

        var mWriter = row.findViewById<TextView>(R.id.blackListTxtWriter)
        var mTitle = row.findViewById<TextView>(R.id.blackListTxtTitle)
        var mContent = row.findViewById<TextView>(R.id.blackListTxtContent)
        var mCategory = row.findViewById<TextView>(R.id.blackListTxtCategory)

        mWriter.text = wName
        mTitle.text = title
        mContent.text = content
        mCategory.text = cTitle.substring(0,1)
        mCategory.background.setColorFilter(Color.parseColor(cColor),PorterDuff.Mode.SRC)



        return row
    }
}