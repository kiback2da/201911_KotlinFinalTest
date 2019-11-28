package com.tjoeun.a201911_kotlinfinaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.util.ServerUtil
import kotlinx.android.synthetic.main.activity_edit_black_list_activty.*
import org.json.JSONObject

class EditBlackListActivty : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_black_list_activty)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

        editBlackListBtnPost.setOnClickListener {
            if(editBlackListEdtTitle.text.length < 5){
                Toast.makeText(mContext, "제목은 최소 5글자 이상이어야 합니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(editBlackListEdtContent.text.length < 10){
                Toast.makeText(mContext, "내용은 최소 10글자 이상이어야 합니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestBlackList(mContext,editBlackListEdtTitle.text.toString(),editBlackListEdtContent.text.toString()
                ,object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {
                        Log.d("로그 : postRequestBlack", json.toString())
                        val code = json.getInt("code")

                        runOnUiThread {
                            if (code == 200) {
                                Toast.makeText(mContext, "Add Success", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(mContext, "Add Fail", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                })

        }

    }

    override fun setValues() {
    }
}
