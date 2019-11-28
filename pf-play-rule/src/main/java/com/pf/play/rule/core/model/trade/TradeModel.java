package com.pf.play.rule.core.model.trade;

import com.pf.play.model.protocol.page.BasePage;

import java.io.Serializable;

/**
 * @Description 交易的实体属性Bean
 * @Author yoko
 * @Date 2019/11/26 22:39
 * @Version 1.0
 */
public class TradeModel extends BasePage implements Serializable {
    private static final long   serialVersionUID = 1233223301144L;

    /**
     * 自增主键ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 交易数量：买卖的数量
     */
    private String tradeNum;

    /**
     * 交易价格：求购价格、卖的价格
     */
    private String tradePrice;

    /**
     * 订单交易状态：0初始化，1锁定，2完成
     */
    private Integer orderTradeStatus;

    /**
     * 订单状态：1正常，2取消，3完成交易
     */
    private Integer orderStatus;

    /**
     * 订单申诉状态：1无申诉，2申诉，3已处理
     */
    private Integer appealStatus;

    /**
     * 创建日期：存的日期格式20160530
     */
    private Integer curday;

    /**
     * 创建所属小时：24小时制
     */
    private Integer curhour;

    /**
     * 创建所属分钟：60分钟制
     */
    private Integer curminute;

    /**
     *运行计算次数
     */
    private Integer runNum;

    /**
     * 运行计算状态：：0初始化，1锁定，2计算失败，3计算成功
     */
    private Integer runStatus;


    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否有效：0有效，1无效/删除
     */
    private Integer yn;

    /**
     * 排序类型：1按照时间降序排，2按照时间升序，3按照交易数量降序，4按照数量升序，5按照单价降序，6按照单价升序
     * 默认按照时间降序排列
     */
    private Integer sortType;

    /**
     * 自己订单号是否需要包含在内：不为空则自己的订单不做显示
     */
    private Integer ownType;

    private Integer curdayStart;

    private Integer curdayEnd;

    /**
     * 总价：单价X数量
     */
    private String totalPrice;

    /**
     * 推广码：也可以当做手机号
     */
    private String inviteCode;

    /**
     * 用户昵称
     */
    private String nickname;
}
