package com.tjoeun.a201911_kotlinfinaltest

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lotto.*
import java.util.*
import kotlin.collections.ArrayList

class LottoActivity : BaseActivity() {

    var mHandler = Handler()
    var isNowBying = false

    //사용금액
    var usedMoney = 0L
    //당첨금액
    var winnerMoney = 0L

    var firstCnt = 0
    var secontCnt = 0
    var thirdCnt = 0
    var fourthCnt = 0
    var fifthCnt = 0
    var wrongCnt = 0

    // 보너스 번호 1개만 저장
    var bonusNum = 0
    //당첨번호 6개 저장
    var lottoNumArrayList = ArrayList<Int>()
    var lottoTextArrayList = ArrayList<TextView>()


    var resultArrayList = ArrayList<Int>()
    var resultTxtArrayList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        lottoBtnBuyOne.setOnClickListener {
            setThisWeekLottoNum()
            checkLottoResult()
            usedMoney += 1000
            lottoTxtUsedMoney.text = String.format("사용금액 : %,d원",usedMoney)
        }

        lottoBtnAutoBuy.setOnClickListener {
            if(!isNowBying) {
                doLottoLoop()
                isNowBying = true
                lottoBtnAutoBuy.text = "구매 중단"
            }else{
                //반복 중단 로직 필요
                stopLottoLoop()
                isNowBying = false
                lottoBtnAutoBuy.text = "자동 구매 재개"
            }
        }
    }

    // 로또 당첨 결과를 확인
    fun checkLottoResult(){

        // 6개 1등 => 20억
        // 5개 3등 => 150만원
        // 4개 4등 => 5만원
        // 3개 5등 => 5천원
        // 꽝 => 0원

        var correctCnt = 0

        for(markNum in resultArrayList){
            for(thisWeekNum in lottoNumArrayList){
                if(markNum == thisWeekNum){
                    correctCnt++
                }
            }
        }

        when(correctCnt){
            6 -> {winnerMoney += 2000000000
                  firstCnt++}
            5 -> {
                var isSecondRank = false
                for(num in lottoNumArrayList){
                    if(num == bonusNum){
                        isSecondRank = true
                    }
                }

                if(isSecondRank){
                    secontCnt++
                    winnerMoney += 6500000
                }else{
                    winnerMoney += 1500000
                    thirdCnt++
                }
            }
            4 -> {winnerMoney += 50000
                  fourthCnt++}
            3 -> {usedMoney -= 5000
                  fifthCnt++}
            else -> wrongCnt++
        }

        lottoTxtWinnerCount1.text = String.format("1등 당첨 : %,d",firstCnt)
        lottoTxtWinnerCount2.text = String.format("2등 당첨 : %,d",secontCnt)
        lottoTxtWinnerCount3.text = String.format("3등 당첨 : %,d",thirdCnt)
        lottoTxtWinnerCount4.text = String.format("4등 당첨 : %,d",fourthCnt)
        lottoTxtWinnerCount5.text = String.format("5등 당첨 : %,d",fifthCnt)
        lottoTxtWrongCount.text = String.format("낙첨 횟수 : %,d",wrongCnt)
        lottoTxtWinnderMoney.text = String.format("누적 당첨 금액 : %,d",winnerMoney)
    }

    var lottoRunnable = object : Runnable{
        override fun run() {
            if(usedMoney < 100000000){
                setThisWeekLottoNum()
                checkLottoResult()
                usedMoney += 1000
                lottoTxtUsedMoney.text = String.format("사용금액 : %,d원", usedMoney)
                doLottoLoop()
            }else{
                runOnUiThread {
                    Toast.makeText(mContext,"구매를 종료합니다",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun doLottoLoop(){
        mHandler.post(lottoRunnable)
    }

    fun stopLottoLoop(){
        mHandler.removeCallbacks(lottoRunnable)
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

        //보너스 번호 추가 (중복제외)
        var tempRandomNum = (Math.random()*45+1).toInt()
        var isDup = true
        for(num in lottoNumArrayList){
            if(tempRandomNum == num){
                //중복발견
                isDup = false
                break
            }
            if(isDup){
                bonusNum = tempRandomNum
                break
            }
        }
        lottoTxtLotNumBonus.text = bonusNum.toString()

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

        resultTxtArrayList.add(lottoTxtMyLotNum1)
        resultTxtArrayList.add(lottoTxtMyLotNum2)
        resultTxtArrayList.add(lottoTxtMyLotNum3)
        resultTxtArrayList.add(lottoTxtMyLotNum4)
        resultTxtArrayList.add(lottoTxtMyLotNum5)
        resultTxtArrayList.add(lottoTxtMyLotNum6)

        for(i in resultTxtArrayList){
            resultArrayList.add(i.text.toString().toInt())
        }
    }
}
