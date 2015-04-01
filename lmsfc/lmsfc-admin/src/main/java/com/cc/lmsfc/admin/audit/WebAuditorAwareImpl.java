package com.cc.lmsfc.admin.audit;

import com.cc.lmsfc.common.model.security.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

public class WebAuditorAwareImpl implements AuditorAware<String> {

	public String getCurrentAuditor() {
        String userName = ((User)SecurityUtils.getSubject().getSession().getAttribute("loginUser")).getUserName();
		return StringUtils.isEmpty(userName) ? "System" : userName;
	}

}
