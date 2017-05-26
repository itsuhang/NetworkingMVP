package com.suhang.networkmvp.function.download

import com.suhang.networkmvp.constants.DEFAULT_TAG
import com.suhang.networkmvp.function.rx.RxBus
import com.suhang.networkmvp.function.rx.RxBusSingle
import com.suhang.networkmvp.mvp.result.ProgressResult
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.IOException

class DownloadProgressResponseBody (private val responseBody: ResponseBody) : ResponseBody(),AnkoLogger {
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
                RxBusSingle.instance().post(ProgressResult((totalBytesRead * 100 / responseBody.contentLength()).toInt(), bytesRead.toInt() != -1,DEFAULT_TAG))
                return bytesRead
            }
        }

    }
}