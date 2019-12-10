package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.UNumberTypeLog;
import com.pf.play.rule.core.model.VcMember;
import com.pf.play.rule.core.model.VcMemberResource;

/**
 * @Description TODO
 * @Author long
 * @Date 2019/12/9 16:28
 * @Version 1.0
 */
public interface SynchroService<T> extends BaseService<T> {
    void  addGive(Integer memberId);
    void  addGoods(Integer memberId);
    VcMemberResource queryVcMemberResource(VcMemberResource vcMemberResource);
}
