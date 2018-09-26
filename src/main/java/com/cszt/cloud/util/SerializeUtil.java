package com.cszt.cloud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化对象帮助类
 * 对象的Class必须实现Serializable接口
 * @ClassName: SerializeUtil   
 * @Description: 序列化对象帮助类  
 * @author: tanjin  
 * @date:2018年9月25日 下午3:29:15
 */
public class SerializeUtil {
    private static Logger log = LoggerFactory.getLogger(SerializeUtil.class);

    private SerializeUtil() {

    }

    /**
     * 对象序列化。不能序列化时返回null
     * @Title: serialize   
     * @Description: 对象序列化。不能序列化时返回null  
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            log.info("--------------Object serialize error ! Object is null--------------");
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            log.error("--------------Object serialize error ! :--------------" + e.getMessage(), e);
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (Exception e) {
                log.error("--------------Object serialize cloes stream error ! :--------------" + e.getMessage(), e);
            }
        }
        return null;

    }

    /**
     * 对象反序列化。不能反序列化时返回null
     * @Title: unserialize   
     * @Description: 对象反序列化。不能反序列化时返回null  
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            log.info("--------------Object unserialize error ! Object is null--------------");
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.error("--------------Object unserialize error ! :--------------" + e.getMessage(), e);
        } finally {
            try {
                ois.close();
                bais.close();
            } catch (Exception e) {
                log.error("--------------Object unserialize close stream error ! :--------------" + e.getMessage(), e);
            }
        }
        return null;
    }
}
