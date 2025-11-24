package com.qy.message.app.application.service;

import com.qy.message.app.application.dto.Regulation;

import java.util.List;

public interface RuleParseService {
	 List<Regulation> ruleParse(Integer templateId);
}
