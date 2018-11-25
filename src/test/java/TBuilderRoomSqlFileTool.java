
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

    /**
     * 多线程写入一个文件
     *
     * @author lyuan
     *
     */
public class TBuilderRoomSqlFileTool {
    final static int BSIZE = 1024 * 1024;
    final static int DATACACHENUM = 5000;
    static int currThreadCount = 0;
    static int maxThreadCount = 20;
    static File dataFile = new File("data/dataFile.csv");

    public static BufferedWriter initDataWrite(File dataFile) throws Exception {
        if (!dataFile.exists()) {
            if (!dataFile.createNewFile()) {
                System.err.println("创建文件失败，已存在：" + dataFile.getAbsolutePath());
            }
        }
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), "UTF-8"));
    }

    //读一行数据
    public static void loadDB(CallBack<Void> callBack) throws Exception {
        int num = 0;
        /*List<String> testList= new ArrayList<>();
        testList.add("123");
        testList.add("223");
        testList.add("323");
        testList.add("423");
        testList.add("523");

        Iterator it = testList.iterator();
        while (it.hasNext()) {
            String bdbObj = (String) it.next();
            if(bdbObj != null){
                String line = bdbObj;
                num++;
                callBack.call(num, line);
            }
        }*/

        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("data/testTxt.txt")), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String lineTxt;
        while ((lineTxt = br.readLine()) != null) {
            num++;
            callBack.call(num,lineTxt);
        }
    }

    public static void writeLog(String str, Object... values) {
        System.out.println(str);
    }

    public static void main(String[] args) throws Exception {
        final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreadCount);//10
        final List<Future<String>> threadResultList = new ArrayList<Future<String>>();
        final BufferedWriter bw = initDataWrite(dataFile); // 主要的buffer对象.
        final WriteDataHandle writeDataFile = new WriteDataHandle(DATACACHENUM);//缓存 10000
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        loadDB(new CallBack<Void>() {

            @Override
            public Void call(int num, String data) {
                try {
                    final String tempData = data;
                    // 添加数据，如果超出了缓存数据，则 开始写入文件系统
                    if (writeDataFile.add(tempData)) {
                        currThreadCount++;//0
                        // 如果提交的线程过多，则取回之后再提交.
                        if (currThreadCount >= maxThreadCount) {// 0  10
                            for (Future<String> fs : threadResultList) {
                                String tempDataName = fs.get();
                                currThreadCount--;
                            }
                            threadResultList.clear(); // 清空
                            currThreadCount = threadResultList.size();
                        }
                        Future<String> future = threadPool.submit(new TaskWithResult(writeDataFile, bw));
                        threadResultList.add(future);
                    }

                } catch (Exception e) {
                    writeLog("录入错误的数据：:0", e.getMessage());
                }
                return null;

            }
        });
        writeDataFile.flush(bw);
        threadPool.shutdown();
        stopWatch.stop();
        System.out.println(String.format("任务完成时间:%s ms", stopWatch.getTime()));
    }
}

class TaskWithResult implements Callable<String> {
    WriteDataHandle handle;

    BufferedWriter bufferedWriter;

    public TaskWithResult(WriteDataHandle handle, BufferedWriter bufferedWriter) {
        this.handle = handle;
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    public String call() throws Exception {
        String fileName = Thread.currentThread().getName();

        handle.save(bufferedWriter);

        return fileName;
    }

}

class WriteDataHandle {
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//读写锁

    WriteLock writeLock = readWriteLock.writeLock();

    List<String> cacheList;

    int currItemCount = 0;

    int dataCacheNum;

    public WriteDataHandle() {
        cacheList = new ArrayList<String>();
    }

    public WriteDataHandle(int dataCacheNum) {
        this.dataCacheNum = dataCacheNum;
        cacheList = new ArrayList<String>(dataCacheNum);
    }

    public boolean isCacheExpires() {
        return currItemCount >= dataCacheNum;
    }

    public boolean add(String sqlStr) {
        try {
            writeLock.lock();
            cacheList.add(sqlStr);
            currItemCount++;
            return isCacheExpires();
        } finally {
            writeLock.unlock();
        }
    }

    public void save(BufferedWriter bw) throws Exception {
        try {
            writeLock.lock();
            // 如果数据没有超出缓存.则返回.
            if (!isCacheExpires()) {
                return;
            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (String str : cacheList) {
                bw.write(str + "\r\n");
                currItemCount--;
            }
            stopWatch.stop();
            System.out.println(String.format("%s，消费完成，耗费时间:%s ms,消费数据长度:%s",Thread.currentThread().getName(), stopWatch.getTime(),
                    cacheList.size()));
            cacheList.clear(); // 清空数据.
        } finally {
            writeLock.unlock();
        }
    }

    public void flush(BufferedWriter bw) throws Exception {
        System.out.println(String.format("flush线程：%s, 需要保存数据的集合长度:%s", Thread.currentThread().getName(), cacheList.size()));
        for (String str : cacheList) {
            bw.write(str + "\r\n");
        }
        System.out.println(String.format("flush线程：%s, 消费完成，消费数据长度：%s", Thread.currentThread().getName(), cacheList.size()));
        cacheList.clear(); // 清空数据
        closeWrite(bw);
    }

    private void closeWrite(BufferedWriter bw) throws Exception {
        bw.flush();
        bw.close();
    }

}

class StopWatch {
    long begin;
    long end;

    public void start() {
        begin = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long getTime() {
        return end - begin;
    }
}

interface CallBack<T> {
    T call(int num, String str);
}

