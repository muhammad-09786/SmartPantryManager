package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {


    public interface Listener {
        void onItemClick(PantryItem item);
        void onDeleteClick(PantryItem item);
    }

    private List<PantryItem> items;
    private final Listener listener;
    private boolean highlightExpiring = false;

    public PantryAdapter(List<PantryItem> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }


    public void setItems(List<PantryItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
    public void setHighlightExpiring(boolean highlight) {
        this.highlightExpiring = highlight;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvDetails.setText(formatQty(item.getQuantity()) + " " + item.getUnit());

        String expiry = item.getExpiry();
        if (expiry == null || expiry.isEmpty()) {
            holder.tvExpiry.setVisibility(View.GONE);
        } else {
            holder.tvExpiry.setVisibility(View.VISIBLE);
            if (highlightExpiring && isExpiringSoon(expiry)) {
                holder.tvExpiry.setText("Expires soon: " + expiry);
                holder.tvExpiry.setTextColor(0xFFD32F2F);
            } else {
                holder.tvExpiry.setText("Expires: " + expiry);
                holder.tvExpiry.setTextColor(0xFF666666);
            }
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    private String formatQty(double q) {
        return (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
    }


    private boolean isExpiringSoon(String expiry) {
        try {
            java.text.SimpleDateFormat fmt =
                    new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US);
            java.util.Date date = fmt.parse(expiry);
            if (date == null) return false;
            long threeDaysMs = 3L * 24 * 60 * 60 * 1000;
            return date.getTime() - System.currentTimeMillis() <= threeDaysMs;
        } catch (java.text.ParseException e) {
            return false;
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails, tvExpiry;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvExpiry = itemView.findViewById(R.id.tvExpiry);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}