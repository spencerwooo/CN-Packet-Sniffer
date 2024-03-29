# CN Packet Sniffer

[Computer Networks] The Packet Sniffing Experiment

## Dependencies

1. Underlying library: `libpcap` for Unix and `winpcap` for Windows
2. Java wrapper: `Pap4J` - [kaitoy/pcap4j](https://github.com/kaitoy/pcap4j)

## Screenshots

![](https://i.loli.net/2019/05/07/5cd16fe28b2c7.png)

## Permissions

In order to capture traffic on your network interface, you'll need to set the correct permissions as stated here: [Wireshark - you don't have permission to capture on that device mac
](https://stackoverflow.com/questions/41126943/wireshark-you-dont-have-permission-to-capture-on-that-device-mac/42574321)

## Building

We use Maven as our dependency manager. Open the project via Intellij Idea, and let Maven do the rest.

Do note that **the version of `pcap4j` on Maven Central is bugged**, see [issues/220](https://github.com/kaitoy/pcap4j/issues/220). To avoid this, follow the instructions mentioned here: [pcap4j - issue 220 "How to use a SNAPSHOT version of the dependency via Maven."](https://github.com/kaitoy/pcap4j/issues/220#issuecomment-489669183)

## Instructions

**链路层抓包及协议分析**

利用 WinPcap 库实现数据链路层帧的捕获，显示分析帧结构及其所封装的各层包的结构。

### 配置文件关键要点：

无

### 程序运行屏幕输出要点：

（可以参考 Wireshark 的输出内容和格式）

- 首先屏幕显示当前配置的网络适配器，并要求**选择**捕获适配器编号；
- 按照捕获帧的层次关系显示以下信息：
  - 数据链路层（MAC 子层）层结构及各个字段的值
  - 网络层分组的格式及各个字段的值
  - 运输层报文段的格式及各个字段的值
  - 应用层报文格式及各个字段的值
