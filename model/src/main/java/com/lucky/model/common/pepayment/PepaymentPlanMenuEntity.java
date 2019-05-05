package com.lucky.model.common.pepayment;

/**
 * 计划还款菜单实体
 *
 * @author wangxingyi
 * @date 2018/12/11
 */
public class PepaymentPlanMenuEntity {
    /**
     * 菜单背景颜色
     */
    private int menuBackground;
    /**
     * 菜单图片
     */
    private int menuImage;
    /**
     * 菜单标题
     */
    private String menuTitle;
    /**
     * 菜单内容
     */
    private String menuContent;

    /**
     * @param menuBackground 菜单背景颜色
     * @param menuImage      菜单图片
     * @param menuTitle      菜单标题
     * @param menuContent    菜单内容
     */
    public PepaymentPlanMenuEntity(int menuBackground, int menuImage, String menuTitle, String menuContent) {
        this.menuBackground = menuBackground;
        this.menuImage = menuImage;
        this.menuTitle = menuTitle;
        this.menuContent = menuContent;
    }

    public int getMenuBackground() {
        return menuBackground;
    }

    public void setMenuBackground(int menuBackground) {
        this.menuBackground = menuBackground;
    }

    public int getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(int menuImage) {
        this.menuImage = menuImage;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuContent() {
        return menuContent;
    }

    public void setMenuContent(String menuContent) {
        this.menuContent = menuContent;
    }
}
