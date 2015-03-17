package com.cc.lmsfc.task.mock;

import com.cc.lmsfc.common.model.task.ArticleTaskJob;

/**
 * Created by tomchen on 15-3-16.
 */
public class MockData {

    private static ArticleTaskJob atj;

    static{
        atj = new ArticleTaskJob();


    }

    public static ArticleTaskJob getAtj() {
        return atj;
    }

    public static void setAtj(ArticleTaskJob atj) {
        MockData.atj = atj;
    }
}
