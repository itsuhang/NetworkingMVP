package com.suhang.networkmvp.domain

/**
 * Created by 苏杭 on 2017/2/3 15:45.
 */

data class GithubBean(var isError: Boolean, var results: List<ResultsEntity>?) {


    data class ResultsEntity(var _id: String?, var content: String?, var created_at: String?, var publishedAt: String?, var rand_id: String?, var title: String?, var updated_at: String?)

}
