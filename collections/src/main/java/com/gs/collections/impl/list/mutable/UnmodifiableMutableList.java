/*
 * Copyright 2014 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.list.mutable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import com.gs.collections.api.LazyIterable;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.function.primitive.BooleanFunction;
import com.gs.collections.api.block.function.primitive.ByteFunction;
import com.gs.collections.api.block.function.primitive.CharFunction;
import com.gs.collections.api.block.function.primitive.DoubleFunction;
import com.gs.collections.api.block.function.primitive.FloatFunction;
import com.gs.collections.api.block.function.primitive.IntFunction;
import com.gs.collections.api.block.function.primitive.LongFunction;
import com.gs.collections.api.block.function.primitive.ShortFunction;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.block.predicate.Predicate2;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.block.procedure.primitive.ObjectIntProcedure;
import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.list.primitive.MutableBooleanList;
import com.gs.collections.api.list.primitive.MutableByteList;
import com.gs.collections.api.list.primitive.MutableCharList;
import com.gs.collections.api.list.primitive.MutableDoubleList;
import com.gs.collections.api.list.primitive.MutableFloatList;
import com.gs.collections.api.list.primitive.MutableIntList;
import com.gs.collections.api.list.primitive.MutableLongList;
import com.gs.collections.api.list.primitive.MutableShortList;
import com.gs.collections.api.multimap.list.MutableListMultimap;
import com.gs.collections.api.partition.list.PartitionMutableList;
import com.gs.collections.api.stack.MutableStack;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.collection.mutable.AbstractUnmodifiableMutableCollection;
import com.gs.collections.impl.collection.mutable.UnmodifiableCollectionSerializationProxy;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.lazy.ReverseIterable;
import com.gs.collections.impl.stack.mutable.ArrayStack;

/**
 * An unmodifiable view of a list.
 *
 * @see MutableList#asUnmodifiable()
 */
public class UnmodifiableMutableList<T>
        extends AbstractUnmodifiableMutableCollection<T>
        implements MutableList<T>, Serializable
{
    UnmodifiableMutableList(MutableList<? extends T> mutableList)
    {
        super(mutableList);
    }

    /**
     * This method will take a MutableList and wrap it directly in a UnmodifiableMutableList.  It will
     * take any other non-GS-list and first adapt it will a ListAdapter, and then return a
     * UnmodifiableMutableList that wraps the adapter.
     */
    public static <E, L extends List<E>> UnmodifiableMutableList<E> of(L list)
    {
        if (list == null)
        {
            throw new IllegalArgumentException("cannot create an UnmodifiableMutableList for null");
        }
        if (list instanceof RandomAccess)
        {
            return new RandomAccessUnmodifiableMutableList<E>(RandomAccessListAdapter.adapt(list));
        }
        return new UnmodifiableMutableList<E>(ListAdapter.adapt(list));
    }

    private MutableList<T> getMutableList()
    {
        return (MutableList<T>) this.getMutableCollection();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.getMutableList().equals(obj);
    }

    @Override
    public int hashCode()
    {
        return this.getMutableList().hashCode();
    }

    @Override
    public MutableList<T> asUnmodifiable()
    {
        return this;
    }

    @Override
    public ImmutableList<T> toImmutable()
    {
        return Lists.immutable.ofAll(this.getMutableList());
    }

    @Override
    public MutableList<T> asSynchronized()
    {
        return SynchronizedMutableList.of(this);
    }

    @Override
    public UnmodifiableMutableList<T> clone()
    {
        return this;
    }

    @Override
    public MutableList<T> newEmpty()
    {
        return this.getMutableList().newEmpty();
    }

    public void forEach(int fromIndex, int toIndex, Procedure<? super T> procedure)
    {
        this.getMutableList().forEach(fromIndex, toIndex, procedure);
    }

    public void reverseForEach(Procedure<? super T> procedure)
    {
        this.getMutableList().reverseForEach(procedure);
    }

    public void forEachWithIndex(int fromIndex, int toIndex, ObjectIntProcedure<? super T> objectIntProcedure)
    {
        this.getMutableList().forEachWithIndex(fromIndex, toIndex, objectIntProcedure);
    }

    public UnmodifiableMutableList<T> sortThis(Comparator<? super T> comparator)
    {
        throw new UnsupportedOperationException("Cannot call sortThis() on " + this.getClass().getSimpleName());
    }

    public UnmodifiableMutableList<T> sortThis()
    {
        throw new UnsupportedOperationException("Cannot call sortThis() on " + this.getClass().getSimpleName());
    }

    public MutableList<T> toReversed()
    {
        return this.getMutableList().toReversed();
    }

    public MutableList<T> reverseThis()
    {
        throw new UnsupportedOperationException("Cannot call reverseThis() on " + this.getClass().getSimpleName());
    }

    public MutableStack<T> toStack()
    {
        return ArrayStack.newStack(this.getMutableList());
    }

    public <V extends Comparable<? super V>> MutableList<T> sortThisBy(Function<? super T, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot call sortThisBy() on " + this.getClass().getSimpleName());
    }

    public boolean addAll(int index, Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException("Cannot call addAll() on " + this.getClass().getSimpleName());
    }

    public T get(int index)
    {
        return this.getMutableList().get(index);
    }

    public T set(int index, T element)
    {
        throw new UnsupportedOperationException("Cannot call set() on " + this.getClass().getSimpleName());
    }

    public void add(int index, T element)
    {
        throw new UnsupportedOperationException("Cannot call add() on " + this.getClass().getSimpleName());
    }

    public T remove(int index)
    {
        throw new UnsupportedOperationException("Cannot call remove() on " + this.getClass().getSimpleName());
    }

    public int indexOf(Object o)
    {
        return this.getMutableList().indexOf(o);
    }

    public int lastIndexOf(Object o)
    {
        return this.getMutableList().lastIndexOf(o);
    }

    public ListIterator<T> listIterator()
    {
        return this.listIterator(0);
    }

    public ListIterator<T> listIterator(int index)
    {
        return new UnmodifiableListIteratorAdapter<T>(this.getMutableList().listIterator(index));
    }

    public UnmodifiableMutableList<T> subList(int fromIndex, int toIndex)
    {
        MutableList<T> subList = this.getMutableList().subList(fromIndex, toIndex);
        return of(subList);
    }

    @Override
    public <P, A> MutableList<A> collectWith(Function2<? super T, ? super P, ? extends A> function, P parameter)
    {
        return this.getMutableList().collectWith(function, parameter);
    }

    @Override
    public <V> MutableList<V> collect(Function<? super T, ? extends V> function)
    {
        return this.getMutableList().collect(function);
    }

    @Override
    public MutableBooleanList collectBoolean(BooleanFunction<? super T> booleanFunction)
    {
        return this.getMutableList().collectBoolean(booleanFunction);
    }

    @Override
    public MutableByteList collectByte(ByteFunction<? super T> byteFunction)
    {
        return this.getMutableList().collectByte(byteFunction);
    }

    @Override
    public MutableCharList collectChar(CharFunction<? super T> charFunction)
    {
        return this.getMutableList().collectChar(charFunction);
    }

    @Override
    public MutableDoubleList collectDouble(DoubleFunction<? super T> doubleFunction)
    {
        return this.getMutableList().collectDouble(doubleFunction);
    }

    @Override
    public MutableFloatList collectFloat(FloatFunction<? super T> floatFunction)
    {
        return this.getMutableList().collectFloat(floatFunction);
    }

    @Override
    public MutableIntList collectInt(IntFunction<? super T> intFunction)
    {
        return this.getMutableList().collectInt(intFunction);
    }

    @Override
    public MutableLongList collectLong(LongFunction<? super T> longFunction)
    {
        return this.getMutableList().collectLong(longFunction);
    }

    @Override
    public MutableShortList collectShort(ShortFunction<? super T> shortFunction)
    {
        return this.getMutableList().collectShort(shortFunction);
    }

    @Override
    public <V> MutableList<V> flatCollect(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.getMutableList().flatCollect(function);
    }

    @Override
    public <V> MutableList<V> collectIf(
            Predicate<? super T> predicate,
            Function<? super T, ? extends V> function)
    {
        return this.getMutableList().collectIf(predicate, function);
    }

    @Override
    public <V> MutableListMultimap<V, T> groupBy(Function<? super T, ? extends V> function)
    {
        return this.getMutableList().groupBy(function);
    }

    @Override
    public <V> MutableListMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.getMutableList().groupByEach(function);
    }

    @Override
    public MutableList<T> reject(Predicate<? super T> predicate)
    {
        return this.getMutableList().reject(predicate);
    }

    @Override
    public <P> MutableList<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getMutableList().rejectWith(predicate, parameter);
    }

    @Override
    public MutableList<T> select(Predicate<? super T> predicate)
    {
        return this.getMutableList().select(predicate);
    }

    @Override
    public <P> MutableList<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getMutableList().selectWith(predicate, parameter);
    }

    @Override
    public PartitionMutableList<T> partition(Predicate<? super T> predicate)
    {
        return this.getMutableList().partition(predicate);
    }

    @Override
    public <P> PartitionMutableList<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getMutableList().partitionWith(predicate, parameter);
    }

    @Override
    public <S> MutableList<S> selectInstancesOf(Class<S> clazz)
    {
        return this.getMutableList().selectInstancesOf(clazz);
    }

    public MutableList<T> distinct()
    {
        return this.getMutableList().distinct();
    }

    @Override
    public <S> MutableList<Pair<T, S>> zip(Iterable<S> that)
    {
        return this.getMutableList().zip(that);
    }

    @Override
    public MutableList<Pair<T, Integer>> zipWithIndex()
    {
        return this.getMutableList().zipWithIndex();
    }

    public MutableList<T> takeWhile(Predicate<? super T> predicate)
    {
        return this.getMutableList().takeWhile(predicate);
    }

    public MutableList<T> dropWhile(Predicate<? super T> predicate)
    {
        return this.getMutableList().dropWhile(predicate);
    }

    public PartitionMutableList<T> partitionWhile(Predicate<? super T> predicate)
    {
        return this.getMutableList().partitionWhile(predicate);
    }

    public LazyIterable<T> asReversed()
    {
        return ReverseIterable.adapt(this);
    }

    public int binarySearch(T key, Comparator<? super T> comparator)
    {
        return Collections.binarySearch(this, key, comparator);
    }

    public int binarySearch(T key)
    {
        return Collections.binarySearch((List<? extends Comparable<? super T>>) this, key);
    }

    @Override
    public MutableList<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot call with() on " + this.getClass().getSimpleName());
    }

    @Override
    public MutableList<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot call without() on " + this.getClass().getSimpleName());
    }

    @Override
    public MutableList<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot call withAll() on " + this.getClass().getSimpleName());
    }

    @Override
    public MutableList<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot call withoutAll() on " + this.getClass().getSimpleName());
    }

    private static final class RandomAccessUnmodifiableMutableList<T> extends UnmodifiableMutableList<T> implements RandomAccess
    {
        private static final long serialVersionUID = 1L;

        RandomAccessUnmodifiableMutableList(MutableList<? extends T> mutableList)
        {
            super(mutableList);
        }

        @Override
        public RandomAccessUnmodifiableMutableList<T> clone()
        {
            return this;
        }
    }

    protected Object writeReplace()
    {
        return new UnmodifiableCollectionSerializationProxy<T>(this.getMutableList());
    }
}
