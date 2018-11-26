import com.metadata.yg.inf.MetadataExecutor;
import com.metadata.yg.task.DataExecutor;
import com.metadata.yg.utils.FileUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MetadataTest {


    @Test
    public void metadataInvok(){
        try {
            Class<?> clazz = Class.forName(FileUtils.readXml("source_conf","class_name"));
            MetadataExecutor executor= (MetadataExecutor) clazz.newInstance();
            List<String> testList=new ArrayList<String>();
            testList.add("test1");
            testList.add("test2");
            testList.add("test3");
            System.out.println(executor.getFormatRow("list"));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void csvTest(){
        List<List> metadata = new ArrayList<>();
        List<String> tmp1 = new ArrayList<>();
        tmp1.add("王小二");
        tmp1.add("男");
        tmp1.add("20");

        List<String> tmp2 = new ArrayList<>();
        tmp2.add("李三娃");
        tmp2.add("男");
        tmp2.add("35");

        metadata.add(tmp1);
        metadata.add(tmp2);

        FileUtils.createCSVFile(metadata,"data/test01");

    }

    @Test
    public void test01(){
        System.out.println("1".getBytes());
    }

    @Test
    public void test02(){
        File file=new File("data/testTxt.txt");
        try {
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            for(int i=0;i<20000;i++) {
                bos.write((i+"---").getBytes());
                bos.write("\n".getBytes());
            }
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test03() {
        try {
            List<String> rs = new ArrayList<>();
            rs.add("123");
            rs.add("124");
            rs.add("125");
            new DataExecutor().executor(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
