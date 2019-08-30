package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*
            웹뷰에 웹 페이지 표시하기
            웹뷰 기본 설정
            -웹뷰를 사용할 땐 항상 기본으로 두 가지 설정을 해야 한다.
            첫째!!!
            javaScriptEnabled 기능을 켠다!
            > 그래야 자바스크립트 기능이 잘 동작한다.
            둘째!!!
            webViewClient는 WebViewClient 클래스를 지정하는데 이것을 지정하지 않으면 웹뷰에 페이지가 표시되지 않고
            자체 웹 브라우저가 동작한다.
            ==>웹뷰 사용시 이 두 옵션 잊지 않기!!!
         */
        webView.apply {
            settings.javaScriptEnabled = true //웹뷰 기본설정1
            webViewClient = WebViewClient()     //웹뷰 기본설정2
        }
        //loadUrl() 메서드를 사용하여 "http://"가 포함된 Url을 전달하면 웹뷰에 페이지가 로딩된다.
        webView.loadUrl("http://www.google.com")

        /*
            키보드의 검색 버튼 동작 정의하기
            - 검색창에 URL을 입력하고 소프트 키보드의 검색 아이콘을 클릭하면 웹 페이지가 웹뷰에 표시되도록 하기.

            1) urlEditText의 setOnEditorActionListener는 에디트텍스트가 선택되고 글자가 입력될 때마다 호출된다.
            인자로는 반응한 뷰, 액션ID , 이벤트 세가지. 여기서는 뷰와 이벤트를 사용하지 않아서 _로 대치한다.
            2) actionId값은 EditorInfo 클래스에 상수로 정의된 값 중에서 검색 버튼에 해당하는 상수와 비교하여
            검색 버튼이 눌렸는지 확인
            3) 검색창에 입력한 주소를 웹뷰에 전달하여 로딩. 마지막으로 true를 반환하여 이벤트를 종료
         */

        urlEditText.setOnEditorActionListener { _, actionId, _ -> //키보드 검색버튼 동작정의 1
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {         //키보드 검색버튼 동작정의 2
               webView.loadUrl(urlEditText.text.toString())         //키보드 검색버튼 동작정의 3
                true
            }else {
                false
            }

        }//

        // 컨텍스트 메뉴 등록
        registerForContextMenu(webView)

    }//onCreate()


    /*
        뒤로 가기 동작 재정의
        - 웹뷰에서 링크를 클릭하여 다른 페이지로 이동하다가 이전 페이지로 돌아가려고 뒤로가기 키를 누르면 앱이 종료된다.
        - 아직 뒤로가기 기능을 구현하지 않았기 때문이다.
        - 이전 페이지로 갈 수 있도록 재정의 해야한다
     */
    override fun onBackPressed() {
        if (webView.canGoBack()){ //1-웹뷰가 이전 페이지로 갈 수 있다면
            webView.goBack()      //2-이전 페이지로 이동하고
        }else {                 //3- 그렇지 않다면
            super.onBackPressed()//4-원래 동작을 수행한다 > 즉 액티비티를 종료한다
        }

    }//onBackPressed()

    /*
        옵션 메뉴를 액티비티에 표시하기
        - 액티비티에서 onCreateOptionsMenu() 메서드를 오버라이드하여 메뉴 리소스 파일을 지정하면 메뉴가 표시된다.
        1) 메뉴 리소스를 액티비티의 메뉴로 표시하려면 menuInflater 객체의 inflate() 메서드를 사용하여
        메뉴 리소스를 지정한다.
        2) true를 반환하면 액티비티에 메뉴가 있다고 인식한다.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)     // 옵션메뉴액티비티표시01
        return true                                 // 옵션메뉴액티비티표시02
    }//onCreateOptionsMenu()

    /*
        옵션 메뉴 클릭 이벤트 처리
        - 옵션 메뉴를 선택했을 때의 이벤트를 처리하려면 onOptionsItemSelected() 메서드를 오버라이드하여 메뉴 아이템의
        ID로 분기하여 처리합니다.

        1) 메뉴 아이템으로 분기를 수행
        2) 구글, 집 아이콘을 클릭하면 구글 페이지 로딩
        3) 네이버 클릭 시 네이버페이지 로딩
        4) 다음 클릭 시 다음페이지 로딩
        5) 연락처를 클릭하면 전화앱을 연다. 이러한 방식을 암시적 인텐트.
        6) 031-123-4567로 웹페이지 주소를 sms 문자로 보낸다. 내 핸드폰번호로 설정해 문자가 발송되는지 확인
        7) test@example.com에 '좋은사이트'라는 제목의 이메일 보낸다.
        8) when문에서는 각 메뉴의 처리를 끝내고 true를 반환한다. 내가 처리하고자 하는 경우를 제외한
        그 이외의 경우에는 super 메서드를 호출하는 것이 안드로이드 시스템에서의 보편적 규칙이다.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        //onOptionsItemSelected() 메서드에 메뉴 아이템의 ID로 분기할 수 있도록 when문을 작성
        when (item?.itemId) {       // 1
            R.id.action_google, R.id.action_home -> {   // 2
                //구글연결
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {      // 3
                //네이버연결
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {       // 4
                //다음연결
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {       // 5
                //전화걸기
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if(intent.resolveActivity(packageManager)!=null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {      // 6
                //문자보내기
                sendSMS("031-123-4567",webView.url)
                return true
            }
            R.id.action_email -> {          // 7
                //이메일 보내기
                email("test@example.com","좋은사이트",webView.url)
                return true
            }

        }//when

        return super.onOptionsItemSelected(item)    // 8

    }//onOptionsItemSelected()

    /*
        컨텍스트 메뉴 등록하기
     */
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
        //> menuInflater.inflate() 메서드를 사용하여 메뉴 리소스를 액티비티의 컨텍스트 메뉴로서 사용하도록 한다.
        //> 이 코드는 옵션 메뉴와 같다.
    }//onCreateContextMenu()


    override fun onContextItemSelected(item: MenuItem): Boolean {
        //코드 구성은 옵션 메뉴와 똑같다
        when (item?.itemId) {
            R.id.action_share -> {
                //웹 페이지 주소를 문자열을 공유하는 앱을 사용해 공유
                share(webView.url)
                return true
            }
            R.id.action_browser -> {
                //기기에 기본 브라우저로 웹 페이지 주소를 다시 연다
                browse(webView.url)
                return true
            }
        }//when


        return super.onContextItemSelected(item)
    }

}//
