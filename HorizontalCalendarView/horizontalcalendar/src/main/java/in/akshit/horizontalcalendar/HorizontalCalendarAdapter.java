package in.akshit.horizontalcalendar;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;

class HorizontalCalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<HorizontalCalendarModel> list;
    private Context mCtx;
    private int select = 0;
    private HorizontalCalendarView.OnCalendarListener onCalendarListener;

    void setOnCalendarListener(HorizontalCalendarView.OnCalendarListener onCalendarListener) {
        this.onCalendarListener = onCalendarListener;
    }

    HorizontalCalendarAdapter(ArrayList<HorizontalCalendarModel> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == R.layout.horizontal_calendar_item_select) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_calendar_item_select, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_calendar_item, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final HorizontalCalendarModel model = list.get(position);
        ((MyViewHolder) holder).bind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null) {
            if (list.get(position).getStatus() == 1) {
                return R.layout.horizontal_calendar_item_select;
            }
        }
        return R.layout.horizontal_calendar_item;
    }

    public void updateSelected(final HorizontalCalendarModel model) {
        int index = list.indexOf(model);
        for (int i = 0; i < list.size(); i++) {
            if (i == index) {
                if (!list.get(i).isClick()) {
                    list.get(i).setStatus(1);
                    notifyItemChanged(i);
                }
            } else {
                if (list.get(i).isClick()) {
                    list.get(i).setStatus(0);
                    notifyItemChanged(i);
                }
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date_month_tv, day_tv;
        private LinearLayout parent_ln;

        MyViewHolder(View view) {
            super(view);
            date_month_tv = view.findViewById(R.id.date_month_tv);
            parent_ln = view.findViewById(R.id.parent);
            day_tv = view.findViewById(R.id.day_tv);
        }

        public void bind(final HorizontalCalendarModel model) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(model.getTimeinmilli());

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            String date = Tools.getDateOfWeek(calendar.get(Calendar.DAY_OF_WEEK));

            SpannableStringBuilder sp = new SpannableStringBuilder();
            sp.append("(").append(String.valueOf(day))
                    .append("/")
                    .append(String.valueOf(month))
                    .append(")");
            day_tv.setText(date);
            date_month_tv.setText(sp);
            parent_ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCalendarListener.onDateSelected(model);
                }
            });
        }
    }
}