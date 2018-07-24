/**
 * 核心类：
 * DigestUtils
 *   主要包括MD5、SHA1、SHA256算法等实现静态方法
 * Base64
 *   主要包含Base64编码和解码静态方法
 */
public class CodecTest{
        /**
         * MD5散列算法实现(长度有16位和32位,常用32位的)
         */
        @Test
        public void testMD5(){
            String data = "hello";
            String md5String = DigestUtils.md5Hex(data);
            System.out.println(md5String);
        }

        /**
         * SHA1散列算法实现(长度为40位)
         */
        @Test
        public void testSHA1(){
            String data = "hello";
            String sha1String = DigestUtils.sha1Hex(data);
            System.out.println(sha1String.length());

        }

        /**
         * SHA256散列算法实现(长度为64位)
         */
        @Test
        public void testSHA256(){
            String data = "hello";
            String sha256String = DigestUtils.sha256Hex(data);
            System.out.println(sha256String.length());
        }

        /**
         * 摘要算法
         */
        @Test
        public void testDigestAlgorithms(){
            // 摘要算法在MessageDigestAlgorithms下有列出
            // public static final String MD2 = "MD2";
            // public static final String MD5 = "MD5";
            // public static final String SHA_1 = "SHA-1";
            // public static final String SHA_256 = "SHA-256";
            // public static final String SHA_384 = "SHA-384";
            // public static final String SHA_512 = "SHA-512";

            // 备注:都是基于HASH算法实现的
        }

        /**
         * 使用Base64类进行编码和解码,注意其可以转换二进制数据到字符串(比如图片转字符串)
         * 
         * 如果是小图片的话,可以使用Base64编码来存储,只是可以并不是推荐使用。
         */
        @Test
        public void testBase64(){
            String encodeString = Base64.encodeBase64String("hello".getBytes());
            System.out.println(encodeString);
            byte[] bytes = Base64.decodeBase64(encodeString);

            System.out.println(new String(bytes));
        }

        /**
         * 使用Base64将图片编码为字符串
         * 
         * @throws Exception
         */
        @Test
        public void image2String() throws Exception{
            FileInputStream inputStream = new FileInputStream("e:/test.jpg");

            // 借助Commons IO 组件的IOUtils静态方法将输入流转为子节数组
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            String imageString = Base64.encodeBase64String(imageBytes);

            System.out.println(imageString);
        }

        /**
         * 使用Base64将字符串解码为图片
         * 
         * @throws Exception
         */
        @Test
        public void string2Image() throws Exception{
            FileInputStream inputStream = new FileInputStream("e:/test.jpg");
            // 借助Commons IO 组件的IOUtils静态方法将输入流转为子节数组
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            String imageString = Base64.encodeBase64String(imageBytes);

            FileOutputStream outputStream = new FileOutputStream("e:/testCopy.jpg");

            byte[] bytes = Base64.decodeBase64(imageString);
            // 借助Commons IO 组件的IOUtils静态方法将字节数组转为输出流
            IOUtils.write(bytes, outputStream);
        }
		
		@Test
		public void testURLCodec() throws Exception {
		   System.out.println("==============URLCodec================");
		   URLCodec codec = new URLCodec();
		   String data = "啦啦啦";
		   String encode = codec.encode(data, "UTF-8");
		   System.out.println(encode);
		   System.out.println(codec.decode(encode, "UTF-8"));
		}
}