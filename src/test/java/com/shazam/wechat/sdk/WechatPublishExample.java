package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.model.request.DraftAddRequest;
import com.shazam.wechat.sdk.model.request.DraftArticle;
import com.shazam.wechat.sdk.model.request.PublishSubmitRequest;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 微信公众号自动发布功能使用示例
 *
 * 注意：此示例需要真实的 AppID 和 AppSecret 才能运行
 */
public class WechatPublishExample {

    public static void main(String[] args) {
        // 1. 创建配置
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")           // 替换为你的 AppID
                .appSecret("your_app_secret")   // 替换为你的 AppSecret
                .build();

        // 2. 创建客户端
        WechatClient client = new WechatClient(config);

        try {
            // 3. 获取 access_token（会自动缓存）
            AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
            System.out.println("Access Token: " + tokenResp.getAccess_token());

            // 4. 上传封面图片
            // 注意：需要替换为真实的图片路径
            // File coverImage = new File("/path/to/cover.jpg");
            // UploadImageResponse imgResp = client.material().uploadImage(coverImage);
            // String thumbMediaId = imgResp.getUrl();
            String thumbMediaId = "existing_media_id";  // 或使用已有的 media_id

            // 5. 创建草稿（单图文）
            DraftArticle article = new DraftArticle.Builder()
                    .title("我的文章标题")
                    .author("作者名")
                    .content("<h1>文章标题</h1><p>文章 HTML 内容...</p>")
                    .digest("文章摘要")
                    .thumbMediaId(thumbMediaId)
                    .needOpenComment(1)           // 打开评论
                    .onlyFansCanComment(0)        // 所有人可评论
                    .contentSourceUrl("https://example.com")
                    .build();

            DraftAddRequest draftRequest = new DraftAddRequest.Builder()
                    .addArticle(article)
                    .build();

            DraftAddResponse draftResp = client.draft().addDraft(draftRequest);
            String mediaId = draftResp.getMediaId();
            System.out.println("草稿创建成功，media_id: " + mediaId);

            // 6. 提交发布
            PublishSubmitRequest publishRequest = new PublishSubmitRequest.Builder()
                    .mediaId(mediaId)
                    .build();

            PublishSubmitResponse publishResp = client.publish().submitPublish(publishRequest);
            String publishId = publishResp.getPublishId();
            System.out.println("发布提交成功，publish_id: " + publishId);

            // 7. 等待发布完成
            Thread.sleep(3000);

            // 8. 查询发布状态
            PublishGetResponse statusResp = client.publish().getPublishStatus(publishId);

            if (statusResp.isPublished()) {
                System.out.println("发布成功！");
                System.out.println("文章链接：" + statusResp.getArticleUrl());
            } else if (statusResp.getPublishStatus() == 1) {
                System.out.println("发布中，请稍后查询...");
            } else {
                System.out.println("发布失败，错误码：" + statusResp.getErrcode());
                System.out.println("错误消息：" + statusResp.getErrmsg());
            }

        } catch (Exception e) {
            System.err.println("操作失败：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 9. 关闭客户端
            client.shutdown();
        }
    }

    /**
     * 多图文消息发布示例
     */
    public static void publishMultiArticle() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            // 创建多篇图文
            DraftArticle article1 = new DraftArticle.Builder()
                    .title("第一篇标题")
                    .content("<p>第一篇内容...</p>")
                    .thumbMediaId("media_id_1")
                    .build();

            DraftArticle article2 = new DraftArticle.Builder()
                    .title("第二篇标题")
                    .content("<p>第二篇内容...</p>")
                    .thumbMediaId("media_id_2")
                    .build();

            DraftArticle article3 = new DraftArticle.Builder()
                    .title("第三篇标题")
                    .content("<p>第三篇内容...</p>")
                    .thumbMediaId("media_id_3")
                    .build();

            // 添加到同一草稿
            DraftAddRequest request = new DraftAddRequest.Builder()
                    .addArticle(article1)
                    .addArticle(article2)
                    .addArticle(article3)
                    .build();

            DraftAddResponse response = client.draft().addDraft(request);
            System.out.println("多图文草稿创建成功，media_id: " + response.getMediaId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 查询草稿列表示例
     */
    public static void listDrafts() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            // 获取草稿总数
            DraftCountResponse countResp = client.draft().countDrafts();
            System.out.println("草稿总数：" + countResp.getTotalCount());

            // 获取前 20 篇草稿
            DraftBatchGetResponse listResp = client.draft().batchGetDrafts(0, 20);
            System.out.println("返回草稿数量：" + listResp.getItem().size());

            for (DraftBatchGetResponse.DraftItem item : listResp.getItem()) {
                System.out.println("Media ID: " + item.getMediaId());
                System.out.println("更新时间：" + item.getUpdateTime());
                System.out.println("更新账号：" + item.getUpdateAccount());
                System.out.println("---");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 删除草稿示例
     */
    public static void deleteDraft(String mediaId) {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            DraftDeleteResponse response = client.draft().deleteDraft(mediaId);
            if (response.isSuccess()) {
                System.out.println("草稿删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 使用已有草稿直接发布
     */
    public static void publishExistingDraft(String mediaId) {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            PublishSubmitRequest request = new PublishSubmitRequest.Builder()
                    .mediaId(mediaId)
                    .build();

            PublishSubmitResponse response = client.publish().submitPublish(request);
            System.out.println("发布提交成功，publish_id: " + response.getPublishId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }
}
