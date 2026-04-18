package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.model.request.MenuButton;
import com.shazam.wechat.sdk.model.request.MenuCreateRequest;
import com.shazam.wechat.sdk.model.response.MenuCreateResponse;
import com.shazam.wechat.sdk.model.response.MenuDeleteResponse;
import com.shazam.wechat.sdk.model.response.MenuGetResponse;
import com.shazam.wechat.sdk.model.response.MenuInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 微信公众号菜单管理功能单元测试
 */
public class WechatMenuTest {

    /**
     * 测试菜单按钮构建器 - click 类型
     */
    @Test
    public void testMenuButtonClick() {
        MenuButton button = new MenuButton.Builder()
                .name("点击事件")
                .asClick("CLICK_KEY")
                .build();

        assertEquals("点击事件", button.getName());
        assertEquals("click", button.getType());
        assertEquals("CLICK_KEY", button.getKey());
    }

    /**
     * 测试菜单按钮构建器 - view 类型
     */
    @Test
    public void testMenuButtonView() {
        MenuButton button = new MenuButton.Builder()
                .name("访问网页")
                .asView("https://example.com")
                .build();

        assertEquals("访问网页", button.getName());
        assertEquals("view", button.getType());
        assertEquals("https://example.com", button.getUrl());
    }

    /**
     * 测试菜单按钮构建器 - miniprogram 类型
     */
    @Test
    public void testMenuButtonMiniprogram() {
        MenuButton button = new MenuButton.Builder()
                .name("小程序")
                .asMiniprogram("wx1234567890", "pages/index/index", "https://example.com")
                .build();

        assertEquals("小程序", button.getName());
        assertEquals("miniprogram", button.getType());
        assertEquals("wx1234567890", button.getAppId());
        assertEquals("pages/index/index", button.getPagePath());
        assertEquals("https://example.com", button.getUrl());
    }

    /**
     * 测试菜单按钮构建器 - media_id 类型
     */
    @Test
    public void testMenuButtonMediaId() {
        MenuButton button = new MenuButton.Builder()
                .name("图文消息")
                .asMediaId("MEDIA_ID_123")
                .build();

        assertEquals("图文消息", button.getName());
        assertEquals("media_id", button.getType());
        assertEquals("MEDIA_ID_123", button.getMediaId());
    }

    /**
     * 测试菜单按钮构建器 - scancode_push 类型
     */
    @Test
    public void testMenuButtonScanCodePush() {
        MenuButton button = new MenuButton.Builder()
                .name("扫码")
                .asScanCodePush("SCAN_KEY")
                .build();

        assertEquals("扫码", button.getName());
        assertEquals("scancode_push", button.getType());
        assertEquals("SCAN_KEY", button.getKey());
    }

    /**
     * 测试菜单按钮构建器 - 其他扫码类型
     */
    @Test
    public void testMenuButtonScanTypes() {
        MenuButton button1 = new MenuButton.Builder()
                .name("扫码等待消息")
                .asScanCodeWaitMsg("WAIT_KEY")
                .build();
        assertEquals("scancode_waitmsg", button1.getType());

        MenuButton button2 = new MenuButton.Builder()
                .name("系统拍照")
                .asPicSysPhoto("PIC_SYS_KEY")
                .build();
        assertEquals("pic_sysphoto", button2.getType());

        MenuButton button3 = new MenuButton.Builder()
                .name("拍照相册")
                .asPicPhotoOrAlbum("PIC_ALBUM_KEY")
                .build();
        assertEquals("pic_photo_or_album", button3.getType());

        MenuButton button4 = new MenuButton.Builder()
                .name("微信相册")
                .asPicWeiXin("PIC_WEIXIN_KEY")
                .build();
        assertEquals("pic_weixin", button4.getType());

        MenuButton button5 = new MenuButton.Builder()
                .name("地理位置")
                .asLocationSelect("LOCATION_KEY")
                .build();
        assertEquals("location_select", button5.getType());
    }

    /**
     * 测试带二级菜单的按钮
     */
    @Test
    public void testMenuButtonWithSubButtons() {
        MenuButton subButton1 = new MenuButton.Builder()
                .name("子菜单 1")
                .asClick("SUB_1")
                .build();

        MenuButton subButton2 = new MenuButton.Builder()
                .name("子菜单 2")
                .asView("https://example.com/sub")
                .build();

        MenuButton parentButton = new MenuButton.Builder()
                .name("更多功能")
                .addSubButton(subButton1)
                .addSubButton(subButton2)
                .build();

        assertEquals("更多功能", parentButton.getName());
        assertNotNull(parentButton.getSubButtons());
        assertEquals(2, parentButton.getSubButtons().size());
        assertEquals("子菜单 1", parentButton.getSubButtons().get(0).getName());
        assertEquals("子菜单 2", parentButton.getSubButtons().get(1).getName());
    }

    /**
     * 测试创建菜单请求构建器
     */
    @Test
    public void testMenuCreateRequestBuilder() {
        MenuButton button1 = new MenuButton.Builder()
                .name("菜单 1")
                .asClick("MENU_1")
                .build();

        MenuButton button2 = new MenuButton.Builder()
                .name("菜单 2")
                .asView("https://example.com")
                .build();

        MenuCreateRequest request = new MenuCreateRequest.Builder()
                .addButton(button1)
                .addButton(button2)
                .build();

        assertNotNull(request.getButton());
        assertEquals(2, request.getButton().size());
        assertEquals("菜单 1", request.getButton().get(0).getName());
        assertEquals("菜单 2", request.getButton().get(1).getName());
    }

    /**
     * 测试使用 ButtonBuilder 直接添加菜单
     */
    @Test
    public void testMenuCreateRequestWithBuilder() {
        MenuCreateRequest request = new MenuCreateRequest.Builder()
                .addButton(new MenuButton.Builder()
                        .name("直接构建")
                        .asClick("DIRECT_KEY"))
                .build();

        assertNotNull(request.getButton());
        assertEquals(1, request.getButton().size());
        assertEquals("直接构建", request.getButton().get(0).getName());
    }

    /**
     * 测试完整菜单配置（3 个一级菜单，含二级菜单）
     */
    @Test
    public void testFullMenuConfiguration() {
        MenuCreateRequest request = new MenuCreateRequest.Builder()
                // 一级菜单 1 - 点击事件
                .addButton(new MenuButton.Builder()
                        .name("点击事件")
                        .asClick("CLICK_EVENT"))
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
                                .asMediaId("MEDIA_123")))
                .build();

        assertNotNull(request.getButton());
        assertEquals(3, request.getButton().size());

        // 验证一级菜单 3 的二级菜单
        MenuButton parent = request.getButton().get(2);
        assertEquals("更多功能", parent.getName());
        assertNotNull(parent.getSubButtons());
        assertEquals(3, parent.getSubButtons().size());
    }

    /**
     * 测试创建菜单响应
     */
    @Test
    public void testMenuCreateResponse() {
        MenuCreateResponse response = new MenuCreateResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试删除菜单响应
     */
    @Test
    public void testMenuDeleteResponse() {
        MenuDeleteResponse response = new MenuDeleteResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试获取菜单响应
     */
    @Test
    public void testMenuGetResponse() {
        MenuGetResponse response = new MenuGetResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        MenuInfo menu = new MenuInfo();
        menu.setMenuId("MENU_ID_123");

        List<MenuButton> buttons = new ArrayList<>();
        MenuButton button = new MenuButton.Builder()
                .name("测试菜单")
                .asClick("TEST_KEY")
                .build();
        buttons.add(button);
        menu.setButton(buttons);

        response.setMenu(menu);

        assertTrue(response.isSuccess());
        assertNotNull(response.getMenu());
        assertEquals("MENU_ID_123", response.getMenu().getMenuId());
        assertEquals(1, response.getMenu().getButton().size());
    }

    /**
     * 测试菜单按钮类型常量
     */
    @Test
    public void testMenuButtonTypes() {
        MenuButton clickButton = new MenuButton.Builder().type("click").build();
        assertEquals("click", clickButton.getType());

        MenuButton viewButton = new MenuButton.Builder().type("view").build();
        assertEquals("view", viewButton.getType());

        MenuButton miniButton = new MenuButton.Builder().type("miniprogram").build();
        assertEquals("miniprogram", miniButton.getType());
    }
}
