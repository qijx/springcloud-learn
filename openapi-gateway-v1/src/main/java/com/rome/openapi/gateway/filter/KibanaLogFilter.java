package com.rome.openapi.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.rome.openapi.gateway.constant.OpenConatant;
import com.rome.openapi.gateway.domain.entity.AppEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

/**
 * @Class: KibanaLogFilter
 * @Auther: MarsKen
 * @Date: 2019/7/29 11:25
 * @Description:
 */
@Slf4j
public class KibanaLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("进入kibana日志打印拦截器...");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        AppEntry appEntry = (AppEntry) ctx.get("app");
//        System.err.println(JSON.toJSON(ctx));
        JSONObject kibanaLog = new JSONObject();
        kibanaLog.put("uri", request.getRequestURI());
        if (appEntry != null) {
            kibanaLog.put("appId", appEntry.getAppId());
            kibanaLog.put("appName", appEntry.getAppName());
            kibanaLog.put("appType", appEntry.getAppType());
        }
        kibanaLog.put("logType", "open-api-result");
        kibanaLog.put("reqTime", System.currentTimeMillis());
        kibanaLog.put("method", request.getMethod());
        kibanaLog.put("isInnerGateWay", ctx.get(OpenConatant.INNER_TO_KEY));
        kibanaLog.put("isWriteUrl", ctx.get(OpenConatant.WRITE_URLTO_KEY));
        setParameterMap(request, kibanaLog);
        setRequestBody(request, kibanaLog);
        setResponseBody(ctx, kibanaLog);
        log.warn(kibanaLog.toJSONString());
        return null;
    }

    private void setParameterMap(HttpServletRequest request, JSONObject kibanaLog) {
        Map<String, String[]> map = request.getParameterMap();
        JSONObject requestParam = new JSONObject();
        map.forEach((param, value) -> {
            requestParam.put(param, JSON.toJSON(value));
        });
        kibanaLog.put("requestParam", requestParam);
    }

    private void setRequestBody(HttpServletRequest request, JSONObject kibanaLog) {
        try {
            InputStream in = request.getInputStream();
            String reqBbody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            kibanaLog.put("requestBody", reqBbody);
        } catch (Exception e) {
            log.error("获取请求体异常", e);
        }
    }

    private void setResponseBody(RequestContext ctx, JSONObject kibanaLog) {
        try {
            if (StringUtils.isEmpty(ctx.getResponseBody())) {
                InputStream in = ctx.getResponseDataStream();
                String outBody = StringUtils.EMPTY;
                if (in != null && ctx.getResponseGZipped()) {
                    RecordingInputStream recordingInputStream = new RecordingInputStream(in);
                    InputStream gzipOut = new GZIPInputStream(recordingInputStream);
                    outBody = StreamUtils.copyToString(gzipOut, Charset.forName("UTF-8"));
                    ctx.getResponse().setHeader("Content-Encoding", "gzip");
                    recordingInputStream.reset();
                    ctx.setResponseDataStream(recordingInputStream.delegate);
                } else {
                    outBody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                    ctx.setResponseBody(outBody);//重要！
                }
                kibanaLog.put("responseBody", JSON.toJSON(outBody));
                kibanaLog.put("isSuccess", Boolean.TRUE);
            } else {
                kibanaLog.put("responseBody", ctx.getResponseBody());
                kibanaLog.put("isSuccess", Boolean.FALSE);
            }
        } catch (Exception e) {
            log.error("获取返回体异常", e);
        }
    }

    private static class RecordingInputStream extends InputStream {
        private InputStream delegate;
        private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        RecordingInputStream(InputStream delegate) {
            this.delegate = (InputStream) Objects.requireNonNull(delegate);
        }

        public int read() throws IOException {
            int read = this.delegate.read();
            if (this.buffer != null && read != -1) {
                this.buffer.write(read);
            }

            return read;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            int read = this.delegate.read(b, off, len);
            if (this.buffer != null && read != -1) {
                this.buffer.write(b, off, read);
            }

            return read;
        }

        public void reset() {
            if (this.buffer == null) {
                throw new IllegalStateException("Stream is not recording");
            } else {
                this.delegate = new SequenceInputStream(new ByteArrayInputStream(this.buffer.toByteArray()), this.delegate);
                this.buffer = new ByteArrayOutputStream();
            }
        }

        public int getBytesRead() {
            return this.buffer == null ? -1 : this.buffer.size();
        }

        public void stopRecording() {
            this.buffer = null;
        }

        public void close() throws IOException {
            this.delegate.close();
        }
    }

}
