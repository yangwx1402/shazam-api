package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.model.request.*;
import com.shazam.wechat.sdk.model.response.*;

import java.util.List;

/**
 * 微信公众号留言管理功能使用示例
 *
 * 注意：此示例需要真实的 AppID 和 AppSecret 才能运行
 */
public class WechatCommentExample {

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

            // 3. 打开评论
            Long msgDataId = 1234567890L;  // 替换为实际的 msg_data_id

            CommentOpenRequest openRequest = new CommentOpenRequest.Builder()
                    .msgDataId(msgDataId)
                    .index(0)  // 第一篇图文
                    .build();

            CommentOpenResponse openResponse = client.comment().openComment(openRequest);
            if (openResponse.isSuccess()) {
                System.out.println("评论功能已打开");
            } else {
                System.out.println("打开评论失败：" + openResponse.getErrmsg());
            }

            // 4. 查看评论
            CommentGetRequest getRequest = new CommentGetRequest.Builder()
                    .msgDataId(msgDataId)
                    .index(0)
                    .begin(0)
                    .count(20)
                    .build();

            CommentGetResponse getResponse = client.comment().getComments(getRequest);
            if (getResponse.isSuccess()) {
                System.out.println("评论总数：" + getResponse.getTotal());

                for (CommentInfo comment : getResponse.getCommentList()) {
                    System.out.println("------------------------------");
                    System.out.println("评论 ID: " + comment.getCommentId());
                    System.out.println("用户：" + comment.getOpenid());
                    System.out.println("内容：" + comment.getContent());
                    System.out.println("时间：" + comment.getCreateTime());
                    System.out.println("是否精选：" + (comment.getIsTop() == 1 ? "是" : "否"));
                    System.out.println("是否删除：" + (comment.getIsDeleted() == 1 ? "是" : "否"));

                    if (comment.getReply() != null) {
                        System.out.println("回复：" + comment.getReply().getContent());
                    }
                }
            }

            // 5. 标记精选
            if (!getResponse.getCommentList().isEmpty()) {
                Long commentId = getResponse.getCommentList().get(0).getCommentId();

                CommentMarkRequest markRequest = new CommentMarkRequest.Builder()
                        .msgDataId(msgDataId)
                        .commentId(commentId)
                        .build();

                CommentMarkResponse markResponse = client.comment().markComment(markRequest);
                if (markResponse.isSuccess()) {
                    System.out.println("评论已标记为精选");
                }
            }

        } catch (Exception e) {
            System.err.println("操作失败：" + e.getMessage());
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 完整评论管理流程示例
     */
    public static void fullCommentManagement() {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            Long msgDataId = 1234567890L;

            // 1. 打开评论
            CommentOpenRequest openRequest = new CommentOpenRequest.Builder()
                    .msgDataId(msgDataId)
                    .index(0)
                    .build();
            client.comment().openComment(openRequest);
            System.out.println("评论功能已打开");

            // 2. 查看评论列表
            CommentGetRequest getRequest = new CommentGetRequest.Builder()
                    .msgDataId(msgDataId)
                    .begin(0)
                    .count(50)
                    .build();
            CommentGetResponse response = client.comment().getComments(getRequest);

            // 3. 管理评论
            for (CommentInfo comment : response.getCommentList()) {
                // 标记精选优质评论
                if (isGoodComment(comment)) {
                    CommentMarkRequest markRequest = new CommentMarkRequest.Builder()
                            .msgDataId(msgDataId)
                            .commentId(comment.getCommentId())
                            .build();
                    client.comment().markComment(markRequest);
                    System.out.println("评论 " + comment.getCommentId() + " 已标记为精选");
                }

                // 回复评论
                CommentReplyRequest replyRequest = new CommentReplyRequest.Builder()
                        .msgDataId(msgDataId)
                        .commentId(comment.getCommentId())
                        .content("感谢您的评论！")
                        .build();
                client.comment().replyComment(replyRequest);
                System.out.println("已回复评论 " + comment.getCommentId());

                // 删除不良评论
                if (isBadComment(comment)) {
                    CommentDeleteRequest deleteRequest = new CommentDeleteRequest.Builder()
                            .msgDataId(msgDataId)
                            .commentId(comment.getCommentId())
                            .build();
                    client.comment().deleteComment(deleteRequest);
                    System.out.println("已删除评论 " + comment.getCommentId());
                }
            }

            // 4. 关闭评论
            CommentCloseRequest closeRequest = new CommentCloseRequest.Builder()
                    .msgDataId(msgDataId)
                    .build();
            client.comment().closeComment(closeRequest);
            System.out.println("评论功能已关闭");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 回复评论示例
     */
    public static void replyToComment(Long msgDataId, Long commentId) {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            CommentReplyRequest request = new CommentReplyRequest.Builder()
                    .msgDataId(msgDataId)
                    .commentId(commentId)
                    .content("感谢您的支持！")
                    .build();

            CommentReplyResponse response = client.comment().replyComment(request);
            if (response.isSuccess()) {
                System.out.println("回复成功");
            } else {
                System.out.println("回复失败：" + response.getErrmsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 删除回复示例
     */
    public static void deleteReply(Long msgDataId, Long commentId) {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            CommentDeleteReplyRequest request = new CommentDeleteReplyRequest.Builder()
                    .msgDataId(msgDataId)
                    .commentId(commentId)
                    .build();

            CommentDeleteResponse response = client.comment().deleteReply(request);
            if (response.isSuccess()) {
                System.out.println("回复已删除");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 批量标记精选评论
     */
    public static void markTopComments(Long msgDataId, List<Long> commentIds) {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            for (Long commentId : commentIds) {
                CommentMarkRequest request = new CommentMarkRequest.Builder()
                        .msgDataId(msgDataId)
                        .commentId(commentId)
                        .build();

                CommentMarkResponse response = client.comment().markComment(request);
                if (response.isSuccess()) {
                    System.out.println("评论 " + commentId + " 已标记为精选");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 取消评论精选
     */
    public static void unmarkComment(Long msgDataId, Long commentId) {
        WechatConfig config = new WechatConfig.Builder()
                .appId("your_app_id")
                .appSecret("your_app_secret")
                .build();

        WechatClient client = new WechatClient(config);

        try {
            CommentUnmarkRequest request = new CommentUnmarkRequest.Builder()
                    .msgDataId(msgDataId)
                    .commentId(commentId)
                    .build();

            CommentUnmarkResponse response = client.comment().unmarkComment(request);
            if (response.isSuccess()) {
                System.out.println("评论已取消精选");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
    }

    /**
     * 判断是否为优质评论（示例方法）
     */
    private static boolean isGoodComment(CommentInfo comment) {
        // 这里可以根据评论内容、长度等判断是否为优质评论
        return comment.getContent() != null && comment.getContent().length() > 10;
    }

    /**
     * 判断是否为不良评论（示例方法）
     */
    private static boolean isBadComment(CommentInfo comment) {
        // 这里可以根据敏感词等判断是否为不良评论
        return false;
    }
}
