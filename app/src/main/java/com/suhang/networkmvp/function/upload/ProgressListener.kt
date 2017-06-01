package com.suhang.networkmvp.function.upload

/**
 * 进度回调接口，比如用于文件上传与下载
 */
interface ProgressListener {
    fun onProgress(currentBytes: Long, contentLength: Long, done: Boolean)
}