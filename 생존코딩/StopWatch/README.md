# Chapter06_StopWatch

난이도 : ★☆☆

프로젝트명 : StopWatch

기능

- 타이머를 시작, 일시정지하고 초기화 할 수 있다.
- 타이머 실행 중에 랩타임을 측정하여 표시한다.

핵심 구성요소

- timer : 일정 시간 간격으로 코드를 백그라운드 스레드에 실행한다.
- runOnUiThread : 메인 스레드에서 UI를 갱신한다.
- ScrollView : 랩타임을 표시할 때 상하로 스크롤되는 뷰를 사용한다.
- FloatingActionButton : 머티리얼 디자인의 둥근 모양의 버튼이다.

라이브러리 설정

- **벡터 드로어블 하위 호환 설정** : 안드로이드 5.0 미만에서 벡터 드로어블을 지원하는 라이브러리
- design 라이브러리 : FloatingActionButton 등 머티리얼 디자인을 제공하는 라이브러리





## 해법 요약

스톱워치를 구현하려면 빠르게 계산하면서 UI를 갱신하는  방법을 알아야 한다. 각각 timer와 runOnUiThread 메서드를 사용해 구현한다. 그리고 랩타입을 누적하여 표시할 ScrollView에 동적으로 TextView를 추가하는 방법도 알아본다.

구현순서

1. 준비하기 : 프로젝트 생성 및 안드로이드 설정
2. 스텝 1 : 화면 작성
3. 스텝 2 : 타이머 구현하기
4. 스텝 3 : 랩 타입 기록하기



------





## 1) 준비하기

화면이 하나인 간단한 앱이라 이 프로젝트에서는 Anko 라이브러리를 사용하지 않는다. 대신 FloatingActionButton이라는 둥근 형태의 버튼을 사용하는데 벡터 드로어블 이미지를 사용하므로 벡터 드로어블 하휘 호환 설정을 해줘야 한다.



#### 1-1) 프로젝트 생성

- 프로젝트명 : StopWatch
- minSdkVersion : 19
- 기본 액티비티 : Empty Activity



#### 1-2) 벡터 드로어블 하위 호환 설정

FloatingActionButton 버튼을 사용한다. 이 버튼의 배경에 벡터 이미지를 사용한다. 안드로이드 5.0 미만의 기기에서도 벡터 이미지가 잘 표시되도록 모듈 수준의 그레이들 파일에 코드를 추가한다.

```
defaultConfig{
	vectorDrawables.useSupportLibrary = true
}
```





------





## 2) 화면 작성

화면에는 시간을 표시하는 TextView 두개와 타이머를 시작 및 일시정지, 초기화하는 FloatingActionButton, '랩 타임' 버튼이 필요하다. 그리고 랩 타임을 기록하고 표시할 ScrollView가 필요하다





#### 2-1) 구현순서

1. 시간을 표시하는 TextView 배치
2. TextView 정렬
3. 벡터 이미지 준비
4. FloatingActionButton 소개
5. 타이머 시작용 FloatingActionButton 작성
6. 타이머 초기화용 FloatingActionButton 작성
7. 랩 타임 기록을 위한 버튼 배치
8. 랩 타임 표시하는 ScrollView 배치





------





## 3) 타이머 구현하기

기본 동작은 시작버튼을 누르면 타이머가 동작하는 것이다. 그리고 버튼 이미지를 일시정지 이미지로 교체한다. 다시 버튼을 누르면 타이머가 일시정지 되며 이미지는 시작 이미지로 교체된다.

구현순서

1. Timer의 사용방법
2. 타이머 시작 구현
3. 타이머 일시정지 구현
4. 버튼에 이벤트 연결



#### timer 사용 방법

코틀린에서 일정한 시간을 주기로 반복하는 동작을 수행할 때는 timer 기능을 사용

안드로이드에는 UI를 조작하는 메인스레드와 오래 걸리는 작업을 보이지 않는 곳에서 처리하는 워커 스레드가 존재한다. 워커 스레드에서는 UI를 조작할 수 없다. 이때는 runOnUiThread() 메서드를 사용해야 한다

> 예) 1초 간격으로 어떤 동작을 수행하는 코드

```kotlin
timer(period = 1000){
    //오래 걸리는 작업
    runOnUiThread{
        //UI조작
    }
}
```





------





## 4) 랩타입 기록하기

마지막으로 100미터 달리기용 랩 타임을 기록하는 기능 추가. 마지막으로 타이머의 초기화를 구현한다.

구현 순서

1. 동적으로 LinearLayout에 뷰 추가하기
2. 랩 타임을 표시하기
3. 타이머 초기화 구현



#### 동적으로 LinearLayout에 뷰 추가하기

ScrollView의 내부에 있는 LinearLayout(vertical)은 수직으로 자식 뷰를 추가하는 특징을 가지고 있다. LinearLayout에 동적으로 뷰를 추가하는 방법은 addView() 메서드를 사용하는 것이다.

```kotlin
val textView = TextView(this)
textView.text="글자"
lapLayout.addView(textView)
```





------



## 5)에뮬레이터 실행 화면



#### 1)기본화면

![1메인](https://user-images.githubusercontent.com/49340180/62119811-58ed6880-b2fb-11e9-9289-3245e95eb641.PNG)

#### 2)플레이 버튼 누를 시

![2실행중](https://user-images.githubusercontent.com/49340180/62119833-66a2ee00-b2fb-11e9-8874-ab35bfa7005e.PNG)

#### 3)정지 버튼 누를 시

![3정지화면](https://user-images.githubusercontent.com/49340180/62119873-76bacd80-b2fb-11e9-9522-04ff2771ebe1.PNG)

#### 4)랩타임 누를 시

![4랩타임기록](https://user-images.githubusercontent.com/49340180/62119893-820df900-b2fb-11e9-8baa-d88972e9661b.PNG)

#### 5)왼쪽 빨간버튼의 초기화를 누르면 맨 처음 화면으로 초기화 됨





------





## 6) 마치며

이번 프로젝트에서는 timer와 runOnUiThread를 사용하여 타이머를 구현했다. 오래 걸리는 처리를 하면서 UI를 변경하는 상황에 간편하게 사용할 수 있다.

- 머티리얼 디자인의 둥근 버튼은 FloatingActionButton으로 표현한다. 벡터 이미지를 아이콘으로 설정하고 tint,backgroundTint 속성으로 아이콘과 배경색을 쉽게 변경할 수 있다.
- timer와 runOnUiThread를 사용하면 계산하면서 UI를 갱신할 수 있다.
- 코틀린 코드를 사용하여 뷰를 동적으로 추가할 수 있다.









