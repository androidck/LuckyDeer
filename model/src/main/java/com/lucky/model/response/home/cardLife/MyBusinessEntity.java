package com.lucky.model.response.home.cardLife;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 我的商户返回参数
 *
 * @author wangxiangyi
 * @date 2019/03/19
 */
public class MyBusinessEntity extends SectionEntity implements Serializable {

    /**
     * 间推商户标题
     */
    private String indirectPushTitle;
    /**
     * 间推人数
     */
    private String indirectPushByNum;
    /**
     * 间推商户等级分类集合
     */
    private List<MyBusinessEntity> indirectPushLevelGroupList;
    /**
     * 直推商户标题
     */
    private String directPushTitle;
    /**
     * 直推商户人数
     */
    private String directPushByNum;
    /**
     * 直推商户等级分类集合
     */
    private List<MyBusinessEntity> directPushLevelGroupList;
    /**
     * 团队总人数
     */
    private String countByNum;
    /**
     * 团队vip人数
     */
    private String vipByNum;
    private List<MyBusinessEntity> levelUserGroupCountList;

    /**********************************************商户级别信息*********************************************/

    /**
     * 级别id
     */
    private String levelId;
    /**
     * 级别名称
     */
    private String levelName;
    /**
     * 级别人数
     */
    private String levelCount;
    /**
     * 级别头像
     */
    private String levelIco;

    /*************************************************自定义字段**************************************************/
    /**
     * 商户标题
     */
    private String title;
    /**
     * 商户人数
     */
    private String numberMerchants;
    /**
     * 商户推荐人类型<p>1：直推<p>2：间推传
     */
    private String type;


    public MyBusinessEntity(boolean isHeader, String title, String numberMerchants) {
        super(isHeader, "");
        this.title = title;
        this.numberMerchants = numberMerchants;
    }

    public MyBusinessEntity(boolean isHeader, String type, MyBusinessEntity entity) {
        super(isHeader, "");
        this.type = type;
        levelId = entity.getLevelId();
        levelName = entity.getLevelName();
        levelCount = entity.getLevelCount();
        levelIco = entity.getLevelIco();
    }

    public String getIndirectPushTitle() {
        return indirectPushTitle;
    }

    public void setIndirectPushTitle(String indirectPushTitle) {
        this.indirectPushTitle = indirectPushTitle;
    }

    public String getIndirectPushByNum() {
        return indirectPushByNum;
    }

    public void setIndirectPushByNum(String indirectPushByNum) {
        this.indirectPushByNum = indirectPushByNum;
    }

    public List<MyBusinessEntity> getIndirectPushLevelGroupList() {
        return indirectPushLevelGroupList;
    }

    public void setIndirectPushLevelGroupList(List<MyBusinessEntity> indirectPushLevelGroupList) {
        this.indirectPushLevelGroupList = indirectPushLevelGroupList;
    }

    public String getDirectPushTitle() {
        return directPushTitle;
    }

    public void setDirectPushTitle(String directPushTitle) {
        this.directPushTitle = directPushTitle;
    }

    public String getDirectPushByNum() {
        return directPushByNum;
    }

    public void setDirectPushByNum(String directPushByNum) {
        this.directPushByNum = directPushByNum;
    }

    public List<MyBusinessEntity> getDirectPushLevelGroupList() {
        return directPushLevelGroupList;
    }

    public void setDirectPushLevelGroupList(List<MyBusinessEntity> directPushLevelGroupList) {
        this.directPushLevelGroupList = directPushLevelGroupList;
    }

    public String getCountByNum() {
        return countByNum;
    }

    public void setCountByNum(String countByNum) {
        this.countByNum = countByNum;
    }

    public String getVipByNum() {
        return vipByNum;
    }

    public void setVipByNum(String vipByNum) {
        this.vipByNum = vipByNum;
    }

    public List<MyBusinessEntity> getLevelUserGroupCountList() {
        return levelUserGroupCountList;
    }

    public void setLevelUserGroupCountList(List<MyBusinessEntity> levelUserGroupCountList) {
        this.levelUserGroupCountList = levelUserGroupCountList;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(String levelCount) {
        this.levelCount = levelCount;
    }

    public String getLevelIco() {
        return levelIco;
    }

    public void setLevelIco(String levelIco) {
        this.levelIco = levelIco;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumberMerchants() {
        return numberMerchants;
    }

    public void setNumberMerchants(String numberMerchants) {
        this.numberMerchants = numberMerchants;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
