/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectSets$SynchronizedSet
 *  com.viaversion.viaversion.libs.fastutil.objects.ObjectSets$UnmodifiableSet
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public final class ObjectSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private ObjectSets() {
    }

    public static <K> ObjectSet<K> emptySet() {
        return EMPTY_SET;
    }

    public static <K> ObjectSet<K> singleton(K element) {
        return new Singleton<K>(element);
    }

    public static <K> ObjectSet<K> synchronize(ObjectSet<K> s) {
        return new SynchronizedSet(s);
    }

    public static <K> ObjectSet<K> synchronize(ObjectSet<K> s, Object sync) {
        return new SynchronizedSet(s, sync);
    }

    public static <K> ObjectSet<K> unmodifiable(ObjectSet<K> s) {
        return new UnmodifiableSet(s);
    }

    public static class EmptySet<K>
    extends ObjectCollections.EmptyCollection<K>
    implements ObjectSet<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(Object ok) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return EMPTY_SET;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Set && ((Set)o).isEmpty();
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }

    public static class Singleton<K>
    extends AbstractObjectSet<K>
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final K element;

        protected Singleton(K element) {
            this.element = element;
        }

        @Override
        public boolean contains(Object k) {
            return Objects.equals(k, this.element);
        }

        @Override
        public boolean remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectListIterator<K> iterator() {
            return ObjectIterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }
    }
}

