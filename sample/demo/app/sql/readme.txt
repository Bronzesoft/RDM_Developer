sql文件夹下面对应的是：

1、version.xml ----  小应用升级版本控制，初始为对应module.xml文件中的version标签值(一般为1.0)。
2、en_us  zh_cn  zh_cht 三个目录下对应有sqlserver, oracle, mySQL对应的sql语句，上传时需要创建自定义数据表，或是升级时需要执行的sql脚本。
3、若不需要执行任何sql脚本，则sqlserver, oracle, mySQL对应目录下为空即可。
4、sql名称命名规则：
	若初始上传时需要执行sql，则以1.0.sql命名
	若后续升级时需要执行sql，必须有2个SQL文件，分别是（旧版本-to-新版本-pre.sql和旧版本-to-新版本-post.sql）
	(例如：1.0-to-1.1-pre.sql, 1.0-to-1.1-post.sql)
