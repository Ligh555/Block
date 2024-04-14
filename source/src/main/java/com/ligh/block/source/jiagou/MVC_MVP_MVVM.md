![image.png](https://cdn.nlark.com/yuque/0/2024/png/40371726/1713104664222-5ac31c22-def6-4bc1-b8cb-0ec9c22a15bf.png#averageHue=%232c2b29&clientId=u3fc74a3f-dbaa-4&from=paste&height=319&id=u0c031203&originHeight=637&originWidth=2347&originalType=binary&ratio=2&rotation=0&showTitle=false&size=259525&status=done&style=none&taskId=uc6e860de-401a-42eb-aa28-8e08a82c4c8&title=&width=1173.5)

 **在回答的时候注意演变**
<a name="GzJ2V"></a>
# 分析角度

1. 数据流向
2. 持有关系
3. 通信关系

其本质上都可以分为 数据展示层 ，逻辑控制层，数据提供层，区别在于如何控制UI更新

MVP 时代

存在问题就是 随着业务越来越复杂，接口越来越多

MVVM时代

MVP的通信通信通过接口，MVVP的通信基于livedata等监听机制

<a name="EoRFt"></a>
# MVC
<a name="VdmxH"></a>
## 缺点
control 与view 过度耦合<br />同时大量逻辑卸载acitivity中
<a name="eunyU"></a>
# MVP

- **View：** Activity 和 Layout XML 文件；
- **Model：** 负责管理业务数据逻辑，如网络请求、数据库处理；
- **Presenter：** 负责处理表现逻辑。

![](https://cdn.nlark.com/yuque/0/2024/webp/40371726/1711473426681-e4feaee9-39b3-4ce5-98f4-df3fabda0051.webp#averageHue=%23344224&clientId=u940e4e1a-c8e7-4&from=paste&id=B1dmC&originHeight=349&originWidth=607&originalType=url&ratio=2&rotation=0&showTitle=false&status=done&style=none&taskId=u0b87de0b-5c0e-4d59-ae2d-8bed3edd021&title=)

<a name="deo51"></a>
## 实现方式1
presenter 持有 view 或者activity等

1. 视图层直接调用presenter 执行操作，
2. presenter 调用 model 获取数据
3. presenter 调用view或者activity 更新视图
```kotlin
class MvpActivityView : AppCompatActivity() {


    val viewBinding = ActivityJiagouBinding.inflate(this.layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    /**
     *  通过presentView去更新数据，在更新view，但是由于直接获取view，难以复用以及兼容性较差
     *  因此将present 持有的view抽象化，也就是接口化，抽离为某一类行为
     */
    fun initView1() {
        val presenterView = PresenterView(viewBinding.etTest)
        presenterView.updateView()
    }

}
```
```kotlin
/**
 * presenter 持有试图自行更新
 */
class PresenterView(private val editView: EditText) {

    fun updateView() {
        val model = DataModel()
        val text = model.getData()
        editView.setText(text)
    }
}
```
<a name="xzeYc"></a>
### 存在问题
但是由于直接获取view，难以复用以及兼容性较差，所以需要将行为抽象化为接口
<a name="gBCQn"></a>
## 实现方式2
定义接口，ui层实现接口，present通过持有接口的实现来控制ui刷新

```kotlin
class MvpActivity : AppCompatActivity(),IUpdate {

    val viewBinding = ActivityJiagouBinding.inflate(this.layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    /**
     *  通过presentView去更新数据，在更新view，
     *  将需要执行的ui操作抽象化为IUpdate接口
     */
    fun initView1() {
        val presenterView = Presenter(this)
        presenterView.updateView()
    }
    override fun updateView(text: String) {
        viewBinding.etTest.setText(text)
    }
}
```

```kotlin
class Presenter(private val update: IUpdate) {

    fun updateView() {
        val model = DataModel()
        val text = model.getData()
        update.updateView(text)
    }

}

interface IUpdate{
    fun updateView(text :String)
}
```
<a name="yQx9G"></a>
## 缺点：

1. 通信基于接口，随着业务的复杂接口定义越来越多，代码量大，同时改动维护难
<a name="OOCX6"></a>
# MVVM	
为了mvp 中UI控制的复杂性，提出了UI与数据双向绑定，从而我们只需要关注于数据的更改

**注意： 此时的viewmodel 和 jetpack 的viewmodel 并不是同一个，毫无关联，只是我们通常将jetpack 的viewmodel 用于viewmodel**
<a name="qtiLt"></a>
## 实现方式
<a name="gnMrz"></a>
### 方式1 Databinding
xml文件的data中声明数据，并设置给view，viewmodel中将数据赋值给databinding
```kotlin
class ViewModel {

    var text :String = ""
}
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  xmlns:tools="http://schemas.android.com/tools">

  <data>

    <variable
      name="viewModel"
      type="com.ligh.block.source.jiagou.mvvm.ViewModel" />

  </data>

  <androidx.appcompat.widget.LinearLayoutCompat
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".jiagou.mvvm.MvvmActivity">
    <!-- 双向绑定-->
    <EditText
      android:id="@+id/et_test"
      android:layout_width="match_parent"
      android:layout_height="50dp"
      android:text="@={viewModel.text}" />
    <!-- 单向绑定-->
    <EditText
      android:id="@+id/et_test1"
      android:layout_width="match_parent"
      android:layout_height="50dp"
      android:text="@{viewModel.text}" />


  </androidx.appcompat.widget.LinearLayoutCompat>
</layout>
```
<a name="SJ7fz"></a>
### 方式2 livedata + viewbinding
通过大量的livedata 驱动ui层变化

![](https://cdn.nlark.com/yuque/0/2024/webp/40371726/1711473740215-b8449bed-305d-4087-9f84-0b3ef2768124.webp#averageHue=%23364527&clientId=u940e4e1a-c8e7-4&from=paste&id=u777afdde&originHeight=279&originWidth=607&originalType=url&ratio=2&rotation=0&showTitle=false&status=done&style=none&taskId=u836d2dd0-b784-482a-a6dc-4da53dc8068&title=)

<a name="I5CnV"></a>
# MVI

- **View：** Activity 和 Layout XML 文件，与 MVVM 中 View 的概念相同；
- **Intent：** 定义数据操作，是将数据传到 Model 的唯一来源，相比 MVVM 是新的概念；
- **ViewModel：** 存储视图状态，负责处理表现逻辑，并将 ViewState 设置给可观察数据容器；
- **ViewState：** 一个数据类，包含页面状态和对应的数据。

![](https://cdn.nlark.com/yuque/0/2024/webp/40371726/1711473788581-1c70ebe0-e984-4ad3-a453-fcc7a73374e4.webp#averageHue=%232d3c1e&clientId=u940e4e1a-c8e7-4&from=paste&id=u74fc92e1&originHeight=546&originWidth=702&originalType=url&ratio=2&rotation=0&showTitle=false&status=done&style=none&taskId=udac7c25b-ae86-42d2-be2e-f493707bce3&title=)

<a name="JEXi8"></a>
## 特点

1. **将页面状态和响应行为约束起来，**及intent和state封装
2. 命令式编程变为响应式编程
3. view响应只能监听viewmodel数据变化决定，数据源更改唯一
<a name="Iq01C"></a>
### 存在问题

1. **state 难以封装**，对于复杂页面需要分割，但是违背了其状态唯一的设计思想
2. **局部刷新困难，**可以用Flow#distinctUntilChanged() 来刷新来减少不必要的刷新。
3. 存在过度设计嫌疑


