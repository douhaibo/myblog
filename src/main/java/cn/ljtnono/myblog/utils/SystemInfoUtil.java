package cn.ljtnono.myblog.utils;

import org.junit.Test;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;


/**
 *  获取服务器系统参数的工具类
 *  @author ljt
 *  @date 2019/3/14
 *  @version 1.0
*/
public class SystemInfoUtil {

    private static HardwareAbstractionLayer hardwareAbstractionLayer ;
    private static OperatingSystem operatingSystem;
    private static SystemInfo systemInfo;
    /**
     *  TODO 工具类不允许实例化
     */
    public SystemInfoUtil() {}

    static {
        systemInfo = new SystemInfo();
        hardwareAbstractionLayer = systemInfo.getHardware();
        operatingSystem = systemInfo.getOperatingSystem();
    }

    @Test
    public  void display() {
        System.out.println(hardwareAbstractionLayer.getMemory().getSwapUsed());
    }

    /**
     * 获取内存总的占用情况
     */
    @Test
    public void getMemoryUsed() {
        double total = (double) hardwareAbstractionLayer.getMemory().getTotal();
        double available = (double) hardwareAbstractionLayer.getMemory().getAvailable();
        double use = total - available;
        double useRate = use / total;
        System.out.println(useRate);
    }

    /**
     * 获取系统cpu的占用情况
     * 返回所有cpu最近时间内CPU的使用率double数
     * 要求保留两位小数
     */
    @Test
    public void getProcessorCpuLoadBetweenTicks() {
        double[] processorCpuLoadBetweenTicks = hardwareAbstractionLayer.getProcessor().getProcessorCpuLoadBetweenTicks();
        for (double processorCpuLoadBetweenTick : processorCpuLoadBetweenTicks) {
            System.out.println(processorCpuLoadBetweenTick);
        }
    }

    /**
     * 获取频繁使用的进程的信息
     * 包括该进程占用的内存 以M 为单位
     * 该进程占用的COU(如果没有的话就不用）
     * 该进程的命令是什么
     */
    @Test
    public void getFrequentProcessInfo() {
        OSProcess[] processes = operatingSystem.getProcesses(0, null);
        for (OSProcess process : processes) {
            System.out.println(process.getName() + " " + process.getBytesRead());
        }
    }
}
