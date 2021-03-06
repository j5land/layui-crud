# layui-crud

## Layui-crud 代码自动生成项目

### 1.项目目标

- 为了加快后台系统开发效率，减少研发人员工作量。
- 在目前前后端分离架构的时代，虽然分离相对有利于各司其职，但是在某些场景下大部分是重复的CURD工作，如果你想晋升、有不错的职业规划，就不要浪费时间在重复的工作上，可以把时间花在有有创造性、有挑战的项目中去。

### 2.项目实现

1. 项目主要基于velocity模板的特性，根据表结构的字段，生成Mybatis相关Mapper、Xml等；
2. 在Mybatis标准的基础上，自动生成字段查询Criteria，便于各种业务查询，无需再编写重复的SQL；
3. 为了效率优先，项目使用前后端不分离的设计，使用UI比较好看以及库比较成熟的Layui，页面部分基于thymeleaf来实现；

### 3.如何使用

快速，直接上手

1. 替换配置文件中的数据库连接信息
2. 替换配置文件auto-config.properties 的相关作者信息，与下载保存目录
3. 启动项目，访问 'http://localhost:8080/auto/page' ，选择你要自动生成的表结构