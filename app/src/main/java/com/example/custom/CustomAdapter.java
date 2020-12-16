package com.example.custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.model.Contact;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Contact> datas;
    private LayoutInflater layoutInflater;

    public CustomAdapter(Context context, List<Contact> datas)
    {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.custom_row, parent, false);
            holder = new ViewHolder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.imgView);
            holder.txtView = (TextView) convertView.findViewById(R.id.txtView);
            holder.layoutRow = (LinearLayout) convertView.findViewById(R.id.layoutRow);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = this.datas.get(position);

        int imgId = this.getMipMapResource(contact.getImage());
        holder.imgView.setImageResource(imgId);
        holder.txtView.setText(contact.getTextValue());
        holder.layoutRow.setBackgroundColor(0xFFCCCCCC);
        return convertView;
    }

    public int getMipMapResource(String resName)
    {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(resName, "mipmap", pkgName);
    }

    static class ViewHolder
    {
        ImageView imgView;
        TextView txtView;
        LinearLayout layoutRow;
    }
}
