RedPacket
========================

This project is for my [akka in scala](http://demonyangyue.github.io/tags/Akka/) blog series, to simulate the RedPacket generation and distribution program of Alipay or Wechat.

### Installation

Log in linux machine, install [sbt](http://www.scala-sbt.org/), run:

```bash
git clone https://github.com/demonyangyue/RedPacket
```

### Execution
```bash
sbt run
```

### Test
```bash
sbt test
```

### Architecture
![image](https://github.com/demonyangyue/RedPacket/blob/master/RedPacket.png)
### Further Improvement

* Use akka agent for red packet statistics instead of plain messages.

