package com.pf.play.common.utils.model;

/**
 * @author df
 * @Description:文件check的属性Bean
 * @create 2019-08-14 16:57
 **/
public class FileCheckModel {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件的MD5值
     */
    private String fileMd5;

    /**
     * 数据日期
     */
    private String curday;

    /**
     * 文件生成的日期
     */
    private String wjscDay;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getCurday() {
        return curday;
    }

    public void setCurday(String curday) {
        this.curday = curday;
    }

    public String getWjscDay() {
        return wjscDay;
    }

    public void setWjscDay(String wjscDay) {
        this.wjscDay = wjscDay;
    }
}
