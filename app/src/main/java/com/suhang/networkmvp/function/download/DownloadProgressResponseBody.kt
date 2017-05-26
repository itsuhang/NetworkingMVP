package com.suhang.networkmvp.function.download

import com.suhang.networkmvp.constants.DEFAULT_TAG
import com.suhang.networkmvp.function.rx.RxBus
import com.suhang.networkmvp.mvp.result.ProgressResult

import java.io.IOException

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Okio
import okio.Source

class DownloadProgressResponseBody (private val responseBody: ResponseBody,var mRxBus: RxBus) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += if (bytesRead.toInt() != -1) bytesRead else 0
                mRxBus.post(ProgressResult((totalBytesRead * 100 / responseBody.contentLength()).toInt(), bytesRead.toInt() != -1,DEFAULT_TAG))
                return bytesRead
            }
        }

    }
}