# BV2AV互转

bvid avid互转代码，基于Java编写，本地互转代码改编自[mcfx](https://www.zhihu.com/question/381784377/answer/1099438784)大佬的算法



## 用途

在本地或云端（B站服务器）实现bv av互转

在线版本可能抛出`java.io.IOException`



使用以下命令编译

```sh
javac -s src/ -d bin/ *.java
```

并使用以下命令调试

``` shell
#av bv互转，直接输入avid或bvid按回车即可
java -cp bin/ bakajzon.bv2av.test.AvBvTest

#离线版本速度测试
java -cp bin/ bakajzon.bv2av.test.test2
```



## Special Thanks

[mcfx](https://www.zhihu.com/question/381784377/answer/1099438784)



## License

WTFPL