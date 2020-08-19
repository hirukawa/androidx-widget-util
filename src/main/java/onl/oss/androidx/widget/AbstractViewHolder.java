package onl.oss.androidx.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractViewHolder<T> extends RecyclerView.ViewHolder {
    public AbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(int position, T item);
}
