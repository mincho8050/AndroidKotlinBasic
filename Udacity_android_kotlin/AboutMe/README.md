# View Groups

![01](https://user-images.githubusercontent.com/49340180/62999691-2ff7d680-beaa-11e9-8c2e-97811dd7bfbb.PNG)

![02](https://user-images.githubusercontent.com/49340180/62999689-2ff7d680-beaa-11e9-994a-4255fede0256.PNG)

![03](https://user-images.githubusercontent.com/49340180/62999690-2ff7d680-beaa-11e9-885c-50a32385dd2c.PNG)







## Density Independent Pixel(dp)

- 160dpi 화면의 경우: 1dp ==1 pixel
- 480dpi에서는 1dp == 3pixels







![01](https://user-images.githubusercontent.com/49340180/62999967-f6739b00-beaa-11e9-9099-2ddede549b4a.PNG)

![02](https://user-images.githubusercontent.com/49340180/62999965-f5db0480-beaa-11e9-858c-74519d6c13e2.PNG)

![03](https://user-images.githubusercontent.com/49340180/62999966-f5db0480-beaa-11e9-99ff-831f67405de7.PNG)







------



# 레이아웃 파일 

 화면에 보이는 모든 활동은 레이아웃 파일에 정의된 레이아웃이 필요하다. 프로젝트 생성 중에 템플릿의 레이아웃으로 MainActivity를 생성하지 않았으므로, 이제 레이아웃 파일을 추가해야 한다.

다른 리소스처럼 레이아웃 파일도 리소스 폴더에 저장된다.

Project > Android 창에서 res 폴더를 열어 어떤 일이 벌어지는지 확인하십시오.
res를 마우스 오른쪽 버튼으로 누르십시오.
팝업 메뉴에서 New > Android 리소스 파일을 선택하십시오.

새 리소스 파일 창에서:

파일 이름을 activity_main.xml로 지정하십시오.
리소스 유형에 대해 레이아웃을 선택하십시오.
아직 선택하지 않은 경우 루트 요소에 대해 LinearLayout을 선택하십시오.
소스 세트가 기본인지 확인하십시오.
디렉터리 이름이 레이아웃인지 확인하십시오.
모든 것이 올바르게 표시되면 확인을 누르십시오.
그러면 res/layout 폴더에 activity_main.xml 파일이 생성된다.

레이아웃 디렉터리가 아직 존재하지 않았다면 Android Studio가 만들어 준 겁니다.  





## Layout Editor Basics

- **Layout Editor로 UI 빌드**
  - https://developer.android.com/studio/write/layout-editor.html#change-appearance
- **Android Studio 만나보기**
  - https://developer.android.com/studio/intro





## Adding a TextView

- TextView

  - https://developer.android.com/reference/android/widget/TextView

  



![01](https://user-images.githubusercontent.com/49340180/63233833-58e2e780-c26d-11e9-946b-30308ae8df3f.PNG)

- Styles and Themes 
  - https://developer.android.com/guide/topics/ui/look-and-feel/themes

- ImageView
  - https://developer.android.com/reference/android/widget/ImageView



------





# ScrollView

![01](https://user-images.githubusercontent.com/49340180/63238360-16c3a100-c281-11e9-8f28-d77d4134a549.PNG)





1. 레이아웃에 글을 작성한 TextView와 함께 ScrollView 추가.
2. TextView를 NameStyle로 스타일링하고 화면 가장자리에서 스크롤 가능한 텍스트를 분리하기 위해 추가 스타일링을 추가하기.
3. TextView에 줄 SpacingMultiplier 속성을 사용하여 줄 사이의 간격을 추가하기.

```xml
<android:lineSpacingMultiplier="1.2"/>
```





 ScrollView의 TextView 위에 ImageView를 추가하는 실험을 해보십시오. 앱을 실행하면 이 이미지는 별과 달리 텍스트가 위로 스크롤될 때 보이지 않게 스크롤된다.

> 힌트: 두 스크롤 가능한 보기를 ScrollView 내의 LinearLayout으로 감싸야 한다. ScrollView > LinerLayout > ImageView + TextView  

```xml
<ScrollView
   android:layout_width="match_parent"
   android:layout_height="match_parent">

   <LinearLayout
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:orientation="vertical">

       <ImageView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           app:srcCompat="@android:drawable/ic_menu_view" />

       <TextView
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:text="@string/some_long_text"/>

   </LinearLayout>

</ScrollView>
```







------



# Data Binding

1. 데이터 바인딩을 사용하면 findViewById() 작업 ㅇ벗이 자동으로 xml에 만든 View들을 만들어준다.
2. RecyclerView에서 각각의 item을 set 해주는 작업도 xml에서 일련의 작업을 통해 알아서 값이 들어간다.
3. 값이 바뀌면 알아서 바뀐 값으로 View를 변경하게 할 수도 있고 기타 등등 유용하게 활용 할만한게 많다.





![01](https://user-images.githubusercontent.com/49340180/63244938-be4ccd80-c299-11e9-83f6-6381332a4b68.PNG)

![02](https://user-images.githubusercontent.com/49340180/63244939-be4ccd80-c299-11e9-8888-410706d34969.PNG)

![03](https://user-images.githubusercontent.com/49340180/63244941-be4ccd80-c299-11e9-9415-88fd5ef118f6.PNG)

![04](https://user-images.githubusercontent.com/49340180/63244942-bee56400-c299-11e9-9476-6b092d09d03e.PNG)

- Getting started with data binding
  - https://developer.android.com/topic/libraries/data-binding/start
- Data Binding Library
  - https://developer.android.com/topic/libraries/data-binding/
- Generated Binding Classes
  - https://developer.android.com/topic/libraries/data-binding/generated-binding





## Data binding : Views



**1) 모듈 수준의 gradle에 추가하기.**

```xml
dataBinding{
  enabled = true
}
```

**2) activity_main.xml에 레이아웃 만들기**

> 레이아웃으로 감싸기

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <!--내가 작성한 레이아웃들-->
</layout>
```

**3) MainActivity.kt에 추가하기**

```kotlin
private lateinit var binding: ActivityMainBinding
```

> import

```kotlin
import com.example.aboutme.databinding.ActivityMainBinding
```

**4) activity_main 레이아웃 받기**

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
}
```

> import

```kotlin
import android.databinding.DataBindingUtil
```



![01](https://user-images.githubusercontent.com/49340180/63257905-c0248a00-c2b5-11e9-8585-22302bdab135.PNG)

![02](https://user-images.githubusercontent.com/49340180/63257908-c0bd2080-c2b5-11e9-9319-9c661519300d.PNG)

![03](https://user-images.githubusercontent.com/49340180/63257909-c0bd2080-c2b5-11e9-89ea-6ea935abd254.PNG)

![04](https://user-images.githubusercontent.com/49340180/63257910-c0bd2080-c2b5-11e9-9b2e-5235f8c30c51.PNG)

![05](https://user-images.githubusercontent.com/49340180/63257911-c155b700-c2b5-11e9-9f40-23fe8c105f65.PNG)





## Data Binding - The Idea

데이터 바인딩에 관한 큰 아이디어는 컴파일 시간에 두 개의 먼 정보를 함께 연결/맵/바인딩하는 개체를 만들어 런타임에 찾을 필요가 없도록 하는 것이다.

이러한 바인딩을 표면화하는 물체를 바인딩 객체라고 한다. 컴파일러에 의해 생성되며, 후드 아래에서 작동하는 방식을 이해하는 것은 흥미롭지만, 데이터 바인딩의 기본적인 용도를 알 필요는 없다.

- https://classroom.udacity.com/courses/ud9012/lessons/4f6d781c-3803-4cb9-b08b-8b5bcc318d1c/concepts/a39e5191-f206-43a4-b293-3f1d6f6a6549



## Data Binding and findViewById

findViewById는 호출될 때마다 보기 계층을 통과하기 때문에 비용이 많이 드는 작업이다.

데이터 바인딩이 활성화된 상태에서 컴파일러는 ID가 있는 \<layout\>에 있는 모든 뷰에 대한 참조를 작성하여 바인딩 객체에 수집한다.

코드에서 바인딩 개체의 인스턴스를 생성한 다음 바인딩 개체를 통해 추가 오버헤드 없이 뷰를 참조하십시오.

![01](https://user-images.githubusercontent.com/49340180/63258931-1db9d600-c2b8-11e9-864c-960ca0abda82.PNG)



## Data Binding Views and Data

데이터를 업데이트한 다음 보기에 표시된 데이터를 업데이트하는 것은 번거롭고 오류의 원인이다. 데이터를 뷰에 보관하는 것은 데이터 분리 및 표시에도 위반된다.

데이터 바인딩은 이 두 가지 문제를 모두 해결한다. 데이터를 데이터 클래스에 보관하십시오. 데이터를 뷰에 사용할 변수로 식별하기 위해 \<data\> 블록을 \<layout\>에 추가한다. 뷰는 변수를 참조한다.

컴파일러는 뷰와 데이터를 바인딩하는 바인딩 객체를 생성한다.

코드에서 데이터를 업데이트하는 바인딩 개체를 통해 데이터를 참조하고 업데이트하여 보기에 표시되는 내용을 업데이트하십시오.

데이터에 뷰를 바인딩하는 것은 데이터 바인딩을 사용하는 보다 고급 기술의 기초를 확립한다.

![02](https://user-images.githubusercontent.com/49340180/63258932-1db9d600-c2b8-11e9-8844-b14803f3fa0e.PNG)



