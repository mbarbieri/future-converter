/**
 * Copyright 2009-2015 the original author or authors.
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
package net.javacrumbs.futureconverter.java8guava;


import com.google.common.util.concurrent.ListenableFuture;
import net.javacrumbs.futureconverter.common.internal.CancellationCallback;
import net.javacrumbs.futureconverter.guavacommon.GuavaFutureUtils;
import net.javacrumbs.futureconverter.guavacommon.GuavaFutureUtils.ListenableFutureValueConsumer;
import net.javacrumbs.futureconverter.guavacommon.Java8FutureUtils;
import net.javacrumbs.futureconverter.guavacommon.Java8FutureUtils.CompletableFutureValueConsumer;

import java.util.concurrent.CompletableFuture;

/**
 * Converts between {@link java.util.concurrent.CompletableFuture} and Guava {@link com.google.common.util.concurrent.ListenableFuture}.
 */
public class FutureConverter {

    /**
     * Converts {@link java.util.concurrent.CompletableFuture} to {@link com.google.common.util.concurrent.ListenableFuture}.
     */
    public static <T> ListenableFuture<T> toListenableFuture(CompletableFuture<T> completableFuture) {
        ListenableFutureValueConsumer<T> listenableFuture = GuavaFutureUtils.createListenableFuture();
        CancellationCallback cancellationCallback = Java8FutureUtils.registerListeners(completableFuture, listenableFuture);
        return GuavaFutureUtils.registerCancellationCallback(listenableFuture, cancellationCallback);
    }

    /**
     * Converts  {@link com.google.common.util.concurrent.ListenableFuture} to {@link java.util.concurrent.CompletableFuture}.
     */
    public static <T> CompletableFuture<T> toCompletableFuture(ListenableFuture<T> listenableFuture) {
        CompletableFutureValueConsumer<T> completableFuture = Java8FutureUtils.createCompletableFuture();
        CancellationCallback cancellationCallback = GuavaFutureUtils.registerListeners(listenableFuture, completableFuture);
        return Java8FutureUtils.registerCancellationCallback(completableFuture, cancellationCallback);
    }
}
