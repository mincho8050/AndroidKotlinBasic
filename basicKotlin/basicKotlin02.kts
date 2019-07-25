/*
    제어문
    - if , when , for , while
    - when문을 제외하고 자바와 거의 동일
 */

//1) if
//실행할 문장이 한 줄이면 블록을 생략
val a=10
val b=20

//일반적인 방법
var max=a
if(a<b)max=b
println(max)

//if~else문의 사용법도 자바와 같음
if(a>b){
    max=a
}else{
    max=b
}
//if문을 식처럼 사용할 수 있다
max=if(a>b)a else b
println(max)


//2) when
//자바의 switch문에 대응한다.
//값이 하나인 경우는 물론 콤마나 in 연산자로 값의 범위를 자유롭게 지정하는 것이 특징
//else를 사용하여 나머지에 대한 경우를 처리.
//코드를 작성할 때 블록으로 코드를 감쌀 수 있다.
val x=1

when (x) {
    1 -> println("x==1")    //값 하나
    2,3 -> println("x==2 or x==3")  //값 여러개는 콤마로
    in 4..7 -> println("4부터 7 사이") //in연산자로 범위 지정
    !in 8..10 -> println("8부터 10 사이가 아님")
    else -> {   //나머지
        println("x는 1이나 2가 아님")
    }
}

//when문 역시 if문과 마찬가지로 식처럼 사용 가능
val numberStr=1

val numStr=when (numberStr%2){
    0 -> "짝"
    else -> "홀"
}
println(numStr)

//when문의 결과를 함수의 반환값으로 사용할 수 있다.
val number=1

fun isEven(num:Int)=when(num%2){//결과가 String으로 추론되어 반환형 선언 생략 가능
    0 -> "짝"
    else -> "홀"
}
println(isEven(number))



//3) for
//for문은 배열이나 컬렉션을 순회하는 문법으로 자바의 foreach문과 비슷하다

//1~5까지 담겨있는 배열을 순회하며 모든 요소를 프린트하는 for문의 예
//in 키워드를 사용하여 모든 요소를 num 변수로 가져온다
val numbers = arrayOf(1,2,3,4,5)

for(num in numbers){
    println(num)
}

//증가 범위는 .. 연산자를 사용한다.
//감소 범위는 downTo 키워드를 사용하며 step 키워드로 증감의 간격을 조절한다.

//1~3 까지 출력
for(i in 1..3){
    println(i)
}
//0~10까지 2씩 증가하며 출력
for(i in 0..10 step 2){
    println(i)
}
//10~0까지 2씩 감소하며 출력
for(i in 10 downTo 0 step 2){
    println(i)
}

//4) while
//주어진 조건이 참일 때 반복하는 문법
//변형으로는 무조건 한 번은 실행되는 do~while문이 있다.
//자바와 동일

//while
var z=10
println(z)
while(z>0){
    z--
    println(z)
}

//do~while
var q=10
do{
    q--
    println(q)
}while (q>0)