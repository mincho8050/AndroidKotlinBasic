package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

/*
    그래픽 API를 다루는 기초
    - 그래픽을 다루려면 다음과 같은 클래스를 사용해야 한다.
    Canvas : 도화지(뷰의 표면) / Paint : 붓(색,굵기,스타일 정의)
 */

class TiltView(context: Context?) : View(context) {

    /*
        Paint 객체 초기화
        - 그리기 메서드에는 Paint 객체가 필요하다.
     */
    private val greenPaint : Paint = Paint()
    private val blackPaint : Paint = Paint()

    private var cX:Float = 0f
    private var cY:Float = 0f

    private var xCoord:Float=0f
    private var yCoord:Float=0f


    //녹색 페인트와 검은색 테두리 페인트를 준비한다.
    init {
        /*
            FILL : 색을 채운다. 획 관련된 설정을 무시한다
            FILL_AND_STROKE : 획과 관련된 성절을 유지하면서 색을 채운다.
            STROKE : 획 관련 설정을 유지하여 외곽선만 그린다
         */
        //녹색 페인트
        greenPaint.color =Color.GREEN 
        //검은색 테두리 페인트
        blackPaint.style = Paint.Style.STROKE
    }
    
    //뷰의 크기가 결정되면 호출한 onSizeChanged() 메서드에 중점 좌표(cX,cY)를 계산한다
    //w:변경된 가로길이, h:변경된 세로길이, oldw:변경전가로길이, oldh:변경전세로길이
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cX = w/2f
        cY = h/2f
    }
    
    //
    override fun onDraw(canvas: Canvas?) {
        //그리기
        /*
            - 원그리기
            drawCircle(cx:Float, cy:Float.radius:Float, paint:Paint!):
            > cx : x좌표 , cy : y좌표, redius : 반지름, paint : Paint객체
            - 선을 그린다.
            drawLine(startX:Float, startY:Float, stopX:Float, stopY:Float, paint:Paint!):
            > startX : 한점의 x좌표, startY:한점의 y좌표, stopX:다른점의x좌표, stopY:다른점의y좌표, paint:Paint객체
         */

        //바깥원
        canvas?.drawCircle(cX, cY, 100f, blackPaint)
        //녹색원
        //>녹색원을 그릴때 xCoord와 yCoord 값을 x좌표와 y좌표에 각각 더해줬다.
        canvas?.drawCircle(xCoord+cX,yCoord+cY,100f,greenPaint)
        //가운데 십자가
        canvas?.drawLine(cX-20, cY, cX+20, cY, blackPaint)
        canvas?.drawLine(cX, cY-20, cX, cY+20, blackPaint)

    }//onDraw

    //이 메서드는 SensorEvent값을 인자로 받는다.
    fun onSensorEvent (event:SensorEvent) {
        //화면을 가로로 돌렸으므로 x축과 y축을 서로 바꿈
        //화면의 방향을 가로 모드로 회전시켰기 때문에 x축과 y축을 서로 바꿔야 이해가 편하다.
        //여기서 센서값에 20을 곱한 이유는 센서값의 범위를 그대로 좌표로 사용하면
        //너무 범위가 적어서 녹색원의 움직임을 알아보기 어렵기 때문이다.
        //그래서 적당한 값을 곱해 보정하여 녹색원이 이동하는 범위를 넓혔다.
        yCoord = event.values[0]*20
        xCoord = event.values[1]*20
        invalidate()//invalidate() 메서드는 뷰의 onDraw() 메서드를 다시 호출하는 메서드다. 즉, 뷰를 다시 그리게 된다.
    }


}//