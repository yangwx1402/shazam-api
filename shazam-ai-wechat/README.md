# 微信公众号 SDK (WeChat SDK for Java)

[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.0+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

一个简洁、高效的微信公众号 API SDK，提供认证、素材管理、草稿箱管理、发布、菜单管理、留言管理等功能的封装。

---

## 特性

- ✅ **简洁 API** - 流式构建器模式，代码清晰易读
- ✅ **自动 Token 管理** - 内置缓存机制，自动刷新 access_token
- ✅ **完整功能** - 支持认证、素材、草稿、发布、菜单、留言等核心 API
- ✅ **异常处理** - 统一的异常处理机制，详细的错误码说明
- ✅ **易于扩展** - 模块化设计，支持自定义扩展

---

## 快速开始

### 1. 添加依赖

```xml
<dependencies>
    <!-- JSON 处理 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>

    <!-- 日志 API -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>

    <!-- 测试依赖 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### 2. 创建客户端

```java
import com.shazam.wechat.sdk.WechatClient;
import com.shazam.wechat.sdk.WechatConfig;

// 创建配置
WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")           // 替换为你的 AppID
    .appSecret("your_app_secret")   // 替换为你的 AppSecret
    .build();

// 创建客户端
WechatClient client = new WechatClient(config);
```

### 3. 发布文章（完整流程）

```java
// 1. 获取 access_token（自动缓存）
AccessTokenResponse tokenResp = client.auth().getStableAccessToken();

// 2. 上传封面图片
File coverImage = new File("/path/to/cover.jpg");
UploadImageResponse imgResp = client.material().uploadImage(coverImage);

// 3. 创建草稿
DraftArticle article = new DraftArticle.Builder()
    .title("我的文章标题")
    .author("作者名")
    .content("<h1>文章标题</h1><p>文章 HTML 内容...</p>")
    .digest("文章摘要")
    .thumbMediaId(imgResp.getUrl())
    .needOpenComment(1)
    .onlyFansCanComment(0)
    .contentSourceUrl("https://example.com")
    .build();

DraftAddRequest draftRequest = new DraftAddRequest.Builder()
    .addArticle(article)
    .build();

DraftAddResponse draftResp = client.draft().addDraft(draftRequest);
String mediaId = draftResp.getMediaId();

// 4. 提交发布
PublishSubmitRequest publishRequest = new PublishSubmitRequest.Builder()
    .mediaId(mediaId)
    .build();

PublishSubmitResponse publishResp = client.publish().submitPublish(publishRequest);
String publishId = publishResp.getPublishId();

// 5. 查询发布状态
Thread.sleep(3000);  // 等待发布完成
PublishGetResponse statusResp = client.publish().getPublishStatus(publishId);

if (statusResp.isPublished()) {
    System.out.println("发布成功！文章链接：" + statusResp.getArticleUrl());
}

// 6. 关闭客户端
client.shutdown();
```

### 4. 创建自定义菜单

```java
// 创建菜单
MenuCreateRequest menuRequest = new MenuCreateRequest.Builder()
    .addButton(new MenuButton.Builder()
        .name("点击事件")
        .asClick("CLICK_KEY"))
    .addButton(new MenuButton.Builder()
        .name("访问网页")
        .asView("https://example.com"))
    .addButton(new MenuButton.Builder()
        .name("更多功能")
        .addSubButton(new MenuButton.Builder()
            .name("子菜单 1")
            .asClick("SUB_1"))
        .addSubButton(new MenuButton.Builder()
            .name("子菜单 2")
            .asView("https://example.com/sub"))
        .build())
    .build();

MenuCreateResponse menuResponse = client.menu().createMenu(menuRequest);
if (menuResponse.isSuccess()) {
    System.out.println("菜单创建成功！");
}

// 查询菜单
MenuGetResponse menuGetResponse = client.menu().getMenu();

// 删除菜单
MenuDeleteResponse deleteResponse = client.menu().deleteMenu();
```

### 5. 留言管理 API

```java
// 打开评论
CommentOpenRequest openRequest = new CommentOpenRequest.Builder()
    .msgDataId(1234567890L)
    .index(0)
    .build();
client.comment().openComment(openRequest);

// 查看评论
CommentGetRequest getRequest = new CommentGetRequest.Builder()
    .msgDataId(1234567890L)
    .begin(0)
    .count(20)
    .build();
CommentGetResponse getResponse = client.comment().getComments(getRequest);

// 标记精选
CommentMarkRequest markRequest = new CommentMarkRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .build();
client.comment().markComment(markRequest);

// 回复评论
CommentReplyRequest replyRequest = new CommentReplyRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .content("感谢支持！")
    .build();
client.comment().replyComment(replyRequest);

// 关闭评论
CommentCloseRequest closeRequest = new CommentCloseRequest.Builder()
    .msgDataId(1234567890L)
    .build();
client.comment().closeComment(closeRequest);
```

---

## 功能模块

### 认证 API (`client.auth()`)

```java
// 获取稳定版 access_token（推荐）
AccessTokenResponse token = client.auth().getStableAccessToken();

// 强制刷新 access_token
AccessTokenResponse token = client.auth().getStableAccessToken(true);

// 获取普通版 access_token（不推荐）
AccessTokenResponse token = client.auth().fetchNewAccessToken();
```

### 素材管理 API (`client.material()`)

```java
// 上传图片
UploadImageResponse response = client.material().uploadImage(new File("cover.jpg"));
String imageUrl = response.getUrl();
```

### 草稿管理 API (`client.draft()`)

```java
// 新增草稿
DraftAddRequest request = new DraftAddRequest.Builder()
    .addArticle(new DraftArticle.Builder()
        .title("标题")
        .content("内容")
        .thumbMediaId("media_id")
        .build())
    .build();
DraftAddResponse response = client.draft().addDraft(request);

// 获取草稿详情
DraftGetResponse response = client.draft().getDraft("media_id");

// 删除草稿
DraftDeleteResponse response = client.draft().deleteDraft("media_id");

// 更新草稿
DraftUpdateRequest request = new DraftUpdateRequest.Builder()
    .mediaId("media_id")
    .index(0)
    .articles(articleBuilder)
    .build();
DraftUpdateResponse response = client.draft().updateDraft(request);

// 获取草稿列表
DraftBatchGetResponse response = client.draft().batchGetDrafts(0, 20);

// 获取草稿总数
DraftCountResponse response = client.draft().countDrafts();
```

### 发布管理 API (`client.publish()`)

```java
// 提交发布
PublishSubmitRequest request = new PublishSubmitRequest.Builder()
    .mediaId("media_id")
    .build();
PublishSubmitResponse response = client.publish().submitPublish(request);

// 查询发布状态
PublishGetResponse response = client.publish().getPublishStatus("publish_id");
```

### 菜单管理 API (`client.menu()`)

```java
// 创建菜单
MenuCreateRequest request = new MenuCreateRequest.Builder()
    .addButton(new MenuButton.Builder()
        .name("点击事件")
        .asClick("CLICK_KEY"))
    .addButton(new MenuButton.Builder()
        .name("访问网页")
        .asView("https://example.com"))
    .addButton(new MenuButton.Builder()
        .name("更多")
        .addSubButton(new MenuButton.Builder()
            .name("子菜单 1")
            .asClick("SUB_1"))
        .addSubButton(new MenuButton.Builder()
            .name("子菜单 2")
            .asView("https://example.com/sub"))
        .build())
    .build();
MenuCreateResponse response = client.menu().createMenu(request);

// 查询菜单
MenuGetResponse response = client.menu().getMenu();

// 删除菜单
MenuDeleteResponse response = client.menu().deleteMenu();
```

### 留言管理 API (`client.comment()`)

```java
// 打开评论
CommentOpenRequest request = new CommentOpenRequest.Builder()
    .msgDataId(1234567890L)
    .index(0)
    .build();
client.comment().openComment(request);

// 查看评论
CommentGetRequest request = new CommentGetRequest.Builder()
    .msgDataId(1234567890L)
    .begin(0)
    .count(20)
    .build();
CommentGetResponse response = client.comment().getComments(request);

// 标记精选
CommentMarkRequest request = new CommentMarkRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .build();
client.comment().markComment(request);

// 取消精选
CommentUnmarkRequest request = new CommentUnmarkRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .build();
client.comment().unmarkComment(request);

// 删除评论
CommentDeleteRequest request = new CommentDeleteRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .build();
client.comment().deleteComment(request);

// 回复评论
CommentReplyRequest request = new CommentReplyRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .content("感谢支持！")
    .build();
client.comment().replyComment(request);

// 删除回复
CommentDeleteReplyRequest request = new CommentDeleteReplyRequest.Builder()
    .msgDataId(1234567890L)
    .commentId(987654321L)
    .build();
client.comment().deleteReply(request);

// 关闭评论
CommentCloseRequest request = new CommentCloseRequest.Builder()
    .msgDataId(1234567890L)
    .build();
client.comment().closeComment(request);
```

### 回调检测 API (`client.callback()`)

```java
// 网络通信检测
CallbackCheckResponse response = client.callback().check();
```

---

## 高级配置

### 自定义超时时间

```java
WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")
    .appSecret("your_app_secret")
    .connectTimeout(10000)   // 连接超时 10 秒
    .readTimeout(30000)      // 读取超时 30 秒
    .build();
```

### 自定义 Token 缓存

```java
// 实现 TokenCache 接口（如使用 Redis）
TokenCache customCache = new RedisTokenCache();

WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")
    .appSecret("your_app_secret")
    .tokenCache(customCache)
    .build();
```

### 多图文消息发布

```java
DraftArticle article1 = new DraftArticle.Builder()
    .title("第一篇标题")
    .content("第一篇内容...")
    .thumbMediaId("media_id_1")
    .build();

DraftArticle article2 = new DraftArticle.Builder()
    .title("第二篇标题")
    .content("第二篇内容...")
    .thumbMediaId("media_id_2")
    .build();

DraftAddRequest request = new DraftAddRequest.Builder()
    .addArticle(article1)
    .addArticle(article2)
    .build();

DraftAddResponse response = client.draft().addDraft(request);
```

### 菜单按钮类型

| 类型 | 方法 | 说明 |
|------|------|------|
| `click` | `asClick(key)` | 点击推事件 |
| `view` | `asView(url)` | 跳转网页 |
| `miniprogram` | `asMiniprogram(appId, pagePath, url)` | 跳转小程序 |
| `media_id` | `asMediaId(mediaId)` | 下发消息 |
| `view_limited` | `asViewLimited(mediaId)` | 跳转图文 |
| `scancode_push` | `asScanCodePush(key)` | 扫码推事件 |
| `scancode_waitmsg` | `asScanCodeWaitMsg(key)` | 扫码 + 弹出提示 |
| `pic_sysphoto` | `asPicSysPhoto(key)` | 系统拍照 |
| `pic_photo_or_album` | `asPicPhotoOrAlbum(key)` | 拍照或相册 |
| `pic_weixin` | `asPicWeiXin(key)` | 微信相册 |
| `location_select` | `asLocationSelect(key)` | 地理位置 |

### 留言管理参数

| 参数 | 说明 |
|------|------|
| `msg_data_id` | 群发返回的图文消息 ID（必填） |
| `index` | 多图文时指定第几篇（从 0 开始） |
| `comment_id` | 评论 ID |
| `content` | 回复内容（不超过 200 字） |

### 菜单配置示例

```java
// 完整菜单配置（包含所有按钮类型）
MenuCreateRequest request = new MenuCreateRequest.Builder()
    // 点击推事件
    .addButton(new MenuButton.Builder()
        .name("点击事件")
        .asClick("CLICK_EVENT"))
    // 跳转网页
    .addButton(new MenuButton.Builder()
        .name("访问官网")
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
    // 带二级菜单
    .addButton(new MenuButton.Builder()
        .name("更多功能")
        .addSubButton(new MenuButton.Builder()
            .name("子菜单 1")
            .asClick("SUB_1"))
        .addSubButton(new MenuButton.Builder()
            .name("子菜单 2")
            .asView("https://example.com/sub"))
        .build())
    .build();

client.menu().createMenu(request);
```

---

## 错误处理

```java
import com.shazam.wechat.sdk.exception.WechatApiException;

try {
    DraftAddResponse response = client.draft().addDraft(request);
} catch (WechatApiException e) {
    System.err.println("错误码：" + e.getErrorCode());
    System.err.println("错误消息：" + e.getErrorMsg());
    
    // 根据错误码处理
    switch (e.getErrorCode()) {
        case 40001:
            System.err.println("Token 无效，请检查 access_token");
            break;
        case 40007:
            System.err.println("素材 media_id 不存在");
            break;
        case 45009:
            System.err.println("超过频率限制");
            break;
        default:
            System.err.println("其他错误：" + e.getErrorMsg());
    }
}
```

### 常见错误码

| 错误码 | 说明 | 解决方案 |
|-------|------|---------|
| 40001 | access_token 无效 | 检查 token 是否过期 |
| 40007 | 素材 media_id 不存在 | 检查 media_id 是否正确 |
| 40013 | AppID 不合法 | 检查 AppID 配置 |
| 40125 | AppSecret 无效 | 检查 AppSecret 配置 |
| 40164 | IP 不在白名单 | 在微信后台添加 IP 白名单 |
| 45009 | 超过频率限制 | 降低调用频率或调用 clear_quota |
| 53404 | 账号被限制带货能力 | 删除商品后重试 |
| 88000 | 没有留言权限 | 确认账号类型和权限 |
| 88001 | 图文不存在 | 检查 msg_data_id 是否正确 |
| 88002 | 文章存在敏感信息 | 检查文章内容 |
| 88003 | 评论不存在 | 检查 comment_id 是否正确 |
| 88004 | 回复内容过长 | 缩短回复内容至 200 字以内 |

---

## 项目结构

```
src/main/java/com/shazam/wechat/sdk/
├── WechatClient.java              # 客户端主入口
├── WechatConfig.java              # 配置类
├── api/
│   ├── WechatApi.java             # API 基础接口
│   ├── AuthApi.java               # 认证 API
│   ├── CallbackApi.java           # 回调检测 API
│   ├── material/
│   │   └── MaterialApi.java       # 素材管理 API
│   ├── draft/
│   │   └── DraftApi.java          # 草稿管理 API
│   ├── publish/
│   │   └── PublishApi.java        # 发布管理 API
│   ├── menu/
│   │   └── MenuApi.java           # 菜单管理 API
│   └── comment/
│       └── CommentApi.java        # 留言管理 API
├── impl/
│   ├── AbstractWechatApi.java     # API 抽象基类
│   ├── AuthApiImpl.java           # 认证 API 实现
│   ├── CallbackApiImpl.java       # 回调检测 API 实现
│   ├── material/
│   │   └── MaterialApiImpl.java   # 素材 API 实现
│   ├── draft/
│   │   └── DraftApiImpl.java      # 草稿 API 实现
│   ├── publish/
│   │   └── PublishApiImpl.java    # 发布 API 实现
│   ├── menu/
│   │   └── MenuApiImpl.java       # 菜单 API 实现
│   └── comment/
│       └── CommentApiImpl.java    # 留言 API 实现
├── model/
│   ├── request/                   # 请求模型
│   │   ├── DraftAddRequest.java
│   │   ├── DraftArticle.java
│   │   ├── DraftUpdateRequest.java
│   │   ├── PublishSubmitRequest.java
│   │   ├── MenuButton.java
│   │   ├── MenuCreateRequest.java
│   │   ├── CommentOpenRequest.java
│   │   ├── CommentCloseRequest.java
│   │   ├── CommentGetRequest.java
│   │   ├── CommentMarkRequest.java
│   │   ├── CommentUnmarkRequest.java
│   │   ├── CommentDeleteRequest.java
│   │   ├── CommentReplyRequest.java
│   │   ├── CommentDeleteReplyRequest.java
│   │   └── ...
│   └── response/                  # 响应模型
│       ├── DraftAddResponse.java
│       ├── DraftGetResponse.java
│       ├── PublishSubmitResponse.java
│       ├── MenuCreateResponse.java
│       ├── MenuGetResponse.java
│       ├── MenuDeleteResponse.java
│       ├── CommentOpenResponse.java
│       ├── CommentCloseResponse.java
│       ├── CommentGetResponse.java
│       ├── CommentInfo.java
│       ├── CommentMarkResponse.java
│       ├── CommentUnmarkResponse.java
│       ├── CommentDeleteResponse.java
│       ├── CommentReplyResponse.java
│       └── ...
├── http/
│   ├── HttpClient.java            # HTTP 客户端
│   └── JsonUtil.java              # JSON 工具
├── cache/
│   ├── TokenCache.java            # Token 缓存接口
│   └── DefaultTokenCache.java     # 默认缓存实现
├── constant/
│   ├── WechatApiEndpoint.java     # API 端点常量
│   └── WechatErrorCode.java       # 错误码常量
└── exception/
    ├── WechatException.java       # 基础异常
    └── WechatApiException.java    # API 异常
```

---

## 运行测试

```bash
# 运行单元测试
mvn test

# 运行特定测试类
mvn test -Dtest=WechatPublishTest
```

---

## 注意事项

1. **Access Token**: SDK 会自动管理 token 缓存（默认 2 小时），无需手动刷新
2. **图片上传**: 图文消息的图片必须通过微信接口上传，不能使用外部链接
3. **发布限制**: 公众号每天发布次数有限（通常 1-2 次），请合理使用
4. **IP 白名单**: 确保服务器 IP 已在微信后台配置白名单
5. **HTML 内容**: 文章内容需符合微信 HTML 规范，不支持某些标签

---

## 参考文档

- [微信开放平台 - 草稿箱管理](https://developers.weixin.qq.com/doc/subscription/api/draftbox/draftmanage/api_draft_add.html)
- [微信开放平台 - 发布能力](https://developers.weixin.qq.com/doc/offiaccount/Draft_Box/Add_Draft.html)
- [微信开放平台 - 接口说明](https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html)

---

## 许可证

MIT License

---

## 版本历史

### v1.2.0 (2026-04-12)
- ✅ 留言管理 API（打开/关闭评论、查看评论、管理评论）
- ✅ 支持评论标记精选、取消精选
- ✅ 支持评论回复、删除回复

### v1.1.0 (2026-04-12)
- ✅ 菜单管理 API（创建、查询、删除）
- ✅ 支持 11 种菜单按钮类型
- ✅ 支持二级菜单配置

### v1.0.0 (2026-04-12)
- ✅ 认证 API（access_token 获取）
- ✅ 素材管理 API（图片上传）
- ✅ 草稿管理 API（增删改查）
- ✅ 发布管理 API（提交发布、查询状态）
- ✅ 回调检测 API
