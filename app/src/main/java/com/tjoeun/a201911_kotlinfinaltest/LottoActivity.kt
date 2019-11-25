package com.tjoeun.a201911_kotlinfinaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_lotto.*

class LottoActivity : BaseActivity() {

    var lottoArrayList = ArrayList<Int>()
    var lottoTextArrayList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        lottoBtnBuyOne.setOnClickListener {
            setThisWeekLottoNum()

        }
    }

    //숫자를 랜덤으로 6개를 생성 1 ~ 45, 중복이 되면 안됨.
    fun setThisWeekLottoNum(){
        for(lotTxt in lottoTextArrayList){

            var randomNum = 0

                while (true) {
                    randomNum = (Math.random() * 45 + 1).toInt()

                    var isDupOk = true

                    for(num in lottoArrayList){
                        if(num == randomNum){
                            isDupOk = false
                            break
                        }
                    }
                    if(isDupOk){
                        lottoArrayList.add(randomNum)
                        break
                    }
                }
            lotTxt.text = randomNum.toString()
        }
    }

    override fun setValues() {
        lottoTextArrayList.add(lottoTxtLotNum1)
        lottoTextArrayList.add(lottoTxtLotNum2)
        lottoTextArrayList.add(lottoTxtLotNum3)
        lottoTextArrayList.add(lottoTxtLotNum4)
        lottoTextArrayList.add(lottoTxtLotNum5)
        lottoTextArrayList.add(lottoTxtLotNum6)
    }
}
