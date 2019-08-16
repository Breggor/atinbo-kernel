//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.handler;

import com.atinbo.constants.UrlPatternType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 处理器
 *
 * @author breggor
 */
public abstract class Handler {

    protected Handler prev;
    protected Handler next;
    protected UrlPatternType patternType;
    protected List<String> patternConsts;

    public Handler() {
        this.patternType = UrlPatternType.CONTAIN;
        this.patternConsts = Collections.singletonList("/*");
    }


    public Handler(String... patternConsts) {
        this.patternType = UrlPatternType.CONTAIN;
        this.patternConsts = Collections.singletonList("/*");
        this.patternConsts = Arrays.asList(patternConsts);
    }

    public Handler(UrlPatternType patternType, String... patternConsts) {
        this.patternType = UrlPatternType.CONTAIN;
        this.patternConsts = Collections.singletonList("/*");
        this.patternType = patternType;
        this.patternConsts = Arrays.asList(patternConsts);
    }

    public abstract void handle(String url, HttpServletRequest req, HttpServletResponse res) throws Exception;

    public Handler except(String... patternType) {
        this.patternType = UrlPatternType.EXCEPT;
        this.patternConsts = Arrays.asList(patternType);
        return this;
    }

    public Handler contain(String... patternType) {
        this.patternType = UrlPatternType.CONTAIN;
        this.patternConsts = Arrays.asList(patternType);
        return this;
    }
}
