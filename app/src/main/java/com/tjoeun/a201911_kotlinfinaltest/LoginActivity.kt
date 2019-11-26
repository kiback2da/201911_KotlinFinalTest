package com.tjoeun.a201911_kotlinfinaltest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tjoeun.a201911_kotlinfinaltest.util.ContextUtil
import com.tjoeun.a201911_kotlinfinaltest.util.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        loginChkSaveID.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                Log.d("로그 : isChecked","${loginEdtLoginID.text.toString()}")
                ContextUtil.setRememberId(mContext,true)
                ContextUtil.setUserId(mContext,loginEdtLoginID.text.toString())
            }else{
                ContextUtil.setRememberId(mContext,false)
                ContextUtil.setUserId(mContext,"")
            }
        }

        loginBtnLogin.setOnClickListener {
            ServerUtil.postRequestLogin(mContext,loginEdtLoginID.text.toString(),loginEdtLoginPW.text.toString(),object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    Log.d("로그 : json","${json.toString()}")
                    var code = json.getInt("code")
                    if(code == 200){
                        val data = json.getJSONObject("data")
                        var token = data.getString("token")
                        ContextUtil.setToken(mContext,token)

                        Log.d("로그 : json","${token}")

                        var intent = Intent(mContext,NoticeListActivity::class.java)

                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(mContext,"로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    override fun setValues() {
        var remeberId = ContextUtil.getRememberId(mContext)

        if(remeberId){
            Log.d("로그","${ContextUtil.getUserId(mContext)}")
            loginEdtLoginID.setText(ContextUtil.getUserId(mContext))
            loginChkSaveID.isChecked = true
        }else{
            loginChkSaveID.isChecked = false
        }
    }
}
