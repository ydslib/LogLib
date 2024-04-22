# 日志库设计文档
日志库包含：logger代码级别的日志，crash崩溃日志，卡顿监测堆栈日志

# 使用
## 分支
- master
  master分支的脚本是使用kotlin DSL写的，不兼容低版本的gradle与gradle-plugin
- old
  old分支的脚本是使用Groovy写的，兼容低版本的gradle与gradle-plugin

在根部的build.gradle中添加如下代码
```groovy
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```

master分支的liblog库依赖
在项目的build.gradle.kt中
```groovy
implementation 'com.github.ydslib:LogLib:1.0.0'
```

old分支的liblog库依赖
在项目的build.gradle中
```groovy
implementation 'com.github.ydslib:LogLib:old-1.0.7'
```


## 1. SLog
外观模式，提供统一的入口
### 1.1 方法
### 1.2 日志打印
SLog.kt
```kotlin
//主要初始化context，异常，卡顿
fun initLog(context: Context, initCrash: Boolean = false, initBlock: Boolean = false)
fun i(tag: String, msg: String, saveLog: Boolean = false)
fun v(tag: String, msg: String, saveLog: Boolean = false)
fun d(tag: String, msg: String, saveLog: Boolean = false)
fun w(tag: String, msg: String, saveLog: Boolean = false)
fun e(tag: String, msg: String, saveLog: Boolean = false)
/**
 * 保存日志
 * type:日志类型 LOG,CRASH_LOG,BLOCK_LOG
 * msg:日志信息
 */
fun saveLog(type: String, msg: String?)
//保存异常日志
fun saveCrashLog(msg: String?)
//保存卡顿日志
fun saveBlockLog(msg: String?)
//查询日志
//onQueryListener：查询日志监听，监听返回的日志信息
private fun queryLog(type: String, onQueryListener: OnQueryListener)
//根据类型打印日志LOG,CRASH_LOG,BLOCK_LOG，该打印的日志来自db存储的
private fun printLog(type: String? = null)
//打印logger日志,日志内容来自db
fun printLogger()
//打印异常日志，日志内容来自db
fun printCrashLog()
//打印卡顿日志，日志内容来自db
fun printBlockLog()
private fun saveLogger(context: Context?, tag: String, msg: String, level: String)
//打开查看Logger日志页面
fun startLoggerPage(context: Context)
//打开异常日志页面
fun startCrashPage(context: Context)
//打开卡顿日志页面
fun startBlockPage(context: Context)
```

## 2. logger日志
功能：
● 日志分为info，verbose，debug，warning以及error级别
● 日志去除长度限制，传统Log日志长度限制为4*1024个字符大小
● 保存日志到数据库
### 2.1 方法功能
日志打印
Logger.kt
```kotlin
fun i(tag:String,msg:String,saveLog:Boolean=false)
fun v(tag:String,msg:String,saveLog:Boolean=false)
fun d(tag:String,msg:String,saveLog:Boolean=false)
fun w(tag:String,msg:String,saveLog:Boolean=false)
fun e(tag:String,msg:String,saveLog:Boolean=false)
//打印全部内容
private fun printWholeLog(tag: String?, msg: String?, level: String)
//根据级别打印
private fun printLogByLevel(level: String, tag: String, msg: String)
//保存Logger日志到数据库
fun saveLoggerLog(context: Context?, tag: String, msg: String, level: String)
//保存Logger日志到数据库
override fun <T> saveLog(context: Context, log: T?)
//从数据库中查询logger日志
override fun queryLog(context: Context): List<Any>?
```

## 3. crash崩溃日志
功能：
- 崩溃后打印堆栈信息
- 保存崩溃信息到数据库
## 4. 卡顿监测日志
功能
- 监测卡顿，并打印卡顿信息
- 保存卡顿日志到数据库

## 5. 数据库设计
### 5.1 数据库
#### 5.1.1 logger日志表
|字段	|描述|
|----|:----|
|id	|主键，自动增加|
|time	|日志记录时间|
|level	|日志级别|
|tag	|日志标识|
|msg	|日志信息|
#### 5.1.2 crash日志表
|字段	|描述|
|----|:----|
|id	|主键，自动增加|
|time	|日志记录时间|
|msg	|日志信息|
#### 5.1.3 block日志表
|字段	|描述|
|----|:----|
|id	|主键，自动增加|
|time	|日志记录时间|
|msg	|日志信息|
