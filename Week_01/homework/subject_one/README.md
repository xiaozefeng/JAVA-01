Java 字节码本质上是 将Java源码编译后交给JVM运行的操作指令

Java bytecode 是由单字节 (byte) 的指令组成，理论上可以支持 256 (byte长度为8  2的8次幂=256)  个操作码 ( opcode)

### 字节码的生成与查看

```shell
# 无package
javac Hello.java
javap -c -verbose Hello
# 有package , com.geek
javac -d . Hello.java
javap -c -verbose com.geek.Hello
```
#### 对象创建

```java
package bytecode;
public class NewObject{
    public static void main() {
        NewObject no = new NewObject();
    }
}
```
**对应的字节码**

![图片](https://uploader.shimo.im/f/vOgTCSeQBGNHNOwC.png!thumbnail?fileGuid=QYGTC8V8668cJcwk)

* **为什么**`new`**指令后需要一个**`dup`**指令呢？ 因为在执行完**`invokespecial`**后，会弹栈，如果没有**`dup`**复制一份存在栈中，那么栈中就没有 NewObject对象的引用了， 无法进行 `astore_0`的操作**
#### 创建数组

```java
package bytecode;
public class NewArray{
   private Integer[] numbers=  new Integer[]{111,222,333};
}
```
**字节码**
![图片](https://uploader.shimo.im/f/faFMflaDpCEbVnLX.png!thumbnail?fileGuid=QYGTC8V8668cJcwk)

#### 四则运算

```java
package bytecode;
public class ArithmeticOperation{
    public static void main(String[]args){
        int a = 100;
        int b = 200;
        int c = a+b;
        int d = a*b;
        int e = a-b;
        int f = a/b;
        int g = a&b;
        int h = a|b;
        int i = a^b;
        int j = a>>1;
        int k = a<<1;
    }
}
```
**字节码:**
![图片](https://uploader.shimo.im/f/ASNqaoV54nFNMFut.png!thumbnail?fileGuid=QYGTC8V8668cJcwk)

#### 分支跳转

```java
package bytecode;
public class Branch {
    public static void main(String[]args){
        int a = 1;
        int b = 4;
        if ( b != 0) {
            int c = a/b;
        }
    }
}
```
**字节码**
![图片](https://uploader.shimo.im/f/EoPMZhHQ8Se1Z0Rj.png!thumbnail?fileGuid=QYGTC8V8668cJcwk)


#### **循环**

```java
package bytecode;
public class Loop {
    private static int [] numbers = { 111,222,333};
    public static void main(String[]args){
        int sum = 0;
        for ( int number : numbers ) {
            sum += number;
        }
        System.out.println(sum);
    }
}
```
**字节码**
![图片](https://uploader.shimo.im/f/FFdnWTFQXG1XA5sq.png!thumbnail?fileGuid=QYGTC8V8668cJcwk)



