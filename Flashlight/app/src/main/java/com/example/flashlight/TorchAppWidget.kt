package com.example.flashlight

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/*
    [앱 위젯에서 손전등 켜기]
    - 레이아웃을 수정했으니 위젯을 클리갛면 플래스가 켜지도록 코드를 작성한다.
    TorchAppWidget.kt파일을 열면 다음과 같은 기본코드가 있다.

    - 1)앱 위젯용 파일은 AppWidgetProvider라는 일종의 브로드캐스트 리시버 클래스를 상속받는다.
    - 2)onUpdate() 메서드는 위젯이 업데이트 되어야 할 때 호출
    - 3)위젯이 여러 개 배치되었다면 모든 위젯을 업데이트 한다.
    - 4)위젯이 처음 생성될 때 호출
    - 5)여러 개일 경우 마지막 위젯이 제거될 때 호출
    - 6)위젯을 업데이트할 때 수행되는 코드
    - 7)위젯은 액티비티에서 레이아웃을 다루는 것과는 조금 다르다. 위젯에 배치하는 뷰는 따로 있다.
    그것들은 RemoteViews 객체로 가져올 수 있다.
    - 8)setTextViewText() 메서드는 RemotViews 객체용으로 준비된 텍스트값을 변경하는 메서드
    - 9)여기서 위젯을 클릭했을 때의 처리 추가
    - 10)레이아웃을 모두 수정했다면 AppWidgetManager를 사용해 위젯 업데이트

    9번 부분에 필요한 코드를 추가해야 한다!! 기억하기!!
 */
class TorchAppWidget : AppWidgetProvider() {    //-1

    //-2
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {     //-3
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
    //-4
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }
    //-5
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        //-6
        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val widgetText = context.getString(R.string.appwidget_text)
            // RemoteView 객체를 구성한다.
            val views = RemoteViews(context.packageName, R.layout.torch_app_widget) //-7
            views.setTextViewText(R.id.appwidget_text, widgetText)      //-8

            /*
                위젯을 클릭했을 때의 처리 추가 -9
                RemoteViews 객체는 위젯의 전체 레이아웃의 정보. 여기에 포함된 텍스트 뷰의 글자를 바꿀 때
                setTextViewText() 메서드를 사용한다.
                - 9.1)클릭 이벤트를 연결하려면 setOnClickPendingIntent() 메서드를 사용한다.
                여기에는 클릭이 발생할 뷰의 ID와 PendingIntent 객체가 필요하다

                PendingIntent는 실행할 인텐트 정보를 가지고 있다가 수행해준다.
                다음과 같이 어떤 인텐트를 실행할지에 따라서 다른 메서드를 사용한다.
                - PendingIntent.getActivity() : 액티비티 실행
                - PendingIntent.getService() : 서비스 실행
                - PendingIntent.getBroadcast() : 브로드캐스트 실행

                TorchService 서비스를 실행하는 데 9.2)PendingIntent.getService() 메서드를 사용한다.
                전달하는 인자는 컨텍스트, 리퀘스트 코드, 9.3)서비스 인텐트, 플래그 4개이다.
                리퀘스트 코드나 플래그값은 사용하지 않기 때문에 0을 전달한다.

                -> 위젯을 클릭하면 TorchService 서비스가 시작된다.
                하지만 TorchService는 인텐트에 ON 또는 OFF 액션을 지정해서 켜거나 끈다. 위젯의 경우 어떤 경우가 ON이고
                OFF인지 알 수 없기 때문에 액션을 지정할 수 없다. 액션이 지정되지 않아도 플래시가 동작하도록
                TorchService.kt 파일을 수정해야 한다.
             */
            //실행할 Intent 작성
            val intent = Intent(context, TorchService::class.java)      // -9.3
            val pendingIntent = PendingIntent.getService(context, 0, intent, 0) // -9.2
            //위젯을 클릭하면 위에서 정의한 Intent 실행
            views.setOnClickPendingIntent(R.id.appwidget_layout,pendingIntent)  // -9.1


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views) //-10
        }
    }
}

