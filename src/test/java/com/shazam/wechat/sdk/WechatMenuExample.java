package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.model.request.MenuButton;
import com.shazam.wechat.sdk.model.request.MenuCreateRequest;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 微信公众号菜单管理功能使用示例
 *
 * 注意：此示例需要真实的 AppID 和 AppSecret 才能运行
 */
public class WechatMenuExample {

    public static void main(String[] args) {
        // 1. 创建客户端
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")           // 替换为你的 AppID
                .appSecret("your_app_secret")   // 替换为你的 AppSecret
                .build();

        WechatClient client = new WechatClient(config);

        try {
            // 2. 获取 access_token（会自动缓存）
            AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
            System.out.println("Access Token: " + tokenResp.getAccess_token());

            // 3. 创建菜单
            MenuCreateRequest request = new MenuCreateRequest.Builder()
                    // 一级菜单 1 - 点击事件
                    .addButton(new MenuButton.Builder()
                            .name("点击事件")
                            .asClick("CLICK_EVENT_KEY"))
                    // 一级菜单 2 - 跳转网页
                    .addButton(new MenuButton.Builder()
                            .name("访问官网")
                            .asView("https://example.com"))
                    // 一级菜单 3 - 带二级菜单
                    .addButton(new MenuButton.Builder()
                            .name("更多功能")
                            .addSubButton(new MenuButton.Builder()
                                    .name("功能 1")
                                    .asClick("FUNC_1"))
                            .addSubButton(new MenuButton.Builder()
                                    .name("功能 2")
                                    .asView("https://example.com/func2"))
                            .addSubButton(new MenuButton.Builder()
                                    .name("功能 3")
                                    .asMediaId("MEDIA_ID_123")))
                    .build();

            MenuCreateResponse response = client.menu().createMenu(request);
            if (response.isSuccess()) {
                System.out.println("菜单创建成功！");
                System.out.println("菜单将在 24 小时内生效");
            }

        } catch (Exception e) {
            System.err.println("操作失败：" + e.getMessage());
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 完整菜单配置示例（包含所有按钮类型）
     */
    public static void createFullMenu() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            MenuCreateRequest request = new MenuCreateRequest.Builder()
                    // 点击推事件
                    .addButton(new MenuButton.Builder()
                            .name("点击事件")
                            .asClick("CLICK_EVENT"))
                    // 跳转网页
                    .addButton(new MenuButton.Builder()
                            .name("访问网页")
                            .asView("https://example.com"))
                    // 跳转小程序
                    .addButton(new MenuButton.Builder()
                            .name("小程序")
                            .asMiniprogram(
                                    "wx1234567890abcdef",
                                    "pages/index/index",
                                    "https://example.com"))
                    // 扫码推事件
                    .addButton(new MenuButton.Builder()
                            .name("扫码")
                            .asScanCodePush("SCAN_CODE"))
                    // 扫码推事件且弹出"消息接收中"
                    .addButton(new MenuButton.Builder()
                            .name("扫码等待")
                            .asScanCodeWaitMsg("SCAN_WAIT"))
                    // 系统拍照发图
                    .addButton(new MenuButton.Builder()
                            .name("系统拍照")
                            .asPicSysPhoto("PIC_SYS"))
                    // 拍照或相册发图
                    .addButton(new MenuButton.Builder()
                            .name("拍照相册")
                            .asPicPhotoOrAlbum("PIC_ALBUM"))
                    // 微信相册发图器
                    .addButton(new MenuButton.Builder()
                            .name("微信相册")
                            .asPicWeiXin("PIC_WEIXIN"))
                    // 地理位置选择器
                    .addButton(new MenuButton.Builder()
                            .name("地理位置")
                            .asLocationSelect("LOCATION"))
                    // 下发消息
                    .addButton(new MenuButton.Builder()
                            .name("图文消息")
                            .asMediaId("MEDIA_ID"))
                    // 跳转图文消息
                    .addButton(new MenuButton.Builder()
                            .name("图文链接")
                            .asViewLimited("MEDIA_ID"))
                    // 带二级菜单
                    .addButton(new MenuButton.Builder()
                            .name("更多")
                            .addSubButton(new MenuButton.Builder()
                                    .name("子菜单 1")
                                    .asClick("SUB_1"))
                            .addSubButton(new MenuButton.Builder()
                                    .name("子菜单 2")
                                    .asView("https://example.com/sub"))
                            .addSubButton(new MenuButton.Builder()
                                    .name("子菜单 3")
                                    .asMiniprogram(
                                            "wx1234567890abcdef",
                                            "pages/sub/index",
                                            "https://example.com")))
                    .build();

            MenuCreateResponse response = client.menu().createMenu(request);
            if (response.isSuccess()) {
                System.out.println("完整菜单创建成功！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 查询菜单示例
     */
    public static void getMenu() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            MenuGetResponse response = client.menu().getMenu();

            if (response.isSuccess()) {
                MenuInfo menu = response.getMenu();
                if (menu != null) {
                    System.out.println("菜单 ID: " + menu.getMenuId());
                    System.out.println("菜单数量：" + menu.getButton().size());

                    for (MenuButton button : menu.getButton()) {
                        System.out.println("一级菜单：" + button.getName());
                        System.out.println("  类型：" + button.getType());
                        System.out.println("  Key/URL: " + button.getKey() + " / " + button.getUrl());

                        if (button.getSubButtons() != null) {
                            for (MenuButton sub : button.getSubButtons()) {
                                System.out.println("    二级菜单：" + sub.getName());
                                System.out.println("      类型：" + sub.getType());
                            }
                        }
                    }
                } else {
                    System.out.println("当前没有菜单");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 删除菜单示例
     */
    public static void deleteMenu() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            MenuDeleteResponse response = client.menu().deleteMenu();

            if (response.isSuccess()) {
                System.out.println("菜单删除成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 简单菜单示例（快速开始）
     */
    public static void createSimpleMenu() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            // 最简单的菜单配置：3 个一级菜单，无二级菜单
            MenuCreateRequest request = new MenuCreateRequest.Builder()
                    .addButton(new MenuButton.Builder()
                            .name("菜单 1")
                            .asClick("MENU_1"))
                    .addButton(new MenuButton.Builder()
                            .name("菜单 2")
                            .asView("https://example.com"))
                    .addButton(new MenuButton.Builder()
                            .name("菜单 3")
                            .asClick("MENU_3"))
                    .build();

            MenuCreateResponse response = client.menu().createMenu(request);
            if (response.isSuccess()) {
                System.out.println("简单菜单创建成功！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }
}
