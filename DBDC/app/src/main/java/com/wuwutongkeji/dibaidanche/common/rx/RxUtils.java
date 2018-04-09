package com.wuwutongkeji.dibaidanche.common.rx;


import com.wuwutongkeji.dibaidanche.common.config.AppConfig;
import com.wuwutongkeji.dibaidanche.common.exception.DbdcExceptioin;
import com.wuwutongkeji.dibaidanche.common.net.impl.NetModel;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.Bai on 2017/9/11.
 */
public class RxUtils {

    private static Observable.Transformer ioToMainThreadSchedulerTransformer;

    static {
        ioToMainThreadSchedulerTransformer = createIOToMainThreadScheduler();
    }

    private static <T> Observable.Transformer<T,T> createIOToMainThreadScheduler(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T>Observable<T> converterMode(final Observable<NetModel<T>> observable){
        return observable.flatMap(new Func1<NetModel<T>, Observable<T>>() {
            @Override
            public Observable<T> call(final NetModel<T> tNetModel) {
                return Observable.create(new Observable.OnSubscribe<T>(){

                    @Override
                    public void call(Subscriber<? super T> subscriber) {

                        if(null == tNetModel){
                            subscriber.onError(new DbdcExceptioin(tNetModel.getDesc()));
                        }else {
                            if(!tNetModel.isSuccess()){
                                if(AppConfig.NO_LOGIN.equals(tNetModel.getReturnCode())){
                                    EventBus.getDefault().post(AppConfig.NO_LOGIN);
                                }else {
                                    subscriber.onError(new DbdcExceptioin(tNetModel.getDesc()));
                                }
                            }else{
                                subscriber.onNext(tNetModel.getData());
                                subscriber.onCompleted();
                            }
                        }
                    }
                });
            }
        }).compose(ioToMainThreadSchedulerTransformer);
    }

    public static <T>Observable<NetModel<T>> converterNillMode(final Observable<NetModel<T>> observable){
        return observable.flatMap(new Func1<NetModel<T>, Observable<NetModel<T>>>() {
            @Override
            public Observable<NetModel<T>> call(final NetModel<T> tNetModel) {
                return Observable.create(new Observable.OnSubscribe<NetModel<T>>(){

                    @Override
                    public void call(Subscriber<? super NetModel<T>> subscriber) {
                        if (null == tNetModel) {
                            subscriber.onError(new DbdcExceptioin(AppConfig.NET_ERROR));
                        } else {
                            if(AppConfig.NO_LOGIN.equals(tNetModel.getReturnCode())){
                                EventBus.getDefault().post(AppConfig.NO_LOGIN);
                            }else{
                                subscriber.onNext(tNetModel);
                                subscriber.onCompleted();
                            }
                        }
                    }
                });
            }
        }).compose(ioToMainThreadSchedulerTransformer);
    }

    public static <T>Observable<T> converterAllMode(final Observable<T> observable){
        return observable.compose(ioToMainThreadSchedulerTransformer);
    }

    public static <T> Observable.Transformer<T, T> applyIOToMainThreadSchedulers() {
        return ioToMainThreadSchedulerTransformer;
    }

}
