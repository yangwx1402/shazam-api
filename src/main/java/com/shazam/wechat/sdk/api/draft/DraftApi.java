package com.shazam.wechat.sdk.api.draft;

import com.shazam.wechat.sdk.api.WechatApi;
import com.shazam.wechat.sdk.model.request.DraftAddRequest;
import com.shazam.wechat.sdk.model.request.DraftUpdateRequest;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 草稿管理 API
 */
public interface DraftApi extends WechatApi {

    /**
     * 新增草稿
     *
     * @param request 草稿请求
     * @return 新增结果，包含 media_id
     */
    DraftAddResponse addDraft(DraftAddRequest request);

    /**
     * 获取草稿详情
     *
     * @param mediaId 草稿 media_id
     * @return 草稿详情
     */
    DraftGetResponse getDraft(String mediaId);

    /**
     * 删除草稿
     *
     * @param mediaId 草稿 media_id
     * @return 删除结果
     */
    DraftDeleteResponse deleteDraft(String mediaId);

    /**
     * 更新草稿
     *
     * @param request 更新请求
     * @return 更新结果
     */
    DraftUpdateResponse updateDraft(DraftUpdateRequest request);

    /**
     * 获取草稿列表
     *
     * @param offset 从全部素材的哪一项开始，默认为 0
     * @param count  素材数量，默认 20，最大 20
     * @return 草稿列表
     */
    DraftBatchGetResponse batchGetDrafts(int offset, int count);

    /**
     * 获取草稿列表（默认参数）
     *
     * @return 草稿列表（前 20 条）
     */
    default DraftBatchGetResponse batchGetDrafts() {
        return batchGetDrafts(0, 20);
    }

    /**
     * 获取草稿总数
     *
     * @return 草稿总数
     */
    DraftCountResponse countDrafts();
}
