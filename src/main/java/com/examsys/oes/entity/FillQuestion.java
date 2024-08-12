package com.examsys.oes.entity;

import com.baomidou.mybatisplus.annotation.TableName;

//填空题实体类
@TableName("ONLINE_EXAM.ADMIN")
public class FillQuestion {
    private Integer questionId;

    private String subject;

    private String question;

    private String answer;

    private Integer score;

    private String level;

    private String chapter;

    private String analysis; //题目解析

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }



    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return "FillQuestion{" +
                "questionId=" + questionId +
                ", subject='" + subject + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                ", level='" + level + '\'' +
                ", section='" + chapter + '\'' +
                ", analysis='" + analysis + '\'' +
                '}';
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
