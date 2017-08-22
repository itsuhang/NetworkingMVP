package com.suhang.networkmvp.function.aspjectj


import com.suhang.networkmvp.mvp.result.*
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by 苏杭 on 2017/8/17 11:47.
 */
@Aspect
class AspectJ : AnkoLogger {
    @Before("execution(* com.suhang.networkmvp.function.rx.Consumer.accept(..))")
    @Throws(Throwable::class)
    fun onEventReceive(joinPoint: JoinPoint) {
        val arg = joinPoint.args[0]
        if (arg is SuccessResult) {
            info("autoLog-receive-success:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nTAG=${arg.tag}  \nRESULT=${arg.result}")
        }
        if (arg is ErrorResult) {
            info("autoLog-receive-error:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nTAG=${arg.tag}  \nRESULT=${arg.result}")
        }
        if (arg is LoadingResult) {
            info("autoLog-receive-loading:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nTAG=${arg.tag}  \nISLOADING=${arg.isLoading}")
        }
        if (arg is EventResult) {
            info("autoLog-receive-event:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nVIEW=${arg.view}")
        }
        if (arg is ProgressResult) {
            info("autoLog-receive-process:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nTAG=${arg.tag}  \nPROGRESS=${arg.progress}  \nDONE=${arg.done}")
        }
    }

    @Before("call(* com.suhang.networkmvp.function.rx.RxBus.post(..))")
    @Throws(Throwable::class)
    fun onEventPost(joinPoint: JoinPoint) {
        val arg = joinPoint.args[0]
        if (arg is SuccessResult) {
            info("autoLog-post-success:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nTAG=${arg.tag}  \nRESULT=${arg.result}")
        }
        if (arg is ErrorResult) {
            info("autoLog-post-success:FILENAME=${joinPoint.sourceLocation.fileName}  LINE=${joinPoint.sourceLocation.line}\nTAG=${arg.tag}  \nRESULT=${arg.result}")
        }
    }


    @Before("execution(* android.app.Activity.onCreate(..))")
    @Throws(Throwable::class)
    fun onActivityCreate(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onCreate ${joinPoint.target}")
        }
    }

    @Before("execution(* android.app.Activity.onResume(..))")
    @Throws(Throwable::class)
    fun onActivityResume(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onResume ${joinPoint.target}")
        }
    }

    @Before("execution(* android.app.Activity.onStart(..))")
    @Throws(Throwable::class)
    fun onActivityStart(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onStart ${joinPoint.target}")
        }
    }

    @Before("execution(* android.app.Activity.onRestart(..))")
    @Throws(Throwable::class)
    fun onActivityRestart(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onRestart ${joinPoint.target}")
        }
    }

    @Before("execution(* android.app.Activity.onPause(..))")
    @Throws(Throwable::class)
    fun onActivityPause(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onPause ${joinPoint.target}")
        }
    }

    @Before("execution(* android.app.Activity.onStop(..))")
    @Throws(Throwable::class)
    fun onActivityStop(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onStop ${joinPoint.target}")
        }
    }

    @Before("execution(* android.app.Activity.onDestroy(..))")
    @Throws(Throwable::class)
    fun onActivityDestroy(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseActivity")) {
            info("LifeCycle-Activity-onDestroy ${joinPoint.target}")
        }
    }


    @Before("execution(* android.support.v4.app.Fragment.onCreate(..))")
    @Throws(Throwable::class)
    fun onFragmentCreate(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onCreate ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onCreateView(..))")
    @Throws(Throwable::class)
    fun onFragmentCreateView(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onCreateView ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onActivityCreate(..))")
    @Throws(Throwable::class)
    fun onFragmentActivityCreate(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onActivityCreate ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onAttach(..))")
    @Throws(Throwable::class)
    fun onFragmentAttach(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onAttach ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onDetach(..))")
    @Throws(Throwable::class)
    fun onFragmentDetach(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onDetach ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onDestroyView(..))")
    @Throws(Throwable::class)
    fun onFragmentDestroyView(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onDestroyView ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onResume(..))")
    @Throws(Throwable::class)
    fun onFragmentResume(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onResume ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onStart(..))")
    @Throws(Throwable::class)
    fun onFragmentStart(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onStart ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onPause(..))")
    @Throws(Throwable::class)
    fun onFragmentPause(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onPause ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onStop(..))")
    @Throws(Throwable::class)
    fun onFragmentStop(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onStop ${joinPoint.target}")
        }
    }

    @Before("execution(* android.support.v4.app.Fragment.onDestroy(..))")
    @Throws(Throwable::class)
    fun onFragmentDestroy(joinPoint: JoinPoint) {
        if (joinPoint.sourceLocation.fileName.contains("BaseFragment")) {
            info("LifeCycle-Fragment-onDestroy ${joinPoint.target}")
        }
    }
}
