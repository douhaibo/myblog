package cn.ljtnono.myblog.fastdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 *  用于处理FastDFS 文件的上传和下载删除等操作
 *  @author ljt
 *  @date 2019/3/5
 *  @version 1.0
*/
public class FastDFSClient {

    private static final String CONFIG_NAME = "fdfs_client.conf";
    private static final String CONFIG_PATH = FastDFSClient.class.getClassLoader().getResource(CONFIG_NAME).getPath();
    private static StorageServer storageServer = null;
    private static StorageClient1 storageClient1 = null;
    private static TrackerClient trackerClient = null;
    private static TrackerServer trackerServer = null;
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    private static final String HTTP_PREFIX = "https://www.ljtnono.cn/";

    /**
     * 不允许创建实例
     */
    private FastDFSClient(){}

    static {
        try {
            ClientGlobal.init(CONFIG_PATH);
            trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            // 获取连接
            trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                throw new IllegalStateException("getConnection return null");
            }
            storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }
            storageClient1 = new StorageClient1(trackerServer,storageServer);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileName 文件全路径
     * @param extName 文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return 文件在fastdfs 中的存储路径
     * @throws Exception 删除出现错误
     */
    public static String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient1.upload_file1(fileName, extName, metas);
        result = HTTP_PREFIX  + result;
        return result;
    }

    public static String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public static String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }
    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileContent 文件的内容，字节数组
     * @param extName 文件扩展名
     * @param metas 文件扩展信息
     * @throws Exception 删除出现错误
     */
    public static String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient1.upload_file1(fileContent, extName, metas);
        result = HTTP_PREFIX + result;
        return result;
    }

    public static String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public static String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    public static String uploadFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            if (logger.isErrorEnabled()) {
                logger.error("需要上传的文件为空！");
            }
            throw new IllegalArgumentException("需要上传的文件不能为空！");
        }
        final String fileName = file.getOriginalFilename();
        //获取文件的扩展名
        final String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 获取字节数组
        final byte[] filedata = file.getBytes();
        return uploadFile(filedata,extName);
    }

    /**
     * 删除fastdfs中的文件
     * @param id 要删除的文件id
     * @return true 删除成功  false 删除失败
     */
    public static boolean deleteFile(String id) throws Exception {
        return storageClient1.delete_file1(id) != 0;
    }

    public static boolean deleteFile(String groupName,String remoteFilename) throws IOException, MyException {
        return storageClient1.delete_file(groupName,remoteFilename) != 0;
    }

    /**
     * 根据id数组一次删除多个
     * @param ids id数组
     * @return true 删除成功 其他情况抛出异常
     * @throws FastDFSException 操作失败异常并且报错信息
     */
    public static boolean deleteMultiple(String[] ids) throws FastDFSException {
        try {
            for (String id : ids) {
                deleteFile(id);
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("删除错误！原因：" + e.getMessage());
            }
            throw new FastDFSException("删除错误！");
        }
        return true;
    }

}


