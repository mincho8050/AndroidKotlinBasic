# chapter12 Xylophone

난이도 : ★☆☆

프로젝트명  : Xylophone

기능

- 음판을 누르면 소리가 재생된다.

핵심 구성요소

- SoundPool : 음원을 관리하고 재생하는 클래스

라이브러리 설정

- 없음







## 0) 해법 요약

실로폰 앱은 가로화면으로 고정된 한 액티비티에 도, 레, 미, 파, 솔, 라, 시, 도 음판이 있다. 음판을 누르면 해당 음이 재생된다. 음판은 텍스트 뷰 속성을 수정하고, 소리는 SoundPool 클래스를 사용하여 재생한다. SoundPool은 안드로이드 5.0부터 사용법이 달라졌기 때문에 구 버전과 신 버전기기에서 모두 잘 동작하도록 버전 분기를 적용한다.

구현 순서

1. 준비하기 : 프로젝트 생성
2. 스텝 1 : 레이아웃 작성
3. 스텝 2 : 소리 재생하기





## 1) 레이아웃 작성

실로폰 화면을 작성한다. 

구현 순서

1. 가로 모드로 고정하기
2. 텍스트 뷰로 음판 만들기





### 1-1) 가로 모드로 고정하기

수평 측정기 프로젝트에서 했던 가로 모드 고정하기 방법

```kotlin
override fun onCreate(saveInstanceState: Bundle?){
    //화면이 가로 모드로 고정되게 하기
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
}
```

가로 모드로 고정하는 또 다른 방법이다. 매니페스트 파일에서 액티비티의 screenOrientation 속성에 landscape를 설정해도 같은 효과를 낸다. 

```xml
<!--AndroidManifest.xml-->
<application ...>
	<activity android:screenOrientation="landscape">
    </activity>
</application>
```













## 2) 소리 재생하기

안드로이드 버전에 따른 소리 재생 방법을 다루고 버전 분기에 대해서도 알아본다. 

구현 순서

1. raw 리소스 디렉토리 추가
2. 실로폰 소리 파일 준비하기
3. 안드로이드에서 소리를 재생하는 방법
4. SoundPool 초기화 버전 분기
5. 음판에 동적으로 클릭 이벤트 정의하기





### 2-1) 안드로이드에서 소리를 재생하는 방법

안드로이드에서 소리를 재생하는 몇 가지 방법이 있다. 대표적으로 MediaPlayer 클래스와 SoundPool 클래스를 사용하는 방법이다.

일반적인 소리 파일 연주에는 MediaPlayer 클래스를 사용한다. 음악 파일과 비디오 파일을 모두 재생할 수 있다. MediaPlayer로 raw 디렉터리 파일을 재생하는 코드는 다음과 같이 간단하다. 사용이 끝나면 반드시 release() 메서드를 호출하여 자원을 해제해야 한다.

```kotlin
//raw 디렉터리의 do1 파일을 재생하는 예
val mediaPlayer = MediaPlayer.create(this, R.raw.do1)
button.setOnClickListener{mediaPlayer.start()}

...
//사용이 끝나면 릴리즈 해야함
mediaPlayer.release()
```

MediaPlayer 클래스는 일반적으로 소리를 한 번만 재생하는 경우 또는 노래나 배경음과 같은 경우에는 유용하다. 하지만 실로폰과 같이 연타를 해서 연속으로 소리를 재생하는 경우에는 SoundPool 클래스가 더 유용하다.

SoundPool은 다음과 같이 사용한다. Builder().build() 메서드로 SoundPool 객체를 생성하고 load() 메서드로 소리 파일을 로드하여 그 아이디를 반환한다. 

```kotlin
val soundPool = SoundPool.Builder().build()

val soundId = soundPool.load(this, R.raw.do1, 1)
button.setOnClickListener{soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)}
```

load() 메서드와 play() 메서드의 원형은 다음과 같다.

```kotlin
//음원을 준비하여 id를 반환한다.
load(context: Context, resId: Int, priority: Int):
```

- context : 컨텍스트를 지정한다. 액티비티를 지정한다.
- resId : 재생할 raw 디렉터리의 소리 파일 리소스를 지정한다.
- priority : 우선순위를 지정한다. 숫자가 높으면 우선순위가 높다.

```kotlin
//음원을 재생한다.
play(soundId: Int, leftVolume: Float, rightVolume: Float, priority: Int, loop:Int,
    rate: Float) :
```

- soundId : load() 메서드에서 반환된 음원의 id를 지정한다.
- leftVolume : 왼쪽 볼륨을 0.0 ~ 1.0 사이에서 지정한다.
- rightVolume : 오른쪽 볼륨을 0.0 ~ 1.0 사이에서 지정한다.
- priority : 우선순위를 지정한다. 0이 가장 낮은 순위
- loop : 반복을 지정한다. 0이면 반복하지 않고, -1이면 반복한다.
- rate : 재생 속도를 지정한다. 1.0이면 보통, 0.5이면 0.5배속, 2.0이면 2배속이다.

실로폰은 연속으로 재생하는 경우이므로 SoundPool 클래스를 사용한다.





### 2-2) SoundPool 초기화 버전 분기

```kotlin
private val soundPool=SoundPool.Builder().setMaxStreams(8).build()
```

이번 프로젝트는 생성 시 minSdkVersion을 19로 설정했으므로 위와 같은 코드를 작성하면 구 버전의 안드로이드 기기에서는 런타임 에러로 앱이 종료된다. SoundPool 클래스의 초기화 방법은 안드로이드 5.0부터 변경되었다. 그 이전에는 다음과 같은 코드를 사용했다. 생성자의 첫 번째 인자는 최대 재생 스트림 갯수이고, 두 번째 인자는 어떤 종류의 음원인지 지정한다. 세 번째 인자는 음질이며 0이 기본값이다.

```kotlin
//안드로이드 5.0 미만에서 SoundPool 사용 방법
private val soundPool = SoundPool(8, AudioManager.STREAM_MUSIC, 0)
```

하지만 이렇게 작성하면 더 이상 사용되지 않는 생성자라는 메시지가 출력된다.

안드로이드 개발을 하다보면 API가 버전업되면서 생성자나 메서드에서 이런 경우를 자주 본다. 이때는 각 버전에서 추천되는 방식으로 동작하도록 버전을 분기한다.

구 버전용 초기화 코드 추가

```kotlin
class MainActivity : AppCompatActivity() {
    private val soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        SoundPool.Builder().setMaxStreams(8).build()
    } else {
        SoundPool(8, AudioManager.STREAM_MUSIC, 0)
    }
    ...
}
```









## 3) 마치며

이번 프로젝트에서는 안드로이드에서 소리 파일을 재생하는 방법을 알아보고 실로폰을 만들었다.

- 소리를 재생하려면 MusicPlayer  또는 SoundPool 클래스를 사용한다. 
- 일반적인 경우 MusicPlayer 클래스가 간편하지만 연속으로 소리를 재생하는 경우에는 SoundPool 클래스를 사용한다.
- 더 이상 사용되지 않는(deprecated) 메서드를 사용할 때는 실행되는 안드로이드 기기의 버전에 따라 분기를 수행하는 것이 좋다.
- 뷰가 여러 개일 때는 forEach() 함수 등을 사용하여 동적으로 클릭 이벤트를 구현할 수 있다.

