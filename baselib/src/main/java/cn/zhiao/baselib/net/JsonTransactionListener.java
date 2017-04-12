package cn.zhiao.baselib.net;

import cn.zhiao.baselib.base.BaseResponse;
import cn.zhiao.baselib.base.JsonResponse;

/**
 * 事务处理监听
 */
public abstract class JsonTransactionListener extends StringTransactionListener {
    /**
     * 带Json数据的成功回调
     *
     * @param response
     */
    public abstract void onSuccess(BaseResponse response);

    @Override
    public void onSuccess(String response) {
        JsonResponse jsonResponse = JsonResponse.getResponse(response);
        onSuccess(jsonResponse);
    }

}
