

/*
    클래스
 */

//1) 클래스 선언
class Person {} //클래스 선언
val person = Person() //인스턴스 생성
// > 자바에서는 new키워드로 객체를 생성하지만 코틀린에서는 new를 사용하지 않음


//2) 생성자

//코틀린에서의 클래스
// > 이 코드는 빈 생성자를 가지는 클래스
class Person2(var name:String){}
println(Person2("안녕"))

//생성자에서 초기화 코드를 작성하려면 다음과 같이 constructor로 생성자 표현
// > 이 생성자는 name을 출력
class Person3{
    constructor(name:String){
        println(name)
    }
}
Person3("안녕")

//생성자 이외에도 init 블록에 작성한 코드가 클래스를 인스턴스화할 때 가장 먼저 초기화
class Person4(name:String){
    init {
        println(name)
    }
}
Person4("바보")


//3) 프로퍼티
//클래스의 속성을 사용할 때는 멤버에 직접 접근하며 이를 프로퍼티라고 함

//Person5 클래스는 name 프로퍼티를 가지고 있다. 프로퍼티에 값을 쓰려면 = 기호로 값을 대입한다. 값을 읽을때는 프로퍼티 참조
//코틀린에서는 다음과 같이 사용한다. 속성에 값을 설정하거나 얻으려면 getter / setter 메서드 없이 바로 점을 찍고 name 프로퍼티에 접근할 수 있다.

//코틀린에서의 클래스
//> 클래스 선언
class Person5(var name:String){

}
//>인스턴스 생성
val person5=Person5("멋쟁이")
person5.name="키다리"  //쓰기
println(person5.name)   //읽기

//자바로 작성된 클래스의 게터/세터 메서드는 코틀린에서 사용할 때 기존의 게터/세터를 사용할 수도 있고 프로퍼티로 사용 가능
//자바에서는 private 접근지정자로 은닉화된 멤버변수에 세터/게터 메서드를 사용해서 접근하는 방식이 일반적
//코틀린은 프로퍼티가 게터/세터를 대체한다.

//자바에서의 클래스 (이 형태가 일반적)
/*

class Person{
    private String name;

    public Person(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
}

*/


//4) 접근 제한자
//변수나 함수를 공개하는데 사용하는 키워드
//public (생략가능)  : 전체공개 , 아무것도 안쓰면 기본적으로 public
//private : 현재 파일 내부에서만 사용
//internal : 같은 모듈 내에서만 사용
//protected : 상속받은 클래스에서만 사용
open class A{
    val a=1 // public
    private val b=2
    protected val c=3
    internal val d=4
}
//안드로이드 스튜디오의 프로젝트는 app 모듈을 기본 제공해 앱을 개발한다. 사실 여러 모듈을 생성할 수 있다.
//같은 프로젝트에 스마트폰용, 시계용, TV용 안드로이드 앱을 만든다면 모듈 3개를 생성
//internal은 이 모듈 간 접근을 제한하는 키워드다.




//5) 클래스의 상속
//코틀린에서의 클래스는 기본적으로 상속이 금지.
//상속이 가능하게 하려면 open 키워드를 클래스 선언 앞에 추가한다.

//다음은 Animal 클래스를 상속받는 Dog 클래스를 나타낸다.
open class Animal{}
class Dog : Animal(){}
//만약 상속받을 클래스가 생성자를 가지고 있다면 다음과 같이 상속받을 수 있다.
open class Animal2(val name:String){}
class Dog2(name:String) : Animal2(name){}




//6) 내부 클래스
//내부 클래스 선언에는 inner를 사용한다. 내부 클래스는 외부 클래스에 대한 참조를 가지고 있다.

//inner키워드가 없다면 a5fmf 20으로 변경할 수 없다.
class OuterClass{
    var a5=10

    //내부 클래스
    inner class OuterClass2{
        fun something(){
            a5=20   //접근가능
        }
    }
}



//7) 추상 클래스
//미구현 메서드가 포함된 클래스.
//클래스와 미구현 메서드 앞에 abstract 키워드를 붙인다.
//추상 클래스는 직접 인스턴스화할 수 없고 다른 클래스가 상속하여 미구현 메서드를 구현해야 한다.
//기본적으로 자바와 동일한 특성을 가진다.
abstract class A7{
    abstract fun func()

    fun func2(){

    }
}
class B7:A7(){
    override fun func(){
        println("hello!!!")
    }
}
//val a7=A7() > 에러
val a7=B7()
println(a7.func())


