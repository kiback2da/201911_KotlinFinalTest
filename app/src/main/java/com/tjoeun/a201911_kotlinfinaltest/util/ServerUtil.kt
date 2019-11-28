package com.tjoeun.a201911_kotlinfinaltest.util

import android.content.Context
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {
    interface JsonResponseHandler{
        fun onResponse(json : JSONObject)
    }

    companion object {
        //어느 서버에 접속할지 서버 주소를 저장하는 변수
        var BASE_URL = "http://192.168.0.26:5000"

        fun postRequestLogin(context: Context, loginId:String, loginPassword:String, handler:JsonResponseHandler?){

            //우리가 만드는 안드로이드 앱을 클라이어트 역할로 동작하게 해주는 클래스
            var client = OkHttpClient()

            //기능 주소와 서버주소를 조합해서 실제 요청 주소 완성
            var url = "${BASE_URL}/auth"

            //POST 메소드에서 요구하는 파라미터를 formBody에 기록
            var formBody = FormBody.Builder()
                .add("login_id",loginId)
                .add("password",loginPassword)
                .build()

            //실제로 날아갈 요청(request) 생성
            var request = Request.Builder().url(url).post(formBody).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("서버통신에러",e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {
                    var body = response.body()!!.string()
                    Log.d("서버",body)

                    var json = JSONObject(body)
                    handler?.onResponse(json)
                }
            })
        }

        fun putRequestSignUp(context: Context, loginId: String, loginPassword: String, name: String, phoneNum: String, handler: JsonResponseHandler?){
            var client = OkHttpClient()
            var url = "${BASE_URL}/auth"
            var formBody = FormBody.Builder().add("login_id",loginId).add("password",loginPassword).add("name",name).add("phone",phoneNum).build()
            var request = Request.Builder().url(url).post(formBody).build()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("로그","서버 통신 에러")
                }

                override fun onResponse(call: Call, response: Response) {
                    var json = JSONObject(response.body().toString())
                    handler?.onResponse(json)
                }

            })
        }

        fun getRequestMyInfo(context: Context, handler: JsonResponseHandler?){
            /*var client = OkHttpClient()
            var urlBuilder = HttpUrl.parse("${BASE_URL}/my_info")!!.newBuilder()

            //get방식의 parameter를 첨부하는 방법
            urlBuilder.addEncodedQueryParameter("device_token","test")

            //URL 최종 확정
            var requestUrl = urlBuilder.build().toString()
            Log.d("로그 : RequestURL",requestUrl)
            Log.d("로그 : Token",ContextUtil.getToken(context))

            var request = Request.Builder().url(requestUrl).header("X-Http-Token",ContextUtil.getToken(context)).build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("로그 : 서버통신에러",e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.e("로그 : onResponse",response.toString())
                    val json = JSONObject(response.body().toString())
                    handler?.onResponse(json)
                }

            })*/
        }


        fun getNotice(context: Context, handler: JsonResponseHandler?){
            var client = OkHttpClient()
            var urlBuilder = HttpUrl.parse("${BASE_URL}/notice")!!.newBuilder()

            //get방식의 parameter를 첨부하는 방법
            //urlBuilder.addEncodedQueryParameter("X-Http-Token",ContextUtil.getToken())

            //URL 최종 확정
            var requestUrl = urlBuilder.build().toString()
            Log.d("로그 : RequestURL",requestUrl)
            Log.d("로그 : Token",ContextUtil.getToken(context))

            var request = Request.Builder().url(requestUrl).header("X-Http-Token",ContextUtil.getToken(context)).build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("로그 : 서버통신에러",e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {
                    var body = response.body()!!.string()
                    Log.d("서버",body)

                    var json = JSONObject(body)
                    handler?.onResponse(json)
                }

            })
        }

        fun getBlackList(context: Context, handler: JsonResponseHandler?){
            var client = OkHttpClient()
            var urlBuilder = HttpUrl.parse("${BASE_URL}/black_list")!!.newBuilder()

            //get방식의 parameter를 첨부하는 방법
            //urlBuilder.addEncodedQueryParameter("X-Http-Token",ContextUtil.getToken())

            //URL 최종 확정
            var requestUrl = urlBuilder.build().toString()
            Log.d("로그 : RequestURL",requestUrl)
            Log.d("로그 : Token",ContextUtil.getToken(context))

            var request = Request.Builder().url(requestUrl).header("X-Http-Token",ContextUtil.getToken(context)).build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("로그 : 서버통신에러",e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {
                    var body = response.body()!!.string()
                    Log.d("서버",body)

                    var json = JSONObject(body)
                    handler?.onResponse(json)
                }

            })
        }


        fun postRequestBlackList(context: Context, title:String, content:String, handler:JsonResponseHandler?){

            //우리가 만드는 안드로이드 앱을 클라이어트 역할로 동작하게 해주는 클래스
            var client = OkHttpClient()

            //기능 주소와 서버주소를 조합해서 실제 요청 주소 완성
            var url = "${BASE_URL}/black_list"

            //POST 메소드에서 요구하는 파라미터를 formBody에 기록
            var formBody = FormBody.Builder()
                .add("title",title)
                .add("content",content)
                .build()

            //실제로 날아갈 요청(request) 생성
            var request = Request.Builder()
                .url(url)
                .header("X-Http-Token",ContextUtil.getToken(context))
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("서버통신에러",e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {
                    var body = response.body()!!.string()
                    Log.d("서버",body)

                    var json = JSONObject(body)
                    handler?.onResponse(json)
                }
            })
        }

    }
}