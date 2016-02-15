package com.tianfang.train.pojo;

import java.util.Date;

public class CompetitionTeam {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.comp_id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private String compId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.team_id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private String teamId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.win
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Integer win;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.draw
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Integer draw;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.lose
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Integer lose;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.goal_in
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Integer goalIn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.goal_out
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Integer goalOut;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.create_time
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.last_update_time
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sst_competition_team.stat
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    private Integer stat;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.id
     *
     * @return the value of sst_competition_team.id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.id
     *
     * @param id the value for sst_competition_team.id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.comp_id
     *
     * @return the value of sst_competition_team.comp_id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public String getCompId() {
        return compId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.comp_id
     *
     * @param compId the value for sst_competition_team.comp_id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setCompId(String compId) {
        this.compId = compId == null ? null : compId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.team_id
     *
     * @return the value of sst_competition_team.team_id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.team_id
     *
     * @param teamId the value for sst_competition_team.team_id
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.win
     *
     * @return the value of sst_competition_team.win
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Integer getWin() {
        return win;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.win
     *
     * @param win the value for sst_competition_team.win
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setWin(Integer win) {
        this.win = win;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.draw
     *
     * @return the value of sst_competition_team.draw
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Integer getDraw() {
        return draw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.draw
     *
     * @param draw the value for sst_competition_team.draw
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.lose
     *
     * @return the value of sst_competition_team.lose
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Integer getLose() {
        return lose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.lose
     *
     * @param lose the value for sst_competition_team.lose
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setLose(Integer lose) {
        this.lose = lose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.goal_in
     *
     * @return the value of sst_competition_team.goal_in
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Integer getGoalIn() {
        return goalIn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.goal_in
     *
     * @param goalIn the value for sst_competition_team.goal_in
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setGoalIn(Integer goalIn) {
        this.goalIn = goalIn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.goal_out
     *
     * @return the value of sst_competition_team.goal_out
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Integer getGoalOut() {
        return goalOut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.goal_out
     *
     * @param goalOut the value for sst_competition_team.goal_out
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setGoalOut(Integer goalOut) {
        this.goalOut = goalOut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.create_time
     *
     * @return the value of sst_competition_team.create_time
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.create_time
     *
     * @param createTime the value for sst_competition_team.create_time
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.last_update_time
     *
     * @return the value of sst_competition_team.last_update_time
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.last_update_time
     *
     * @param lastUpdateTime the value for sst_competition_team.last_update_time
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sst_competition_team.stat
     *
     * @return the value of sst_competition_team.stat
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sst_competition_team.stat
     *
     * @param stat the value for sst_competition_team.stat
     *
     * @mbggenerated Mon Feb 01 11:07:44 CST 2016
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }
}