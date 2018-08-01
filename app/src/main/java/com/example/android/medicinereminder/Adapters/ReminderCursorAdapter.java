package com.example.android.medicinereminder.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

/**
 * This is the custom cursor adapter for recyclerview
 */

public abstract class ReminderCursorAdapter <VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>  {

    private Context mContext;

    private Cursor mCursor;

    private boolean mCursorNotNull;

    private int mRowIdColumn;

    private DataSetObserver mObserver;

    public ReminderCursorAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mCursorNotNull = cursor != null;
        mRowIdColumn = mCursorNotNull ? mCursor.getColumnIndex("_id") : -1;
        mObserver = new NotifyingDataSetObserver();
        if (mCursor != null) {
            mCursor.registerDataSetObserver(mObserver);
        }
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if (mCursorNotNull && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (mCursorNotNull && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        if (!mCursorNotNull) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        onBindViewHolder(viewHolder, mCursor);
    }


    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mObserver != null) {
            oldCursor.unregisterDataSetObserver(mObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (mObserver != null) {
                mCursor.registerDataSetObserver(mObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mCursorNotNull = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mCursorNotNull = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            mCursorNotNull = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mCursorNotNull = false;
            notifyDataSetChanged();
        }
    }
}
