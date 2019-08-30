# ColorMyViews App



![01](https://user-images.githubusercontent.com/49340180/63308670-c0ad3700-c32e-11e9-973a-eb37a0fe1dae.PNG)



**Constraint(제약)**

다른 UI 요소, 상위 레이아웃 또는 보이지 않는 가이드라인에 대한 연결 또는 정렬

**제약조건 배치의 장점**

- 화면과 해상도에 대응하도록 할 수 있음
- 보통 평면뷰 계층
- 뷰를 배치하는 데 최적화됨
- 어디에서나 자유 형식 보기 및 편집기를 통해 제약 조건 추가



![01](https://user-images.githubusercontent.com/49340180/63308880-885a2880-c32f-11e9-8b48-047623e7e273.PNG)







------



![01](https://user-images.githubusercontent.com/49340180/63320959-367ac800-c35a-11e9-9981-1682ef954a6d.PNG)

- 왼) 설계 시 X Y 좌표 또는 제약 조건 없음
- 오) 앱 실행 시 뷰가 0.0으로 표시됨



![01](https://user-images.githubusercontent.com/49340180/63321015-62964900-c35a-11e9-9bb9-ae5b108b220d.PNG)

- 왼) layout_margin을 사용하여 지정된 절대 위치

- 오) 뷰가 여백에 의해 제약된 위치에 나타남

  
    

![01](https://user-images.githubusercontent.com/49340180/63321158-ddf7fa80-c35a-11e9-9e0b-36cf27327d3d.PNG)

- 기본적으로 모든 측면에 구속조건을 추가하면 50%의 편향이 생성되고 뷰가 중앙에 배치됨

![02](https://user-images.githubusercontent.com/49340180/63321164-df292780-c35a-11e9-9b19-26ad08b16aab.PNG)









------







## Ratio (비율)

![01](https://user-images.githubusercontent.com/49340180/63321317-53fc6180-c35b-11e9-99a4-d52ef03ab3a2.PNG)

![02](https://user-images.githubusercontent.com/49340180/63321318-5494f800-c35b-11e9-9086-fecb33b9310d.PNG)

![03](https://user-images.githubusercontent.com/49340180/63321320-5494f800-c35b-11e9-8660-79430d90a1d9.PNG)

![04](https://user-images.githubusercontent.com/49340180/63321321-5494f800-c35b-11e9-883d-5af7cef1f1e8.PNG)

![05](https://user-images.githubusercontent.com/49340180/63321322-5494f800-c35b-11e9-9dd2-bf65ab7f7251.PNG)







------





## Chains



![01](https://user-images.githubusercontent.com/49340180/63321475-cff6a980-c35b-11e9-9c06-afb7b4622ceb.PNG)

![02](https://user-images.githubusercontent.com/49340180/63321469-cf5e1300-c35b-11e9-943c-0f52adfd8434.PNG)

![03](https://user-images.githubusercontent.com/49340180/63321470-cf5e1300-c35b-11e9-9f83-ccbfa1064283.PNG)

![04](https://user-images.githubusercontent.com/49340180/63321471-cf5e1300-c35b-11e9-9672-1fed9b2876b7.PNG)

![05](https://user-images.githubusercontent.com/49340180/63321472-cf5e1300-c35b-11e9-9aed-0f2278e03441.PNG)

![06](https://user-images.githubusercontent.com/49340180/63321473-cff6a980-c35b-11e9-8020-2d88ecadcb5e.PNG)

![07](https://user-images.githubusercontent.com/49340180/63321474-cff6a980-c35b-11e9-9415-3b22b034a674.PNG)





------





# 마무리





### ColorMyViews의 최종 기능을 위해 다음 작업을 수행하십시오.

1. 상자 세트 아래에 두 개의 텍스트 보기를 추가하십시오. 레이블과 일부 정보를 생각해 보십시오. 레이블과 텍스트에 서로 다른 글꼴 크기를 사용하십시오. 솔루션 앱은 텍스트 한 줄만 사용하여 작은 장치에서 중복되는 보기를 피하지만, 긴 문자열로 실험할 수 있다.
2. 레이블에서 정보에 기준선 구속조건을 추가하십시오.
3. 이러한 TextView를 레이아웃에 배치하려면 레이블과 정보 사이에 16dp의 고정 제약 조건을 추가하십시오. 이것은 폭이 변하더라도 겹치지 않도록 보장한다.
4. infoTextView 상단을 BoxTwo 하단으로 구속하고, 바이어스를 사용하여 레이아웃에 수직으로 배치한다. 또한 두 TextView를 모두 수평으로 상위 항목으로 제한하십시오.
5. 레이아웃 하단에 구속되는 세 개의 버튼의 수평 체인을 추가한다. 필요한 제약 조건을 추가하십시오.
6. 다른 장치 및 방향을 선택하여 레이아웃을 테스트하십시오. 레이아웃이 그들 모두에게 효과가 있는 것은 아니지만, 대부분의 사람들에게 효과가 있을 것이다.
7. 아래 표시된 코드를 클릭 핸들러에 추가하십시오. 버튼을 클릭 처리기와 관련된 보기 목록에 추가하는 것을 잊지 마십시오.

```kotlin
// 배경에 색 클래스 색상을 사용한 상자
   R.id.red_button -> box_three_text.setBackgroundResource(R.color.my_red)
   R.id.yellow_button -> box_four_text.setBackgroundResource(R.color.my_yellow)
   R.id.green_button -> box_five_text.setBackgroundResource(R.color.my_green)
```

> 전체 보기 목록 코드

```kotlin
val clickableViews: List<View> =
       listOf(box_one_text, box_two_text, box_three_text,
               box_four_text, box_five_text, constraint_layout,
               red_button, green_button, yellow_button)
```

> 앱 실행 화면

![01](https://user-images.githubusercontent.com/49340180/63327970-3da9d200-c36a-11e9-8cf4-c429ccc1bdb1.PNG)











