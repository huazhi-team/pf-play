package com.pf.play.rule.core.common.utils.constant;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/11/13 15:07
 * @Version 1.0
 */
public class Constant {
    public static  int   TOKEN = 1;
    public static  int   INVITE_CODE = 2;
    public static  int   TRADING_ADDRESS = 3;
    public static  int   MEMBERID = 4;

    /*****************任务类型********************/
    public static  int   TASK_TYPE1 = 1;//完成任务
    public static  int   TASK_TYPE2 = 2;//活力值奖励
    public static  int   TASK_TYPE3 = 3;//赠送给Ta
    public static  int   TASK_TYPE4 = 4;//Ta人赠送
    public static  int   TASK_TYPE5 = 5;//转出手续费
    public static  int   TASK_TYPE6 = 6;//转入手续费
    public static  int   TASK_TYPE7 = 7;//交易所转入
    public static  int   TASK_TYPE8 = 8;//转出砖石
    public static  int   TASK_TYPE9 = 9;//购买任务消耗
    public static  int   TASK_TYPE10 = 10;//赠送礼物手续费
    public static  int   TASK_TYPE11 = 11;//实名制获取奖励
    public static  int   TASK_TYPE12 = 12;//活力值增加
    public static  int   TASK_TYPE13= 13; //活力值到期
    public static  int   TASK_TYPE14= 14; //平台等级分红-1级
    public static  int   TASK_TYPE15= 15; //平台等级分红-2级
    public static  int   TASK_TYPE16= 16; //平台等级分红-3级
    public static  int   TASK_TYPE17= 17; //平台等级分红-4级
    public static  int   TASK_TYPE18= 18; //平台等级分红-5级

    /*****************符号类型********************/
    public static  int   TASK_SYMBOL_TYPE1 = 1;//加
    public static  int   TASK_SYMBOL_TYPE2 = 2;//减

    /*****************符号类型********************/
    public static  int   REWARD_TYPE1 = 1;//直推购买任务
    public static  int   REWARD_TYPE2 = 2;//自己任务活力值
    public static  int   REWARD_TYPE3 = 3;//团队活力值
    public static  int   REWARD_TYPE4 = 4;//联盟活力值



    /***********注册验证码有效时间(单位分钟)*********/
    public  static  int  EFFECTIVE_IDENT_CODE_TIME =88888;

    /***********token 过期时间(单位 天 )*********/
    public  static  int  TOKEN_EXPIRE    =  15 ;



    public  static  int  TASK_TYPE_HISTORY    =  1 ;
    public  static  int  TASK_TYPE_EXISTING   =  2 ;

    /***********同步用户详细信息**********/
    public  static  String    USER_SYNCHRONOUS_URL="http://114.55.67.167:9093/qhr/api/syncUserInfo";
    /***********同步上下级信息详细信息**********/
    public  static  String    PRARENT_SYNCHRONOUS_URL="http://114.55.67.167:9093/qhr/api/syncPrarentId";
    /*********** 比例信息 **********/
    public  static  Double    ACTIVE_VALUE_MASONRY =  0.4 ;


    /*********** 下级奖励表的类型信息 **********/
    public  static  int  REWARD_TASK1    =  1 ; //实名制
    public  static  int  REWARD_TASK2    =  2 ; //下级购买任务
    public  static  int  REWARD_TASK3    =  3 ; //任务到期

    /*********** code 错误码 **********/
    public  static  int  CODE_ERROR_TYPE1    =  1 ; //显示给前端
    public  static  int  CODE_ERROR_TYPE2    =  2 ; //不显示显示给前端

    public  static  long  TOKEN_EXPIRE_S    =604800L ;
    /*********** 经验值类型 **********/
    public  static  int  EMPIRIC_TYPE1    =  1 ; //实名制
    public  static  int  EMPIRIC_TYPE2    =  2 ; //初级任务
    public  static  int  EMPIRIC_TYPE3    =  3 ; //中级任务
    public  static  int  EMPIRIC_TYPE4    =  4 ; //高级任务
    public  static  int  EMPIRIC_TYPE5    =  5 ; //超级任务
    public  static  int  EMPIRIC_TYPE6    =  6 ; //王者任务
    public  static  int  EMPIRIC_TYPE7    =  7 ; //至尊任务

    /*********** 活力值处理类型 **********/
    public  static  int  ACTIVE_VALUE_TYPE1    =  1 ; //实名制
    public  static  int  ACTIVE_VALUE_TYPE2    =  2 ; //初级任务
    public  static  int  ACTIVE_VALUE_TYPE3    =  3 ; //中级任务
    public  static  int  ACTIVE_VALUE_TYPE4    =  4 ; //高级任务


    /*********** 更新task 定时器任务 **********/
    public  static  int  UPDATE_TASK_TIME    =  2160000 ; //更新task 定时器任务

    /*********** 用户任务拥有表task 状态 **********/
    public  static  int  CURRENT_STATE_TYPE1    =  1 ;
    public  static  int  CURRENT_STATE_TYPE2    =  2 ;
    public  static  int  CURRENT_STATE_TYPE3    =  3 ;

}
