package com.aniruddhfichadia.firebasehelpers;

import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;
import com.firebase.client.ValueEventListener;
import com.firebase.client.annotations.Nullable;

/**
 * Created by Aniruddh Fichadia on 30/06/2016.
 */
public abstract class TypeValueEventListener<T>
        implements ValueEventListener {
    @NonNull
    private final FirebaseGsonSerializer firebaseSerializer;
    @NonNull
    private final Class<T>               deserializedClass;

    public TypeValueEventListener(@NonNull FirebaseGsonSerializer firebaseSerializer,
                                  @NonNull Class<T> deserializedClass) {
        this.firebaseSerializer = firebaseSerializer;
        this.deserializedClass = deserializedClass;
    }


    @Override
    public final void onDataChange(DataSnapshot dataSnapshot) {
        onDataChange(firebaseSerializer.deserialize(dataSnapshot, deserializedClass));
    }

    public abstract void onDataChange(@Nullable T value);
}