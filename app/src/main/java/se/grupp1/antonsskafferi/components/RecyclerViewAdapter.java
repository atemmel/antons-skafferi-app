package se.grupp1.antonsskafferi.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import se.grupp1.antonsskafferi.R;
import se.grupp1.antonsskafferi.fragments.BookingFragment;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Integer> mData;
    private static ArrayList<Integer> isChosen = new ArrayList<>();
    private static BookingFragment.RecyclerCallback callback;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.recycler_checkbox, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight()/4;
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    public RecyclerViewAdapter(Context mContext, ArrayList<Integer> mData, BookingFragment.RecyclerCallback callback)
    {
        this.mContext = mContext;
        this.mData = mData;
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {
        holder.table_checkbox.append("" + mData.get(position));

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public ArrayList<Integer> getIsChosen()
    {
        return isChosen;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox table_checkbox;

        public MyViewHolder(View itemView){
            super(itemView);

            table_checkbox = (CheckBox) itemView.findViewById(R.id.recycler_checkbox);

            table_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    int tableId = Integer.parseInt(table_checkbox.getText().toString());
                    if(isChecked)
                    {
                        isChosen.add(tableId);
                    }
                    else
                    {
                        for(int i = 0; i < isChosen.size(); i++)
                        {
                            if(tableId == isChosen.get(i))
                            {
                                isChosen.remove(i);
                                i--;
                            }
                        }
                    }
                    callback.getData(isChosen);
                }
            });

        }
    }

}
