### 关于本项目
本项目为在线疫情防控系统,采用**SpringBoot+Jwt+Thymeleaf+Redis**,该部分为后端部分。

### 提示

需要保证系统中有python的环境,且有着*requests*,*bs4*,*re*,*json*,*tqdm*,这些python库。

在/com/qzlnode/epidemic/miniprogram/other包中的[corona_virus_spider.py](https://github.com/InnocentEyes/Epidemic/blob/master/src/main/java/com/qzlnode/epidemic/miniprogram/other/corona_virus_spider.py) 便是获取疫情数据的脚本文件。

同时，在application.yaml中可以自己配置日志文件的位置:
![img_1.png](img_1.png)

环境是jdk1.8、mysql的版本是8.0.24,如果当前主机mysql的版本号与这里的版本号不一致的话，可以到[pom.xml](pom.xml)文件中修改*mysql-connector-java*中的版本号,
改成自己的版本号就可以了，当然前提条件是要搭配着[Maven](https://maven.apache.org/) 使用.

####正在更新中

