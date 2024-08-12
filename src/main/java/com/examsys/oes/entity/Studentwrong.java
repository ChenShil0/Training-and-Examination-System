package com.examsys.oes.entity;

public class Studentwrong {
    private Integer studentId;

    private String wrongmulti;

    private String wrongjudge;

    private String wrongfill;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }


    public String getWrongmulti() {
        return wrongmulti;
    }

    public void setWrongmulti(String wrongmulti) {
        this.wrongmulti = wrongmulti == null ? null : wrongmulti.trim();
    }

    public String getWrongjudge() {
        return wrongjudge;
    }

    public void setWrongjudge(String wrongjudge) {
        this.wrongjudge = wrongjudge == null ? null : wrongjudge.trim();
    }

    public String getWrongfill() {
        return wrongfill;
    }

    public void setWrongfill(String wrongfill) {
        this.wrongfill = wrongfill == null ? null : wrongfill.trim();
    }


}
