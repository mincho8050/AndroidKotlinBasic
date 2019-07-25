/*
    인터페이스
    추상클래스와 비슷하지만 클래스가 단일 상속만 되는 반면 인터페이스는 다중 구현이 가능하다
    주로 클래스에 동일한 속성을 부여해 같은 메서드라도 다른 행동을 할 수 있게 하는 데 사용한다.
    코틀린의 인터페이스는 자바와 거의 사용법이 같다
*/



//1) 인터페이스 선언
//인터페이스에 추상메서드를 포함할 수 있다.
//원래 추상 클래스에서 추상 메서드는 abstract 키워드가 필요한데 인터페이스에서는 생략할 수 있다.
interface Runnable{
    fun run()
}

//인터페이스는 구현이 없는 메서드뿐만 아니라 구현된 메서드를 포함할 수 있다.
///자바 8의 default 메서드에 대응
interface Runnable1{
    fun run()

    fun fastRun()= println("빨리 달린다")
}



//2) 인터페이스 구현
// 인터페이스를 구현할 때는 인터페이스 이름을 콜론 : 뒤에 나열한다.
//미구현 메서드를 작성하는데 이때 override 키워드를 메서드 앞에 추가.
class Human : Runnable1{
    override fun run() {
        println("달린다")
    }
}
var test=Human()
test.run()

//3) 상속과 인터페이스를 함께 구현
//상속은 하나의 클래스만 상속하는 반면 인터페이스는 콤마로 구분하여 여러 인터페이스를 동시에 구현
open class Animal3{}
interface Runnable3{
    fun run3()
    fun fastRun3() = println("빨리 달려라!")
}
interface Eatable3{
    fun eat3()
}
class Dog3 : Animal3() , Runnable3 , Eatable3 {
    override fun eat3() {
        println("먹는다")
    }

    override fun run3() {
        println("달린다")
    }
}
val dog = Dog3()
dog.run3()
dog.eat3()



/*
    null 가능성
    - 기본적으로 객체를 불변으로 보고 null값을 허용하지 않는다.
    - null값을 허용하려면 별도의 연산자가 필요하고 null을 허용한 자료형을 사용할 때도 별도의 연산자들을 사용하여
      안전하게 호출해야 한다
 */

//1) null 허용?
//코틀린에서는 기본적으로 null값을 허용하지 않는다.
//따라서 모든 객체는 생성과 동시에 값을 대입하여 초기화해야 한다.

//val a : String  > 에러 : 초기화를 반드시 해야 함
//val a : String = null > 에러 : 코틀린은 기본적으로 null을 허용하지 않음/ null값으로 초기화해서 에러 발생

//코틀린에서 null값을 허용하려면 자료형의 오른쪽에 ? 기호를 붙여준다
val a : String? = null
// > 자바에서는 int , long , double과 같은 프리미티브 자료형은 null값을 허용하지 않는다.
// > 다만 그외 모든 클래스형 변수는 null값을 허용한다


//2) lateinit 키워드로 늦은 초기화
//안드로이드 개발할 때는 초기화를 나중에 할 경우가 있다.
//이때는 lateinit 키워드를 변수 선언 앞에 추가하면 된다.
//안드로이드에서는 특정 타이밍에 객체를 초기화할 때 사용합니다.
//초기화를 잊는다면 잘못된 null값을 참조하여 앱이 종료될 수 있으니 주의해야한다.
//lateinit은 다음 조건에서만 사용 할 수 있다
// > var 변수에서만 사용
// > null값으로 초기화 할 수 없다
// > 초기화 전에는 변수를 사용할 수 없다
// > Int , Long , Double , Float에서는 사용할 수 없다
lateinit var a1 : String //OK
a1="hello"
println(a1)


//3) lazy로 늦은 초기화
//lateinit이 var로 선언한 변수의 늦은 초기화!! 라면
//lazy는 값을 변경할 수 없는 val을 사용할 수 있다.
//val 선언 뒤에 by lazy 블록에 초기화에 필요한 코드를 작성한다.
//마지막 줄에는 초기화할 값을 작성한다.
//lazy는 다음 조건에서만 사용
// > val에서만 사용가능
//조건이 적기 때문에 상대적으로 lateinit보다 편하게 사용할 수 있다
val str:String by lazy {
    println("초기화")
    "hello"
}
// str이 처음 호출될 때 초기화 블록이 코드가 실행된다.
//prinln() 메서드로 두 번 호출하면 처음에만 "초기화"가 출력된다
println(str)
println(str)

//4) null값이 아님을 보증(!!)
//변수 뒤에 !!를 추가하면 null값이 아님을 보증

//다음과 같이 null값이 허용되는 name 변수의 경우 String? 타입이기 때문에
// String 타입으로 변환하려면 !!를 붙여서 null값이 아님을 보증해야 한다.
val name : String? ="기다려"
//val name2 : String=name > 에러. > name2는 String이 null값이 허용되지 않기 때문에 에러
val name3 : String? = name //OK
val name4 : String = name!! //OK > null값이 아님을 보증하는 것으로 변환.



//5) 안전한 호출(?.)
//메서드 호출 시 점. 연산자 대신 ?. 연산자를 사용하면 null값이 아닌 경우에만 호출

//str변수의 값이 null값이 아니라면 대문자로 변경하고, null값이라면 null을 반환한다
val str5 : String? = null

var upperCase = if(str5 != null) str5 else null //null
upperCase = str5?.toUpperCase() //null
println(upperCase)



//6) 엘비스 연산자 (?:)
//안전한 호출 시 null이 아닌 기본값을 반환하고 싶을 때는 엘비스 연산자를 함께 사용한다.

//마지막 코드는 이제 null이 아닌 "초기화하시오"라는 문자열을 반환하시오
var str6 : String? = null

var upperCase6 = if (str6 != null) str6 else null
upperCase6 = str6?.toUpperCase()?:"초기화하시오"
println(upperCase6)

