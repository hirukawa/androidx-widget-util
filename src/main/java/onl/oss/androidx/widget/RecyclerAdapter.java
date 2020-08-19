package onl.oss.androidx.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter<T, VH extends AbstractViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private int       resource;
    private Class<VH> viewHolderClass;
    private List<T>   items;

    public RecyclerAdapter(int resource, Class<VH> viewHolderClass) {
        this.resource = resource;
        this.viewHolderClass = viewHolderClass;
        this.items = new ArrayList<T>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(resource, parent, false);
        try {
            if(viewHolderClass.isMemberClass() && !Modifier.isStatic(viewHolderClass.getModifiers())) {
                throw new NoSuchMethodException(viewHolderClass.getSimpleName() + " must be a top level class or a static nested class");
            }
            Constructor<VH> constructor = viewHolderClass.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            VH viewHolder = constructor.newInstance(itemView);
            return viewHolder;
        } catch(NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch(InstantiationException e) {
            throw new RuntimeException(e);
        } catch(InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = items.get(position);
        holder.bind(position, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> items) {
        if(items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void set(List<T> items) {
        this.items.clear();
        if(items != null) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }
}
