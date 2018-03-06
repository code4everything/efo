package com.zhazhapan.efo.model;

/**
 * @author pantao
 * @since 2018/3/6
 */
public class BaseAuthRecord {

    private int isDownloadable;

    private int isUploadable;

    private int isDeletable;

    private int isUpdatable;

    private int isVisible;

    public BaseAuthRecord(int isDownloadable, int isUploadable, int isDeletable, int isUpdatable, int isVisible) {
        this.isDownloadable = isDownloadable;
        this.isUploadable = isUploadable;
        this.isDeletable = isDeletable;
        this.isUpdatable = isUpdatable;
        this.isVisible = isVisible;
    }

    public int getIsDownloadable() {
        return isDownloadable;
    }

    public void setIsDownloadable(int isDownloadable) {
        this.isDownloadable = isDownloadable;
    }

    public int getIsUploadable() {
        return isUploadable;
    }

    public void setIsUploadable(int isUploadable) {
        this.isUploadable = isUploadable;
    }

    public int getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(int isDeletable) {
        this.isDeletable = isDeletable;
    }

    public int getIsUpdatable() {
        return isUpdatable;
    }

    public void setIsUpdatable(int isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
}
