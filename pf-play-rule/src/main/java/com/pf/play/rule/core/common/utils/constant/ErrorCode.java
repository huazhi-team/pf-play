package com.pf.play.rule.core.common.utils.constant;

/**
 * @author df
 * @Description:异常状态码
 * @create 2018-07-27 11:13
 **/
public class ErrorCode {

    /**
     * 常量异常
     */
    public final class ERROR_CONSTANT {
        /**
         * 没有被捕捉到的异常
         * 默认系统异常状态码=255
         */
        public static final String DEFAULT_EXCEPTION_ERROR_CODE = "255";

        /**
         * 没有被捕捉到的异常
         * 默认系统异常错误信息=SYS_ERROR
         */
        public static final String DEFAULT_EXCEPTION_ERROR_MESSAGE = "ERROR";

        /**
         * 被捕捉到的异常，并且捕捉的异常错误码为空，则默认异常状态码
         * 默认捕捉的异常状态码=256
         */
        public static final String DEFAULT_SERVICE_EXCEPTION_ERROR_CODE = "256";

        /**
         * 被捕捉到的异常，但是错误信息为空，则默认异常信息提醒
         * 默认捕捉的异常信息提醒=错误
         */
        public static final String DEFAULT_SERVICE_EXCEPTION_ERROR_MESSAGE = "错误";

    }


    /**
     * 异常-枚举类
     */
    public enum ENUM_ERROR {
//        PROGRAM

        IS_USER_ERROR("-1","请先登录","信息验证不通过"),
        PARAMETER_ERROR("-255","信息验证不通过","信息验证不通过"),

        /********************注册信息*****************************/
        R000001("000001","用户已经注册！","用户已经注册！"),
        R000002("000002","无效的邀请码！","无效的邀请码！"),
        R000003("000003","网络异常，请重新进行添加！",""),
        R000004("000004","短信发送失败！",""),


        U000000("-1","用户信息不存在！","用户信息验证不通过！"),
        U000001("000001","错误,请重试!","用户信息验证不通过！"),
        U000002("000002","错误,请重试!","根据用户信息，查询不到用户信息！"),


        USERMASONRY_ERRPR0("-3000","用户信息验证不通过！",""),


        REALNAME_ERRPR0("-4000","用户信息不存在！",""),
        REALNAME_ERRPR1("-4001","插入待处理表失败！",""),


        TASK_ERRPR0("-5000","该用户为实名制！","该用户为实名制!"),
        TASK_ERRPR1("-5001","服务异常，请重试！","task 任务id失败"),
        TASK_ERRPR2("-5002","服务异常，请重试！","没有该用户信息！"),
        TASK_ERRPR3("-5003","用户金额不足，不能进行领取","用户金额不足，不能进行领取"),
        TASK_ERRPR4("-5004","任务持有数不够","任务持有数不够"),
        TASK_ERRPR5("-5005","金额不够，无法进行领取","金额不够，无法进行领取"),
        TASK_ERRPR6("-5006","服务异常，请重试！","修改数据失败"),
        TASK_ERRPR7("-5007","服务异常，请重试！","程序内部异常，导致空指针！"),
        TASK_ERRPR8("-5008","该用户没满足条件，无法领取","该用户没满足条件，无法领取"),
        TASK_ERRPR9("-5009","服务异常，请重试！","找不到对应的taskId信息"),
        TASK_ERRPR10("-5010","服务异常，请重试！","该类用户可以加入黑名单！"),

        TASK_ERRPR11("-5011","用户处于结算中，请稍后再试！","该类用户可以加入黑名单！"),
        TASK_ERRPR12("-5012","用户的魅力值不够","用户的魅力值不够！"),






        T000001("000001","数据修改异常","查询到活跃值信息，未修改成功异常"),






        ;

        /**
         * 错误码
         */
        private String eCode;
        /**
         * 给客户端看的错误信息
         */
        private String eDesc;
        /**
         * 插入数据库的错误信息
         */
        private String dbDesc;




        private ENUM_ERROR(String eCode, String eDesc,String dbDesc) {
            this.eCode = eCode;
            this.eDesc = eDesc;
            this.dbDesc  = dbDesc;
        }

        public String geteCode() {
            return eCode;
        }

        public void seteCode(String eCode) {
            this.eCode = eCode;
        }

        public String geteDesc() {
            return eDesc;
        }

        public void seteDesc(String eDesc) {
            this.eDesc = eDesc;
        }

        public String getDbDesc() {
            return dbDesc;
        }

        public void setDbDesc(String dbDesc) {
            this.dbDesc = dbDesc;
        }
    }
}
