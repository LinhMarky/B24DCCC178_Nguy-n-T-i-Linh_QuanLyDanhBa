package com.example.quanlydanhba;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        }

        TextView tvAvatar = convertView.findViewById(R.id.tvAvatar);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvGroup = convertView.findViewById(R.id.tvGroup);
        ImageView tvFavorite = convertView.findViewById(R.id.tvFavorite);

        Contact contact = contactList.get(position);

        String firstLetter = contact.getName() != null && !contact.getName().isEmpty() ? String.valueOf(contact.getName().charAt(0)).toUpperCase() : "";
        tvAvatar.setText(firstLetter);
        
        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());
        tvGroup.setText(contact.getGroup());
        
        if (contact.isFavorite()) {
            tvFavorite.setVisibility(View.VISIBLE);
        } else {
            tvFavorite.setVisibility(View.GONE);
        }

        return convertView;
    }
}