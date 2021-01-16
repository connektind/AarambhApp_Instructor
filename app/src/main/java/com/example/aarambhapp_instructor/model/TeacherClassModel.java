package com.example.aarambhapp_instructor.model;

public class TeacherClassModel {
    String liveId;
    String classId;
    String schoolId;
    String liveClass;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
    String studentclass;

    public TeacherClassModel(String liveId, String classId, String schoolId, String liveClass, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate,String studentclass) {
    this.liveId=liveId;
    this.classId=classId;
    this.schoolId=schoolId;
    this.liveClass=liveClass;
    this.statusId=statusId;
    this.createdById=createdById;
    this.modifiedById=modifiedById;
    this.creationDate=creationDate;
    this.modificationDate=modificationDate;
    this.studentclass=studentclass;
    }

    public String getStudentclass() {
        return studentclass;
    }

    public void setStudentclass(String studentclass) {
        this.studentclass = studentclass;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getLiveClass() {
        return liveClass;
    }

    public void setLiveClass(String liveClass) {
        this.liveClass = liveClass;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
