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

    }


    /**
     * 异常-枚举类
     */
    public enum ENUM_ERROR {
//        PROGRAM
        PARAMETER_ERROR("-255","信息验证不通过",1,"信息验证不通过"),

        REGISTER_ERROR0("-1000","用户已经注册！",1,""),
        REGISTER_ERROR1("-1001","该用户已经注册！",1,""),
        REGISTER_ERROR2("-1002","无效的邀请码！",1,""),
        REGISTER_ERROR3("-1003","网络异常，请重新进行添加！",1,""),

        USERINFO_ERRPR0("-2000","用户信息验证不通过！",1,""),
        USERINFO_ERRPR1("-2001","根据用户信息，查询不到用户信息！",1,""),


        USERMASONRY_ERRPR0("-3000","用户信息验证不通过！",1,""),


        REALNAME_ERRPR0("-4000","用户信息不存在！",1,""),
        REALNAME_ERRPR1("-4001","插入待处理表失败！",1,""),




        TASK_ERRPR0("-5000","该用户为实名制！",1,"该用户为实名制!"),
        TASK_ERRPR1("-5001","服务异常，请重试！",1,"task 任务id失败"),
        TASK_ERRPR2("-5002","服务异常，请重试！",1,"没有该用户信息！"),
        TASK_ERRPR3("-5003","用户金额不足，不能进行领取",1,"用户金额不足，不能进行领取"),
        TASK_ERRPR4("-5004","任务持有数不够",1,"任务持有数不够"),
        TASK_ERRPR5("-5005","金额不够，无法进行领取",1,"金额不够，无法进行领取"),
        TASK_ERRPR6("-5006","服务异常，请重试！",1,"修改数据失败"),
        TASK_ERRPR7("-5007","服务异常，请重试！",1,"程序内部异常，导致空指针！"),
        TASK_ERRPR8("-5008","该用户没满足条件，无法领取",1,"该用户没满足条件，无法领取"),
        TASK_ERRPR9("-5009","服务异常，请重试！",1,"找不到对应的taskId信息"),
        TASK_ERRPR10("-5010","服务异常，请重试！",1,"该类用户可以加入黑名单！"),


        ;

        private String eCode;
        private String eDesc;
        private Integer isDisplay;
        private String  defaultValue;
        public Integer getIsDisplay() {
            return isDisplay;
        }
        public void setIsDisplay(Integer isDisplay) {
            this.isDisplay = isDisplay;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        private ENUM_ERROR(String eCode, String eDesc,Integer isDisplay,String defaultValue) {
            this.eCode = eCode;
            this.eDesc = eDesc;
            this.isDisplay = isDisplay;
            this.defaultValue  = defaultValue;
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
    }
}
