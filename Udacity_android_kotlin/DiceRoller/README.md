# DiceRoller 프로젝트

주사위 굴리는 앱 만들기





------







# Android Studio Project

- 앱의 핵심 논리를 위한 Kotlin 파일
- 이미지 및 문자열과 같은 정적 컨텐츠에 대한 리소스 폴더
- OS가 앱을 시작할 수 있도록 필수 앱 세부 정보를 정의하는 Android 매니페스트 파일
- 애플리케이션 구축 및 실행을 위한 Gradle 스크립트



**MainActivity.kt** : 앱이 하는 일

**activity_main.xml** : 앱의 모양(디자인부분)

![01](https://user-images.githubusercontent.com/49340180/62994099-2e6fe380-be95-11e9-90be-732cfb7cc1f8.PNG)

![01](https://user-images.githubusercontent.com/49340180/62994146-58c1a100-be95-11e9-8eaa-0cb7e1e9b108.PNG)









## View Examples

- TextView
- Button
- ImageView
- CheckBox
- RadioButton
- EditText
- ProgressBar









------





# 안드로이드 Layout 클래스

https://recipes4dev.tistory.com/66

https://mattlee.tistory.com/74







![01](https://user-images.githubusercontent.com/49340180/62994974-93c5d380-be99-11e9-9317-d7a25ce29cd9.PNG)

![02](https://user-images.githubusercontent.com/49340180/62994975-93c5d380-be99-11e9-9eae-2d21f3583817.PNG)

![03](https://user-images.githubusercontent.com/49340180/62994976-945e6a00-be99-11e9-9175-ab12062e4144.PNG)







------







# Gradle 소개

Android 빌드 도구

- 앱을 실행하는 장치
- 실행 파일에 대한 컴파일
- 종속성 관리
- 구글 플레이에 대한 앱서명
- 자동 테스트





## APK

Android Application Package

Android 응용 프로그램 배포를 위한 실행 가능한 형식

![01](https://user-images.githubusercontent.com/49340180/62996880-5feeac00-bea1-11e9-84d2-5e475ac05768.PNG)

![02](https://user-images.githubusercontent.com/49340180/62996878-5f561580-bea1-11e9-92f9-2df2b73ab458.PNG)

![03](https://user-images.githubusercontent.com/49340180/62996879-5f561580-bea1-11e9-9082-2a4d4bcaff32.PNG)







## Module

앱에서 별도의 기능을 위한 소스 파일 및 리소스가 있는 폴더

![01](https://user-images.githubusercontent.com/49340180/62996983-b2c86380-bea1-11e9-8700-a8e2fac54e1b.PNG)

![02](https://user-images.githubusercontent.com/49340180/62996986-b360fa00-bea1-11e9-9b2a-f67d06b60850.PNG)





## Repository

In gradle

외부 코드가 다운로드되는 원격 서버





## Dependencies

In gradle

프로젝트가 종속되는 라이브러리 등의 외부 코드





------



# Android 호환성

- 2011 - 첫 번째 지원 라이브러리 릴리스. 이전 버전과의 호환성을 위해 Google 소유 라이브러리
- 2018 - Android Jetpack 출시
- AndroidX는 Android Jetpack의 네임스페이스

![01](https://user-images.githubusercontent.com/49340180/62998764-7992f200-bea7-11e9-950c-cc3fbdea08bb.PNG)







------





# Vector Drawables

**Adding support**

1. build.gradle에 추가
2. 올바른 레이아웃 네임스페이스 사용
3. src를 srcCompat으로 변경

Android Studio 1.4는 빌드시 PNG 파일을 생성하여 벡터 드로어 블에 대한 제한된 호환성 지원을 도입했습니다. 그러나 벡터 드로어 블 및 애니메이션 된 벡터 드로어 블 지원 라이브러리는 유연성과 광범위한 호환성을 모두 제공합니다.이 라이브러리는 지원 라이브러리이므로 모든 Android 플랫폼 버전에서 다시 Android 2.1 (API 레벨 7+)로 사용할 수 있습니다. 벡터 지원 라이브러리를 사용하도록 앱을 구성하려면 앱 모듈에서 파일에 `vectorDrawables` 요소를 추가하십시오 `build.gradle`.





------





# 요점정리

- Basic app structure
- Layout, Activities and Inflation
- Interaction via button (버튼을 통한 상호작용)
- Gradle & Compatibility on Android















