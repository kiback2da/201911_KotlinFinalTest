package com.tjoeun.a201911_kotlinfinaltest.fragments

import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BlackListFragment : BaseFragment() {

    var dateFilterStartDate:Calendar? = null

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

        //날짜 필터 선택버튼 => 몇일부터 필터를 하고 싶은지 datepicker로 선택
        //선택 결과를 텍스트뷰에 반영
        //dateFilterStateDate가 null이면 초기화, 년/월/일 세팅
        //날짜는 2019.09.09 ~ 양식으로 반영

        blackListBtnDateFilterBtn.setOnClickListener {
            val dp = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                if(dateFilterStartDate == null){
                    dateFilterStartDate = Calendar.getInstance()
                }
                val year = dateFilterStartDate?.set(year,month,dayOfMonth)

                val sdf = SimpleDateFormat("yyyy.MM.dd ~")
                blackListTxtDateFilter.text = sdf.format(dateFilterStartDate?.time)
            }, 2019, Calendar.NOVEMBER, 1)
            dp.show()
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