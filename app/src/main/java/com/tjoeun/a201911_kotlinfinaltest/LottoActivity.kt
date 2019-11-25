package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_lotto.*
import java.util.*
import kotlin.collections.ArrayList

class LottoActivity : BaseActivity() {

    var lottoNumArrayList = ArrayList<Int>()
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

        //당첨번호를 초기화 시키고 다시 뽑자
        lottoNumArrayList.clear()

        //6개의 텍스트 뷰에 들어갈 당첨번호를 뽑아내는 반복문
        for(lotTxt in lottoTextArrayList){
            //선정된 랜덤값이 들어갈 변수
            var randomNum = 0
            // 몇번을 반복해야 중복을 피할지 알수가 없다.
            // 반복문 : 반복횟수가 명확하면 for, 언제까지 돌려야 할지 모르면 while(tre)
            while (true) {
                //1에서 45사이의 랜덤값을 뽑아서 임시로 저장
                randomNum = (Math.random() * 45 + 1).toInt()
                // 중복에 대한 변수(중복되지 않았다고 선언)
                var isDupOk = true
                //지금까지 만든 당첨번호를 모두 꺼내어보자
                for(num in lottoNumArrayList){
                    //지금 만든 랜덤번호와 꺼내본 당첨번호가 같은지 여부
                    if(num == randomNum){
                        //중복되는 숫자 확인, 중복검사 통과 X, arrayList에 값 추가 X
                        isDupOk = false
                        break
                    }
                }
                if(isDupOk){
                    // 중복검사를 통과했다면 당첨번호로 넣어주자
                    lottoNumArrayList.add(randomNum)
                    //올바른 번호를 뽑았으니 while문 탈출
                    break
                }
            }
            //순서가 제멋대로 여서 보기가 안좋다
            //lotTxt.text = randomNum.toString()
        }

        //당첨번호 6개를 작은숫자부터 큰 숫자 순서대로(정렬)
        Collections.sort(lottoNumArrayList)

        //6개의 텍스트 뷰와 숫자를 뽑아내서 연결
        for(i in 0..lottoNumArrayList.size-1){
            var numTxt = lottoTextArrayList.get(i)
            var number = lottoNumArrayList.get(i)
            numTxt.text = number.toString()
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
