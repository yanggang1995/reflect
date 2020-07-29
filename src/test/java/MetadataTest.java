//import com.jayway.jsonpath.JsonPath;
//import com.metadata.yg.bjlt.UserInfo;
//import com.metadata.yg.common.People;
//import com.metadata.yg.handle.ReadDataHandle;
//import com.metadata.yg.handle.WriteDataHandle;
//import com.metadata.yg.utils.*;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import static com.metadata.yg.constant.Conf.SUFFIX;
//
//
//public class MetadataTest {
//    private static final Logger logger = LoggerFactory.getLogger(MetadataTest.class);
//
//    @Test
//    public void test01() throws SQLException, IOException {
//        BufferedOutputStream Buff = new BufferedOutputStream(new FileOutputStream(new File("conf/sxlt.json")));
//        C3P0Utils c3P0Utils = new C3P0Utils();
//        ResultSet rs = c3P0Utils.getResultSet("select max(media_code) as product_id,program_name as product_name,'' as product_name_en,'' as product_type,program_name as pro_des_info,'' as price   from sxlt.contentviewlog where day=20181101 and service_type='3' group by program_name limit 2000");
//        ResultSetMetaData rsm = rs.getMetaData();
//        Buff.write("[".getBytes());
//        while (rs.next()) {
//            Buff.write("{".getBytes());
//            for (int i = 1; i <= rsm.getColumnCount(); i+) {
//                System.out.print(rs.getString(i) + "  ");
//                Buff.write(("\"" + rsm.getColumnName(i) + "\":\"" + (i != 4 ? (i == 6 ? RandomUtils.getRandomNum(0, 1000) + ".00" : (i == 3 ? "" : rs.getString(i))) : RandomUtils.getRandomNum(1, 4)) + "\"").getBytes());
//                if (i != rsm.getColumnCount()) {
//                    Buff.write(",".getBytes());
//                }
//            }
//            Buff.write("},".getBytes());
//        }
//        Buff.flush();
//        Buff.close();
//    }
//
//    @Test
//    public void test02() throws SQLException {
//        for (Map conf : FileUtils.readXml("source_conf")) {
//            try {
//                ReadDataHandle readDataHandle = new ReadDataHandle((String) conf.get("class"), (String) conf.get("table"), (String) conf.get("sql"));
//                logger.info(readDataHandle.getSqlCount().toString());
//                readDataHandle.die();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            break;
//        }
//    }
//
//    @Test
//    public void test04() throws Exception {
//        WriteDataHandle writeDataHandle = new WriteDataHandle();
//        BufferedOutputStream bw = writeDataHandle.initDataWrite(new File("data/testRow." + DateUtils.getYesterDay() + "." + SUFFIX));
//        bw.write(ObjectUtils.getObjectValues(new People(12, "yang")));
//        bw.flush();
//        bw.close();
//    }
//
//    @Test
//    public void test06() {
//        for (int i = 0; i < 100; i+)
//            System.out.println(RandomUtils.getRandomNum(1, 5));
//    }
//
//    @Test
//    public void test07() {
//        System.out.println(DateUtils.formatTime(1545983097495l, "yyyyMMddHHmmss"));
//    }
//
//    @Test
//    public void test08() {
//        File file = new File("D:\\YG\\project\\reflect\\conf\\makeData.json");
//        try {
//            System.out.println(((LinkedHashMap) JsonPath.read(file, "$")).get(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test09() {
//        Integer i = new Integer(2);
//        System.out.println(i instanceof Integer);
//    }
//
//    @Test
//    public void test10() {
//        File f = new File("D:\\YG\\project\\reflect\\data");
//        if (f.exists() && f.isDirectory()) {
//            File[] files = f.listFiles();
//            for (File file : files) {
//                if (file.isFile()) {
//                    if (file.length() == 0) {
//                        file.delete();
//                    }
//                }
//            }
//            logger.info(f.length() + "");
//        } else {
//            logger.info("file doesn't exist or is not a file");
//        }
//    }
//
//    @Test
//    public void test11() {
//        UserInfo userInfo = null;
//        System.out.println(Optional.of(userInfo).get());
//    }
//}
