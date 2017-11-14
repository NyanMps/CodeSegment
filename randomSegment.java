// 广义上讲，Java中的随机数的有三种产生方式：
// (01). 通过System.currentTimeMillis()来获取一个当前时间毫秒数的long型数字。
// (02). 通过Math.random()返回一个0到1之间的double值。
// (03). 通过Random类来产生一个随机数，这个是专业的Random工具类，功能强大。


// 第一种
// 获取 [0,100) 的数
long l = System.currentTimeMillis();
System.out.println("随机数为：" + l%100);

// 第二种
double d = Math.random();  // 默认返回 [0,1) 的数
System.out.println("随机数为：" + l*100);

// 第三种
Random random = new Random();  // 可以指定种子数字 new Random(233);
int i = random.nextInt(100);  // 获得 [0,100) 的随机数


/*
 * 最后一种其实也是伪随机
 * 相同种子数的Random对象，相同次数生成的随机数字是完全相同的
 * 种子数只是随机算法的起源数字，和生成的随机数的区间没有任何关系
 */