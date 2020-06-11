package com.example.restclient.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restclient.APIClient;
import com.example.restclient.APIInterface;
import com.example.restclient.R;
import com.example.restclient.entity.GoodRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String DATE_RESPONSE_FORMAT="MMMM d, yyyy";

    private List<GoodRecord> records;

    APIInterface apiInterface;

    public RecyclerAdapter(List<GoodRecord> records) {
        this.records = records;
        apiInterface = APIClient.getClient().create(APIInterface .class);
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(GoodRecord record);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.activity_recycle_view_item, viewGroup, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(records.get(i));

    }

    public void addItem(int position, GoodRecord record) {
        records.add(position, record);
        notifyDataSetChanged();
    }

    public void setItems(List<GoodRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    public GoodRecord remove(int position) {
        GoodRecord record = records.remove(position);
        notifyDataSetChanged();
        return record;
    }

    public void clearItems() {
        records.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    private void deleteGood(GoodRecord record) {
        int position = records.indexOf(record);
        records.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameView;
        private TextView dateView;
        private Button deleteButton;
//        private DeleteButtonListener deleteButtonListener;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name);
            dateView = (TextView) itemView.findViewById(R.id.date);
//            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
//            deleteButtonListener = new DeleteButtonListener();
//            deleteButton.setOnClickListener(deleteButtonListener);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(records.get(position));
                        }
                    }
                }
            });
        }

        public void bind(GoodRecord record) {
            nameView.setText(record.getName());
            dateView.setText(record.getPriotity().toString());

           // String creationDateFormatted = getFormattedDate(record.getDate().toString());
//            String creationDate = record.getDate();
//
//            if(creationDate.trim().equals("")) {
//                dateView.setText("");
//            } else {
//                dateView.setText(record.getDate());
//            }

         //   deleteButtonListener.setRecord(record);
        }

        private String getFormattedDate(String rawDate) {
            SimpleDateFormat utcFormat = new SimpleDateFormat(DATE_RESPONSE_FORMAT, Locale.ROOT);
            try {
                Date date = utcFormat.parse(rawDate);
                return utcFormat.format(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    private class DeleteButtonListener implements View.OnClickListener {
//        private GoodRecord record;
//
//        @Override
//        public void onClick(View v) {
//            deleteGood(record);
//        }
//
//        public void setRecord(GoodRecord record) {
//            this.record = record;
//        }
//    }
}