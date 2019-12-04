package com.pf.play.rule;

import com.alibaba.fastjson.JSON;
import com.pf.play.common.utils.BeanUtils;
import com.pf.play.common.utils.DateUtil;
import com.pf.play.common.utils.StringUtil;
import com.pf.play.model.protocol.request.consumer.RequestConsumer;
import com.pf.play.model.protocol.request.order.RequestOrder;
import com.pf.play.model.protocol.request.trade.RequestTrade;
import com.pf.play.model.protocol.response.consumer.ResponseConsumer;
import com.pf.play.model.protocol.response.order.ConsumerOrder;
import com.pf.play.model.protocol.response.order.Order;
import com.pf.play.model.protocol.response.order.ResponseOrder;
import com.pf.play.model.protocol.response.price.ResponseDayPrice;
import com.pf.play.model.protocol.response.trade.ResponseTrade;
import com.pf.play.model.protocol.response.trade.ResponseTradeTime;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.model.UserInfoModel;
import com.pf.play.rule.core.model.consumer.ConsumerFixedModel;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.price.VirtualCoinPriceDto;
import com.pf.play.rule.core.model.price.VirtualCoinPriceModel;
import com.pf.play.rule.core.model.strategy.StrategyModel;
import com.pf.play.rule.core.model.trade.TradeModel;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 公共方法类
 * @Author yoko
 * @Date 2019/11/21 16:17
 * @Version 1.0
 */
public class PublicMethod {

    /**
     * @Description: check用户更新设置支付密码的客户端上传的数据
     * @param requestConsumer - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 17:57
    */
    public static long checkUpPayCodeData(RequestConsumer requestConsumer)throws Exception{
        long memberId;
        // 1.校验所有数据
        if (requestConsumer == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00001.geteCode(), PfErrorCode.ENUM_ERROR.C00001.geteDesc());
        }

        // 2.校验token值
        if (StringUtils.isBlank(requestConsumer.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 3.校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestConsumer.getToken());

        // 4.校验支付密码
        if (StringUtils.isBlank(requestConsumer.getPayPw())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00003.geteCode(), PfErrorCode.ENUM_ERROR.C00003.geteDesc());
        }

        // 5.校验验证码
        if (requestConsumer.getUpPayCode() == null || requestConsumer.getUpPayCode() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00004.geteCode(), PfErrorCode.ENUM_ERROR.C00004.geteDesc());
        }
        return memberId;
    }

    /**
     * @Description: 校验用户是否处于登录状态
     * @param token - 登录token
     * @return Long
     * @author yoko
     * @date 2019/11/21 18:01
    */
    public static long checkIsLogin(String token) throws Exception{
        Long memberId;
        String strCache = (String) ComponentUtil.redisService.get(token);
        if (!StringUtils.isBlank(strCache)) {
            // 登录存储在缓存中的用户id
            memberId = Long.parseLong(strCache);
        }else {
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00000.geteCode(), PfErrorCode.ENUM_ERROR.C00000.geteDesc());
        }
        return memberId;
    }

    public static void checkVerifCode(String redisKey, int verifCode) throws Exception{
        String strKeyCache = redisKey;
        String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
        strCache = "123456";
        if (!StringUtils.isBlank(strCache)){
            int upPayCode = Integer.parseInt(strCache);
            if (upPayCode != verifCode){
                throw new ServiceException(PfErrorCode.ENUM_ERROR.C00005.geteCode(), PfErrorCode.ENUM_ERROR.C00005.geteDesc());
            }
        }else {
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00006.geteCode(), PfErrorCode.ENUM_ERROR.C00006.geteDesc());
        }
    }

    /**
     * @Description: 组装修改用户支付密码的数据
     * @param memberId - 用户ID
     * @param payPassword - 支付密码
     * @return
     * @author yoko
     * @date 2019/11/21 16:56
    */
    public static UserInfoModel assembleUserInfo(long memberId, String payPassword){
        UserInfoModel resBean = new UserInfoModel();
        resBean.setMemberId((int) memberId);
        resBean.setPayPassword(payPassword);
        return resBean;
    }

    /**
     * @Description: 公共的返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/13 21:45
     */
    public static String assembleResult(long stime, String token, String sign){
        ResponseConsumer dataModel = new ResponseConsumer();
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 公共的返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param empiricalLevel - 用户经验等级
     * @param ratio - 比例值
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/13 21:45
     */
    public static String assembleRatioResult(long stime, String token, String sign, int empiricalLevel, String ratio){
        ResponseConsumer dataModel = new ResponseConsumer();
        dataModel.empiricalLevel = empiricalLevel;
        dataModel.ratio = ratio;
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }



    /**
     * @Description: 查询固定信息时，校验基本数据是否非法
     * @param requestConsumer - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkGetFixedData(RequestConsumer requestConsumer) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestConsumer == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00011.geteCode(), PfErrorCode.ENUM_ERROR.C00011.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestConsumer.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestConsumer.getToken());
        return memberId;
    }


    /**
     * @Description: 查询取用户手续费百分比的信息，校验基本数据是否非法
     * @param requestConsumer - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkRatioData(RequestConsumer requestConsumer) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestConsumer == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00015.geteCode(), PfErrorCode.ENUM_ERROR.C00015.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestConsumer.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestConsumer.getToken());
        return memberId;
    }

    /**
     * @Description: 组装查询用户手续费的查询条件
     * @param memberId
     * @return
     * @author yoko
     * @date 2019/11/29 11:57
    */
    public static ConsumerModel assembleServiceChargeQuery(long memberId){
        ConsumerModel resBean = new ConsumerModel();
        resBean.setMemberId(memberId);
        return resBean;
    }

    /**
     * @Description: TODO
     * @param model
     * @return ConsumerModel
     * @author yoko
     * @date 2019/11/29 13:47
    */
    public static void checkConsumerServiceChargeData(ConsumerModel model) throws Exception{
        if (model == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00016.geteCode(), PfErrorCode.ENUM_ERROR.C00016.geteDesc());
        }
        if (StringUtils.isBlank(model.getRatio())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00017.geteCode(), PfErrorCode.ENUM_ERROR.C00017.geteDesc());
        }
    }


    /**
     * @Description: 组装用户固定账号的查询数据
     * @param memberId - 用户ID
     * @return ConsumerFixedModel
     * @author yoko
     * @date 2019/11/21 19:24
    */
    public static ConsumerFixedModel assembleConsumerFixedQuery(long memberId){
        ConsumerFixedModel resBean = new ConsumerFixedModel();
        resBean.setMemberId(memberId);
        return resBean;
    }


    /**
     * @Description: 添加固定信息时，校验基本数据是否非法
     * @param requestConsumer - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
    */
    public static long checkAddFixedData(RequestConsumer requestConsumer) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestConsumer == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00007.geteCode(), PfErrorCode.ENUM_ERROR.C00007.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestConsumer.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestConsumer.getToken());

        // 校验姓名
        if (StringUtils.isBlank(requestConsumer.getFullName())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00008.geteCode(), PfErrorCode.ENUM_ERROR.C00008.geteDesc());
        }

        // 校验身份证
        if (StringUtils.isBlank(requestConsumer.getIdCard())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00009.geteCode(), PfErrorCode.ENUM_ERROR.C00009.geteDesc());
        }

        // 校验验支付宝账号
        if (StringUtils.isBlank(requestConsumer.getFixedNum())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00010.geteCode(), PfErrorCode.ENUM_ERROR.C00010.geteDesc());
        }
        return memberId;
    }


    /**
     * @Description: 公共的返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param consumerFixedModel - 用户固定账号信息
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/13 21:45
     */
    public static String assembleGetFixedResult(long stime, String token, String sign, ConsumerFixedModel consumerFixedModel){
        ResponseConsumer dataModel = null;
        if (consumerFixedModel != null){
            dataModel = BeanUtils.copy(consumerFixedModel, ResponseConsumer.class);
        }else{
            dataModel = new ResponseConsumer();
        }
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: check用户更新固定账号的信息
     * @param requestConsumer
     * @return Long
     * @author yoko
     * @date 2019/11/21 20:41
    */
    public static long checkUpFixedData(RequestConsumer requestConsumer) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestConsumer == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00013.geteCode(), PfErrorCode.ENUM_ERROR.C00013.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestConsumer.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestConsumer.getToken());

        // 校验验支付宝账号
        if (StringUtils.isBlank(requestConsumer.getFixedNum())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00014.geteCode(), PfErrorCode.ENUM_ERROR.C00014.geteDesc());
        }
        return memberId;
    }

    /**
     * @Description: 组装更新用户支付宝账号数据的方法
     * @param memberId - 用户ID
     * @param fixedNum - 用户支付宝账号
     * @return
     * @author yoko
     * @date 2019/11/21 20:46
    */
    public static ConsumerFixedModel assembleUpConsumerFixed(long memberId, String fixedNum){
        ConsumerFixedModel resBean = new ConsumerFixedModel();
        resBean.setMemberId(memberId);
        resBean.setFixedNum(fixedNum);
        return resBean;
    }


    /**
     * @Description: 查询虚拟币每天兑换的价格时，校验基本数据是否非法
     * @param requestConsumer - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkGetVirtualCoinPriceData(RequestConsumer requestConsumer) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestConsumer == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.P00002.geteCode(), PfErrorCode.ENUM_ERROR.P00002.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestConsumer.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestConsumer.getToken());
        return memberId;
    }

    /**
     * @Description: 组装查询虚拟币每天兑换的价格的查询条件
     * @return VirtualCoinPriceModel
     * @author yoko
     * @date 2019/11/22 18:01
    */
    public static VirtualCoinPriceModel assembleVirtualCoinPriceQuery(){
        // 获取昨天的时间
        VirtualCoinPriceModel resBen = new VirtualCoinPriceModel();
        resBen.setCurdayStart(DateUtil.getIntYesterday());
        resBen.setCurdayEnd(DateUtil.getDayNumber(new Date()));
        return resBen;
    }


    /**
     * @Description: 组装虚拟币的价格数据
     * @param list - 昨日、今日虚拟币的价格
     * @return VirtualCoinPriceDto
     * @author yoko
     * @date 2019/11/22 21:37
    */
    public static VirtualCoinPriceDto assembleVirtualCoinPriceDto(List<VirtualCoinPriceModel> list) throws Exception{
        VirtualCoinPriceDto dto = new VirtualCoinPriceDto();
        int today = DateUtil.getDayNumber(new Date());
        int yesterday = DateUtil.getIntYesterday();
        for (VirtualCoinPriceModel dataModel : list){
            if (dataModel.getCurday() == today){
                // 今天
                dto.setT_exchangePrice(dataModel.getExchangePrice());
                dto.setT_tallestPrice(dataModel.getTallestPrice());
                dto.setMaxPrice(dataModel.getMaxPrice());
                dto.setMinPrice(dataModel.getMinPrice());
            }else if (dataModel.getCurday() == yesterday){
                // 昨天
                dto.setY_exchangePrice(dataModel.getExchangePrice());
                dto.setY_tallestPrice(dataModel.getTallestPrice());
            }
        }
        String growthRate = StringUtil.getGrowthRate(dto.getT_exchangePrice(), dto.getY_exchangePrice());
        dto.setGrowthRate(growthRate);
        return dto;
    }


    /**
     * @Description: 虚拟币价格数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param virtualCoinPriceDto - 虚拟币价格数据
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/13 21:45
     */
    public static String assembleDayPriceResult(long stime, String token, String sign, VirtualCoinPriceDto virtualCoinPriceDto){
        ResponseDayPrice dataModel = null;
        if (virtualCoinPriceDto != null){
            dataModel = BeanUtils.copy(virtualCoinPriceDto, ResponseDayPrice.class);
        }else{
            dataModel = new ResponseDayPrice();
        }
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }



    /**
     * @Description: 查询订单时，校验基本数据是否非法
     * @param requestOrder - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkOrderData(RequestOrder requestOrder) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestOrder == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00001.geteCode(), PfErrorCode.ENUM_ERROR.D00001.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestOrder.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestOrder.getToken());
        return memberId;
    }

    /**
     * @Description: 组装查询订单列表的查询条件
     * @param requestOrder - 查询的基本信息
     * @param memberId - 用户ID
     * //@param sortType - 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * @param ownType - 自己订单号是否需要包含在内：不为空则自己的订单不做显示
     * @return OrderModel
     * @author yoko
     * @date 2019/11/22 18:01
     */
    public static OrderModel assembleOrderQuery(RequestOrder requestOrder, long memberId, int ownType){
        OrderModel resBen = BeanUtils.copy(requestOrder, OrderModel.class);
        resBen.setMemberId(memberId);
        if (!StringUtils.isBlank(requestOrder.getPhoneNum())){
            resBen.setInviteCode(requestOrder.getPhoneNum());
        }
        resBen.setOrderTradeStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
        resBen.setOrderStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        resBen.setOwnType(ownType);
        return resBen;
    }


    /**
     * @Description: 订单信息的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param orderList - 订单信息
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleOrderResult(long stime, String token, String sign, List <OrderModel> orderList){
        ResponseOrder dataModel = new ResponseOrder();
        if (orderList != null && orderList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<Order> dataList = BeanUtils.copyList(orderList, Order.class);
            dataModel.oList = dataList;
        }
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }

    /**
     * @Description: 组装查询今天的虚拟币的价格信息的查询条件
     * @return VirtualCoinPriceModel
     * @author yoko
     * @date 2019/11/25 22:53
    */
    public static VirtualCoinPriceModel assembleVirtualCoinPriceQueryToday(){
        VirtualCoinPriceModel resBean = new VirtualCoinPriceModel();
        resBean.setCurday(DateUtil.getDayNumber(new Date()));
        return resBean;
    }


    /**
     * @Description: 发布订单时，校验基本数据是否非法
     * @param requestOrder - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkAddOrderData(RequestOrder requestOrder, VirtualCoinPriceModel virtualCoinPriceModel) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestOrder == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00002.geteCode(), PfErrorCode.ENUM_ERROR.D00002.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestOrder.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestOrder.getToken());

        // 购买的数量
        if (StringUtils.isBlank(requestOrder.getTradeNum())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00003.geteCode(), PfErrorCode.ENUM_ERROR.D00003.geteDesc());
        }

        // 单价
        if (StringUtils.isBlank(requestOrder.getTradePrice())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00004.geteCode(), PfErrorCode.ENUM_ERROR.D00004.geteDesc());
        }
        // 判断虚拟币今日价格是否有数据
        if(virtualCoinPriceModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.P00004.geteCode(), PfErrorCode.ENUM_ERROR.P00004.geteDesc());
        }
        // 判断单价是否属于设定范围内：推荐的最高单价-推荐的最低单价
        double minPrice = Double.parseDouble(virtualCoinPriceModel.getMinPrice());
        double maxPrice = Double.parseDouble(virtualCoinPriceModel.getMaxPrice());
        double clientPrice = Double.parseDouble(requestOrder.getTradePrice()); // 客户端用户填写的价格
        if (clientPrice < minPrice || clientPrice > maxPrice){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00005.geteCode(), PfErrorCode.ENUM_ERROR.D00005.geteDesc());
        }
        return memberId;
    }


    /**
     * @Description: 组装新增订单的数据
     * @param requestOrder - 订单的基本信息
     * @param memberId - 用户ID
     * @param orderNo - 订单号
     * @return OrderModel
     * @author yoko
     * @date 2019/11/26 11:19
     */
    public static OrderModel assembleAddOrderData(RequestOrder requestOrder, long memberId, String orderNo){
        OrderModel resBen = BeanUtils.copy(requestOrder, OrderModel.class);
        resBen.setMemberId(memberId);
        resBen.setOrderNo(orderNo);
        resBen.setCurday(DateUtil.getDayNumber(new Date()));
        resBen.setCurhour(DateUtil.getHour(new Date()));
        resBen.setCurminute(DateUtil.getCurminute(new Date()));
        return resBen;
    }


    /**
     * @Description: 新增、取消订单信息的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleOrderResult(long stime, String token, String sign){
        ResponseOrder dataModel = new ResponseOrder();
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }



    /**
     * @Description: 组装查询用户购买的订单（买入订单）列表的查询条件
     * @param requestOrder - 查询的基本信息
     * @param memberId - 用户ID
     * @param orderTradeStatus - 订单交易状态：0初始化(我的购入订单)，1锁定(我的代付款订单)，3确认付款，3完成
     * @param orderStatus - 订单状态：1正常，2取消，3完成交易；因为这里的订单交易状态是买入的订单状态（初始化状态），只会查询订单正常状态
     * @param sortType - 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * @return OrderModel
     * @author yoko
     * @date 2019/11/22 18:01
     */
    public static OrderModel assembleBuySellOrderQuery(RequestOrder requestOrder, long memberId, int orderTradeStatus, int orderStatus, int sortType){
        OrderModel resBen = new OrderModel();
        resBen.setMemberId(memberId);
        resBen.setOrderTradeStatus(orderTradeStatus);
        resBen.setOrderStatus(orderStatus);
        resBen.setSortType(sortType);
        return resBen;
    }

    /**
     * @Description: 买入、取消的订单的订单信息的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param orderList - 订单信息
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleBuyOrderResult(long stime, String token, String sign, List <OrderModel> orderList){
        ResponseOrder dataModel = new ResponseOrder();
        if (orderList != null && orderList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<ConsumerOrder> dataList = BeanUtils.copyList(orderList, ConsumerOrder.class);
            dataModel.coList = dataList;
        }
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 查询取消时，校验基本数据是否非法
     * @param requestOrder - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkCancelOrderData(RequestOrder requestOrder) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestOrder == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00001.geteCode(), PfErrorCode.ENUM_ERROR.D00001.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestOrder.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestOrder.getToken());

        // 校验订单值
        if (StringUtils.isBlank(requestOrder.getOrderNo())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00006.geteCode(), PfErrorCode.ENUM_ERROR.D00006.geteDesc());
        }
        return memberId;
    }

    /**
     * @Description: 组装查询要被取消的订单号的查询条件
     * @param requestOrder - 订单信息
     * @param memberId - 用户ID
     * @return
     * @author yoko
     * @date 2019/11/26 21:31
    */
    public static OrderModel assembleCancelOrderQuery(RequestOrder requestOrder, long memberId){
        OrderModel resBean = new OrderModel();
        resBean.setOrderNo(requestOrder.getOrderNo());
        resBean.setMemberId(memberId);
        return resBean;
    }

    /**
     * @Description: 校验要被取消的订单信息
     * @param orderModel - 订单信息
     * @return OrderModel
     * @author yoko
     * @date 2019/11/26 21:34
    */
    public static void checkCancelOrder(OrderModel orderModel) throws Exception{
        if (orderModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00007.geteCode(), PfErrorCode.ENUM_ERROR.D00007.geteDesc());
        }
        if (orderModel.getOrderStatus() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE){
            // 订单状态：1正常，2取消，3完成交易；
            // 订单状态不等于1的订单无法取消
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00008.geteCode(), PfErrorCode.ENUM_ERROR.D00008.geteDesc());
        }
        if (orderModel.getOrderStatus() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            // 订单交易状态：0初始化，1锁定，2确认付款，3完成；
            // 订单交易状态不等于0的订单无法取消
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00009.geteCode(), PfErrorCode.ENUM_ERROR.D00009.geteDesc());
        }
    }


    /**
     * @Description: 组装查询用户已取消的订单（被取消掉的订单）列表的查询条件
     * @param memberId - 用户ID
     * @param orderStatus - 订单状态：1正常，2取消，3完成交易
     * @param sortType - 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * @return OrderModel
     * @author yoko
     * @date 2019/11/22 18:01
     */
    public static OrderModel assembleCancelOrderQuery(long memberId, int orderStatus, int sortType){
        OrderModel resBen = new OrderModel();
        resBen.setMemberId(memberId);
        resBen.setOrderStatus(orderStatus);
        resBen.setSortType(sortType);
        return resBen;
    }



    /**
     * @Description: 查询交易开市时间时，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkTradeTimeData(RequestTrade requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00001.geteCode(), PfErrorCode.ENUM_ERROR.T00001.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());
        return memberId;
    }

    /**
     * @Description: 组装查询策略数据条件的方法
     * @param stgType - 策略类型：1表示成交量虚假数据开关，2表示交易所时间控制
     * @return com.pf.play.rule.core.model.strategy.StrategyModel
     * @author yoko
     * @date 2019/11/27 17:12
     */
    public static StrategyModel assembleStrategyQuery(int stgType){
        StrategyModel resBean = new StrategyModel();
        resBean.setStgType(stgType);
        return resBean;
    }

    /**
     * @Description: 校验判断是否现在属于开市时间
     * @param strategyModel - 开市的交易时间
     * @return int - 返回1表示属于开市时间，2表示目前不是开市时间
     * @author yoko
     * @date 2019/11/27 17:16
     */
    public static int checkTradeTime(StrategyModel strategyModel) throws Exception{
        int num;
        if (strategyModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00002.geteCode(), PfErrorCode.ENUM_ERROR.T00002.geteDesc());
        }
        if (StringUtils.isBlank(strategyModel.getStgValue())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00003.geteCode(), PfErrorCode.ENUM_ERROR.T00003.geteDesc());
        }
        String[] tradeTime = strategyModel.getStgValue().split("-");
        boolean flag = DateUtil.isBeLongSfm(tradeTime[0], tradeTime[1]);
        if (flag){
            num = ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE;
        }else{
            num = ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO;
        }
        return num;
    }


    /**
     * @Description: 开市时间的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param isTrade - 是否是开市时间范围内：1表示属于开市时间，2表示目前不是开市时间
     * @param tradeTime - 开市交易时间
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleTradeTimeResult(long stime, String token, String sign, int isTrade, String tradeTime){
        ResponseTradeTime dataModel = new ResponseTradeTime();
        dataModel.setIsTrade(isTrade);
        dataModel.setTradeTime(tradeTime);
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 查询用户待付款订单时，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkUnpaidData(RequestOrder requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00011.geteCode(), PfErrorCode.ENUM_ERROR.D00011.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());
        return memberId;
    }


    /**
     * @Description: 组装查询待付款的订单的查询条件
     * @param requestOrder - 订单信息
     * @param memberId - 用户ID
//     * @param orderTradeStatus - 订单交易状态：0初始化，1锁定，2确认付款，3完成 --这里查询状态为1的
     * @param sortType - 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * @return
     * @author yoko
     * @date 2019/11/26 21:31
     */
    public static OrderModel assembleUnpaidOrderQuery(RequestOrder requestOrder, long memberId, int sortType){
        OrderModel resBean = BeanUtils.copy(requestOrder, OrderModel.class);
        resBean.setMemberId(memberId);
        List<Integer> orderTradeStatusList = new ArrayList<>();
        orderTradeStatusList.add(1); // 订单流水状态1：锁定
        orderTradeStatusList.add(2);// 订单流水状态2：确认付款（等待中：等待卖家确认收款）
        resBean.setOrderTradeStatusList(orderTradeStatusList);
        List<Integer> tradeStatusList = new ArrayList<>();
        tradeStatusList.add(2);
        tradeStatusList.add(4);
        resBean.setTradeStatusList(tradeStatusList);
        resBean.setBuyMemberId(memberId);
        resBean.setSortType(sortType);
        return resBean;
    }


    /**
     * @Description: 买入、取消的订单的订单信息的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param orderList - 订单信息
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleUnpaidOrderResult(long stime, String token, String sign, List <OrderModel> orderList, int overtime, Integer rowCount){
        ResponseOrder dataModel = new ResponseOrder();
        if (orderList != null && orderList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<ConsumerOrder> dataList = BeanUtils.copyList(orderList, ConsumerOrder.class);
            dataModel.coList = dataList;
        }
        dataModel.overtime = overtime;
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 添加订单流水数据是，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkTradeAddData(RequestTrade requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00004.geteCode(), PfErrorCode.ENUM_ERROR.T00004.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());

        // 校验订单号值
        if (StringUtils.isBlank(requestTrade.getOrderNo())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00005.geteCode(), PfErrorCode.ENUM_ERROR.T00005.geteDesc());
        }
        return memberId;
    }



    /**
     * @Description: 组装查询订单的查询条件
     * @param requestTrade - 查询的基本信息
     * @param memberId - 用户ID
     * @param ownType - 自己订单号是否需要包含在内：不为空则自己的订单不做显示
     * @return OrderModel
     * @author yoko
     * @date 2019/11/22 18:01
     */
    public static OrderModel assembleOrderQueryByTrade(RequestTrade requestTrade, long memberId, int ownType){
        OrderModel resBen = new OrderModel();
        resBen.setMemberId(memberId);
        resBen.setOrderNo(requestTrade.getOrderNo());
        resBen.setOrderTradeStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO);
        resBen.setOrderStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        resBen.setOwnType(ownType);
        return resBen;
    }

    /**
     * @Description: 卖给TA时，校验订单信息
     * @param orderModel - 订单信息
     * @return void
     * @author yoko
     * @date 2019/11/28 16:55
    */
    public static void checkOrderBySell(OrderModel orderModel) throws Exception{
        if (orderModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00006.geteCode(), PfErrorCode.ENUM_ERROR.T00006.geteDesc());
        }
    }

    /**
     * @Description: 组装查询用户基本信息的查询条件
     * @param memberId
     * @return ConsumerModel
     * @author yoko
     * @date 2019/11/28 21:18
    */
    public static ConsumerModel assembleConsumerQuery(long memberId){
        ConsumerModel resBean = new ConsumerModel();
        resBean.setMemberId(memberId);
        return resBean;
    }

    /**
     * @Description: 校验卖家的基本信息
     * @param consumerModel - 用户基本信息
     * @param payPassword - 用户支付密码
     * @author yoko
     * @date 2019/11/28 21:34
    */
    public static void checkSellConsumer(ConsumerModel consumerModel, String payPassword) throws Exception{
        if (consumerModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00007.geteCode(), PfErrorCode.ENUM_ERROR.T00007.geteDesc());
        }
        // 卖家是否实已名认证
        if (consumerModel.getIsCertification() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO){

            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00008.geteCode(), PfErrorCode.ENUM_ERROR.T00008.geteDesc());
        }
        // 卖家是否设置了支付密码
        if (StringUtils.isBlank(consumerModel.getPayPassword())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00009.geteCode(), PfErrorCode.ENUM_ERROR.T00009.geteDesc());
        }

        // 卖家是否输入了支付密码
        if (StringUtils.isBlank(payPassword)){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00013.geteCode(), PfErrorCode.ENUM_ERROR.T00013.geteDesc());
        }

        // 支付密码是否正确
        if (!consumerModel.getPayPassword().equals(payPassword)){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00014.geteCode(), PfErrorCode.ENUM_ERROR.T00014.geteDesc());
        }

        // 卖家是否是黑名单用户--用户当前状态：1、正常用户 2、黑名单
        if (consumerModel.getIsActive() != ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00010.geteCode(), PfErrorCode.ENUM_ERROR.T00010.geteDesc());
        }

        // 卖家是否设置了收款账号：支付宝账号
        if (StringUtils.isBlank(consumerModel.getFixedNum())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00011.geteCode(), PfErrorCode.ENUM_ERROR.T00011.geteDesc());
        }

    }


    /**
     * @Description: 校验买家的基本信息
     * @param consumerModel
     * @author yoko
     * @date 2019/11/28 21:34
     */
    public static void checkBuyConsumer(ConsumerModel consumerModel) throws Exception{
        if (consumerModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00012.geteCode(), PfErrorCode.ENUM_ERROR.T00012.geteDesc());
        }
    }


    /**
     * @Description: 组装订单交易流水数据
     * @param orderModel - 订单信息
    * @param sellConsumer - 卖家信息
    * @param buyConsumer - 买家信息
     * @return com.pf.play.rule.core.model.trade.TradeModel
     * @author yoko
     * @date 2019/11/29 14:14
     */
    public static TradeModel assembleTradeData(OrderModel orderModel, ConsumerModel sellConsumer, ConsumerModel buyConsumer){
        TradeModel resBean = new TradeModel();
        resBean.setOrderId(orderModel.getId());
        resBean.setOrderNo(orderModel.getOrderNo());
        resBean.setSellMemberId(sellConsumer.getMemberId());
        resBean.setBuyMemberId(buyConsumer.getMemberId());
        resBean.setTradeNum(orderModel.getTradeNum());
        resBean.setTradePrice(orderModel.getTradePrice());
        resBean.setTotalPrice(orderModel.getTotalPrice());
        // 计算手续费：手续费=订单交易的数量*具体手续费
        String serviceCharge = StringUtil.getMultiply(orderModel.getTradeNum(), StringUtil.getBigDecimalDivide(sellConsumer.getRatio(), String.valueOf(100)));
        resBean.setServiceCharge(serviceCharge);
        resBean.setSellNickname(sellConsumer.getNickname());
        resBean.setBuyNickname(buyConsumer.getNickname());
        resBean.setSellPhone(sellConsumer.getPhoneNum());
        resBean.setBuyPhone(buyConsumer.getPhoneNum());
        resBean.setSellFixedNum(sellConsumer.getFixedNum());
        resBean.setBuyFixedNum(buyConsumer.getFixedNum());
        resBean.setRatio(sellConsumer.getRatio());
        return resBean;
    }


    /**
     * @Description: T组装要冻结用户钻石的数据
     * @param memberId - 用户ID
     * @param tradeNum - 交易的钻石数量
     * @param serviceCharge - 手续费
     * @return com.pf.play.rule.core.model.consumer.ConsumerModel
     * @author yoko
     * @date 2019/11/29 17:56
     */
    public static ConsumerModel assembleConsumerAddReduceData(long memberId, String tradeNum, String serviceCharge){
        ConsumerModel resBean = new ConsumerModel();
//        serviceCharge = StringUtil.getBigDecimalDivide(serviceCharge, String.valueOf(100));
//        serviceCharge =  StringUtil.getMultiply(tradeNum, serviceCharge);
        String addReduceNum = StringUtil.getBigDecimalAdd(tradeNum, serviceCharge); // 订单要交易的数量 + 手续费 = 用户要冻结的钻石数量
        resBean.setMemberId(memberId);
        resBean.setAddReduceNum(addReduceNum);
        return resBean;
    }


    /**
     * @Description: 校验卖家是否有足够的资源进行支付订单
     * @param ownNum - 卖家拥有的资源数量
     * @param deductNum - 要扣减的资源数量
     * @return void
     * @author yoko
     * @date 2019/11/29 18:10
     */
    public static void checkEnoughResources(String ownNum, String deductNum) throws Exception{
        boolean flag = StringUtil.getBigDecimalSubtract(ownNum, deductNum);
        if (!flag){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00017.geteCode(), PfErrorCode.ENUM_ERROR.T00017.geteDesc());
        }
    }

    /**
     * @Description: 订单交易流水时返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleTradeResult(long stime, String token, String sign){
        ResponseTrade dataModel = new ResponseTrade();
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 校验策略类型1是否有数据部署
     * @param strategyModel - 策略类型：1表示成交量虚假数据开关
     * @return void
     * @author yoko
     * @date 2019/12/2 14:35
    */
    public static void checkStrategyShamData(StrategyModel strategyModel) throws Exception{
        if (strategyModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00018.geteCode(), PfErrorCode.ENUM_ERROR.T00018.geteDesc());
        }
    }

    /**
     * @Description: 组装查询交易所当前买量、今日成交量的查询条件
     * @param type - 查询类型：0表示查询当前买量，1表示查询今日成交量
     * @return com.pf.play.rule.core.model.trade.TradeModel
     * @author yoko
     * @date 2019/12/2 17:53
     */
    public static TradeModel assembleOrderTradeNum(int type){
        TradeModel resBean = new TradeModel();
        if (type == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            resBean.setOrderStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        }else if (type == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE){
            resBean.setOrderStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_THREE);
            resBean.setCurday(DateUtil.getDayNumber(new Date()));
        }

        return resBean;
    }

    /**
     * @Description: 交易所的当前交易量、今日成交量的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param buyTradeNum - 前买量
     * @param sucTradeNum - 今日成交量
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleTradeDataResult(long stime, String token, String sign, String buyTradeNum, String sucTradeNum){
        ResponseTrade dataModel = new ResponseTrade();
        dataModel.setBuyTradeNum(buyTradeNum);
        dataModel.setSucTradeNum(sucTradeNum);
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }


    /**
     * @Description: 组装修改订单号的状态的方法
     * @param orderNo - 订单号
     * @param orderTradeStatus - 订单交易状态：0初始化，1锁定，2确认付款，3完成
     * @param orderStatus - 订单状态：1正常，2取消，3完成交易
     * @return com.pf.play.rule.core.model.order.OrderModel
     * @author yoko
     * @date 2019/12/3 11:42
     */
    public static OrderModel assembleUpdataOrderStatusData(String orderNo, Integer orderTradeStatus, Integer orderStatus){
        OrderModel resBean = new OrderModel();
        resBean.setOrderNo(orderNo);
        if (orderTradeStatus != null){
            resBean.setOrderTradeStatus(orderTradeStatus);
        }
        if (orderStatus != null){
            resBean.setOrderStatus(orderStatus);
        }
        return resBean;

    }


    /**
     * @Description: 买家确认支付，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkTradeConfirmPayData(RequestTrade requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00019.geteCode(), PfErrorCode.ENUM_ERROR.T00019.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());

        // 校验订单号值
        if (StringUtils.isBlank(requestTrade.getOrderNo())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00025.geteCode(), PfErrorCode.ENUM_ERROR.T00025.geteDesc());
        }

        // 校验第三方支付凭证（截图图片地址）
        if (StringUtils.isBlank(requestTrade.getPictureAds())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00020.geteCode(), PfErrorCode.ENUM_ERROR.T00020.geteDesc());
        }
        return memberId;
    }


    /**
     * @Description: 组装查询订单的查询条件
     * @param requestTrade - 查询的基本信息
     * @param memberId - 用户ID
     * @return OrderModel
     * @author yoko
     * @date 2019/11/22 18:01
     */
    public static OrderModel assembleOrderQueryByConfirmPay(RequestTrade requestTrade, long memberId, int orderTradeStatus){
        OrderModel resBen = new OrderModel();
        resBen.setMemberId(memberId);
        resBen.setOrderNo(requestTrade.getOrderNo());
        resBen.setOrderTradeStatus(orderTradeStatus);
        resBen.setOrderStatus(ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE);
        return resBen;
    }


    /**
     * @Description: 买家确认支付时，校验订单信息
     * @param orderModel - 订单信息
     * @return void
     * @author yoko
     * @date 2019/11/28 16:55
     */
    public static void checkOrderByConfirmPay(OrderModel orderModel) throws Exception{
        if (orderModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00021.geteCode(), PfErrorCode.ENUM_ERROR.T00021.geteDesc());
        }
    }

    /**
     * @Description: 组装要更新的订单交易流水的交易状态的数据
     * @param orderId - 订单号的主键ID
     * @param pictureAds - 图片地址
     * @param tradeStatus - 交易状态（更改后）
     * @param oldStatus - 交易状态（更改前）
     * @return com.pf.play.rule.core.model.trade.TradeModel
     * @author yoko
     * @date 2019/12/3 16:03
     */
    public static TradeModel assembleUpTradeStatusByConfirmPay(long orderId, String pictureAds, int tradeStatus, int oldStatus){
        TradeModel resBean = new TradeModel();
        resBean.setOrderId(orderId);
        if (!StringUtils.isBlank(pictureAds)){
            resBean.setPictureAds(pictureAds);
        }
        resBean.setTradeStatus(tradeStatus);
        resBean.setOldStatus(oldStatus);
        return resBean;
    }

    public static OrderModel assembleUpOrderStatusByConfirmPay(String orderNo, int orderTradeStatus, int oldStatus){
        OrderModel resBean = new OrderModel();
        resBean.setOrderNo(orderNo);
        resBean.setOrderTradeStatus(orderTradeStatus);
        resBean.setOldStatus(oldStatus);
        return resBean;
    }


    /**
     * @Description: 卖家确认已收款，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkTradeConfirmReceiptData(RequestTrade requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00023.geteCode(), PfErrorCode.ENUM_ERROR.T00023.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());

        // 校验订单号值
        if (StringUtils.isBlank(requestTrade.getOrderNo())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00024.geteCode(), PfErrorCode.ENUM_ERROR.T00024.geteDesc());
        }
        return memberId;
    }


    /**
     * @Description: 组装查询要更新的订单状态
     * @param requestTrade - 查询的基本信息
     * @param orderTradeStatus - 订单交易状态：0初始化，1锁定，2确认付款，3完成
     * @param orderStatus - 订单状态：1正常，2取消，3完成交易
     * @return OrderModel
     * @author yoko
     * @date 2019/11/22 18:01
     */
    public static OrderModel assembleOrderUpByConfirmReceipt(RequestTrade requestTrade, int orderTradeStatus, int orderStatus){
        OrderModel resBen = new OrderModel();
        resBen.setOrderNo(requestTrade.getOrderNo());
        resBen.setOrderTradeStatus(orderTradeStatus);
        resBen.setOrderStatus(orderStatus);
        return resBen;
    }

    /**
     * @Description: 组装查询订单信息的查询条件
     * @param requestTrade - 订单基本信息
     * @param orderTradeStatus - 订单交易状态：0初始化，1锁定，2确认付款，3完成
     * @return OrderModel
     * @author yoko
     * @date 2019/12/3 21:03
    */
    public static OrderModel assembleOrderQueryByConfirmRpt(RequestTrade requestTrade, int orderTradeStatus){
        OrderModel resBean = new OrderModel();
        resBean.setOrderNo(requestTrade.getOrderNo());
        resBean.setOrderTradeStatus(orderTradeStatus);
        return resBean;
    }


    /**
     * @Description: 卖家确认已收款时，校验订单信息
     * @param orderModel - 订单信息
     * @return void
     * @author yoko
     * @date 2019/11/28 16:55
     */
    public static void checkOrderByConfirmReceipt(OrderModel orderModel) throws Exception{
        if (orderModel == null){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00027.geteCode(), PfErrorCode.ENUM_ERROR.T00027.geteDesc());
        }
    }


    /**
     * @Description: 组装要更新的订单交易流水的交易状态：更新成完成交易（确认已收款（卖家确认））
     * @param orderId - 订单号的主键ID
     * @param tradeStatus - 交易状态（更改后）
     * @param oldStatus - 交易状态（更改前）
     * @return com.pf.play.rule.core.model.trade.TradeModel
     * @author yoko
     * @date 2019/12/3 16:03
     */
    public static TradeModel assembleUpTradeStatusByConfirmReceipt(long orderId, long memberId, int tradeStatus, int oldStatus){
        TradeModel resBean = new TradeModel();
        resBean.setOrderId(orderId);
        resBean.setSellMemberId(memberId);
        resBean.setTradeStatus(tradeStatus);
        resBean.setOldStatus(oldStatus);
        return resBean;
    }


    /**
     * @Description: 组装查询订单交易流水的查询条件
     * @param orderId - 订单号的主键ID
     * @param sellMemberId - 卖家的用户ID
     * @param tradeStatus - 交易状态
     * @return OrderModel
     * @author yoko
     * @date 2019/12/3 21:03
     */
    public static TradeModel assembleTradeQueryByConfirmReceipt(long orderId, long sellMemberId, int tradeStatus){
        TradeModel resBean = new TradeModel();
        resBean.setOrderId(orderId);
        resBean.setSellMemberId(sellMemberId);
        resBean.setTradeStatus(tradeStatus);
        return resBean;
    }

    /**
     * @Description: 组装卖家要扣除的冻结的钻石数
     * @param tradeModel - 订单交易流水的信息
     * @return
     * @author yoko
     * @date 2019/12/3 22:17
    */
    public static ConsumerModel assembleSellConsumerSubtractMasonry(TradeModel tradeModel){
        ConsumerModel resBean = new ConsumerModel();
        resBean.setMemberId(tradeModel.getSellMemberId());
        String addReduceNum = StringUtil.getBigDecimalAdd(tradeModel.getTradeNum(), tradeModel.getServiceCharge());
        resBean.setAddReduceNum(addReduceNum);
        return resBean;
    }

    /**
     * @Description: 组装买家要加的钻石数
     * @param tradeModel - 订单交易流水的信息
     * @return
     * @author yoko
     * @date 2019/12/3 22:17
     */
    public static ConsumerModel assembleBuyConsumerAddMasonry(TradeModel tradeModel){
        ConsumerModel resBean = new ConsumerModel();
        resBean.setMemberId(tradeModel.getBuyMemberId());
        resBean.setAddReduceNum(tradeModel.getTradeNum());
        return resBean;
    }


    /**
     * @Description: 查询用户待收款订单时，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkReceivableData(RequestOrder requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00013.geteCode(), PfErrorCode.ENUM_ERROR.D00013.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());
        return memberId;
    }


    /**
     * @Description: 组装查询待收款的订单的查询条件
     * @param requestOrder - 订单信息
     * @param memberId - 用户ID
     * @param sortType - 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * @return
     * @author yoko
     * @date 2019/11/26 21:31
     */
    public static OrderModel assembleReceivableQuery(RequestOrder requestOrder, long memberId, int sortType){
        OrderModel resBean = BeanUtils.copy(requestOrder, OrderModel.class);
        List<Integer> orderTradeStatusList = new ArrayList<>();
        orderTradeStatusList.add(1); // 订单流水状态1：锁定
        orderTradeStatusList.add(2);// 订单流水状态2：确认付款（等待中：等待卖家确认收款）
        resBean.setOrderTradeStatusList(orderTradeStatusList);
        List<Integer> tradeStatusList = new ArrayList<>();
        tradeStatusList.add(2);
        tradeStatusList.add(4);
        resBean.setTradeStatusList(tradeStatusList);
        resBean.setSellMemberId(memberId);
        resBean.setSortType(sortType);
        return resBean;
    }


    /**
     * @Description: 查询用户已完成订单时，校验基本数据是否非法
     * @param requestTrade - 基础数据
     * @return void
     * @author yoko
     * @date 2019/11/21 18:59
     */
    public static long checkFinishData(RequestOrder requestTrade) throws Exception{
        long memberId;
        // 校验所有数据
        if (requestTrade == null ){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00014.geteCode(), PfErrorCode.ENUM_ERROR.D00014.geteDesc());
        }

        // 校验token值
        if (StringUtils.isBlank(requestTrade.getToken())){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.C00002.geteCode(), PfErrorCode.ENUM_ERROR.C00002.geteDesc());
        }

        // 校验用户是否登录
        memberId = PublicMethod.checkIsLogin(requestTrade.getToken());
        return memberId;
    }


    /**
     * @Description: 组装查询已完成的订单的查询条件
     * @param requestOrder - 基本信息
     * @param memberId - 用户ID
     * @return
     * @author yoko
     * @date 2019/11/26 21:31
     */
    public static OrderModel assembleFinishQuery(RequestOrder requestOrder, long memberId){
        OrderModel resBean = BeanUtils.copy(requestOrder, OrderModel.class);
        resBean.setSellMemberId(memberId);
        resBean.setBuyMemberId(memberId);
        return resBean;
    }


    /**
     * @Description: 买入、取消的订单的订单信息的数据组装返回客户端的方法
     * @param stime - 服务器的时间
     * @param token - 登录token
     * @param sign - 签名
     * @param orderList - 订单信息
     * @param rowCount - 总行数
     * @return java.lang.String
     * @author yoko
     * @date 2019/11/25 22:45
     */
    public static String assembleFinishOrderResult(long stime, String token, String sign, List <OrderModel> orderList, Integer rowCount){
        ResponseOrder dataModel = new ResponseOrder();
        if (orderList != null && orderList.size() > ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            List<ConsumerOrder> dataList = BeanUtils.copyList(orderList, ConsumerOrder.class);
            dataModel.coList = dataList;
        }
        if (rowCount != null){
            dataModel.rowCount = rowCount;
        }
        dataModel.setStime(stime);
        dataModel.setToken(token);
        dataModel.setSign(sign);
        return JSON.toJSONString(dataModel);
    }



    public static void main(String [] args){

        String t = "1.835";
        String y = "1.921";
        String z = "100";
//        String res = (t-y)/y*100;
        BigDecimal resDoble;
        BigDecimal tt = new BigDecimal(t);
        BigDecimal yy = new BigDecimal(y);
        BigDecimal zz = new BigDecimal(z);
        BigDecimal jf_res = tt.subtract(yy);
        resDoble = jf_res.divide(yy, 4, BigDecimal.ROUND_HALF_UP).multiply(zz);
        DecimalFormat sb = new DecimalFormat("###.##");
        String data = sb.format(resDoble);

        System.out.println("data:" + data);
    }

}
