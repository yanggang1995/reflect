package com.metadata.yg.handle;

import com.metadata.yg.utils.DataUtils;
import com.metadata.yg.utils.DateUtils;
import com.metadata.yg.utils.ObjectUtils;
import com.metadata.yg.utils.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.metadata.yg.constant.Conf.*;

public class WriteDataHandle {
    public static final Logger logger = LoggerFactory.getLogger(WriteDataHandle.class);

    public static int index = 0;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();//读写锁

    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private List cacheList;

    int currItemCount = 0;

    int dataCacheNum;

    public WriteDataHandle() {
        cacheList = new ArrayList();
    }

    public WriteDataHandle(int dataCacheNum) {
        this.dataCacheNum = dataCacheNum;
        cacheList = new ArrayList(dataCacheNum);
    }

    public boolean isCacheExpires() {
        return currItemCount >= dataCacheNum;
    }

    public boolean add(List data) {
        try {
            writeLock.lock();
            cacheList.add(data);
            currItemCount++;
            return isCacheExpires();
        } finally {
            writeLock.unlock();
        }
    }

    public void save(String path) throws Exception {
        BufferedOutputStream bw = null;
        try {
            writeLock.lock();
            System.out.println(path + "." + DateUtils.getYesterDay() + "." + index + "." + SUFFIX);
            bw = initDataWrite(new File(path + "." + DateUtils.getYesterDay() + "." + index + "." + SUFFIX));
            // 如果数据没有超出缓存.则返回.
            if (!isCacheExpires()) {
                return;
            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (Object dataList : cacheList) {
                for(Object object:(List)dataList){
                    bw.write(ObjectUtils.getObjectValues(object));
                }

                /*for (int i = 0; i < tmp.size(); i++) {
                    bw.write(tmp.get(i).toString().getBytes());
                    if (i == tmp.size() - 1) {
                        break;
                    }
                    bw.write(FileUtils.radixStr(COLUMN, RADIX));
                }
                bw.write(FileUtils.radixStr(ROW, RADIX));*/
                // TODO: 2018/12/1
                currItemCount--;
            }
            bw.flush();
            stopWatch.stop();
            logger.info(String.format("%s，消费完成，耗费时间:%s ms,消费数据长度:%s", Thread.currentThread().getName(), stopWatch.getTime(),
                    cacheList.size()));
            cacheList.clear(); // 清空数据.
            index++;
        } finally {
            if (bw != null) {
                bw.close();
            }
            writeLock.unlock();
        }
    }

    public void flush(String path) throws Exception {
        BufferedOutputStream bw = null;
        try {
            bw = initDataWrite(new File(path + "." + DateUtils.getYesterDay() + "." + index + "." + SUFFIX));
            for (Object dataList : cacheList) {
                List tmp = (List<String>) dataList;
                for (int i = 0; i < tmp.size(); i++) {
                    bw.write(tmp.get(i).toString().getBytes());
                    if (i == tmp.size() - 1) {
                        break;
                    }
                    bw.write(DataUtils.radixStr(COLUMN, RADIX));
                }
                bw.write(DataUtils.radixStr(ROW, RADIX));
            }
            logger.info(String.format("flush线程：%s, 消费完成，消费数据长度：%s", Thread.currentThread().getName(), cacheList.size()));
            cacheList.clear(); // 清空数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                closeWrite(bw);
            }
        }
    }

    private void closeWrite(BufferedOutputStream bw) throws Exception {
        bw.flush();
        bw.close();
    }

    public BufferedOutputStream initDataWrite(File dataFile) throws Exception {
        if (!dataFile.exists()) {
            if (!dataFile.createNewFile()) {
                logger.info("创建文件失败，已存在：" + dataFile.getAbsolutePath());
            }
        }
        return new BufferedOutputStream(new FileOutputStream(dataFile, true));
    }
}
