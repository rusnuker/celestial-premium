/*
 * Decompiled with CFR 0.150.
 */
package org.apache.commons.io.input;

import org.apache.commons.io.input.Tailer;

public interface TailerListener {
    public void init(Tailer var1);

    public void fileNotFound();

    public void fileRotated();

    public void handle(String var1);

    public void handle(Exception var1);
}

