# OFDBox
这是一个简易的ofd解析库。

### 项目结构
* ofdbox-core           核心库，提供ofd解析功能
* ofdbox-converter      转换库，提供ofd转图片、pdf等格式或者从其他格式转成ofd

# 注意
暂时不要使用除转图片之外的任何API，内建对象正在修改中。

### 已实现功能
* ofd基本解析
* ofd转图片
### 正在实现的功能

* ofd转pdf
* ofd内建对象包装，提供更易用的API
### QuickStart
暂未提供maven仓库，请自行打包

#### 解析ofd
```
OFDReader reader = new OFDReader();
OFD ofd = reader.read(new File("test.ofd"));
```
#### 转图片
```
OFDReader reader = new OFDReader();
OFD ofd = reader.read(new File("test.ofd"));

Ofd2Img ofd2Img = new Ofd2Img();
//转图片以页为单位
BufferedImage image = ofd2Img.toImage(ofd.getDocuments().get(0).getPages().get(0), 20);
ImageIO.write(image, "JPEG", new FileOutputStream("test.jpg"));
```
可参考项目ofdbox实现的简易ofd后端渲染例程 https://github.com/QAQtutu/ofdbox-viewer

#### 系统字体加载
现在不需要手动配置系统字体名映射了，可自动加载系统字体并配置字体名的英文名称与中文名称的映射。
windows会扫描C:/Windows/Fonts目录，mac是/System/Library/Fonts，linux是/usr/share/fonts。如需加载其他目录的字体，请在程序开始运行之前手动扫描目录。
```
FontUtils.scanFontDir(new File("C:/Windows/Fonts"));
```

### 转pdf暂时停止开发
##### 转pdf
```
OFDReader reader = new OFDReader();
OFD ofd = reader.read(new File("test.ofd"));

Ofd2pdf ofd2pdf = new Ofd2pdf();
ofd2pdf.toPdf(ofd.getDocuments().get(0), new File("test.pdf");
```