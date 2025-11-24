package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import com.qy.util.PinyinUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 拼音名称
 *
 * @author legendjw
 */
@Getter
public class PinyinName implements ValueObject {
    private String name;
    private String namePinyin;

    public PinyinName(String name) {
        this.name = name;
        this.namePinyin = StringUtils.isNotBlank(name) ? PinyinUtils.getAlpha(name) : null;
    }

    public PinyinName(String name, String namePinyin) {
        this.name = name;
        this.namePinyin = namePinyin;
    }
}