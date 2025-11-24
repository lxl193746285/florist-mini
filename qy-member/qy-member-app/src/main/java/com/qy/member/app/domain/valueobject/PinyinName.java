package com.qy.member.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import com.qy.util.PinyinUtils;
import lombok.Getter;

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
        this.namePinyin = PinyinUtils.getAlpha(name);
    }

    public PinyinName(String name, String namePinyin) {
        this.name = name;
        this.namePinyin = namePinyin;
    }
}