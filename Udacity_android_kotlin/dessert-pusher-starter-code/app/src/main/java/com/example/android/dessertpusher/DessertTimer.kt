/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dessertpusher

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

/**
 * 시작하거나 정지할 수 있는 타이머를 나타내는 클래스 입니다. secondsCount 출력 카운트:
 * 시작한 지 몇 초, 매 1초마다.
 *
 * -----
 *
 * Handler 와 Runnable은 본 강의의 범위를 벗어난다. 이것은 부분적으로 그들이 상대하기 때문이다.
 * 실링, 나중 수업에서 다루게 될 복잡한 주제
 *
 * 지금 자세히 알아보려면 Android Developer 설명서를 참조하십시오.
 * 나사산:
 *
 * https://developer.android.com/guide/components/processes-and-threads
 *
 */
class DessertTimer(lifecycle: Lifecycle) : LifecycleObserver{

    // 타이머가 시작된 후 카운트된 시간(초)
    var secondsCount = 0

    /**
     * [Handler]는 일련의 메시지([Android.os]로 알려진)를 처리하기 위한 클래스입니다.메시지]s)
     * 또는 작업([Runnable]이라고 함)
     */
    private var handler = Handler()
    private lateinit var runnable: Runnable

    init {
        lifecycle.addObserver(this)
    }

    //@OnLifecycleEvent를 사용하여 startTimer 및 stopTimer에 주석 달기
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        // 로그를 인쇄하고 초 카운터를 증분하는 실행 가능한 작업 생성
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at : $secondsCount")
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            // postDelayed 처리기가 순환하는 작업 대기열에 작업 다시 추가
            // 통과. delayMillis parameter는 handler 에게 runnable 을
            // 1초(1000ms)
            handler.postDelayed(runnable, 1000)
        }

        // 이것이 처음에 타이머를 시작하는 것이다.
        handler.postDelayed(runnable, 1000)

        // handler가 작동하는 스레드(Tread)는 루퍼(Looper)라는 클래스에 의해 결정된다는 점에 유의하십시오.
        // 이 경우, Looper가 정의되지 않으며, 기본값인 메인 스레드 또는 UI 스레드로 설정된다.
    }

    //@OnLifecycleEvent를 사용하여 startTimer 및 stopTimer에 주석 달기
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        // 처리기 대기열에서 실행 가능한 모든 보류 중인 게시물을 제거하여 효과적으로 실행 중지
        // timer
        handler.removeCallbacks(runnable)
    }
}