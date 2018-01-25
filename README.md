## Introduction

***

### GOALS

The plugin is aimed at helping coder to simplify the coding work.

### USER GUIDE

1. [Download From GitHub](https://github.com/zimuwse/coder-helper/blob/master/product/coder-helper.zip)
2. [Download From Jetbrains](https://plugins.jetbrains.com/plugin/10400-coder-helper)
3. Steps:
    1. [Step1](https://github.com/zimuwse/coder-helper/blob/master/product/step1.png)
    2. [Step2](https://github.com/zimuwse/coder-helper/blob/master/product/step2.png)
    3. [Step3](https://github.com/zimuwse/coder-helper/blob/master/product/step3.png)
    4. [Step4](https://github.com/zimuwse/coder-helper/blob/master/product/step4.png)
    5. [Step5](https://github.com/zimuwse/coder-helper/blob/master/product/step5.png)
    5. [Step6](https://github.com/zimuwse/coder-helper/blob/master/product/step6.png)


### DEVELOP GUIDE

In order to keep developing on this project,you should import the following dependent jars into project library.

```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.0.2</version>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.1.15</version>
</dependency>
```

If you want to debug without idea's way, you should change static filed
 **org.lr.helper.config.Conf.DEBUG**
 to
  **TRUE**
 and then you can run 
**org.lr.helper.HelperMainAction.main**
 as common java application to start the plugin 
 in Ordinary way that is clearly faster than idea's.




### UPDATE NOTES
#### 2018-01-24
1. update UI
2. Users can add type mappings from mysql to java.
3. Users can save fields in panel as preference by clicking button named Save Preference and it will be loaded when frame opened again.
4. Users can choose file types to generate.
5. Users can preview files that generated from field named Preview Table.
6. Users can see detail logs when generating files.
7. Users will double click to choose tables in a new frame where tables can be filtered by key word
8. Other Optimizations.
