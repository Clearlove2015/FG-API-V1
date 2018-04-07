package com.odbpo.fenggou.rxjavaretrofit.net;

/**
 * @author: zc
 * @Time: 2017/11/23 15:39
 * @Desc:
 */
public class Global {

    public final static String PHONE = "17351466656";//18625035123

    public final static String PASSWORD ="123456";//aa123123/aa123456

//    public static String API_PATH = "http://139.196.70.254/api/v2/";//android

    //机房测试服务器
    public final static String API_PATH = "http://10.33.180.12:8080/api/v1/";//android测试服务器

    //阿忠IP
//    public static String API_PATH = "http://10.33.133.42:8080/api/v1/";

    public final static String LOGIN = "customer/login";

    public static final String CUSTOMERS = "customers";

    public final static String MEMBER_LEVEL = "customers/point/level";

    public final static String UPDATE_NICKNAME = "customers/nickname";

    public final static String REAL_NAME = "customers/realName";

    public final static String UPDATE_SEX = "customers/gender";

    public final static String GOODS_FOLLOW = "customers/follows";

    public final static String ORDER_NUMBER = "orders/status/counts";

    public static final String ORDER_GOODSLIST = "orders/{status}";

    public static final String HISTORY_LIST = "customers/browserecord";

    public static final String CLEAR_HISTORY_LIST = "customers/browserecord/clear";

    public static final String POINT_LIST = "customers/point";

    public static final String PRODUCT_CATEGORY = "goods/mobcates";

    public static final String ORDER_DETAIL = "orders/{orderId}/order";

    public static final String RETURN_DETAIL = "order/{orderId}/back/{isBack}/detail";

    public static final String UPLOAD_IMG = "order/proof/uploads";

    public static final String UPLOAD_IMG_TEST = "/FileUpload/FileUploadServlet";

    public static final String UPLOAD_IMG_SINGLE = "order/proof/upload";

    public static final String GET_LOGISTICS_INFO = "order/{orderId}/express";

    public static final String GET_KUAIDI = "http://www.kuaidi100.com/query";

    public static final String GET_COUPON = "goods/{goodsInfoId}/coupons";

    public static final String GET_COUPONS = "store/coupons";

    public static final String GET_ACCOUNT = "trandeInfo/findAll/{status}";

    public static final String MSG_NUMBER = "customers/messages/unread/counts";

    public static final String DELETED_GOODS = "goods/deleteShoppingGoods";

    public static final String LIKE_LOGIN = "guess/like";

    public static final String WECHAT_LOGIN = "customer/weChat/Login";

    public static final String ALIPAY = "alipay/pay";

}
