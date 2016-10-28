package com.suhang.networkmvp.model;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.google.gson.Gson;
import com.suhang.networkmvp.bean.ErrorBean;
import com.suhang.networkmvp.constants.ErrorCode;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.presenter.INetworkPresenter;
import com.suhang.networkmvp.util.Md5Util;
import com.suhang.networkmvp.util.RetrofitHelper;
import com.suhang.networkmvp.util.RxUtil;
import com.suhang.networkmvp.util.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscription;

/**
 * Created by sh on 2016/10/25 16:45.
 */

public class NetworkModel implements INetworkModel {
	private RetrofitHelper mHelper;
	private Map<Integer, Subscription> mSubscriptionMap;
	private Map<Integer, Call> mCallMap;
	private static DiskLruCache sOpen;
	private Gson mGson;

	public NetworkModel() {
		mHelper = new RetrofitHelper();
		initDiskLruCache();
		mGson = new Gson();
		mCallMap = new HashMap<>();
	}

	/**
	 * 添加网络任务到队列,以便于取消任务
	 *
	 * @param subscription
	 * @param tag
	 */
	private void addSubscrebe(Subscription subscription, int tag) {
		if (mSubscriptionMap == null) {
			mSubscriptionMap = new HashMap<>();
		}
		mSubscriptionMap.put(tag, subscription);
	}

	/**
	 * 初始化硬盘缓存类
	 */
	private static void initDiskLruCache() {
		try {
			sOpen = DiskLruCache.open(new File(INetworkService.CACHE_PATH), SystemUtil.getAppVersion(), 1, 1024 * 1024 * 200);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> void loadData(Class<T> aClass, Map<String, String> params, boolean needCache, int tag, INetworkPresenter.OnDataLoadingListener<T> listener) {
		T t = dealCache(aClass, null, listener);
		if (t != null) {
			listener.onSuccess(t, false);
		}
		Observable<T> observable = fetch(aClass, params);
		if (observable != null) {
			Subscription subscribe = observable.compose(RxUtil.fixScheduler()).subscribe(t1 -> {
				listener.onSuccess(t1, true);
				dealCache(aClass, t1, listener);
			}, throwable -> {
				listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK + "\n" + throwable.getMessage()));
			});
			addSubscrebe(subscribe, tag);
		} else {
			listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH));
		}

	}

	@Override
	public <T> void upload(Class<T> aClass, Map<String, String> params, File file, int tag, INetworkPresenter.
			OnDataLoadingListener<T> listener) {
		Observable<T> observable = mHelper.uploadHead(aClass, file, params, (currentBytes, contentLength, done) -> listener.onProgress((int) (100f * currentBytes / contentLength), done));
		Subscription subscribe = observable.compose(RxUtil.fixScheduler()).subscribe(t -> listener.onSuccess(t, false)
				, throwable -> {
					listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK + "\n" + throwable.getMessage()));
				});
		addSubscrebe(subscribe, tag);
	}

	@Override
	public void download(String url, String name, String path, int tag, INetworkPresenter.
			OnDataLoadingListener listener) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Call call = client.newCall(request);
		mCallMap.put(tag, call);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_DOWNLOAD_IO, ErrorCode.ERROR_DESC_DOWNLOAD_IO + "\n" + e.getMessage()));
			}

			@Override
			public void onResponse(Call call, Response response) {
				try {
					saveFile(response, path, name, listener);
				} catch (IOException e) {
					listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_DOWNLOAD_IO, ErrorCode.ERROR_DESC_DOWNLOAD_IO + "\n" + e.getMessage()));
				}
			}
		});
	}


	/**
	 * 从本地获取缓存,有网则先从本地获取,再从网络获取,写入缓存
	 *
	 * @param aClass
	 * @param t
	 * @return
	 */

	private <T> T dealCache(Class<T> aClass, T t, INetworkPresenter.OnDataLoadingListener<T> listener) {
		T at = null;
		try {
			Field field = aClass.getField("URL");
			String url = (String) field.get(null);
			String key = Md5Util.getMD5(url);
			DiskLruCache.Value value = sOpen.get(key);
			if (t != null) {
				String s = mGson.toJson(t);
				if (value == null || !s.equals(value.getString(0))) {
					DiskLruCache.Editor edit = sOpen.edit(key);
					edit.set(0, s);
					edit.commit();
					at = t;
				}
			} else {
				if (value != null) {
					at = mGson.fromJson(value.getString(0), aClass);
				}
			}
		} catch (IOException e) {
			listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_CACHEWR, ErrorCode.ERROR_DESC_CACHEWR));
		} catch (NoSuchFieldException e) {
			listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL));
		} catch (NoSuchAlgorithmException e) {
			listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_ALGORITHM, ErrorCode.ERROR_DESC_ALGORITHM));
		} catch (IllegalAccessException e) {
			listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL));
		}
		return at;
	}

	/**
	 * 通过Class来判断是否有该Bean类对应的抓取数据的方法
	 *
	 * @param aClass
	 * @param params
	 * @return
	 */
	private <T> Observable<T> fetch(Class<T> aClass, Map<String, String> params) {
		Observable<T> observable = null;
//		if (GameInfoBean.class.equals(aClass)) {
//			observable = mHelper.getGameInfo(aClass, params);
//		} else if (HistoryBean.class.equals(aClass)) {
//			observable = mHelper.getHistoryInfo(aClass, params);
//		} else if (PersonalInfoBean.class.equals(aClass)) {
//			observable = mHelper.getPersonalInfo(aClass, params);
//		}
		return observable;
	}

	@Override
	public void cancelNormal(int tag) {
		if (mSubscriptionMap.get(tag) != null) {
			mSubscriptionMap.get(tag).unsubscribe();
		}
	}

	@Override
	public void cancelDownload(int tag) {
		if (mCallMap.get(tag) != null) {
			mCallMap.get(tag).cancel();
		}
	}

	@Override
	public void cancelAll() {
		for (Map.Entry<Integer, Subscription> entry : mSubscriptionMap.entrySet()) {
			entry.getValue().unsubscribe();
		}
		mSubscriptionMap.clear();
		for (Map.Entry<Integer, Call> entry : mCallMap.entrySet()) {
			entry.getValue().cancel();
		}
		mCallMap.clear();
	}


	/**
	 * 访问网络获取流,并将流写入到文件中
	 *
	 * @param response
	 * @param destFileDir
	 * @param name
	 * @param listener
	 * @return
	 * @throws IOException
	 */
	private File saveFile(Response response, String destFileDir, String name, INetworkPresenter.OnDataLoadingListener listener) throws IOException {
		InputStream is = null;
		byte[] buf = new byte[2048];
		boolean len = false;
		FileOutputStream fos = null;

		try {
			is = response.body().byteStream();
			final long total = response.body().contentLength();
			long sum = 0L;
			File dir = new File(destFileDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File(dir, name);
			fos = new FileOutputStream(file);

			int len1;
			while ((len1 = is.read(buf)) != -1) {
				sum += (long) len1;
				fos.write(buf, 0, len1);
				float percent = (float) sum * 100f / (float) total;
				listener.onProgress((int) percent, false);
			}
			listener.onProgress(100, true);
			listener.onSuccess(file.getAbsolutePath(), false);
			fos.flush();
			File finalSum = file;
			return finalSum;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException var22) {

			}

			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException var21) {

			}

		}
	}
}
