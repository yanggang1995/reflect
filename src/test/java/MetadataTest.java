import com.metadata.yg.common.People;
import com.metadata.yg.handle.ReadDataHandle;
import com.metadata.yg.handle.WriteDataHandle;
import com.metadata.yg.task.DataExecutor;
import com.metadata.yg.utils.C3P0Utils;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.FileUtils;
import com.metadata.yg.utils.ObjectUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.Map;

import static com.metadata.yg.constant.Conf.SUFFIX;


public class MetadataTest {
    private static final Logger logger = LoggerFactory.getLogger(MetadataTest.class);

    @Test
    public void test01() {
        for(Map conf:FileUtils.readXml("source_conf")){
            try {
                C3P0Utils c3P0Utils=new C3P0Utils();
                new DataExecutor(("data/"+conf.get("table")).toLowerCase()).executor(ObjectUtils.getTransform((String) conf.get("class")),c3P0Utils.getResultSet((String) conf.get("sql")));
                c3P0Utils.closeConn();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }

    @Test
    public void test02() throws SQLException {
        for(Map conf:FileUtils.readXml("source_conf")){
            try {
                ReadDataHandle readDataHandle = new ReadDataHandle((String)conf.get("class"),(String)conf.get("table"),(String)conf.get("sql"));
                logger.info(readDataHandle.getSqlCount().toString());
                readDataHandle.die();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }

    @Test
    public void test04() throws Exception {
        WriteDataHandle writeDataHandle = new WriteDataHandle();
        BufferedOutputStream bw = writeDataHandle.initDataWrite(new File("data/testRow." + DateUtils.getYesterDay() + "." + SUFFIX));
        bw.write(ObjectUtils.getObjectValues(new People(12,"yang")));
        bw.flush();
        bw.close();
    }

}
