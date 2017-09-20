package com.feasttime.rxbus;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by chen on 2017/8/29.
 */

public class RxBus {
    private static RxBus instance;
    //存放订阅者信息
    private Map<Object, CompositeDisposable> subscriptionsMap;

    private Subject<Object> subjectBus;

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    RxBus tempInstance = new RxBus();
                    tempInstance.subjectBus = PublishSubject.create().toSerialized();
                    tempInstance.subscriptionsMap = new HashMap<>();
                    instance = tempInstance;
                }
            }
        }
        return instance;
    }

    private CompositeDisposable initRxBus(Object object) {
        CompositeDisposable compositeDisposable = subscriptionsMap.get(object);
        if (compositeDisposable != null) {
            return compositeDisposable;
        }

        compositeDisposable = new CompositeDisposable();
        subscriptionsMap.put(object,compositeDisposable);
        return compositeDisposable;
    }

//    public Disposable register(Class eventType, Consumer observer) {
//        return toObserverable(eventType).subscribe(observer);
//    }


    public void register(Object object,Class eventType, Consumer observer) {
        CompositeDisposable compositeDisposable = initRxBus(object);
        Disposable disposable = toObserverable(eventType).subscribe(observer);
        compositeDisposable.add(disposable);
    }

//    public Disposable register(Class eventType, Consumer observer, Scheduler scheduler) {
//        return toObserverable(eventType).observeOn(scheduler).subscribe(observer);
//    }



    public void unRegister(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void unRegister(Object object) {
        if (object != null) {
            CompositeDisposable compositeDisposable = subscriptionsMap.get(object);
            if (compositeDisposable != null) {
                compositeDisposable.dispose();
                subscriptionsMap.remove(object);
            }
        }
    }

    public void unRegister(CompositeDisposable compositeDisposable) {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public void post(final Object event) {
        subjectBus.onNext(event);
    }

    private Observable toObserverable(Class cls) {
        return subjectBus.ofType(cls);
    }

    public boolean hasObservers() {
        return subjectBus.hasObservers();
    }
}
