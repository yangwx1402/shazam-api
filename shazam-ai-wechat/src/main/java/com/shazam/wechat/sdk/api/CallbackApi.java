package com.shazam.wechat.sdk.api;

import com.shazam.wechat.sdk.model.request.CallbackCheckAction;
import com.shazam.wechat.sdk.model.request.CheckOperator;
import com.shazam.wechat.sdk.model.response.CallbackCheckResponse;

/**
 * 回调检测相关 API
 */
public interface CallbackApi extends WechatApi {

    /**
     * 网络通信检测（默认全部检测，自动选择运营商）
     *
     * @return 检测结果响应
     */
    CallbackCheckResponse check();

    /**
     * 网络通信检测
     *
     * @param action   检测动作
     * @param operator 检测运营商
     * @return 检测结果响应
     */
    CallbackCheckResponse check(CallbackCheckAction action, CheckOperator operator);
}
