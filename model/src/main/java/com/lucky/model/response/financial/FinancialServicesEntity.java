package com.lucky.model.response.financial;


import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 金融服务实体
 *
 * @author wangxiangyi
 * @date 2018/11/07
 */
public class FinancialServicesEntity extends SectionEntity implements Serializable {


    /**
     * 菜单或功能id
     */
    private String id;
    /**
     * 请求类型
     */
    private String pid;
    /**
     * 类型或菜单名称
     */
    private String menuName;
    /**
     * 菜单内容
     */
    private List<FinancialServicesEntity> list;

    /**
     * 是否是新纪录
     */
    private boolean isNewRecord;
    /**
     * 创建时间
     */
    private String createDate;
    /**
     * 更新时间
     */
    private String updateDate;
    /**
     * 排序<p> 序号越小越靠前
     */
    private String parentIds;
    /**
     *
     */
    private int sort;

    /**
     * 图标
     */
    private String icon;
    /**
     * 链接或电话
     */
    private String linkHref;
    /**
     * 跳转标识<p>
     * 1：金融服务：柜台征信<p>
     * 2：在线放款：银行贷款<p>
     * 3：基本知识：客服电话<p>
     * 4：基本知识：进度查询<p>
     * 备注：<p>
     * 2和4一样，都是展示logo+银行名称，点击跳转<p>
     * 3 展示logo+银行名称+电话，点击弹窗拨打电话
     */
    private String jumpMark;
    /**
     * 使用地方<p>
     * 1： APP<p>
     * 2： 微信H5
     */
    private String userPlace;
    /**
     * 是否有效<p>
     * 1：是 <p>
     * 2： 否
     */
    private String isValid;
    /**
     * 父id
     */
    private String parentId;

    /**
     * 标题构造方法
     *
     * @param isHeader 是否是标题 true：是标题 false：不是标题
     * @param header   标题名称
     */
    public FinancialServicesEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    /**
     * 功能构造方法
     *
     * @param isHeader 是否是标题 true：是标题 false：不是标题
     * @param header   标题名称
     * @param icon     功能图标
     * @param menuName 功能名称
     */
    public FinancialServicesEntity(boolean isHeader, String header, String icon, String menuName) {
        super(isHeader, header);
        this.icon = icon;
        this.menuName = menuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<FinancialServicesEntity> getList() {
        return list;
    }

    public void setList(List<FinancialServicesEntity> appMenu) {
        this.list = appMenu;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLinkHref() {
        return linkHref;
    }

    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }

    public String getJumpMark() {
        return jumpMark;
    }

    public void setJumpMark(String jumpMark) {
        this.jumpMark = jumpMark;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    public String isValid() {
        return isValid;
    }

    public void isValid(String isValid) {
        this.isValid = isValid;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
