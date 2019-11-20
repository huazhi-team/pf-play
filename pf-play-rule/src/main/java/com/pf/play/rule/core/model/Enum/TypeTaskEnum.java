package com.pf.play.rule.core.model.Enum;

public enum TypeTaskEnum {
    taskType1("进阶任务",1),taskType2("初级任务",2),taskType3("启蒙任务",3),
    taskType4("中级任务",4),taskType5("大师任务",5),taskType6("高级任务",6),
    taskType7("史诗任务",7),taskType8("至尊任务",8);

    private String  name ;
    private Integer value;

    private TypeTaskEnum(String   name , Integer value){
        this.name =name ;
        this.value =value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
