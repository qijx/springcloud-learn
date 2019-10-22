##  此项目按照以下步骤可以制作成一个脚手架项目，后面会进行各种工具类补充





### maven项目脚手架制作

#####  首先搭建好一个完善的项目之后有两种办法进行脚手架制作

+ 第一种
  + 在idea右边栏点击maven找到一个带m的小图标点击![1570879089757](C:\Users\qijx\AppData\Roaming\Typora\typora-user-images\1570879089757.png)
  
  + ~~~
    archetype:create-from-project
    ~~~
  
  + 

  + ![1570879077265](C:\Users\qijx\AppData\Roaming\Typora\typora-user-images\1570879077265.png)
  
  + 然后执行完毕会在项目的target下生成generated-sources文件夹再进入target\generated-sources\archetype到根目录下
  
  + ~~~
    mvn clean install
    ~~~
  
  + 
  
  + ![1570879221214](C:\Users\qijx\AppData\Roaming\Typora\typora-user-images\1570879221214.png)
  
  + 结果会在自己配置的maven仓库中生成脚手架，然后自己上传到私服之后大家都可以用了
  
  + ![1570879311036](C:\Users\qijx\AppData\Roaming\Typora\typora-user-images\1570879311036.png)
  
  + 
  
+ 第二种方法就是在cmd执行maven命令，跟上述操作差不多，所以就不介绍了

  ### 用脚手架生成项目步骤

  +  ![1570879632585](C:\Users\qijx\AppData\Roaming\Typora\typora-user-images\1570879632585.png)

    

    
  
    ### 删除IntelliJ Idea中自己添加的Maven Archetype
  
    
  
    * windows下面的：C:\Users\wang\.IntelliJIdea2018.1\system\Maven\Indices\UserArchetypes.xml 
    * 
  
    ![1571638255321](C:\Users\qijx\AppData\Roaming\Typora\typora-user-images\1571638255321.png)
  
    * 然后重启idea即可



