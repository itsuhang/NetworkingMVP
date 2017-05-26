package com.suhang.networkmvp.function.upload


import com.suhang.networkmvp.constants.DEFAULT_TAG
import com.suhang.networkmvp.function.rx.RxBusSingle
import com.suhang.networkmvp.mvp.result.ProgressResult

import java.io.File
import java.io.IOException

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.Okio
import okio.Sink

class UploadFileRequestBody : RequestBody {

    private var mRequestBody: RequestBody? = null

    private var bufferedSink: BufferedSink? = null


    constructor(file: File, mimeType: MediaType?) {
        var mediaType: MediaType? = MediaType.parse("multipart/form-data")
        if (mimeType != null) {
            mediaType = mimeType
        }
        this.mRequestBody = RequestBody.create(mediaType, file)
    }

    constructor(requestBody: RequestBody) {
        this.mRequestBody = requestBody
    }

    //返回了requestBody的类型，想什么form-data/MP3/MP4/png等等等格式
    override fun contentType(): MediaType? {
        return mRequestBody!!.contentType()
    }

    //返回了本RequestBody的长度，也就是上传的totalLength
    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mRequestBody!!.contentLength()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink))
        }
        //写入
        mRequestBody!!.writeTo(bufferedSink!!)
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink!!.flush()
    }

    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            //当前写入字节数
            internal var bytesWritten = 0L
            //总字节长度，避免多次调用contentLength()方法
            internal var contentLength = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                if (contentLength.toInt() == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength()
                }
                //增加当前写入的字节数
                bytesWritten += byteCount
                //回调上传接口
                RxBusSingle.instance().post(ProgressResult((bytesWritten * 100 / contentLength).toInt(), bytesWritten == contentLength, DEFAULT_TAG))
            }
        }
    }
}