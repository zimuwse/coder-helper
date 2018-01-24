package org.lr.helper.service;

import org.lr.helper.config.Mysql2JavaTypeMappingConf;
import org.lr.helper.model.*;
import org.lr.helper.ui.TaskProgressFrame;
import org.lr.helper.util.DBUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zimuwse
 * @time: 2018-01-23 14:17
 * @description:
 */
public class Mysql2JavaJob implements Runnable {
    private int total;
    private Queue<String> queue;
    private DB2JavaMybatisVO vo;
    private TaskProgressFrame frame;

    public Mysql2JavaJob(DB2JavaMybatisVO vo, int width, int height, String title) {
        this.vo = vo;
        this.frame = new TaskProgressFrame(title);
        this.total = vo.getTables().size();
        this.queue = new LinkedBlockingQueue<>(vo.getTables());
        frame.show(width, height);
    }

    @Override
    public void run() {
        int threadCount = 1;
        if (total > 20) {
            threadCount = total / 20;
        }
        if (threadCount > 4) {
            threadCount = 4;
        }
        final long time = System.currentTimeMillis();
        final AtomicInteger threadCounter = new AtomicInteger(threadCount);
        final boolean isJavaBean = GenFileTypeOptions.isJavaBean(vo.getOption());
        final boolean isMybatisMapper = GenFileTypeOptions.isMybatisMapper(vo.getOption());
        final boolean isMybatisxml = GenFileTypeOptions.isMybatisXml(vo.getOption());
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String tab = null;
                    List<KV> mappings = new Mysql2JavaTypeMappingConf().get();
                    String threadData = "[Thread:" + Thread.currentThread().getName() + "]";
                    try {
                        Connection connection = DBUtils.getConnection(vo);
                        while ((tab = queue.poll()) != null) {
                            frame.append(threadData + "[Creating Table(" + tab + ") Files Start ...]");
                            DBTable dbTable = null;
                            try {
                                dbTable = DBUtils.getTableDetail(connection, tab);
                            } catch (Exception e) {
                                frame.append(threadData + "[ERROR]table:" + tab + ",detail:" + e.getMessage());
                            }
                            if (null == dbTable)
                                continue;
                            JavaEntity javaEntity = new JavaEntity(dbTable, vo.getAuthor(), vo.getBeanSuffix(), vo.getBeanPkg(), vo.getPrefixes(), mappings);
                            JavaMapper javaMapper = new JavaMapper(javaEntity.getName(), vo.getMapperSuffix(), vo.getMapperPkg(), javaEntity);
                            JavaXmlMapper javaXmlMapper = new JavaXmlMapper(vo.getMapperLocation(), javaMapper);
                            if (isJavaBean) {
                                boolean result = new JavaFile(vo.getBeanLocation(), javaEntity).save();
                                frame.append(threadData + "[Create][result=" + result + "]" + vo.getBeanLocation() + "/" + javaEntity.fileName());
                            }
                            if (isMybatisMapper) {
                                boolean result = new JavaFile(vo.getMapperLocation(), javaMapper).save();
                                frame.append(threadData + "[Create][result=" + result + "]" + vo.getMapperLocation() + "/" + javaMapper.fileName());
                            }
                            if (isMybatisxml) {
                                boolean result = new JavaFile(vo.getXmlLocation(), javaXmlMapper).save();
                                frame.append(threadData + "[Create][result=" + result + "]" + vo.getXmlLocation() + "/" + javaXmlMapper.fileName());
                            }
                            frame.append("[Creating Table(" + tab + ") Files End]");
                        }
                    } catch (Exception e) {
                        frame.append(threadData + "[ERROR]table:" + tab + ",detail:" + e.getMessage());
                    } finally {
                        threadCounter.decrementAndGet();
                    }
                }
            }).start();
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (queue.isEmpty()) {
                    frame.updateProgress(100, "100%");
                    if (0 == threadCounter.get()) {
                        frame.finish(System.currentTimeMillis() - time);
                        timer.cancel();
                    }
                } else {
                    int remain = queue.size();
                    int per = (total - remain) * 100 / total;
                    frame.updateProgress(per, per + "%");
                }
            }
        }, 0, 5);
    }
}
