package org.example.demo2024;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.anwen.mongo.mapper.MongoPlusMapMapper;
import com.mybatisflex.core.query.QueryWrapper;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.conditions.update.LambdaEsUpdateWrapper;
import org.dromara.easyes.core.core.EsWrappers;
import org.example.demo2024.biz.ISysPermissionService;
import org.example.demo2024.dto.Demo2;
import org.example.demo2024.entity.SysPermission;
import org.example.demo2024.entity.SysUser;
import org.example.demo2024.es.Document;
import org.example.demo2024.es.mapping.DocumentMapper;
import org.example.demo2024.mapper.SysUserMapper;
import org.example.demo2024.mongo.Person;
import org.example.demo2024.mongo.biz.PersonMongoService;
import org.example.demo2024.rpc.MyClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
class Demo2024ApplicationTests {

    @Test
    void test121(){

        System.out.println("本机IP:" + getIpAddress());
    }

    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }

    @Test
    void test301(){
        //330600300000

        String a = "330601000000";
        String substring = a.substring(5, 6);
        System.err.println(substring);

        String content = "ZZZaaabbbccc中文1234";


        // 身份证号码的正则表达式
        String regex = "\\d{17}[\\dXx]";
        // 待匹配的文本
        String text = "张三110111201109288919李四130205199011141101王五140107200111252313, 210921196303108818。";

        // 创建Pattern对象
        Pattern pattern = Pattern.compile(regex);
        // 创建Matcher对象
        Matcher matcher = pattern.matcher(text);

        // 查找所有匹配的身份证号码
        while (matcher.find()) {
            // 输出匹配到的身份证号码
            System.out.println("找到身份证号码: " + matcher.group());
        }


        ArrayList<String> all = ReUtil.findAll(regex, text, 0, new ArrayList<String>());
        System.err.println(all);


        boolean validCard = IdcardUtil.isValidCard("130428200011107625");
        System.err.println(validCard);

        Long item = Long.valueOf("330600120000".substring(6, 8));
        System.err.println(item);

    }











    @Autowired
    private ISysPermissionService iSysPermissionService;

    @Test
    void testList(){
        List<SysPermission> list = iSysPermissionService.list();
        System.err.println(list);

        iSysPermissionService.save(SysPermission.builder()
                        .menuType(1)
                        .name("qwerqwr")
                        .title("测试11111")
                        .component("etgre")
                        .path("/test11111")
                        .enabled(0)
                        .rank(100)
                .build());
    }


    @Autowired
    private SysUserMapper sysUserMapper;
    @Resource
    private PersonMongoService personMongoService;

    @Autowired
    private MongoPlusMapMapper mongoPlusMapMapper;

    @Resource
    private DocumentMapper documentMapper;

//    @Test
//    void contextLoads() {
//        List<SysUser> sysUsers = sysUserMapper.selectListByQuery(new QueryWrapper());
//        System.err.println(sysUsers);
//
//        QueryWrapper wrapper = QueryWrapper.create()
//                .select()
//                .where(USER.AGE.eq(18));
//        SysUser sysUser = sysUserMapper.selectOneByQuery(wrapper);
//        System.err.println(sysUser.toString());
//        System.err.println(sysUser.getName());
//
//        List<Person> list = personMongoService.list();
//        list.forEach(System.out::println);
//
//        List<Map<String,Object>> userList = mongoPlusMapMapper.list("tb_person");
//        userList.forEach(System.out::println);
//
//        Demo2 testDemo = SpringUtil.getBean("testDemo");
//        System.err.println(testDemo.toString());
//    }

    @Test
    public void testCreateIndex() {
        // 测试创建索引,框架会根据实体类及字段上加的自定义注解一键帮您生成索引 需确保索引托管模式处于manual手动挡(默认处于此模式),若为自动挡则会冲突
        boolean success = documentMapper.createIndex();
        Assertions.assertTrue(success);
    }

    @Test
    public void testInsert() {
        // 测试插入数据
        Document document = new Document();
        document.setTitle("老汉");
        document.setContent("推*技术过硬");
        int successCount = documentMapper.insert(document);
        System.out.println(successCount);
    }

    @Test
    public void testSelect() {
        // 测试查询 写法和MP一样 可以用链式,也可以非链式 根据使用习惯灵活选择即可
        String title = "老汉";
        Document document = EsWrappers.lambdaChainQuery(documentMapper)
                .eq(Document::getTitle, title)
                .one();
        System.out.println(document);
        Assertions.assertEquals(title,document.getTitle());
    }

    @Test
    public void testUpdate() {
        // 测试更新 更新有两种情况 分别演示如下:
        // case1: 已知id, 根据id更新 (为了演示方便,此id是从上一步查询中复制过来的,实际业务可以自行查询)
        String id = "JEZVR40BJi2Qe66_SEiL";
        String title1 = "隔壁老王";
        Document document1 = new Document();
        document1.setId(id);
        document1.setTitle(title1);
        documentMapper.updateById(document1);

        // case2: id未知, 根据条件更新
        LambdaEsUpdateWrapper<Document> wrapper = new LambdaEsUpdateWrapper<>();
        wrapper.eq(Document::getTitle,title1);
        Document document2 = new Document();
        document2.setTitle("隔壁老李");
        document2.setContent("推*技术过软");
        documentMapper.update(document2,wrapper);

        // 关于case2 还有另一种省略实体的简单写法,这里不演示,后面章节有介绍,语法与MP一致
    }


    @Test
    public void testDelete() {
        // 测试删除数据 删除有两种情况:根据id删或根据条件删
        // 鉴于根据id删过于简单,这里仅演示根据条件删,以老李的名义删,让老李心理平衡些
        LambdaEsQueryWrapper<Document> wrapper = new LambdaEsQueryWrapper<>();
        String title = "隔壁老李";
        wrapper.eq(Document::getTitle,title);
        int successCount = documentMapper.delete(wrapper);
        System.out.println(successCount);
    }


    // 注入自定义的 Forest 接口实例
    @Resource
    private MyClient myClient;

    @Test
    public void testClient() {
        File file = myClient.downloadFile("C:\\TestDownload", "1.png",progress->{
            System.out.println("total bytes: " + progress.getTotalBytes());   // 文件大小
            System.out.println("current bytes: " + progress.getCurrentBytes());   // 已下载字节数
            System.out.println("progress: " + Math.round(progress.getRate() * 100) + "%");  // 已下载百分比
            if (progress.isDone()) {   // 是否下载完成
                System.out.println("--------   Download Completed!   --------");
            }
        });

    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testAddKey(){
        stringRedisTemplate.opsForValue().set("key2024","v123456");
    }



    @Autowired
    private Tesseract tesseract;

    @Test
    public void ocr1() throws IOException, TesseractException {

        File file = FileUtil.file("C:\\Users\\oppor\\Pictures\\Snipaste_2023-12-31_15-58-32.png");
//        File file = FileUtil.file("C:\\Users\\oppor\\Pictures\\1685165402017.jpg");
        BufferedInputStream inputStream = FileUtil.getInputStream(file);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        // 对图片进行文字识别
        String s = tesseract.doOCR(bufferedImage);
        System.err.println(s);
        inputStream.close();
    }

    /**
     * 识别图片中的文字
     * @param imageFile 图片文件
     * @return 文字信息
     */
    private String recognizeText(MultipartFile imageFile) throws TesseractException, IOException {

        // 转换
        InputStream sbs = new ByteArrayInputStream(imageFile.getBytes());
        BufferedImage bufferedImage = ImageIO.read(sbs);

        // 对图片进行文字识别
        return tesseract.doOCR(bufferedImage);
    }



}
